package com.g2d.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.g2d.display.ui.Form;
import com.g2d.display.ui.UIComponent;
import com.g2d.editor.property.PopupCellEditUILayout;

public class UIComponentEditor<C extends UIComponent> extends DisplayObjectEditor<C>
{
	private static final long serialVersionUID = 1L;
	
	class ComponentView extends JInternalFrame implements ComponentListener , ChangeListener
	{
		private static final long serialVersionUID = 1L;
		
		public ComponentView() 
		{
			super();
			if (!(object instanceof Form)) {
				this.setLocation(object.getX(), object.getY());
			}
			this.setSize(object.getWidth(), object.getHeight());
			this.setVisible(true);
			this.setResizable(true);
			this.addComponentListener(this);
		}
		
		public void paint(Graphics dg) 
		{
			Graphics2D g = (Graphics2D)dg;
			
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, getWidth()-1, getHeight()-1);
			
			object.render(g);
			
//			if (!(object instanceof Form)) {
//				this.setLocation(object.getX(), object.getY());
//			}
//			this.setSize(object.getWidth(), object.getHeight());
		}
		
		public void componentHidden(ComponentEvent e) {}
		public void componentMoved(ComponentEvent e) {
			if (!(object instanceof Form)) {
				object.setLocation(this.getX(), this.getY());
				num_size_x.setValue(object.getX());
				num_size_y.setValue(object.getY());
			}
		}
		public void componentResized(ComponentEvent e) {
			object.setSize(this.getWidth(), this.getHeight());
			num_size_w.setValue(object.getWidth());
			num_size_h.setValue(object.getHeight());
		}
		public void componentShown(ComponentEvent e) {}
		
		public void stateChanged(ChangeEvent e) {
			try {
				object.setLocation(
						Integer.parseInt(num_size_x.getValue().toString()), 
						Integer.parseInt(num_size_y.getValue().toString())
						);
				object.setSize(
						Integer.parseInt(num_size_w.getValue().toString()), 
						Integer.parseInt(num_size_h.getValue().toString())
						);
				
				if (!(object instanceof Form)) {
					this.setLocation(object.getX(), object.getY());
				}
				this.setSize(object.getWidth(), object.getHeight());
				
			} catch (Exception e2) {
				showError(e2);
			}
		}
	}
	
	
	ComponentView 	view_component;
	
	// property edit
	JLabel 			lbl_size			= new JLabel("位置范围");
	JSpinner		num_size_x			= new JSpinner();
	JSpinner		num_size_y			= new JSpinner();
	JSpinner		num_size_w			= new JSpinner();
	JSpinner		num_size_h			= new JSpinner();
	
	JLabel 			lbl_name 			= new JLabel("名字");
	JTextField 		txt_name			= new JTextField();
	
	JButton			btn_change_image	= new JButton("更换图片");
	// options
	
	public UIComponentEditor(C comp)
	{
		super(comp);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		{
			this.setTitle(object.toString());
			this.setSize(500, 600);
			//object.getRoot().setAppendWindow(this);
			
		}
		
		
		{
			JPanel pan1 = new JPanel();
			JPanel pan2 = new JPanel();
			JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pan1, pan2);
			
			{
				
				pan1.setLayout(null);
				
				view_component = new ComponentView();
				pan1.setSize(view_component.getWidth(), view_component.getHeight());
				pan1.add(view_component);
				
				split.setDividerLocation(
						view_component.getY() +
						view_component.getHeight() + 
						20);
			}
			
			{

				pan2.setLayout(null);
				
				int sx = 10, sy = -10;
				// name
				lbl_name.setBounds(sx, sy += 20, 100, 20);
				txt_name.setBounds(sx, sy += 20, 100, 20);
				txt_name.setText(object.name);
				pan2.add(lbl_name);
				pan2.add(txt_name);
				
				// image
				btn_change_image.setBounds(sx + 120, sy, 100, 20);
				btn_change_image.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						try {
							new PopupCellEditUILayout.MakeLayoutForm(object){
								@Override
								public void close() {
									object.setCustomLayout(getUILayout());
									view_component.repaint();
									super.close();
								}
							}.setVisible(true);
						} catch (Exception err) {
							showError(err);
						}
					}
				});
				pan2.add(btn_change_image);
				
				// size
				lbl_size.setBounds	(sx, sy += 20, 100, 20);
				num_size_x.setBounds(sx, sy += 20, 100, 20);
				num_size_y.setBounds(sx, sy += 20, 100, 20);
				num_size_w.setBounds(sx, sy += 20, 100, 20);
				num_size_h.setBounds(sx, sy += 20, 100, 20);
				
				num_size_x.setValue(object.getX());
				num_size_y.setValue(object.getY());
				num_size_w.setValue(object.getWidth());
				num_size_h.setValue(object.getHeight());
				
				num_size_x.addChangeListener(view_component);
				num_size_y.addChangeListener(view_component);
				num_size_w.addChangeListener(view_component);
				num_size_h.addChangeListener(view_component);
				
				pan2.add(lbl_size);
				pan2.add(num_size_x);
				pan2.add(num_size_y);
				pan2.add(num_size_w);
				pan2.add(num_size_h);
			
			}
			
			
			
			table.addTab("属性", split);
			
			table.setSelectedComponent(split);
		}
		
	}
	
	
	public void showError(Throwable cause) 
	{
		JDialog dialog = new JDialog(this, "错误:"+cause.getMessage(), true);
		dialog.setAlwaysOnTop(true);
		
		JTextArea texts = new JTextArea(cause.getMessage()+"\n");
		for (StackTraceElement stack : cause.getStackTrace()) {
			texts.append("\t"+stack.toString()+"\n");
		}
		dialog.add(texts);
		
		dialog.setSize(400,300);
		dialog.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - dialog.getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - dialog.getHeight()/2
				);
		
		dialog.setVisible(true);
		
	}
	
	
}

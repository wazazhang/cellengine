package com.g2d.editor.property;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.g2d.Tools;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.editor.Util;

/**
 * @author WAZA
 * 负责在JTable里更改颜色
 */
public class PopupCellEditUILayout extends PopupCellEdit<UILayout>
{
	static public class MakeLayoutForm extends JFrame
	{
		private static final long serialVersionUID = 1L;
		
		
		BufferedImage					image ;
		String							image_name;
		UILayout.ImageStyle				image_style			= ImageStyle.NULL;
		
		
		// left pan
		JPanel 							pan1 				= new JPanel();
		Canvas 							canvas;
		
		// left bot
		JPanel 							pan3				= new JPanel();
		DstView							dstview;
		
		// right pan
		JPanel 							pan2				= new JPanel();
		JLabel							lbl_broadsize		= new JLabel("切割尺寸");	
		JSpinner						spin_broadsize		= new JSpinner();	
		ButtonGroup						group_styles		;
		JLabel							lbl_styles			= new JLabel("切割样式");	
		
		// area split
		JSplitPane 						lsplit 				= new JSplitPane(JSplitPane.VERTICAL_SPLIT, 	pan1, 	pan3);
		JSplitPane 						split 				= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 	lsplit, pan2);
		
		JButton							ok					= new JButton("确定");
		JButton							cancel				= new JButton("取消");
		
		UIComponent						object;
		
		public MakeLayoutForm() throws Exception{
			this(null);
		}
		
		@SuppressWarnings("deprecation")
		public MakeLayoutForm(UIComponent src) throws Exception
		{
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			object = src;
			canvas 				= new SrcCanvas() ;
			dstview				= new DstView();
			
			setAlwaysOnTop(true);
			setSize(800,600);
			setLocation(
					Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
					Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2);
			
			{
				java.awt.FileDialog fd = new FileDialog(this);
				fd.setLocation(getLocation());
				fd.setTitle("选择原图片");
				fd.setMode(FileDialog.LOAD);
				fd.show();
				String file = fd.getDirectory() + fd.getFile();
				
				image_name = fd.getFile();
				
				System.out.println("You chose to open this file: " + file);
			
				FileInputStream fis = new FileInputStream(new File(file));
				image = Tools.readImage(fis);
			}
			

			// left
			{
				
				{
					pan1.setLayout(null);
					canvas.setSize(image.getWidth(null), image.getHeight(null));
					canvas.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					pan1.add(canvas);
					
					lsplit.setDividerLocation(canvas.getHeight());
		
					if (object!=null) {
						split.setDividerLocation(Math.max(canvas.getWidth()+10, object.getWidth()+10));
					}
					else{
						split.setDividerLocation(canvas.getWidth()+10);
					}

				}
				
				{
					pan3.setLayout(null);
					pan3.add(dstview);
					
				}
			}
			
			// right
			{
				int sx = 10, sy = 10;
				
				pan2.setLayout(null);
				lbl_broadsize.setBounds (sx, sy+=20, 100, 20);
				spin_broadsize.setBounds(sx, sy+=20, 100, 20);
				
				spin_broadsize.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						try
						{
							int size = Integer.parseInt(spin_broadsize.getValue().toString());
							
							if (size<1)
							{
								spin_broadsize.setValue(1);
							}
							else if ((
									image_style == UILayout.ImageStyle.IMAGE_STYLE_H_012 || 
									image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_8 || 
									image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_9
									) && size>image.getWidth(null)/2-1) 
							{
								size = image.getWidth(null)/2-1;
								spin_broadsize.setValue(size);
							}
							else if ((
									image_style == UILayout.ImageStyle.IMAGE_STYLE_V_036 || 
									image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_8 || 
									image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_9
									) && size>image.getHeight(null)/2-1) 
							{
								size = image.getHeight(null)/2-1;
								spin_broadsize.setValue(size);
							}
						
							canvas.repaint();
							dstview.repaint();
							
							
						}
						catch (Exception err) {
							err.printStackTrace();
						}
						
					}
				});
				spin_broadsize.setValue(8);			
				pan2.add(lbl_broadsize);
				pan2.add(spin_broadsize);
				
				sy += 20;
				
				{
					lbl_styles.setBounds (sx, sy+=20, 200, 20);
					pan2.add(lbl_styles);
					
					group_styles = Util.createButtonsFromEnum(UILayout.ImageStyle.class, JRadioButton.class);
					Enumeration<AbstractButton> btns = group_styles.getElements();
					int i=0;
					while(btns.hasMoreElements()){
						AbstractButton style = btns.nextElement();
						style.setBounds(sx, sy+=20, 200, 20);
						style.setActionCommand(style.getText());
						style.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e) {
								System.out.println(e.getActionCommand());
								image_style = UILayout.ImageStyle.getStyle(e.getActionCommand());
								canvas.repaint();
								dstview.repaint();
							}
						});
						pan2.add(style);
						i++;
					}
					
				}
				
				sy+=20;
				sy+=20;
				
				ok.setBounds	(sx    , sy, 100, 20);
				cancel.setBounds(sx+110, sy, 100, 20);
				ok.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						MakeLayoutForm.this.setVisible(false);
						MakeLayoutForm.this.close();
					}
				});
				cancel.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						MakeLayoutForm.this.setVisible(false);
					}
				});
				pan2.add(ok);
				pan2.add(cancel);
				
			}
			
			this.add(split);
			
		}

		public void close(){
		}
		
		
		public UILayout getUILayout() 
		{
			try{
				int bordersize = Integer.parseInt(spin_broadsize.getValue().toString());
				ImageUILayout image_layout = new ImageUILayout(image, image_name, image_style, bordersize);
				return image_layout;
			}catch (Exception e) {
				spin_broadsize.setValue(1);
				e.printStackTrace();
			}
			return UILayout.createBlankRect();
		}
		
		
		
		class SrcCanvas extends Canvas
		{
			private static final long serialVersionUID = 1L;
			
			public void update(Graphics g) {
				paint(g);
			}
			
			public void paint(Graphics g) 
			{
				if (image != null)
				{
					try
					{
						int bordersize = Integer.parseInt(spin_broadsize.getValue().toString());
						g.drawImage(image, 0, 0, canvas.getWidth(), getHeight(), this);
						g.setColor(new Color(0xffffffff, true));
						
						switch(image_style)
						{
						case IMAGE_STYLE_ALL_8:
						case IMAGE_STYLE_ALL_9:
							g.drawLine(0, bordersize, getWidth(), bordersize);
							g.drawLine(0, getHeight()-bordersize, getWidth(), getHeight()-bordersize);
							g.drawLine(bordersize, 0, bordersize, getHeight());
							g.drawLine(getWidth()-bordersize, 0, getWidth()-bordersize, getHeight());
							break;
						case IMAGE_STYLE_H_012:
							g.drawLine(bordersize, 0, bordersize, getHeight());
							g.drawLine(getWidth()-bordersize, 0, getWidth()-bordersize, getHeight());
							break;
						case IMAGE_STYLE_V_036:
							g.drawLine(0, bordersize, getWidth(), bordersize);
							g.drawLine(0, getHeight()-bordersize, getWidth(), getHeight()-bordersize);
							break;
						case IMAGE_STYLE_BACK_4:
							break;
						}
					}
					catch (Exception e) {
						spin_broadsize.setValue(1);
						e.printStackTrace();
					}
				}
			}
			
		};
		
		class DstView extends JInternalFrame 
		{
			private static final long serialVersionUID = 1L;
			
			public DstView() 
			{
				super();
				if (object!=null) {
					this.setSize(object.getWidth(), object.getHeight());
				}else{
					this.setSize(200, 200);
				}
				
				this.setVisible(true);
				this.setResizable(true);
			}
			
			public void paint(Graphics dg) 
			{
				Graphics2D g = (Graphics2D)dg;
				
				g.clearRect(0, 0, getWidth(), getHeight());

				try{
					getUILayout().render((Graphics2D)g, 0, 0, getWidth(), getHeight());
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		

	}
	
	
	
	
//	UIComponent object;
	
	
	public PopupCellEditUILayout()
	{
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (current_value!=null) {
			current_value.render((Graphics2D)g, 0, 0, getWidth(), getHeight());
		}
	}
	
	@Override
	public void onOpenEditor() 
	{
		try {
			MakeLayoutForm dialog = new MakeLayoutForm(){
				@Override
				public void close() {
					current_value = getUILayout();
					super.close();
				}
			};
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	
}

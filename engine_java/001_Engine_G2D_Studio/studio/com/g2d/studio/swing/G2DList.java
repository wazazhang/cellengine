package com.g2d.studio.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;

import com.g2d.awt.util.AbstractDialog;
import com.g2d.studio.res.Res;

public class G2DList<T extends G2DListItem> extends JList
{
	private static final long serialVersionUID = 1L;
	
	public G2DList()
	{
		this.setCellRenderer(new ListRender());
		this.addKeyListener(new ListKeyListener());
	}
	
	public G2DList(T[] items)
	{
		super(items);
		this.setCellRenderer(new ListRender());
		this.addKeyListener(new ListKeyListener());
	}
	
	public G2DList(Collection<T> items)
	{
		super(new Vector<T>(items));
		this.setCellRenderer(new ListRender());
		this.addKeyListener(new ListKeyListener());
	}
	
	
	@Override
	public void setSelectedValue(Object anObject, boolean shouldScroll) {
		super.setSelectedValue(anObject, shouldScroll);
//		if (shouldScroll) {
//			int index = getSelectedIndex();
//			Rectangle rect = getCellBounds(index, index);
//			System.out.println("getSelectedIndex = " + index);	
//			System.out.println(getSize());	
//			
//			System.out.println(rect);
//			int bw = getWidth() / rect.width;
//			if (bw > 0) {
//				rect.y = rect.y / bw;
//			}
//			System.out.println(rect);
//			scrollRectToVisible(rect);
//		}
	}
	
	@SuppressWarnings("unchecked")
	public T getSelectedItem()
	{
		try{
			return (T)super.getSelectedValue();
		}catch(Exception err){}
		return null;
	}
	
	public Object[] getSelectedItems()
	{
		try{
			return super.getSelectedValues();
		}catch(Exception err){}
		return null;
	}	
	
	protected ListSearchDialog openSearchDialog() {
		ListSearchDialog dialog = new ListSearchDialog(this);
		dialog.setVisible(true);
		return dialog;
	}
	
	class ListRender extends DefaultListCellRenderer implements ListCellRenderer
	{
		private static final long serialVersionUID = 1L;

		public ListRender() {
	         setOpaque(true);
	     }
		
		public Component getListCellRendererComponent(
				JList list, 
				Object value,
				int index, 
				boolean isSelected,
				boolean cellHasFocus) 
		{
			DefaultListCellRenderer render = (DefaultListCellRenderer)super.getListCellRendererComponent(
					list, value, index, isSelected, cellHasFocus);
			
			if (value instanceof G2DListItem) {
				G2DListItem item = (G2DListItem) value;
				Component comp = item.getListComponent(list, value, index, isSelected, cellHasFocus);
				if (comp != null) {
					comp.setSize(render.getSize());
					return comp;
				} else {
					render.setIcon(item.getListIcon(false));
					render.setText(item.getListName());
				}
			}
			
			return render;

		}
		 

	}
	
	protected class ListKeyListener extends KeyAdapter
	{
		ListSearchDialog sd = null;
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
//				if (sd != null) {
//					sd.setVisible(true);
//				} else {
//					sd = openSearchDialog();
//				}
				openSearchDialog();
			}
		}
	}

	@SuppressWarnings("serial")
	static public class ListSearchDialog  extends AbstractDialog implements ActionListener
	{
		private static String	history_text = "";

		private G2DList<?> 		list;

//		--------------------------------------------------------------------------------------------
		protected JPanel		center 		= new JPanel(null);
		
		protected JLabel		lbl_as_name	= new JLabel("表达式");
		protected JTextField 	txt_as_name	= new JTextField(history_text);
		
//		--------------------------------------------------------------------------------------------
		protected JButton 		next 		= new JButton("下一个");	
		protected JButton 		prew		= new JButton("上一个");		
		protected JButton 		cancel 		= new JButton("取消");
//		--------------------------------------------------------------------------------------------

		
		public ListSearchDialog(G2DList<?> list) 
		{
			super(list);
//			super.setModalityType(ModalityType.TOOLKIT_MODAL);
			super.setAlwaysOnTop(true);
			super.setIconImage(Res.icon_edit);
			super.setTitle("查找 "+list.getClass().getCanonicalName());
			super.setSize(320, 160);
			
			this.setCenter();
			this.list = list;
			
			add(center, BorderLayout.CENTER);
			{
				lbl_as_name	.setBounds(10, 10, 50,  30);
				txt_as_name	.setBounds(62, 12, getWidth()-20-52, 30);
				
				center		.add(lbl_as_name);
				center		.add(txt_as_name);
				
				prew		.addActionListener(this);
				next		.addActionListener(this);
				cancel		.addActionListener(this);

				next		.setBounds(getWidth()-110, 50, 100,  30);
				prew		.setBounds(getWidth()-218, 50, 100,  30);
				cancel		.setBounds(getWidth()-110, 90, 100,  30);
				
				next		.setToolTipText("Enter");
				prew		.setToolTipText("Shift+Enter");
				
				center		.add(prew);
				center		.add(next);
				center		.add(cancel);
				
				KeyStroke ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
				cancel.registerKeyboardAction(this, "cancel", ks1, JComponent.WHEN_IN_FOCUSED_WINDOW);
				
				KeyStroke ks2 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, java.awt.event.InputEvent.SHIFT_MASK);
				prew.registerKeyboardAction(this, "prew", ks2, JComponent.WHEN_IN_FOCUSED_WINDOW);
				
				KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
				next.registerKeyboardAction(this, "next", ks3, JComponent.WHEN_IN_FOCUSED_WINDOW);
			}
			
		}
		
		public G2DList<?> getList() {
			return list;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == next) {
				Object node = getUserObject(false);
				if (node != null) {
					list.setSelectedValue(node, true);
				}
			}  
			else if (e.getSource() == prew) {
				Object node = getUserObject(true);
				if (node != null) {
					list.setSelectedValue(node, true);
				}
			}
			else if (e.getSource() == cancel) {
				this.setVisible(false);
			}
		}

		protected Object getUserObject(boolean reverse)
		{
			try {
				history_text = txt_as_name.getText();
				if (!txt_as_name.getText().isEmpty()) 
				{
					Pattern pattern = Pattern.compile(txt_as_name.getText());
					
					Object current = list.getSelectedValue();
					
					Vector<Object> nodes = new Vector<Object>(list.getModel().getSize());
					for (int i=0; i<list.getModel().getSize(); i++) {
						nodes.add(list.getModel().getElementAt(i));
					}
					
					if (reverse) {
						Collections.reverse(nodes);
					}
					
					for (Object node : nodes) {
						if (current != null) {
							if (node == current) {
								current = null;
							}
						} else {
							String text = node.toString();
							if (node instanceof G2DListItem) {
								text = ((G2DListItem)node).getListName();
							}
							Matcher matcher_name = pattern.matcher(text);
							if(matcher_name.find()){
								System.out.println(text);
								return node;
							}
						}
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
			return null;
		}
	}
	
	
}

package com.g2d.editor.property;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cell.gfx.gui.UIRect;
import com.g2d.Engine;
import com.g2d.Tools;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.editor.Util;
import com.g2d.geom.Rectangle;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

/**
 * @author WAZA
 * 负责在JTable里更改颜色
 */
public class PopupCellEditUILayout extends PopupCellEdit<UILayout>
{
	public static File uilayout_root;
	
	UIComponent object;
	
	public PopupCellEditUILayout()
	{
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (current_value!=null) {
			com.g2d.Graphics2D g2d = AwtEngine.wrap((Graphics2D)g);
			current_value.render(g2d, 0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public void setValue(Field field, UILayout value, ObjectPropertyEdit comp) {
		super.setValue(field, value, comp);
		
	}
	
	@Override
	public void onOpenEditor(UILayout value) 
	{
		try {
			object = (UIComponent)sender.getObject();
			MakeLayoutForm dialog = new MakeLayoutForm(value);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class MakeLayoutForm extends AbstractFrame implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		JButton tool_changesrc = new JButton("更换图片");
		JButton tool_cleansrc = new JButton("清除图片");
		G2DWindowToolBar tools;

		BufferedImage image;
		File image_file;
		UILayout.ImageStyle image_style = ImageStyle.NULL;

		// left pan
		JPanel pan1 = new JPanel();
		SrcCanvas src_canvas;

		// left bot
		JPanel pan3 = new JPanel();
		DstView dstview;

		// right pan
		JPanel pan2 = new JPanel();
		JLabel lbl_broadsize = new JLabel("切割尺寸");
		JSpinner spin_broadsize = new JSpinner();
		ButtonGroup group_styles;
		JLabel lbl_styles = new JLabel("切割样式");

		// area split
		JSplitPane lsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pan1,
				pan3);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lsplit,
				pan2);

		
		JLabel lbl_border_color 	= new JLabel("BorderColor");
		JLabel lbl_back_color 		= new JLabel("BackColor");
		JButton btn_border_color 	= new JButton("更换边颜色");
		JButton btn_back_color 		= new JButton("更换背景色");
		
		
		JRadioButton preferredSize = new JRadioButton("调整为首选尺寸");
		
		JButton ok = new JButton("确定");
		JButton cancel = new JButton("取消");

		public MakeLayoutForm() throws Exception{
			this(null);
		}
		
		@SuppressWarnings("deprecation")
		public MakeLayoutForm(UILayout rect) throws Exception
		{
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			tools				= new G2DWindowToolBar(this, false, false, false, false);
			
//			object 				= src;
			src_canvas 				= new SrcCanvas() ;
			dstview				= new DstView();
			
			setSize(800,600);
			setLocation(
					AbstractFrame.getScreenWidth()/2 - getWidth()/2,
					AbstractFrame.getScreenHeight()/2 - getHeight()/2);

			if (rect instanceof ImageUILayout) {
				ImageUILayout srect = (ImageUILayout)rect;
				image = AwtEngine.unwrap(srect.srcImage());	
				image_file = srect.srcImageName();
				spin_broadsize.setValue(srect.clip_border);
			} 
			
			if (rect != null) {
				lbl_back_color.setForeground(
						AwtEngine.unwrap(rect.getBackColor()));
				lbl_border_color.setForeground(
						AwtEngine.unwrap(rect.getBorderColor()));
				image_style = rect.getStyle();
			} else {
				changeSrcImage();
				UILayout srect = new UILayout();
				lbl_back_color.setForeground(
						AwtEngine.unwrap(srect.getBackColor()));
				lbl_border_color.setForeground(
						AwtEngine.unwrap(srect.getBorderColor()));
			}
			
			
			// left
			{
			
				{
					pan1.setLayout(null);	
					
					if (image != null) {
						src_canvas.setSize(
								image.getWidth(null), 
								image.getHeight(null));
					}
					src_canvas.setCursor(
							Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					pan1.add(src_canvas);
					pan1.setMinimumSize(new Dimension(220, 220));
				}
				
				{
					pan3.setLayout(null);
					pan3.setMinimumSize(new Dimension(220, 220));
					pan3.add(dstview);
				}
			}
			
			// right
			{
				int sx = 10, sy = 10;
				
				pan2.setLayout(null);
				lbl_broadsize.setBounds (sx, sy+=20, 100, 20);
				spin_broadsize.setBounds(sx, sy+=20, 100, 20);
				
				spin_broadsize.addChangeListener(new SpinChanged());
				spin_broadsize.setValue(8);			
				pan2.add(lbl_broadsize);
				pan2.add(spin_broadsize);
				
				sy += 20;
				
				{
					lbl_styles.setBounds (sx, sy+=20, 200, 20);
					pan2.add(lbl_styles);
					
					group_styles = Util.createButtonsFromEnum(
							UILayout.ImageStyle.class, JRadioButton.class);
					Enumeration<AbstractButton> btns = group_styles.getElements();
					int i=0;
					while(btns.hasMoreElements()){
						JRadioButton style = (JRadioButton)btns.nextElement();
						style.setBounds(sx, sy+=20, 200, 20);
						style.setActionCommand(style.getText());
						style.addActionListener(new StyleChanged());
						if (style.getText().equals(image_style.toString())) {
							style.setSelected(true);
						}
						pan2.add(style);
						i++;
					}
					
					
					lbl_border_color.setBounds(sx, sy+=20, 200, 20);
					btn_border_color.setBounds(sx + 200, sy, 200, 20);
					lbl_back_color.setBounds(sx, sy+=20, 200, 20);
					btn_back_color.setBounds(sx + 200, sy, 200, 20);
					
					pan2.add(lbl_border_color);
					pan2.add(btn_border_color);
					pan2.add(lbl_back_color);
					pan2.add(btn_back_color);
					btn_border_color.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							changeColor(lbl_border_color);
						}
					});
					btn_back_color.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							changeColor(lbl_back_color);
						}
					});
				}
				
				sy +=20;
				sy+=20;
				
				preferredSize.setBounds(sx, sy, 200, 20);
				pan2.add(preferredSize);
				
				sy+=20;
				sy+=20;
				
				ok.setBounds	(sx    , sy, 100, 20);
				ok.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						MakeLayoutForm.this.setVisible(false);
						MakeLayoutForm.this.onOkClose();
					}
				});
				cancel.setBounds(sx+110, sy, 100, 20);
				cancel.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						MakeLayoutForm.this.setVisible(false);
					}
				});
				pan2.add(ok);
				pan2.add(cancel);
				
			}

			tools.add(tool_changesrc);
			tool_changesrc.addActionListener(this);
			
			tools.add(tool_cleansrc);
			tool_cleansrc.addActionListener(this);
			
			this.add(tools, BorderLayout.NORTH);
			this.add(split, BorderLayout.CENTER);
			
		}
		
		class StyleChanged implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				image_style = UILayout.ImageStyle.getStyle(e.getActionCommand());
				src_canvas.repaint();
				dstview.repaint();
			}
		}
		
		class SpinChanged implements ChangeListener
		{
			public void stateChanged(ChangeEvent e) {
				try
				{
					int size = Integer.parseInt(spin_broadsize.getValue().toString());
					
					if (size<1)
					{
						spin_broadsize.setValue(1);
					}
					if (image != null) {
						if ((image_style == UILayout.ImageStyle.IMAGE_STYLE_H_012||
							image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_8 || 
							image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_9)) {
							if (size > image.getWidth(null) / 2 - 1) {
								size = image.getWidth(null) / 2 - 1;
								spin_broadsize.setValue(size);
							}
						} else if ((image_style == UILayout.ImageStyle.IMAGE_STYLE_V_036|| 
								image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_8 ||
								image_style == UILayout.ImageStyle.IMAGE_STYLE_ALL_9)) {
							if (size > image.getHeight(null) / 2 - 1) {
								size = image.getHeight(null) / 2 - 1;
								spin_broadsize.setValue(size);
							}
						}
					}
					src_canvas.repaint();
					dstview.repaint();
					
					
				}
				catch (Exception err) {
					err.printStackTrace();
				}
				
			}
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == tool_changesrc) {
				changeSrcImage();
			}
			else if (e.getSource() == tool_cleansrc) {
				cleanSrcImage();
			}
		}

		public void changeColor(final JLabel lbl)
		{
			final JColorChooser colorChooser = new JColorChooser();
			JDialog dialog = JColorChooser.createDialog(
					this, 
					"Pick a Color",
					true, // modal
					colorChooser, 
					new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							lbl.setForeground(colorChooser.getColor());
							dstview.repaint();
						}
					}, // OK button handler
					null); // no CANCEL button handler
			dialog.setVisible(true);
		}
		public void cleanSrcImage() 
		{
			image = null;
			image_file = null;
			src_canvas.repaint();
			dstview.repaint();
		}
		
		public BufferedImage changeSrcImage()
		{
			java.awt.FileDialog fd = new FileDialog(this);
			fd.setLocation(getLocation());
			if (uilayout_root != null) {
				fd.setDirectory(uilayout_root.getPath());
			}
			fd.setTitle("选择原图片");
			fd.setMode(FileDialog.LOAD);
			fd.setVisible(true);
			try {
				if (fd.getFile() != null && !fd.getFile().isEmpty()) {
					String file = fd.getDirectory() + fd.getFile();
					System.out.println("You chose to open this file: " + file);
					image_file = new File(file).getCanonicalFile();
					FileInputStream fis = new FileInputStream(new File(file));
					image = AwtEngine.unwrap(Engine.getEngine().createImage(fis));
					src_canvas.setSize(
							image.getWidth(null), 
							image.getHeight(null));
					src_canvas.repaint();
					dstview.repaint();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		}
		
		public void onOkClose()
		{
			current_value = getUILayout();
			try {
				current_field.set(object, current_value);
				if (preferredSize.isSelected()) {
					Rectangle psize = current_value.getPreferredSize();
					object.setSize(new com.g2d.geom.Dimension(psize.width, psize.height));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		public UILayout getUILayout() 
		{
			try{
				if (image == null) {
					UILayout rect = new UILayout(image_style);
					rect.setBackColor(
							AwtEngine.wrap(lbl_back_color.getForeground()));
					rect.setBorderColor(
							AwtEngine.wrap(lbl_border_color.getForeground()));
					return rect;
				} else {
					int bordersize = Integer.parseInt(spin_broadsize.getValue().toString());
					ImageUILayout image_layout = new ImageUILayout(
							AwtEngine.wrap_awt(image), 
							image_file,
							image_style, 
							bordersize);
					return image_layout;
				}
			} catch (Exception e) {
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
				g.clearRect(0, 0, getWidth(), getHeight());
				
				if (image != null)
				{
					try
					{
						int bs = Integer.parseInt(spin_broadsize.getValue().toString());
						g.drawImage(image, 0, 0, src_canvas.getWidth(), getHeight(), this);
						g.setColor(new Color(0xffffffff, true));
						
						switch(image_style)
						{
						case IMAGE_STYLE_ALL_8:
						case IMAGE_STYLE_ALL_9:
							g.drawLine(0, bs, getWidth(), bs);
							g.drawLine(0, getHeight()-bs, getWidth(), getHeight()-bs);
							g.drawLine(bs, 0, bs, getHeight());
							g.drawLine(getWidth()-bs, 0, getWidth()-bs, getHeight());
							break;
						case IMAGE_STYLE_H_012:
							g.drawLine(bs, 0, bs, getHeight());
							g.drawLine(getWidth()-bs, 0, getWidth()-bs, getHeight());
							break;
						case IMAGE_STYLE_V_036:
							g.drawLine(0, bs, getWidth(), bs);
							g.drawLine(0, getHeight()-bs, getWidth(), getHeight()-bs);
							break;
						case IMAGE_STYLE_BACK_4:
							break;
						case IMAGE_STYLE_HLM:
							g.drawLine(0, bs, getWidth(), bs);
							g.drawLine(0, getHeight()-bs, getWidth(), getHeight()-bs);
							g.drawLine(bs, 0, bs, getHeight());
							break;
						case IMAGE_STYLE_VTM:
							g.drawLine(0, bs, getWidth(), bs);
							g.drawLine(bs, 0, bs, getHeight());
							g.drawLine(getWidth()-bs, 0, getWidth()-bs, getHeight());
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
				
				if (image != null) {
					this.setSize(image.getWidth(), image.getHeight());
				} else {
					this.setSize(200, 200);
				}
				
				this.setVisible(true);
				this.setResizable(true);
			}
			
			public void paint(Graphics dg) 
			{
				super.paint(dg);
				
				Graphics2D g = (Graphics2D)dg;
				
				g.clearRect(0, 0, getWidth(), getHeight());
				try{
					com.g2d.Graphics2D g2d = AwtEngine.wrap((Graphics2D)dg);
					getUILayout().render(g2d, 0, 0, getWidth(), getHeight());
				}catch (Exception e) {
					//e.printStackTrace();
				}
			}
			
		}
		

	}
	
	
	
}

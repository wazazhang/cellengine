package com.g2d.studio.cpj;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.gfx.IImage;
import com.cell.gfx.IImages;

import com.g2d.studio.Studio;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.awt.util.Tools;

public class CPJEffectImageSelectDialog extends AbstractDialog implements ActionListener, ListSelectionListener
{
	JSplitPane			split	= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	G2DList<CPJSprite>	list	= new G2DList<CPJSprite>();
	Vector<CPJSprite>	list_data;

	ImageList			childs;
	
	JButton 			ok		= new JButton("确定");
	
	TileImage			reslut_data;
	
	public CPJEffectImageSelectDialog(Component owner) 
	{
		super(owner);
		super.setLayout(new BorderLayout());
		super.setSize(800, 400);
		super.setCenter();
		
		this.add(split, BorderLayout.CENTER);
		this.add(ok, BorderLayout.SOUTH);
		this.ok.addActionListener(this);

		this.list_data = Studio.getInstance().getCPJResourceManager().getNodes(CPJResourceType.EFFECT, CPJSprite.class);
		this.list.setListData(list_data);
		this.list.addListSelectionListener(this);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.list.setVisibleRowCount(-1);
		
		JScrollPane top = new JScrollPane(list);
		top.setPreferredSize(new Dimension(400, 100));
		top.setMinimumSize(new Dimension(400, 100));
		
		split.setTopComponent(top);
		split.setBottomComponent(new JPanel());
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (list.getSelectedValue() instanceof CPJSprite) {
			CPJSprite sprite = (CPJSprite)list.getSelectedValue();
			childs = new ImageList(sprite);
			split.setBottomComponent(new JScrollPane(childs));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CPJSprite object = (CPJSprite)list.getSelectedValue();
		if (object!=null && object.parent!=null && childs!=null) {
			SubImage index = (SubImage)childs.getSelectedValue();
			if (childs!=null) {
				this.reslut_data = new TileImage(object.parent.name, object.name, index.index);
			}
		}
		this.setVisible(false);
	}
	
	public TileImage showDialog() {
		super.setVisible(true);
		return reslut_data;
	}
	
//	------------------------------------------------------------------------------------------------------

	public static class TileImage
	{
		final public String 		parent_name;
		final public String 		sprite_name;
		final public int			index;
		
		public TileImage(String parent_name, String sprite_name, int index) 
		{
			this.parent_name	= parent_name;
			this.sprite_name	= sprite_name;
			this.index			= index;
		}
		
		@Override
		public String toString() {
			return this.parent_name + "/" + this.sprite_name + "[" + index + "]";
		}
		
		public BufferedImage getEffectImage() 
		{
			try {
				CPJIndex<CPJSprite> index = Studio.getInstance().getCPJResourceManager().getNode(
						 CPJResourceType.EFFECT, 
						 parent_name, 
						 sprite_name);
				if (index != null) {
					CPJFile parent = index.getObject().parent;
					synchronized (parent.getSetResource()) {
						boolean unload = !parent.getSetResource().isLoadImages();
						try{
							parent.getSetResource().initAllStreamImages();
							IImages	tiles	= parent.getSetResource().getImages(index.getObject().getSetObject().ImagesName);
							IImage	tile	= tiles.getImage(this.index);
							if (tile!=null) {
								return Tools.createImage(tile);
							}
						} finally {
							if (unload) {
								parent.getSetResource().destoryAllStreamImages();
							}
						}
					}
				}
			} catch(Exception err) {
				err.printStackTrace();
			}
			return null;
		}
	}
	
	
	
//	------------------------------------------------------------------------------------------------------
	
	class SubImage extends JLabel implements G2DListItem
	{
		final BufferedImage		image;
		final ImageIcon 		icon;
		final int 				index;
		
		public SubImage(IImage img, int index) {
			this.image	= Tools.createImage(img);
			this.icon	= Tools.createIcon(image);
			this.index	= index;
			this.setIcon(icon);
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		
		@Override
		public ImageIcon getListIcon(boolean update) {
			return icon;
		}
		
		@Override
		public String getListName() {
			return "" + index;
		}
	}
	
	class ImageList extends G2DList<SubImage>
	{
		public ImageList(CPJSprite sprite) 
		{
			super.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			super.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			super.setVisibleRowCount(-1);

			Vector<SubImage> items = new Vector<SubImage>();
			synchronized (sprite.parent.getSetResource()) {
				boolean unload = !sprite.parent.getSetResource().isLoadImages();
				try{
					sprite.parent.getSetResource().initAllStreamImages();
					IImages tiles = sprite.parent.getSetResource().getImages(sprite.getSetObject().ImagesName);
					for (int i=0; i<tiles.getCount(); i++) {
						IImage tile = tiles.getImage(i);
						if (tile != null) {
							items.add(new SubImage(tile, i));
						}
					}
				} finally {
					if (unload) {
						sprite.parent.getSetResource().destoryAllStreamImages();
					}
				}
			}
			this.setListData(items);
		}
		
	}

}

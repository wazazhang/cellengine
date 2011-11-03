package com.g2d.studio.gameedit.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.xls.XLSFile;
import com.cell.xls.XLSFullRow;
import com.g2d.awt.util.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.XLSObjectViewer;

final public class XLSUnit extends XLSTemplateNode<TUnit>
{
	private CPJSprite cpj_sprite;
	
	public XLSUnit(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);
//		if (template_data.getDisplayNode()!=null) {
//			CPJIndex<CPJSprite> spr_index = Studio.getInstance().getCPJResourceManager().getNode(
//					CPJResourceType.ACTOR, 
//					template_data.getDisplayNode().cpj_project_name,
//					template_data.getDisplayNode().cpj_object_id);
//			if (spr_index != null) {
//				cpj_sprite = spr_index.getObject();
//			}
//		}
	}
	
	@Override
	protected TUnit newData(XLSFile xls_file, XLSFullRow xls_row) {
		return new TUnit(getIntID(), xls_row.desc);
	}
	
	public CPJSprite getCPJSprite() {
		if (cpj_sprite == null && template_data.getDisplayNode()!=null) {
			CPJIndex<CPJSprite> spr_index = Studio.getInstance().getCPJResourceManager().getNode(
					CPJResourceType.ACTOR, 
					template_data.getDisplayNode().cpj_project_name,
					template_data.getDisplayNode().cpj_object_id);
			if (spr_index != null) {
				cpj_sprite = spr_index.getObject();
			}
		}
		return cpj_sprite;	
	}
	
	public void setCPJSprite(CPJSprite sprite) {
		template_data.setDisplayNode(sprite.parent.getName(), sprite.getName());
		this.cpj_sprite = sprite;
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_file==null) {
			icon_file = Studio.getInstance().getIconManager().getIcon("0");
		}
		return super.getIcon(update);
	}
	
	@Override
	public ImageIcon createIcon() {
		getCPJSprite();
		if (cpj_sprite != null) {
			return Tools.createIcon(Tools.combianImage(20, 20, cpj_sprite.getIcon(true).getImage()));
		} else {
			return super.createIcon();
		}
	}

	public ObjectViewer<?> getEditComponent(){
		onOpenEdit();
		if (edit_component==null) {
			edit_component = new NPCObjectViewer();
		}
		return edit_component;
	}
	
	public void resetResource(CPJFile cpj) {
		if (template_data.getDisplayNode() != null && 
			template_data.getDisplayNode().cpj_project_name.equals(cpj.getName())) {
			cpj_sprite = null;
			getCPJSprite();
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	class NPCObjectViewer extends XLSObjectViewer<XLSUnit> implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JPanel	page_properties;
		JButton	set_binding		= new JButton("绑定资源");
//		JButton	set_avatar		= new JButton("设置AVATAR");
		
		public NPCObjectViewer() 
		{
			super(XLSUnit.this,
					new ObjectAdapters.UnitBattleTeamNodeAdapter(),
					new ObjectAdapters.ItemListIDSelectAdapter(),
					new ObjectAdapters.ShopItemListIDSelectAdapter()
			);
			if (cpj_sprite!=null) {
				set_binding.setIcon(cpj_sprite.getIcon(false));
				set_binding.setText("(" + cpj_sprite.name + ")" + "绑定资源");
			}
			page_properties = new JPanel();
//			page_properties.setLayout(new GridLayout(2, 1));
			{
				set_binding.addActionListener(this);
				page_properties.add(set_binding);
				
//				set_avatar.addActionListener(this);
//				page_properties.add(set_avatar);
			}
			table.insertTab("属性", null, page_properties, null, 0);
			table.setSelectedIndex(0);
		}
		

		public void actionPerformed(ActionEvent e)
		{
			if (set_binding==e.getSource()) {
				CPJSprite spr = new CPJResourceSelectDialog<CPJSprite>(this,
						CPJResourceType.ACTOR).showDialog();
				if (spr != null) {
					setCPJSprite(spr);
					XLSUnit.this.getIcon(true);
					set_binding.setIcon(cpj_sprite.getIcon(false));
					set_binding.setText("(" + cpj_sprite.name + ")" + "绑定资源");
					Studio.getInstance().getObjectManager().getPage(XLSUnit.class).repaint();
				}
			}
//			else if (set_avatar == e.getSource()) {
//				
//			}
		}
		
	}
	
		
	
	
	
	
	
	
	
	
	
	
}

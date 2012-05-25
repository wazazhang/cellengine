package com.g2d.studio.gameedit.display;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.rpg.display.Node;
import com.cell.rpg.template.TAvatar;
import com.g2d.cell.CellSprite;
import com.g2d.display.Sprite;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DAvatar;

public class DAvatarSprite extends Sprite
{
	private DAvatar davatar;
	private TAvatar tavatar;

	private CellSprite body_spr;
	private ArrayList<CellSprite> parts = new ArrayList<CellSprite>();
	
	public DAvatarSprite(DAvatar avt) 
	{
		this.davatar = avt;
		this.tavatar = avt.getData();
		
		{
			CPJIndex<CPJSprite> body_index = Studio
					.getInstance()
					.getCPJResourceManager()
					.getNode(CPJResourceType.ACTOR, 
							tavatar.body.cpj_project_name,
							tavatar.body.cpj_object_id);
			if (body_index != null) {
				CPJSprite body = Studio.getInstance().getCPJResourceManager()
						.getNode(body_index);
				body_spr = body.createCellSprite();
				addChild(body_spr);
			}	
		}
		for (Node sub : tavatar.body.sub_nodes) {
			CPJIndex<CPJSprite> sub_index = Studio
					.getInstance()
					.getCPJResourceManager()
					.getNode(CPJResourceType.AVATAR,
							sub.cpj_project_name,
							sub.cpj_object_id);
			if (sub_index != null) {
				CPJSprite subcpj = Studio.getInstance().getCPJResourceManager()
						.getNode(sub_index);
				CellSprite subspr = subcpj.createCellSprite();
				parts.add(subspr);
				addChild(subspr);
			}
		}
	}

	public void setAnimate(int anim, int frame) 
	{
		if (body_spr != null)
		{
			body_spr.cspr.setCurrentFrame(anim, frame);
			for (CellSprite cs : parts) {
				cs.cspr.setCurrentFrame(anim, frame);
			}
		}
	}
	
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(com.g2d.Graphics2D g) 
	{
		super.render(g);
		
		
	}
	




}

package com.g2d.studio.old.scene;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;

import com.cell.rpg.entity.Region;
import com.cell.rpg.entity.Unit;
import com.g2d.annotation.Property;
import com.g2d.game.rpg.MoveableUnit;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.Version;
import com.g2d.studio.old.swing.AbilityPanel;
import com.g2d.studio.old.swing.RPGUnitPanel;


@Property("一个范围，通常用于触发事件")
public class SceneRegion extends com.g2d.game.rpg.Unit implements SceneUnitTag<Region>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	@Property("color")
	public Color 	color = new Color(0x8000ff00, true);
	
	transient FormSceneViewer scene_view;
	
	final public Region region;

//	--------------------------------------------------------------------------------------------------------
	
	public SceneRegion(Rectangle rect, FormSceneViewer scene) 
	{
		this.scene_view = scene;
		this.region = new Region(getWidth(), getHeight());
		init();
		local_bounds	= rect;

		priority = -Integer.MAX_VALUE / 2;
	}
	
	public SceneRegion(Region in, FormSceneViewer scene) throws IOException
	{
		this.scene_view = scene;
		this.region = in;
		init();
		onRead(in) ;
	}
	
	protected void init()
	{
		enable				= true;
		enable_drag			= true;
		enable_input		= true;
		enable_drag_resize	= true;
		enable_focus 		= true;
		enable_input 		= true;

	}
	

//	--------------------------------------------------------------------------------------------------------
	
	public void onRead(Region region)
	{
		setID(scene_view.getViewObject().getScene().getWorld(), 
				region.name);
		setLocation(
				region.pos.x,
				region.pos.y);
		color = new Color(
				region.color, true);
		alpha = region.alpha;
		setSize(
				region.width, 
				region.height);
	}
	
	public Region onWrite()
	{
		region.name			= getID() + "";
		region.pos.x		= getX();
		region.pos.y		= getY();
		region.color		= color.getRGB();
		region.alpha		= alpha;
		region.width		= getWidth();
		region.height		= getHeight();
		return region;
	}
	
	@Override
	public void onReadComplete(ArrayList<Unit> all) {
		
	}
	@Override
	public void onWriteComplete(ArrayList<Unit> all) {
		
	}

//	--------------------------------------------------------------------------------------------------------

	@Override
	public Region getUnit() {
		return region;
	}
	
	@Override
	public com.g2d.game.rpg.Unit getSceneUnit() {
		return this;
	}
	@Override
	public FormSceneViewer getViewer() {
		return scene_view;
	}
	
	@Override
	public Color getSnapColor() {
		return null;
	}
	
	@Override
	public Shape getSnapShape() {
		return null;
	}

	@Override
	public Menu getEditMenu() {
		return new UnitMenu(scene_view, this);
	}
	
	

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);

		if (scene_view!=null)
		{
			if (scene_view.isSelectedRegionBox()) {
				g.setColor(color);
				g.fill(local_bounds);
				// 选择了该精灵
				if (scene_view.selected_unit == this) {
					g.setColor(Color.WHITE);
					g.draw(local_bounds);
				}
				// 当鼠标放到该精灵上
				else if (isCatchedMouse() && scene_view.tool_selector.isSelected()) {
					g.setColor(Color.GREEN);
					g.draw(local_bounds);
				}
				this.enable = scene_view.isBushSelect();
			} else {
				Composite composite = g.getComposite();
				setAlpha(g, alpha * 0.5f);
				g.setColor(color);
				g.fill(local_bounds);
				g.setComposite(composite);
				this.enable = false;
			}
		}
	
	}
	
	@Override
	public DisplayObjectEditor<?> createEditorForm() 
	{
		return new DisplayObjectEditor<SceneRegion>(
				this,
				new RPGUnitPanel(region),
				new AbilityPanel(this, region));
	}
	
	@Override
	public String toString() 
	{
		return getID()+"";
	}
	
	
}
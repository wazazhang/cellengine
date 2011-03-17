package com.g2d.studio.old.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;

import com.cell.rpg.entity.Actor;
import com.cell.rpg.entity.Unit;

import com.g2d.annotation.Property;
import com.g2d.cell.game.SceneSprite;
import com.g2d.display.ui.Menu;
import com.g2d.display.ui.Menu.MenuItem;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.Version;
import com.g2d.studio.old.Studio;
import com.g2d.studio.old.actor.FormActorViewer;
import com.g2d.studio.old.swing.AbilityPanel;
import com.g2d.studio.old.swing.RPGUnitPanel;
import com.g2d.util.Drawing;

/**
 * @author WAZA
 *
 */
@Property("一个可见的游戏对象")
public class SceneActor extends SceneSprite implements SceneUnitTag<Actor>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	transient FormActorViewer	actor_view;
	transient FormSceneViewer	scene_view;
	
	transient Rectangle snap_shape = new Rectangle(-2, -2, 4, 4);
	
	final public Actor actor;

//	--------------------------------------------------------------------------------------------------------
	
	public SceneActor(FormActorViewer actor, FormSceneViewer scene) 
	{
		this.init(actor, scene);
		this.actor = actor.createRPGActor();
	}

	public SceneActor(Actor in, FormSceneViewer scene)  throws IOException
	{
		actor = in;
		scene_view = scene;
		onRead(in);
		this.init(actor_view, scene);
		csprite.setCurrentFrame(cur_anim, 0);
	}
	
	protected void init(FormActorViewer actor, FormSceneViewer scene)
	{		
		this.enable			= true;
		this.enable_focus 	= true;
		this.enable_drag	= true;
		this.enable_input	= true;
		
		this.actor_view		= actor;
		this.scene_view		= scene;
		
		super.init(actor_view.getViewObject());

		priority = 0;
	}

//	--------------------------------------------------------------------------------------------------------
	
	public void onRead(Actor actor)
	{
		actor_view	= Studio.getInstance().getActor(
				actor.display_node.cpj_project_name,
				actor.display_node.cpj_object_id);
		setID(scene_view.getViewObject().getScene().getWorld(), 
				actor.name);
		setLocation(
				actor.pos.x,
				actor.pos.y);
		cur_anim	= actor.display_node.cur_anim;
		scale_x		= actor.display_node.scale_x;
		scale_y		= actor.display_node.scale_y;
	}
	
	public Actor onWrite()
	{
//		actor.display_node.cpj_project_name = actor_view.getCpjName();
//		actor.display_node.cpj_object_id = actor_view.getCpjObjectID();
		actor.name					= getID() + "";
		actor.pos.x					= getX();
		actor.pos.y					= getY();
		actor.pos.z					= priority;
		actor.display_node.scale_x		= (float)scale_x;
		actor.display_node.scale_y		= (float)scale_y;
		actor.display_node.cur_anim		= cur_anim;
		return actor;
	}

	@Override
	public void onReadComplete(ArrayList<Unit> all) {
		
	}
	@Override
	public void onWriteComplete(ArrayList<Unit> all) {
		
	}

//	--------------------------------------------------------------------------------------------------------

	@Override
	public Actor getUnit() {
		return actor;
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
		return Color.GREEN;
	}
	
	@Override
	public Shape getSnapShape() {
		return snap_shape;
	}
	
	@Override
	public Menu getEditMenu() {
		return new UnitMenu(scene_view, this);
	}

//	--------------------------------------------------------------------------------------------------------
	
	public void update() 
	{
//		alpha = Math.abs((float)Math.sin(timer/10d));
		
		super.update();
		
		if (csprite!=null) 
		{
			csprite.nextCycFrame();
		}
		
//		current selected unit
		if (scene_view.tool_selector.isSelected())
		{
			if (scene_view.selected_unit == this)
			{
				if (getRoot().isMouseWheelUP()) {
					getSprite().nextAnimate(-1);
				}
				if (getRoot().isMouseWheelDown()) {
					getSprite().nextAnimate(1);
				}
			}
		}
		
		
		
	}
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		if (scene_view != null) 
		{
			if (scene_view.isSelectedActorBox()) {
				// 选择了该精灵
				if (scene_view.selected_unit == this) {
					drawTouchRange(g);
					drawLookRange(g);
					g.setColor(Color.WHITE);
					g.draw(local_bounds);
					g.setColor(Color.WHITE);
					Drawing.drawStringBorder(g, 
							getSprite().getCurrentAnimate() + "/" + getSprite().getAnimateCount(),
							0, getSprite().getVisibleBotton() + 1,
							Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
				} // 当鼠标放到该精灵上
				else if (isCatchedMouse()) {
					drawTouchRange(g);
					if (scene_view.tool_selector.isSelected()) {
						g.setColor(Color.GREEN);
						g.draw(local_bounds);
					}
				}
				this.enable = scene_view.isBushSelect();
			} else {
				this.enable = false;
			}
		}
	}
	
	
	
	protected void drawTouchRange(Graphics2D g)
	{
		g.setColor(Color.GREEN);
		g.drawArc(
				-actor.touch_range, 
				-actor.touch_range, 
				actor.touch_range<<1, 
				actor.touch_range<<1,
				0, 360);
	}
	protected void drawLookRange(Graphics2D g) 
	{
		g.setColor(Color.YELLOW);
		g.drawArc(
				-actor.look_range, 
				-actor.look_range, 
				actor.look_range<<1, 
				actor.look_range<<1,
				0, 360);
		g.setColor(new Color((int)(Color.YELLOW.getRGB() & 0x7fffffff), true));
		g.fillArc(
				-actor.look_range, 
				-actor.look_range, 
				actor.look_range<<1, 
				actor.look_range<<1,
				0, 360);
	}
	
	@Override
	public DisplayObjectEditor<?> createEditorForm() {
		return new DisplayObjectEditor<SceneActor>(
				this,
				new RPGUnitPanel(actor), 
				new AbilityPanel(this, actor));
	}
	
	@Override
	public String toString() {
		return getID()+"";
	}

}

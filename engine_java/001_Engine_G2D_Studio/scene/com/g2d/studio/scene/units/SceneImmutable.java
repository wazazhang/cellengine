package com.g2d.studio.scene.units;


import java.awt.Component;
import java.io.InputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.gameedit.SetResource;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gfx.game.CSprite;
import com.cell.rpg.instance.zones.ability.InstanceZoneUnitKillAction;
import com.cell.rpg.instance.zones.ability.InstanceZoneUnitVisible;
import com.cell.rpg.scene.Immutable;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.ability.ActorTransport;
import com.g2d.BasicStroke;
import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Stroke;
import com.g2d.awt.util.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.game.SceneSprite;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.TextTip;
import com.g2d.display.Tip;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;
import com.g2d.studio.Studio;
import com.g2d.studio.Version;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneUnitMenu;
import com.g2d.studio.scene.editor.SceneUnitTagEditor;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.util.Drawing;

public class SceneImmutable extends SceneSprite implements SceneUnitTag<Immutable>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	final public SceneEditor	editor;
	final Immutable				sprite;
	final public CPJSprite		cpj_spr;
	
	Rectangle					snap_shape = new Rectangle(-2, -2, 4, 4);

	private SceneNode 			next_scene;
	private SceneUnit 			next_transport;

	TextTip tip = new TextTip();

//	--------------------------------------------------------------------------------------------------------
	
	/**
	 * 新建的单位
	 * @param editor
	 * @param xls_unit
	 * @param x
	 * @param y
	 */
	public SceneImmutable(SceneEditor editor, CPJSprite cpj_spr, int x, int y, int anim) 
	{
		this.editor = editor;
		this.cpj_spr = cpj_spr;
		this.cur_anim = anim;		
		this.setLocation(x, y);
		this.init(cpj_spr.parent.getSetResource(), cpj_spr.name);
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		this.sprite = new Immutable(getID()+"", cpj_spr.getCPJFile().getName(), cpj_spr.name);
	}
	
	/**
	 * 读入的单位
	 * @param editor
	 * @param actor
	 */
	public SceneImmutable(SceneEditor editor, Immutable spr) 
	{
		this.editor = editor;
		this.sprite = spr;
		{
			CPJIndex<CPJSprite> index = Studio.getInstance().getCPJResourceManager().getNode(
						CPJResourceType.ACTOR, 
						spr.getDisplayNode().cpj_project_name,
						spr.getDisplayNode().cpj_object_id);
//			index.getObject().createDisplayObject();

			this.cpj_spr = CPJSprite.class.cast(index.getObject());
			this.cur_anim = spr.animate;
			this.setID(
					editor.getGameScene().getWorld(), 
					sprite.id);
			this.setLocation(
					sprite.x,
					sprite.y);
			this.init(
					cpj_spr.getCPJFile().getSetResource(), 
					cpj_spr.name);
		}
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
	}

	public Immutable onWrite()
	{
		sprite.name				= getID() + "";
		sprite.animate			= cur_anim;
		sprite.x				= getX();
		sprite.y				= getY();
		sprite.z				= priority;
		return sprite;
	}

	@Override
	public void added(DisplayObjectContainer parent) {
		this.enable			= true;
		this.enable_focus 	= true;
		this.enable_drag	= true;
		this.enable_input	= true;
		super.added(parent);
		sprite.name 			= getID()+"";
	}
	
	@Override
	public void loaded(SetResource set, CSprite cspr, SpriteSet spr) {
		super.loaded(set, cspr, spr);
		this.getSprite().setCurrentAnimate(cur_anim);
	}

	protected boolean enable_click_focus() {
		return true;
	}
//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public void onReadComplete(Vector<SceneUnitTag<?>> all) {}
	@Override
	public void onWriteReady(Vector<SceneUnitTag<?>> all) {}
//	--------------------------------------------------------------------------------------------------------

	@Override
	public Immutable getUnit() {
		return sprite;
	}
	
	@Override
	public Unit getGameUnit() {
		return this;
	}
	
	@Override
	public Color getSnapColor() {
		return Color.GREEN;
	}
	
	@Override
	public Shape getSnapShape() {
		return snap_shape;
	}
	
//	@Override
//	public Menu getEditMenu() {
//		return new UnitMenu(scene_view, this);
//	}

//	--------------------------------------------------------------------------------------------------------
	
	
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
		if (editor.isToolSelect())
		{
			if (editor.getSelectedUnit() == this)
			{
				if (getRoot().isMouseWheelUP()) {
					getSprite().nextAnimate(-1);
				}
				if (getRoot().isMouseWheelDown()) {
					getSprite().nextAnimate(1);
				}
				cur_anim = getSprite().getCurrentAnimate();
			}
		}
		
	}

	BufferedImage img_script = Tools.wrap_g2d(Res.img_script);
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		if (editor != null) 
		{	
			if (getUnit().getBindedTriggers().getTriggerCount() > 0) {
				g.drawImage(img_script, -img_script.getWidth()/2, local_bounds.y);
			}
			g.pushStroke();
			try
			{
				g.setStroke(new BasicStroke(2));
				
				if (editor.getSelectedPage().isSelectedType(getClass())) 
				{
					ActorTransport tp = getUnit().getAbility(ActorTransport.class);
					if (tp != null) {
						if (next_scene == null || !next_scene.getID().equals(tp.next_scene_id)) {
							next_scene = Studio.getInstance().getSceneManager().getSceneNode(tp.next_scene_id);
							next_transport = null;
						}
						if (next_scene != null) {
							if (next_transport == null || !next_transport.getName().equals(tp.next_scene_object_id)) {
								for (SceneUnit su : next_scene.getData().scene_units) {
									if (su.name.equals(tp.next_scene_object_id)) {
										next_transport = su;
										break;
									}
								}
							}
							g.setColor(Color.WHITE);
							Drawing.drawStringBorder(g, 
									"传送到 : " + next_scene.getListName(), 
									0, local_bounds.y-40, 
									Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
							if (next_transport != null) {
								Drawing.drawStringBorder(g, 
										"" + next_transport.getName(), 
										0, local_bounds.y-20, 
										Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
							}
						}
						
						
					}
					
					// 选择了该精灵
					if (editor.getSelectedUnit() == this) {
						drawTouchRange(g);
						g.setColor(Color.WHITE);
						g.draw(local_bounds);
						g.setColor(Color.WHITE);
						Drawing.drawStringBorder(g, 
								getSprite().getCurrentAnimate() + "/" + getSprite().getAnimateCount(),
								0, getSprite().getVisibleBotton() + 1,
								Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
					} // 当鼠标放到该精灵上
					else if (isPickedMouse()) {
						drawTouchRange(g);
						if (editor.isToolSelect()) {
							g.setColor(Color.GREEN);
							g.draw(local_bounds);
						}
					}
					this.enable = editor.isToolSelect();
				} else {
					this.enable = false;
				}
			
			} finally {
				g.popStroke();
			}	
			Util.drawScript(g, editor, this);
		}
	}
	
	protected void drawTouchRange(Graphics2D g)
	{
		if (sprite!=null) {
			g.setColor(Color.GREEN);
			g.drawArc(
					-sprite.touch_range, 
					-sprite.touch_range, 
					sprite.touch_range<<1, 
					sprite.touch_range<<1,
					0, 360);
		}
	}
		
	@Override
	public String toString() {
		return getID()+"";
	}

	@Override
	public Tip getTip() {
		return Util.getTip(editor, this, tip);
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	@Override
	public DisplayObjectEditor<?> getEditorForm() {
		return new SceneUnitTagEditor(editor, this,
				new SceneAbilityAdapters.ActorPathStartAdapter(editor),
				new SceneAbilityAdapters.ActorTransportAdapter(editor)
				);
	}
	
	@Override
	public Menu getEditMenu() {
		return new SceneUnitMenu(editor, this);
	}
	
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return null;
	}
	@Override
	public String getListName() {
		return getID() + "";
	}
	@Override
	public void onHideFrom() {}

	public static java.awt.image.BufferedImage converImage(InputStream is) throws Exception
	{
		String text = "http://www.abc.com";
		java.awt.image.BufferedImage image = ImageIO.read(is);
		java.awt.Graphics2D g2d = image.createGraphics();
		try {
			g2d.setColor(java.awt.Color.WHITE);
			java.awt.geom.Rectangle2D rect = g2d.getFont().getStringBounds(text, g2d.getFontRenderContext());
			g2d.drawString(text, 
					(int)(image.getWidth()  -rect.getWidth()  -rect.getX()), 
					(int)(image.getHeight() -rect.getHeight() -rect.getY()));
		} finally {
			g2d.dispose();
		}
		return image;
	}
}

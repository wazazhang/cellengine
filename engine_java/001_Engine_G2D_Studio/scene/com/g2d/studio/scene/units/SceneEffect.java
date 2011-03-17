package com.g2d.studio.scene.units;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.rpg.instance.zones.ability.InstanceZoneUnitKillAction;
import com.cell.rpg.instance.zones.ability.InstanceZoneUnitVisible;
import com.cell.rpg.particle.ParticleAppearanceType.DisplayNodeImage;
import com.cell.rpg.particle.ParticleAppearanceType.DisplayNodeSprite;
import com.cell.rpg.scene.Effect;
import com.cell.rpg.scene.script.entity.SceneUnit;
import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.awt.util.Tools;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;
import com.g2d.display.TextTip;
import com.g2d.display.Tip;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleDisplay;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;
import com.g2d.studio.Studio;
import com.g2d.studio.Version;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.CPJEffectImageSelectDialog.TileImage;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.quest.QuestCellEditAdapter;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneUnitMenu;
import com.g2d.studio.scene.editor.SceneUnitTagEditor;
import com.g2d.util.Drawing;


@Property("一个在场景中渲染的特效")
public class SceneEffect extends com.g2d.game.rpg.Unit implements SceneUnitTag<Effect>
{
	private static final long serialVersionUID = Version.VersionGS;

	final SceneEditor		editor;
	final public Effect 	effect;
	Rectangle				snap_shape = new Rectangle(-1, -2, 2, 2);

	ParticleDisplay			particles;
	Sprite 					particle_layers = new Sprite();

	public int 				high;

	TextTip tip = new TextTip();

//	--------------------------------------------------------------------------------------------------------
	
	public SceneEffect(SceneEditor editor, int x, int y, int hight, DEffect deffect) 
	{
		this.editor 		= editor;
		this.setLocation(x, y);
		this.setLocalBounds(-16, -16, 32, 32);
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		this.effect = new Effect(getID() + "", deffect.getData().getIntID());
		this.high = hight;
	}
	
	public SceneEffect(SceneEditor editor, Effect in) throws IOException
	{
		this.editor = editor;
		this.effect = in;
		{
			this.setID(
					editor.getGameScene().getWorld(), 
					effect.name);
			this.setLocation(
					effect.x,
					effect.y);
			this.high = (int)effect.z;
			this.setLocalBounds(-16, -16, 32, 32);
		}
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
	}
	
	public Effect onWrite()
	{
		effect.name			= getID() + "";
		effect.x			= getX();
		effect.y			= getY();
		effect.z			= high;
		return effect;
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {
		this.enable			= true;
		this.enable_focus 	= true;
		this.enable_drag	= true;
		this.enable_input	= true;
		this.priority 		= Integer.MAX_VALUE / 2;
		super.added(parent);
		addChild(particle_layers);
		effect.name 			= getID()+"";
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
	public Effect getUnit() {
		return effect;
	}
	
	@Override
	public Unit getGameUnit() {
		return this;
	}

	@Override
	public Color getSnapColor() {
		return Color.YELLOW;
	}
	
	@Override
	public Shape getSnapShape() {
		return snap_shape;
	}

//	--------------------------------------------------------------------------------------------------------
	
	private ParticleDisplay resetParticles()
	{
		try{
			DEffect deffect = Studio.getInstance().getObjectManager().getObject(DEffect.class, effect.template_effect_id);
			for (Layer layer : deffect.getData().particles) {
				if (layer.appearance instanceof DisplayNodeImage) {
					DisplayNodeImage image = (DisplayNodeImage)layer.appearance;
					if (image.getImage() == null) {
						TileImage tile_image = new TileImage(
								image.cpj_project_name, 
								image.cpj_sprite_name, 
								image.cpj_image_id
								);
						image.setImage(Tools.wrap_g2d(tile_image.getEffectImage()));
					}
				}
				else if (layer.appearance instanceof DisplayNodeSprite) {
					DisplayNodeSprite sprite = (DisplayNodeSprite)layer.appearance;
					if (sprite.getSprite() == null) {
						CPJIndex<CPJSprite> index = Studio.getInstance().getCPJResourceManager().getNode(
								CPJResourceType.EFFECT, 
								sprite.cpj_project_name, 
								sprite.cpj_sprite_name);
						if (index != null) {
							CPJSprite spr = Studio.getInstance().getCPJResourceManager().getNode(index);
							if (spr != null) {
								sprite.setSprite(spr.createCSprite());
							}
						}
					}
				}
			}
			ParticleDisplay particles = new ParticleDisplay(deffect.getData().particles);
			particles.setLocation(0, -high);
			particle_layers.addChild(particles);
			return particles;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void update() 
	{
		super.update();

		if (particles == null) {
			particles = resetParticles();
		} else {
			particles.setLocation(0, -high);
		}
		
		if (editor.isToolSelect())
		{
			if (editor.getSelectedUnit() == this)
			{
				if (getRoot().isKeyHold(KeyEvent.VK_SHIFT)) {
					if (getRoot().isMouseWheelUP()) {
						high += 10;
					}
					if (getRoot().isMouseWheelDown()) {
						high -= 10;
					}
				} else {
					if (getRoot().isMouseWheelUP()) {
						high ++;
					}
					if (getRoot().isMouseWheelDown()) {
						high --;
					}
				}
			}
		}
	}
	
	BufferedImage img_script = Tools.wrap_g2d(Res.img_script);
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		if (editor!=null)
		{
			if (getUnit().getBindedTriggers().getTriggerCount() > 0) {
				g.drawImage(img_script, -img_script.getWidth()/2, 0);
			}
			if (editor.getSelectedPage().isSelectedType(getClass())) 
			{
				g.setColor(Color.YELLOW);
				g.draw(local_bounds);
				{
					int sx = 0, sy = 0;
					g.setColor(Color.GREEN);
					g.drawLine(sx, sy, sx, sy-high);
					
					sy -= high;
					g.drawLine(sx-16, sy-16, sx+16, sy+16);
					g.drawLine(sx+16, sy-16, sx-16, sy+16);
				}
				
				
				// 选择了该精灵
				if (editor.getSelectedUnit() == this) {
					g.setColor(Color.WHITE);
					g.draw(local_bounds);
					if (particles!=null) {
						try{
							g.translate(particles.x, particles.y);
							g.draw(particles.local_bounds);
						} finally {
							g.translate(-particles.x, -particles.y);
						}
					}
				}
				// 当鼠标放到该精灵上
				else if (isCatchedMouse() && editor.isToolSelect()) {
					g.setColor(Color.GREEN);
					g.draw(local_bounds);
					if (particles!=null) {
						try{
							g.translate(particles.x, particles.y);
							g.draw(particles.local_bounds);
						} finally {
							g.translate(-particles.x, -particles.y);
						}
					}
				}
				this.enable = editor.isToolSelect();
			} else {
				this.enable = false;
			}
			Util.drawScript(g, editor, this);
		}
	
	}
	
	@Override
	public String toString() 
	{
		return getID()+"";
	}
	
	@Override
	public Tip getTip() {
		return Util.getTip(editor, this, tip);
	}
	
	@Override
	public DisplayObjectEditor<?> getEditorForm() {
		return new SceneUnitTagEditor(editor, this,
				new SceneAbilityAdapters.RegionSpawnNPCNodeAdapter(),
				new SceneAbilityAdapters.RegionSpawnCollectionNodeAdapter(),
				new QuestCellEditAdapter.QuestTriggerAdapter());
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
	public void onHideFrom() {
		if (particles != null) {
			particle_layers.removeChild(particles);
		}
		particles = null;
	}

}
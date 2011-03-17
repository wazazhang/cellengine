package com.g2d.studio.old.scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import com.cell.math.MathVector;
import com.cell.math.Vector;
import com.cell.rpg.entity.Point;
import com.cell.rpg.entity.Region;
import com.cell.rpg.entity.Unit;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.ui.Menu;
import com.g2d.display.ui.Menu.MenuItem;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.Version;
import com.g2d.studio.old.swing.AbilityPanel;
import com.g2d.studio.old.swing.RPGUnitPanel;


@Property("一个点，通常用于路点")
public class ScenePoint extends com.g2d.game.rpg.Unit implements SceneUnitTag<Point>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	@Property("color")
	public Color 				color 	= new Color(0xffffff00, true);
	transient FormSceneViewer	scene_view;
	final public Point 			point;
	transient Rectangle 		snap_shape = new Rectangle(-1, -1, 2, 2);

	final HashSet<ScenePoint>	next_nodes = new HashSet<ScenePoint>();
	
//	--------------------------------------------------------------------------------------------------------
	
	public ScenePoint(int x, int y, FormSceneViewer scene) 
	{
		this.scene_view = scene;
		this.point = new Point(x, y);
		this.setLocation(x, y);
		init();
	}
	
	public ScenePoint(Point in, FormSceneViewer scene) throws IOException
	{
		this.scene_view = scene;
		this.point = in;
		init();
		onRead(in) ;
	}
	
	protected void init()
	{
		enable				= true;
		enable_drag			= true;
		enable_input		= true;
		enable_focus 		= true;
		enable_input 		= true;
		
		local_bounds.setBounds(-4, -4, 8, 8);
		priority = Integer.MAX_VALUE / 2;
	}
	
	@Override
	public void removed(DisplayObjectContainer parent) {
		super.removed(parent);
		try{
			synchronized(next_nodes) {
				for (ScenePoint p : scene_view.scene.getWorld().getChildsSubClass(ScenePoint.class)) {
					if (p.next_nodes.contains(this)) {
						p.next_nodes.remove(this);
					}
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public void onRead(Point p)
	{
		setID(scene_view.getViewObject().getScene().getWorld(), 
				p.name);
		setLocation(
				p.pos.x,
				p.pos.y);
		color = new Color(
				p.color, true);
		alpha = p.alpha;
	}
	
	@Override
	public Point onWrite()
	{
		point.name		= getID() + "";
		point.pos.x		= getX();
		point.pos.y		= getY();
		point.color		= color.getRGB();
		point.alpha		= alpha;
		return point;
	}
	
	@Override
	public void onReadComplete(ArrayList<Unit> all) {
		next_nodes.clear();
		if (point.next_ids!=null) {
			for (String next : point.next_ids) {
				try{
					Unit next_unit = findUnit(all, next);
					ScenePoint next_point = (ScenePoint)(scene_view.getTagUnit(next_unit).getSceneUnit());
					next_nodes.add(next_point);
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onWriteComplete(ArrayList<Unit> all) {
		point.next_ids = new ArrayList<String>(next_nodes.size());
		for (ScenePoint next : next_nodes) {
			try{
				point.next_ids.add(next.getUnit().name);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}

	private static Unit findUnit(ArrayList<Unit> all, String name) {
		for (Unit u : all) {
			if (u.name.equals(name)){
				return u;
			}
		}
		return null;
	}

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public Point getUnit() {
		return point;
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
		return Color.YELLOW;
	}
	
	@Override
	public Shape getSnapShape() {
		return snap_shape;
	}
	
	@Override
	public Menu getEditMenu() {
		return new UnitMenu(scene_view, this);
	}
	
	public Menu getLinkMenu(final ScenePoint next) {
		final ScenePoint unit = this;
		final String item_1 = "单向链接";
		final String item_2 = "反向链接";
		final String item_3 = "双向链接";
		final String itemd_1 = "单向解开";
		final String itemd_2 = "反向解开";
		final String itemd_3 = "双向解开";
		
		return new Menu(100, new String[]{
				item_1, item_2, item_3, itemd_1, itemd_2, itemd_3
		}){
			protected void onClickMenuItem(MenuItem item) {
				try{
					synchronized(unit.next_nodes) {
						synchronized(next.next_nodes) {
							if (item.getUserData().equals(item_1)) {
								unit.next_nodes.add(next);
							} else if (item.getUserData().equals(item_2)) {
								next.next_nodes.add(unit);
							} else if (item.getUserData().equals(item_3)) {
								unit.next_nodes.add(next);
								next.next_nodes.add(unit);
							} else if (item.getUserData().equals(itemd_1)) {
								unit.next_nodes.remove(next);
							} else if (item.getUserData().equals(itemd_2)) {
								next.next_nodes.remove(unit);
							} else if (item.getUserData().equals(itemd_3)) {
								unit.next_nodes.remove(next);
								next.next_nodes.remove(unit);
							}
						}
					}
				}catch(Exception err){}
			}
		};
	}
	
//	--------------------------------------------------------------------------------------------------------

	public void linkNext(ScenePoint next) {
		synchronized(next_nodes) {
			next_nodes.add(next);
		}
	}
	
	public void removeLink(ScenePoint next) {
		synchronized(next_nodes) {
			next_nodes.remove(next);
		}
	}
	
//	--------------------------------------------------------------------------------------------------------
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		if (scene_view!=null) 
		{
			if (scene_view.isSelectedPointBox()) {
				g.setColor(color);
				float talpha = 0.5f;
				if (scene_view.selected_unit == this) {
					g.setColor(Color.WHITE);
					talpha = 1f;
				}
				if (!next_nodes.isEmpty()) {
					synchronized(next_nodes) {
						pushObject(g.getComposite());
						pushObject(g.getStroke());
						setAlpha(g, talpha);
						g.setStroke(new BasicStroke(2));
						int[] tx3 = new int[]{-4,-20,-20};
						int[] ty3 = new int[]{ 0, -5,  5};
						for (ScenePoint next : next_nodes) {
							double nx = next.x - x;
							double ny = next.y - y;
							double angle = MathVector.getDegree(nx, ny);
							g.drawLine(0, 0, (int)nx, (int)ny);
							g.translate(nx, ny);
							g.rotate(angle);
							g.fillPolygon(tx3, ty3, 3);
							g.rotate(-angle);
							g.translate(-nx, -ny);
						}
						g.setStroke(popObject(Stroke.class));
						g.setComposite(popObject(Composite.class));
					}
				}
				g.fill(local_bounds);
				// 选择了该精灵
				if (scene_view.selected_unit == this) {
					g.setColor(Color.BLUE);
					g.draw(local_bounds);
				} 
				// 当鼠标放到该精灵上
				else if (isCatchedMouse() && scene_view.tool_selector.isSelected()) {
					g.setColor(Color.RED);
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
		return new DisplayObjectEditor<ScenePoint>(
				this,
				new RPGUnitPanel(point),
				new AbilityPanel(this, point));
	}
	
	@Override
	public String toString() 
	{
		return getID()+"";
	}
	
	
}
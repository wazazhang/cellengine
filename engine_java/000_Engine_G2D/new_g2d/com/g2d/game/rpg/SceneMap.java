package com.g2d.game.rpg;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.cell.CMath;
import com.cell.game.ai.pathfind.AstarManhattan;
import com.cell.game.ai.pathfind.AstarManhattanMap;
import com.cell.game.ai.pathfind.AstarManhattan.WayPoint;
import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Font;
import com.g2d.Graphics2D;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.geom.Line;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;
import com.g2d.util.Drawing;

public abstract class SceneMap extends DisplayObjectContainer implements AstarManhattanMap
{
	final public Scene		owner_scene;
	
	final protected int 	gridW, gridH;
	final protected int 	world_grid_x_size;
	final protected int 	world_grid_y_size;
	final protected int[][] grid_matrix;

	final TerrainViewer		grid_viewer;
	
//	---------------------------------------------------------------------------------------------------------------------------

	protected AstarManhattan	astar_path_finder;
	
	public boolean				runtime_sort		= true;
	
	
	private Map<Object, Unit>	units_name		= new HashMap<Object, Unit>(100);
	private AtomicInteger		units_index		= new AtomicInteger(1);
	
	
	
//	---------------------------------------------------------------------------------------------------------------------------

	public SceneMap(Scene scene, 
			int grid_w, 
			int grid_h, 
			int grid_x_count,
			int grid_y_count,
			int matrix[][]) 
	{
		owner_scene			= scene;

		local_bounds.x		= 0;
		local_bounds.y		= 0;
		local_bounds.width	= grid_w * grid_x_count;
		local_bounds.height	= grid_h * grid_y_count;
		gridW				= grid_w;
		gridH				= grid_h;
		world_grid_x_size	= grid_x_count;
		world_grid_y_size	= grid_y_count;
		grid_matrix			= new int[grid_x_count][grid_y_count];

		setSorter(new SceneUnitSorter());
		
		for (int x = grid_x_count - 1; x >= 0; --x) {
			for (int y = grid_y_count - 1; y >= 0; --y) {
				this.grid_matrix[x][y] = matrix[x][y];
			}
		}
		
		grid_viewer 		= new TerrainViewer();
		
		astar_path_finder	= new AstarManhattan(this, true, false);
	}

	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}
	
	@Override
	synchronized public boolean addChild(DisplayObject child) {
		if ((child instanceof Unit)) {
			Unit unit = (Unit)child;
			if (unit.getID() == null) {
				for (int i = Integer.MAX_VALUE; i > Integer.MIN_VALUE; --i) {
					String id = unit.getClass().getSimpleName() + "_" +
							units_index.getAndIncrement();
//					System.out.println("crate default id = " + id);
					if (unit.setID(this, id)) {
						break;
					}
				}
			}
			if (!units_name.containsKey(unit.getID())) {
				if (super.addChild(child)) {
					units_name.put(unit.getID(), unit);
					unit.setOwnerScene(owner_scene);
					unit.setOwnerWorld(this);
					return true;
				}
			}
			return false;
		}
		return super.addChild(child);
	}
	
	@Override
	synchronized public boolean removeChild(DisplayObject child) {
		if ((child instanceof Unit)) {
			Unit unit = (Unit)child;
			if (super.removeChild(child)) {
				units_name.remove(unit.getID());
				unit.setOwnerScene(null);
				unit.setOwnerWorld(null);
				return true;
			}
			return false;
		}
		return super.removeChild(child);
	}
	
	synchronized public boolean containsUnitWithID(Object id) {
		return units_name.containsKey(id);
	}
	
	synchronized public Unit getUnitWithID(Object id) {
		return units_name.get(id);
	}
	
	synchronized boolean tryChangeUnitID(Unit unit, Object new_id) {
		if (new_id == null || containsUnitWithID(new_id)) {
			return false;
		} else if(contains(unit)) {
			units_name.remove(unit.getID());
			units_name.put(new_id, unit);
			return true;
		}
		return false;
	}
	
//	---------------------------------------------------------------------------------------------------------------------------

	final public AstarManhattan.WayPoint findPath(int sx, int sy, int dx, int dy) {
		return astar_path_finder.findPath(sx, sy, dx, dy);
	}
	

	@Override
	public void setDebug(boolean d) {
		showDebugGrid(d);
	}

	public void showDebugGrid(boolean show) {
		if (show) {
			if (!contains(grid_viewer)) {
				this.addChild(grid_viewer);
			}
		} else {
			if (contains(grid_viewer)) {
				this.removeChild(grid_viewer);
			}
		}
	}

//		---------------------------------------------------------------------------------------------------------------------------

	final public int localToGridX(int x) {
		x = CMath.cycMod(x, gridW);
		x = Math.min(x, world_grid_x_size - 1);
		x = Math.max(x, 0);
		return x;
	}

	final public int localToGridY(int y) {
		y = CMath.cycMod(y, gridH);
		y = Math.min(y, world_grid_y_size - 1);
		y = Math.max(y, 0);
		return y;
	}

	final public int gridXToLocal(int x) {
		x *= gridW;
		x += gridW / 2;
		return x;
	}

	final public int gridYToLocal(int y) {
		y *= gridH;
		y += gridH / 2;
		return y;
	}

	final public int getCellH() {
		return gridW;
	}

	final public int getCellW() {
		return gridH;
	}

	final public int getXCount() {
		return world_grid_x_size;
	}

	final public int getYCount() {
		return world_grid_y_size;
	}
		
//	---------------------------------------------------------------------------------------------------------------------------

	public boolean getFlag(int bx, int by) {
		return testFlagBlock(grid_matrix[bx][by]);
	}

	public int getFlagValue(int bx, int by) {
		return grid_matrix[bx][by];
	}
	
	/**
	 * 指定的格子FLAG值是否可以通过
	 * @param flag_value
	 * @return true=不能通过， false=可以通过
	 */
	protected boolean testFlagBlock(int flag_value)  {
		return flag_value != 0;
	}

//	---------------------------------------------------------------------------------------------------------------------------

	public boolean touchMap(Shape shape) 
	{
		Rectangle srect = shape.getBounds();
		int sx = localToGridX(srect.x);
		int sy = localToGridY(srect.y);
		int dx = localToGridX(srect.x + srect.width);
		int dy = localToGridY(srect.y + srect.height);
		for (int y = sy; y <= dy; ++y) {
			for (int x = sx; x <= dx; ++x) {
				if (testFlagBlock(getFlagValue(x, y))) {
					Rectangle rect = new Rectangle(x * gridW, y * gridH, gridW,
							gridH);
					if (shape instanceof Rectangle) {
						if (rect.intersects((Rectangle) shape)) {
							return true;
						}
					} else if (shape instanceof Line) {
						if (rect.intersectsLine((Line) shape)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
		
	public WayPoint optimizePath(WayPoint root)
	{
		if (root != null) {
			WayPoint current = root;
			WayPoint prev = root.Next;
			WayPoint next = root.Next;
			while (next != null) {
				Line line = new Line(current.X, current.Y, next.X, next.Y);
				if (!touchMap(line)) {
					current.Next = next;
				} else {
					current = prev;
				}
				prev = next;
				next = next.Next;
			}
		}

		return root;
	}
		

//		---------------------------------------------------------------------------------------------------------------------------

	public void added(DisplayObjectContainer parent) {}
	public void removed(DisplayObjectContainer parent) {}
	public void update() {}
	public void render(Graphics2D g) {}

	@Override
	protected void renderAfter(Graphics2D g) {
		super.renderAfter(g);
		if (runtime_sort) {
			super.sort();
		}
	}
	
	abstract public BufferedImage createMiniMap(double width, double height);

	abstract public BufferedImage createScreenshot(int x, int y, double width, double height);
//	---------------------------------------------------------------------------------------------------------------------------

	public class TerrainViewer extends DisplayObjectContainer
	{
		public TerrainViewer() {
			priority = 10000;
		}
		
		@Override
		protected boolean testCatchMouse(Graphics2D g) {
			return false;
		}
		
		public void added(DisplayObjectContainer parent) {}
		public void removed(DisplayObjectContainer parent) {}
		public void update() {}	
		
		public void render(Graphics2D g) 
		{
			if (owner_scene == null) {
				return;
			}
			Font src_font 	= g.getFont();
			try
			{
				Color font_color = Color.WHITE;
				Font font = src_font.newSize(10);
				g.setFont(font);
				int sx = CMath.cycMod((int)owner_scene.getCameraX(), gridW);
				int sy = CMath.cycMod((int)owner_scene.getCameraY(), gridH);
				int dx = sx + owner_scene.getCameraWidth() / gridW + 1;
				int dy = sy + owner_scene.getCameraHeight()/ gridH + 1;
				for (int y = sy; y < dy; y++) {
					for (int x = sx; x < dx; x++) {
						if (CMath.includeRectPoint2(0, 0, world_grid_x_size, world_grid_y_size, x, y)) {
							if (grid_matrix[x][y] != 0){
								int rx = x * gridW;
								int ry = y * gridH;
								g.setColor(new Color(0xff000000 | grid_matrix[x][y]));
								g.drawRect(rx, ry, gridW, gridH);
								g.setColor(font_color);
								Drawing.drawString(g, x + "," + y, rx + 2, ry + 2);
							}
						}
					}
				}
			}
			finally
			{
				g.setFont(src_font);
			}
		}
	}

	public static class SceneUnitSorter implements Comparator<DisplayObject> {
		@Override
		public int compare(DisplayObject o1, DisplayObject o2) {
			return (int) ((o1.y + o1.priority) - (o2.y + o2.priority));
		}
	}
	
}

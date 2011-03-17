package com.g2d.game.rpg;

import java.awt.Color;
import java.awt.geom.Line2D;

import com.cell.CMath;
import com.cell.game.ai.pathfind.AstarManhattan;
import com.cell.game.ai.pathfind.AstarManhattan.WayPoint;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayShape;


public abstract class MoveableUnit extends Unit
{
	private static final long serialVersionUID = Version.VersionG2D;

//	------------------------------------------------------------------------------------------------------------------------------------
//	scene
	
	/** 移动速度 距离/秒 */
	@Property("移动速度 距离/秒")
	public double		move_speed 		= 4;
	
	@Property("是否被阻挡")
	public boolean		move_blockade 			= false;

	
//	------------------------------------------------------------------------------------------------------------------------------------

	@Property("移动目标")
	protected double 	move_target_x = 0, 
						move_target_y = 0;

	transient WayPoint 	path;
	
//	------------------------------------------------------------------------------------------------------------------------------------

	public double getMoveTargetX()
	{
		return move_target_x;
	}
	
	public double getMoveTargetY() 
	{
		return move_target_y;
	}
	
	public void stopMove()
	{
		move_target_x = x;
		move_target_y = y;
		path = null;
	}
	
	public void beginMoveTarget(double targetX, double targetY)
	{
		move_target_x = targetX;
		move_target_y = targetY;
	}

	public AstarManhattan.WayPoint beginMove(double targetX, double targetY) 
	{
		move_target_x = targetX;
		move_target_y = targetY;
		
		int sx = getOwnerWorld().localToGridX((int)x);
		int sy = getOwnerWorld().localToGridY((int)y);
		int dx = getOwnerWorld().localToGridX((int)targetX);
		int dy = getOwnerWorld().localToGridY((int)targetY);
		
		WayPoint end = new WayPoint((int)targetX, (int)targetY);
		
		if (getOwnerWorld().getFlag(dx, dy)!=false) {
			end = null;
			int rx = CMath.getDirect(dx - sx);
			int ry = CMath.getDirect(dy - sy);
			int r = Math.max(Math.abs(dx - sx), Math.abs(dy - sy));
//			System.out.println("redirect count = " + r);
			for (int i=0; i<r; i++) {
				if (dx != sx) dx -= rx;
				if (dy != sy) dy -= ry;
				if (getOwnerWorld().getFlag(dx, dy)==false) {
					break;
				}
			}
		}

//		System.out.println("find path : " + sx + "," + sy + " -> " + dx + "," + dy);
		
		if (debug) {
			while (path != null) {
				if (path.Data != null) {
					getOwnerWorld().grid_viewer.removeChild((DisplayObject) path.Data);
				}
				path = path.Next;
			}
		}
		
		path = new WayPoint((int)x, (int)y);
		path.Next = getOwnerWorld().findPath(sx, sy, dx, dy).Next;

		{
			// 让 end 点成为最后一点
			WayPoint rp = path;
			while (rp!=null) {
				if (rp.Next == null) {
					rp.Next = end;
					break;
				}
				rp = rp.Next;
			}
			
			// 优化路径
			path = getOwnerWorld().optimizePath(path);
			
			move_target_x = path.X;
			move_target_y = path.Y;
			
			if (debug)
			{
				WayPoint p = path;
				Color pcolor = Color.BLUE;
				while (p!=null) {
					if (p.Next!=null) {
						DisplayShape ds = new DisplayShape(
								new Line2D.Double(p.X, p.Y, p.Next.X, p.Next.Y),
								pcolor);
//						ds.enable_fill = true;
						p.Data = ds;
						getOwnerWorld().grid_viewer.addChild(ds);
//						System.out.println(ds.shape);
					}
					p = p.Next;
				}
			}
		}
		
		return path;
	}
	
	
	public void setLocation(double x, double y) 
	{
		super.setLocation(x, y);
		move_target_x = x;
		move_target_y = y;
	}
	
	public void setLocation(int x, int y) 
	{
		super.setLocation(x, y);
		move_target_x = x;
		move_target_y = y;
	}
	
	public void move(double dx, double dy) 
	{
		super.move(dx, dy);
		if (move_blockade) {
			int wx = getOwnerWorld().localToGridX((int)x);
			int wy = getOwnerWorld().localToGridY((int)y);
			if (getOwnerWorld().getFlag(wx, wy)!=false) {
				super.move(-dx, -dy);
				move_target_x = x;
				move_target_y = y;
			}
		}
	}
	
	
	public void update() 
	{
		super.update();
		
		
		if (move_target_x!=x || move_target_y!=y || path!=null)
		{
			double distance = CMath.getDistance(move_speed, getIntervalMS());
			
			if (moveTo(move_target_x, move_target_y, distance))
			{
				if (path!=null) 
				{
					if (debug){
						if (path.Data!=null){
							getOwnerWorld().grid_viewer.removeChild((DisplayObject)path.Data);
						}
					}
					if (path.Next!=null) {
						move_target_x = path.Next.X;
						move_target_y = path.Next.Y;
					}
					path = path.Next;
					
					if (path == null)
					{
						this.onMoveStopped();
					}
				}
			}
		}
	}
	
	
	public abstract void onMoveStopped();
	
}; // class



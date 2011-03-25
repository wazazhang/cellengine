package com.g2d.game.rpg;

import com.cell.CMath;
import com.cell.game.ai.pathfind.AstarManhattan;
import com.cell.game.ai.pathfind.AstarManhattan.WayPoint;
import com.cell.math.MathVector;
import com.cell.math.TVector;

import com.g2d.Color;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayShape;
import com.g2d.geom.Line;
import com.g2d.geom.Point;


public abstract class MoveableUnit extends Unit
{
//	------------------------------------------------------------------------------------------------------------------------------------
//	scene
	
	/** 移动速度 距离/秒 */
	public double		move_speed 		= 4;
	
	public boolean		move_blockade 			= false;

//	------------------------------------------------------------------------------------------------------------------------------------

	protected double 	move_target_x = 0, 
						move_target_y = 0;

	protected WayPoint 	path;
	
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

	public WayPoint beginMoveDirect(double targetX, double targetY)
	{
		int sx = getOwnerWorld().localToGridX((int)x);
		int sy = getOwnerWorld().localToGridY((int)y);
		int dx = getOwnerWorld().localToGridX((int)targetX);
		int dy = getOwnerWorld().localToGridY((int)targetY);
		
		if (getOwnerWorld().getFlag(sx, sy)) {
			if (getOwnerWorld().getFlag(dx, dy)) {
				return null;
			} else {
				path = new WayPoint(dx, dy, getOwnerWorld().getCellW(), getOwnerWorld().getCellH());
				move_target_x = path.X;
				move_target_y = path.Y;
				return path;
			}
		}
		else 
		{
			double  delta  = Math.min(getOwnerWorld().getCellW(), getOwnerWorld().getCellH());
			TVector vector = new TVector(x, y);
			while (true) {
				boolean arrive = MathVector.moveTo(vector, targetX, targetY, delta);
				int cx = getOwnerWorld().localToGridX((int)(vector.x));
				int cy = getOwnerWorld().localToGridY((int)(vector.y));
				if (getOwnerWorld().getFlag(cx, cy)) {
					MathVector.moveTo(vector, x, y, delta);
					break;
				}
				if (arrive) {
					break;
				}
			}
			
			path = new WayPoint((int)vector.getVectorX(), (int)vector.getVectorY());
			move_target_x = path.X;
			move_target_y = path.Y;
			return path;
		}
	}
	
	public AstarManhattan.WayPoint beginMove(double targetX, double targetY) 
	{
//		System.out.println("find path : " + sx + "," + sy + " -> " + dx + "," + dy);
		if (debug) {
			while (path != null) {
				if (path.Data != null) {
					getOwnerWorld().grid_viewer.removeChild((DisplayObject) path.Data);
				}
				path = path.Next;
			}
		}

		TVector target_point = findTheNearMoveableCell(targetX, targetY);
		if (target_point != null) {
			targetX = target_point.x;
			targetY = target_point.y;
		}
		
		int sx = getOwnerWorld().localToGridX((int)x);
		int sy = getOwnerWorld().localToGridY((int)y);
		int dx = getOwnerWorld().localToGridX((int)targetX);
		int dy = getOwnerWorld().localToGridY((int)targetY);
		
		if (sx == dx && sy == dy) 
		{
			move_target_x = targetX;
			move_target_y = targetY;
			return new WayPoint((int)targetX, (int)targetY);
		}
		else if (getOwnerWorld().getFlag(dx, dy))
		{
			return beginMoveDirect(targetX, targetY);
		}
		else
		{
			WayPoint find	= getOwnerWorld().findPath(sx, sy, dx, dy).Next;
			
			if (find == null)
			{
				return beginMoveDirect(targetX, targetY);
			}
			else
			{
				WayPoint end	= new WayPoint((int)targetX, (int)targetY);
				
				path = new WayPoint((int)x, (int)y);
				path.Next = find;

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
									new Line(p.X, p.Y, p.Next.X, p.Next.Y),
									pcolor);
//							ds.enable_fill = true;
							p.Data = ds;
							getOwnerWorld().grid_viewer.addChild(ds);
//							System.out.println(ds.shape);
						}
						p = p.Next;
					}
				}
				return path;
			} 
		}
	}
	

	/**
	 * 找到离目标点最近的一个空格
	 * @param targetX
	 * @param targetY
	 * @return 返回空表示目标点不是阻挡或无法移动
	 */
	public TVector findTheNearMoveableCell(double targetX, double targetY)
	{
		int dx = getOwnerWorld().localToGridX((int)targetX);
		int dy = getOwnerWorld().localToGridY((int)targetY);
		
		if (getOwnerWorld().getFlag(dx, dy)) 
		{
			double delta = Math.min(getOwnerWorld().getCellW(), getOwnerWorld().getCellH());
			TVector vector = new TVector(targetX, targetY);
			while (true) {
				boolean arrive = MathVector.moveTo(vector, x, y, delta);
				int cx = getOwnerWorld().localToGridX((int)(vector.x));
				int cy = getOwnerWorld().localToGridY((int)(vector.y));
				if (!getOwnerWorld().getFlag(cx, cy)) {
					return vector;
				}
				if (arrive) {
					break;
				}
			}
		}
		return null;
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



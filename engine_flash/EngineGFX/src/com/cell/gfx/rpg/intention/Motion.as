package com.cell.gfx.rpg.intention
{
	import com.cell.gfx.rpg.G2DActionUnit;
	import com.cell.gfx.rpg.G2DUnit;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	import com.cell.math.TVector2D;
	
	import flash.utils.getTimer;

	public class Motion implements Action
	{
		/** 移动速度 距离/秒 */
		public var move_speed	: Number = 200;
		
		public var target_pos	: IVector2D;
		
		protected var update_time	: int = 0;
		
		
		
		public function Motion(targetX:Number, targetY:Number, speed:Number = 200) 
		{
			this.target_pos = new TVector2D(targetX, targetY);
			this.move_speed = speed;
		}
		
		
		public function onUpdate(unit:G2DActionUnit) : void 
		{
			var cur_time : int = getTimer();
			var interval : int = cur_time - update_time;
			update_time = cur_time;
			if (target_pos != null)
			{
//				interval = 50;
				var distance : Number = MathVector.getDistanceSpeed(move_speed, interval);
//				trace("interval:"+interval + "  distance:"+distance);
				if (MathVector.moveTo(unit, 
					target_pos.getVectorX(), 
					target_pos.getVectorY(), 
					distance))
				{
					target_pos = null;
				}
			}
		}
		
		public function onStart(unit:G2DActionUnit) : void  {
			this.update_time = getTimer();
		}
		
		public function onStop(unit:G2DActionUnit) : void {
			this.target_pos = null;
		}

		public function isEnd() : Boolean {
			return this.target_pos == null;
		}
		
		public function stop() : void{
			this.target_pos = null;
		}
		
		
//		public WayPoint beginMoveDirect(double targetX, double targetY)
//		{
//			int sx = getOwnerWorld().localToGridX((int)x);
//			int sy = getOwnerWorld().localToGridY((int)y);
//			int dx = getOwnerWorld().localToGridX((int)targetX);
//			int dy = getOwnerWorld().localToGridY((int)targetY);
//			
//			if (getOwnerWorld().getFlag(sx, sy)) {
//				if (getOwnerWorld().getFlag(dx, dy)) {
//					return null;
//				} else {
//					path = new WayPoint(dx, dy, getOwnerWorld().getCellW(), getOwnerWorld().getCellH());
//					move_target_x = path.X;
//					move_target_y = path.Y;
//					return path;
//				}
//			}
//			else 
//			{
//				double  delta  = Math.min(getOwnerWorld().getCellW(), getOwnerWorld().getCellH());
//				TVector vector = new TVector(x, y);
//				while (true) {
//					boolean arrive = MathVector.moveTo(vector, targetX, targetY, delta);
//					int cx = getOwnerWorld().localToGridX((int)(vector.x));
//					int cy = getOwnerWorld().localToGridY((int)(vector.y));
//					if (getOwnerWorld().getFlag(cx, cy)) {
//						MathVector.moveTo(vector, x, y, delta);
//						break;
//					}
//					if (arrive) {
//						break;
//					}
//				}
//				
//				path = new WayPoint((int)vector.getVectorX(), (int)vector.getVectorY());
//				move_target_x = path.X;
//				move_target_y = path.Y;
//				return path;
//			}
//		}
//		
//		public AstarManhattan.WayPoint beginMove(double targetX, double targetY) 
//		{
//			//		System.out.println("find path : " + sx + "," + sy + " -> " + dx + "," + dy);
//			if (debug) {
//				while (path != null) {
//					if (path.Data != null) {
//						getOwnerWorld().grid_viewer.removeChild((DisplayObject) path.Data);
//					}
//					path = path.Next;
//				}
//			}
//			
//			TVector target_point = findTheNearMoveableCell(targetX, targetY);
//			if (target_point != null) {
//				targetX = target_point.x;
//				targetY = target_point.y;
//			}
//			
//			int sx = getOwnerWorld().localToGridX((int)x);
//			int sy = getOwnerWorld().localToGridY((int)y);
//			int dx = getOwnerWorld().localToGridX((int)targetX);
//			int dy = getOwnerWorld().localToGridY((int)targetY);
//			
//			if (sx == dx && sy == dy) 
//			{
//				move_target_x = targetX;
//				move_target_y = targetY;
//				return new WayPoint((int)targetX, (int)targetY);
//			}
//			else if (getOwnerWorld().getFlag(dx, dy))
//			{
//				return beginMoveDirect(targetX, targetY);
//			}
//			else
//			{
//				WayPoint find	= getOwnerWorld().findPath(sx, sy, dx, dy).Next;
//				
//				if (find == null)
//				{
//					return beginMoveDirect(targetX, targetY);
//				}
//				else
//				{
//					WayPoint end	= new WayPoint((int)targetX, (int)targetY);
//					
//					path = new WayPoint((int)x, (int)y);
//					path.Next = find;
//					
//					// 让 end 点成为最后一点
//					WayPoint rp = path;
//					while (rp!=null) {
//						if (rp.Next == null) {
//							rp.Next = end;
//							break;
//						}
//						rp = rp.Next;
//					}
//					
//					// 优化路径
//					path = getOwnerWorld().optimizePath(path);
//					
//					move_target_x = path.X;
//					move_target_y = path.Y;
//					
//					if (debug)
//					{
//						WayPoint p = path;
//						Color pcolor = Color.BLUE;
//						while (p!=null) {
//							if (p.Next!=null) {
//								DisplayShape ds = new DisplayShape(
//									new Line(p.X, p.Y, p.Next.X, p.Next.Y),
//									pcolor);
//								//							ds.enable_fill = true;
//								p.Data = ds;
//								getOwnerWorld().grid_viewer.addChild(ds);
//								//							System.out.println(ds.shape);
//							}
//							p = p.Next;
//						}
//					}
//					return path;
//				} 
//			}
//		}
//		
//		
//		/**
//		 * 找到离目标点最近的一个空格
//		 * @param targetX
//		 * @param targetY
//		 * @return 返回空表示目标点不是阻挡或无法移动
//		 */
//		public TVector findTheNearMoveableCell(targetX:Number, targetY:Number)
//		{
//			int dx = getOwnerWorld().localToGridX((int)targetX);
//			int dy = getOwnerWorld().localToGridY((int)targetY);
//			
//			if (getOwnerWorld().getFlag(dx, dy)) 
//			{
//				double delta = Math.min(getOwnerWorld().getCellW(), getOwnerWorld().getCellH());
//				TVector vector = new TVector(targetX, targetY);
//				while (true) {
//					boolean arrive = MathVector.moveTo(vector, x, y, delta);
//					int cx = getOwnerWorld().localToGridX((int)(vector.x));
//					int cy = getOwnerWorld().localToGridY((int)(vector.y));
//					if (!getOwnerWorld().getFlag(cx, cy)) {
//						return vector;
//					}
//					if (arrive) {
//						break;
//					}
//				}
//			}
//			return null;
//		}
//		
//		
//		
//		public void setLocation(double x, double y) 
//		{
//			super.setLocation(x, y);
//			move_target_x = x;
//			move_target_y = y;
//		}
//		
//		public void setLocation(int x, int y) 
//		{
//			super.setLocation(x, y);
//			move_target_x = x;
//			move_target_y = y;
//		}
//		
//		public void move(double dx, double dy) 
//		{
//			super.move(dx, dy);
//			if (move_blockade) {
//				int wx = getOwnerWorld().localToGridX((int)x);
//				int wy = getOwnerWorld().localToGridY((int)y);
//				if (getOwnerWorld().getFlag(wx, wy)!=false) {
//					super.move(-dx, -dy);
//					move_target_x = x;
//					move_target_y = y;
//				}
//			}
//		}
//		
		
		

	}
}
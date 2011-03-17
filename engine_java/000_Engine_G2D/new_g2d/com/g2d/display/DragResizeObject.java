package com.g2d.display;


import com.cell.CMath;
import com.g2d.AnimateCursor;
import com.g2d.BasicStroke;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Stroke;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.geom.Dimension;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;

public class DragResizeObject 
{

	final public static byte	DRAG_DIRECT_NORTH_WEST    = 0 ;// : North West<br>                     
	final public static byte	DRAG_DIRECT_NORTH         = 1 ;// : North <br>                         
	final public static byte	DRAG_DIRECT_NORTH_EAST    = 2 ;// : North East<br>                     
	final public static byte	DRAG_DIRECT_WEST          = 3 ;// : West<br>                           
	final public static byte	DRAG_DIRECT_CENTER        = 4 ;// : Center<br>                         
	final public static byte	DRAG_DIRECT_EAST          = 5 ;// : East<br>                           
	final public static byte	DRAG_DIRECT_SOUTH_WEST    = 6 ;// : South West<br>                     
	final public static byte	DRAG_DIRECT_SOUTH         = 7 ;// : South<br>                          
	final public static byte	DRAG_DIRECT_SOUTH_EAST    = 8 ;// : South East<br>                     

	final public MouseMoveEvent	event;
	final public int 			border_size;
	final public Dimension		minimum_size;
	
	final public Rectangle		start_drag_bounds;
	final public byte			start_drag_direct;
	 
	public DragResizeObject(
			MouseMoveEvent	event,
			Dimension		minimum_size,
			int 			bsize,
			Rectangle		start_bounds, 
			int 			start_mouse_x, 
			int 			start_mouse_y) 
	{
		this.event				= event;
		this.minimum_size		= minimum_size;
		this.border_size 		= Math.max(bsize, 1);
		this.start_drag_bounds	= start_bounds.getBounds();
		this.start_drag_direct	= getDragDirect(
				start_drag_bounds,
				Math.max(border_size, border_size), 
				start_mouse_x,
				start_mouse_y);
	}
	
	public void update(DisplayObject display)
	{
		if (start_drag_direct != DragResizeObject.DRAG_DIRECT_CENTER) 
		{
			Rectangle dstart_rect = start_drag_bounds;
			
			int sx = event.mouseDownStartX;
			int sy = event.mouseDownStartY;
			int dx = display.getMouseX() - sx;
			int dy = display.getMouseY() - sy;
			
			int rx = dstart_rect.x;
			int ry = dstart_rect.y;
			int rw = dstart_rect.width;
			int rh = dstart_rect.height;
			
			switch(start_drag_direct)
			{
			// north
			case 0: case 1: case 2:
				rh = display.local_bounds.height - dy;
				if (rh < minimum_size.height) {
					rh = minimum_size.height;
					ry = display.local_bounds.height - rh;
				} else {
					ry = display.local_bounds.y + dy;
				}
				break;
			// south
			case 6: case 7: case 8:
				rh = display.local_bounds.height + dy;
				if (rh < minimum_size.height) {
					rh = minimum_size.height;
				}
				break;
			}
			
			switch(start_drag_direct)
			{
			// west
			case 0: case 3: case 6:
				rw = display.local_bounds.width - dx;
				if (rw < minimum_size.width) {
					rw = minimum_size.width;
					rx = display.local_bounds.width - rw;
				} else {
					rx = display.local_bounds.x + dx;
				}
				break;
			// east
			case 2: case 5: case 8:
				rw = display.local_bounds.width + dx;
				if (rw < minimum_size.width) {
					rw = minimum_size.width;
				}
				break;
			}
			
			dstart_rect.setBounds(rx, ry, rw, rh);
		}
		
		
	}
	
	
	/**
	 * 渲染完子控件后被调用
	 * @param g
	 */
	public void render(DisplayObject display, Graphics2D g)
	{
		if (start_drag_direct != DragResizeObject.DRAG_DIRECT_CENTER)		
		{
			g.pushClip();
			g.pushStroke();
			try {
				Rectangle start_rect = start_drag_bounds;			
				g.setClip(start_rect.x, start_rect.y, start_rect.width+1, start_rect.height+1);
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(border_size));
				g.drawRect(start_rect);
			} finally {
				g.popStroke();
				g.popClip();
			}
		}
	}
	
	/**
	 * 获得拖拽矩形
	 * @return
	 */
	public Rectangle getDstRectangle() 
	{
		return start_drag_bounds;
	}
	
	/**
	 * 根据拖拽方向获得鼠标指针
	 * @param direct
	 * @return
	 */
	public static AnimateCursor getCursor(byte direct)
	{
		switch (direct){
		case 0: return AnimateCursor.RESIZE_CURSOR_NW;
		case 1: return AnimateCursor.RESIZE_CURSOR_N;
		case 2: return AnimateCursor.RESIZE_CURSOR_NE;
		
		case 3: return AnimateCursor.RESIZE_CURSOR_W;
		case 5: return AnimateCursor.RESIZE_CURSOR_E;
		
		case 6: return AnimateCursor.RESIZE_CURSOR_SW;
		case 7: return AnimateCursor.RESIZE_CURSOR_S;
		case 8: return AnimateCursor.RESIZE_CURSOR_SE;
		}
		return null;
	}
	
	/**
	 * @param bounds
	 * @param bs
	 * @param dx
	 * @param dy
	 * @return 
	 * 0 : North West<br>
	 * 1 : North <br>
	 * 2 : North East<br>
	 * 3 : West<br>
	 * 4 : Center<br>
	 * 5 : East<br>
	 * 6 : South West<br>
	 * 7 : South<br>
	 * 8 : South East<br>
	 */
	public static byte getDragDirect(Rectangle bounds, int bs, int dx, int dy)
	{
		bs = Math.max(bs, 1);
		int bx = bounds.x;
		int by = bounds.y;
		int bw = bounds.width;
		int bh = bounds.height;
		int bd = bs<<1;
		int[][] bounds9 = new int[][] {
		{bx     , by,      bs,      bs     }, {bx + bs, by,      bw - bd, bs     }, {bw - bs, by,      bs,      bs     },
		{bx     , by + bs, bs,      bh - bd}, {bx + bs, by + bs, bw - bd, bh - bd}, {bw - bs, by + bs, bs,      bh - bd},
		{bx     , bh - bs, bs,      bs     }, {bx + bs, bh - bs, bw - bd, bs     }, {bw - bs, bh - bs, bs,      bs     },
		};
		for (int i = bounds9.length-1; i >=0; i--) {
			if (CMath.includeRectPoint2(
					bounds9[i][0], bounds9[i][1],
					bounds9[i][2], bounds9[i][3], 
					dx, dy)) {
				return (byte)i;
			}
		}
		return (byte)4;
	}

}

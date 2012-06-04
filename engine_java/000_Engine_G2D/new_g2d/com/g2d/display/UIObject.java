package com.g2d.display;

import java.util.Vector;

import com.g2d.AnimateCursor;
import com.g2d.Composite;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseDragDropAccepter;
import com.g2d.display.event.MouseDragDropListener;
import com.g2d.display.event.MouseDragResizeListener;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.geom.AffineTransform;
import com.g2d.geom.Dimension;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;

/**
 * 一般为一个矩形的显示单位
 * @author WAZA
 */
public abstract class UIObject extends InteractiveObject
{

	/**是否可以拖拽调整大小*/
	@Property("是否可以拖拽调整大小")
	public boolean				enable_drag_resize;

	/**允许拖拽到其他控件上，被拖拽时，将一直显示在鼠标上*/
	public boolean				enable_drag_drop;
	
	/**允许接受拖拽的单位到此控件*/
	public boolean				enable_accept_drag_drop;
	
	
	/**调整边距时的触碰范围*/
	private int					drag_border_size;
	/**调整边距时的最小尺寸*/
	private Dimension			drag_minimum_size;
	
	private DragResizeObject	drag_resize;

	
	Vector<MouseDragResizeListener>	mouse_drag_resize_listeners;
	Vector<MouseDragDropListener>	mouse_drag_drop_listeners;
	Vector<MouseDragDropAccepter>	mouse_drag_drop_accepters;
	
	
//	-------------------------------------------------------------------------------------------------------------

	public UIObject() 
	{
		this.enable_drag_resize				= false;
		this.enable_drag_drop				= false;
		this.drag_border_size				= 4;
		this.drag_minimum_size				= new Dimension(9, 9);
		
		this.mouse_drag_resize_listeners	= new Vector<MouseDragResizeListener>();
		this.mouse_drag_drop_listeners		= new Vector<MouseDragDropListener>();
		this.mouse_drag_drop_accepters		= new Vector<MouseDragDropAccepter>();
	}

	/**子类提供可拖拽数据的方法*/
	protected boolean enable_drag_drop() {
		return enable_drag_drop;
	}
	
	/**是否鼠标颠倒就聚焦*/
	protected boolean enable_click_focus() {
		return true;
	}
	
//	-------------------------------------------------------------------------------------------------------------
	
	void renderInteractive(Graphics2D g)
	{
		if (enable) {
			if (isCatchedMouse()) {
				renderCatchedMouse(g);
			}
			super.renderInteractive(g);
			
			if (enable_drag_resize && enable_drag && !enable_drag_drop) {
				renderDragResize(g);
			}
			else if (enable_accept_drag_drop) {
				if (getStage()!=null) {
					InteractiveObject dragged = getStage().getDraggedObject();
					if (dragged!=null && dragged!=this){
						if (isPickedMouse()) {
							renderAcceptDrapDrop(g);
						}
					}
				}
			}
		} else {
			super.renderInteractive(g);
		}
	}

//	-------------------------------------------------------------------------------------------------------------
	
	void onRenderDragDrop(Stage stage, Graphics2D g)
	{
		g.pushClip();
		g.pushTransform();
		g.pushComposite();
		try
		{
			float tx = stage.mouse_x - (getWidth()>>1)  * stage.drag_drop_scale_x;
			float ty = stage.mouse_y - (getHeight()>>1) * stage.drag_drop_scale_y;
			g.translate(tx, ty);

			g.scale(stage.drag_drop_scale_x, stage.drag_drop_scale_y);
			
			setAlpha(g, stage.drag_drop_object_alpha);
			
			this.renderBefore(g);
			this.render(g);
			this.renderInteractive(g);
			this.renderAfter(g);
		} 
		finally 
		{
			g.popComposite();
			g.popTransform();
			g.popClip();
		}

	}
	
	@Override
	public AnimateCursor getCursor() 
	{
		if (enable_drag_resize && enable_drag && !enable_drag_drop) 
		{
			byte direct = 4;
			if (drag_resize == null) {
				direct = DragResizeObject.getDragDirect(
						local_bounds, 
						drag_border_size, 
						getMouseX(),
						getMouseY());
			} else {
				direct = drag_resize.start_drag_direct;
			}
			return DragResizeObject.getCursor(direct);
		}
		return null;
	}
	
	protected void onStartDrag(MouseMoveEvent event) 
	{
		if (enable_drag_resize)
		{
			this.drag_resize = new DragResizeObject(
				event,
				drag_minimum_size,
				drag_border_size, 
				local_bounds, 
				getMouseX(),
				getMouseY());
			
			if (drag_resize.start_drag_direct != DragResizeObject.DRAG_DIRECT_CENTER) {
				this.onDragResizeStart(drag_resize.start_drag_direct);
				for (MouseDragResizeListener l : mouse_drag_resize_listeners) {
					l.onDragResizeStart(this, drag_resize.start_drag_direct);
				}
			}
		}
	}
	
	protected void onStopDrag()
	{
		if (enable_drag_resize && 
			drag_resize != null &&
			drag_resize.start_drag_direct != DragResizeObject.DRAG_DIRECT_CENTER)
		{
			Rectangle start_rect = drag_resize.getDstRectangle();
			
			this.setLocation(
					x + start_rect.x, 
					y + start_rect.y);
			this.setSize(
					Math.max((int)(start_rect.width), drag_border_size), 
					Math.max((int)(start_rect.height), drag_border_size));
			
			this.onDragResizeEnd();
			for (MouseDragResizeListener l : mouse_drag_resize_listeners) {
				l.onDragResizeEnd(this);
			}
		}
		drag_resize = null;
	}

	protected boolean onUpdateDragging() 
	{
		if (drag_resize != null) {
			drag_resize.update(this);
			for (MouseDragResizeListener l : mouse_drag_resize_listeners) {
				l.onDragResizeRunning(this, drag_resize);
			}
			return drag_resize.start_drag_direct == DragResizeObject.DRAG_DIRECT_CENTER;
		} else {
			return true;
		}
	}

	/**
	 * 渲染完子控件后被调用
	 * @param g
	 */
	void renderDragResize(Graphics2D g) 
	{
		if (drag_resize != null) {
			drag_resize.render(this, g);
		}
	}

//	-------------------------------------------------------------------------------------------------------------
	
	
	public void addEventListener(EventListener listener) {
		super.addEventListener(listener);
		if (listener instanceof MouseDragResizeListener) {
			mouse_drag_resize_listeners.add((MouseDragResizeListener)listener);
		}
		if (listener instanceof MouseDragDropListener) {
			mouse_drag_drop_listeners.add((MouseDragDropListener)listener);
		}
		if (listener instanceof MouseDragDropAccepter) {
			mouse_drag_drop_accepters.add((MouseDragDropAccepter)listener);
		}
	}
	
	public void removeEventListener(EventListener listener) {
		super.removeEventListener(listener);
		if (listener instanceof MouseDragResizeListener) {
			mouse_drag_resize_listeners.remove((MouseDragResizeListener)listener);
		}
		if (listener instanceof MouseDragDropListener) {
			mouse_drag_drop_listeners.remove((MouseDragDropListener)listener);
		}
		if (listener instanceof MouseDragDropAccepter) {
			mouse_drag_drop_accepters.remove((MouseDragDropAccepter)listener);
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------
	
	/**
	 * 设置控件最小值
	 * @param width
	 * @param height
	 */
	public void setMinimumSize(int width, int height) {
		drag_minimum_size.setSize(
				Math.max((drag_border_size<<1)+1, width), 
				Math.max((drag_border_size<<1)+1, height));
	}
	
	public int getMinimumWidth() {
		return drag_minimum_size.width;
	}
	
	public int getMinimumHeight() {
		return drag_minimum_size.height;
	}

//	-------------------------------------------------------------------------------------------------------------
//	sub class impl
	
	protected void renderCatchedMouse(Graphics2D g){}

	protected void renderAcceptDrapDrop(Graphics2D g) {}
	
	
//	-------------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------------
//	drag resize
	/**
	 * 当该控件被鼠标开始拖动以改变大小时发生
	 * @param drag_type 拖拽的类型 
	 * @see InteractiveObject.getDragDirect(Rectangle bounds, int bs, int dx, int dy)
	 */
	protected void onDragResizeStart(int drag_type){
		
	}
	
	/**当该控件被鼠标拖动完成改变大小时发生*/
	protected void onDragResizeEnd(){
		
	}

//	-----------------------------------------------------------------------------------------------------------------
//	drag drop
	
	/**当该控件被鼠标开始拖拽时发生*/
	protected void onMouseStartDragDrop() {
		
	}
	
	/**
	 * 当该控件被鼠标放下拖拽时发生
	 * @param drop_end_object 放下时，接触到的单位
	 */
	protected void onMouseStopDragDrop(DisplayObject drop_end_object) {
		
	}

//	-----------------------------------------------------------------------------------------------------------------
//	drag accepter
	/**
	 * 当有某个控件被拖拽，并在此单位上释放时发生
	 * @param object 被拖拽的控件
	 */
	protected void onDragDropedObject(InteractiveObject object) {
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	


	
	
	
}

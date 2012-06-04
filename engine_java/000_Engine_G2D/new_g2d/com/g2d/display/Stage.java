package com.g2d.display;


import com.cell.math.MathVector;
import com.cell.math.TVector;
import com.g2d.AnimateCursor;
import com.g2d.Canvas;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.display.event.Event;
import com.g2d.display.event.MouseDragDropAccepter;
import com.g2d.display.event.MouseDragDropListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.geom.Point;
import com.g2d.geom.Rectangle;



public abstract class Stage extends DisplayObjectContainer
{
//	-----------------------------------------------------------------------------------------------------------

//	transition
	/** 切换场景时用的贞数 */
	
//	tip
//	transient private TextTip			default_tip;
//	transient private Tip				next_tip;
//	transient private String			next_tip_text;
//	transient private AttributedString	next_tip_atext;

	private TipAnchor					default_tip_anchor			= new TipAnchor.DefaultTipAnchor();
		
	private StageTransition				current_transition			= new DefaultStageTransition();
	
//	picked object
	/**当前控件内，最后被鼠标捕获到的单位*/
	transient private DisplayObject		mouse_picked_object;
	transient private DisplayObject		last_mouse_picked_object;

//	drag drop object
	/** 当鼠标拖拽渲染时，被渲染的单位alpha度 */
	float								drag_drop_object_alpha		= 0.5f;
	/** 当鼠标拖拽渲染时，被渲染的单位缩放比率 */
	float								drag_drop_scale_x 			= 1.0f, 
										drag_drop_scale_y			= 1.0f;
	
	/**被拖拽的单位，该控件将覆盖显示鼠标提示*/
	transient private UIObject			mouse_drag_drop_object;
	
	transient private AnimateCursor		last_cursor;
	
	/** 鼠标移动多少像素开始拖拽 */
	protected int						drag_drop_start_length			= 8;
	transient private TVector			last_mouse_down_pos;
	transient private UIObject			last_mouse_down_object;
	
//	-----------------------------------------------------------------------------------------------------------

	private int keyHoldTimer 			= 0;
	private int keyHoldTimerMax 		= 10;
	
	public boolean isKeyRepeat(int ... keycode)
	{
		if (root.isKeyDown(keycode)) {
			return true;
		}
		if (keyHoldTimer >= keyHoldTimerMax) {
			return root.isKeyDown(keycode);
		}
		return false;
	}
	
	protected Stage() 
	{
		debug 					= false;
	}
	
	/***
	 * 由初始化后调用，用于参数传递。
	 * @param root
	 * @param args
	 */
	public void inited(Canvas root, Object[] args) {
		// no args process
	}
	
//	---------------------------------------------------------------------------------------------------------------
//
	
	final public void setTransition(StageTransition transition) {
		this.current_transition = transition;
	}
	
	final public void startTransitionIn() {
		if (current_transition!=null) {
			current_transition.startTransitionIn();
		}
	}
	
	final public void startTransitionOut() {
		if (current_transition!=null) {
			current_transition.startTransitionOut();
		}
	}
	
	final public boolean isTransition() {
		if (current_transition != null) {
			return current_transition.isTransitionIn() || current_transition.isTransitionOut();
		}
		return false;
	}

	/**
	 * 是否正在生成，进入当前屏幕
	 * @return
	 */
	final public boolean isTransitionIn() {
		if (current_transition != null) {
			return current_transition.isTransitionIn();
		}
		return false;
	}

	/**
	 * 是否正在消亡，将切换到下一屏幕
	 * @return
	 */
	final public boolean isTransitionOut() {
		if (current_transition != null) {
			return current_transition.isTransitionOut();	
		}
		return false;
	}

//	---------------------------------------------------------------------------------------------------------------

	
	final public void setDefaultTipAnchor(TipAnchor anchor) {
		this.default_tip_anchor = anchor;
	}
	
	final public TipAnchor getDefaultTipAnchor() {
		return this.default_tip_anchor;
	}
	
//	public void setCursorG2D(CursorG2D cursor) {
//		this.cursor = cursor;
//	}

//	---------------------------------------------------------------------------------------------------------------

	@Override
	final public boolean onPoolEvent(Event<?> event) {
		return super.onPoolEvent(event);
	}

	final public void onAdded(Canvas canvas, int w, int h){
		this.parent = this;
		this.root = canvas;
		this.x = 0;
		this.y = 0;
		this.local_bounds.x = 0;
		this.local_bounds.y = 0;
		this.local_bounds.width  = w;
		this.local_bounds.height = h;
		super.onAdded(this);
	}
	
	final public void onRemoved(Canvas canvas) {
		super.onRemoved(this);
	}
	
	final public void onUpdate(Canvas canvas, int w, int h)
	{
		this.parent 		= this;
		this.root 			= canvas;
		this.interval_ms	= canvas.getUpdateIntervalMS();
		this.x 				= 0;
		this.y 				= 0;
		this.local_bounds.x = 0;
		this.local_bounds.y = 0;
		this.local_bounds.width  = w;
		this.local_bounds.height = h;
		
		super.onUpdate(this);
		
//		DisplayObject.main_timer ++;
	}
	
	final public void onRender(Canvas canvas, Graphics2D g)
	{	
		g.setFont(canvas.getDefaultFont());
		g.pushClip();
		try {
			g.setClip(local_bounds);
			super.onRender(g);
		} finally {
			g.popClip();
		}
		{
			if (getRoot().isMouseDown(MouseEvent.BUTTON_LEFT)) {
				if (mouse_picked_object instanceof UIObject) {
					last_mouse_down_object = (UIObject)mouse_picked_object;
					if (last_mouse_down_object.enable_drag_drop) {
						last_mouse_down_pos = new TVector(mouse_x, mouse_y);
					}
				}
			}
			if (getRoot().isMouseHold(MouseEvent.BUTTON_LEFT)) {
				if (last_mouse_down_pos != null && last_mouse_down_object != null) {
					if (MathVector.getDistance(
							last_mouse_down_pos.x, 
							last_mouse_down_pos.y, 
							mouse_x, mouse_y) > Math.abs(drag_drop_start_length)) {
						try {
							startDragDrop(last_mouse_down_object);
						} finally {
							last_mouse_down_object = null;
							last_mouse_down_pos = null;
						}
					}
				}
			} else {
				last_mouse_down_object = null;
				last_mouse_down_pos = null;
				if (mouse_drag_drop_object!=null) {
					stopDragDrop();
				}
			}
			
			last_mouse_picked_object = mouse_picked_object;
			mouse_picked_object = null;
			
			if (mouse_drag_drop_object != null) {
				renderDragged(g);
			} else {
				renderTip(g);
			}
		}
		
		if (isTransition()) 
		{
			renderTransition(g);
		} 
		
		if (!canvas.isFocusOwner()) {
			renderLostFocus(g);
		}
	}

	@Override
	final void onUpdate(DisplayObjectContainer parent) {
		super.onUpdate(parent);
		keyHoldTimer ++;
		if (root.getDownKeyCount()>0) {
			keyHoldTimer = 0;
		}
	}

	@Override
	final public void onRender(Graphics2D g) {
		super.onRender(g);
	}
	
	private void renderTransition(Graphics2D g) {
		current_transition.render(g, local_bounds);
	}
	
	private void renderDragged(Graphics2D g) {
		mouse_drag_drop_object.onRenderDragDrop(this, g);
	}
	
	private void renderTip(Graphics2D g)
	{
//		System.out.println("last_mouse_picked_object = "+last_mouse_picked_object);
		if (last_mouse_picked_object instanceof InteractiveObject) {
			InteractiveObject interactive = (InteractiveObject)last_mouse_picked_object;
			Tip tip = interactive.getTip();
			if (tip != null) {
				TipAnchor anchor = tip.getTipAnchor();
				if (anchor == null) {
					anchor = default_tip_anchor;
				}
				if (anchor != null) {
					tip.onUpdate(this);
					Point p = anchor.setStageLocation(this, tip,
							interactive.getScreenX() + interactive.local_bounds.x,
							interactive.getScreenY() + interactive.local_bounds.y,
							interactive.getWidth(),
							interactive.getHeight());
					tip.setLocation(p.x, p.y);
				}
				tip.onRender(g);
			}
			last_cursor = interactive.getCursor();
		} else {
			last_cursor = null;
		}
		
//		if (next_tip != null) {
//			next_tip.setLocation(this, mouse_x, mouse_y);
//			next_tip.onUpdate(this);
//			next_tip.onRender(g);
//			next_tip = null;
//		} else {
//			if (next_tip_text!=null && next_tip_text.length()!=0) {
//				next_tip_atext = TextBuilder.buildScript(next_tip_text);
//				next_tip_text = null;
//			}
//			if (next_tip_atext != null) {
//				default_tip.setText(next_tip_atext);
//				default_tip.setLocation(this, mouse_x, mouse_y);
//				default_tip.onUpdate(this);
//				default_tip.onRender(g);
//				next_tip_atext = null;
//			}
//		}
	}
	

	private void startDragDrop(UIObject obj) {
//		if (mouse_drag_drop_object == null) {
			mouse_drag_drop_object = obj;
			mouse_drag_drop_object.onMouseStartDragDrop();
			for (MouseDragDropListener l : mouse_drag_drop_object.mouse_drag_drop_listeners) {
				l.onMouseStartDragDrop(mouse_drag_drop_object);
			}
//			System.out.println("start drag drop : " + mouse_drag_drop_object);
//		}
	}
	
	private void stopDragDrop() {
		if (mouse_drag_drop_object!=null){
			mouse_drag_drop_object.onMouseStopDragDrop(last_mouse_picked_object);
			for (MouseDragDropListener l : mouse_drag_drop_object.mouse_drag_drop_listeners) {
				l.onMouseStopDragDrop(mouse_drag_drop_object, last_mouse_picked_object);
			}
			if (last_mouse_picked_object instanceof UIObject) {
				UIObject accepter = ((UIObject)last_mouse_picked_object);
				if (accepter.enable_accept_drag_drop){
					accepter.onDragDropedObject(mouse_drag_drop_object);
					for (MouseDragDropAccepter l : accepter.mouse_drag_drop_accepters) {
						l.onDragDropedObject(accepter, mouse_drag_drop_object);
					}
				}
			}
//			System.out.println("stop drag drop : " + mouse_drag_drop_object + " in " + last_mouse_picked_object);
			mouse_drag_drop_object = null;
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------

	@Override
	final protected boolean testCatchMouse(Graphics2D g) {
		return true;
	}
	
	final public void focuseClean(Canvas canvas) {
		stopDragDrop();
	}
	
	public void renderLostFocus(Graphics2D g) {}
	
	public void onFocusGained() {}
	
	public void onFocusLost() {}
	
	/**
	 * 监听客户端窗口关闭按钮
	 * @return 如果使用默认的关闭方法则返回false，即点击关闭后程序退出，如果返回true，则会阻挡窗口关闭由子控件处理。
	 */
	public boolean onWindowClose(){return false;}

//	---------------------------------------------------------------------------------------------------------------
	
	final public AnimateCursor getCursor() {
		return last_cursor;
	}
	
//	public CursorG2D getCursorG2D() {
//		return this.cursor;
//	}
	
//	/**
//	 * 设置鼠标悬停
//	 * @param text
//	 */
//	public void setTip(AttributedString atext) {
//		synchronized (default_tip) {
//			next_tip_text = null;
//			next_tip_atext = atext;
//			next_tip = null;
//		}
//	}
//
//	/**
//	 * 设置鼠标悬停
//	 * @param text
//	 */
//	public void setTip(String script) {
//		synchronized (default_tip) {
//			next_tip_text = script;
//			next_tip_atext = null;
//			next_tip = null;
//		}
//	}
//	
//	/**
//	 * 设置鼠标悬停
//	 * @param text
//	 */
//	public void setTip(Tip tip) {
//		synchronized (default_tip) {
//			next_tip_text = null;
//			next_tip_atext = null;
//			next_tip = tip;
//		}
//	}
//
//	public void setTipTextAntialiasing(boolean enable) {
//		default_tip.enable_antialiasing = enable;
//	}
	
//	----------------------------------------------------------------------------------------------------------------------

	/**
	 * 得到当前获得鼠标的最高层单位
	 * @return
	 */
	final public DisplayObject getMousePickedObject() {
		return last_mouse_picked_object;
	}
	
	final public<T> T getMousePickedObjectAs(Class<T> type) {
		if (type.isInstance(last_mouse_picked_object)) {
			return type.cast(last_mouse_picked_object);
		}
		return null;
	}
	
	final void setMousePickedObject(DisplayObject object) {
		mouse_picked_object = object;
	}

//	----------------------------------------------------------------------------------------------------------------------
	
	final public void setDragDropObjectAlpha(float alpha) {
		alpha = Math.min(1.0f, alpha);
		alpha = Math.max(0.1f, alpha);
		drag_drop_object_alpha = alpha;
	}
	
	final public void setDragDropObjectScale(float sw, float sh) {
		drag_drop_scale_x = sw;
		drag_drop_scale_y = sh;
	}
	
	final public InteractiveObject getDraggedObject() {
		return mouse_drag_drop_object;
	}
	
	public static class DefaultStageTransition implements StageTransition
	{
		private int 				transition_max_time		= 10;
		private int 				transition_timer		= 0;
		
		private boolean 			is_transition_in 		= true;
		private boolean 			is_transition_out		= false;

		public Color 				color = new Color(0, 0, 0, 1f);
		
		public DefaultStageTransition() {
			startTransitionIn();
		}
		public DefaultStageTransition(Color color) {
			startTransitionIn();
			this.color = color;
		}
		
		public void setTransitionMaxTime(int time) {
			transition_max_time = Math.max(1, time);
		}
		
		public void startTransitionIn() {
			if (!is_transition_in) {
				is_transition_in 	= true;
				is_transition_out 	= false;
				transition_timer 	= 0;
			}
		}
		
		public void startTransitionOut() {
			if (!is_transition_out) {
				is_transition_in 	= false;
				is_transition_out 	= true;
				transition_timer 	= 0;
			}
		}
		
		public boolean isTransition() {
			return is_transition_out || is_transition_in;
		}

		public boolean isTransitionIn() {
			return is_transition_in;
		}

		public boolean isTransitionOut() {
			return is_transition_out;
		}

		@Override
		public void render(Graphics2D g, Rectangle bounds)
		{
			float alpha = 0;
			
			if (is_transition_in) {
				alpha = 1 - transition_timer / (float) transition_max_time;
			} else if (is_transition_out) {
				alpha = transition_timer / (float) transition_max_time;
			}
			alpha = Math.max(alpha, 0);
			alpha = Math.min(alpha, 1);
			g.pushComposite();
			try{
				g.setColor(color);
				g.setAlpha(alpha);
				g.fillRect(bounds);
			} finally {
				g.popComposite();
			}
			
			if (transition_timer > transition_max_time) {
				is_transition_in	= false;
				is_transition_out	= false;
			}
			transition_timer ++;
		}
	}
}

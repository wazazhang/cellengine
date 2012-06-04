package com.g2d.display;

import java.util.Stack;

import com.cell.CMath;
import com.cell.math.Vector;
import com.g2d.Canvas;
import com.g2d.Color;
import com.g2d.Composite;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.geom.AffineTransform;
import com.g2d.geom.Dimension;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;


/**
 * @author WAZA
 *
 */
public abstract class DisplayObject implements Vector
{
//	--------------------------------------------------------------------------------------------------------------------------
	
//	public static int 		main_timer;

	static Stack<Object> display_stack = new Stack<Object>();
	
	public static Engine getEngine() {
		return Engine.getEngine();
	}

//	-------------------------------------------------------------
//	 public
	
	/** 是否可视,如果父节点不可视,那么子节点将也不可视 */ 
	public boolean 				visible = true;
	
	/** 基于父节点的位置 */
	@Property("x")
	public double				x;
	@Property("y")
	public double				y;
	@Property("z")
	public double				z;
	
	/** 优先级别 */
	public int 					priority;
	
	/** 当前坐标系的 rectangle */
	@Property("Bounds")
	public Rectangle 			local_bounds 		= new Rectangle(0,0,100,100);

	@Property("ClipBounds")
	public boolean				clip_local_bounds	= false;
	
//	-------------------------------------------------------------
	
	/** debug , transient 代表运行时的数据，不能够序列化*/
	transient public boolean 	debug;
	
	/** 该对象被更新了多少次, 不可序列化 */
	transient public int 		timer;

	/**上次更新和这次更新相距的时间*/
	transient int				interval_ms;
	
//	transient long				last_update_time	= System.currentTimeMillis();
//	-------------------------------------------------------------
//	extends 
	
	/** 表示鼠标是否在local_bounds内,
	 * isHitMouse的快照，如果isHitMouse被重写，则该变量将无效*/
	transient private boolean	hit_mouse;
	
	/** isCatchedMouse的快照，如果isCatchedMouse被重写，则该变量将无效*/
	transient private boolean	catched_mouse;
	
	/**鼠标在当前对象的相对坐标*/
	transient int		mouse_y;
	transient int 		mouse_x;
	
//	-------------------------------------------------------------
//	 local
	
	/** 父节点 */
	transient DisplayObjectContainer	parent;
	/** 父节点向子节点递归时,向子节点传输 root canvas 信息 */
	transient Canvas 					root;
	/** 屏幕坐标系的 x, y */
	transient int 						screen_x, screen_y;

//	--------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 得到stage外的最上层java控件
	 * @return
	 */
	final public com.g2d.Canvas getRoot() {
		return root;
	}
	
	/**
	 * 得到父节点
	 * @return
	 */
	final public DisplayObjectContainer getParent() {
		return parent;
	}
	
	/**
	 * 得到当前所在的stage
	 * @return
	 */
	final public Stage getStage() {
		if (root != null) {
			return root.getStage();
		}
		return null;
	}
	
	/**
	 * 是否在当前操作系统中被聚焦
	 * @return
	 */
	final public boolean isFocusedRoot()
	{
		if (root!=null && !root.isFocusOwner()){
			return false;
		}
		
		DisplayObjectContainer p = this.parent;
		DisplayObject c = this;
		
		while (p != null) 
		{
			if (p.getFocus() != c) {
				return false;
			}
			c = p;
			p = p.getParent();
		}
		
		return true;
	}
	
	/**
	 * 请求在所有层次被聚焦，也就是说设置为深度遍历的第一个节点
	 * @return
	 */
	public void requestFocusRoot()
	{
		DisplayObjectContainer p = this.parent;
		DisplayObject c = this;
		while (p != null && p != c) {
			p.focus(c);			
			if (p instanceof Stage) {
				return;
			}
			c = p;
			p = p.getParent();
		}
	}
	
	/**
	 * 从父节点移除自己
	 * @return
	 */
	final public boolean removeFromParent() {
		try{
			if( parent != null) {
				parent.removeChild(this);
				return true;
			}
		}finally{
			parent = null;
		}
		return false;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	advanced
	
	
//	--------------------------------------------------------------------------------------------------------------------------------------------
//	local position with parent

	/**
	 * 是否为debug模式，显示一些debug信息
	 * @param d
	 */
	public void setDebug(boolean d) {
		debug = d;
	}
	
	/**
	 * 得到位于父节点的坐标
	 * @return
	 */
	public int getX() {
		return (int)x;
	}
	
	/**
	 * 得到位于父节点的坐标
	 * @return
	 */
	public int getY() {
		return (int)y;
	}
	
	/**
	 * 得到矩形尺寸
	 * @return
	 */
	public int getWidth() {
		return local_bounds.width;
	}
	
	/**
	 * 得到矩形尺寸
	 * @return
	 */
	public int getHeight() {
		return local_bounds.height;
	}
	
	/**
	 * 设置位于父节点的坐标
	 * @return
	 */
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 设置到父节点的中心
	 */
	public void setCenter(){
		if (parent!=null){
			this.x = parent.getWidth()-this.getWidth()>>1;
			this.y = parent.getHeight()-this.getHeight()>>1;
		}
	}
	
	/**
	 * 设置矩形尺寸
	 * @return
	 */
	public void setSize(int w, int h) {
		local_bounds.width = w;
		local_bounds.height = h;
	}

	final public void setSize(Dimension dimension) {
		setSize(dimension.width, dimension.height);
	}
	
	/**
	 * 设置基于自己坐标系的 bounds
	 * @return
	 */
	public void setLocalBounds(int x, int y, int w, int h) {
		local_bounds.x = x;
		local_bounds.y = y;
		local_bounds.width = w;
		local_bounds.height = h;
	}

	/**
	 * 设置优先级
	 * @param p
	 */
	public void setPriority(int p) {
		this.priority = p;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	screen position with root
	
	final public int getScreenX(){
		return screen_x;
	}
	
	final public int getScreenY(){
		return screen_y;
	}
	
	final public int screenToLocalX(int x) {
		return x - screen_x;
	}

	final public int screenToLocalY(int y) {
		return y - screen_y;
	}

	final public int localToScreenX(int x) {
		return screen_x + x;
	}

	final public int localToScreenY(int y) {
		return screen_y + y;
	}
	
	final public Rectangle getScreenBounds(){
		Rectangle rect = new Rectangle(local_bounds);
		rect.x += screen_x;
		rect.y += screen_y;
		return rect;
	}
	
//	-------------------------------------------------------------

	/**表示鼠标是否在local_bounds内*/
	final public boolean isHitMouse() {
		return hit_mouse;
	}
	
	/**鼠标在当前对象的相对坐标*/
	final public int getMouseX() {
		return mouse_x;
	}
	
	/**鼠标在当前对象的相对坐标*/
	final public int getMouseY() {
		return mouse_y;
	}

	/**
	 * 表示该控件是否得到鼠标<br>
	 * 最先得到鼠标的控件将获得鼠标输入,键盘输入的焦点<br>
	 * 默认情况下是鼠标在local_bounds内,并且鼠标在graphics的clip内<br>
	 * 可以覆盖 {@link testCatchMouse(Graphics2D g)}方法来确定是否能获取鼠标
	 */
	final public boolean isCatchedMouse() {
		return catched_mouse;
	}

	/**
	 * 提供是否能被鼠标获取的能力，一般情况，该方法将返回isCatchedMouse()的快照。
	 * @param g
	 * @return
	 */
	abstract protected boolean testCatchMouse(Graphics2D g);
	
	/**
	 * 是否是当前唯一获得鼠标的最高层对象
	 * @return
	 */
	final public boolean isPickedMouse() {
		if (root!=null) {
			return root.getStage().getMousePickedObject() == this;
		}
		return false;
	}
	
	
	
//	-------------------------------------------------------------

	
	/**
	 * 递归方法
	 * @param event
	 * @return 如果该对象处理了消息,则返回true
	 */
	boolean onPoolEvent(com.g2d.display.event.Event<?> event) {
		// 直接跳过该消息
		return false;
	}
	
	/**
	 * 递归方法
	 * @param parent
	 */
	void onAdded(DisplayObjectContainer parent) {
		this.added(parent);
	}
	
	/**
	 * 递归方法
	 * @param parent
	 */
	void onRemoved(DisplayObjectContainer parent) {
		this.removed(parent);
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 递归方法
	 * @param parent
	 */
	void onUpdate(DisplayObjectContainer parent) 
	{
		this.parent 			= parent;
		this.root 				= parent.root;
		this.interval_ms		= parent.interval_ms;
		
		this.screen_x = parent.screen_x + (int)this.x;
		this.screen_y = parent.screen_y + (int)this.y;
		
		this.mouse_x = screenToLocalX(root.getMouseX());
		this.mouse_y = screenToLocalY(root.getMouseY());
		this.hit_mouse = CMath.includeRectPoint2(
				screen_x+local_bounds.x, 
				screen_y+local_bounds.y, 
				local_bounds.width, 
				local_bounds.height,
				root.getMouseX(), root.getMouseY());

		this.updateBefore(parent);
		
		this.update();
		
		this.updateAfter(parent);
		
		this.timer ++;
	}
	
	void updateBefore(DisplayObjectContainer parent) {}
	void updateAfter(DisplayObjectContainer parent) {}
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 递归方法
	 * @param g
	 */
	public void onRender(Graphics2D g) 
	{
		if (visible) 
		{
			g.pushClip();
			g.pushTransform();
			g.pushComposite();
			try {
				g.translate(x, y);
				if (clip_local_bounds) {
					g.clip(local_bounds);
				}
				if (testCatchMouse(g)) {
					catched_mouse = true;
					Stage stage = getStage();
					if (stage != null)
						stage.setMousePickedObject(this);
				} else {
					catched_mouse = false;
				}
				this.renderBefore(g);
				this.render(g);
				this.renderDebug(g);
				this.renderInteractive(g);
				this.renderAfter(g);
			} finally {
				g.popComposite();
				g.popTransform();
				g.popClip();
			}
		}
	}
	

	protected void renderBefore(Graphics2D g){}
	
	protected void renderAfter(Graphics2D g) {}
	
	void renderInteractive(Graphics2D g){}
	
	protected void renderDebug(Graphics2D g)
	{
		if (debug) {
			g.setColor(Color.GREEN);
			g.drawRect(local_bounds.x, local_bounds.y, local_bounds.width - 1, local_bounds.height - 1);
			g.setColor(Color.CYAN);
			g.drawLine(-10, 0, 0 + 10, 0);
			g.drawLine(0, 0 - 10, 0, 0 + 10);
			g.setColor(Color.YELLOW);
			g.drawLine(-10, priority, 0 + 10, priority);
			g.drawLine(0, priority - 10, 0, priority + 10);
		}
	}
	
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 在该对象被添加到场景中后发生</br>
	 * 当父控件被添加到爷爷控件时，该方法也会被调用，此时parent等于爷爷<br>
	 * 注意:不要在此处初始化持久性数据
	 * @param parent
	 */
	abstract public void added(DisplayObjectContainer parent);
	
	/**
	 * 在该对象从父控件中移除后发生</br>
	 * 当父控件从爷爷控件移除时，该方法也会被调用，此时parent等于爷爷<br>
	 * 注意:不要在此处初始化持久性数据
	 * @param parent
	 */
	abstract public void removed(DisplayObjectContainer parent);
	
	/**
	 * 主更新
	 */
	abstract public void update();
	
	/**
	 * 主渲染
	 * @param g
	 */
	abstract public void render(Graphics2D g);
	
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	final public boolean isInParentBounds(DisplayObjectContainer parent)
	{
		return CMath.intersectRect2(
				parent.local_bounds.x,
				parent.local_bounds.y,
				parent.local_bounds.width,
				parent.local_bounds.height,
				(float)(x + local_bounds.x), 
				(float)(y + local_bounds.y), 
				local_bounds.width, 
				local_bounds.height);
	}
	
	
	final public void setAlpha(Graphics2D g, float alpha)
	{
		g.setAlpha(alpha);
	}


	final public <T> void pushObject(T value){
		display_stack.push(value);
	}
	
	final public <T> T popObject(Class<T> type){
		return type.cast(display_stack.pop());
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	final public void addVectorX(double dx) {
		this.x += dx;
	}

	@Override
	final public void addVectorY(double dy) {
		this.y += dy;
	}

	@Override
	final public double getVectorX() {
		return this.x;
	}

	@Override
	final public double getVectorY() {
		return this.y;
	}

	@Override
	final public void setVectorX(double x) {
		this.x = x;
	}

	@Override
	public void setVectorY(double y) {
		this.y = y;
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 测试同一父节点的2个单位是否碰撞
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean touch(DisplayObject src, DisplayObject dst)
	{
		return CMath.intersectRect2(
				(float)src.x+src.local_bounds.x, 
				(float)src.y+src.local_bounds.y, 
				src.local_bounds.width, 
				src.local_bounds.height, 
				
				(float)dst.x+dst.local_bounds.x, 
				(float)dst.y+dst.local_bounds.y, 
				dst.local_bounds.width, 
				dst.local_bounds.height
				);
	}
	
	/**
	 * 测试同一父节点的2个单位是否碰撞
	 * @param src 包含者
	 * @param dst 被包含着
	 * @return
	 */
	public static boolean include(DisplayObject src, DisplayObject dst)
	{
		return CMath.includeRectPoint2(
				(float)src.x+src.local_bounds.x, 
				(float)src.y+src.local_bounds.y, 
				src.local_bounds.width, 
				src.local_bounds.height, 
				(float)dst.x,
				(float)dst.y
				);
	}

	/**上次更新和这次更新相距的时间*/
	public int getIntervalMS() {
		return interval_ms;
	}
}

package com.g2d.display;

import java.awt.Graphics2D;
import java.util.Vector;

import com.g2d.Version;
import com.g2d.display.event.Event;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.KeyListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseListener;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.event.MouseWheelListener;

/**
 * InteractiveObject 二要素<br>
 * 1. 被聚焦 <br>
 *    处理键盘，鼠标滚轮事件<br>
 * 2. 已获得鼠标指针<br>
 *    处理鼠标点击事件<br>
 * @author WAZA
 */
public abstract class InteractiveObject extends DisplayObjectContainer
{
//	private static InteractiveObject s_focused_object = null;
	private static final long serialVersionUID = Version.VersionG2D;

	/**是否接收事件,如果为false,该对象就只相当于DisplayObjectContainer,也不向孩子传送事件*/
	public boolean				enable;
	
	/**是否处理输入事件,如果为false,则只向孩子传送事件*/
	public boolean 				enable_input;
	
	/**是否可被聚焦*/
	public boolean 				enable_focus;
	
	/**是否支持鼠标滚轮事件*/
	public boolean 				enable_mouse_wheel;
	
	/**是否支持键盘事件*/
	public boolean 				enable_key_input;

	/**是否可被拖动*/
	public boolean 				enable_drag;
	
//	----------------------------------------------------------------------------------------------
	
//	----------------------------------------------------------------------------------------------

	transient private boolean 						is_focused;
	transient private int 							catched_mouse_down_tick ;
	transient private MouseMoveEvent 				mouse_draged_event ;

	transient private Vector<KeyListener> 			keylisteners;
	transient private Vector<MouseListener> 		mouselisteners;
	transient private Vector<MouseWheelListener> 	mousewheellisteners;

//	-----------------------------------------------------------------------------------------------------------------
	
	public InteractiveObject() 
	{
		enable					= true;
		enable_input			= true;
		enable_focus			= true;
		enable_drag				= false;
		enable_mouse_wheel		= false;
		enable_key_input		= false;
		
		is_focused 				= false;
		keylisteners 			= new Vector<KeyListener>();
		mouselisteners 			= new Vector<MouseListener>();
		mousewheellisteners		= new Vector<MouseWheelListener>();
	}

//	-----------------------------------------------------------------------------------------------------------------

	/**
	 * 当前是否被父节点聚焦
	 * @return the is_focused
	 */
	public boolean isFocused() {
		return is_focused;
	}

	/**
	 * 返回该控件对应的鼠标
	 * @return
	 */
	public AnimateCursor getCursor() {
		return null;
	}

	/**
	 * 返回该控件对应的Tip
	 * @return
	 */
	public Tip getTip() {
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		if (!enable || !enable_focus) {
			return false;
		} else if (isOnDragged()) {
			return true;
		} else if (isHitMouse() && g.hitClip(mouse_x, mouse_y, 1, 1)) {
			return true;
		} else {
			return false;
		}
	}

	/**子类提供可拖拽数据的方法*/
	protected boolean enable_drag_drop() {
		return false;
	}
	
	/**是否鼠标颠倒就聚焦*/
	protected boolean enable_click_focus() {
		return false;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	
	final void onUpdate(DisplayObjectContainer parent) 
	{
		is_focused = parent.getFocus() == this;
		
		super.onUpdate(parent);
		
		if (enable)
		{
			if (enable_input && visible) {
				if (!getRoot().isMouseHold(java.awt.event.MouseEvent.BUTTON1)){
					stopDrag();
				} else if (mouse_draged_event!=null && !enable_drag_drop()) {
					if (enable_drag) {
						onDrag();
					}
					onMouseDraged(mouse_draged_event);
				}
			}
		}
	}

	final boolean onPoolEvent(Event<?> event) 
	{
		if (enable)
		{
	
				if (getParent()!=null) {
					if (getParent().getFocus() == this) {
						is_focused = true;
					} else if (enable_focus) {
						if ((event instanceof MouseEvent) && 
							((MouseEvent)event).type == MouseEvent.EVENT_MOUSE_DOWN) {
							if (isCatchedMouse() && enable_click_focus()) {
								is_focused = true;
								if (getParent() != null) {
									getParent().focus(this);
								}
							}
						}
					}
				}
				
				// 先向孩子传递
				if (is_focused) {
					if (super.onPoolEvent(event)) {
						return true;
					}
				}
				
				if (enable_input && visible) {
					if (event instanceof MouseWheelEvent) {
						return processMouseWheelEvent((MouseWheelEvent) event);
					} else if (event instanceof MouseEvent) {
						boolean ret = processMouseEvent((MouseEvent) event);
	//					if (ret) {
	//						System.out.println("f object = " + toString());
	//					}
						return ret;
					} else if (event instanceof KeyEvent) {
						return processKeyEvent((KeyEvent) event);
					}
				}

		}
		return false;
	}
	
	final boolean processKeyEvent(KeyEvent event) 
	{
		if (is_focused && enable_key_input)
		{
			event.source = this;
			switch(event.type)
			{
			case KeyEvent.EVENT_KEY_DOWN:
				onKeyDown(event);
				for (KeyListener listener : keylisteners) {
					listener.keyDown(event);
				}
				break;
			case KeyEvent.EVENT_KEY_UP:
				onKeyUp(event);
				for (KeyListener listener : keylisteners) {
					listener.keyUp(event);
				}
				break;
			case KeyEvent.EVENT_KEY_TYPED:
				onKeyTyped(event);
				for (KeyListener listener : keylisteners) {
					listener.keyTyped(event);
				}
				break;
			}
			return true;
		}
		return false;
	}


	final boolean processMouseWheelEvent(MouseWheelEvent event)
	{
		if (is_focused && enable_mouse_wheel)
		{
			event.source = this;
			onMouseWheelMoved(event);
			if (!mousewheellisteners.isEmpty()){
				for (MouseWheelListener listener : mousewheellisteners) {
					listener.mouseWheelMoved(event);
				}
			}
			return true;
		}
		return false;
	}
	
	
	final boolean processMouseEvent(MouseEvent event)
	{
		if (isCatchedMouse() && isHitMouse())
		{
			event.source = this;
			switch(event.type)
			{
			case MouseEvent.EVENT_MOUSE_DOWN:
				// mouse down event
				onMouseDown(event);
				for (MouseListener listener : mouselisteners) {
					listener.mouseDown(event);
				}
				// mouse click record
				catched_mouse_down_tick = event.mouseDownCount;
				// mouse drag start
				if (event.mouseButton == MouseEvent.BUTTON_LEFT && !enable_drag_drop()) {
//					System.out.println("start drag");
					startDrag(event);
				}else{
					mouse_draged_event = null;
				}
				return true;
			case MouseEvent.EVENT_MOUSE_UP:
				// mouse up event
				onMouseUp(event);
				for (MouseListener listener : mouselisteners) {
					listener.mouseUp(event);
				}
				// mouse click test
				if (catched_mouse_down_tick == event.mouseDownCount) {
					onMouseClick(event);
					for (MouseListener listener : mouselisteners) {
						listener.mouseClick(event);
					}
				}
				// mouse drag end
				stopDrag();
				return true;
			}
			
			return true;
		}
		return false;
	}


//	-----------------------------------------------------------------------------------------------------------------
	
	public void addEventListener(EventListener listener) {
		if (listener instanceof KeyListener) {
			keylisteners.add((KeyListener)listener);
		}
		if (listener instanceof MouseListener) {
			mouselisteners.add((MouseListener)listener);
		}
		if (listener instanceof MouseWheelListener) {
			mousewheellisteners.add((MouseWheelListener)listener);
		}
	}
	
	public void removeEventListener(EventListener listener) {
		if (listener instanceof KeyListener) {
			keylisteners.remove((KeyListener)listener);
		}
		if (listener instanceof MouseListener) {
			mouselisteners.remove((MouseListener)listener);
		}
		if (listener instanceof MouseWheelListener) {
			mousewheellisteners.remove((MouseWheelListener)listener);
		}
	}

//	-----------------------------------------------------------------------------------------------------------------
//	-----------------------------------------------------------------------------------------------------------------
//	mouse
	/**当有鼠标按键按下时发生
	 * @see MouseListener*/
	protected void onMouseDown(MouseEvent event) {}
	/**当有鼠标按键松开下时发生
	 * @see MouseListener*/
	protected void onMouseUp(MouseEvent event) {}
	/**当有鼠标按键完成了一次点击时发生(先按下，后松开)
	 * @see MouseListener*/
	protected void onMouseClick(MouseEvent event) {}
	/**当该控件被鼠标按键按住并移动时发生
	 * @see MouseListener*/
	protected void onMouseDraged(MouseMoveEvent event){}

//	-----------------------------------------------------------------------------------------------------------------
//	mouse wheell
	/**当该控件获得焦点并有鼠标滚轮事件发生时
	 * @see MouseWheelListener*/
	protected void onMouseWheelMoved(MouseWheelEvent event){}

//	-----------------------------------------------------------------------------------------------------------------
//	key
	/**当该控件获得焦点并有键盘按下某键时发生
	 * @see KeyListener*/
	protected void onKeyDown(KeyEvent event) {}
	/**当该控件获得焦点并有键盘松开某键时发生
	 * @see KeyListener*/
	protected void onKeyUp(KeyEvent event) {}
	/**当该控件获得焦点并有键盘敲入字符时发生
	 * @see KeyListener*/
	protected void onKeyTyped(KeyEvent event) {}

//	-----------------------------------------------------------------------------------------------------------------

	
//	-----------------------------------------------------------------------------------------------------------------

	public boolean isOnDragged() {
		return mouse_draged_event!=null;
	}

	final private void startDrag(MouseEvent event) 
	{
		mouse_draged_event 				= new MouseMoveEvent(event.superEvent, getMouseX(), getMouseY());
		mouse_draged_event.source		= this;
		onStartDrag(mouse_draged_event);
	}
	
	
	final private void stopDrag()
	{
		onStopDrag();
		mouse_draged_event = null;
	}
	
	final private void onDrag() 
	{
		if (onUpdateDragging()) {
			this.setLocation(
					parent.getMouseX() - mouse_draged_event.mouseDownStartX, 
					parent.getMouseY() - mouse_draged_event.mouseDownStartY);
		}
	}

//	-----------------------------------------------------------------------------------------------------------------
	
	protected void onStartDrag(MouseMoveEvent event) {}

	protected void onStopDrag() {}

	/**
	 * 正在拖动控件时调用，返回true代表该控件将位移
	 * @return
	 */
	protected boolean onUpdateDragging() {
		return true;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
}

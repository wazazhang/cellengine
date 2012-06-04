package com.g2d.java2d;

import java.applet.Applet;
import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.VolatileImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.exception.NotImplementedException;
import com.g2d.AnimateCursor;
import com.g2d.Engine;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.event.Event;
import com.g2d.display.event.TextInputer;

public abstract class CommonCanvasAdapter implements 
com.g2d.Canvas,
KeyListener, 
MouseListener, 
MouseMotionListener, 
MouseWheelListener, 
FocusListener
{
	final private Component 			component;
	
//	game stage
	private int 						stageWidth 		= 800;
	private int 						stageHeight 	= 600;
	private Stage 						currentStage 	= null;
	
	private Class<?>					nextStageClass 	= null;
	private Object[]					nextStageArgs;
	private Stage						nextStage;
	
	transient private long 				prewUpdateTime			= 0;
	private int 						framedelay				= 30;
	private int							framedelay_unactive 	= 1000;
	private double 						fps;
	
	private float 						size_rate_x;
	private float 						size_rate_y;
	private int 						mousex;
	private int 						mousey;
	
	private long						last_update_time	= 0;
	private int							update_interval		= 0;

//	private ConcurrentLinkedQueue<Event<?>>		event_queue 		= new ConcurrentLinkedQueue<Event<?>>();
	private AtomicReference<Class<?>>	last_stage_type = new AtomicReference<Class<?>>();
	
	private HashMap<Integer, KeyEvent> 			keystate 			= new HashMap<Integer, KeyEvent>();	// hold state
	private HashMap<Integer, KeyEvent> 			keystate_down 		= new HashMap<Integer, KeyEvent>();	// 收集按键
	private HashMap<Integer, KeyEvent> 			keystate_up 		= new HashMap<Integer, KeyEvent>();	// 收集按键
	private HashMap<Integer, KeyEvent> 			keystate_query_down = new HashMap<Integer, KeyEvent>(); // 用于查询
	private HashMap<Integer, KeyEvent> 			keystate_query_up	= new HashMap<Integer, KeyEvent>(); // 用于查询
	
	private HashMap<Integer, MouseEvent> 		mousestate 				= new HashMap<Integer, MouseEvent>();
	private HashMap<Integer, MouseEvent> 		mousestate_down 		= new HashMap<Integer, MouseEvent>();
	private HashMap<Integer, MouseEvent> 		mousestate_up 			= new HashMap<Integer, MouseEvent>();
	private HashMap<Integer, MouseEvent> 		mousestate_query_down	= new HashMap<Integer, MouseEvent>();
	private HashMap<Integer, MouseEvent> 		mousestate_query_up		= new HashMap<Integer, MouseEvent>();
	
	private HashMap<Integer, Long> 				mousestate_prev_down_time	= new HashMap<Integer, Long>();
	private HashMap<Integer, MouseEvent> 		mousestate_prev_down_pos	= new HashMap<Integer, MouseEvent>();
	
	private HashMap<Integer, MouseWheelEvent> 	mousewheel			= new HashMap<Integer, MouseWheelEvent>();
	private HashMap<Integer, MouseWheelEvent> 	mousewheel_query	= new HashMap<Integer, MouseWheelEvent>();

	private final int[] KEYCODES_4_TEMP = new int[]{};

	private com.g2d.Font				defaultFont;
	private AnimateCursor				defaultCursor;
	private AnimateCursor				nextCursor;

//	--------------------------------------------------------------------------------------------------------------------------
//	construction
	
	public CommonCanvasAdapter(
			Component comp, 
			int stage_width, 
			int stage_height)
	{
		component		= comp;
		stageWidth		= stage_width;
		stageHeight		= stage_height;

		component.addKeyListener(this);
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
		component.addFocusListener(this);

		//
		component.setFocusable(true);
		component.setEnabled(true);
		component.enableInputMethods(true);
		
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,  new HashSet<AWTKeyStroke>());
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, new HashSet<AWTKeyStroke>());

		component.setSize(stageWidth, stageHeight);
		component.setMinimumSize(new Dimension(100,100));
	}
	
	public Component getComponent() {
		return component;
	}
	
	public boolean isFocusOwner() {
		return component.isFocusOwner();
	}

	public void setDefaultCursor(AnimateCursor cursor) {
		defaultCursor = cursor;
	}
	
	public void setDefaultFont(com.g2d.Font font) {
		defaultFont = font;
	}

	public com.g2d.Font getDefaultFont() {
		return defaultFont;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------
//	stage
//	--------------------------------------------------------------------------------------------------------------------------

	/**
	 * 切换场景
	 * @param stage
	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
	 */
	public void changeStage(Stage stage, Object ... args) {
		nextStage = stage;
		nextStageArgs = args;
		if (currentStage!=null) {
			currentStage.startTransitionOut();
		}
	}
	
//	/**
//	 * 切换场景
//	 * @param stageName
//	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
//	 */
//	@SuppressWarnings("unchecked")
//	public void changeStage(String stageName, Object ... args) {
//		try {
//			this.changeStage((Class<Stage>) Class.forName(stageName), args);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 切换场景
	 * @param stageClass
	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
	 */
	public void changeStage(Class<? extends Stage> stageClass, Object ... args){
		nextStageClass = stageClass;
		nextStageArgs = args;
		if (currentStage!=null) {
			currentStage.startTransitionOut();
		}
	}
	
	/***
	 * 立刻切换当前场景
	 * @param stage
	 */
	public void setStage(Stage stage) {
		if (currentStage != null) {
			currentStage.onRemoved(this);
			currentStage = null;
		}
		if (stage != null) {
			currentStage = stage;
			currentStage.setSize(stageWidth, stageHeight);
			currentStage.inited(this, nextStageArgs);
			currentStage.onAdded(this, stageWidth, stageHeight);
			currentStage.startTransitionIn();
		}
	}
	
	/**
	 * 设置场景象素大小
	 * @param width
	 * @param height
	 */
	public void setStageSize(int width, int height)
	{
		stageWidth = width;
		stageHeight = height;
	}
	
	public int getStageWidth() {
		return stageWidth;
	}
	
	public int getStageHeight() {
		return stageHeight;
	}
	
	public Stage getStage() {
		return currentStage;
	}

	public Class<?> getLastStageType() {
		return last_stage_type.get();
	}
	
//	--------------------------------------------------------------------------------------------------------------------------
//	update and transition
//	--------------------------------------------------------------------------------------------------------------------------

	/**
	 * 设置游戏更新速度
	 * @param fps
	 */
	public void setFPS(double fps) {
		framedelay = (int)(1000 / fps);
	}
	
	/**
	 * 得到帧延迟，单位ms
	 * @return
	 */
	public int getFrameDelay() {
		return framedelay;
	}

	public void setUnactiveFPS(double fps) {
		framedelay_unactive = (int)(1000 / fps);
	}
	
	public int getUnactiveFrameDelay() {
		return framedelay_unactive;
	}
	
	/**
	 * 得到游戏更新速度
	 * @return
	 */
	public int getFPS() {
		return (int)fps;
	}

	@Override
	public int getUpdateIntervalMS() {
		return update_interval;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------
//	game
//	--------------------------------------------------------------------------------------------------------------------------
	
	protected float getMouseSizeRateW(int stageWidth) {
		return (float)stageWidth / getComponent().getWidth();
	}
	protected float getMouseSizeRateH(int stageWidth) {
		return (float)stageHeight/ getComponent().getHeight();
	}
//	public void repaint_game() {
//		component.repaint(0, 0, 0, getWidth(), getHeight());
//	}
	
	public void update(java.awt.Graphics2D g2d) 
	{
//		GraphicsConfiguration gc = ((Graphics2D)g).getDeviceConfiguration();
		
		try 
		{
			if (last_update_time == 0) {
				update_interval = 0;
			} else {
				update_interval = (int)(System.currentTimeMillis() - last_update_time);
			}
			last_update_time = System.currentTimeMillis();
			
			fixMouse() ;
			queryKey();
			
			size_rate_x = getMouseSizeRateW(stageWidth);
			size_rate_y = getMouseSizeRateH(stageHeight);

			nextCursor = null;
			
			updateStage(g2d, currentStage);
			
			if (currentStage != null) {
				nextCursor = currentStage.getCursor();
			}
			
			if (nextCursor instanceof CommonAnimateCursor) {
				getComponent().setCursor(((CommonAnimateCursor)nextCursor).update());
			} else if (defaultCursor instanceof CommonAnimateCursor) {
				getComponent().setCursor(((CommonAnimateCursor)defaultCursor).update());
			} else {
				getComponent().setCursor(Cursor.getDefaultCursor());
			}
			
			tryChageStage();
			
			double updatetime = System.currentTimeMillis() - prewUpdateTime;
			if (updatetime>0){
				fps = (int)(1000.0D / updatetime);
			}
			prewUpdateTime = System.currentTimeMillis();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	 
	protected abstract void updateStage(java.awt.Graphics2D g2d, Stage currentStage);
	
	protected void tryChageStage()
	{
		if (nextStageClass != null || nextStage != null)
		{
			// clear current stage
	    	if(currentStage!=null)
    		{
	    		if (!currentStage.isTransition())
	    		{
	    			currentStage.onRemoved(this);
	    			last_stage_type.set(currentStage.getClass());
	    			currentStage = null;
	    			System.gc();
	    		}
    		}
	    	else
	    	{
	    		if (nextStageClass != null)
		    	{
		    		try {
						currentStage = (Stage) nextStageClass.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }
		    	else if (nextStage != null)
		    	{
					currentStage = nextStage;
			    }
		    	
		    	System.out.println("ChangeStage -> "+ currentStage.getClass().getName());	
		    	{
		    		currentStage.setSize(stageWidth, stageHeight);
					currentStage.inited(this, nextStageArgs);
					currentStage.onAdded(this, stageWidth, stageHeight);
					currentStage.startTransitionIn();
				
			    	nextStage		= null;
					nextStageClass	= null;
					nextStageArgs	= null;
					
			    	System.gc();
					Thread.yield();	
		    	}
				System.out.println("Memory Status : " + (Runtime.getRuntime().freeMemory()/1024) + "/" + (Runtime.getRuntime().totalMemory()/1024) + "(K byte)");	
		    	
	    	}
		}
	}
	
//	--------------------------------------------------------------------------------
//	control and input
	
	synchronized private void queryKey() 
	{
		// 用收集到的按键替换查询的按键,并清理收集到的按键,重新监测下一帧的情况
		
		keystate_query_down.clear();
		keystate_query_down.putAll(keystate_down);
		keystate_query_up.clear();
		keystate_query_up.putAll(keystate_up);
		
		mousestate_query_down.clear();
		mousestate_query_down.putAll(mousestate_down);
		mousestate_query_up.clear();
		mousestate_query_up.putAll(mousestate_up);
		
		mousewheel_query.clear();
		mousewheel_query.putAll(mousewheel);
		
		keystate_down.clear();
		keystate_up.clear();
		mousestate_down.clear();
		mousestate_up.clear();
		mousewheel.clear();
	}
	
	@Override
	public boolean isOnTextInput(int keycode) {
		if (getStage() != null) {
			DisplayObject o = getStage();
			while (o != null) {
				if (o instanceof TextInputer) {
					if (((TextInputer)o).isInput(keycode)) {
						return true;
					}
				}
				if (o instanceof DisplayObjectContainer) {
					o = ((DisplayObjectContainer)o).getFocus();
				} else {
					break;
				}
			}
		}
		return false;
	}
	
	/**
	 * 检测当前帧键有没有被按住
	 * @param keycode
	 * @return
	 */
	synchronized public boolean isKeyHold(int ... keycode) 
	{
		for (int k : keycode) 
			if (keystate.get(k)!=null) 
				return true;
		return false;
	}
	
	/**
	 * 检测当前帧键有没有被按下
	 * @param keycode
	 * @return
	 */
	synchronized public boolean isKeyDown(int ... keycode)
	{
		for (int k : keycode) 
			if (keystate_query_down.get(k)!=null) 
				return true;
		return false;
	}
	
	/**
	 * 检测当前帧键有没有被松开
	 * @param keycode
	 * @return
	 */
	synchronized public boolean isKeyUp(int ... keycode)
	{
		for (int k : keycode) 
			if (keystate_query_up.get(k)!=null) 
				return true;
		return false;
	}
	
	/**
	 * 检测当前帧被按下的键的个数
	 * @return
	 */
	synchronized public int getDownKeyCount()
	{
		int count = keystate_query_down.size();
		return count;
	}
	
	/**
	 * 检测当前帧被按下的键的个数
	 * @return
	 */
	synchronized public int getHoldKeyCount()
	{
		int count = keystate.size();
		return count;
	}
	
	/**
	 * 检测当前帧被松开的键的个数
	 * @return
	 */
	synchronized public int getUpKeyCount()
	{
		int count = keystate_query_up.size();
		
		return count;
	}
	
	/**
	 *  检测当前帧鼠标有没有被按住
	 * @param button
	 * @return
	 */
	synchronized public boolean isMouseHold(int ... button) 
	{
		for (int b : button) 
			if (mousestate.get(b)!=null) 
				return true;
		return false;
	}
	
	/**
	 *  检测当前帧鼠标有没有被按下
	 * @param button
	 * @return
	 */
	synchronized public boolean isMouseDown(int ... button)
	{
		for (int b : button) 
			if (mousestate_query_down.get(b)!=null) 
				return true;
		return false;
	}
	
	@Override
	synchronized public boolean isMouseContinuous(long freeze_time, int... button) {
		for (int b : button) {
			if (mousestate.get(b)!=null) {
				Long		prve_time	= mousestate_prev_down_time.get(b);
				if (prve_time != null) {
					if (System.currentTimeMillis() - prve_time > freeze_time) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 在上次鼠标被按下后多少时间，检查鼠标被按下
	 * @param time 如果(现在时间-上次按下时间 < time)则进行判断
	 * @param button
	 * @return
	 */
	synchronized public boolean isMouseDoubleDown(long time, int ... button){
		for (int b : button) {
			if (mousestate_query_down.get(b)!=null) {
				Long		prve_time	= mousestate_prev_down_time.get(b);
				MouseEvent	prve_pos	= mousestate_prev_down_pos.get(b);
				if (prve_time != null && prve_pos!=null) {
					if (System.currentTimeMillis() - prve_time < time && 
							prve_pos.getX()==mousex && 
							prve_pos.getY()==mousey) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 *  检测当前帧鼠标有没有被松开
	 * @param button
	 * @return
	 */
	synchronized public boolean isMouseUp(int ... button) 
	{
		for (int b : button) 
			if (mousestate_query_up.get(b)!=null) 
				return true;
		return false;
	}
	
	/**
	 *  检测当前帧鼠标滚轮有没有向上滚
	 * @param button
	 * @return
	 */
	synchronized public boolean isMouseWheelUP() {
		return mousewheel_query.get(-1)!=null;
	}
	
	/**
	 *  检测当前帧鼠标滚轮有没有向下滚
	 * @param button
	 * @return
	 */
	synchronized public boolean isMouseWheelDown() {
		return mousewheel_query.get(1)!=null;
	}
	
	/**
	 * 得到鼠标指针在stage的位置
	 * @param button
	 * @return
	 */
	synchronized public int getMouseX() {
		return mousex;
	}
	
	/**
	 * 得到鼠标指针在stage的位置
	 * @param button
	 * @return
	 */
	synchronized public int getMouseY() {
		return mousey;
	}
	
	void fixMouse() 
	{
		mousex = Math.max(mousex, 0);
		mousex = Math.min(mousex, stageWidth-1);
		
		mousey = Math.max(mousey, 0);
		mousey = Math.min(mousey, stageHeight-1);
	}
	
	
	void poolEvent(Event<?> event){
		if (currentStage!=null) {
			currentStage.onPoolEvent(event);
		}
	}
	
	// key events
	synchronized public void keyPressed(KeyEvent e) {
		if (!isOnTextInput(e.getKeyCode())) {
			keystate_down.put(e.getKeyCode(), e);
		}
		boolean poll = true;
		if (keystate.containsKey(e.getKeyCode())) {
			keystate_down.remove(e.getKeyCode());
			poll = false;
		} else {
			keystate.put(e.getKeyCode(), e);
		}
		if (poll) {
			poolEvent(new com.g2d.display.event.KeyEvent(
					e, com.g2d.display.event.KeyEvent.EVENT_KEY_DOWN));
		}
	}
	synchronized public void keyReleased(KeyEvent e) {
		if (!isOnTextInput(e.getKeyCode())) {
			keystate_up.put(e.getKeyCode(), e);
		}
		keystate.remove(e.getKeyCode());
		poolEvent(new com.g2d.display.event.KeyEvent(
				e, com.g2d.display.event.KeyEvent.EVENT_KEY_UP));
	}
	synchronized public void keyTyped(KeyEvent e) {
		poolEvent(new com.g2d.display.event.KeyEvent(
				e, com.g2d.display.event.KeyEvent.EVENT_KEY_TYPED));
	}
	
	// mouse events
	synchronized public void mouseClicked(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);
		fixMouse() ;
		//poolEvent(new com.cell.g2d.display.event.MouseEvent(e));
	}
	synchronized public void mouseEntered(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);
		fixMouse() ;
		//poolEvent(new com.cell.g2d.display.event.MouseEvent(e));
	}
	synchronized public void mouseExited(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);
		fixMouse() ;
		//poolEvent(new com.cell.g2d.display.event.MouseEvent(e));
	}
	synchronized public void mousePressed(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);		
		fixMouse() ;
		mousestate_down.put(e.getButton(), e);
		mousestate.put(e.getButton(), e);
		mousestate_prev_down_time.put(e.getButton(), System.currentTimeMillis());
		mousestate_prev_down_pos.put(e.getButton(), e);
		poolEvent(new com.g2d.display.event.MouseEvent(
				e.getButton(), e.getClickCount(), com.g2d.display.event.MouseEvent.EVENT_MOUSE_DOWN));
//		System.out.println(e);
	}
	synchronized public void mouseReleased(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);
		fixMouse() ;
		mousestate_up.put(e.getButton(), e);
		mousestate.remove(e.getButton());
		poolEvent(new com.g2d.display.event.MouseEvent(
				e.getButton(), e.getClickCount(), com.g2d.display.event.MouseEvent.EVENT_MOUSE_UP));
		//System.out.println(e);
	}
	synchronized public void mouseDragged(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);
		fixMouse() ;
		//poolEvent(new com.cell.g2d.display.event.MouseEvent(e, com.cell.g2d.display.event.MouseEvent.EVENT_MOUSE_DRAGGED));
		//System.out.println(e);
	}
	synchronized public void mouseMoved(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);
		fixMouse() ;
		//poolEvent(new com.cell.g2d.display.event.MouseEvent(e, com.cell.g2d.display.event.MouseEvent.EVENT_MOUSE_MOVED));
		//System.out.println(e);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation()>0) {
			mousewheel.put(1 , e);
		}else if (e.getWheelRotation()<0) {
			mousewheel.put(-1, e);
		}
		poolEvent(new com.g2d.display.event.MouseWheelEvent(
				e.getButton(), e.getWheelRotation()));
		//System.out.println(e);
	}
	
//	--------------------------------------------------------------------------------

//	boolean is_focused;
	
	public void focusGained(FocusEvent e) {
//		System.out.println(e.paramString());
		if (currentStage!=null) {
			currentStage.onFocusGained();
			currentStage.focuseClean(this);
		}
	}
	
	public void focusLost(FocusEvent e) {
//		System.out.println(e.paramString());
		if (currentStage!=null) {
			currentStage.onFocusLost();
			currentStage.focuseClean(this);
		}
	}


//	@Override
//	public CursorG2D getCursorG2D() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
//	--------------------------------------------------------------------------------

}

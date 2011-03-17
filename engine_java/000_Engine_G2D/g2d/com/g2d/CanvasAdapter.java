package com.g2d;

import java.applet.Applet;
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

import com.cell.exception.NotImplementedException;
import com.g2d.display.AnimateCursor;
import com.g2d.display.Stage;
import com.g2d.display.event.Event;
import com.g2d.display.event.TextInputer;
import com.g2d.editor.DisplayObjectEditor;

public class CanvasAdapter implements 
com.g2d.display.Canvas,
KeyListener, 
MouseListener, 
MouseMotionListener, 
MouseWheelListener, 
FocusListener
{

//	--------------------------------------------------------------------------------
	
	final private CanvasContainer		parent;
	final private Component				container_;
	final private Component 			component;

	private Applet						owner_applet;
	
	private boolean 					game_exit		= false;
	
//	java swing/awt refre
	private Font 						defaultFont 	= new Font("song", Font.PLAIN, 13);
//	Container 							parentFrame;
//	private boolean						is_root_applet	= false;
	private AnimateCursor				defaultCursor;
	private AnimateCursor				nextCursor;
	
	
//	game stage
	private int 						stageWidth 		= 800;
	private int 						stageHeight 	= 600;
	private Stage 						currentStage 	= null;
	
	private Class<?>					nextStageClass 	= null;
	private Object[]					nextStageArgs;
	private Stage						nextStage;
	
//	graphics and updating system
	transient private VolatileImage		vm_buffer;
//	transient private Graphics2D		vm_buffer_g2d;
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
//	append window
	private Window 						append_window;
	private Component					internal_frame;
	
//	game hook
//	transient Vector<Runnable>			window_close_hooks	= new Vector<Runnable>();
	
//	game event
	
	
//	private ConcurrentLinkedQueue<Event<?>>		event_queue 		= new ConcurrentLinkedQueue<Event<?>>();
	
	
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


	
//	--------------------------------------------------------------------------------------------------------------------------
//	construction
	
	public CanvasAdapter(CanvasContainer container)
	{
		this(container, 
				container.getComponent().getWidth(), 
				container.getComponent().getHeight());
	}
	
	
	@SuppressWarnings("unchecked")
	public CanvasAdapter(CanvasContainer container, int stage_width, int stage_height)
	{
//		super(null, true);
		parent			= container;
		container_		= container.getContainer();
		component		= container.getComponent();
		
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
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

		component.setSize(stageWidth, stageHeight);
		component.setMinimumSize(new Dimension(100,100));
	
	}
	
	public Component getParent(){
		return getComponent();
	}
	
	public Component getComponent() {
		return component;
	}
	
	public boolean isExit() {
		return game_exit;
	}
	
	public void exit() {
		game_exit = true;
		if (currentStage!=null) {
			currentStage.onRemoved(this);
		}
		destory_vm_buffer();
	}
	
	public boolean isFocusOwner() {
		return component.isFocusOwner();
	}
	
	/**
	 * 是否由Applet进入游戏的
	 * @return
	 */
	public boolean isRootApplet() {
		return owner_applet != null;
	}

	public Applet getApplet() {
		return owner_applet;
	}
	
	public void setApplet(Applet applet) {
		this.owner_applet = applet;
	}
	
	public Image getVMBuffer() {
		return vm_buffer;
	}
	
	/**
	 * 设置默认鼠标指针图形
	 * @param cursor
	 */
	public void setDefaultCursor(AnimateCursor cursor)
	{
		defaultCursor = cursor;
	}
	
//	/**
//	 * 设置鼠标指针
//	 * @param cursor
//	 */
//	public void setCursor(AnimateCursor cursor){
//		nextCursor = cursor;
//	}
	
	/**
	 * 设置默认渲染子体
	 * @param font
	 */
	public void setDefaultFont(Font font) {
		defaultFont = font;
		Tools.setDefaultFont(font);
	}

	public Font getDefaultFont() {
		return defaultFont;
	}
	
//	public void setCursor(Cursor cursor) {
//		nextCursor = new AnimateCursor(cursor);
//	}
//	
//	/**
//	 * 设置鼠标悬停
//	 * @param text
//	 */
//	public void setTip(AttributedString text) {
//		if (currentStage!=null) {
//			currentStage.setTip(text);
//		}
//	}
//	
//	/**
//	 * 设置鼠标悬停
//	 * @param text
//	 */
//	public void setTip(String text) {
//		if (currentStage!=null) {
//			currentStage.setTip(text);
//		}
//	}
	
//	--------------------------------------------------------------------------------
//	substage manag

//	public void addCloseHook(Runnable e) {
//		window_close_hooks.add(e);
//	}
	
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
	
	/**
	 * 切换场景
	 * @param stageName
	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
	 */
	@SuppressWarnings("unchecked")
	public void changeStage(String stageName, Object ... args) {
		try {
			this.changeStage((Class<Stage>) Class.forName(stageName), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 切换场景
	 * @param stageClass
	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
	 */
	public void changeStage(Class<?> stageClass, Object ... args){
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
		currentStage = stage;
		currentStage.onAdded(this, stageWidth, stageHeight);
		currentStage.startTransitionIn();
		currentStage.inited(this, nextStageArgs);
	}
	
	/**
	 * 设置场景象素大小
	 * @param width
	 * @param height
	 */
	public void setStageSize(int width, int height)
	{
		synchronized (this)
		{
			destory_vm_buffer();
			stageWidth = width;
			stageHeight = height;
		}
	}
	
	public int getStageWidth() {
		return stageWidth;
	}
	
	public int getStageHeight() {
		return stageHeight;
	}
	
	public int getWidth() {
		return component.getWidth();
	}
	
	public int getHeight() {
		return component.getHeight();
	}
	
	/**
	 * 根据parentFrame的大小来设置stage，使得stage刚好包容在parentFrame里
	 * @param parentFrame
	 */
	public void fillStageSize(Container parentFrame)
	{
		synchronized (this) 
		{
			if (parentFrame!=null)
			{
				destory_vm_buffer();
				stageWidth  = parentFrame.getWidth() - (parentFrame.getInsets().left+parentFrame.getInsets().right);
				stageHeight = parentFrame.getHeight()- (parentFrame.getInsets().left+parentFrame.getInsets().right);
				component.setSize(stageWidth, stageHeight);
			}
		}
	}
	
	public Stage getStage() {
		return currentStage;
	}
	
//	--------------------------------------------------------------------------------
	
	/**
	 * 设置一个Window，贴在canvas的右边
	 * @param append
	 */
	public void setAppendWindow(Window append) 
	{
		if (append!=null) {
			Point p = component.getLocationOnScreen();
			append.setLocation(p.x + getWidth(), p.y);
			append.setVisible(true);
		}
		
		if (append_window!=null && append!=append_window) {
			if (append_window instanceof DisplayObjectEditor<?>) {
				if (((DisplayObjectEditor<?>)append_window).isAutoStick()) {
					append_window.setVisible(false);
				}
			}
		}
		
		append_window = append;
	}
	
	/**
	 * 得到贴在canvas的右边的Window
	 */
	public Window getAppendWindow() 
	{
		return append_window;
	}
	
	/**
	 * 设置一个Window，贴在canvas的右边
	 */
	protected void updateAppendWindow() 
	{
		if (append_window!=null) {
			if (append_window instanceof DisplayObjectEditor<?>){
				if (((DisplayObjectEditor<?>)append_window).isAutoStick()) {
					Point p = component.getLocationOnScreen();
					append_window.setLocation(p.x + getWidth(), p.y);
				}
			}
		}
	}

	/**
	 * 显示在低级stage中的高级ui
	 * @param frame
	 */
	public void setInternalFrame(Component frame) 
	{			
		if (container_ instanceof Container)
		{			
			if (internal_frame!=null) {
				((Container)container_).remove(internal_frame);
			}
			
			internal_frame = frame;
			
			if (internal_frame!=null) {
				((Container)container_).add(internal_frame);
//				internal_frame.requestFocus();
				System.out.println("setInternalFrame : " + internal_frame.getClass().getName());
				container_.requestFocus();
			}
			
		}
		else
		{
			System.err.println("setInternalFrame : \"" + container_.getClass().getName() + "\" is not a container ! ");
			throw new NotImplementedException();
		}
		
	}
	
	/**
	 * 当前在低级stage中显示的高级ui
	 * @return
	 */	
	public Component getInternalFrame()
	{
		return internal_frame;
	}
	
//	--------------------------------------------------------------------------------
//	update and transition

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
//	--------------------------------------------------------------------------------
//	game
	
	void destory_vm_buffer() {
		if (vm_buffer != null) {
			vm_buffer.flush();
			vm_buffer = null;
			System.out.println("CanvasAdapter : destory_vm_buffer");
		}
//		if (vm_buffer_g2d!=null) {
//			vm_buffer_g2d.dispose();
//			vm_buffer_g2d = null;
//		}
	}
	
	void create_vm_buffer(GraphicsConfiguration gc) {
		vm_buffer 		= gc.createCompatibleVolatileImage(stageWidth, stageHeight, Transparency.OPAQUE);
//		vm_buffer_g2d 	= (Graphics2D)vm_buffer.getGraphics();
		System.out.println("CanvasAdapter : create_vm_buffer");
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		destory_vm_buffer();
		System.out.println("CanvasAdapter : finalize");
	}
	

//	public void repaint_game() {
//		component.repaint(0, 0, 0, getWidth(), getHeight());
//	}
	
	public void update(GraphicsConfiguration gc) 
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
			
			if (vm_buffer == null)
			{
				create_vm_buffer(gc);
			}
			else if (vm_buffer.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) 
			{
				destory_vm_buffer();
				create_vm_buffer(gc);
			}
			
			size_rate_x = (float)stageWidth / getWidth();
			size_rate_y = (float)stageHeight/ getHeight();
			
			nextCursor = null;
			
			synchronized (this)
			{
				Graphics2D g2d = vm_buffer.createGraphics();
				
				try{
					Tools.setDefaultFont(defaultFont);
					g2d.setFont(defaultFont);
				
					paint_stage(g2d);
					
					if (internal_frame!=null) {
						g2d.scale(size_rate_x, size_rate_y);
						g2d.clip(internal_frame.getBounds());
						parent.superPaint(g2d);
					}
				} finally {
					g2d.dispose();
				}

//				g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), null);
			}

			if (nextCursor!=null) {
				component.setCursor(nextCursor.getCursor());
				nextCursor.update();
			}else if (defaultCursor!=null){
				component.setCursor(defaultCursor.getCursor());
				defaultCursor.update();
			}else {
				component.setCursor(Cursor.getDefaultCursor());
			}
			
			double updatetime = System.currentTimeMillis() - prewUpdateTime;
			if (updatetime>0){
				fps = (int)(1000.0D / updatetime);
			}
			prewUpdateTime = System.currentTimeMillis();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	void paint_stage(Graphics2D g)
	{
		if (currentStage!=null)
		{
			currentStage.onUpdate(this, stageWidth, stageHeight);
			currentStage.onRender(g);

			nextCursor = currentStage.getCursor();
			
			if (!isFocusOwner()) {
				currentStage.renderLostFocus(g);
			}
		}
		
		
		if (nextStageClass!=null || nextStage!=null)
		{
			// clear current stage
	    	if(currentStage!=null)
    		{
	    		if (!currentStage.isTransition())
	    		{
	    			currentStage.onRemoved(this);
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
	
	private boolean isPickedKeyInputer(int[] chars) {
		if (getStage()!=null && getStage().getMousePickedObject() instanceof TextInputer) {
			TextInputer inputer = (TextInputer)getStage().getMousePickedObject();
			return inputer.isInput(chars);
		}
		return false;
	}
	
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
	public boolean isOnTextInput() {
		if (getStage()!=null) {
			if (getStage().getFocusLeaf() instanceof TextInputer) {
				return true;
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
		if (isPickedKeyInputer(keycode)){
			return false;
		}
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
		if (isPickedKeyInputer(keycode)){
			return false;
		}
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
		if (isPickedKeyInputer(keycode)){
			return false;
		}
		for (int k : keycode) 
			if (keystate_query_up.get(k)!=null) 
				return true;
		return false;
	}
	
	private static final int[] KEYCODES_4_TEMP = new int[]{};
	
	/**
	 * 检测当前帧被按下的键的个数
	 * @return
	 */
	synchronized public int getDownKeyCount()
	{
		if (isPickedKeyInputer(KEYCODES_4_TEMP))
			return 0;
		
		int count = keystate.size();
		
		return count;
	}
	
	/**
	 * 检测当前帧被松开的键的个数
	 * @return
	 */
	synchronized public int getUpKeyCount()
	{
		if (isPickedKeyInputer(KEYCODES_4_TEMP))
			return 0;		
		
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
		keystate_down.put(e.getKeyCode(), e);
		keystate.put(e.getKeyCode(), e);
		poolEvent(new com.g2d.display.event.KeyEvent(e, com.g2d.display.event.KeyEvent.EVENT_KEY_DOWN));
	}
	synchronized public void keyReleased(KeyEvent e) {
		keystate_up.put(e.getKeyCode(), e);
		keystate.remove(e.getKeyCode());
		poolEvent(new com.g2d.display.event.KeyEvent(e, com.g2d.display.event.KeyEvent.EVENT_KEY_UP));
	}
	synchronized public void keyTyped(KeyEvent e) {
		poolEvent(new com.g2d.display.event.KeyEvent(e, com.g2d.display.event.KeyEvent.EVENT_KEY_TYPED));
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
		poolEvent(new com.g2d.display.event.MouseEvent(e, com.g2d.display.event.MouseEvent.EVENT_MOUSE_DOWN));
//		System.out.println(e);
	}
	synchronized public void mouseReleased(MouseEvent e) {
		mousex = (int)(e.getX() * size_rate_x);
		mousey = (int)(e.getY() * size_rate_y);
		fixMouse() ;
		mousestate_up.put(e.getButton(), e);
		mousestate.remove(e.getButton());
		poolEvent(new com.g2d.display.event.MouseEvent(e, com.g2d.display.event.MouseEvent.EVENT_MOUSE_UP));
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
		poolEvent(new com.g2d.display.event.MouseWheelEvent(e));
		//System.out.println(e);
	}
	
//	--------------------------------------------------------------------------------

//	boolean is_focused;
	
	public void focusGained(FocusEvent e) {
//		System.out.println(e.paramString());
		if (currentStage!=null) {
			currentStage.onFocusGained(e);
			currentStage.focuseClean(this);
		}
	}
	
	public void focusLost(FocusEvent e) {
//		System.out.println(e.paramString());
		if (currentStage!=null) {
			currentStage.onFocusLost(e);
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

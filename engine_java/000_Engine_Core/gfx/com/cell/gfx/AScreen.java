
package com.cell.gfx;

import java.util.Stack;
import java.util.Vector;

import com.cell.CObject;
import com.cell.CUtil;


/**
 * SubScreen</br>
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
abstract public class AScreen extends CObject
{
	private static IGfxBridge	GfxAdapter;

	public static void initGfxSystem(IGfxBridge gfxAdapter) {
		GfxAdapter = gfxAdapter;
		System.out.println(
				"AScreen : Gfx System initialized !\n" + 
					"\tIGfxBridge = " + GfxAdapter + "\n" +
					"");
	}
	
	public static IGfxBridge getGfxAdapter() {
		if (GfxAdapter == null) {
			if (CObject.getAppBridge() instanceof IGfxBridge) {
				initGfxSystem((IGfxBridge)CObject.getAppBridge());
			}
		}
		return GfxAdapter;
	}
	
	
//	-------------------------------------------------------------------------------------------------------
//	game refer
	
	/**GameOver Flag*/
	volatile static public boolean ExitGame = false;
	
	/**Current AScreen Instance*/
	volatile static private AScreen CurSubScreen = null;
	
	/**Current IGraphics Instance*/
	volatile static public IGraphics CurGraphics = null;
	
	/**Screen Width*/
	static public int SCREEN_WIDTH = 176;
	/**Screen Height*/
	static public int SCREEN_HEIGHT = 208;
	/**Screen Width/2*/
	static public int SCREEN_HCENTER = 176/2;
	/**Screen Height/2*/
	static public int SCREEN_VCENTER = 208/2;

	/**Delay per Frame FPS=30 FrameDelay = 1000/30 = 33*/
	static public int FrameDelay = 25;//default fps = 50
	
	/**Game Render Enable*/
	static public boolean RenderEnable = true;
	/**Screen Transition Enable*/
	static public boolean TransitionEnable = true;

	/**Game Key Enable*/
	static public boolean KeyEnable = true;
	
	/**Game Logic Enable*/
	static private boolean LogicEnable = true;

//	------------------------------------------------------------------------------------------------------	

	static public int 		FrameImageDrawed	= 0;

	static public boolean	IsDebug				= false;
	
	static private int 		Timer 				= 1;

	
	/**
	 * tick frame timer
	 */
	static public void tickTimer() {
		Timer++;
	}
	/**
	 * reset frame timer
	 */
	static public void resetTimer() {
		Timer = 1;
	}
	/**
	 * get current frame timer
	 * @return 
	 */
	static public int getTimer() {
		return Timer;
	}
	
//	------------------------------------------------------------------------------------------------------	

	static public void clearCurScreen(){
		if (CurSubScreen!=null){
			try{
				CurSubScreen.notifyDestroy();
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		CurSubScreen = null;
	}
	
	static public AScreen getCurScreen(){
		return CurSubScreen;
	}
	
//	------------------------------------------------------------------------------------------------------	

	private boolean m_IsLoading = true;
	
	/**
	 * canvas notifyHide call back </br>
	 */
	public void notifyPause(){}
	
	/**
	 * canvas notifyShow call back</br>
	 */
	public void notifyResume(){}
	
	
	public void notifyRenderPause(IGraphics g){}
	
	/***
	 * render loading
	 * @param g
	 * @return is loading complete
	 */
	public boolean notifyRenderLoading(IGraphics g){return true;}
	
	/**
	 * main game logic call back</br>
	 */
	abstract public void notifyLogic();
	/**
	 * main render call back : canvas paint call back</br>
	 * @param g 
	 */
	abstract public void notifyRender(IGraphics g);
	
	/**
	 * main release call back : call back when screen is disposing </br>
	 * @param g 
	 */
	abstract public void notifyDestroy();
	//--------------------------------------------------------------------------------------------------------------------
	
	/**Current Time Transition Flag*/
	static private boolean 	Transition 			= false;
	
	static private int 		TransitionMaxTime 	= 0 ;
	static private int 		TransitionTime 		= 0 ;
	static private boolean 	TransitionIn 		= false;
	static private boolean 	TransitionOut 		= false;
	
	static private String 	TransitionText[] 	= new String[]{"Loading..."};
	static private IImage	TransitionImage		= null;
	static private IImage	TransitionTagImage	= null;
	static private String	TipText[]			= null;
	static private int 		tipIndex 			= 0;
	//
	
	static public int		TransitionType			= 0;
	final static public int TRANSITION_STRIP_H		= 0;
	final static public int TRANSITION_ALPHA		= 1;
	
	// type refer
	static public int 		TransitionStripCellW 		= 16;
	static public int		TransitionStripDelta 		= 2;

	static public int		TransitionAlphaColor		= 0;
	static public int		TransitionAlphaMaxTime		= 16;
	
	
	static public void setTransitionImage(IImage img) {
		TransitionImage = img;
	}
	
	static public IImage getTransistionImage(){
		return TransitionImage;
	}
	
	static public void setTransitionTagImage(IImage img){
		TransitionTagImage = img;
	}
	
	static public IImage getTransistionTagImage(){
		return TransitionTagImage;
	}
	
	static public void setTipText(String[] tag){
		TipText = tag;
	}
	
	static public boolean isHaveTipText(){
		return TipText!=null&&TipText.length>1;
	}
	
	/**
	 * If Current Screen is Transition
	 * @return 
	 */
	static public boolean isTransition(){
		return Transition;
	}
	
	/**
	 * Set Current In TransitionIn
	 */
	static private void setTransitionIn(){
		Transition = true;
		if (TransitionType==TRANSITION_STRIP_H){
			TransitionMaxTime = SCREEN_WIDTH / TransitionStripCellW + TransitionStripCellW/TransitionStripDelta;
		}else{
			TransitionMaxTime = TransitionAlphaMaxTime;
		}
		TransitionTime = 0;
		TransitionIn = true;
		TransitionOut = false;
	}
	/**
	 * Set Current In TransitionOut
	 */
	static private void setTransitionOut(){
		Transition = true;
		if (TransitionType==TRANSITION_STRIP_H){
			TransitionMaxTime = SCREEN_WIDTH / TransitionStripCellW + TransitionStripCellW/TransitionStripDelta;
		}else{
			TransitionMaxTime = TransitionAlphaMaxTime;
		}
		TransitionTime = 0;
		TransitionIn = false;
		TransitionOut = true;
	}
	
	/**
	 * @return 
	 */
	static private boolean isTransitionOut(){
		if(AScreen.TransitionEnable==false)return true;
		if(!TransitionOut || TransitionTime>TransitionMaxTime)return true;
		return false;
	}
	/**
	 * @return 
	 */
	static private boolean isTransitionIn(){
		if(AScreen.TransitionEnable==false)return true;
		if(!TransitionIn || TransitionTime>TransitionMaxTime)return true;
		return false;
	}
	
	/**
	 * Render Transition Effect 
	 * @param g
	 * @param w 
	 * @param h 
	 */
	static private void Transition(IGraphics g,int w,int h)
	{
		w += TransitionStripCellW*2;
		
		TransitionTime++;
		
		if(TransitionIn && TransitionTime>TransitionMaxTime){
			Transition = false;
		}
		if(Transition==false){
			return;
		}
		
		//System.out.println("Transition Screen : " + TransitionTime);
		switch(TransitionType)
		{
		case TRANSITION_STRIP_H:
		{
			g.setClip(0, 0, w, h);
			g.setColor(0xff000000);

			int count = w/TransitionStripCellW+1;
			if(TransitionIn){// black -> clean
				for(int i=0;i<count;i++){
					g.fillRect(
							i*TransitionStripCellW , 0, 
							TransitionStripCellW-(TransitionTime-i)*TransitionStripDelta, h);
				}
			}
			if(TransitionOut){//clean -> black
				for(int i=0;i<count;i++){
					g.fillRect(
							i*TransitionStripCellW - (TransitionTime-i)*TransitionStripDelta, 0, 
							(TransitionTime-i)*TransitionStripDelta, h);
				}
			}	
		}
			break;
		case TRANSITION_ALPHA:
		{
			float rate = (float)Math.min((TransitionTime+1), TransitionMaxTime) / TransitionMaxTime;
			
			if(TransitionIn){
				g.setClip(0, 0, w, h);
				g.setColor(CUtil.toColor((int)(0xff * (1-rate)), TransitionAlphaColor));
				g.fillRect(0, 0, w, h);
			}
			if(TransitionOut){
				g.setClip(0, 0, w, h);
				g.setColor(CUtil.toColor((int)(0xff * (rate)), TransitionAlphaColor));
				g.fillRect(0, 0, w, h);
			}
			
			
			
			
			
		}
			break;
			
		}
	
		
		if( (TransitionIn && TransitionTime<=1) || 
			(TransitionOut && TransitionTime>=TransitionMaxTime-1))
		{
			// draw splash
			if(TransitionImage!=null)
			{
				g.drawImage(
						TransitionImage, 
						SCREEN_HCENTER - TransitionImage.getWidth()/2,
						SCREEN_VCENTER - TransitionImage.getHeight()/2,
						0);
				if (TransitionTagImage!=null)
				{
					int x = SCREEN_HCENTER - TransitionTagImage.getWidth()/2;
					int y = SCREEN_VCENTER - TransitionImage.getHeight()/2+ 180;
					g.drawImage(TransitionTagImage, x, y, 0);
					if (TipText!=null&&TipText.length>0){
						drawString(g, TipText[tipIndex],
								SCREEN_WIDTH/2 - g.getStringWidth(TipText[tipIndex])/2 , 
								y + (TransitionTagImage.getHeight() - g.getStringHeight())/2,
								0xffffffff);
					}
				}
			}
			// draw string
			else if (TransitionText != null)
			{
				int th = (g.getStringHeight() + 1) * TransitionText.length;
		    	int ty = SCREEN_HEIGHT/2 - th/2;
		    	
		    	for(int i=0;i<TransitionText.length;i++)
		    	{
		            drawString(g, TransitionText[i], 
		        			SCREEN_WIDTH/2 - g.getStringWidth(TransitionText[i])/2 , 
		        			ty + (g.getStringHeight() + 1) * i , 
		        			0xffffffff);
		    	}
			}
		}
		

		
	}
	
//	---------------------------------------------------------------------------------
	
	volatile static private Class NextScreenClassName = null;

	/**
	 * Change Current To NextScreen
	 * @param screenClassName Your extends AScreen Class Name
	 */
	static public void ChangeSubScreen(Class screenClassName) 
	{
		TransitionText = new String[]{"Loading..."};
		NextScreenClassName = screenClassName;
		KeyEnable = false;
		LogicEnable = false;
		if (TipText!=null&&TipText.length>0){
			tipIndex = CUtil.getRandom(0, TipText.length-1);
		}
		setTransitionOut();
	}
	
	/**
	 * Change Current To NextScreen
	 * @param screenClassName Your extends AScreen Class Name
	 * @param transitionText Show Text On Transition Screen SingleLine
	 */
	static public void ChangeSubScreen(Class screenClassName,String transitionText) 
	{
		TransitionText = new String[]{transitionText};
		NextScreenClassName = screenClassName;
		KeyEnable = false;
		LogicEnable = false;
		if (TipText!=null&&TipText.length>0){
			tipIndex = CUtil.getRandom(0, TipText.length-1);
		}
		setTransitionOut();
	}
	
	/**
	 * Change Current To NextScreen
	 * @param screenClassName Your extends AScreen Class Name
	 * @param transitionText Show Text On Transition Screen MultiLine
	 */
	static public void ChangeSubScreen(Class screenClassName,String[] transitionText) 
	{
		TransitionText = transitionText;
		NextScreenClassName = screenClassName;
		KeyEnable = false;
		LogicEnable = false;
		if (TipText!=null&&TipText.length>0){
			tipIndex = CUtil.getRandom(0, TipText.length-1);
		}
		setTransitionOut();
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Change Current To NextScreen
	 * @param screenClassName Your extends AScreen Class Name
	 */
	static public void ChangeSubScreen(String screenClassName) 
	{
		try{
			NextScreenClassName = Class.forName(screenClassName);
			TransitionText = new String[]{"Loading..."};
			KeyEnable = false;
			LogicEnable = false;
			if (TipText!=null&&TipText.length>0){
				tipIndex = CUtil.getRandom(0, TipText.length-1);
			}
			setTransitionOut();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Change Current To NextScreen
	 * @param screenClassName Your extends AScreen Class Name
	 * @param transitionText Show Text On Transition Screen SingleLine
	 */
	static public void ChangeSubScreen(String screenClassName,String transitionText) 
	{
		try {
			NextScreenClassName = Class.forName(screenClassName);
			TransitionText = new String[]{transitionText};
			KeyEnable = false;
			LogicEnable = false;
			if (TipText!=null&&TipText.length>0){
				tipIndex = CUtil.getRandom(0, TipText.length-1);
			}
			setTransitionOut();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Change Current To NextScreen
	 * @param screenClassName Your extends AScreen Class Name
	 * @param transitionText Show Text On Transition Screen MultiLine
	 */
	static public void ChangeSubScreen(String screenClassName,String[] transitionText) 
	{
		try {
			NextScreenClassName = Class.forName(screenClassName);
			TransitionText = transitionText;
			KeyEnable = false;
			LogicEnable = false;
			if (TipText!=null&&TipText.length>0){
				tipIndex = CUtil.getRandom(0, TipText.length-1);
			}
			setTransitionOut();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static public void ChangeSubScreen(String screenClassName, IImage image) 
	{
		try {
			NextScreenClassName = Class.forName(screenClassName);
			TransitionImage = image;
			KeyEnable = false;
			LogicEnable = false;
			if (TipText!=null&&TipText.length>0){
				tipIndex = CUtil.getRandom(0, TipText.length-1);
			}
			setTransitionOut();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
//	--------------------------------------------------------------------------------------------------------------

	/**
	 * Check when ChangeSubScreen Event are happen
	 */
	static private void TryChangeSubSreen()
	{
	    if (NextScreenClassName!=null && isTransitionOut())
	    {
	    	try
	    	{
	    		if(CurSubScreen!=null)
	    		{
	    			CurSubScreen.notifyDestroy();
			    	CurSubScreen = null;
			    	System.gc();
					Thread.yield();
	    		}
	    		
	    		System.out.println(
						"\n"+
						"ChangeSubScreen -> "+NextScreenClassName.getName()+"\n"+
						"Memory Status : "+ 
						(Runtime.getRuntime().freeMemory()/1024) + 
						"/" + 
						(Runtime.getRuntime().totalMemory()/1024) +
						"(K byte)" +
						"\n"
						);
	    		
				CurSubScreen = (AScreen)(NextScreenClassName.newInstance());
				System.gc();
				Thread.yield();
				
				KeyEnable = true;
				LogicEnable = true;
		    	setTransitionIn();
		    	NextScreenClassName = null;
		    	
	    	}
	    	catch (Exception e) 
	    	{
	    		e.printStackTrace();
				ExitGame = true;
			}
	    	
		}
	}

//	-------------------------------------------------------------------------------------------------------
//	advance key mask
	private static boolean[] VKDState;
	private static boolean[] VKUState;
	private static boolean[] VKHState;
	

//	-------------------------------------------------------------------------------------------------------
//	key refer
	
	/**Key Input Enable*/
	static private boolean KeyInput 	= false;
	
	// key constance
	static final public int KEY_ANY 	= 0xffffffff;
	static final public int KEY_0 		= 0x00000001;
	static final public int KEY_1 		= 0x00000002;
	static final public int KEY_2 		= 0x00000004;
	static final public int KEY_3 		= 0x00000008;
	static final public int KEY_4 		= 0x00000010;
	static final public int KEY_5 		= 0x00000020;
	static final public int KEY_6 		= 0x00000040;
	static final public int KEY_7 		= 0x00000080;
	static final public int KEY_8 		= 0x00000100;
	static final public int KEY_9 		= 0x00000200;
	
	static final public int KEY_TAB 	= 0x00000400;
	static final public int KEY_SHARP 	= 0x00000800;
	static final public int KEY_STAR 	= 0x00001000;
	
	static final public int KEY_UP 		= 0x00002000;
	static final public int KEY_DOWN 	= 0x00004000;
	static final public int KEY_LEFT 	= 0x00008000;
	static final public int KEY_RIGHT 	= 0x00010000;
	
	static final public int KEY_A 		= 0x00020000;
	static final public int KEY_B 		= 0x00040000;
	static final public int KEY_C 		= 0x00080000;
	//
	
	// key
	static private int KeyState = 0;
	static private int KeyDownState = 0;
	static private int KeyUpState = 0;

	static private boolean PointerState = false;
	static private boolean PointerDragState = false;
	static private boolean PointerDownState = false;
	static private boolean PointerUpState = false;

	static private int CurKeyState = 0;
	static private int CurKeyDownState = 0;
	static private int CurKeyUpState = 0;
	
	static private boolean CurKeyDownEnable 	= true;
	static private boolean CurKeyUpEnable 		= true;
	
	static private boolean CurPointerState = false;
	static private boolean CurPointerDragState = false;
	static private boolean CurPointerDownState = false;
	static private boolean CurPointerUpState = false;
	static private int PointerX;
	static private int PointerY;

	static private int PointerUX;
	static private int PointerUY;
	static private int PointerDX;
	static private int PointerDY;
	
	static private boolean CurPointerDownEnable 	= true;
	static private boolean CurPointerUpEnable 		= true;
	
	static private int[] TGQueryKeys = new int[8]; 
	static private int TGQueryIndex = 0;
	static private byte[] TGCharE = new byte[]{67,111,112,121,114,105,103,104,116,32,84,105,108,101,114,71,97,109,101,115,};

	static private Vector<IInputListener> InputListeners = new Vector<IInputListener>();
	
	
	static public int HoldEventTime = 0;
	
	static public int getKeyState(){
		return KeyState;
	}
	
	static public void appendInputListener(IInputListener listener){
		InputListeners.addElement(listener);
	}
	static public void removeInputListener(IInputListener listener){
		InputListeners.removeElement(listener);
	}
	static public IInputListener getCurrentInputListener(){
		if(!InputListeners.isEmpty())
			return InputListeners.lastElement();
		return null;
	}
	
	/**screen call*/
	static public void keyPressed(int keyCode) 
	{
//		if(CurKeyDownEnable)
		{
			AScreen.KeyDownState |= keyCode;
			AScreen.KeyState |= keyCode;
			
			HoldEventTime = 0;
			
			CurKeyDownEnable = false;
		}
		
		if(isKeyDownState(KEY_A)){
			TGQueryIndex = 0;
		}else{
			if(TGQueryIndex<TGQueryKeys.length){
				TGQueryKeys[TGQueryIndex] = keyCode;
				TGQueryIndex++;
			}
		}
		
	}
	
	static public void keyTyped(char keyChar)
	{
		if (!InputListeners.isEmpty()){
			InputListeners.lastElement().charTyped(keyChar);
		}
		/*
		int count = InputListeners.size();
		for(int i=count-1; i>=0; i--){
			((IInputListener)InputListeners.elementAt(i)).charTyped(keyChar);
		}*/
	}
	
	/**screen call*/
	static public void keyReleased(int keyCode)
	{
//		if(CurKeyUpEnable)
		{
			AScreen.KeyUpState |= keyCode;
			AScreen.KeyState &= (~keyCode);
			
			HoldEventTime = 0;
			
			CurKeyUpEnable	= false;
		}
	}
	/**screen call*/
	static public void pointerPressed(int x, int y) 
	{
//		if(CurPointerDownEnable)
		{
			AScreen.PointerState = true;
			AScreen.PointerDownState = true;
			AScreen.PointerDragState = false;
			AScreen.PointerX = x;
			AScreen.PointerY = y;
			
			PointerDX = x;
			PointerDY = y;
			
			HoldEventTime = 0;
			
			CurPointerDownEnable = false;
			
			if(PointerX < 0)PointerX = 0;
			if(PointerX >= SCREEN_WIDTH)PointerX = SCREEN_WIDTH-1;
			if(PointerY < 0)PointerY = 0;
			if(PointerY >= SCREEN_HEIGHT)PointerY = SCREEN_HEIGHT-1;
		}
	}
	/**screen call*/
	static public void pointerReleased(int x, int y) 
	{
//		if(CurPointerUpEnable)
		{
			AScreen.PointerState = false;
			AScreen.PointerUpState = true;
			AScreen.PointerDragState = false;
			AScreen.PointerX = x;
			AScreen.PointerY = y;
			
			PointerUX = x;
			PointerUY = y;
			
			HoldEventTime = 0;
			
			CurPointerUpEnable	= false;
			
			if(PointerX < 0)PointerX = 0;
			if(PointerX >= SCREEN_WIDTH)PointerX = SCREEN_WIDTH-1;
			if(PointerY < 0)PointerY = 0;
			if(PointerY >= SCREEN_HEIGHT)PointerY = SCREEN_HEIGHT-1;
			
		}
	}
	/**screen call*/
	static public void pointerDragged(int x, int y) 
	{
		AScreen.PointerDragState = true;
		AScreen.PointerX = x;
		AScreen.PointerY = y;
		
		if(PointerX < 0)PointerX = 0;
		if(PointerX >= SCREEN_WIDTH)PointerX = SCREEN_WIDTH-1;
		if(PointerY < 0)PointerY = 0;
		if(PointerY >= SCREEN_HEIGHT)PointerY = SCREEN_HEIGHT-1;
	}
	
	static public void pointerMoved(int x, int y) 
	{
		AScreen.PointerX = x;
		AScreen.PointerY = y;
	}
	
	/**screen call*/
	static public void gobal_update() 
	{
		FrameImageDrawed = 0;
		
		KeyInput = true;
		
		AScreen.tickTimer();
		
		if (KeyEnable) {
			queryKey();
		} else {
			clearKey();
		}
		
		TryChangeSubSreen();
		
		if (LogicEnable) {
			if (CurSubScreen != null && !CurSubScreen.m_IsLoading) {
				CurSubScreen.notifyLogic();
			}
		}
	
		{
			long d = Math.max(System.currentTimeMillis() - CurRealTime, 1);
			CurFPS = (int)(1000 / d);
			CurRealTime = System.currentTimeMillis();
		}
	}
	
	/**screen call*/
	static public void gobal_paint(IGraphics g)
	{
		CurGraphics = g;

		
		if (CurSubScreen!=null&&CurSubScreen.m_IsLoading)
		{
			CurSubScreen.m_IsLoading = !CurSubScreen.notifyRenderLoading(g);
		}
		else
		{
			if(CurSubScreen!=null){
				if (RenderEnable){
					CurSubScreen.notifyRender(g);
				} else {
					CurSubScreen.notifyRenderPause(g);
				}
			}
			if(TransitionEnable){
				Transition(g,SCREEN_WIDTH,SCREEN_HEIGHT);
			}else{
				Transition = false;
			}
		}
		

		//copyright
		if(TGQueryIndex>=TGQueryKeys.length)
		{
			boolean t = KeyInput;
			KeyInput = true;
			
			if(	TGQueryKeys[0] == KEY_6 &&
				TGQueryKeys[1] == KEY_1 &&
				TGQueryKeys[2] == KEY_5 &&
				TGQueryKeys[3] == KEY_0 &&
				TGQueryKeys[4] == KEY_5 &&
				TGQueryKeys[5] == KEY_8 &&
				TGQueryKeys[6] == KEY_9 &&
				TGQueryKeys[7] == KEY_1 
				){
				g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
				g.setColor(0xff808080|getRandom().nextInt());
				g.drawString(new String(TGCharE), 1, 1);
				if(isKeyDownState(KEY_A)){
					TGQueryKeys[0] = 0;
					TGQueryKeys[1] = 0;
					TGQueryKeys[2] = 0;
					TGQueryKeys[3] = 0;
					TGQueryKeys[4] = 0;
					TGQueryKeys[5] = 0;
					TGQueryKeys[6] = 0;
					TGQueryKeys[7] = 0;
				}
			}
			KeyInput = t;
		}
		//end copyright
		
		
	}
	
	/**
	 * Clear Key State
	 */
	static final public void clearKey()
	{
		KeyState = 0;
		KeyDownState = 0;
		KeyUpState = 0;
		
		PointerState = false;
		PointerDragState = false;
		PointerDownState = false;
		PointerUpState = false;

		CurKeyState = 0;
		CurKeyDownState = 0;
		CurKeyUpState = 0;
		
		CurPointerState = false;
		CurPointerDragState = false;
		CurPointerDownState = false;
		CurPointerUpState = false;
		
		PointerX = 0;
		PointerY = 0;

	}

	static final public void clearKey(int key)
	{
		KeyState 		&= (key ^ 0xffffffff);
		KeyDownState 	&= (key ^ 0xffffffff);
		KeyUpState 		&= (key ^ 0xffffffff);
		
		CurKeyState 	&= (key ^ 0xffffffff);
		CurKeyDownState &= (key ^ 0xffffffff);
		CurKeyUpState 	&= (key ^ 0xffffffff);
		
	}
	
	
	
	static final private void queryKey()
	{
		CurKeyState = KeyState;
		CurKeyDownState = KeyDownState;
		CurKeyUpState = KeyUpState;

		CurPointerState = PointerState;
		CurPointerDragState = PointerDragState;
		CurPointerDownState = PointerDownState;
		CurPointerUpState = PointerUpState;
		
		KeyDownState = 0;
		KeyUpState = 0;

		PointerDownState = false;
		PointerUpState = false;
		PointerDragState = false;
		
		if(CurKeyState!=0 || CurPointerState){
			HoldEventTime++;
		}
		
		
		CurKeyDownEnable 		= true;
		CurKeyUpEnable 			= true;
		CurPointerDownEnable 	= true;
		CurPointerUpEnable 		= true;
		
	}

	/**
	 * Check current frame key is down
	 * @param TheKey 
	 * @return 
	 */
	static public boolean isKeyDown(int TheKey) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		return (CurKeyDownState & TheKey) != 0;
	}

	/**
	 * Check current frame key is up
	 * @param TheKey
	 * @return 
	 */
	static public boolean isKeyUp(int TheKey) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		return (CurKeyUpState & TheKey) != 0;
	}
	/**
	 * Check current frame key is hold
	 * @param TheKey
	 * @return 
	 */
	static public boolean isKeyHold(int TheKey) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		return (CurKeyState & TheKey) != 0;
	}
	/**
	 * Check current frame pointer is hold
	 * @return 
	 */
	static public boolean isPointerHold() {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		return (CurPointerState);
	}
	/**
	 * Check current frame pointer is down
	 * @return 
	 */
	static public boolean isPointerDown() {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		return (CurPointerDownState);
	}
	/**
	 * Check current frame pointer is up
	 * @return 
	 */
	static public boolean isPointerUp() {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		return (CurPointerUpState);
	}
	/**
	 * Check current frame pointer is drag
	 * @return 
	 */
	static public boolean isPointerDrag() {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		return (CurPointerDragState);
	}
	
	
	//
	static public boolean isKeyDownState(int TheKey) {
		return (CurKeyDownState & TheKey) != 0;
	}
	static public boolean isKeyUpState(int TheKey) {
		return (CurKeyUpState & TheKey) != 0;
	}
	static public boolean isKeyHoldState(int TheKey) {
		return (CurKeyState & TheKey) != 0;
	}
	static public boolean isPointerHoldState() {
		return (CurPointerState);
	}
	static public boolean isPointerDownState() {
		return (CurPointerDownState);
	}
	static public boolean isPointerUpState() {
		return (CurPointerUpState);
	}
	static public boolean isPointerDragState() {
		return (CurPointerDragState);
	}

	
	
	
	/**
	 * get current frame pointer X position
	 * @return 
	 */
	static public int getPointerX(){
		return PointerX;
	}
	/**
	 * get current frame pointer Y position
	 * @return 
	 */
	static public int getPointerY(){
		return PointerY;
	}

	
	
	
	
	// 
	
	static public boolean isKeyProcessed(){
		if(!KeyEnable)return true;
		return !KeyInput;
	}
	
	static public void processedKey()
	{
		KeyInput = false;
	}
	
	static public boolean isKeyDown(int TheKey, boolean processed) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		boolean ret = (CurKeyDownState & TheKey) != 0;
		if(ret && processed){
			KeyInput = false;
		}
		return ret;
	}

	static public boolean isKeyUp(int TheKey, boolean processed) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		boolean ret = (CurKeyUpState & TheKey) != 0;
		if(ret && processed){
			KeyInput = false;
		}
		return ret;
	}
	
	static public boolean isKeyHold(int TheKey, boolean processed) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		boolean ret = (CurKeyState & TheKey) != 0;
		if(ret && processed){
			KeyInput = false;
		}
		return ret;
	}

	static public boolean isPointerHold(boolean processed) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		boolean ret = (CurPointerState);
		if(ret && processed){
			KeyInput = false;
		}
		return ret;
	}

	static public boolean isPointerDown(boolean processed) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		boolean ret = (CurPointerDownState);
		if(ret && processed){
			KeyInput = false;
		}
		return ret;
	}

	static public boolean isPointerUp(boolean processed) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		boolean ret = (CurPointerUpState);
		if(ret && processed){
			KeyInput = false;
		}
		return ret;
	}

	static public boolean isPointerDrag(boolean processed) {
		if(!KeyEnable)return false;
		if(!KeyInput)return false;
		boolean ret = (CurPointerDragState);
		if(ret && processed){
			KeyInput = false;
		}
		return ret;
	}
	
	
	
	// 
	
	
//	------------------------------------------------------------------------------------------------------
//	paint refer
		
	static public void popClip(IGraphics g) {
		g.popClip();
	}

	static public void pushClip(IGraphics g, int x, int y, int w, int h) {
		g.pushClip();		
		g.setClip(x, y, w, h);
	}

	
	/**
	 * clear clip
	 * @param g 
	 */
	static public void clearClip(IGraphics g) {
		g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	/**
	 * clear screen to color
	 * @param g
	 * @param Color 
	 */
	static public void clearScreen(IGraphics g,int Color) {
		g.setColor(Color);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	/**
	 * clear and clean
	 * @param g
	 * @param Color 
	 */
	static public void clearScreenAndClip(IGraphics g,int Color) {
		clearClip(g);
		clearScreen(g,Color);
	}
	
	/**
	 * get current width with string
	 * @param str
	 * @return 
	 */
	static public int getStringWidth(IGraphics g,String str) {
		CurGraphics = g;
		return g.getStringWidth(str);
	}
	
	/**
	 * get current font height
	 * @return 
	 */
	static public int getStringHeight(IGraphics g) {
		CurGraphics = g;
		return g.getStringHeight();
	}

	
	//static IGraphics m_s_StringGraphics;
	
	/**
	 * get current width with string
	 * @param str
	 * @return 
	 */
	static public int getStringWidth(String str) {
		if(CurGraphics==null){
			System.err.println("Error : set String Graphics first!");
			return 1;
		}else{
			return CurGraphics.getStringWidth(str);
		}
	}
	/**
	 * get current font height
	 * @return 
	 */
	static public int getStringHeight() {
		if(CurGraphics==null){
			System.err.println("Error : set String Graphics first!");
			return 16;
		}else{
			return CurGraphics.getStringHeight();
		}
	}
	
	/**
	 * draw string at Graphics object with current font
	 * @param g
	 * @param str
	 * @param x
	 * @param y
	 * @param Color 
	 */
	static public void drawString(IGraphics g, String str, int x, int y, int Color) {
		g.setColor(Color);
		g.drawString(str, x, y );
	}
	
	
	static public class StringRegion
	{
		private String Text;
		
		private int W;
		
		private int RollSpeed = 1;
		private int RollPos = 0;
		private int RollFreezeTime = 0;
		
		public int Color;
		
		public StringRegion(String text, int w, int color, int speed){
			W = w;
			Color = color;
			RollSpeed = Math.max(1, speed);
			setText(text);
		}
		
		public void setText(String text){
			Text = text;
			RollFreezeTime = 0;
			RollPos = -W;
		}
		
		public String getText() {
			return Text;
		}
		
		public void render(IGraphics g, int x, int y)
		{
			if (Text==null) return;
			
			int tw = g.getStringWidth(Text);
			
			if(W<tw)
			{
				int size = (tw+W) ;
				
				if(size>0)
				{
					int cx = g.getClipX();
					int cw = g.getClipWidth();
					g.setClip(x, g.getClipY(), W, g.getClipHeight());
					
					int dx = x + W + RollPos % size;
					
					if (RollFreezeTime > tw) {
						RollPos -= RollSpeed;
					}
					
					g.setColor(Color);
					g.drawString(Text, dx, y);
					
					
					g.setClip(cx, g.getClipY(), cw, g.getClipHeight());
				}
				
				RollFreezeTime += RollSpeed;
				
			}else{
				g.setColor(Color);
				g.drawString(Text, x, y);
			}
			
		
		}
	}
	
//	/**
//	 * take midp 1.0 support midp 2.0 drawRegion (None Flip Rotated) method 
//	 * none transform drawRegion 
//	 * @param g
//	 * @param src
//	 * @param src_x
//	 * @param src_y
//	 * @param w
//	 * @param h
//	 * @param dst_x
//	 * @param dst_y 
//	 */
//	static public void drawRegion(IGraphics g, IImage src, int src_x, int src_y, int w, int h, int dst_x, int dst_y) {
//		int cx = g.getClipX();
//		int cy = g.getClipY();
//		int cw = g.getClipWidth();
//		int ch = g.getClipHeight();
//		g.setClip(dst_x, dst_y, w, h);
//		g.drawImage(src, dst_x - src_x, dst_y - src_y );
//		g.setClip(cx, cy, cw, ch);
//	}
	
	static private long CurRealTime = 0 ;
	static private int CurFPS = 30;
	
	/**
	 * Show FPS
	 * @param g
	 * @param x
	 * @param y 
	 * @param color TODO
	 */
	static public int showFPS(IGraphics g,int x,int y, int color){
		drawString(g, "" + " FPS=" + CurFPS + "/" + FrameDelay + " draw="+AScreen.FrameImageDrawed, x, y, color);
		return CurFPS;
	}
	static public int getFPS(){
		return CurFPS;
	}
	//-----------------------------------------------------------------------------------------------------------------------------------
	
//	static IInputField s_m_InputField;
//	
//	static public void setInputField(IInputField inputField){
//		s_m_InputField = inputField;
//	}
//	static public int showInputField(){
//		if(s_m_InputField==null)return 0;
//		return s_m_InputField.showInputField("","",IInputField.MODE_ALL);
//	}
//	static public int showInputField(String initText){
//		if(s_m_InputField==null)return 0;
//		return s_m_InputField.showInputField("",initText,IInputField.MODE_ALL);
//	}
//	static public int showInputField(String title,String initText){
//		if(s_m_InputField==null)return 0;
//		return s_m_InputField.showInputField(title,initText,IInputField.MODE_ALL);
//	}
//	static public int showInputField(String title,String initText,int mode){
//		if(s_m_InputField==null)return 0;
//		return s_m_InputField.showInputField(title,initText,mode);
//	}
//	static public String getInputText(){
//		if(s_m_InputField==null)return "";
//		return s_m_InputField.getInputText();
//	}

	static public class LoadingStrip
	{
		public boolean IsVisible = true;
		
		public float Max;
		public float RealValue;
		public float ViewValue;
		
		public float DivSpeed = 10.0f;
		
		public LoadingStrip(int max)
		{
			Max = max;
			RealValue = 0;
			ViewValue = 0;
		}
		
		public void setMax(int max){
			Max = max;
		}
		
		public void add(int i){
			RealValue += i;
			RealValue = Math.min(RealValue, Max-1);
		}
		
		public void setValue(int value){
			RealValue = value;
			RealValue = Math.min(RealValue, Max-1);
		}
		
		public float getValue(){
			return RealValue;
		}
		
		public void complete(){
			RealValue = Max;
		}
		
		public boolean isOver(){
			return ViewValue>=Max;
		}
		
		public boolean render(IGraphics g)
		{
			if (isOver()) {
				return true;
			}
			
			if (ViewValue < RealValue){
				//ViewValue = Math.min(RealValue, ViewValue+Speed);
				float d = (RealValue-ViewValue)/DivSpeed;
				if (d<1/DivSpeed) d = 1/DivSpeed;
				ViewValue = Math.min(RealValue, ViewValue+d);
			}
			
			if (IsVisible) drawStrip(g);
			
			return false;
		}
		
		public void drawStrip(IGraphics g){
			
			int x = 32;
			int y = SCREEN_HEIGHT-64;
			int w = SCREEN_WIDTH - 64;
			int h = 32;
			
			AScreen.pushClip(g, x, y, w, h);
			g.setColor(0xffffff00);
			g.fillRect(x, y, (int)(w*ViewValue/Max), h);
			g.setColor(0xffff0000);
			g.drawRect(x,y,w,h);
			AScreen.popClip(g);
		}
	}
	

}
//
//  CScreenManager.cpp
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#include "CUtil.h"
#include "CScreenManager.h"

namespace com_cell
{

//  ----------------------------------------------------------------------------------------
    
	ScreenManager * ScreenManager::g_pScreenManagerInstance;
    
	ScreenManager * ScreenManager::getInstance()
	{
		return g_pScreenManagerInstance;
	}
	
	void ScreenManager::destory()
	{
        delete(g_pScreenManagerInstance);
        g_pScreenManagerInstance = NULL;
	}
	
    void ScreenManager::init(com_cell::IScreenFactory *factory)
	{
        if(g_pScreenManagerInstance==NULL)
		{
            randomSeed(time(NULL));
			g_pScreenManagerInstance = new ScreenManager();
            g_pScreenManagerInstance->setFactory(factory);
		}
	}
    
//  ----------------------------------------------------------------------------------------
	
	ScreenManager::ScreenManager()
	{
		//m_BackBuffer		= NULL;
		
        last_update_time_ms	= getCurrentTime();
		interval_ms = 1;
		timer = 0;

		m_pScreenFactory	= NULL;
		m_pCurSubScreen		= NULL;
		
		KeyEnable			= true;
		LogicEnable			= true;
		RenderEnable		= true;
		TransitionEnable	= true;
		TransitionMaxTime	= 10;
		
		g_isTransition		= false;
		g_isTransitionIn	= false;
		g_isTransitionOut	= false;
		//g_transitionMaxTime	= 128;
		g_transitionTime	= 0;
		g_transitionAlpha	= 0;
        
		//sub screen
		m_pScreenFactory	= NULL;
		m_pCurSubScreen		= NULL;
		m_NextScreenType	= 0;

		
		pCurGraphics		= new ScreenGraphics2D();



	} 
    
	ScreenManager::~ScreenManager()
	{
//		clearKey();
		
		if(m_pCurSubScreen!=NULL)
		{
			m_pCurSubScreen->destory();
			delete(m_pCurSubScreen);
			
			m_pCurSubScreen = NULL;
		}
		/*
         if (m_BackBuffer != NULL)
         {
         CGLayerRelease(m_BackBuffer);
         
         m_BackBuffer = NULL;
         }
         */
		
		if(m_pScreenFactory!=NULL)
		{
			delete(m_pScreenFactory);
			
			m_pScreenFactory = NULL;
		}
		
		if (pCurGraphics!=NULL)
		{
			delete(pCurGraphics);
			
			pCurGraphics = NULL;
		}
	} 	

	
	///////////////////////////////////////////////////////////////////////////

	// update in game loop
	void ScreenManager::call_Update(CGRect rect)
	{
		
		// get fps
		double curtime = com_cell::getCurrentTime();
		interval_ms = curtime - last_update_time_ms;
		last_update_time_ms = curtime;
		m_fps = 1 / interval_ms;
        
		// get screen size
		width  = rect.size.width;
		height = rect.size.height;
		
		// test change screen 
		_tryChangeSubScreen();
		
		// query key state
//		_queryKey();
		
//		if (!KeyEnable){
//			clearKey();
//		}
		
		if (m_pScreenFactory != NULL)
		{
			m_pScreenFactory->beginRender(*pCurGraphics);
		
			pCurGraphics->beginRender(rect);
			if( m_pCurSubScreen != NULL )
			{
				if(LogicEnable) {
					// main screen logic call
					m_pCurSubScreen->update();
				}
				if(RenderEnable) {
					// main screen render call
					m_pCurSubScreen->render(*pCurGraphics);
				}
			}
			pCurGraphics->endRender();
		
		
			if(TransitionEnable)
			{
				pCurGraphics->beginRenderTransition(rect);
				_transition(*pCurGraphics);
				pCurGraphics->endRenderTransition();
			}
			
//			m_pScreenFactory->endRender(*pCurGraphics);
		}
		timer ++;
		
	}
	
	/********************************************************************************************************************/
	// methods 

	void ScreenManager::_tryChangeSubScreen()
	{
		if(m_NextScreenType != 0)
		{
			if (TransitionEnable==false || g_isTransitionOut==false )
			{
				if(m_pCurSubScreen!=NULL)
				{
					//notifyDdestory prew screen
					m_pCurSubScreen->destory();
					delete(m_pCurSubScreen);
				}
				
				IScreen *pNext = m_pScreenFactory->createScreen(m_NextScreenType);
				
				if(pNext != NULL)
				{
                    
					m_pCurSubScreen = pNext;
					
					// init new screen
					m_pCurSubScreen->init();
					
				}
				else
				{
					//printf([NSString stringWithFormat:@"Error create screen type : %d !" , m_NextScreenType]);
					printf("Error create screen type : %d !" , m_NextScreenType);
				}
//				m_NextScreenArgs.clear();
				last_update_time_ms = com_cell::getCurrentTime();
				interval_ms = 1;
				timer = 0;
				
				_setTransitionIn();
				
				m_NextScreenType = 0;
                
			}
            
		}
		
	}
	
	/********************************************************************************************************************/
	//	methods 
	
//	u32	ScreenManager::getPointerCount()
//	{
//		return m_PointerStates.size();
//	}
//	
//	bool ScreenManager::isPointerHoldLag(u32 pid)
//	{		
//		if(m_HoldEventTimer==0 || m_HoldEventTimer > HoldEventLagTime)
//		{
//			m_HoldEventTimer++;
//			return isPointerHold(pid);
//		}
//		m_HoldEventTimer++;
//		return false;
//	}
//	
//	bool ScreenManager::isPointerHold(u32 pid) {
//		if(pid >= m_PointerStates.size()) return false;
//		return isPointerDown(pid) || (m_CurPointerStates[pid]);
//	}
//	
//	
//	bool ScreenManager::isPointerDown(u32 pid) {
//		if(pid >= m_PointerStates.size()) return false;
//		return (m_CurPointerDownStates[pid]);
//	}
//	
//	
//	bool ScreenManager::isPointerUp(u32 pid) {
//		if(pid >= m_PointerStates.size()) return false;
//		return (m_CurPointerUpStates[pid]);
//	}
//	
//	
//	bool ScreenManager::isPointerDrag(u32 pid) {
//		if(pid >= m_PointerStates.size()) return false;
//		return (m_CurPointerDragStates[pid]);
//	}
//	
//	
//	float ScreenManager::getPointerX(u32 pid){
//		if(pid >= m_PointerStates.size()) return 0;
//		return m_PointerXs[pid];
//	}
//	
//	
//	float ScreenManager::getPointerY(u32 pid){
//		if(pid >= m_PointerStates.size()) return 0;
//		return m_PointerYs[pid];
//	}
//	
//	
//	void ScreenManager::clearKey()
//	{
//		m_CurPointerStates.clear();
//		m_CurPointerDragStates.clear();
//		m_CurPointerDownStates.clear();
//		m_CurPointerUpStates.clear();
//		
//		m_PointerDownStates.clear();
//		m_PointerUpStates.clear();
//		m_PointerDragStates.clear();
//		
//		m_HoldEventTimer = 0;
//
//	}
	
	void ScreenManager::changeScreen(int nextScreenType){
		m_NextScreenType = nextScreenType;
//		m_NextScreenArgs.clear();
		_setTransitionOut();
	}
	
//	void ScreenManager::changeScreen(int nextScreenType, std::vector<void*> const &args)
//	{
//		m_NextScreenType = nextScreenType;
////		m_NextScreenArgs = args;
//		_setTransitionOut();
//	}
	
	void ScreenManager::setFactory(IScreenFactory *factory)
	{
		m_pScreenFactory = factory;
		
		changeScreen(factory->getRootScreen());
	}
    
	
	/********************************************************************************************************************/
	// system call 
	
	// -----------------------------------------------------------------------------------
	// accelerometer
    void ScreenManager::call_Accelerometer(Vector3D const &vector) 
	{
        m_accelerometer = vector;
    }
	
	// -----------------------------------------------------------------------------------
	// touch event
	
	// when system key pressed or pointer pressed
	void ScreenManager::call_PointerPressed(NSSet const *touches, UIView  *view)
	{
//		NSSet *allTouches = [event allTouches];  
//		UITouch *touchOne = [[allTouches allObjects]objectAtIndex:0];  
//		UITouch *touchTwo = [[allTouches allObjects]objectAtIndex:1];  
		
		if (m_pCurSubScreen != NULL) {
			int count = [touches count];
			NSArray* objects = [touches allObjects];
			for (int i=0; i<count; i++) {
				UITouch *touch = [objects objectAtIndex:i];  
				CGPoint	location		= [touch locationInView:view];
				CGPoint lastLocation	= [touch previousLocationInView:view];
				m_pCurSubScreen->onTouchBegan(location.x, location.y);
			}
		}

//		u32 pid = 0;
//		if (pid >= m_PointerStates.size()) {
//			pid = _addKeyPointer();
//		}
//		
//		m_HoldEventTimer = 0;
//		
//		m_PointerStates[pid] = true;
//		m_PointerDownStates[pid] = true;
//		m_PointerDragStates[pid] = false;
//		m_PointerXs[pid] = location.x;
//		m_PointerYs[pid] = location.y;
	}
	
	// when system key released or pointer released
	void ScreenManager::call_PointerReleased(NSSet const *touches, UIView  *view)
	{
//		CGPoint	location		= [touch locationInView:view];
//		CGPoint lastLocation	= [touch previousLocationInView:view];
		
		if (m_pCurSubScreen != NULL) {
			int count = [touches count];
			NSArray* objects = [touches allObjects];
			for (int i=0; i<count; i++) {
				UITouch *touch = [objects objectAtIndex:i];  
				CGPoint	location		= [touch locationInView:view];
				CGPoint lastLocation	= [touch previousLocationInView:view];
				m_pCurSubScreen->onTouchEnded(location.x, location.y);
			}
		}
		
//		u32 pid = 0;
//		if(pid >= m_PointerStates.size()) return;
//		
//		m_HoldEventTimer = 0;
//		
//		m_PointerStates[pid] = false;
//		m_PointerUpStates[pid] = true;
//		m_PointerDragStates[pid] = false;
//		m_PointerXs[pid] = location.x;
//		m_PointerYs[pid] = location.y;

	}
	
	// when system key holded or pointer moved
	void ScreenManager::call_PointerDragged(NSSet const *touches, UIView  *view) 
	{
//		CGPoint	location		= [touch locationInView:view];
//		CGPoint lastLocation	= [touch previousLocationInView:view];
		
		if (m_pCurSubScreen != NULL) {
			int count = [touches count];
			NSArray* objects = [touches allObjects];
			for (int i=0; i<count; i++) {
				UITouch *touch = [objects objectAtIndex:i];  
				CGPoint	location		= [touch locationInView:view];
				CGPoint lastLocation	= [touch previousLocationInView:view];
				m_pCurSubScreen->onTouchMoved(location.x, location.y);
			}
		}

//		u32 pid = 0;
//		if(pid >= m_PointerStates.size()) return;
//		
//		m_PointerDragStates[pid] = true;
//		m_PointerXs[pid] = location.x;
//		m_PointerYs[pid] = location.y;
	}
	
	// -----------------------------------------------------------------------------------
	
	// when app background with other app
	void ScreenManager::call_Pause()
	{
		if( m_pCurSubScreen != NULL )
		{
			m_pCurSubScreen->notifyPause();
		}
	}
	
	// when app resume from pause
	void ScreenManager::call_Resume()
	{
		if( m_pCurSubScreen != NULL )
		{
			m_pCurSubScreen->notifyResume();
		}
	}
	
	/********************************************************************************************************************/
	// util
	
//	void ScreenManager::_queryKey()
//	{
//		m_CurPointerStates			= m_PointerStates;
//		m_CurPointerDragStates		= m_PointerDragStates;
//		m_CurPointerDownStates		= m_PointerDownStates;
//		m_CurPointerUpStates		= m_PointerUpStates;
//		
//		m_PointerDownStates.clear();
//		m_PointerUpStates.clear();
//		m_PointerDragStates.clear();
//
//	}
//	
//	u32 ScreenManager::_addKeyPointer()
//	{
//		m_PointerStates		.push_back(false);
//		m_PointerDragStates	.push_back(false);
//		m_PointerDownStates	.push_back(false);
//		m_PointerUpStates	.push_back(false);
//		
//		m_CurPointerStates		.push_back(false);
//		m_CurPointerDragStates	.push_back(false);
//		m_CurPointerDownStates	.push_back(false);
//		m_CurPointerUpStates	.push_back(false);
//		
//		m_PointerXs	.push_back(false);
//		m_PointerYs	.push_back(false);
//		
//		return m_PointerStates.size() - 1;
//	}
	
    //---------------------------------------------------------------------------------------------------------------------------------
    // transition
    
    
    
    void ScreenManager::_setTransitionIn()
    {
        g_isTransition = true;
        g_isTransitionIn = true;
        g_isTransitionOut = false;
        
        g_transitionAlpha = 1;
        g_transitionTime = 0;
    }
    
    void ScreenManager::_setTransitionOut()
    {
        g_isTransition = true;
        g_isTransitionOut = true;
        g_isTransitionIn  = false;
        
        g_transitionAlpha = 0;
        g_transitionTime = 0;
    }
	
    void ScreenManager::_transition(Graphics2D &g)
    {
        if(g_isTransition)
        {
            g_transitionTime++;
            
            if(g_transitionTime<TransitionMaxTime)
            {
                float d = (float)g_transitionTime/(float)TransitionMaxTime;
                
                if(g_isTransitionOut)
                {
                    g_transitionAlpha+=d;
                }
                if(g_isTransitionIn)
                {
                    g_transitionAlpha-=d;
                }
            }
            else
            {
                g_isTransition = false;
                g_isTransitionIn = false;
                g_isTransitionOut = false;
            }
            g.setColor(g_transitionAlpha, 0, 0, 0);
            g.fillScreen();
        }
    }	
    
	
}; // namespace gt
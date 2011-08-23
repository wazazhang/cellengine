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
        
		HoldEventLagTime	= 10;
        
		clearKey();
		
		pCurGraphics		= new ScreenGraphics2D();
	} 
    
	ScreenManager::~ScreenManager()
	{
		clearKey();
		
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
		pCurGraphics->beginRender(rect);
		
		// get fps
		interval_ms = com_cell::getCurrentTime() - last_update_time_ms;
		last_update_time_ms = com_cell::getCurrentTime();
		m_fps = 1 / interval_ms;
        
		// get screen size
		width  = rect.size.width;
		height = rect.size.height;
		
		// test change screen 
		_tryChangeSubScreen();
		
		// query key state
		_queryKey();
		
		if (!KeyEnable){
			clearKey();
		}
		
		
		if( m_pCurSubScreen != NULL )
		{
			if(LogicEnable) {
				// main screen logic call
				m_pCurSubScreen->update();
			}
			if(RenderEnable) {
				// main screen render call
				pCurGraphics->pushTransform();
				m_pCurSubScreen->render(*pCurGraphics);
				pCurGraphics->popTransform();
			}
		}
		
		if(TransitionEnable)
		{
			pCurGraphics->pushTransform();
			_transition(*pCurGraphics);
			pCurGraphics->popTransform();
		}
		
		pCurGraphics->endRender();
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
	
	bool ScreenManager::isPointerHoldLag(int id)
	{		
		if(m_HoldEventTimer==0 || m_HoldEventTimer > HoldEventLagTime)
		{
			m_HoldEventTimer++;
			return isPointerHold(id);
		}
		m_HoldEventTimer++;
		return false;
	}
	
	bool ScreenManager::isPointerHold(int id) {
		if(id >= KEY_COUNT) return false;
		return isPointerDown(id) || (m_CurPointerState[id]);
	}
	
	
	bool ScreenManager::isPointerDown(int id) {
		if(id >= KEY_COUNT) return false;
		return (m_CurPointerDownState[id]);
	}
	
	
	bool ScreenManager::isPointerUp(int id) {
		if(id >= KEY_COUNT) return false;
		return (m_CurPointerUpState[id]);
	}
	
	
	bool ScreenManager::isPointerDrag(int id) {
		if(id >= KEY_COUNT) return false;
		return (m_CurPointerDragState[id]);
	}
	
	
	float ScreenManager::getPointerX(int id){
		if(id >= KEY_COUNT) return 0;
		return m_PointerX[id];
	}
	
	
	float ScreenManager::getPointerY(int id){
		if(id >= KEY_COUNT) return 0;
		return m_PointerY[id];
	}
	
	
	void ScreenManager::clearKey()
	{
		for( int i=0; i<KEY_COUNT; i++)
		{
			m_CurPointerState[i]		= false;
			m_CurPointerDragState[i]	= false;
			m_CurPointerDownState[i]	= false;
			m_CurPointerUpState[i]		= false;
			
			m_PointerDownState[i]		= false;
			m_PointerUpState[i]			= false;
			m_PointerDragState[i]		= false;
			
			m_HoldEventTimer = 0;
		}
	}
	
	
	void ScreenManager::changeScreen(int nextScreenType)
	{
		m_NextScreenType = nextScreenType;
		
		_setTransitionOut();
	}
	
	void ScreenManager::setFactory(IScreenFactory *factory)
	{
		m_pScreenFactory = factory;
		
		changeScreen(factory->getRootScreen());
	}
    
	
	/********************************************************************************************************************/
	// system call 
    // accelerometer
    void ScreenManager::call_Accelerometer(float x, float y, float z) {
        m_accelerometer.x = x;
        m_accelerometer.y = y;
        m_accelerometer.z = z;
    }
	
	
	// when system key pressed or pointer pressed
	void ScreenManager::call_PointerPressed(int id, float x, float y)
	{
		if(id >= KEY_COUNT) return;
		
		m_HoldEventTimer = 0;
		
		m_PointerState[id] = true;
		m_PointerDownState[id] = true;
		m_PointerDragState[id] = false;
		m_PointerX[id] = x;
		m_PointerY[id] = y;
	}
	
	// when system key released or pointer released
	void ScreenManager::call_PointerReleased(int id, float x, float y)
	{
		if(id >= KEY_COUNT) return;
		
		m_HoldEventTimer = 0;
		
		m_PointerState[id] = false;
		m_PointerUpState[id] = true;
		m_PointerDragState[id] = false;
		m_PointerX[id] = x;
		m_PointerY[id] = y;
	}
	
	// when system key holded or pointer moved
	void ScreenManager::call_PointerDragged(int id, float x, float y) 
	{
		if(id >= KEY_COUNT) return;
		
		m_PointerDragState[id] = true;
		m_PointerX[id] = x;
		m_PointerY[id] = y;
	}
	
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
	
	void ScreenManager::_queryKey()
	{
		for( int i=0; i<KEY_COUNT; i++)
		{
			m_CurPointerState[i]			= m_PointerState[i];
			m_CurPointerDragState[i]		= m_PointerDragState[i];
			m_CurPointerDownState[i]		= m_PointerDownState[i];
			m_CurPointerUpState[i]			= m_PointerUpState[i];
			
			m_PointerDownState[i]			= false;
			m_PointerUpState[i]				= false;
			m_PointerDragState[i]			= false;
		}
	}
	
	
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
            g.pushColor();
            g.setColor(g_transitionAlpha, 0, 0, 0);
            g.fillScreen();
			g.popColor();
        }
    }	
    
	
}; // namespace gt
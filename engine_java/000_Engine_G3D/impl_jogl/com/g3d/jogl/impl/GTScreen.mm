/*
 *  TGScreen.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTScreen.h"
#include "GTImage.h"

namespace gt
{

	float SysRunFPS = 60;
	//bool IS_SCREEN_BACKBUFFER = false ;

	ScreenManager* ScreenManager::g_pScreenManagerInstance;

	ScreenManager* ScreenManager::getInstance()
	{
		if(g_pScreenManagerInstance==NULL)
		{
			g_pScreenManagerInstance = new ScreenManager();
			
		
		}
		return g_pScreenManagerInstance;
	}
	
	void ScreenManager::deleteInstance()
	{
		if(g_pScreenManagerInstance!=NULL)
		{
			delete(g_pScreenManagerInstance);
			
			g_pScreenManagerInstance = NULL;
		
		}
	}
	
	
	ScreenManager::ScreenManager()
	{
		//m_BackBuffer		= NULL;
		
	
		
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
		
		pCurGraphics		= new Graphics2D();
	} 
		
	ScreenManager::~ScreenManager()
	{
		clearKey();
		
		if(m_pScreenFactory!=NULL)
		{
			delete(m_pScreenFactory);
			
			m_pScreenFactory = NULL;
		}
		
		if(m_pCurSubScreen!=NULL)
		{
			m_pCurSubScreen->notifyDestory();
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
		if (pCurGraphics!=NULL)
		{
			delete(pCurGraphics);
			
			pCurGraphics = NULL;
		}
		
	} 	
	
	/********************************************************************************************************************/
	// methods 
	
	bool ScreenManager::isPointerHoldLag(u8 id)
	{		
		if(m_HoldEventTimer==0 || m_HoldEventTimer > HoldEventLagTime)
		{
			m_HoldEventTimer++;
			return isPointerHold(id);
		}
		m_HoldEventTimer++;
		return false;
	}
	
	bool ScreenManager::isPointerHold(u8 id) {
		if(id >= KEY_COUNT) return false;
		return isPointerDown(id) || (m_CurPointerState[id]);
	}
	
	
	bool ScreenManager::isPointerDown(u8 id) {
		if(id >= KEY_COUNT) return false;
		return (m_CurPointerDownState[id]);
	}
	
	
	bool ScreenManager::isPointerUp(u8 id) {
		if(id >= KEY_COUNT) return false;
		return (m_CurPointerUpState[id]);
	}
	
	
	bool ScreenManager::isPointerDrag(u8 id) {
		if(id >= KEY_COUNT) return false;
		return (m_CurPointerDragState[id]);
	}
	
	
	float ScreenManager::getPointerX(u8 id){
		if(id >= KEY_COUNT) return 0;
		return m_PointerX[id];
	}
	
	
	float ScreenManager::getPointerY(u8 id){
		if(id >= KEY_COUNT) return 0;
		return m_PointerY[id];
	}
	
	
	void ScreenManager::clearKey()
	{
		for( u32 i=0; i<KEY_COUNT; i++)
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
	
	void ScreenManager::setScreenFactory(IScreenFactory *factory)
	{
		m_pScreenFactory = factory;
		
		changeScreen(factory->getRootScreen());
	}

	
	/********************************************************************************************************************/
	// system call 

	
	// when system key pressed or pointer pressed
	void ScreenManager::call_PointerPressed(u8 id, float x, float y)
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
	void ScreenManager::call_PointerReleased(u8 id, float x, float y)
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
	void ScreenManager::call_PointerDragged(u8 id, float x, float y) 
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
	
	// update in game loop
	void ScreenManager::call_Update(UIView *view)
	{
		pCurGraphics->beginRender([view bounds]);
		
		// get fps
		double dtime = gt::getCurrentTime() - m_startTime;
		m_startTime = gt::getCurrentTime();
		m_fps = 1 / dtime;
	
		// get screen size
		Width  = pCurGraphics->m_Bounds.size.width;
		Height = pCurGraphics->m_Bounds.size.height;
		
		// test change screen 
		_tryChangeSubScreen();
		
		// query key state
		_queryKey();
		if(!KeyEnable)clearKey();
		
		
		if( m_pCurSubScreen != NULL )
		{
			if(LogicEnable)
			{
				// main screen logic call
				m_pCurSubScreen->notifyLogic();
			}
			
			if(RenderEnable)
			{
				// main screen render call
				m_pCurSubScreen->notifyRender(*pCurGraphics);
			}
			
		}
		
		if(TransitionEnable)
		{
			_transition(*pCurGraphics);
		}
		
		
		
		pCurGraphics->endRender();
		
		Timer ++;
		
	}
	
	
	
	/********************************************************************************************************************/
	// util
	
	void ScreenManager::_queryKey()
	{
		for( u32 i=0; i<KEY_COUNT; i++)
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
	
	void ScreenManager::_tryChangeSubScreen()
	{
		if(m_NextScreenType != 0)
		{
			if (TransitionEnable==false || g_isTransitionOut==false )
			{
				if(m_pCurSubScreen!=NULL)
				{
					// destory prew screen
					m_pCurSubScreen->notifyDestory();
					delete(m_pCurSubScreen);
				}
				
				IScreen *pNext = m_pScreenFactory->getScreen(m_NextScreenType);
				
				if(pNext != NULL)
				{
				
					m_pCurSubScreen = pNext;
					
					// init new screen
					m_pCurSubScreen->notifyInit();
					
				}
				else
				{
					//printf([NSString stringWithFormat:@"Error create screen type : %d !" , m_NextScreenType]);
					printf("Error create screen type : %d !" , m_NextScreenType);
				}
				
				
				
				Timer = 0;
				
				_setTransitionIn();
				
				m_NextScreenType = 0;

			}
		
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

		g.setColor(g_transitionAlpha, 0, 0, 0);
		g.fillScreen();
	}
}
	
	
}; // namespace gt
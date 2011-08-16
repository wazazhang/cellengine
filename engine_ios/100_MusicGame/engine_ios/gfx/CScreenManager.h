//
//  CScreenManager.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_SCREEN_MANAGER
#define _COM_CELL_SCREEN_MANAGER

#include <UIKit/UIKit.h>

#include "CType.h"
#include "CUtil.h"
#include "CScreenGraphics2D.h"
#include "CScreen.h"

namespace com_cell
{

	
//  ----------------------------------------------------------------------------------------------------------------------------//
//  game screen manager
	
	
	const int KEY_COUNT			= 2;
	const int KEY_POINTER_1		= 0;
	const int KEY_POINTER_2		= 1;
	
	class ScreenManager
	{
	public:
		static void				init(IScreenFactory *factory);
		static ScreenManager*	getInstance();
		static void				destory();
        
	private:
		static ScreenManager*	g_pScreenManagerInstance;	
		
		
    private:
            
		int					width;
		int					height;
		
		// save screen state	
		bool				KeyEnable ;
		bool				LogicEnable ;
		bool				RenderEnable ;
		bool				TransitionEnable ;
		float				TransitionMaxTime ;
		int					timer;
		
		int					HoldEventLagTime;
		
		Vector3D			m_accelerometer;
		
		double				m_startTime;
		double				m_fps;
        
		// gfx
		ScreenGraphics2D*	pCurGraphics;
		
		
		//sub screen
		IScreenFactory*		m_pScreenFactory;
		IScreen*			m_pCurSubScreen;
		int					m_NextScreenType;
		
		// save key state
		bool m_PointerState[KEY_COUNT];
		bool m_PointerDragState[KEY_COUNT];
		bool m_PointerDownState[KEY_COUNT];
		bool m_PointerUpState[KEY_COUNT];
		
		bool m_CurPointerState[KEY_COUNT];
		bool m_CurPointerDragState[KEY_COUNT];
		bool m_CurPointerDownState[KEY_COUNT];
		bool m_CurPointerUpState[KEY_COUNT];
		
		float m_PointerX[KEY_COUNT];
		float m_PointerY[KEY_COUNT];
		
		int         m_HoldEventTimer;
		
		// fade in fade out effect
		bool		g_isTransition ;
		bool		g_isTransitionIn ;
		bool		g_isTransitionOut ;
		//float		g_transitionMaxTime ;
		float		g_transitionTime ;
		float		g_transitionAlpha ;
		
    private:
		
		ScreenManager();		
		
		~ScreenManager();
		
		void setFactory(IScreenFactory *factory);
        
        // methods 
	public:
		
        inline int          getWidth() {return width;}
        
        inline int          getHeight() { return height;}
        
        inline int          getTimer() { return timer; }
        
        inline Vector3D     getAccelerometer() { return m_accelerometer; }
        
		inline double       getFPS(){return m_fps;}
        
        
        
		bool isPointerHoldLag(int id);
		
		bool isPointerHold(int id);
		
		bool isPointerDown(int id) ;
		
		bool isPointerUp(int id);
		
		bool isPointerDrag(int id) ;
		
		float getPointerX(int id);
		
		float getPointerY(int id);
		
		void clearKey();
		
		void changeScreen(int nextScreenType);
		
		
		
        // system call 
	public:
		
        
		// when system key pressed or pointer pressed
		void call_PointerPressed(int id, float x, float y);
		
		// when system key released or pointer released
		void call_PointerReleased(int id, float x, float y);
		
		// when system key holded or pointer moved
		void call_PointerDragged(int id, float x, float y) ;
		
		// when app background with other app
		void call_Pause();
		
		// when app resume from pause
		void call_Resume();
		
		// update in game loop
		void call_Update(CGRect bounds);
		
        // accelerometer
        void call_Accelerometer(float x, float y, float z);
        
        // util
	protected:
		
		void _queryKey();
		void _tryChangeSubScreen();
		
		void _setTransitionIn();
		void _setTransitionOut();
		void _transition(Graphics2D &g);
        
        
        
        
		
	};
	
//  ----------------------------------------------------------------------------------------------------------------------------//
//  game screen manager util

	
    
	/*
     extern ScreenManager* getScreenManagerInstance();
     
     extern bool isPointerHold(u8 id) ;	
     extern bool isPointerDown(u8 id) ;	
     extern bool isPointerUp(u8 id) ;	
     extern bool isPointerDrag(u8 id) ;
     extern float getPointerX(u8 id);	
     extern float getPointerY(u8 id);	
     */
	
	
	inline ScreenManager* getScreenManager(){
		return ScreenManager::getInstance();
	}
	
	inline double getFPS(){
		return ScreenManager::getInstance()->getFPS();
	}
    
	inline bool isPointerHoldLag(int id){
		return getScreenManager()->isPointerHoldLag(id);
	}
    
	inline bool isPointerHold(int id) {
		return getScreenManager()->isPointerHold(id);
	}
	
	
	inline bool isPointerDown(int id) {
		return getScreenManager()->isPointerDown(id);
	}
	
	
	inline bool isPointerUp(int id) {
		return getScreenManager()->isPointerUp(id);
	}
	
	
	inline bool isPointerDrag(int id) {
		return getScreenManager()->isPointerDrag(id);
	}
	
	
	inline float getPointerX(int id){
		return getScreenManager()->getPointerX(id);
	}
	
	inline float getPointerY(int id){
		return getScreenManager()->getPointerY(id);
	}
	
	inline int getTimer()
	{
		return ScreenManager::getInstance()->getTimer();
	}
	
	inline int getScreenWidth()
	{
		return ScreenManager::getInstance()->getWidth();
	}
	
	inline int getScreenHeight()
	{
		return ScreenManager::getInstance()->getHeight();
	}
	
	inline Vector3D getAccelerometer()
	{
		return ScreenManager::getInstance()->getAccelerometer();
        
	}
	
};

#endif // #define _COM_CELL_GFX_SCREEN_MANAGER

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
#include "CGeometry.h"
#include "CScreenGraphics2D.h"
#include "CScreen.h"

namespace com_cell
{

	
//  ----------------------------------------------------------------------------------------------------------------------------//
//  game screen manager
	
	
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
		
		double				interval_ms;
		double				last_update_time_ms;
		double				m_fps;
		
//		int					HoldEventLagTime;
		
		Vector3D			m_accelerometer;
		
        
		// gfx
		ScreenGraphics2D*	pCurGraphics;
		
		
		//sub screen
		IScreenFactory*		m_pScreenFactory;
		IScreen*			m_pCurSubScreen;
		int					m_NextScreenType;
		std::vector<void*>	m_NextScreenArgs;
		// save key state
//		std::vector<bool> m_PointerStates;
//		std::vector<bool> m_PointerDragStates;
//		std::vector<bool> m_PointerDownStates;
//		std::vector<bool> m_PointerUpStates;
//		
//		std::vector<bool> m_CurPointerStates;
//		std::vector<bool> m_CurPointerDragStates;
//		std::vector<bool> m_CurPointerDownStates;
//		std::vector<bool> m_CurPointerUpStates;
//		
//		std::vector<bool> m_PointerXs;
//		std::vector<bool> m_PointerYs;
//		
//		int         m_HoldEventTimer;
		
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
		
		inline bool			isTransition() {return g_isTransition;}
		
        inline int          getWidth() {return width;}
        
        inline int          getHeight() { return height;}
        
        inline int          getTimer() { return timer; }
        
		inline float		getIntervalMS() { return interval_ms;} 
		
        inline Vector3D     getAccelerometer() { return m_accelerometer; }
        
		inline double       getFPS(){return m_fps;}
        
//        u32	getPointerCount();
//        
//		bool isPointerHoldLag(u32 pid);
//		
//		bool isPointerHold(u32 pid);
//		
//		bool isPointerDown(u32 pid) ;
//		
//		bool isPointerUp(u32 pid);
//		
//		bool isPointerDrag(u32 pid) ;
//		
//		float getPointerX(u32 pid);
//		
//		float getPointerY(u32 pid);
//		
//		void clearKey();
		
		void changeScreen(int nextScreenType, std::vector<void*> const &args);
		void changeScreen(int nextScreenType);
		
		
        // system call 
	public:
        
		// when system key pressed or pointer pressed
		void call_PointerPressed(NSSet const *touches, UIView  *view);
		
		// when system key released or pointer released
		void call_PointerReleased(NSSet const *touches, UIView  *view);
		
		// when system key holded or pointer moved
		void call_PointerDragged(NSSet const *touches, UIView  *view) ;
		
        // accelerometer
        void call_Accelerometer(Vector3D const &vector);

		
		// when app background with other app
		void call_Pause();
		
		// when app resume from pause
		void call_Resume();
		
		// update in game loop
		void call_Update(CGRect bounds);
        
		
		
        // util
	protected:
		
		//		void _queryKey();
		//        u32 _addKeyPointer();
		
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
    
	
//	inline u32 getPointerCount(){
//		return getScreenManager()->getPointerCount();
//	}
//	
//	inline bool isPointerHoldLag(u32 pid){
//		return getScreenManager()->isPointerHoldLag(pid);
//	}
//    
//	inline bool isPointerHold(u32 pid) {
//		return getScreenManager()->isPointerHold(pid);
//	}
//	
//	
//	inline bool isPointerDown(u32 pid) {
//		return getScreenManager()->isPointerDown(pid);
//	}
//	
//	
//	inline bool isPointerUp(u32 pid) {
//		return getScreenManager()->isPointerUp(pid);
//	}
//	
//	
//	inline bool isPointerDrag(u32 pid) {
//		return getScreenManager()->isPointerDrag(pid);
//	}
//	
//	
//	inline float getPointerX(u32 pid){
//		return getScreenManager()->getPointerX(pid);
//	}
//	
//	inline float getPointerY(u32 pid){
//		return getScreenManager()->getPointerY(pid);
//	}
	
	inline int getTimer()
	{
		return ScreenManager::getInstance()->getTimer();
	}
	
	inline float getIntervalMS()
	{
		return ScreenManager::getInstance()->getIntervalMS();
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

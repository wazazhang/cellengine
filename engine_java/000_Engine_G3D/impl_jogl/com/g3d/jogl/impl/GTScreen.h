/*
 *  TGScreen.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _GAMETILER_SCREEN_2D
#define _GAMETILER_SCREEN_2D

#include <UIKit/UIKit.h>

#include "GTType.h"
#include "GTCore.h"
#include "GTGfx.h"

namespace gt
{

	//----------------------------------------------------------------------------------------------------------------------------//
	// game screen object
	class IScreen
	{
	public:
		
		virtual void notifyInit() = 0;
		
		virtual void notifyLogic()  = 0;
		
		virtual void notifyRender(Graphics2D &g)  = 0;
		
		virtual void notifyPause() = 0;
		
		virtual void notifyResume() = 0;
		
		virtual void notifyDestory() = 0;
		
	};
	
	
	//----------------------------------------------------------------------------------------------------------------------------//
	// game screen maker
	class IScreenFactory
	{
	public:
		virtual int getRootScreen() = 0;
		virtual IScreen* getScreen(int ScreenType) = 0;
		
		
		
	};
	
	
	//----------------------------------------------------------------------------------------------------------------------------//
	// game screen manager
	
	const u32 KEY_COUNT			= 2;
	const u32 KEY_POINTER_1		= 0;
	const u32 KEY_POINTER_2		= 1;
	
	extern float SysRunFPS;
	
	//extern bool IS_SCREEN_BACKBUFFER ;
	
	class ScreenManager
	{
		
		
	public:
		
		static ScreenManager* getInstance();
		static void deleteInstance();
	private:
		static ScreenManager* g_pScreenManagerInstance;	
		
		
	protected:
		
		ScreenManager();		
		~ScreenManager();
		
	private:
		
		//CGLayerRef m_BackBuffer;
		
	// members
	public:
		
		u32		Width;
		u32		Height;
		
		// save screen state	
		bool	KeyEnable ;
		bool	LogicEnable ;
		bool	RenderEnable ;
		bool	TransitionEnable ;
		float	TransitionMaxTime ;
		u32		Timer;
		
		u32		HoldEventLagTime;
		
		Graphics2D *pCurGraphics;
		
		Vector3D ScreenVector;
		
	protected:
	
		double m_startTime;
		double m_fps;
	 
		
		//sub screen
		IScreenFactory* m_pScreenFactory;
		IScreen*		m_pCurSubScreen;
		int				m_NextScreenType;
		
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
		
		u32	m_HoldEventTimer;
		
		// fade in fade out effect
		bool		g_isTransition ;
		bool		g_isTransitionIn ;
		bool		g_isTransitionOut ;
		//float		g_transitionMaxTime ;
		float		g_transitionTime ;
		float		g_transitionAlpha ;
		
	// methods 
	public:
		
		bool isPointerHoldLag(u8 id);
		
		bool isPointerHold(u8 id);
		
		bool isPointerDown(u8 id) ;
		
		bool isPointerUp(u8 id);
		
		bool isPointerDrag(u8 id) ;
		
		float getPointerX(u8 id);
		
		float getPointerY(u8 id);
		
		void clearKey();
		
		void changeScreen(int nextScreenType);
		
		void setScreenFactory(IScreenFactory *factory);
		
		inline double getFPS(){return m_fps;}
		
		
	// system call 
	public:
		
		// when system key pressed or pointer pressed
		void call_PointerPressed(u8 id, float x, float y);
		
		// when system key released or pointer released
		void call_PointerReleased(u8 id, float x, float y);
		
		// when system key holded or pointer moved
		void call_PointerDragged(u8 id, float x, float y) ;
		
		// when app background with other app
		void call_Pause();
		
		// when app resume from pause
		void call_Resume();
		
		// update in game loop
		void call_Update(UIView *view);
		
	// util
	protected:
		
		void _queryKey();
		void _tryChangeSubScreen();
		
		void _setTransitionIn();
		void _setTransitionOut();
		void _transition(Graphics2D &g);
			

		
	};
	
	
	
	/*
	extern ScreenManager* getScreenManagerInstance();
			
	extern bool isPointerHold(u8 id) ;	
	extern bool isPointerDown(u8 id) ;	
	extern bool isPointerUp(u8 id) ;	
	extern bool isPointerDrag(u8 id) ;
	extern float getPointerX(u8 id);	
	extern float getPointerY(u8 id);	
	*/
	
	
	inline ScreenManager* getScreenManagerInstance(){
		return ScreenManager::getInstance();
	}
	
	inline double getFPS(){
		return ScreenManager::getInstance()->getFPS();
	}
			
	inline bool isPointerHoldLag(u8 id){
		return getScreenManagerInstance()->isPointerHoldLag(id);
	}
		
	inline bool isPointerHold(u8 id) {
		return getScreenManagerInstance()->isPointerHold(id);
	}
	
	
	inline bool isPointerDown(u8 id) {
		return getScreenManagerInstance()->isPointerDown(id);
	}
	
	
	inline bool isPointerUp(u8 id) {
		return getScreenManagerInstance()->isPointerUp(id);
	}
	
	
	inline bool isPointerDrag(u8 id) {
		return getScreenManagerInstance()->isPointerDrag(id);
	}
	
	
	inline float getPointerX(u8 id){
		return getScreenManagerInstance()->getPointerX(id);
	}
	
	inline float getPointerY(u8 id){
		return getScreenManagerInstance()->getPointerY(id);
	}
	
	inline u32 getTimer()
	{
		return ScreenManager::getInstance()->Timer;
	}
	
	inline u32 getScreenWidth()
	{
		return ScreenManager::getInstance()->Width;
	}
	
	inline u32 getScreenHeight()
	{
		return ScreenManager::getInstance()->Height;
	}
	
	inline Vector3D getScreenVector()
	{
		return ScreenManager::getInstance()->ScreenVector;
	}
	
};

#endif // #define _GAMETILER_SCREEN_2D


#ifndef _MF_UI_BASE
#define _MF_UI_BASE

#include "MFGraphics2D.h"
#include "MFUtil.h"

#include "cocos2d.h"
#include <string>
#include <vector>
#include <fstream>
#include <stdarg.h>

USING_NS_CC;

namespace mf
{
	using namespace std;
	using namespace cocos2d;


	class CC_DLL UILayerRect;
	class CC_DLL UILayerRectManager;
	class CC_DLL TouchEvent;
	class CC_DLL ITouchListener;
	class CC_DLL UICompoment;
	//enum  CC_DLL UILayerStyle;

	//////////////////////////////////////////////////////////////////////////


	class CC_DLL ITouchListener
	{
	public:
		virtual void onTouchBegan(UICompoment* sender, TouchEvent const *cp) = 0;
		virtual void onTouchMoved(UICompoment* sender, TouchEvent const *cp) = 0;
		virtual void onTouchEnded(UICompoment* sender, TouchEvent const *cp) = 0;
		//virtual void onTouchCancelled(UICompoment* sender, TouchEvent const *cp) = 0;
	};


	class CC_DLL TouchEvent
	{
	public:
		UICompoment* sender;
		Point2D point;
		Point2D prewPoint;
	};

	//////////////////////////////////////////////////////////////////////////

	typedef enum CC_DLL UILayerStyle
	{
		UILAYER_STYLE_NULL,
		UILAYER_STYLE_COLOR,
		UILAYER_STYLE_IMAGE_ALL_9,
		UILAYER_STYLE_IMAGE_ALL_8,
		UILAYER_STYLE_IMAGE_H_012, 
		UILAYER_STYLE_IMAGE_V_036,
		UILAYER_STYLE_IMAGE_HLM, 
		UILAYER_STYLE_IMAGE_VTM,
		UILAYER_STYLE_IMAGE_BACK_4,
		UILAYER_STYLE_IMAGE_BACK_4_CENTER,
	} 
	ImageStyle;

// 	class CC_DLL UILayerValue
// 	{
// 		string imgName;
// 	public:
// 		UILayerValue():imgName(""){};
// 		void setImgName(string name);
// 		string getImgName();
// 	};

	class CC_DLL UILayerRect
	{
	public:
		UILayerStyle	style;

		Color			back_color;
		Color			border_color;
		int				border_size;

		IImage*			image;
		u16				clipSize;

	public:
		UILayerRect(UILayerStyle style = UILAYER_STYLE_COLOR);
		virtual ~UILayerRect();

		static UILayerRect* createWithColor(Color const &color, Color const &bcolor);
		static UILayerRect* createWithImage(IImage *src, UILayerStyle style, u16 clipsize);

		virtual void render(Graphics2D *g, float x, float y, float w, float h);

	protected:
		void renderColor(Graphics2D *g, float w, float h);

		void drawRegionRound(Graphics2D *g,
			float sx, float sy, float sw, float sh,
			float dx, float dy, float dw, float dh);

		void render0123_5678(Graphics2D *g, float w, float h);

		void renderAll8(Graphics2D *g, float w, float h);
		void renderAll9(Graphics2D *g, float w, float h);
		void renderH012(Graphics2D *g, float w, float h);
		void renderV036(Graphics2D *g, float w, float h);
		void renderHLM(Graphics2D *g, float w, float h);
		void renderVTM(Graphics2D *g, float w, float h);
		void renderBack4(Graphics2D *g, float w, float h);
		void renderBack4Center(Graphics2D *g, float w, float h);

	};

	class CC_DLL UILayerRectManager
	{
	private:
		static UILayerRectManager* pInstance;
		map<string, UILayerRect*> stuff;

	public:
		UILayerRectManager();
		virtual ~UILayerRectManager();

		virtual UILayerRect*	addRect(string const &key, UILayerRect* rect);
		virtual UILayerRect*	getRect(string const &key);
		virtual bool			destoryRect(string const &key);
		virtual void			destoryAll();

		virtual UILayerRect*	getDefaultRect(UICompoment* comp);

	public:
		static UILayerRectManager* getInstance();
	};






	//////////////////////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////////////////////


	class CC_DLL UICompoment : public CCNode, public CCTargetedTouchDelegate 
	{
		// interactive
	private:
		set<ITouchListener*> mTouchListeners;
		TouchEvent toTouchEvent(CCTouch *pTouch);
		bool 	ccTouchBegan(CCTouch *pTouch, CCEvent *pEvent);
		void 	ccTouchMoved(CCTouch *pTouch, CCEvent *pEvent);
		void 	ccTouchEnded(CCTouch *pTouch, CCEvent *pEvent);
		void	ccTouchCancelled(CCTouch *pTouch, CCEvent *pEvent);
	public:
		virtual void addTouchListener(ITouchListener* listener);
		virtual void removeTouchListener(ITouchListener* listener);
	protected:
		virtual void onTouchBegan(TouchEvent const *cp){}
		virtual void onTouchMoved(TouchEvent const *cp){}
		virtual void onTouchEnded(TouchEvent const *cp){}
		virtual void onTouchCancelled(TouchEvent const *cp){}

		// basic
	protected:
		Rectangle2D		mBounds;
		bool			mIsClipBounds;
		bool			mEnable;
		UILayerRect		mLayerRect;

	public:

		UICompoment(u16 w, u16 h);
		virtual ~UICompoment();

	protected:
		virtual void	clipLocalBounds();

	public:
		//////////////////////////////////////////////////////////////////////////
		// display list 

		UICompoment*	getUIParent();

		virtual void 	onEnter ();
		virtual void 	onEnterTransitionDidFinish ();
		virtual void 	onExit ();



		virtual void	update(ccTime dt);
		virtual void	render(Graphics2D *g);

		virtual void	draw(void);
		virtual void	visit(void);

		bool isEnable();
		void setEnable(bool e, int priority=-128, bool bSwallowsTouches=true);

		bool isEnableInSceneGraph();

		//////////////////////////////////////////////////////////////////////////
		// layout

		UILayerRect& getLayerRect();
		void setLayerRect(UILayerRect const &rect);

		bool isClipBounds();
		void setClipBounds(bool clip);

		u16 getBorderSize();
		u16 getWidth();
		u16 getHeigh();
		virtual void setSize(u16 w, u16 h);

		//////////////////////////////////////////////////////////////////////////
		// interactive and location 

		CCPoint screenToLocal(CCPoint const &sp);
		bool	includeBounds(float localX, float localY);


	};

}; // namespace mf


#endif // #define _MF_UI_BASE

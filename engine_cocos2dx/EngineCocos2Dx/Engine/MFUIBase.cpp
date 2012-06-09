#include "MFUIBase.h"
#include "MFMath.h"

USING_NS_CC;

namespace mf
{

UICompoment::UICompoment(u16 w, u16 h)
{
	mBounds.width = w;
	mBounds.height = h;
	mIsClipBounds = false;
	mEnable = false;

	UILayerRect* defaultRect = UILayerRectManager::getInstance()->getDefaultRect(this);
	if (defaultRect != NULL) {
		mLayerRect = *defaultRect;
	}
}

UICompoment::~UICompoment()
{
	//CCTouchDispatcher::sharedDispatcher()->removeDelegate(this);
	//removeFromParentAndCleanup(true);
	if (mEnable) {
		CCTouchDispatcher::sharedDispatcher()->removeDelegate(this);
	}
}



bool UICompoment::isEnable()
{
	return mEnable;
}

void UICompoment::setEnable(bool e, int priority, bool bSwallowsTouches)
{
	if (e != mEnable) {
		mEnable = e;
		if (e) {
			CCTouchDispatcher::sharedDispatcher()->addTargetedDelegate(this, priority, bSwallowsTouches);
		} else {
			CCTouchDispatcher::sharedDispatcher()->removeDelegate(this);
		}
	}
}

void UICompoment::onEnter ()
{
	
}

void UICompoment::onEnterTransitionDidFinish ()
{
	
}

void UICompoment::onExit ()
{
}

UICompoment* UICompoment::getUIParent()
{
	if (m_pParent != NULL && m_pParent != this) {
		UICompoment* pUI = dynamic_cast<UICompoment*>(m_pParent);
		if (pUI != NULL) {
			return pUI;
		}
	}
	return NULL;
}

bool UICompoment::isEnableInSceneGraph()
{
	UICompoment* pUI = this;
	do {
		if (pUI->isEnable() && pUI->getIsVisible()) {

		} else {
			return false;
		}
		pUI = pUI->getUIParent();
	} while (pUI != NULL);
	return true;
}

CCPoint UICompoment::screenToLocal(CCPoint const &sp)
{
	CCPoint cp = CCDirector::sharedDirector()->convertToGL( sp );
	cp = convertToNodeSpace(cp);
	return cp;
}


bool UICompoment::includeBounds(float localX, float localY)
{
	return Math::includeRectPoint(0, 0, mBounds.width, mBounds.height, localX, localY);
}

/////////////////////////////////////////////////////////////////////////////////////
// Touches and Interactive
/////////////////////////////////////////////////////////////////////////////////////
TouchEvent UICompoment::toTouchEvent(CCTouch *pTouch)
{
	TouchEvent ret;
	CCPoint cp;
#if COCOS2D_VERSION == 0x00010001
	cp = screenToLocal( pTouch->locationInView() );
	ret.point = Point2D(cp.x, cp.y);
	cp = screenToLocal( pTouch->previousLocationInView() );
	ret.prewPoint = Point2D(cp.x, cp.y);
#else
	cp = screenToLocal( pTouch->locationInView(0) );
	ret.point = Point2D(cp.x, cp.y);
	cp = screenToLocal( pTouch->previousLocationInView(0) );
	ret.prewPoint = Point2D(cp.x, cp.y);
#endif

	ret.sender = this;

	return ret;
}

bool UICompoment::ccTouchBegan(CCTouch *pTouch, CCEvent *pEvent)
{	
	if (!isEnableInSceneGraph()) {
		return false;
	}
	UICompoment* pUI = dynamic_cast<UICompoment*>(m_pParent);
	if (pUI != NULL && pUI->mIsClipBounds) {
#if COCOS2D_VERSION == 0x00010001
		CCPoint pcp = pUI->screenToLocal(pTouch->locationInView());
#else
		CCPoint pcp = pUI->screenToLocal(pTouch->locationInView(0));
#endif
		if (!pUI->includeBounds(pcp.x, pcp.y)) {
			return false;
		}
	}
	TouchEvent const &cp = toTouchEvent(pTouch);
	if (includeBounds(cp.point.x, cp.point.y)) {
		onTouchBegan(&cp);
		for (set<ITouchListener*>::iterator it = mTouchListeners.begin(); it!=mTouchListeners.end(); ++it) {
			(*it)->onTouchBegan(this, &cp);
		}
		return true;
	} else {
		return false;
	}
}

void UICompoment::ccTouchMoved(CCTouch *pTouch, CCEvent *pEvent)
{
	TouchEvent	const &cp = toTouchEvent(pTouch);
	onTouchMoved(&cp);
	for (set<ITouchListener*>::iterator it = mTouchListeners.begin(); it!=mTouchListeners.end(); ++it) {
		(*it)->onTouchMoved(this, &cp);
	}
}

void UICompoment::ccTouchEnded(CCTouch *pTouch, CCEvent *pEvent)
{
	TouchEvent	const &cp = toTouchEvent(pTouch);
	onTouchEnded(&cp);
	for (set<ITouchListener*>::iterator it = mTouchListeners.begin(); it!=mTouchListeners.end(); ++it) {
		(*it)->onTouchEnded(this, &cp);
	}
}

void UICompoment::ccTouchCancelled(CCTouch *pTouch, CCEvent *pEvent)
{
	TouchEvent	const &cp = toTouchEvent(pTouch);
	onTouchCancelled(&cp);
// 	for (set<ITouchListener*>::iterator it = mTouchListeners.begin(); it!=mTouchListeners.end(); ++it) {
// 		(*it)->onTouchEnded(this, &cp);
// 	}
}

/*
void 	TouchNode::ccTouchesBegan		(CCSet *pTouches, CCEvent *pEvent){
printf("TouchNode[%d]::ccTouchesBegan\n", this);
}
void 	TouchNode::ccTouchesMoved		(CCSet *pTouches, CCEvent *pEvent){
printf("TouchNode[%d]::ccTouchesMoved\n", this);
}
void 	TouchNode::ccTouchesEnded		(CCSet *pTouches, CCEvent *pEvent){
printf("TouchNode[%d]::ccTouchesEnded\n", this);
}
void 	TouchNode::ccTouchesCancelled	(CCSet *pTouches, CCEvent *pEvent){
printf("TouchNode[%d]::ccTouchesCancelled\n", this);
}
*/
void UICompoment::addTouchListener(ITouchListener* listener)
{
	mTouchListeners.insert(listener);
}

void UICompoment::removeTouchListener(ITouchListener* listener)
{
	mTouchListeners.erase(listener);
}


/////////////////////////////////////////////////////////////////////////////////////
//
/////////////////////////////////////////////////////////////////////////////////////
u16 UICompoment::getBorderSize()
{
	return mLayerRect.border_size;
}

u16 UICompoment::getWidth()
{
	return mBounds.width;
}

u16 UICompoment::getHeigh()
{
	return mBounds.height;
}

void UICompoment::setSize(u16 w, u16 h)
{
	mBounds.width = w;
	mBounds.height = h;
}


bool UICompoment::isClipBounds()
{
	return mIsClipBounds;
}

void UICompoment::setClipBounds(bool clip)
{
	mIsClipBounds = clip;
}

UILayerRect& UICompoment::getLayerRect()
{
	return mLayerRect;
}

void UICompoment::setLayerRect(UILayerRect const &rect)
{
	mLayerRect = rect;
}
/////////////////////////////////////////////////////////////////////////////////////
//
/////////////////////////////////////////////////////////////////////////////////////

void UICompoment::update(ccTime dt)
{

}

void UICompoment::render(Graphics2D *g)
{
	mLayerRect.render(g, 0, 0, getWidth(), getHeigh());
}

void UICompoment::clipLocalBounds()
{
	CCPoint pt = this->convertToWorldSpace(CCPointZero);
	// scissorRect is the rectangle you want to show.
	// 坐标系为屏幕坐标系，向上移动高度
	CCEGLView::sharedOpenGLView().setScissorInPoints(
		pt.x, pt.y - getHeigh(),
		getWidth(),
		getHeigh());
}

void UICompoment::visit(void)
{
	if (mIsClipBounds) {
		glEnable(GL_SCISSOR_TEST);
		clipLocalBounds();
		CCNode::visit();
		glDisable(GL_SCISSOR_TEST);
	}
	else {
		CCNode::visit();
	}
}

void UICompoment::draw(void)
{
	Graphics2D g2d;
	render(&g2d);
}





//////////////////////////////////////////////////////////////////////////

UILayerRect::UILayerRect(UILayerStyle style)
{
	this->style = style;
	this->back_color = COLOR_GRAY;
	this->border_color = COLOR_WHITE;
	this->border_size = 2;
}

UILayerRect::~UILayerRect()
{
}


UILayerRect* UILayerRect::createWithColor(Color const &color, Color const &bcolor)
{
	UILayerRect* ret = new UILayerRect();
	ret->style = UILAYER_STYLE_COLOR;
	ret->back_color = color;
	ret->border_color = bcolor;
	return ret;
}

UILayerRect* UILayerRect::createWithImage(IImage *src, UILayerStyle style, u16 clipsize)
{
	UILayerRect* ret = new UILayerRect();
	if (src != NULL) {
		ret->clipSize = clipsize;
		ret->image = src;
		ret->style = style;
	}
	return ret;
}


void UILayerRect::render(Graphics2D *g, float x, float y, float w, float h)
{
	if (style == UILAYER_STYLE_NULL) {
		return;
	}
	g->translate(x, y);

	switch(style)
	{
	case UILAYER_STYLE_COLOR:
		renderColor(g, w, h);
		break;
	case UILAYER_STYLE_IMAGE_ALL_9:
		renderAll9(g, w, h);
		break;
	case UILAYER_STYLE_IMAGE_ALL_8:
		renderAll8(g, w, h);
		break;
	case UILAYER_STYLE_IMAGE_H_012:
		renderH012(g, w, h);
		break;
	case UILAYER_STYLE_IMAGE_V_036:
		renderV036(g, w, h);
		break;

	case UILAYER_STYLE_IMAGE_HLM:
		renderHLM(g, w, h);
		break;
	case UILAYER_STYLE_IMAGE_VTM:
		renderVTM(g, w, h);
		break;

	case UILAYER_STYLE_IMAGE_BACK_4:
		renderBack4(g, w, h);
		break;
	case UILAYER_STYLE_IMAGE_BACK_4_CENTER:
		renderBack4Center(g, w, h);
		break;
	}

	g->translate(-x, -y);
	
}

void UILayerRect::renderColor(Graphics2D* g, float w, float h)
{
	g->setColor(border_color);
	g->fillRect(0, 0, w, h);
	g->setColor(back_color);
	g->fillRect(border_size, border_size, w-(border_size<<1), h-(border_size<<1));
}

void UILayerRect::drawRegionRound(Graphics2D *g,
					 float sx, float sy, float sw, float sh,
					 float x, float y, float w, float h)
{
	g->beginImage(image);
	for (int dx = 0; dx < w;) {
		float ssw = sw;
		if (dx+sw>w) {
			ssw = w - dx;
		}
		for (int dy = 0; dy < h;) {
			float ssh = sh;
			if (dy+sh>h) {
				ssh = h - dy;
			}
			g->drawImageRegion(sx, sy, ssw, ssh, x+dx, y+dy);
			dy += sh;
		}
		dx += sw;
	}
}

void UILayerRect::render0123_5678(Graphics2D* g, float w, float h)
{
	int b  = clipSize;
	int b2 = clipSize<<1;
	int iw = image->getWidth();
	int ih = image->getHeight();
	int twb2 = w - b2;
	int thb2 = h - b2;
	int iwb2 = iw - b2;
	int ihb2 = ih - b2;

	// top bottom
	drawRegionRound(g,    b,    0, iwb2,    b,   b,   0, twb2,    b);
	drawRegionRound(g,    b, ih-b, iwb2,    b,   b, h-b, twb2,    b);
	// left right
	drawRegionRound(g,    0,    b,    b, ihb2,   0,   b,    b, thb2);
	drawRegionRound(g, iw-b,    b,    b, ihb2, w-b,   b,    b, thb2);


	g->drawImageRegion(image,    0,    0, b, b,   0,   0);
	g->drawImageRegion(image, iw-b,    0, b, b, w-b,   0);
	g->drawImageRegion(image,    0, ih-b, b, b,   0, h-b);
	g->drawImageRegion(image, iw-b, ih-b, b, b, w-b, h-b);

}

void UILayerRect::renderAll8(Graphics2D* g, float w, float h)
{
	g->setColor(back_color);
	g->fillRect(clipSize, clipSize, w-(clipSize<<1), h-(clipSize<<1));
	g->setColorEnable(false);
	render0123_5678(g, w, h);
	g->setColorEnable(true);
}

void UILayerRect::renderAll9(Graphics2D* g, float w, float h)
{
	int b  = clipSize;
	int b2 = clipSize<<1;
	int iw = image->getWidth();
	int ih = image->getHeight();
	int twb2 = w - b2;
	int thb2 = h - b2;
	int iwb2 = iw - b2;
	int ihb2 = ih - b2;
	g->setColorEnable(false);
	drawRegionRound(g, b, b, iwb2, ihb2, b, b, twb2, thb2);
	render0123_5678(g, w, h);
	g->setColorEnable(true);
}

void UILayerRect::renderH012(Graphics2D* g, float w, float h)
{
	int b  = clipSize;
	int b2 = clipSize<<1;
	int iw = image->getWidth();
	int ih = image->getHeight();
	int twb2 = w - b2;
	int thb2 = h - b2;
	int iwb2 = iw - b2;
	int ihb2 = ih - b2;

	g->setColorEnable(false);
	// top
	drawRegionRound(g,    b,    0, iwb2,    ih,   b,   0, twb2,   h);

	g->drawImageRegionSize(image,    0,    0, b, ih,   0,   0, b, h);
	g->drawImageRegionSize(image, iw-b,    0, b, ih, w-b,   0, b, h);
	g->setColorEnable(true);

}

void UILayerRect::renderV036(Graphics2D* g, float w, float h)
{
	int b  = clipSize;
	int b2 = clipSize<<1;
	int iw = image->getWidth();
	int ih = image->getHeight();
	int twb2 = w - b2;
	int thb2 = h - b2;
	int iwb2 = iw - b2;
	int ihb2 = ih - b2;

	g->setColorEnable(false);
	// left
	drawRegionRound(g, 0, b, iw, ihb2, 0, b, w, thb2);

	g->drawImageRegion(image,    0,    0, iw, b,   0,   0);
	g->drawImageRegion(image,    0, ih-b, iw, b,   0, h-b);
	g->setColorEnable(true);
}

void UILayerRect::renderHLM(Graphics2D *g, float w, float h)
{
	int b  = clipSize;
	int b2 = clipSize<<1;
	int iw = image->getWidth();
	int ih = image->getHeight();
	int twb2 = w - b2;
	int thb2 = h - b2;
	int iwb1 = iw - b;
	int ihb2 = ih - b2;

	g->setColorEnable(false);

	// back
	drawRegionRound(g, b, b, iwb1, ihb2, b, b, twb2, thb2);
	// top bottom
	drawRegionRound(g,    b,    0, iwb1,    b,   b,   0, twb2,    b);
	drawRegionRound(g,    b, ih-b, iwb1,    b,   b, h-b, twb2,    b);
	// left right
	drawRegionRound(g,    0,    b,    b, ihb2,   0,   b,    b, thb2);
	

	g->pushTransform();
	g->translate(w, 0);
	g->scale(-1, 1);
	{
		drawRegionRound(g, 0,    b,    b, ihb2, 0,   b,    b, thb2);

		g->drawImageRegion(image, 0,    0, b, b, 0,   0);
		g->drawImageRegion(image, 0, ih-b, b, b, 0, h-b);
	}
	g->popTransform();

	g->drawImageRegion(image,    0,    0, b, b,   0,   0);
	g->drawImageRegion(image,    0, ih-b, b, b,   0, h-b);

	g->setColorEnable(true);
}

void UILayerRect::renderVTM(Graphics2D *g, float w, float h)
{
	int b  = clipSize;
	int b2 = clipSize<<1;
	int iw = image->getWidth();
	int ih = image->getHeight();
	int twb2 = w - b2;
	int thb2 = h - b2;
	int iwb2 = iw - b2;
	int ihb1 = ih - b;

	g->setColorEnable(false);

	// back
	drawRegionRound(g, b, b, iwb2, ihb1, b, b, twb2, thb2);

	// top bottom
	drawRegionRound(g,    b,    0, iwb2,    b,   b,   0, twb2,    b);
	// left right
	drawRegionRound(g,    0,    b,    b, ihb1,   0,   b,    b, thb2);
	drawRegionRound(g, iw-b,    b,    b, ihb1, w-b,   b,    b, thb2);


	g->pushTransform();
	g->translate(0, h);
	g->scale(1, -1);
	{
		drawRegionRound(g,    b, 0, iwb2,    b,   b, h-b, twb2,    b);

		g->drawImageRegion(image,    0, 0, b, b,   0, 0);
		g->drawImageRegion(image, iw-b, 0, b, b, w-b, 0);
	}
	g->popTransform();

	g->drawImageRegion(image,    0,    0, b, b,   0,   0);
	g->drawImageRegion(image, iwb2,    0, b, b, w-b,   0);

	g->setColorEnable(true);
}

void UILayerRect::renderBack4(Graphics2D* g, float w, float h)
{
	g->setColorEnable(false);
	g->drawImageSize(image, 0, 0, w, h);
	g->setColorEnable(true);
}

void UILayerRect::renderBack4Center(Graphics2D* g, float w, float h)
{
	g->setColorEnable(false);
	g->drawImage(image, 
		(w-image->getWidth())/2, 
		(h-image->getHeight())/2);
	g->setColorEnable(true);
}


//////////////////////////////////////////////////////////////////////////






UILayerRect* UILayerRectManager::addRect(string const &key, UILayerRect* rect)
{
	stuff[key] = rect;
	return (stuff[key]);
}

UILayerRect* UILayerRectManager::getRect(string const &key)
{
	map<string, UILayerRect*>::iterator it = stuff.find(key);
	if (it != stuff.end()) {
		return (it->second);
	}
	return NULL;
}

bool UILayerRectManager::destoryRect(string const &key)
{
	map<string, UILayerRect*>::iterator it = stuff.find(key);
	if (it != stuff.end()) {
		delete it->second;
		stuff.erase(it);
		return true;
	}
	return false;
}

void UILayerRectManager::destoryAll()
{
	for (map<string, UILayerRect*>::iterator it = stuff.begin(); it!=stuff.end(); it++) {
		delete it->second;
	}
	stuff.clear();
}

UILayerRect* UILayerRectManager::getDefaultRect(UICompoment* comp)
{
	return stuff["default"];
}

UILayerRectManager* UILayerRectManager::pInstance = NULL;
UILayerRectManager::UILayerRectManager(){
	pInstance = this;
}
UILayerRectManager::~UILayerRectManager(){
	pInstance = NULL;
}
UILayerRectManager* UILayerRectManager::getInstance()
{
	if (pInstance == NULL) {
		pInstance = new UILayerRectManager();
	}
	return pInstance;
}

// string UILayerValue::getImgName()
// {
// 	return imgName;
// }
// void UILayerValue::setImgName(string name)
// {
// 	imgName = name;
// }


}; // namespace mf
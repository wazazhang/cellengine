
#ifndef _MY_TouchTestNode_H
#define _MY_TouchTestNode_H

#include "cocos2d.h"
#include "MFEngine.h"

using namespace mf;
using namespace cocos2d;


class TouchTestNode : public cocos2d::CCLayer
{

private:

	float m_timer;
	int m_ticktime;
	bool m_mouseDown;

	CCellSprite*	actor_node;

	CCPoint			m_curPoint;


public:

	TouchTestNode();
	~TouchTestNode();

	virtual void	update(ccTime dt);

	virtual void	draw(void);

	
	virtual void ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent);
};

class TouchNode : public CCellSprite, public CCTargetedTouchDelegate 
{
public:

	int pickCount;

public:
	static TouchNode* node(CSpriteMeta* meta, int anim, float x, float y);

	TouchNode(CSpriteMeta *meta);

	virtual ~TouchNode(void);

	virtual bool 	ccTouchBegan		(CCTouch *pTouch, CCEvent *pEvent);
	virtual void 	ccTouchMoved		(CCTouch *pTouch, CCEvent *pEvent);
	virtual void 	ccTouchEnded		(CCTouch *pTouch, CCEvent *pEvent);
// 	virtual void 	ccTouchCancelled	(CCTouch *pTouch, CCEvent *pEvent);
// 	virtual void 	ccTouchesBegan		(CCSet *pTouches, CCEvent *pEvent);
// 	virtual void 	ccTouchesMoved		(CCSet *pTouches, CCEvent *pEvent);
// 	virtual void 	ccTouchesEnded		(CCSet *pTouches, CCEvent *pEvent);
// 	virtual void 	ccTouchesCancelled	(CCSet *pTouches, CCEvent *pEvent);


	virtual void touchDelegateRetain(){retain();};
	virtual void touchDelegateRelease(){release();};

	virtual void update(ccTime dt);

	virtual void render(Graphics2D* g);

};

class TouchNode2 : public TouchNode
{
public:
	static TouchNode2* node(CSpriteMeta* meta, int anim, float x, float y);

	TouchNode2(CSpriteMeta *meta);

	virtual ~TouchNode2(void);
};

class SelfNode : public CCellSprite
{
public:

	static SelfNode* node(CSpriteMeta* meta, int anim, float x, float y);

	SelfNode(CSpriteMeta* meta);
	virtual ~SelfNode();

	virtual void update(ccTime dt);

	virtual void render(Graphics2D* g);

};


#endif // _MY_NODE_H

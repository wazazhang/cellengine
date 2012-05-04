
#ifndef _MY_NODE_H
#define _MY_NODE_H

#include "cocos2d.h"
#include "MFEngine.h"

using namespace mf;
using namespace cocos2d;

extern mf::ResourceManager* getResourceManager();

class MyNode : public cocos2d::CCLayer
{
private:

	float m_timer;
	int m_ticktime;
	boolean m_mouseDown;

	CCellSprite*	actor_node;

	CCellMap*		map_node;

	CCellWorld*		world_node;

	CCPoint m_curPoint;

public:

	MyNode();
	~MyNode();

	virtual void	update(ccTime dt);

	virtual void	draw(void);

	
	virtual void ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent);
};


class SelfNode : public CCellSprite
{
public:

	static SelfNode* node(CSpriteMeta* meta, int anim, float x, float y);

	SelfNode(CSpriteMeta* meta);

	virtual void update(ccTime dt);

	virtual void render(Graphics2D* g);

};


#endif // _MY_NODE_H


#ifndef _MY_SceneTestNode_H
#define _MY_SceneTestNode_H

#include "cocos2d.h"
#include "MFEngine.h"

using namespace mf;
using namespace cocos2d;


class SceneTestNode : public cocos2d::CCLayer
{
private:

	float m_timer;
	int m_ticktime;
	bool m_mouseDown;

	CCellMap*		map_node_b;
	CCellMap*		map_node_s;
	CCellWorld*		world_node;
	CCellWorld*		scene_node;

	CCPoint			m_curPoint;

public:

	SceneTestNode();
	~SceneTestNode();

	virtual void	update(ccTime dt);

	virtual void	draw(void);

	virtual void ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent);
};


#endif // _MY_NODE_H

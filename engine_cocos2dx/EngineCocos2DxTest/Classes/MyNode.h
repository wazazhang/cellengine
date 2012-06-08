
#ifndef _MY_NODE_H
#define _MY_NODE_H

#include "cocos2d.h"
#include "MFEngine.h"

using namespace mf;
using namespace cocos2d;


class MyNode : public cocos2d::CCLayer
{
private:

	float m_timer;
	int m_ticktime;
	bool m_mouseDown;
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


#endif // _MY_NODE_H

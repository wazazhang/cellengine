
#ifndef _MY_UITestNode_H
#define _MY_UITestNode_H

#include "cocos2d.h"
#include "MFEngine.h"

using namespace mf;
using namespace cocos2d;


class MyUIEdit : public UIEdit
{
public:

	MyUIEdit(string const &res_root);

protected:

	virtual CCNode*	createComponent(XMLNode* e);


};

class UITestNode : public CCLayer, public ITouchListener
{
private:

	float m_timer;
	int m_ticktime;
	bool m_mouseDown;
	
	TextLayer*		text_layer;

	CCPoint			m_curPoint;

	Rectangle2D		m_textCamera;

	MyUIEdit*		m_uiEdit;

public:

	UITestNode();
	~UITestNode();

	virtual void	update(ccTime dt);

	virtual void	draw(void);

	
	virtual void ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent);
	virtual void ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent);


	virtual void onTouchBegan(UICompoment* sender, TouchEvent const *cp);
	virtual void onTouchMoved(UICompoment* sender, TouchEvent const *cp);
	virtual void onTouchEnded(UICompoment* sender, TouchEvent const *cp);
};



#endif // _MY_NODE_H

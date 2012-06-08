
#include "MyNode.h"
#include "MFEngine.h"

#include <iostream>
#include <string>
USING_NS_CC;

using namespace mf;
using namespace std;


MyNode::MyNode()
{
	wcout.imbue(locale("chs"));
	cout.imbue(locale("chs"));

	m_timer = 0;
	m_ticktime = 0;
	m_mouseDown = false;

	setIsTouchEnabled(true);
	schedule(schedule_selector(MyNode::update));




}

MyNode::~MyNode()
{

}


void MyNode::update(ccTime dt)
{
	int ticktime = m_ticktime;

	CCSize wsize = CCDirector::sharedDirector()->getWinSize();
	// 各种移动摄像机测试
	if (m_mouseDown) 
	{
		float dx = Math::getDirect(m_curPoint.x - wsize.width/2);
		float dy = Math::getDirect(m_curPoint.y - wsize.height/2);

		
	}
	

	m_timer += 0.1f;
	m_ticktime += 1;
}

void MyNode::draw()
{
	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	//if (false)
	{
		mf::Graphics2D g;
		g.pushTransform();

	
		

		g.popTransform();
	}
}


void MyNode::ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent)
{ 
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it); 


	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView(0) );
	m_curPoint = convertToNodeSpace(m_curPoint);

	m_mouseDown = true;

}

void MyNode::ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);

	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView(0) );
	m_curPoint = convertToNodeSpace(m_curPoint);

	
}

void MyNode::ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);
	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView(0) );
	m_curPoint = convertToNodeSpace(m_curPoint);
	
	m_mouseDown = false;
}


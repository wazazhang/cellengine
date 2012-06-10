
#include "TouchTestNode.h"
#include "MFEngine.h"
#include "Resource.h"

#include <iostream>
#include <string>
USING_NS_CC;

using namespace mf;
using namespace std;


TouchTestNode::TouchTestNode()
{
	m_timer = 0;
	m_ticktime = 0;
	m_mouseDown = false;

	setIsTouchEnabled(true);


	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	CellResource * res_test = MyResourceManager::getInstance()->addResource("edit/output/ccc.xml");
	CellResource* res_actor = MyResourceManager::getInstance()->addResource("actor_000000/output/actor.xml");
	CellResource* res_scene = MyResourceManager::getInstance()->addResource("scene_000000/output/scene.xml");

	// 单位
	actor_node = new CCellSprite(
		res_actor->getSpriteMeta("000000"));
	actor_node->setPosition(100, 100);
	addChild(actor_node);





	schedule(schedule_selector(TouchTestNode::update));
}

TouchTestNode::~TouchTestNode()
{
	delete actor_node;
}


void TouchTestNode::update(ccTime dt)
{
	int ticktime = m_ticktime;

	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	actor_node->nextCycFrame();
	m_timer += 0.1f;
	m_ticktime += 1;
}

void TouchTestNode::draw()
{
	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	
}


void TouchTestNode::ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent)
{ 
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it); 


	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView(0) );
	m_curPoint = convertToNodeSpace(m_curPoint);

	actor_node->setPosition(m_curPoint);
	m_mouseDown = true;


	// 添加一个测试触摸精灵
  	getParent()->addChild(TouchNode2::node(
  		actor_node->getMeta(), 
  		1, 
  		m_curPoint.x, m_curPoint.y));


	printf("screen(%f,%f) local(%f,%f)\n", 
		touch->locationInView(0).x,
		touch->locationInView(0).y, 
		m_curPoint.x,
		m_curPoint.y
		);
}

void TouchTestNode::ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);

	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView(0) );
	m_curPoint = convertToNodeSpace(m_curPoint);

	actor_node->setPosition(m_curPoint);

	getParent()->addChild(SelfNode::node(
		actor_node->getMeta(), 
		actor_node->getCurrentAnimate(), 
		m_curPoint.x, m_curPoint.y));

 //.//	printf("size for childs %d\n", getParent()->getChildrenCount());
}

void TouchTestNode::ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);
	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView(0) );
	m_curPoint = convertToNodeSpace(m_curPoint);
	actor_node->setPosition(m_curPoint);
	m_mouseDown = false;
}


/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
TouchNode* TouchNode::node(CSpriteMeta* meta, int anim, float x, float y)
{
	TouchNode* ret = new TouchNode(meta);
	ret->setCurrentFrame(anim, 0);
	ret->setPosition(x, y);
	ret->autorelease();
	return ret;
}

TouchNode::TouchNode(CSpriteMeta *meta) : CCellSprite(meta)
{
	this->pickCount = 0;

	CCTouchDispatcher::sharedDispatcher()->addTargetedDelegate(this, 0, true);
	schedule(schedule_selector(TouchNode::update));
}

TouchNode::~TouchNode(void)
{
	CCTouchDispatcher::sharedDispatcher()->removeDelegate(this);	
	printf("destory touch node ~\n");
}

void TouchNode::update(ccTime dt)
{
	
}

void TouchNode::render(Graphics2D* g)
{
	g->setColor(COLOR_BLUE);
	g->fillRect(0,0,32,32);
	g->setColor(1,1,1,1);

	CCPoint cp = CCDirector::sharedDirector()->convertToGL(m_tPosition);
	glScissor(cp.x, cp.y, 32, 32);
	glEnable(GL_SCISSOR_TEST);
	glDisable(GL_COLOR_ARRAY);
	CCellSprite::render(g);
	glDisable(GL_SCISSOR_TEST);

	CCD ccd;
	getMeta()->getVisibleBounds(getCurrentAnimate(), ccd);
	g->setColor(COLOR_WHITE);
	g->drawRect(ccd.X1, ccd.Y1, ccd.getWidth(), ccd.getHeight());
}


bool 	TouchNode::ccTouchBegan		(CCTouch *pTouch, CCEvent *pEvent)
{
	CCTouch* touch = pTouch; 

	CCPoint m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView(0) );
	m_curPoint = convertToNodeSpace(m_curPoint);

	CCD ccd;
	getMeta()->getVisibleBounds(getCurrentAnimate(), ccd);
	if (Math::includeRectPoint(ccd.X1, ccd.Y1, ccd.X2, ccd.Y2, m_curPoint.x, m_curPoint.y)) {
		printf("TouchNode[%d]::ccTouchBegan\n", this);
		return true;
	} else {
		return false;
	}
}

void 	TouchNode::ccTouchMoved		(CCTouch *pTouch, CCEvent *pEvent){
	printf("TouchNode[%d]::ccTouchMoved\n", this);
}
void 	TouchNode::ccTouchEnded		(CCTouch *pTouch, CCEvent *pEvent){
	printf("TouchNode[%d]::ccTouchEnded\n", this);
}
/*
void 	TouchNode::ccTouchCancelled	(CCTouch *pTouch, CCEvent *pEvent){
	printf("TouchNode[%d]::ccTouchCancelled\n", this);
}
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
/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
TouchNode2* TouchNode2::node(CSpriteMeta* meta, int anim, float x, float y){
	TouchNode2* ret = new TouchNode2(meta);
	ret->setCurrentFrame(anim, 0);
	ret->setPosition(x, y);
	ret->autorelease();
	return ret;
}

TouchNode2::TouchNode2(CSpriteMeta *meta) : TouchNode(meta) {

}

TouchNode2::~TouchNode2(void){

}


/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
SelfNode* SelfNode::node(CSpriteMeta* meta, int anim, float x, float y)
{
	SelfNode* ret = new SelfNode(meta);
	ret->setCurrentFrame(anim, 0);
	ret->setPosition(x, y);
	ret->autorelease();
	ret->schedule(schedule_selector(SelfNode::update));
	return ret;
}

SelfNode::SelfNode(CSpriteMeta* meta) : CCellSprite(meta) {
	
}
SelfNode::~SelfNode(){
	printf("destory self node ~\n");
}
void SelfNode::update(ccTime dt)
{
	if (nextFrame()) {
		this->setIsVisible(false);
		this->removeFromParentAndCleanup(true);
	}
}

void SelfNode::render(Graphics2D* g)
{
	float alpha = (((float)getCurrentFrame()) / getMeta()->getFrameCount(getCurrentFrame()));
	g->setBlend(BLEND_NORMAL);
	g->setColorAlpha(1 - alpha);

	CCellSprite::render(g);
}

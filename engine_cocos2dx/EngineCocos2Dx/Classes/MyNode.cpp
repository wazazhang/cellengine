
#include "MyNode.h"
#include "MFEngine.h"


USING_NS_CC;

using namespace mf;
using namespace std;

mf::ResourceManager* s_resourceManager;

ResourceManager* getResourceManager()
{
	if (s_resourceManager == NULL) {
		s_resourceManager = new ResourceManager();
	}
	return s_resourceManager;
}


MyNode::MyNode()
{
	m_timer = 0;
	m_ticktime = 0;
	m_mouseDown = false;

	setIsTouchEnabled(true);
#if FALSE
	string xmldata;
	loadAllText("edit/output/ccc.xml", xmldata);
	//XMLNode* ndf = XMLNode::parseFromText(xmldata.c_str());
	XMLNode* ndf = XMLNode::parseFromFile("edit/output/ccc.xml");
	printf(ndf->toString().c_str());
	delete ndf;
#endif

	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	getResourceManager()->addResource("edit/output/ccc.xml");
	getResourceManager()->addResource("actor_000000/output/actor.xml");
	
	CellResource * res_test = getResourceManager()->getResource("edit/output/ccc.xml");
	CellResource* res_actor = getResourceManager()->getResource("actor_000000/output/actor.xml");
	
	map_node = new CCellMapDirect(
		res_test->getMapMeta("unamed_Map"),
		400, 400);
	map_node->setPosition(50, wsize.height-50);
	addChild(map_node, -1);

	actor_node = new CCellSprite(
		res_actor->getSpriteMeta("000000"));
	actor_node->setPosition(100, 100);
	addChild(actor_node);



	schedule(schedule_selector(MyNode::update));

}

MyNode::~MyNode()
{
	delete actor_node;
	delete map_node;
}



void MyNode::ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent)
{ 
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it); 
	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	actor_node->setPosition(m_curPoint);
	m_mouseDown = true;
}

void MyNode::ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);
	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	actor_node->setPosition(m_curPoint);

	getParent()->addChild(SelfNode::node(
		actor_node->getMeta(), 
		actor_node->getCurrentAnimate(), 
		m_curPoint.x, m_curPoint.y));

 	printf("size for childs %d\n", getParent()->getChildrenCount());
}

void MyNode::ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);
	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	actor_node->setPosition(m_curPoint);
	m_mouseDown = false;
}

void MyNode::update(ccTime dt)
{
	int ticktime = m_ticktime;

	CCSize wsize = CCDirector::sharedDirector()->getWinSize();
	if (m_mouseDown) 
	{
		map_node->moveCamera(
			Math::getDirect(m_curPoint.x - wsize.width/2), 
			-Math::getDirect(m_curPoint.y - wsize.height/2));
	}
	actor_node->nextCycFrame();
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
		
		g.translate(0, wsize.height);
		g.scale(1, -1);

		g.popTransform();
	}
}


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

void SelfNode::update(ccTime dt)
{
	if (nextFrame()) {
		this->setIsVisible(false);
		removeFromParentAndCleanup(false);
	}
}

void SelfNode::render(Graphics2D* g)
{
	float alpha = (((float)getCurrentFrame()) / getMeta()->getFrameCount(getCurrentFrame()));
	g->setBlend(BLEND_NORMAL);
	g->setColorAlpha(1 - alpha);

	CCellSprite::render(g);
}

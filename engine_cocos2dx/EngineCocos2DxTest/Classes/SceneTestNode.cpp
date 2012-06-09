
#include "SceneTestNode.h"
#include "Resource.h"
#include <iostream>
#include <string>
USING_NS_CC;

using namespace mf;
using namespace std;

SceneTestNode::SceneTestNode()
{
	m_timer = 0;
	m_ticktime = 0;
	m_mouseDown = false;

	setIsTouchEnabled(true);


	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	CellResource * res_test = MyResourceManager::getInstance()->addResource("edit/output/ccc.xml");
	CellResource* res_actor = MyResourceManager::getInstance()->addResource("actor_000000/output/actor.xml");
	CellResource* res_scene = MyResourceManager::getInstance()->addResource("scene_000000/output/scene.xml");
	
	// 缓冲地图
 	map_node_b = new CCellMapBuffer(
 		res_test->getMapMeta("unamed_Map"),
 		400, 400);
 	map_node_b->setPosition(50, 50);
	//addChild(map_node_b, -1);

	// 直接地图
 	map_node_s = new CCellMapDirect(
 		res_test->getMapMeta("unamed_Map"),
 		200, 200);
 	map_node_s->setPosition(100, 100);
 	//addChild(map_node_s, -1);

	// 场景
	world_node = new CCellWorld();
	world_node->init(
		res_test, 
		res_test->getSetWorld("scene000000"));
	world_node->setPosition(50, 50);
	world_node->setCameraSize(400, 400);
	//addChild(world_node);

	// 场景吞食天地 
	scene_node = new CCellWorld();
	scene_node->init(
		res_scene, 
		res_scene->getSetWorld("000000"));
	scene_node->setPosition(0, 0);
	scene_node->setCameraSize(wsize.width, wsize.height);
	addChild(scene_node, -1);



	schedule(schedule_selector(SceneTestNode::update));
}

SceneTestNode::~SceneTestNode()
{
	delete map_node_s;
	delete map_node_b;
	delete scene_node;
	delete world_node;
}


void SceneTestNode::update(ccTime dt)
{
	int ticktime = m_ticktime;

	CCSize wsize = CCDirector::sharedDirector()->getWinSize();
	// 各种移动摄像机测试
	if (m_mouseDown) 
	{
		float dx = Math::getDirect(m_curPoint.x - wsize.width/2);
		float dy = Math::getDirect(m_curPoint.y - wsize.height/2);

		map_node_s->moveCamera(dx, dy);
		map_node_b->moveCamera(dx, dy);
		world_node->moveCamera(dx, dy);
		scene_node->moveCamera(dx, dy);

	}
	m_timer += 0.1f;
	m_ticktime += 1;
}

void SceneTestNode::draw()
{
	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	
}


void SceneTestNode::ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent)
{ 
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it); 


	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	m_curPoint = convertToNodeSpace(m_curPoint);

	m_mouseDown = true;

}

void SceneTestNode::ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);

	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	m_curPoint = convertToNodeSpace(m_curPoint);

}

void SceneTestNode::ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);
	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	m_curPoint = convertToNodeSpace(m_curPoint);

	m_mouseDown = false;
}


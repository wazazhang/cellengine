
#include "MFCellWorld.h"

namespace mf
{
	using namespace std;
	using namespace cocos2d;


	CCellWorld::CCellWorld()
	{
	}
	CCellWorld::~CCellWorld()
	{
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	void CCellWorld::setCameraSize(float width, float height)
	{
		m_curLayer->setCameraSize(width, height);
	}

	void CCellWorld::locateCamera(float x, float y)
	{
		m_curLayer->locateCamera(x, y);
	}

	void CCellWorld::moveCamera(float x, float y)
	{
		m_curLayer->moveCamera(x, y);
	}
	

	void CCellWorld::visit()
	{
		Graphics2D g;
		g.clipRect(getPositionX(), getPositionY(), m_curLayer->m_camera.width, m_curLayer->m_camera.height);
		//setPosition(-m_camera.x, -m_camera.y);
		CCNode::visit();
		g.clipClean();
	}

	void CCellWorld::draw(void)
	{
		
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////


	void CCellWorld::init(CellResource* res, WorldSet const &worldset)
	{
		this->m_res = res;
		this->m_worldset = worldset;

		m_curLayer = new CCellWorldLayer(this);
		m_curLayer->autorelease();

		// 		for (map<int, WorldObjectMap>::const_iterator it = m_worldset.Maps.begin(); 
		// 			it != m_worldset.Maps.end(); ++it) {
		// 				addWorldChild(createChild((it->second)));
		// 		}
		for (map<int, WorldObjectImage>::const_iterator it = m_worldset.Images.begin(); 
			it != m_worldset.Images.end(); ++it) {
				addWorldChild(createWorldObject((it->second)));
		}
		for (map<int, WorldObjectSprite>::const_iterator it = m_worldset.Sprs.begin(); 
			it != m_worldset.Sprs.end(); ++it) {
				addWorldChild(createWorldObject((it->second)));
		}
		for (map<int, WorldObjectWaypoint>::const_iterator it = m_worldset.WayPoints.begin(); 
			it != m_worldset.WayPoints.end(); ++it) {
				addWorldChild(createWorldObject((it->second)));
		}
		for (map<int, WorldObjectRegion>::const_iterator it = m_worldset.Regions.begin(); 
			it != m_worldset.Regions.end(); ++it) {
				addWorldChild(createWorldObject((it->second)));
		}

		addChild(m_curLayer);
	}

	void CCellWorld::addWorldChild(CCNode* pChild)
	{
		if (pChild != NULL) {
// 			if (instanceof<CCellWorldMap>(*pChild)) {
// 			}
			m_curLayer->addChild(pChild, pChild->getZOrder());
		}
	}

// 
// 	CCNode* CCellWorld::createWorldObject(WorldObjectMap const &child)
// 	{
// 		CMapMeta* pMeta = m_res->getMapMeta(child.MapID);
// 		if (pMeta != NULL) {
// 			m_pCurMap = pMeta;
// 		}
// 		return NULL;
// 	}

	CCNode* CCellWorld::createWorldObject(WorldObjectSprite const &child)
	{
		CSpriteMeta* pMeta = m_res->getSpriteMeta(child.SprID);
		if (pMeta != NULL) {
			CCellWorldSprite *pspr = new CCellWorldSprite(pMeta, child);
			pspr->autorelease();
			return pspr;
		}
		return NULL;
	}

	CCNode* CCellWorld::createWorldObject(WorldObjectImage const &child)
	{
		ITiles *pTiles = m_res->getImages(child.ImagesID);
		if (pTiles != NULL && pTiles->isActive(child.TileID)) {
			CCellWorldImage *pimg = new CCellWorldImage(pTiles, child);
			pimg->autorelease();
			return pimg;
		}
		return NULL;
	}

	CCNode* CCellWorld::createWorldObject(WorldObjectWaypoint const &child)
	{
		return NULL;
	}
	CCNode* CCellWorld::createWorldObject(WorldObjectRegion const &child)
	{
		return NULL;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////
	


	CCellWorldLayer::CCellWorldLayer(CCellWorld* wd)
	{
		this->m_world = wd;
		for (map<int, WorldObjectMap>::const_iterator it = wd->m_worldset.Maps.begin(); 
			it != wd->m_worldset.Maps.end(); ++it) {
				CMapMeta* meta = wd->m_res->getMapMeta((it->second).MapID);
				if (meta!=NULL) {
					this->m_pCurMap = meta;
				}
		}
		setCameraSize(400, 400);
	}

	CCellWorldLayer::~CCellWorldLayer(){

	}

	void CCellWorldLayer::setCameraSize(float width, float height)
	{
		if (m_pCurMap != NULL) {
			m_camera.width  = MIN(width,  m_pCurMap->getWidthPixel());
			m_camera.height = MIN(height, m_pCurMap->getHeighPixel());
		} else {
			m_camera.width = width;
			m_camera.height = height;
		}
	}

	void CCellWorldLayer::locateCamera(float x, float y)
	{
		if (m_pCurMap != NULL) {
			m_camera.x = MAX(0, MIN(x, m_pCurMap->getWidthPixel()-m_camera.width));
			m_camera.y = MAX(0, MIN(y, m_pCurMap->getHeighPixel()-m_camera.height));
		} else {
			m_camera.x = x;
			m_camera.y = y;
		}
		setPosition(-m_camera.x, -m_camera.y);
	}

	void CCellWorldLayer::moveCamera(float x, float y)
	{
		locateCamera(m_camera.x + x, m_camera.y + y);
	}

	void CCellWorldLayer::draw(void)
	{
		Graphics2D g;
		glDisable(GL_COLOR_ARRAY);
		g.pushTransform();
		//g.scale(1, -1);
		
		m_pCurMap->getTiles()->renderBegin(&g);
		m_pCurMap->renderBath(
			&g, 
			m_camera.x, 
			m_camera.y, 
			m_camera.width, 
			m_camera.height);
		m_pCurMap->getTiles()->renderEnd(&g);
		//g->drawRect(0, 0, mCamera.width, mCamera.height);
		
		g.popTransform();
		glEnable(GL_COLOR_ARRAY);
		
	}




	//////////////////////////////////////////////////////////////////////////////////////////////////////



	

	
	CCellWorldSprite::CCellWorldSprite(CSpriteMeta* pMeta, WorldObjectSprite const &data) : 
	CCellSprite(pMeta) {
		m_data = data;
		setPosition(data.X, data.Y);
		setCurrentFrame(data.Anim, data.Frame);
	}
	CCellWorldSprite::~CCellWorldSprite(){

	}
	

	
	CCellWorldImage::CCellWorldImage(ITiles *pTiles, WorldObjectImage const &data) 
	{
		m_tiles = pTiles;
		m_data = data;
		m_lbounds.x = 0;
		m_lbounds.y = 0;

		switch (m_data.Trans)
		{
		case ImageTrans::R_90:
		case ImageTrans::R_270:
		case ImageTrans::MR_90:
		case ImageTrans::MR_270:
			m_lbounds.width = pTiles->getHeight(data.TileID);
			m_lbounds.height = pTiles->getWidth(data.TileID);
			break;
		default:
			m_lbounds.width = pTiles->getWidth(data.TileID);
			m_lbounds.height = pTiles->getHeight(data.TileID);
			break;
		}

		switch (m_data.Anchor)
		{
		case ImageAnchor::L_T:
			
			break;
		case ImageAnchor::C_T:
			m_lbounds.x = -m_lbounds.width/2;
			break;
		case ImageAnchor::R_T:
			m_lbounds.x = -m_lbounds.width;
			break;

		case ImageAnchor::L_C:
			m_lbounds.y = -m_lbounds.height/2;
			break;
		case ImageAnchor::C_C:
			m_lbounds.x = -m_lbounds.width/2;
			m_lbounds.y = -m_lbounds.height/2;
			break;
		case ImageAnchor::R_C:
			m_lbounds.x = -m_lbounds.width;
			m_lbounds.y = -m_lbounds.height/2;
			break;

		case ImageAnchor::L_B:
			m_lbounds.y = -m_lbounds.height;
			break;
		case ImageAnchor::C_B:
			m_lbounds.x = -m_lbounds.width/2;
			m_lbounds.y = -m_lbounds.height;
			break;
		case ImageAnchor::R_B:
			m_lbounds.x = -m_lbounds.width;
			m_lbounds.y = -m_lbounds.height;
			break;

		}



		setPosition(data.X, data.Y);
	}
	CCellWorldImage::~CCellWorldImage(){
		
	}
	void CCellWorldImage::draw(void){
		Graphics2D g;
		m_tiles->renderBegin(&g);
		m_tiles->render(&g, m_data.TileID, m_lbounds.x, m_lbounds.y, m_data.Trans);
		m_tiles->renderEnd(&g);
// 		g.drawRect(m_lbounds.x, m_lbounds.y, m_lbounds.width, m_lbounds.height);
// 		g.drawLine(0, -100, 0, 100);
// 		g.drawLine(-100, 0, 100, 0);
	}



};
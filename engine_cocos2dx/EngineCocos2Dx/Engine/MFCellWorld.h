
#ifndef _MF_CELL_WORLD
#define _MF_CELL_WORLD

#include "MFType.h"
#include "MFGraphics2D.h"
#include "MFGameObject.h"
#include "MFCellResource.h"
#include <vector>

#include "cocos2d.h"

namespace mf
{
	using namespace std;
	using namespace cocos2d;
	
	class CC_DLL CCellWorldLayer;

	/////////////////////////////////////////////////////////////////////////////////


	class CC_DLL  CCellWorld : public CCNode
	{
		friend class CCellWorldLayer;

	protected:
		CellResource*	m_res;
		WorldSet		m_worldset;

		CCellWorldLayer* m_curLayer;

	protected:

		void addWorldChild(CCNode* pChild);

	public:
		CCellWorld();
		virtual ~CCellWorld();

		virtual void init(CellResource* res, WorldSet const &worldset);
		
		void setCameraSize(float width, float height);
		void locateCamera(float x, float y);
		void moveCamera(float x, float y);

		virtual void visit(void);
		virtual void draw(void);

		virtual CCNode* createWorldObject(WorldObjectSprite const &child);
		virtual CCNode* createWorldObject(WorldObjectImage const &child);
		virtual CCNode* createWorldObject(WorldObjectWaypoint const &child);
		virtual CCNode* createWorldObject(WorldObjectRegion const &child);
	};



	/////////////////////////////////////////////////////////////////////////////////

	class CC_DLL  CCellWorldLayer : public CCNode
	{
		friend class CCellWorld;
	protected:
		CCellWorld*		m_world;
		CMapMeta*		m_pCurMap;
		Rectangle2D		m_camera;
		vector<CCNode*>	m_childs;
	public:
		CCellWorldLayer(CCellWorld* wd);
		virtual ~CCellWorldLayer();
		virtual void draw(void);
		virtual void visit(void);
		void setCameraSize(float width, float height);
		void locateCamera(float x, float y);
		void moveCamera(float x, float y);
		
		void addWorldChild(CCNode* c);
		void removeWorldChild(CCNode* c);
		void moveWorldChild(CCNode* c, float x, float y);

	};

	/////////////////////////////////////////////////////////////////////////////////

	class CC_DLL  CCellWorldSprite : public CCellSprite
	{
	protected:
		WorldObjectSprite m_data;
	public:
		CCellWorldSprite(CSpriteMeta* pMeta, WorldObjectSprite const &data);
		virtual ~CCellWorldSprite();
	};

	/////////////////////////////////////////////////////////////////////////////////

	class CC_DLL  CCellWorldImage : public CCNode
	{
	protected:
		WorldObjectImage m_data;
		ITiles* m_tiles;
		Rectangle2D m_lbounds;
	public:
		CCellWorldImage(ITiles *pTiles, WorldObjectImage const &data);
		virtual ~CCellWorldImage();
		virtual void draw(void);
	};


};

#endif // #define _MF_CELL_WORLD

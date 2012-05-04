
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
	
	class CCellWorldLayer;

	/////////////////////////////////////////////////////////////////////////////////


	class CCellWorld : public CCNode
	{
		friend CCellWorldLayer;

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

	class CCellWorldLayer : public CCNode
	{
		friend CCellWorld;
	protected:
		CCellWorld*		m_world;
		CMapMeta*		m_pCurMap;
		Rectangle2D		m_camera;
	public:
		CCellWorldLayer(CCellWorld* wd);
		virtual ~CCellWorldLayer();
		virtual void draw(void);

		void setCameraSize(float width, float height);
		void locateCamera(float x, float y);
		void moveCamera(float x, float y);

		
	};

	/////////////////////////////////////////////////////////////////////////////////

	class CCellWorldSprite : public CCellSprite
	{
	protected:
		WorldObjectSprite m_data;
	public:
		CCellWorldSprite(CSpriteMeta* pMeta, WorldObjectSprite const &data);
		virtual ~CCellWorldSprite();
	};

	/////////////////////////////////////////////////////////////////////////////////

	class CCellWorldImage : public CCNode
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

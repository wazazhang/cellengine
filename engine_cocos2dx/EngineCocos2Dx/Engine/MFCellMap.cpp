

#include "MFCellMap.h"
#include "MFGameObject.h"
#include "MFType.h"

namespace mf
{
	using namespace std;

	CMapMeta::CMapMeta(ITiles* tiles, int cellw, int cellh, int layerCount, int widthBlock, int heightBlock) 
	{
		mCellW			= cellw;
		mCellH			= cellh;
		mWidthBlock 	= widthBlock;
		mHeightBlock	= heightBlock;
		mWidthPixel		= widthBlock * cellw;
		mHeightPixel	= heightBlock * cellh;

		mIsCyc 			= false;

		mTiles 			= tiles;

		mLayers.resize(layerCount);

		for (int ly=0; ly<layerCount; ly++) {
			mLayers[ly].TileMatrix2D.resize(heightBlock);
			mLayers[ly].FlipMatrix2D.resize(heightBlock);
			mLayers[ly].FlagMatrix2D.resize(heightBlock);
			for (int by=0; by<heightBlock; by++) {
				mLayers[ly].TileMatrix2D[by].resize(widthBlock);
				mLayers[ly].FlipMatrix2D[by].resize(widthBlock);
				mLayers[ly].FlagMatrix2D[by].resize(widthBlock);
			}
		}

		mBlockTypes.resize(8);
	}

	int CMapMeta::fillMapCell(int layer, int bx, int by, int tileID, int flip, int flag)
	{
		mLayers[layer].TileMatrix2D[by][bx] = tileID;
		mLayers[layer].FlipMatrix2D[by][bx] = flip;
		mLayers[layer].FlagMatrix2D[by][bx] = flag;
		return layer;
	}

	int CMapMeta::defineBlockType(int index,
		int BlocksType, int BlocksMask, 
		int BlocksX1, int BlocksY1, 
		int BlocksX2, int BlocksY2)
	{
		if (BlocksType == CCD::CD_TYPE_RECT) {
			mBlockTypes[index] = CCD::createCDRect_2Point(
				BlocksMask, 
				BlocksX1, BlocksY1,
				BlocksX2, BlocksY2);
		} else {
			mBlockTypes[index] = CCD::createCDLine(
				BlocksMask, 
				BlocksX1, BlocksY1,
				BlocksX2, BlocksY2);
		}
		return index;
	}

	ITiles* CMapMeta::getTiles() {
		return mTiles;
	}

	int CMapMeta::getWidthPixel() {
		return mWidthPixel;
	}

	int CMapMeta::getHeighPixel() {
		return mHeightPixel;
	}

	int CMapMeta::getWidthBlock() {
		return mWidthBlock;
	}

	int CMapMeta::getHeighBlock() {
		return mHeightBlock;
	}

	int CMapMeta::getCellW() {
		return mCellW;
	}

	int CMapMeta::getCellH() {
		return mCellH;
	}

	int CMapMeta::getLayerCount() {
		return mLayers.size();
	}



	int CMapMeta::getTile(int layer, int bx,int by){
		return mLayers[layer].TileMatrix2D[by][bx];
	}
	int CMapMeta::getFlip(int layer, int bx,int by){
		return mLayers[layer].FlipMatrix2D[by][bx];
	}
	int CMapMeta::getFlag(int layer, int bx,int by){
		return mLayers[layer].FlagMatrix2D[by][bx];
	}

	void CMapMeta::renderCell(Graphics2D* g, int layer, int sbx, int sby, int dx, int dy)
	{
		int tileid = mLayers[layer].TileMatrix2D[sby][sbx];
		int transt = mLayers[layer].FlipMatrix2D[sby][sbx];
		mTiles->render(g, tileid, dx, dy, transt);
	}

	void CMapMeta::renderRegion(Graphics2D* g, u32 cax, u32 cay, u32 caw, u32 cah)
	{
		u32 sbx1 = cax / mCellW;
		u32 sby1 = cay / mCellH;
		u32 sbx2 = MIN(cax + (caw / mCellW) + 1, getWidthBlock()-1);
		u32 sby2 = MIN(cay + (cah / mCellH) + 1, getHeighBlock()-1);

		int layerCount = getLayerCount();

		u32 osx = Math::cycNum(0, cax, mCellW);
		u32 osy = Math::cycNum(0, cay, mCellH);

		s32 dx;
		s32 dy;

		for (int la=0; la<layerCount; ++la) 
		{
			dx = -osx;
			dy = -osy;

			for (int bx = sbx1; bx <= sbx2; ++bx) 
			{
				for (int by = sby1; by <= sby2; ++by)
				{
					renderCell(g, la, bx, by, dx, dy);
					dy += mCellW;
				}
				dx += mCellH;
				dy = -osy;
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// CCellMapDirect
	////////////////////////////////////////////////////////////////////////////////////////////////////

	CCellMapDirect::CCellMapDirect(CMapMeta* meta, float width, float height)
	{
		this->mMeta = meta;
		setCameraSize(width, height);
	}

	CCellMapDirect::~CCellMapDirect()
	{

	}

	void CCellMapDirect::setCameraSize(float width, float height)
	{
		this->mCamera.width  = MIN(width,  mMeta->getWidthPixel());
		this->mCamera.height = MIN(height, mMeta->getHeighPixel());
	}

	void CCellMapDirect::locateCamera(float x, float y)
	{
		this->mCamera.x = MAX(0, MIN(x, mMeta->getWidthPixel()-mCamera.width));
		this->mCamera.y = MAX(0, MIN(y, mMeta->getHeighPixel()-mCamera.height));
	}

	void CCellMapDirect::moveCamera(float x, float y)
	{
		locateCamera(mCamera.x + x, mCamera.y + y);
	}

	void CCellMapDirect::draw(void)
	{
		mf::Graphics2D g;
		render(&g);
	}

	// call in draw , implements replace
	void CCellMapDirect::render(Graphics2D *g)
	{
		glDisable(GL_COLOR_ARRAY);
		g->pushTransform();
		g->scale(1, -1);
		g->clipRect(0, 0, mCamera.width, mCamera.height);
		mMeta->getTiles()->renderBegin(g);
		mMeta->renderRegion(g, 
			ceil(mCamera.x), 
			ceil(mCamera.y), 
			ceil(mCamera.width), 
			ceil(mCamera.height));
		mMeta->getTiles()->renderEnd(g);
		//g->drawRect(0, 0, mCamera.width, mCamera.height);
		g->clipClean();
		g->popTransform();
		glEnable(GL_COLOR_ARRAY);
	}

}; // namespace mf
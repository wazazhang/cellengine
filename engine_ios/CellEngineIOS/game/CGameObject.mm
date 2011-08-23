//
//  CGameObject.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-20.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CGameObject.h"
#include "CMath.h"

namespace com_cell_game
{	
	using namespace com_cell;
	using namespace std;

	
	//------------------------------------------------------------------------------------------------
	// Tiles
	//------------------------------------------------------------------------------------------------
	
	void CTiles::transform(Graphics2D &g, int width, int height, int Trans)
	{
		switch (Trans) 
		{
			case TRANS_ROT90: {
				g.translate(height, 0);
				g.rotate(ANGLE_90);
				break;
			}
			case TRANS_ROT180: {
				g.translate(width, height);
				g.rotate(ANGLE_180);
				break;
			}
			case TRANS_ROT270: {
				g.translate(0, width);
				g.rotate(ANGLE_270);
				break;
			}
			case TRANS_MIRROR: {
				g.translate(width, 0);
				g.scale(-1, 1);
				break;
			}
			case TRANS_MIRROR_ROT90: {
				g.translate(height, 0);
				g.rotate(ANGLE_90);
				g.translate(width, 0);
				g.scale(-1, 1);
				break;
			}
			case TRANS_MIRROR_ROT180: {
				g.translate(width, 0);
				g.scale(-1, 1);
				g.translate(width, height);
				g.rotate(ANGLE_180);
				break;
			}
			case TRANS_MIRROR_ROT270: {
				g.rotate(ANGLE_270);
				g.scale(-1, 1);
				break;
			}
		}
	}
	
	//------------------------------------------------------------------------------------------------
	// CCD
	//------------------------------------------------------------------------------------------------

	CCD CCD::createCDRect(int mask, float x, float y, float w, float h) 
	{
		CCD ret;
		ret.Type = CD_TYPE_RECT;
		ret.Mask = mask;
		ret.X1 = x;
		ret.Y1 = y;
		ret.X2 = (x + w-1);
		ret.Y2 = (y + h-1);
		return ret;
	}
	
	CCD CCD::createCDRect_2Point(int mask, float sx, float sy, float dx, float dy) 
	{
		CCD ret;
		ret.Type = CD_TYPE_RECT;
		ret.Mask = mask;
		ret.X1 = MIN(sx, dx);
		ret.Y1 = MIN(sy, dy);
		ret.X2 = MAX(sx, dx);
		ret.Y2 = MAX(sy, dy);
		return ret;
	}
	
	CCD CCD::createCDLine(int mask, float px, float py, float qx, float qy)
	{
		CCD ret;
		ret.Type = CD_TYPE_LINE;
		ret.Mask =  mask;
		ret.X1 = px;
		ret.Y1 = py;
		ret.X2 = qx;
		ret.Y2 = qy;
		return ret;
	}
	
	CCD::CCD()
	{
		Type = CD_TYPE_RECT;
		Mask = 0;
		X1 = 0;
		Y1 = 0;
		X2 = 0;
		Y2 = 0;
	}
	
	float CCD::getWidth() 
	{
		return X2 - X1;	
	}
	
	float CCD::getHeight() 
	{
		return Y2 - Y1;
	}

	float CCD::getMedianY()  
	{
		return -(getHeight() / 2 - (Y1 + getHeight()));
	}
	

	float CCD::getMedianX()  
	{
		return -(getWidth() / 2 - (X1 + getWidth()));
	}
	
	
	void CCD::render(Graphics2D &g, float px, float py, Color const &color) 
	{
		if ( Mask == 0) return;
		g.setColor(color);
		switch (Type) {
			case CD_TYPE_LINE:
				g.drawLine(px + X1, py + Y1, px + X2, py + Y2 );
				break;
			case CD_TYPE_RECT:
				g.drawRect(px + X1, py + Y1, X2 - X1 , Y2 - Y1 );
				break;
		}
	}
	
	
	bool CCD::touch(CCD  const &b1, int x1, int y1, 
					CCD  const &b2, int x2, int y2, 
					bool processStatus) 
	{
		if ((processStatus && (b1.Mask & b2.Mask) != 0) || !processStatus) {
			if (b1.Type == CD_TYPE_RECT && b2.Type == CD_TYPE_RECT) {
				return touchRect(b1, x1, y1, b2, x2, y2);
			} else if (b1.Type == CD_TYPE_LINE && b2.Type == CD_TYPE_LINE) {
				return touchLine(b1, x1, y1, b2, x2, y2);
			} else if (b1.Type == CD_TYPE_RECT) {
				return touchRectLine(b1, x1, y1, b2, x2, y2);
			} else if (b2.Type == CD_TYPE_RECT) {
				return touchRectLine(b2, x2, y2, b1, x1, y1);
			}
		}
		return false;
	}
	
	bool CCD::touchRect(CCD  const &src, int sx, int sy, 
						CCD  const &dst, int dx, int dy) 
	{
		return Math::intersectRect(
							 src.X1 + sx, src.Y1 + sy, 
							 src.X2 + sx, src.Y2 + sy, 
							 dst.X1 + dx, dst.Y1 + dy, 
							 dst.X2 + dx, dst.Y2 + dy);
	}
	
	bool CCD::touchLine(CCD  const &src, int sx, int sy, 
						CCD  const &dst, int dx, int dy)
	{
		if (touchRect(src, sx, sy, dst, dx, dy)) {
			return Math::intersectLine(
								 src.X1 + sx, src.Y1 + sy, 
								 src.X2 + sx, src.Y2 + sy, 
								 dst.X1 + dx, dst.Y1 + dy, 
								 dst.X2 + dx, dst.Y2 + dy);
		}
		return false;
	}
	
	bool CCD::touchRectLine(CCD const &rect, int rx, int ry, 
								 CCD const &line, int lx, int ly)
	{
		if (Math::intersectRect(rx + rect.X1, ry + rect.Y1,//
							   rx + rect.X2, ry + rect.Y2,//
							   lx + MIN(line.X1,line.X2), ly + MIN(line.Y1,line.Y2),//
							   lx + MAX(line.X1,line.X2), ly + MAX(line.Y1,line.Y2))//
			) {
			if (Math::intersectLine(//TOP
									(rx + rect.X1) , (ry + rect.Y1) ,//
									(rx + rect.X2) , (ry + rect.Y1) ,//
									(lx + line.X1) , (ly + line.Y1) ,//
									(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (Math::intersectLine(//LEFT
									(rx + rect.X1) , (ry + rect.Y1) ,//
									(rx + rect.X1) , (ry + rect.Y2) ,//
									(lx + line.X1) , (ly + line.Y1) ,//
									(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (Math::intersectLine(//RIGHT
									(rx + rect.X2) , (ry + rect.Y1) ,//
									(rx + rect.X2) , (ry + rect.Y2) ,//
									(lx + line.X1) , (ly + line.Y1) ,//
									(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (Math::intersectLine(//BUTTON
									(rx + rect.X1) , (ry + rect.Y2) ,//
									(rx + rect.X2) , (ry + rect.Y2) ,//
									(lx + line.X1) , (ly + line.Y1) ,//
									(lx + line.X2) , (ly + line.Y2)))//
				return true;
		}
		return false;
	}
	
	//------------------------------------------------------------------------------------------------
	// CGroup
	//------------------------------------------------------------------------------------------------

	
	CGroup::CGroup()
	{
		SubIndex = 0;
		SubCount = 0;
		
		w_left = 0;
		w_top = 0;
		w_bottom = 1;
		w_right = 1;
		w_width = 0;
		w_height = 0;
	}
	
	void CGroup::fixArea(int left, int top, int right, int botton) 
	{
		if (left < w_left)
			w_left = left;
		if (top < w_top)
			w_top = top;
		if (right > w_right)
			w_right = right;
		if (botton > w_bottom)
			w_bottom = botton;
		
		w_width = (w_right - w_left);
		w_height = (w_bottom - w_top);
	}
	
	
	void CGroup::getAllBounds(CCD &outcd)
	{
		outcd.X1 = w_left;
		outcd.X2 = w_right;
		outcd.Y1 = w_top;
		outcd.Y2 = w_bottom;
	}
	
	void CGroup::setFrames(vector<vector<int> > const &frames)
	{
		Frames = frames;
	}
	
	vector<vector<int> >& CGroup::getFramesRef()
	{
		return Frames;
	}
	
	void CGroup::setComboFrame(vector<int> const &frame, int index)
	{
		Frames[index] = frame;
	}
	
	
	int CGroup::getFrameCount()
	{
		return Frames.size();
	}
	
	int CGroup::getComboFrameCount(int index)
	{
		return Frames[index].size();
	}

	bool CGroup::touchArea(CGroup const &c1, float x1, float y1,
						   CGroup const &c2, float x2, float y2)
	{
		if(Math::intersectRect(
							   x1 + c1.w_left,  
							   y1 + c1.w_top, 
							   x1 + c1.w_right,  
							   y1 + c1.w_bottom, 
							   x2 + c2.w_left, 
							   y2 + c2.w_top, 
							   x2 + c2.w_right, 
							   y2 + c2.w_bottom)){
			return true;
		}
		return false;
	}

	
	//------------------------------------------------------------------------------------------------
	// CCollides
	//------------------------------------------------------------------------------------------------

	
	
	CCollides::CCollides(int cdCount)
	{
		SubIndex = 0;
		SubCount = cdCount;
		Frames.resize(cdCount);
		cds.resize(cdCount);
	}
	
	void CCollides::addCDRect(int mask, float x, float y, float w, float h)
	{
		addCD(CCD::createCDRect(mask, x, y, w, h));
	}
	
	void CCollides::addCDLine(int mask, float px, float py, float qx, float qy)
	{
		addCD(CCD::createCDLine(mask,px,py,qx,qy));
	}
	
	void CCollides::addCD(CCD const &cd) 
	{
		if (SubIndex >= SubCount) {
//			System.err.println("Out of Max CD Count !");
			return;
		}		
		cds[SubIndex] = cd;
		fixArea(cd.X1, cd.Y1, cd.X2, cd.Y2);
		Frames[SubIndex] = vector<int>(1);
		Frames[SubIndex].push_back(SubIndex);
		SubIndex++;
	}
	
	bool CCollides::getCD(int index, CCD &outcd)
	{
		if (index < cds.size()) {
			outcd = cds[index];		
			return true;
		}
		return false;
	}
	
	bool CCollides::getFrameCD(int frame,int sub, CCD &outcd)
	{
		return getCD(Frames[frame][sub], outcd);
	}
	
	void CCollides::render(Graphics2D &g, int index, float x, float y, Color const &color)
	{
		for(int i=Frames[index].size()-1;i>=0;i--){
			cds[Frames[index][i]].render(g, x, y, color);
		}
	}
	
	void CCollides::renderSub(Graphics2D &g, int index, int sub, float x, float y, Color const &color)
	{
		cds[Frames[index][sub]].render(g, x, y, color);
	}
	
	bool CCollides::touchCD(CCollides const &c1,int index1, 
							float x1, float y1,
							CCD const &c2,
							float x2, float y2)
	{
		for(int i=c1.Frames[index1].size()-1;i>=0;i--){
			if(CCD::touch(c1.cds[c1.Frames[index1][i]], x1, y1, // 
						  c2, x2, y2, //
						  true)){
				return true;
			}
		}
		return false;
	}
	
	
	bool CCollides::touchSubCD(CCollides const &c1,
							   int index1, int part1,
							   float x1, float y1,
							   CCD const &c2,
							   float x2, float y2)
	{
		if( CCD::touch(c1.cds[c1.Frames[index1][part1]], x1, y1, // 
					  c2, x2, y2, //
					  true)){
			return true;
		}
		return false;
	}
	
	bool CCollides::touch(CCollides const &c1, int index1, float x1, float y1,
						  CCollides const &c2, int index2, float x2, float y2)
	{
		
		if (touchArea(c1, x1, y1, c2, x2, y2)){
			for (int i=c1.Frames[index1].size()-1;i>=0;i--){
				for (int j=c2.Frames[index2].size()-1;j>=0;j--){
					if (CCD::touch(
								  c1.cds[c1.Frames[index1][i]], x1, y1, // 
								  c2.cds[c2.Frames[index2][j]], x2, y2, //
								  true)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	bool CCollides::touchSub(CCollides const &c1,int index1,int part1,float x1,float y1,
							 CCollides const &c2,int index2,int part2,float x2,float y2)
	{
		if (CCD::touch(
					  c1.cds[c1.Frames[index1][part1]], x1, y1, // 
					  c2.cds[c2.Frames[index2][part2]], x2, y2, //
					  true))
		{
			return true;
		}
		return false;
	}
	
	
	//------------------------------------------------------------------------------------------------
	// CAnimates
	//------------------------------------------------------------------------------------------------
//	this.images = images;
//	SubCount = (short) partCount;
//	SubIndex = 0;
//	
//	STileID = new short[partCount];
//	SFlip = new byte[partCount];
//	
//	SW = new short[partCount];
//	SH = new short[partCount];
//	SX = new short[partCount];
//	SY = new short[partCount];
//	
//	Frames = new short[partCount][];
	
	CAnimates::CAnimates(int partCount, CTiles *tils)
	{
		tiles		= tils;
		SubCount	= partCount;
		SubIndex	= 0;
		
		STileID	.resize(partCount);
		SFlip	.resize(partCount);
		
		SW		.resize(partCount);
		SH		.resize(partCount);
		SX		.resize(partCount);
		SY		.resize(partCount);
		
		Frames	.resize(partCount);
	}
	
	void CAnimates::addPart(int px, int py, int tileid, char trans) 
	{
		if (SubIndex >= SubCount) {
			//				System.err.println("Out Of Animate Max Count !");
			return;
		}
		STileID[SubIndex] = tileid;
		SW[SubIndex] = tiles->getWidth(tileid);
		SH[SubIndex] = tiles->getHeight(tileid);
		SX[SubIndex] = px;
		SY[SubIndex] = py;
		SFlip[SubIndex] = trans;
		
		switch(trans){
			case CTiles::TRANS_NONE:
			case CTiles::TRANS_ROT180:
			case CTiles::TRANS_MIRROR:
			case CTiles::TRANS_MIRROR_ROT180:
				SW[SubIndex] =  tiles->getWidth(tileid);
				SH[SubIndex] =  tiles->getHeight(tileid);
				break;
			case CTiles::TRANS_ROT90:
			case CTiles::TRANS_ROT270:
			case CTiles::TRANS_MIRROR_ROT90:
			case CTiles::TRANS_MIRROR_ROT270:
				SW[SubIndex] =  tiles->getHeight(tileid);
				SH[SubIndex] =  tiles->getWidth(tileid);
				break;
		}
		fixArea(SX[SubIndex], SY[SubIndex], 
				SX[SubIndex] + SW[SubIndex],
				SY[SubIndex] + SH[SubIndex]);
		
		Frames[SubIndex] = vector<int>(1);
		Frames[SubIndex].push_back(SubIndex);
		SubIndex++;
		
	}
	
	CTiles* CAnimates::getTiles()
	{
		return tiles;
	}
	
	Image* CAnimates::getTile(int index)
	{
		return tiles->getImage(index);
	}
	
	Image* CAnimates::getFrameImage(int frame, int sub)
	{
		return tiles->getImage(STileID[Frames[frame][sub]]);
	}
	
	int CAnimates::getFrameX(int frame,int sub)
	{
		return SX[Frames[frame][sub]];
	}
	int CAnimates::getFrameY(int frame,int sub)
	{
		return SY[Frames[frame][sub]];
	}
	
	int CAnimates::getFrameW(int frame,int sub) 
	{
		return SW[Frames[frame][sub]];
	}
	
	int CAnimates::getFrameH(int frame,int sub) 
	{
		return SH[Frames[frame][sub]];
	}
	
	char CAnimates::getFrameTransform(int frame, int sub)
	{
		return SFlip[Frames[frame][sub]];
	}
	
	CCD CAnimates::getFrameBounds(int frame)
	{
		int left	= INT32_MAX;
		int right	= INT32_MIN; 
		int top		= INT32_MAX;
		int bottom	= INT32_MIN;
		
		for (int i=SX.size()-1; i>=0; --i)
		{
			left	= MIN(getFrameX(frame, i), left);
			right	= MAX(getFrameX(frame, i) + getFrameW(frame, i), right);
			top		= MIN(getFrameY(frame, i), top);
			bottom	= MAX(getFrameY(frame, i) + getFrameH(frame, i), bottom);
		}
		CCD cd = CCD::createCDRect_2Point(0, left, top, right - left, bottom - top);
		return cd;//new int[]{left, top, right - left, bottom - top};
	}
	
	/**
	 * Draw one frame with specify frame id</br>
	 * @param g	graphics surface 
	 * @param index frame id </br>
	 * @param x x on graphics surface</br>
	 * @param y y on graphics surface</br>
	 */
	void CAnimates::render(Graphics2D &g, int index, float x, float y)
	{
		for(int i=Frames[index].size()-1;i>=0;i--){
			int idx = Frames[index][i];
			tiles->render(g,
						  STileID[idx], 
						  x + SX[idx], //
						  y + SY[idx], //
						  SFlip[idx]);
		}
	}	
	
	/**
	 * Draw one part with specify frame id and part id</br>�1�7
	 * @param g graphics surface 
	 * @param index frame id </br>
	 * @param part part id </br>
	 * @param x x on graphics surface </br>�1�7
	 * @param y y on graphics surface </br>
	 */
	void CAnimates::renderSub(Graphics2D &g, int index, int part, float x, float y)
	{
		int idx = Frames[index][part];
		tiles->render(g,
					  STileID[idx], 
					  x + SX[idx], //
					  y + SY[idx], //
					  SFlip[idx]);
	}	
	
	/**
	 * Draw one frame with specify frame id ignore part's coordinate, 
	 * all part based zero point.</br>
	 * @param g graphics surface
	 * @param index frame id
	 * @param x x on graphics surface
	 * @param y y on graphics surface
	 */
	void CAnimates::renderSingle(Graphics2D &g, int index, float x, float y)
	{
		for(int i=Frames[index].size()-1;i>=0;i--){
			int idx = Frames[index][i];
			tiles->render(g,
						  STileID[idx], 
						  x , //
						  y , //
						  SFlip[idx]);
		}
	}
	
	/**
	 * Draw one part with specify frame id and part id ignore part's coordinate, 
	 * part based zero point.</br>
	 * @param g graphics surface
	 * @param index frame id
	 * @param part part id
	 * @param x x on graphics surface
	 * @param y y on graphics surface
	 */
	void CAnimates::renderSingleSub(Graphics2D &g, int index, int part, float x, float y)
	{
		int idx = Frames[index][part];
		tiles->render(g,
					  STileID[idx], 
					  x , //
					  y , //
					  SFlip[idx]);
		
	}

	
	
	
	
	
}; // namespace gametiler
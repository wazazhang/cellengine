//
//  CGameObject.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-20.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_GAME_GAME_OBJECT
#define _COM_CELL_GAME_GAME_OBJECT

#include "stdio.h"
#include <string>
#include <vector>
#include <map>

#include "CImage.h"
#include "CGraphics2D.h"

namespace com_cell_game
{
	using namespace com_cell;
	using namespace std;
	
	class ICellResource
	{
	public:
		virtual ~ICellResource(){}
		
		
	};// class ICellResource
	
	//------------------------------------------------------------------------------------------------
	// Tiles
	//------------------------------------------------------------------------------------------------
	
	class CTiles : public ICellResource
	{
	public:	
		static const float ANGLE_90		= M_PI / 2;
		static const float ANGLE_180	= M_PI ;
		static const float ANGLE_270	= M_PI * 3 / 2;

		static const int TRANS_NONE = 0;
		static const int TRANS_ROT90 = 1;
		static const int TRANS_ROT180 = 2;
		static const int TRANS_ROT270 = 3;
		static const int TRANS_MIRROR = 4;
		static const int TRANS_MIRROR_ROT90 = 5;
		static const int TRANS_MIRROR_ROT180 = 6;
		static const int TRANS_MIRROR_ROT270 = 7;

		
	public:
		
		virtual Image*	getImage(int index) = 0;
				
		virtual int		getWidth(int Index) = 0;
		
		virtual int		getHeight(int Index) = 0;
		
		virtual int		getCount() = 0;
				
		virtual void	render(Graphics2D &g, int Index, float PosX, float PosY, int Trans) = 0;
		
	protected:
		
		void transform(Graphics2D &g, int width, int height, int Trans);
		
	}; // class CTiles
	
	//------------------------------------------------------------------------------------------------
	// CCD
	//------------------------------------------------------------------------------------------------

	class CCD
	{
	public:
		static const char CD_TYPE_RECT		= 1;
		static const char CD_TYPE_LINE		= 2;
		static const char CD_TYPE_POINT		= 3;
		
		static CCD createCDRect(int mask, float x, float y, float w, float h);
		
		static CCD createCDRect_2Point(int mask, float sx, float sy, float dx, float dy) ;
		
		static CCD createCDLine(int mask, float px, float py, float qx, float qy) ;		
		
	public:
		
		char Type;
		
		int Mask;
		
		/**Left */
		float X1;
		/**Top */
		float Y1;
		/**Right*/
		float X2;
		/**Bottom*/
		float Y2;
		
	public:
		
		CCD();
		
		float getWidth();
		
		float getHeight();
		
		/**
		 * 得到外包矩形中心点和渲染中心点的偏移
		 * @return
		 */
		float getMedianY() ;
		
		/**
		 * 得到外包矩形中心点和渲染中心点的偏移
		 * @return
		 */
		float getMedianX() ;
		
		void render(Graphics2D &g, float px, float py, Color const &color);
		
		
		static bool touch(CCD const &b1, int x1, int y1, 
						  CCD const &b2, int x2, int y2, 
						  bool processStatus);		
		
		static bool touchRect(CCD const &src, int sx, int sy, 
							  CCD const &dst, int dx, int dy);		
		
		static bool touchLine(CCD const &src, int sx, int sy, 
							  CCD const &dst, int dx, int dy) ;
		
		static bool touchRectLine(CCD const &rect, int rx, int ry, 
								  CCD const &line, int lx, int ly);
		
	}; // class CCD
	
	
	//------------------------------------------------------------------------------------------------
	// CGroup
	//------------------------------------------------------------------------------------------------

	class CGroup
	{		
	protected:
		
		vector<vector<int> > Frames;
		
		int SubIndex;
		int SubCount;
		
		int w_left, w_top, w_bottom, w_right, w_width, w_height;
		
	public:
		
		CGroup();		
		
		void fixArea(int left, int top, int right, int botton) ;		
		
		void getAllBounds(CCD &outcd);
		
		/**
		 * set frame sequence, frames[frame id][part id] = groupted object. </br> 
		 * e.g. : animates's image id ; collides's CCD object ;</br>
		 * @param frames frames[frame id][part id]
		 */
		void setFrames(vector<vector<int> > const &frames);	
		
		// 
		vector<vector<int> >& getFramesRef();		
		
		/**
		 * set part sequence specify frame index</br>
		 * @param frame frames[frame id][part id]
		 * @param index frame id
		 */
		void setComboFrame(vector<int> const &frame, int index);
		
		/**
		 * get frames count</br>
		 * @return count
		 */
		int getFrameCount();
		
		/**
		 * get frames count</br>
		 * @return count
		 */
		int getComboFrameCount(int index);
		
		/**
		 * fast detect 2 collides's area specify frame, 
		 * area is every part within a frame's max scope. 
		 * </br>
		 * @param c1
		 * @param index1
		 * @param x1
		 * @param y1
		 * @param c2
		 * @param index2
		 * @param x2
		 * @param y2
		 * @return 
		 */
		static bool touchArea(CGroup const &c1, float x1, float y1,
							  CGroup const &c2, float x2, float y2);
		

		
	}; // class CGroup
	
	//------------------------------------------------------------------------------------------------
	// CCollides
	//------------------------------------------------------------------------------------------------
	/**
	 * Collides contian some frame, it have a coordinate system with all part. </br>
	 * every frame contian some part </br> 
	 * every part is a collision block (CCD object) </br>
	 * @author yifeizhang
	 * @since 2006-11-29 
	 * @version 1.0
	 */
	class CCollides : public CGroup
	{		
	protected :
		
		vector<CCD> cds;
		
	public:
		
		CCollides(int cdCount);		
		
		CCollides(){};	
		
		/**
		 * add a rectangle collision block part </br>
		 * @param mask collision block mask
		 * @param x left
		 * @param y top
		 * @param w width
		 * @param h hight
		 */
		void addCDRect(int mask, float x, float y, float w, float h);		
		
		/**
		 * add a line collision block part</br>
		 * @param mask collision block mask
		 * @param px point 1 x
		 * @param py point 1 y
		 * @param qx point 2 x
		 * @param qy point 2 y
		 */
		void addCDLine(int mask, float px, float py, float qx, float qy);
		
		/**
		 * add a collision block part</br>
		 * @param cd collision block part
		 */
		void addCD(CCD const &cd);
		
		/**
		 * Get a collision block form group, return ccd[index]
		 * @param index ccd[index]
		 * @return collision block
		 */
		bool getCD(int index, CCD &outcd);
		
		/**
		 * Get collision block form specify frame id and part id </br>
		 * @param frame frame id within collides
		 * @param sub part id within frame
		 * @return image
		 */
		bool getFrameCD(int frame, int sub, CCD &outcd);
		
		/**
		 * draw a collides block
		 * @param g
		 * @param index frame id
		 * @param x
		 * @param y
		 * @param color 
		 */
		void render(Graphics2D &g, int index, float x, float y, Color const &color);
		
		/**
		 * draw a collides block
		 * @param g
		 * @param index frame id
		 * @param x
		 * @param y
		 * @param color 
		 */
		void renderSub(Graphics2D &g, int index, int sub, float x, float y, Color const &color);
		
		
		/**
		 * collision test with a collides specify frame and a collision block (CCD object)</br>
		 * @param c1 src collides 
		 * @param index1 src frame id
		 * @param x1 src x offset
		 * @param y1 src y offset
		 * @param c2 dst CCD object
		 * @param x2 dst x offset
		 * @param y2 dst y offset
		 * @return 
		 */
		static bool touchCD(CCollides const &c1,int index1, 
							float x1, float y1,
							CCD const &c2,
							float x2, float y2);		
		
		/**
		 * collision test with a collides specify frame part and a collision block (CCD object)</br>
		 * @param c1 src collides
		 * @param index1 src frame id
		 * @param part1 src part id
		 * @param x1 src x offset 
		 * @param y1 src y offset
		 * @param c2 dst CCD object
		 * @param x2 dst x offset
		 * @param y2 dst y offset
		 * @return 
		 */
		static bool touchSubCD(CCollides const &c1,
							   int index1, int part1,
							   float x1, float y1,
							   CCD const &c2,
							   float x2, float y2);
		
		/**
		 * collision test with 2 collides specify frame</br>
		 * @param c1 collides 1
		 * @param index1 frame id 1
		 * @param x1  x offset 1
		 * @param y1  y offset 1
		 * @param c2 collides 2
		 * @param index2 frame id 2
		 * @param x2  x offset 2
		 * @param y2  y offset 2
		 * @return is intersect
		 */
		static bool touch(CCollides const &c1, int index1, float x1, float y1,
						  CCollides const &c2, int index2, float x2, float y2);		
		
		/**
		 * collision test with 2 collides specify frame part</br>
		 * @param c1 collides 1
		 * @param index1 frame id 1
		 * @param part1 part id 1
		 * @param x1  x offset 1
		 * @param y1  y offset 1
		 * @param c2 collides 2
		 * @param index2 frame id 2
		 * @param part2 part id 2
		 * @param x2  x offset 2
		 * @param y2  y offset 2
		 * @return is intersect
		 */
		static bool touchSub(CCollides const &c1,int index1,int part1,float x1,float y1,
							 CCollides const &c2,int index2,int part2,float x2,float y2);		
		
		 
	}; // class CCollides


	
	//------------------------------------------------------------------------------------------------
	// CAnimates
	//------------------------------------------------------------------------------------------------
	/**
	 * Animates contain some frame, it have a coordinate system with all part. </br>
	 * every frame contian some part </br> 
	 * every part is Images's index value </br>
	 * every part has itself 2d coordinate x y </br>>
	 * every part has itself Flip attribute </br>
	 * @author yifeizhang
	 * @since 2006-11-29 
	 * @version 1.0
	 */
	class CAnimates : public CGroup
	{
	protected:
		
		CTiles *tiles;
		
		vector<int>		SX;
		vector<int>		SY;
		vector<int>		SW;
		vector<int>		SH;
		vector<int>		STileID;
		vector<char>	SFlip;
		
	public:
		/**
		 * Construct Animates
		 * @param partCount size of part </br>
		 * @param images reference </br>
		 */
		CAnimates(int partCount, CTiles *tils);
		
		CAnimates(){};
		
		/**
		 * Add an image part form construct images reference</br>
		 * @param px part's x coordinate </br>
		 * @param py part's y coordinate </br>
		 * @param tileid part's images index value </br>
		 * @param trans part's flip rotate paramenter
		 * 	 */
		void addPart(int px, int py, int tileid, char trans) ;
		
		CTiles* getTiles();
		
		/**
		 * Get image form construct images reference</br>
		 * @param index index of construct images 
		 * @return image
		 */
		Image* getTile(int index);
		
		/**
		 * Get image form specify frame id and part id</br>
		 * @param frame frame id within animates</br>
		 * @param sub part id within frame</br>
		 * @return image
		 */
		Image* getFrameImage(int frame, int sub);
		
		int getFrameX(int frame,int sub);		
		int getFrameY(int frame,int sub);		
		int getFrameW(int frame,int sub) ;		
		int getFrameH(int frame,int sub) ;		
		
		char getFrameTransform(int frame, int sub);
		
		CCD getFrameBounds(int frame);
		
		/**
		 * Draw one frame with specify frame id</br>
		 * @param g	graphics surface 
		 * @param index frame id </br>
		 * @param x x on graphics surface</br>
		 * @param y y on graphics surface</br>
		 */
		void render(Graphics2D &g, int index, float x, float y);
		
		/**
		 * Draw one part with specify frame id and part id</br>
		 * @param g graphics surface 
		 * @param index frame id </br>
		 * @param part part id </br>
		 * @param x x on graphics surface </br>
		 * @param y y on graphics surface </br>
		 */
		void renderSub(Graphics2D &g, int index, int part, float x, float y);
		
		/**
		 * Draw one frame with specify frame id ignore part's coordinate, 
		 * all part based zero point.</br>
		 * @param g graphics surface
		 * @param index frame id
		 * @param x x on graphics surface
		 * @param y y on graphics surface
		 */
		void renderSingle(Graphics2D &g, int index, float x, float y);
		
		/**
		 * Draw one part with specify frame id and part id ignore part's coordinate, 
		 * part based zero point.</br>
		 * @param g graphics surface
		 * @param index frame id
		 * @param part part id
		 * @param x x on graphics surface
		 * @param y y on graphics surface
		 */
		void renderSingleSub(Graphics2D &g, int index, int part, float x, float y);
		
		
	}; // CAnimates

















}; // namespace gametiler


#endif // #define _COM_CELL_GAME_GAME_OBJECT
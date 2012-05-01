//
//  CGraphics2D.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _MF_GRAPHICS_2D
#define _MF_GRAPHICS_2D

#include "cocos2d.h"
#include "MFType.h"

namespace mf
{
	typedef struct Blend
	{
		int sfactor;
		int dfactor;

		Blend() {
			sfactor = GL_SRC_ALPHA;
			dfactor = GL_ONE_MINUS_SRC_ALPHA;
		}

		Blend(int s, int d) {
			sfactor = s;
			dfactor = d;
		}

	} Blend;

	const Blend BLEND_NORMAL = Blend(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	const Blend BLEND_SCREEN = Blend(GL_SRC_ALPHA, GL_ONE);

	typedef struct Color
	{
		GLfloat R;
		GLfloat G;
		GLfloat B;
		GLfloat A;

		Color(GLfloat r, GLfloat g, GLfloat b, GLfloat a) {
			R = r;
			G = g;
			B = b;
			A = a;
		}

		Color() {
			R = 1;
			G = 1;
			B = 1;
			A = 1;
		}

	} Color;


	const Color COLOR_WHITE 		= Color(1, 1, 1			,1 );
	const Color COLOR_LIGHT_GRAY 	= Color(.75f, .75f, .75f,1 );
	const Color COLOR_GRAY 			= Color(.50f, .50f, .50f,1 );
	const Color COLOR_DARK_GRAY 	= Color(.25f, .25f, .25f,1 );
	const Color COLOR_BLACK 		= Color(0, 0, 0			,1 );
	const Color COLOR_RED 			= Color(1, 0, 0			,1 );
	const Color COLOR_PINK 			= Color(1, .68f, .68f	,1 );
	const Color COLOR_ORANGE 		= Color(1, .78f, 0		,1 );
	const Color COLOR_YELLOW 		= Color(1, 1, 0			,1 );
	const Color COLOR_GREEN 		= Color(0, 1, 0			,1 );
	const Color COLOR_MAGENTA 		= Color(1, 0, 1			,1 );
	const Color COLOR_CYAN 			= Color(0, 1, 1			,1 );
	const Color COLOR_BLUE 			= Color(0, 0, 1			,1 );



	//------------------------------------------------------------------------------------------------
	// Graphics2D
	//------------------------------------------------------------------------------------------------

	typedef struct Color4P
	{
		Color p0;
		Color p1;
		Color p2;
		Color p3;
	public:
		Color4P(GLfloat r, GLfloat g, GLfloat b, GLfloat a) {
			set(r, g, b, a);
		}
		Color4P(){
			set(1, 1, 1, 1);
		}
		void set(GLfloat r, GLfloat g, GLfloat b, GLfloat a) {
			p0 = Color(a, r, g, b);
			p1 = Color(a, r, g, b);
			p2 = Color(a, r, g, b);
			p3 = Color(a, r, g, b);
		}

	} Color4P;

	class Graphics2D
	{
	protected:
		Color4P					m_color;
		Blend					m_blend;

        std::vector<Blend>		m_stack_blend;
        std::vector<Color4P>	m_stack_color;

    public:

        Graphics2D();
		
		virtual ~Graphics2D();

	public:

		void		clipRect(float x, float y, float w, float h);

		void		clipClean();
		
        //////////////////////////////////////////////////////////////////////////////////
		// color
		void        setColor(float r, float g, float b, float a);	
		void        setColor(Color const &color);	
		void		setColorAlpha(GLfloat alpha);
		Color       getColor();		
		void        pushColor();
        void        popColor();
        
        //////////////////////////////////////////////////////////////////////////////////
        // gemo
        
        void drawLine(float x1, float y1, float x2, float y2);
        
		void fillRect(float x, float y, float w, float h);
        
		void drawRect(float x, float y, float w, float h);
        
        void drawArc(float x, float y, float width, float height, float startAngle, float angle);
        
        void fillArc(float x, float y, float width, float height, float startAngle, float angle);
        
        void drawOval(float x, float y, float width, float height);        
        
        void fillOval(float x, float y, float width, float height);        
        
        // 绘制多边形
        // points[x0,y0,x1,y1...]
        void drawPolygon(float points[], float nPoints);
        
        // 填充多边形
        // points[x0,y0,x1,y1...]
        void fillPolygon(float points[], float nPoints);
        
        //////////////////////////////////////////////////////////////////////////////////
		// image
        /*
		void drawImage(Image *src, float x, float y);
		
		void drawImageSize(Image *src, float x, float y, float w, float h);
		
		void drawImageScale(Image *src, float x, float y, float rate_w, float rate_h);
		
		void drawImageMask(Image *src, float x, float y,
						   Color const &maskColor);
		
		void drawImageMaskSize(Image *src, float x, float y, float w, float h, 
							   Color const &maskColor);
		
		void drawImageMaskScale(Image *src, float x, float y, float scale_w, float scale_h, 
								Color const &maskColor);
		*/

        ///////////////////////////////////////////////////////////////////////////////
        // transform
        
        // 位移变换
        void translate(float x, float y);
        
        // 旋转变换
        // degree 角度
        void rotate(float angle);
        
        // 缩放变换
        void scale(float sx, float sy);
        
        // 推入几何变换
        void pushTransform();
        
        // 弹出几何变换
        void popTransform();
        
        ///////////////////////////////////////////////////////////////////////////////
        // blend
        
        // 设置渲染方式
        void    setBlend(Blend blend);
        
        // 获取渲染模式
        Blend   getBlend();
        
        // 推入渲染模式
        void    pushBlend();
        
        // 弹出渲染模式
        void    popBlend();
	};
    

	//------------------------------------------------------------------------------------------------
	// Tiles
	//------------------------------------------------------------------------------------------------


	class ITiles
	{
	public:

		enum Trans
		{
			TRANS_NONE 				= 0,
			TRANS_ROT90 			= 1,
			TRANS_ROT180 			= 2,
			TRANS_ROT270 			= 3,
			TRANS_MIRROR 			= 4,
			TRANS_MIRROR_ROT90 		= 5,
			TRANS_MIRROR_ROT180 	= 6,
			TRANS_MIRROR_ROT270 	= 7,
		} ;

	public:

		virtual int		getWidth(int Index) = 0;

		virtual int		getHeight(int Index) = 0;

		virtual int		getCount() = 0;

		virtual void	renderBegin(Graphics2D *g) = 0;

		virtual void	render(Graphics2D *g, int Index, float PosX, float PosY, int trans) = 0;
	
		virtual void	renderEnd(Graphics2D *g) = 0;

	}; // class ITiles
	
	
	//////////////////////////////////////////////////////////////////////
	// 整图输出
	//////////////////////////////////////////////////////////////////////
	class TilesGroup : public ITiles
	{
	
	protected:
		
		int			count;
		int			imageWidth;
		int			imageHeight;
		GLuint		tuxture_name;

		vector<GLfloat*>	tile_texcoords;
		vector<GLfloat*>	tile_vertices;

	public:

		TilesGroup(int count, int imageWidth, int imageHeight);

		virtual ~TilesGroup();

		void setTile(int index, int cx, int cy, int cw, int ch);

		virtual int		getWidth(int Index);

		virtual int		getHeight(int Index);

		virtual int		getCount();

		virtual void	renderBegin(Graphics2D *g);

		virtual void	render(Graphics2D *g, int Index, float PosX, float PosY, int trans);

		virtual void	renderEnd(Graphics2D *g);

	};

}; // namespace mf



#endif //_MF_GRAPHICS_2D

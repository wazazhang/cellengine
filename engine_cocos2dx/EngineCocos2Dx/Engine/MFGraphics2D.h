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
	using namespace std;
	using namespace cocos2d;

	class CC_DLL IImage;
	class CC_DLL ITiles;
	class CC_DLL Graphics2D;
	class CC_DLL BufferedGraphis2D;

	typedef CC_DLL  struct Blend
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

	typedef CC_DLL  struct Color
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
		inline void set(GLfloat r, GLfloat g, GLfloat b, GLfloat a) {
			R = r;
			G = g;
			B = b;
			A = a;
		}
		inline void setARGB(u32 argb) {
			A = ((argb & 0xff000000) >> 24) / 255.0f;
			R = ((argb & 0x00ff0000) >> 16) / 255.0f;
			G = ((argb & 0x0000ff00) >> 8)  / 255.0f;
			B = ((argb & 0x000000ff) >> 0)  / 255.0f;
		}
	} Color;


	const Color COLOR_NULL	 		= Color(0, 0, 0, 0);

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

	typedef CC_DLL  struct Color4P
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
		inline void set(GLfloat r, GLfloat g, GLfloat b, GLfloat a) {
			p0.set(r, g, b, a);
			p1.set(r, g, b, a);
			p2.set(r, g, b, a);
			p3.set(r, g, b, a);
		}

	} Color4P;


	typedef CC_DLL enum ImageAnchor
	{
		L_T,
		C_T,
		R_T,
		L_C,
		C_C,
		R_C,
		L_B,
		C_B,
		R_B,
	} ImageAnchor;

	typedef CC_DLL enum ImageTrans
	{
		NONE = 0,
		R_90 = 1, 
		R_180 = 2,
		R_270 = 3,
		MIRROR = 4,
		MR_90 = 5, 
		MR_180 = 6,
		MR_270 = 7,
	} ImageTrans;

	class CC_DLL  Graphics2D
	{
	protected:
		Color4P				m_color;
		Blend				m_blend;

        vector<Blend>		m_stack_blend;
        vector<Color>		m_stack_color;

	private:
		IImage*				m_beginImage;
		GLfloat				m_beginImageMW;
		GLfloat				m_beginImageMH;
		GLfloat				m_beginImageW;
		GLfloat				m_beginImageH;

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
		void		setColorEnable(bool enable);


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
        void drawPolygon(float points[], int nPoints);
        
        // 填充多边形
        // points[x0,y0,x1,y1...]
        void fillPolygon(float points[], int nPoints);
        
        //////////////////////////////////////////////////////////////////////////////////
		// image
        
		void drawImage(IImage *src, float x, float y);
		
		void drawImageSize(IImage *src, float x, float y,
			float w, float h);
		
		void drawImageScale(IImage *src, float x, float y,
			float rate_w, float rate_h);

		void drawImageRegion(IImage *src, 
			float sx, float sy, float sw, float sh, 
			float dx, float dy);

		void drawImageRegionSize(IImage *src, 
			float sx, float sy, float sw, float sh,
			float dx, float dy, float dw, float dh);
		

		void beginImage(IImage* begin);

		void drawImage(float x, float y);

		void drawImageSize(float x, float y,
			float w, float h);

		void drawImageScale(float x, float y,
			float rate_w, float rate_h);

		void drawImageRegion(
			float sx, float sy, float sw, float sh, 
			float dx, float dy);

		void drawImageRegionSize(
			float sx, float sy, float sw, float sh,
			float dx, float dy, float dw, float dh);


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
	// GraphicsImage2D
	//------------------------------------------------------------------------------------------------

	class CC_DLL  BufferedGraphis2D : public Graphics2D
	{
		friend class IImage;
	protected:
		GLuint				m_uFBO;
		GLint				m_nOldFBO;
		IImage*				m_Buff;
		GLuint				m_width;
		GLuint				m_heigh;
	protected:
		BufferedGraphis2D(IImage *buff);
	public:
		virtual ~BufferedGraphis2D();

		inline int getWidth() {
			return m_width;
		}
		inline int getHeight() {
			return m_heigh;
		}
		inline GLuint getTextureName();

		void begin(void);
		void end(void);
		void clear(Color const &color);
	};
	//------------------------------------------------------------------------------------------------
	// Image
	//------------------------------------------------------------------------------------------------

	class CC_DLL  IImage
	{
		friend class BufferedGraphis2D;
	private:
		cocos2d::CCTexture2D* m_texture; // render texture
	protected:
		cocos2d::CCTexture2D* m_texture_f; // from a file
		cocos2d::CCTexture2D* m_texture_s; // from size
		GLuint m_width;
		GLuint m_height;

	protected:
		IImage(cocos2d::CCTexture2D* m_texture);

	public:
		virtual ~IImage();

		inline int getWidth() {
			return m_width;
		}
		inline int getHeight() {
			return m_height;
		}
		inline GLuint getTextureName() {
			return m_texture->getName();
		}
		inline CCTexture2D* getTexture2D() {
			return m_texture;
		}

		virtual BufferedGraphis2D* createGraphics();

	

	public:

		static IImage* createFromFile(string const &path);

		static IImage* createWithSize(int width, int height, Color const &color = Color(0,0,0,0));

		static IImage* createWithText(const char *text, const char *fontName, float fontSize);

	};


	//------------------------------------------------------------------------------------------------
	// Tiles
	//------------------------------------------------------------------------------------------------
	

	class CC_DLL  ITiles
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

		virtual void	copyPixcel(Graphics2D *g, int Index, float PosX, float PosY, int trans) = 0;

		virtual bool	isActive(int Index) = 0;

	}; // class ITiles
	
	//////////////////////////////////////////////////////////////////////
	// 整图输出
	//////////////////////////////////////////////////////////////////////
	class CC_DLL  TilesGroup : public ITiles
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

		virtual void	copyPixcel(Graphics2D *g, int Index, float PosX, float PosY, int trans);

		virtual bool	isActive(int Index);
	};

}; // namespace mf



#endif //_MF_GRAPHICS_2D

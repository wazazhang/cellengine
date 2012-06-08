//////////////////////////////////////////////////////////////////////////
// TextLayer策略
// 1. 当设备支持FrameBufferObject时，将TextLayer绘制到其表面。
// 2. 当设备支持pBuffer时，将TextLayer绘制到其表面。
// 3. 通过glCopyPixcel复制到Texture2D
// 4. 直接输出到屏幕（最坏的情况）
//////////////////////////////////////////////////////////////////////////

#ifndef _MF_UI_TEXT
#define _MF_UI_TEXT

#include "MFType.h"
#include "MFGraphics2D.h"
#include "MFUtil.h"
#include "MFXMLParser.h"
#include "MFUIBase.h"

#include "cocos2d.h"
#include <string>
#include <vector>
#include <fstream>
#include <stdarg.h>

namespace mf
{
	using namespace std;

	class CC_DLL TextAttribute;
	class CC_DLL TextLayerLine;
//	template class CC_DLL vector<IImage*>;
//	template class CC_DLL vector<TextLayerLine*>;
//	template class CC_DLL vector<TextAttribute>;
	/////////////////////////////////////////////////////////////////////////////////////////////
	// 
	/////////////////////////////////////////////////////////////////////////////////////////////


	class CC_DLL TextAttribute
	{
	public:
		Color	fontColor;	// alpha 0 表示无特性
		float	fontSize;	// 0 表示无特性
		string	fontName;	// "" 表示无特性
		string	resImage;	// "" 表示无特性
		string	resSprite;	// "" 表示无特性
		string	link;		// 标记此处可以被点击触发事件

		TextAttribute(
			Color	const & fColor = COLOR_NULL, 
			float   const & fSize = 0,
			string	const & fName = "",
			string	const & rImage = "",
			string	const & rSprite = "",
			string	const & pLink = "");
		~TextAttribute();	

		void combine(TextAttribute const &other);

		const bool isValid();

		static TextAttribute combine(TextAttribute const &src, TextAttribute const &dst);

	};

	/////////////////////////////////////////////////////////////////////////////////////////////
	// 
	/////////////////////////////////////////////////////////////////////////////////////////////


	class CC_DLL AttributedString 
	{
		friend class TextLayer;
	public:

	private:
		mfstring text;
		vector<TextAttribute> attributes;
	public:
		AttributedString();
		~AttributedString();

	public:// write method
		
		AttributedString* addAttribute(TextAttribute const &attribute) ;
	
		AttributedString* addAttribute(TextAttribute const &attribute, int beginIndex, int count);

		AttributedString* append(AttributedString const &other);

		AttributedString* append(mfstring const &other);

		AttributedString* append(mfstring const &other, TextAttribute const &ta);
		
		AttributedString* deleteString(int beginIndex, int count);

		AttributedString* clearString();

	public:// read method
		
		u16 length() const; // return char count

		mfstring toWString() const;

		//string toString(const char* local);

		virtual bool getTChar(u16 index , mfchar_t &out);

		bool getAttribute(u16 index, TextAttribute &out);

	public:
		
		static AttributedString* createFromXML(XMLNode *xml);
	};

	typedef CC_DLL enum TextAnchor
	{			
		taLEFT,
		taCENTER,
		taRIGHT,
	}
	TextAnchor;
	


	class CC_DLL TextLayerLine;
	class CC_DLL TextLayerClickInfo;

	class CC_DLL TextLayer 
	{
	protected:
		vector<IImage*> mChars;
		vector<TextLayerLine*> mLines;
		AttributedString mText;
		float mWidth;
		float mHeight;
		TextAnchor mAnchor;
		
		bool mEnableLineBreak;

	public:
		TextLayer(float width = 100, TextAnchor const anchor = taLEFT);
		
		virtual ~TextLayer();

	public:// write method
		
		TextLayer* setAnchor(TextAnchor const anchor);

		TextLayer* setEnableMultiline(bool multiline);
		
		TextLayer* setWidth(float width);

		TextLayer* setString(AttributedString const &text);

		TextLayer* append(AttributedString const &other);

		TextLayer* append(mfstring const &other);

		TextLayer* appendEndLine();

		TextLayer* deleteString(int beginIndex, int count);

		TextLayer* clearString();

	public:// read method

		AttributedString getText() const;

		u16 getTextLength() const;

		float getHeight() const;

		float getWidth() const;

		TextAnchor getAnchor() const;

		bool isMultiline() const;

		/// 渲染文本
		/// @parameter g	绘图指针
		/// @parameter px	绘制到目标x
		/// @parameter py	绘制到目标y
		/// @parameter pw	绘制范围宽
		/// @parameter ph	绘制范围高
		/// @parameter cy	自身坐标X
		/// @parameter cy	自身坐标Y
		virtual void render(Graphics2D *g, float px, float py, float pw, float ph, float cx, float cy);
		
		/// 点选文本
		/// @parameter cy	自身坐标X
		/// @parameter cy	自身坐标Y
		/// @parameter outInfo 输出点选信息
		virtual bool click(float cx, float cy, TextLayerClickInfo &outInfo);

	protected:

		// 将UNICODE编码成图形系统识别的文字
		virtual IImage* createCharImage(mfstring const &src, TextAttribute const &cttr);

		virtual bool testLineBreak(
			mfchar_t tchar, 
			int index, bool& isBlank,
			float curX, float totalWidth);

		virtual void resetChars(int begin, int count);
		virtual void resetLines();

		virtual void renderLine(
			Graphics2D* g, 
			float dx, float dy,
			TextLayerLine* const line,
			Rectangle2D const *region);
	};


	/////////////////////////////////////////////////////////////////////////////////////////////
	// 
	/////////////////////////////////////////////////////////////////////////////////////////////
	class CC_DLL TextLayerLine
	{
		friend class TextLayer;
	private:
		IImage* pBuffer;
		float w;
		float h;
		float x;
		float y;
		int charIndex;
		int charCount;
	public:
		TextLayerLine(
			int index, int count, 
			float w, float h, 
			float x, float y,
			vector<IImage*> mChars);
		~TextLayerLine();

		inline float getW() {
			return w;
		}
		inline float getH() {
			return h;
		}
		inline float getX() {
			return x;
		}
		inline float getY() {
			return y;
		}
	};

	class CC_DLL TextLayerClickInfo
	{
	public:
		int				mIndex;
		mfchar_t		mChar;
		IImage*			mTile;
		TextAttribute	mAttribute;
	};



	//////////////////////////////////////////////////////////////////////////

	class CC_DLL SimpleTextBox : public UICompoment
	{
	public:
		bool		mEnableScrollX;
		bool		mEnableScrollY;

	private:
		TextLayer	mLayer;

		Point2D		mLayerPos;
		Point2D		mLayerPosDown;

		Point2D		mScrollSpeed;

		bool		mMouseDown;
		Point2D		mMouseDownPoint;

	public:
		SimpleTextBox(int w, int h);
		virtual ~SimpleTextBox();


		bool initFromFile(const char* fileName);

		bool initWithXMLText(string const &xml);

		SimpleTextBox* appendText(AttributedString const &atext);
		SimpleTextBox* appendText(mfstring const &atext);
		SimpleTextBox* appendXMLText(string const &xml);
		SimpleTextBox* appendEndLine();
		SimpleTextBox* deleteText(int beginIndex, int count);
		SimpleTextBox* clearText();

		virtual void	setSize(u16 w, u16 h);

		virtual void	update(ccTime dt);
		virtual void	render(Graphics2D *g);
		

		virtual void onTouchBegan(TouchEvent const *cp);
		virtual void onTouchMoved(TouchEvent const *cp);
		virtual void onTouchEnded(TouchEvent const *cp);
		virtual void onTouchCancelled(TouchEvent const *cp);

		virtual void onTextClicked(TouchEvent const *cp, TextLayerClickInfo const *ck){}

		static SimpleTextBox* node(int w, int h);

	};




}; // namespace mf


#endif // #define _MF_UI_TEXT

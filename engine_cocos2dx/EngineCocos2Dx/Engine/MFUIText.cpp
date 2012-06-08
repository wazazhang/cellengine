
#include "MFType.h"
#include "MFUIText.h"
#include "MFUtil.h"
#include "MFXMLParser.h"
#include "MFMath.h"

namespace mf
{

	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////

	TextAttribute::TextAttribute(
		Color	const & fColor, 
		float   const & fSize,
		string	const & fName,
		string	const & rImage,
		string	const & rSprite,
		string	const & pLink)
	{
		this->fontColor = fColor;
		this->fontSize = fSize;
		this->fontName = fName;
		this->resImage = rImage;
		this->resSprite = rSprite;
		this->link = pLink;
	}
	TextAttribute::~TextAttribute(){}


	const bool TextAttribute::isValid()
	{
		if (fontColor.A != 0) {
			return true;
		}
		if (fontSize != 0) {
			return true;
		}
		if (fontName.length() != 0) {
			return true;
		}
		if (resImage.length() != 0) {
			return true;
		}
		if (resSprite.length() != 0) {
			return true;
		}
		if (link.length() != 0) {
			return true;
		}
	}

	void TextAttribute::combine(TextAttribute const &other)
	{
		if (other.fontColor.A != 0) {
			this->fontColor = other.fontColor;
		}
		if (other.fontSize != 0) {
			this->fontSize = other.fontSize;
		}
		if (other.fontName.length() != 0) {
			this->fontName = other.fontName;
		}
		if (other.resImage.length() != 0) {
			this->resImage = other.resImage;
		}
		if (other.resSprite.length() != 0) {
			this->resSprite = other.resSprite;
		}
		if (other.link.length() != 0) {
			this->link = other.link;
		}
	}

	TextAttribute TextAttribute::combine(TextAttribute const &src, TextAttribute const &dst)
	{
		TextAttribute ret = src;
		ret.combine(dst);
		return ret;
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	
	AttributedString::AttributedString()
	{
		append(text);
	}

	AttributedString::~AttributedString()
	{

	}

	AttributedString* AttributedString::append(AttributedString const &other)
	{
		text += other.text;
		for (u16 i=0; i<other.text.length(); ++i) {
			attributes.push_back(other.attributes[i]);
		}
		return this;
	}

	AttributedString* AttributedString::append(mfstring const &other)
	{
		if (attributes.size() > 0) {
			TextAttribute &lastAttr = attributes[attributes.size() - 1];
			append(other, lastAttr);
		} else {
			append(other, TextAttribute());
		}
		return this;
	}

	AttributedString* AttributedString::append(mfstring const &other, TextAttribute const &ta)
	{
		text += other;
		for (u16 i=0; i<other.length(); ++i) {
			attributes.push_back(ta);
		}
		return this;
	}


	AttributedString* AttributedString::addAttribute(TextAttribute const &attribute)
	{
		return addAttribute(attribute, 0, text.length());
	}

	AttributedString* AttributedString::addAttribute(TextAttribute const &attr, int beginIndex, int count)
	{
		int endIndex = beginIndex + count;
		for (u16 i = beginIndex; i<endIndex; ++i) {
			attributes[i].combine(attr);
		}
		return this;
	}

	AttributedString* AttributedString::deleteString(int beginIndex, int count)
	{
		text.erase(beginIndex, count);
		vector<TextAttribute>::iterator beginIT = attributes.begin() + beginIndex;
		vector<TextAttribute>::iterator endIT = attributes.begin() + beginIndex + count;
		attributes.erase(beginIT, endIT);
		return this;
	}


	AttributedString* AttributedString::clearString()
	{
		return deleteString(0, length());
	}

	u16 AttributedString::length() const
	{
		return text.length();
	}

	

	

	mfstring AttributedString::toWString() const
	{
		return text;
	}

// 	string AttributedString::toString(const char* local)
// 	{
// 		return StringConvert::ws2s(text, local);
// 	}

	bool AttributedString::getTChar(u16 index , mfchar_t &out)
	{
		if (index < text.length()) {
			out = text.at(index);
			return true;
		}
		return false;
	}


	bool AttributedString::getAttribute(u16 index, TextAttribute &out)
	{
		if (index < attributes.size()) {
			out = attributes[index];
			return true;
		}
		return false;
	}


	//////////////////////////////////////////////////////////////////////////////////////////
	// 
	//////////////////////////////////////////////////////////////////////////////////////////
	// 从XML编译
	CC_DLL extern void internalBuildXML(AttributedString* atext, XMLNode *node, TextAttribute const &parentAttr);

	AttributedString* AttributedString::createFromXML(XMLNode *xml)
	{
		AttributedString* attr = new AttributedString();
		TextAttribute curAttr(COLOR_WHITE, 16, "song");
		internalBuildXML(attr, xml, curAttr);
		return attr;
	}

#define MF_TEXT_XML_KEY_COLOR	"color"
#define MF_TEXT_XML_KEY_SIZE	"size"
#define MF_TEXT_XML_KEY_FACE	"face"
#define MF_TEXT_XML_KEY_LINK	"link"

#define MF_TEXT_XML_NODE_BREAK	"br"
#define MF_TEXT_XML_NODE_SPACE	"p"

	void internalBuildXML(AttributedString* atext, XMLNode *node, TextAttribute const &parentAttr)
	{
		string const &nname = node->getName();
		string const &nvalue = node->getValue();

		TextAttribute attr;
		{
			if (node->isAttribute(MF_TEXT_XML_KEY_COLOR)) {
				u32 keyColor = node->getAttributeAsHexU32(MF_TEXT_XML_KEY_COLOR);
				attr.fontColor.setARGB(keyColor);
			}
			if (node->isAttribute(MF_TEXT_XML_KEY_SIZE)) {
				attr.fontSize = node->getAttributeAsInt(MF_TEXT_XML_KEY_SIZE);
			}
			if (node->isAttribute(MF_TEXT_XML_KEY_FACE)) {
				attr.fontName = node->getAttribute(MF_TEXT_XML_KEY_FACE);
			}
			if (node->isAttribute(MF_TEXT_XML_KEY_LINK)) {
				attr.link = node->getAttribute(MF_TEXT_XML_KEY_LINK);
			}
		}

		if (nvalue.length() > 0 || node->childCount() > 0)
		{
			mfstring const &body = StringConvert::utf8ToWS(nvalue);
			//wcout<<"body : "<<body<<endl;
			if (attr.isValid()) {
				attr = TextAttribute::combine(parentAttr, attr);
				atext->append(body, attr);
				for (XMLIterator it = node->childBegin(); it!=node->childEnd(); ++it) {
					internalBuildXML(atext, *it, attr);
				}
			} else {
				atext->append(body, parentAttr);
				for (XMLIterator it = node->childBegin(); it!=node->childEnd(); ++it) {
					internalBuildXML(atext, *it, parentAttr);
				}
			}
		}

		if (stringEquals(nname, MF_TEXT_XML_NODE_BREAK))
		{
			atext->append(StringConvert::utf8ToWS("\n"));
		}
		else if (stringEquals(nname, MF_TEXT_XML_NODE_SPACE))
		{
			atext->append(StringConvert::utf8ToWS(" "));
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////////////////////////
	/*
	{
	// create a insert text sprite and do some action
	CCLabelTTF * label = CCLabelTTF::labelWithString(text, FONT_NAME, FONT_SIZE);
	this->addChild(label);
	ccColor3B color = { 226, 121, 7};
	label->setColor(color);
	}*/
	

	
	TextLayer::TextLayer(float width, TextAnchor const anchor) 
	{
		mWidth = width;
		mAnchor = anchor;
		mEnableLineBreak = true;
	}

	TextLayer::~TextLayer()
	{
		// clear old lines
		for (vector<TextLayerLine*>::iterator it = mLines.begin(); it != mLines.end(); it ++) {
			delete (*it);
		}
		mLines.clear();
		// delete old chars
		for (vector<IImage*>::iterator it = mChars.begin(); it != mChars.end(); it ++) {
			delete (*it);
		}
		mChars.clear();
	}

	AttributedString TextLayer::getText() const
	{
		return mText;
	}

	u16 TextLayer::getTextLength() const
	{
		return mText.length();
	}

	float TextLayer::getHeight() const
	{
		return mHeight;
	}

	float TextLayer::getWidth() const
	{
		return mWidth;
	}


	TextAnchor TextLayer::getAnchor() const
	{
		return mAnchor;
	}

	bool TextLayer::isMultiline() const
	{
		return mEnableLineBreak;
	}


	TextLayer* TextLayer::setWidth(float width)
	{
		if (mWidth != width) {
			mWidth = width;
			resetLines();
		}
		return this;
	}

	TextLayer* TextLayer::setEnableMultiline(bool multiline)
	{
		if (mEnableLineBreak != multiline) {
			mEnableLineBreak = multiline;
			resetLines();
		}
		return this;
	}

	TextLayer* TextLayer::setAnchor(TextAnchor const anchor)
	{
		if (mAnchor != anchor) {
			mAnchor = anchor;
			resetLines();
		}
		return this;
	}

	TextLayer* TextLayer::setString(AttributedString const &text)
	{
		mText = text;
		resetChars(0, mText.length());
		return this;
	}

	TextLayer* TextLayer::append(AttributedString const &other)
	{
		if (other.length() > 0) {
			int begin = mText.length();
			mText.append(other);
			resetChars(begin, mText.length() - begin);
		}
		return this;
	}

	TextLayer* TextLayer::append(mfstring const &other)
	{
		if (other.length() > 0) {
			int begin = mText.length();
			mText.append(other);
			resetChars(begin, mText.length() - begin);
		}
		return this;
	}

	TextLayer* TextLayer::appendEndLine()
	{
		append(L"\n");
		return this;
	}

	TextLayer* TextLayer::deleteString(int beginIndex, int count)
	{
		if (count > 0) {
			mText.deleteString(beginIndex, count);
			resetChars(beginIndex, -count);
		}
		return this;
	}

	TextLayer* TextLayer::clearString()
	{
		return deleteString(0, getTextLength());
	}

	

	void TextLayer::render(Graphics2D *g, float px, float py, float pw, float ph, float cx, float cy)
	{
		g->pushColor();
		g->pushTransform();
		g->translate(px, py);
		//g->clipRect(cx, cy, pw, ph);
		Rectangle2D region(cx, cy, pw, ph);
		for (vector<TextLayerLine*>::iterator it = mLines.begin(); it != mLines.end(); it ++) {
			TextLayerLine* line = (*it);
			// check is in visible region
			if (Math::intersectRectWH(
				cx, cy, pw, ph, 
				line->getX(), line->getY(), line->getW(), line->getH()))
			{
				//g->drawImage(line->pBuffer, sx, sy);
				//line->pBuffer->getTexture2D()->drawAtPoint(sx, sy);
				renderLine(g, line->getX(), line->getY(), line, &region);
			}
		}
		//g->clipClean();
		g->popTransform();
		g->popColor();
	}

	void TextLayer::renderLine(
		Graphics2D* g, 
		float dx, float dy,
		TextLayerLine* const line,
		Rectangle2D const *region)
	{
		float mw = dx;
		float mh = dy + line->h;
		switch (mAnchor)
		{
		case taCENTER:
			mw += (mWidth - line->w)/2;
			break;
		case taRIGHT:
			mw += (mWidth - line->w);
			break;
		}
		int endIndex = line->charIndex + line->charCount;
		for (int i=line->charIndex; i<endIndex; i++) 
		{
			IImage* const cimg = mChars[i];
			float ty = mh - cimg->getHeight();
			float tx = mw;
			if (Math::intersectRectWH(
				region->x, region->y, region->width, region->height,
				tx, ty, cimg->getWidth(), cimg->getHeight()))
			{
				TextAttribute const &attr = mText.attributes[i];
				g->setColor(attr.fontColor);
				g->drawImage(cimg, tx, ty);
				//g->drawRect(tx, ty, cimg->getWidth(), cimg->getHeight());
			}
			mw += cimg->getWidth();
		}
	}

	bool TextLayer::click(float cx, float cy, TextLayerClickInfo &outInfo)
	{
		for (vector<TextLayerLine*>::iterator it = mLines.begin(); it != mLines.end(); it ++) 
		{
			TextLayerLine* line = (*it);

			float mw = line->x;
			float mh = line->y;
			switch (mAnchor)
			{
			case taCENTER:
				mw += (mWidth - line->w)/2;
				break;
			case taRIGHT:
				mw += (mWidth - line->w);
				break;
			}

			if (Math::includeRectPointWH(
				mw, mh, line->w, line->h, cx, cy))
			{
				mh += line->h;
				int endIndex = line->charIndex + line->charCount;
				for (int i=line->charIndex; i<endIndex; ++i) {
					IImage* const cimg = mChars.at(i);
					float ty = mh - cimg->getHeight();
					float tx = mw;
					if (Math::includeRectPointWH(
						tx, ty, cimg->getWidth(), cimg->getHeight(), cx, cy))
					{
						outInfo.mIndex		= i;
						outInfo.mAttribute  = mText.attributes.at(i);
						outInfo.mChar		= mText.text.at(i);
						outInfo.mTile		= mChars.at(i);
						return true;
					} else {
						mw += cimg->getWidth();
					}
				}
			}
		}
		return false;
	}


	IImage* TextLayer::createCharImage(mfstring const &src, TextAttribute const &cttr)
	{
		// 将UNICODE编码成图形系统识别的文字
		string const &cstr = StringConvert::wsToUTF8(src);
		IImage* cimg = IImage::createWithText(
			cstr.c_str(), 
			cttr.fontName.c_str(), 
			cttr.fontSize);
		return cimg;
	}

	// 测试此处字符是否需要换行
	bool TextLayer::testLineBreak(
		mfchar_t tchar, 
		int index, bool& isBlank,
		float curX, float totalWidth)
	{
		if(tchar == '\n') {
			isBlank = true;
			return true;
		}

		float sx = curX;
		for (int i=index; i<mChars.size();) {
			IImage* cimg = mChars[i];
			sx += cimg->getWidth();
			if (sx > totalWidth) {
				return true;
			}
			++i;
			if (i<mChars.size())
			{
				mfchar_t ch = mText.text.at(i);
				if ((ch >= 'a' && ch <= 'z') || 
					(ch >= 'A' && ch <= 'Z') || 
					(ch == '-')){
					continue;
				} else {
					break;
				}
			}
		}

		return false;
	}

	/// begin 开始处理的地方
	/// count 处理多少字符，负数表示删除
	void TextLayer::resetChars(int begin, int count)
	{
		if (count < 0) {
			// delete old chars
			for (int i=(-count); i>=0; --i) {
				vector<IImage*>::iterator cit = mChars.begin() + begin + i;
				IImage* cimg = (*cit);
				mChars.erase(cit);
				delete cimg;
			}
		} else {
			// append new chars
			for (int i=0; i<count; i++) {
				IImage* cimg = createCharImage(
					mText.text.substr(begin+i, 1), 
					mText.attributes[begin+i]);
				mChars.insert(mChars.begin() + begin + i, cimg);
			}
		}

		resetLines();
	}

	void TextLayer::resetLines()
	{
		// clear old lines
		for (vector<TextLayerLine*>::iterator it = mLines.begin(); it != mLines.end(); it ++) {
			delete (*it);
		}
		mLines.clear();

		// reset lines
		int const count = mText.text.length();
		float t_height = 0;
		int   t_lastIndex = 0;
		bool  t_isBlank = false;
		float linew = 0;
		float lineh = 0;
		for (int i=0; i<count; ++i) 
		{
			mfchar_t tchar = mText.text.at(i);
			IImage* cimg = mChars[i];
			// check line break
		
			if ((i >= mText.length() - 1) || // <-- 如果已到最后
				(mEnableLineBreak && testLineBreak(tchar, i, t_isBlank, linew, mWidth))) // <-- 测试换行
			{
				// 创建行缓冲区
				TextLayerLine* lineBuffer = new TextLayerLine(
					t_lastIndex, i - t_lastIndex, 
					linew, lineh, 
					0, t_height,
					mChars);
				mLines.push_back(lineBuffer);
				t_height += lineh;
				t_lastIndex = i;
				lineh = 0;
				linew = 0;
				continue;
			} else {
				lineh = MAX(lineh, cimg->getHeight());
				linew = linew + cimg->getWidth();
			}
		}
		this->mHeight = t_height;
	}

	////////////////////////////////////////////////////////////////////////////////

	TextLayerLine::TextLayerLine(
		int index, int count, 
		float w, float h, 
		float x, float y, 
		vector<IImage*> mChars)
	{
		//this->pBuffer = IImage::createWithSize(w, h);
		this->pBuffer = NULL;
		this->x = x;
		this->y = y;
		this->w = w;
		this->h = h;
		this->charIndex = index;
		this->charCount = count;
	}

	TextLayerLine::~TextLayerLine()
	{
		if (pBuffer) {
			delete pBuffer;
			pBuffer = NULL;
		}
	}






	////////////////////////////////////////////////////////////////////////////////////////////

	SimpleTextBox* SimpleTextBox::node(int w, int h)
	{
		SimpleTextBox *node = new SimpleTextBox(w, h);
		if (node) {
			node->autorelease();
			return node;
		}
		CC_SAFE_DELETE(node);
		return NULL;
	}

	SimpleTextBox::SimpleTextBox(int w, int h) : UICompoment(w, h)
	{
		mEnableScrollX = false;
		mEnableScrollY = true;
		mLayer.setWidth(w);
		mLayer.setAnchor(taLEFT);
		setClipBounds(true);
	}

	SimpleTextBox::~SimpleTextBox(){
	}

	void SimpleTextBox::setSize(u16 w, u16 h)
	{
		UICompoment::setSize(w, h);
		mLayer.setWidth(w);
	}

	bool SimpleTextBox::initFromFile(const char* fileName)
	{
		mLayer.clearString();
		XMLNode* xml = XMLNode::parseFromFile(fileName);
		if (xml) {
			AttributedString* atext = AttributedString::createFromXML(xml);
			if (atext) {
				mLayer.append(*atext);
				delete atext;
			}
			delete xml;
			return true;
		}
		return false;
	}

	bool SimpleTextBox::initWithXMLText(string const &text)
	{
		mLayer.clearString();
		XMLNode* xml = XMLNode::parseFromText(text);
		//this->mItemID = xml->getAttributeAsInt("id");
		if (xml) {
			AttributedString* atext = AttributedString::createFromXML(xml);
			if (atext) {
				mLayer.append(*atext);
				delete atext;
			}
			delete xml;
			return true;
		}
		return false;
	}


	SimpleTextBox* SimpleTextBox::appendText(AttributedString const &atext)
	{
		mLayer.append(atext);
		return this;
	}

	SimpleTextBox* SimpleTextBox::appendText(mfstring const &atext)
	{
		mLayer.append(atext);
		return this;
	}

	SimpleTextBox* SimpleTextBox::appendXMLText(string const &text)
	{
		XMLNode* xml = XMLNode::parseFromText(text);
		if (xml) {
			AttributedString* atext = AttributedString::createFromXML(xml);
			if (atext) {
				mLayer.append(*atext);
				delete atext;
			}
			delete xml;
		}
		return this;
	}

	SimpleTextBox* SimpleTextBox::appendEndLine()
	{
		mLayer.appendEndLine();
		return this;
	}

	SimpleTextBox* SimpleTextBox::deleteText(int beginIndex, int count)
	{
		mLayer.deleteString(beginIndex, count);
		return this;
	}

	SimpleTextBox* SimpleTextBox::clearText()
	{
		mLayer.clearString();
		return this;
	}

	void SimpleTextBox::update(ccTime dt)
	{

	}


	void SimpleTextBox::render(Graphics2D *g)
	{
		UICompoment::render(g);

		

		mLayer.render(g, 
			mLayerPos.x,
			mLayerPos.y,
			getWidth(), 
			getHeigh(), 
			-mLayerPos.x,
			-mLayerPos.y);

		if (!mMouseDown) {
			if (mEnableScrollX && mScrollSpeed.x != 0) {
				mLayerPos.x += mScrollSpeed.x;
				mScrollSpeed.x *= 0.01;
			}
			if (mEnableScrollY && mScrollSpeed.y != 0) {
				mLayerPos.y += mScrollSpeed.y;
				mScrollSpeed.y *= 0.01;
			}
		}
	}
	

	// void SimpleTextBox::onClick(TextLayer* p, TextAttribute* attr)
	// {
	// 	//if (attr->link == "onItenClick") {
	// 	//	//popMenu();
	// 	//}
	// }

	void SimpleTextBox::onTouchBegan(TouchEvent const *cp)
	{
		mMouseDown = true;
		mScrollSpeed.x = 0;
		mScrollSpeed.y = 0;
		mMouseDownPoint = cp->point;
		mLayerPosDown = mLayerPos;

		//cout<<"click: "<<cp->point.x<<","<<cp->point.y<<endl;

		TextLayerClickInfo ck;
		if (mLayer.click(
			cp->point.x-mLayerPos.x, 
			cp->point.y-mLayerPos.y, 
			ck)) 
		{
			onTextClicked(cp, &ck);
			//wcout<<"click: char="<<ck.mChar<<" link="<<(ck.mAttribute.link.c_str())<<endl;
		}
	}

	void SimpleTextBox::onTouchMoved(TouchEvent const *cp)
	{
		if (mMouseDown) {
			float dx = cp->point.x - mMouseDownPoint.x;
			float dy = cp->point.y - mMouseDownPoint.y;
			if (mEnableScrollX) {
				mLayerPos.x = mLayerPosDown.x + dx;
			}
			if (mEnableScrollY) {
				mLayerPos.y = mLayerPosDown.y + dy;
			}
		}
	}
	void SimpleTextBox::onTouchEnded(TouchEvent const *cp)
	{
		mMouseDown = false;
		if (mEnableScrollX) {
			mScrollSpeed.x = (cp->point.x - cp->prewPoint.x) * 10.0f;
		}
		if (mEnableScrollY) {
			mScrollSpeed.y = (cp->point.y - cp->prewPoint.y) * 10.0f;
		}
		//cout<<mScrollSpeed.y<<endl;
	}

	void SimpleTextBox::onTouchCancelled(TouchEvent const *cp)
	{
		cout<<"onTouchCancelled"<<endl;
	}


}; // namespace mf
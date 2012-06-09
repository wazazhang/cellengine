
#include "UITestNode.h"
#include "MFEngine.h"

#include <iostream>
#include <string>
USING_NS_CC;

using namespace mf;
using namespace std;

UITestNode::UITestNode()
{
	string s("a");
	s.substr(1);

	string vs = "what {0%s} is {3%s} the {2%d} matrix {1%d}";
	cout<< vs <<endl;
	cout<< stringFormat(vs, "#0", 1, 2, "#3") <<endl;


	m_timer = 0;
	m_ticktime = 0;
	m_mouseDown = false;

	setIsTouchEnabled(true);


	CCSize wsize = CCDirector::sharedDirector()->getWinSize();
	
	// 文本
	// test text encode
	{
 		text_layer = new TextLayer(400);
	// 	AttributedString atext(L"abc场景吞食天地\n感谢您选择了 JIRA.\n\
	// 		欢迎使用JIRA—帮助您的团队轻松地跟踪与管理项目\n\
	// 		\n\
	// 		Get up to speed with an in-depth, interactive Atlassian training course.\n\
	// 		从哪里开始?\n\
	// 		访问 JIRA 101 guide了解如何配置JIRA");
		//atext.addAttribute(TextAttribute(COLOR_YELLOW, 16, "hei"));
		//atext.addAttribute(TextAttribute(COLOR_GREEN, 32, "song"), 10, 6);
		//text_layer->append(atext);
		{
			XMLNode* ndf = XMLNode::parseFromFile("Text.xml");
			AttributedString* atext = AttributedString::createFromXML(ndf);
			//text_layer->setAnchor(taLEFT);
			//text_layer->setAnchor(taRIGHT);
			text_layer->append(*atext);
			delete atext;
			delete ndf;
		}
		

		m_textCamera.width = 200;
		m_textCamera.height = 200;

		{
			string xmldata ;
			loadAllText("Text.xml", xmldata);
			mfstring wxmldata;
			StringConvert::utf8StreamToWS(xmldata, wxmldata);
			cout<<wxmldata.c_str()<<endl;

			//XMLNode* ndf = XMLNode::parseFromText(xmldata.c_str());
			//XMLNode* ndf = XMLNode::parseFromFile("Text.xml");
			//cout<<ndf->toString()<<endl;
			//delete ndf;
		}

		
	}

	{
		string arg0 = StringConvert::wsToUTF8(L"旺财");
		string arg1 = StringConvert::wsToUTF8(L"9527");

		string xmlstr1; loadAllText("Text2.xml", xmlstr1);

		string xmlstr2; loadAllText("Text3.xml", xmlstr2);

		string fstr1 = stringFormat(xmlstr1, arg0.c_str(), arg1.c_str(), 127);

		string fstr2 = stringFormat(xmlstr2, arg0.c_str(), arg1.c_str(), 65535);


		wcout<<StringConvert::utf8ToWS(fstr1)<<endl;
		wcout<<StringConvert::utf8ToWS(fstr2)<<endl;



		SimpleTextBox * textBox = SimpleTextBox::node(400, 400);
		textBox->setLayerRect(UILayerRect(UILAYER_STYLE_NULL));
		textBox->initFromFile("Text.xml");
		//textBox->initWithText("<p size=\"16\" face=\"song\" >what is the matrix!</p>");
		textBox->setPosition(10, 20);
		addChild(textBox);
		textBox->appendEndLine();
		textBox->appendXMLText(fstr1);
		textBox->appendEndLine();
		textBox->appendXMLText(fstr2);
	}

	if (false)
	{
		UICompoment* pNode = new UICompoment(200, 200);
		pNode->setPosition(500, 20);
		pNode->setClipBounds(true);
		pNode->addTouchListener(this);
		pNode->setTag(-10);
		pNode->getLayerRect().back_color = Color(0.5f, 0, 0, 1);
		addChild(pNode);

		int i = 0;
		for (int y=0; y<5; y++) {
			for (int x=0; x<5; x++) {
				string userdata = stringFormat("snode {0%d} {1%d}", x, y);
				UICompoment* snode = new UICompoment(40, 40);
				snode->setPosition(x*50+5, y*50+5);
				snode->addTouchListener(this);
				snode->setTag(i);
				pNode->addChild(snode);
				i++;
			}
		}
	}
	if (false)
	{
		UICompoment* pNode = new UICompoment(200, 200);
		pNode->setPosition(500 + 116, 20 + 53);
		pNode->setClipBounds(true);
		pNode->addTouchListener(this);
		pNode->setTag(-10);
		pNode->getLayerRect().back_color = Color(0.5f, 0.5f, 0, 1);
		addChild(pNode);

		int i = 100;
		for (int y=0; y<5; y++) {
			for (int x=0; x<5; x++) {
				string userdata = stringFormat("snode {0%d} {1%d}", x, y);
				UICompoment* snode = new UICompoment(40, 40);
				snode->setPosition(x*50+5, y*50+5);
				snode->addTouchListener(this);
				snode->setTag(i);
				pNode->addChild(snode);
				i++;
			}
		}
	}


	{
		m_uiEdit = new MyUIEdit("uiedit");
		CCNode* comp = m_uiEdit->createFromFile("test.gui.xml");
		addChild(comp);
	}



	schedule(schedule_selector(UITestNode::update));
}

MyUIEdit::MyUIEdit(string const &res_root) : UIEdit(res_root)
{

}

// 覆盖UIEdit的方法，创建自定义控件
CCNode*	MyUIEdit::createComponent(XMLNode* e)
{	
	string aname = e->getAttribute("name");

	if (stringEquals(aname, "TextBox"))
	{
		UILayerRect layout;
		XMLNode* elayout = e->findChild("layout");
		if (elayout) {
			getLayout(elayout, layout);
		}

		SimpleTextBox * textBox = SimpleTextBox::node(
			e->getAttributeAsInt("width"), 
			e->getAttributeAsInt("height"));
		textBox->setPosition(
			e->getAttributeAsFloat("x"),
			e->getAttributeAsFloat("y"));
		
		textBox->setLayerRect(layout);
		textBox->initFromFile("Text.xml");
		textBox->setEnable(true);
		return textBox;
	}
	return UIEdit::createComponent(e);
}

UITestNode::~UITestNode()
{
	delete text_layer;
	delete m_uiEdit;
}


void UITestNode::update(ccTime dt)
{
	int ticktime = m_ticktime;

	CCSize wsize = CCDirector::sharedDirector()->getWinSize();
	// 各种移动摄像机测试
	if (m_mouseDown) 
	{
		float dx = Math::getDirect(m_curPoint.x - wsize.width/2);
		float dy = Math::getDirect(m_curPoint.y - wsize.height/2);

		m_textCamera.x = m_curPoint.x - m_textCamera.width / 2;
		m_textCamera.y = m_curPoint.y - m_textCamera.height / 2;
	}
	m_timer += 0.1f;
	m_ticktime += 1;
}

void UITestNode::draw()
{
	CCSize wsize = CCDirector::sharedDirector()->getWinSize();

	mf::Graphics2D g;

	//UILayerRect *rect = UILayerRectManager::getInstance()->getDefaultRect(NULL);

	//rect->render(&g, 100, 100, 300, 400);


	if (false)
	{
		g.pushTransform();

		g.setColor(COLOR_GRAY);
		g.fillRect(
			m_textCamera.x, 
			m_textCamera.y,
			m_textCamera.width, 
			m_textCamera.height);

		text_layer->render(&g, 
			0, 0,
			m_textCamera.width, m_textCamera.height, 
			m_textCamera.x, 
			m_textCamera.y);

		g.popTransform();
	}
}


//////////////////////////////////////////////////////////////////////////

void UITestNode::onTouchBegan(UICompoment* sender, TouchEvent const *cp)
{
	cout<<"onTouchBegan: "<<sender->getTag()<<endl;
}

void UITestNode::onTouchMoved(UICompoment* sender, TouchEvent const *cp)
{
	cout<<"onTouchMoved: "<<sender->getTag()<<endl;
}

void UITestNode::onTouchEnded(UICompoment* sender, TouchEvent const *cp)
{
	cout<<"onTouchEnded: "<<sender->getTag()<<endl;
}

//////////////////////////////////////////////////////////////////////////

void UITestNode::ccTouchesBegan(CCSet *pTouches, CCEvent *pEvent)
{ 
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it); 


	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	m_curPoint = convertToNodeSpace(m_curPoint);

	m_mouseDown = true;
}

void UITestNode::ccTouchesMoved(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);

	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	m_curPoint = convertToNodeSpace(m_curPoint);
}

void UITestNode::ccTouchesEnded(CCSet *pTouches, CCEvent *pEvent)
{
	CCSetIterator it = pTouches->begin();
	CCTouch* touch = (CCTouch*)(*it);
	m_curPoint = CCDirector::sharedDirector()->convertToGL( touch->locationInView() );
	m_curPoint = convertToNodeSpace(m_curPoint);
	m_mouseDown = false;
}
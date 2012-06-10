#include "MFUIEdit.h"
#include "MFMath.h"

USING_NS_CC;

namespace mf_uiedit
{
	using namespace mf;

	static const string UERoot_ClassName			= "com.g2d.studio.ui.edit.gui.UERoot";
	static const string UEButton_ClassName			= "com.g2d.studio.ui.edit.gui.UEButton";
	static const string UEToggleButton_ClassName	= "com.g2d.studio.ui.edit.gui.UEToggleButton";
	static const string UEImageBox_ClassName		= "com.g2d.studio.ui.edit.gui.UEImageBox";
	static const string UELabel_ClassName			= "com.g2d.studio.ui.edit.gui.UELabel";
	static const string UECanvas_ClassName			= "com.g2d.studio.ui.edit.gui.UECanvas";
	static const string UETextInput_ClassName		= "com.g2d.studio.ui.edit.gui.UETextInput";
	static const string UETextBox_ClassName			= "com.g2d.studio.ui.edit.gui.UETextBox";

	// 根据子节点名字查找子节点
	// @childName 节点名字
	// @recursion 是否递归
	UEComponent* UEComponent::findChild(string const &childName, bool recursion)
	{
		if(m_pChildren && m_pChildren->count() > 0)
		{
			CCNode  *pNode;
			ccArray *arrayData = getChildren()->data;

			for(int i = 0 ; i < arrayData->num; i++ )
			{
				pNode = (CCNode*) arrayData->arr[i];
				if ( pNode) 
				{
					UEComponent* ue = dynamic_cast<UEComponent*>(pNode);
					if (ue && stringEquals(ue->name, childName)) {
						return ue;
					}
				}
			}

			// 广度遍历
			if (recursion)
			{
				for( int i = 0; i < arrayData->num; i++ )
				{
					pNode = (CCNode*) arrayData->arr[i];
					if ( pNode) 
					{
						UEComponent* ue = dynamic_cast<UEComponent*>(pNode);
						if (ue && stringEquals(ue->name, childName)) {
							UEComponent* schild = ue->findChild(childName, recursion);
							if (schild) {
								return schild;
							}
						}
					}
				}
			}
		}

		return NULL;
	}
	UEComponent::UEComponent():UICompoment(100,100)
	{
	}
	string UEComponent::getEditUserData(){
		return euser_data;
	}
	string UEComponent::getName(){
		return name;
	}
	int UEComponent::getEditUserTag(){
		return euser_tag;
	}
// 	XMLNode* UEComponent::getChild(XMLNode* e,string const &childName){
// 		XMLNode* child = e->findChild(childName);
// 		return child;
// 	}
// 	UILayerValue UEComponent::getLayerValue(){
// 		return layerValue;
// 	}
	void UEComponent::onRead(UIEdit* edit, XMLNode* e)
	{
		this->name = 
			e->getAttribute("name");

		this->setClipBounds(
			e->getAttributeAsBool("clipbounds"));

		this->setSize(
			e->getAttributeAsInt("width"), 
			e->getAttributeAsInt("height"));
		this->setPosition(
			e->getAttributeAsFloat("x"),
			e->getAttributeAsFloat("y"));
		/*
		this->setZOrder(
			e->getAttributeAsInt("z"));
		*/
		this->euser_data =
			e->getAttribute("userData");
		this->euser_tag =
			e->getAttributeAsInt("userTag");

		this->setEnable(
			e->getAttributeAsBool("enable_focus"));
		this->setIsVisible(
			e->getAttributeAsBool("visible"));

		XMLNode* e_layout = e->findChild("layout");
		if (e_layout != NULL) {
			edit->getLayout(e_layout, this->mLayerRect);
		}

	}


	UERoot::UERoot(){}
	void UERoot::onRead(UIEdit* edit, XMLNode* e){
		UEComponent::onRead(edit, e);
	}


	UEButton::UEButton(){
		touchPressed = false;
	}
	string UEButton::getText(){
		return text;
	}
// 	UILayerValue UEButton::getlayerValueDown(){
// 		return layerValue_down;
// 	}
	void UEButton::onRead(UIEdit* edit, XMLNode* e){
		UEComponent::onRead(edit, e);
		XMLNode* e_layout_down = e->findChild("layout_down");			//# 按下时的 样式
		if (e_layout_down) {
			edit->getLayout(e_layout_down, this->layout_down);
		}

		this->textColor.setARGB(e->getAttributeAsHexU32("textColor"));	//# 文本颜色
		this->text			= e->getAttribute("text");					//# 文本
		this->text_anchor	= e->getAttribute("text_anchor");			//# 文本 对齐方式   
		this->text_offset_x	= e->getAttributeAsInt("text_offset_x");	//# 文本 绘制偏移坐标
		this->text_offset_y	= e->getAttributeAsInt("text_offset_y");	//# 文本 绘制偏移坐标

		this->cclable = CCLabelTTF::labelWithString(
			text.c_str(),
			CCSize(getWidth(), getHeigh()), 
			CCTextAlignmentCenter, 
			"Arial", 12);
		this->cclable->getTexture()->setAliasTexParameters();
		this->cclable->setScaleY(-1);
		this->cclable->setPosition(ccp(getWidth()/2, getHeigh()/2));
		this->addChild(cclable);
	}
	void UEButton::onTouchBegan(TouchEvent const *cp){
		this->touchPressed = true;
	}
	void UEButton::onTouchMoved(TouchEvent const *cp){
	}
	void UEButton::onTouchEnded(TouchEvent const *cp){
		this->touchPressed = false;
	}
	void UEButton::render(Graphics2D *g){
		if (touchPressed) {
			layout_down.render(g, 0, 0,getWidth(), getHeigh());
		} else {
			mLayerRect.render(g, 0, 0,getWidth(), getHeigh());
		}
	}



	UEToggleButton::UEToggleButton(){}
	void UEToggleButton::onRead(UIEdit* edit, XMLNode* e){
		UEButton::onRead(edit, e);
		isChecked = e->getAttributeAsBool("isChecked");
	}
	void UEToggleButton::onTouchBegan(TouchEvent const *cp){
		isChecked = !isChecked;
	}
	void UEToggleButton::onTouchMoved(TouchEvent const *cp){

	}
	void UEToggleButton::onTouchEnded(TouchEvent const *cp){

	}
	void UEToggleButton::render(Graphics2D *g){
		if (isChecked) {
			layout_down.render(g, 0, 0,getWidth(), getHeigh());
		} else {
			mLayerRect.render(g, 0, 0,getWidth(), getHeigh());
		}
	}


	UEImageBox::UEImageBox(){
		mLayerRect.style = UILAYER_STYLE_NULL;
	}
	void UEImageBox::onRead(UIEdit* edit, XMLNode* e){
		UEComponent::onRead(edit, e);
		imageSrc = edit->getUIResImage(e->getAttribute("imagePath"));
		x_rotate = e->getAttributeAsFloat("x_rotate");
		x_scaleX = e->getAttributeAsFloat("x_scaleX")/100.0f;
		x_scaleY = e->getAttributeAsFloat("x_scaleY")/100.0f;
	}
	void UEImageBox::render(Graphics2D *g){
		UEComponent::render(g);
		if (imageSrc != NULL) {
			g->pushTransform();
			int iw = imageSrc->getWidth();
			int ih = imageSrc->getHeight();
			g->translate(getWidth()/2, getHeigh()/2);
			g->rotate(x_rotate);
			g->scale(x_scaleX, x_scaleY);
			g->drawImage(imageSrc, -iw/2, -ih/2);
			g->popTransform();
		}
	}


	
	UELabel::UELabel(){
		
	}
	void UELabel::onRead(UIEdit* edit, XMLNode* e){
		UEComponent::onRead(edit, e);
		text = e->getAttribute("text");
		textColor.setARGB(e->getAttributeAsHexU32("textColor"));
		text_anchor = e->getAttribute("text_anchor");
		
// 		this->cclable = CCLabelTTF::labelWithString(
// 			text.c_str(),
// 			CCSize(getWidth(), getHeigh()), 
// 			CCTextAlignmentCenter, 
// 			"song", 16);
		this->cclable = CCLabelTTF::labelWithString(
			text.c_str(),
			CCSize(getWidth(), getHeigh()), 
			CCTextAlignmentCenter, 
			"song", 12);
		this->cclable->getTexture()->setAliasTexParameters();
		this->cclable->setScaleY(-1);
		this->cclable->setPosition(ccp(getWidth()/2, getHeigh()/2));
		this->addChild(cclable);
	}

	UECanvas::UECanvas(){}
	void UECanvas::onRead(UIEdit* edit, XMLNode* e){
		UEComponent::onRead(edit, e);
	}

	UETextInput::UETextInput(){}
	void UETextInput::onRead(UIEdit* edit, XMLNode* e){
		UEComponent::onRead(edit, e);
		textColor.setARGB(e->getAttributeAsHexU32("textColor"));
		isPassword	= e->getAttributeAsBool("isPassword");	//# 是否显示为密码
		is_readonly = e->getAttributeAsBool("is_readonly");	//# 是否只读，不能被编辑
		this->cclable = CCLabelTTF::labelWithString(
			text.c_str(),
			CCSize(getWidth(), getHeigh()), 
			CCTextAlignmentLeft, 
			"song", 12);
		this->cclable->getTexture()->setAliasTexParameters();
		this->cclable->setScaleY(-1);
		this->cclable->setPosition(ccp(0, getHeigh()/2));
		this->addChild(cclable);
	}




	UETextBox::UETextBox(){}
	void UETextBox::onRead(UIEdit* edit, XMLNode* e){
		UEComponent::onRead(edit, e);
		string Text = e->getAttribute("Text");
		textColor.setARGB(e->getAttributeAsHexU32("textColor"));
		this->cclable = CCLabelTTF::labelWithString(
			Text.c_str(),
			CCSize(
				getWidth()-mLayerRect.clipSize*2, 
				getHeigh()-mLayerRect.clipSize*2), 
			CCTextAlignmentLeft, 
			"song", 12);
 		this->cclable->setColor(ccc3(
 			textColor.R*255, 
 			textColor.G*255, 
 			textColor.B*255));
		this->cclable->setColor(ccc3(0xff, 0x00, 0x00));
		this->cclable->getTexture()->setAliasTexParameters();
		this->cclable->setScaleY(-1);
		this->cclable->setPosition(ccp(getWidth()/2, getHeigh()/2));
		this->addChild(cclable);
	}

	//////////////////////////////////////////////////////////////////////////
	
// 	UEComponent* UIEdit::getUEComponent(XMLNode* e)
// 	{
// 		UEComponent* comp = (UEComponent*)createBaseComponent(e);
// 		if (comp != NULL) 
// 		{
// 			comp->onRead(this, e);
// 		}
// 		return comp;
// 	}

	UIEdit::UIEdit()
	{

	}
	UIEdit::UIEdit(string const &res_root)
	{
		this->res_root = res_root;
	}

	void UIEdit::setRoot(string const &res_root)
	{
		this->res_root = res_root;
	}

	CCNode* UIEdit::createFromFile(const string &fileName)
	{
		string path(res_root);
		path.append("/").append(fileName);
		XMLNode* node = XMLNode::parseFromFile(path);
		if (node != NULL) {
			CCNode* ret = createFromXML(node);
			delete node;
			return ret;
		}
		return NULL;
	}

	CCNode* UIEdit::createFromXML(XMLNode* node)
	{
		if (node->childCount() > 0) {
			XMLIterator root = node->childBegin();
			if (root != node->childEnd()) {
				CCNode * ret = readInternal(node);
				return ret;
			}
		}
		return NULL;
	}

	bool UIEdit::getLayout(XMLNode* rect, UILayerRect& out_layout)
	{
		UILayerRect layout;
		ImageStyle style = getUILayerStyle(rect->getAttribute("style"));
		layout.style = style;
		if (rect->isAttribute("img")) {
			layout.clipSize = rect->getAttributeAsInt("clip");
			layout.image    = getUIResImage(rect->getAttribute("img"));
			layout.imageName = rect->getAttribute("img");
		}
		layout.back_color.setARGB(rect->getAttributeAsHexU32("bgc"));
		layout.border_color.setARGB(rect->getAttributeAsHexU32("bdc"));
		out_layout = layout;
		return true;
	}

// 	bool UIEdit::getLayout(XMLNode* rect, UILayerValue& out_layout)
// 	{
// 		UILayerValue layout;
// 		if (rect->isAttribute("img")) {
// 			layout.setImgName(rect->getAttribute("img"));
// 		}
// 		out_layout = layout;
// 		return true;
// 	}

	IImage* UIEdit::getUIResImage(string const &name)
	{
		string path(res_root);
		path.append("/res/").append(name);
		IImage* ret = IImage::createFromFile(path);
		return ret;
	}

	UILayerStyle UIEdit::getUILayerStyle(string const& name)
	{
		if (stringEquals("NULL", name)) {
			return UILAYER_STYLE_NULL;
		}
		if (stringEquals("COLOR", name)) {
			return UILAYER_STYLE_COLOR;
		}
		if (stringEquals("IMAGE_STYLE_ALL_9", name)) {
			return UILAYER_STYLE_IMAGE_ALL_9;
		}
		if (stringEquals("IMAGE_STYLE_ALL_8", name)) {
			return UILAYER_STYLE_IMAGE_ALL_8;
		}
		if (stringEquals("IMAGE_STYLE_H_012", name)) {
			return UILAYER_STYLE_IMAGE_H_012;
		}
		if (stringEquals("IMAGE_STYLE_V_036", name)) {
			return UILAYER_STYLE_IMAGE_V_036;
		}
		if (stringEquals("IMAGE_STYLE_HLM", name)) {
			return UILAYER_STYLE_IMAGE_HLM;
		}
		if (stringEquals("IMAGE_STYLE_VTM", name)) {
			return UILAYER_STYLE_IMAGE_VTM;
		}
		if (stringEquals("IMAGE_STYLE_BACK_4", name)) {
			return UILAYER_STYLE_IMAGE_BACK_4;
		}
		if (stringEquals("IMAGE_STYLE_BACK_4_CENTER", name)) {
			return UILAYER_STYLE_IMAGE_BACK_4_CENTER;
		}
		return UILAYER_STYLE_NULL;
	}


	CCNode* UIEdit::createComponent(XMLNode* e)
	{
		string name = e->getName();

		UEComponent* ret = NULL;
		if (stringEquals(name, UERoot_ClassName)) {
			ret = new UERoot();
		}
		else if (stringEquals(name, UEButton_ClassName)) {
			ret = new UEButton();
		}
		else if (stringEquals(name, UEToggleButton_ClassName)) {
			ret = new UEToggleButton();
		}
		else if (stringEquals(name, UEImageBox_ClassName)) {
			ret = new UEImageBox();
		}
		else if (stringEquals(name, UELabel_ClassName)) {
			ret = new UELabel();
		}
		else if (stringEquals(name, UECanvas_ClassName)) {			
			ret = new UECanvas();
		}
		else if (stringEquals(name, UETextInput_ClassName)) {
			ret = new UETextInput();
		}
		else if (stringEquals(name, UETextBox_ClassName)) {
			ret = new UETextBox();
		}
		if (ret != NULL) {
			ret->autorelease();
		}
		return ret;

	}


	CCNode* UIEdit::readInternal(XMLNode* e)
	{
		CCNode* comp = createComponent(e);
		UEComponent* ui = dynamic_cast<UEComponent*>(comp);
		if (ui != NULL) 
		{
			ui->onRead(this, e);

			XMLNode* childs = e->findChild("childs");

			if (childs != NULL)
			{
				for (XMLIterator it=childs->childBegin(); it!=childs->childEnd(); it++) 
				{
					XMLNode* child = *it;
					CCNode* ccomp = readInternal(child);
					UEComponent* cui = dynamic_cast<UEComponent*>(ccomp);
					if (cui != NULL) {
						ui->addChild(cui);
					}
				}
			}
		}
		return comp;
	}


// 	CCNode* UIEdit::createComponent(XMLNode* e)
// 	{
// 		string name = e->getName();
// 
// 		UEComponent* ret = NULL;
// 		if (stringEquals(name, UERoot_ClassName)) {
// 			ret = new UERoot();
// 		}
// 		else if (stringEquals(name, UEButton_ClassName)) {
// 			UEComponent* ui = new UEButton();
// 			ui->onRead(this, e);
// 			ret = (UEComponent*)createButton(ui);
// 		}
// 		else if (stringEquals(name, UEToggleButton_ClassName)) {
// 			ret = new UEToggleButton();
// 		}
// 		else if (stringEquals(name, UEImageBox_ClassName)) {
// 			UEComponent* ui = new UEImageBox();
// 			ui->onRead(this, e);
// 			ret = (UEComponent*)createImageBox(ui);
// 		}
// 		else if (stringEquals(name, UELabel_ClassName)) {
// 			UEComponent* ui = new UELabel();
// 			ui->onRead(this, e);
// 			ret = (UEComponent*)createLabel(ui);
// 		}
// 		else if (stringEquals(name, UECanvas_ClassName)) {
// 			UEComponent* ui = new UECanvas();
// 			ui->onRead(this, e);
// 			ret = (UEComponent*)createCanvas(ui,e->findChild("childs"));
// 		}
// 		else if (stringEquals(name, UETextInput_ClassName)) {
// 			ret = new UETextInput();
// 		}
// 		else if (stringEquals(name, UETextBox_ClassName)) {
// 			ret = new UETextBox();
// 		}
// 		//if (ret != NULL) {
// 		//	ret->autorelease();
// 		//}
// 		return ret;
// 	}
// 
// 	CCNode* UIEdit::createCanvas(UEComponent* ui,XMLNode* child)
// 	{
// 		return new UECanvas();
// 	}
// 
// 	CCNode* UIEdit::createButton(UEComponent* ui)
// 	{
// 		return new UEButton();
// 	}
// 
// 	CCNode* UIEdit::createImageBox(UEComponent* ui)
// 	{
// 		return new UEImageBox();
// 	}
// 
// 	CCNode* UIEdit::createLabel(UEComponent* ui)
// 	{
// 		return new UELabel();
// 	}



}; // namespace mf
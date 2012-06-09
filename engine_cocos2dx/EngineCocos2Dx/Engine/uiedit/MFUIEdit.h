
#ifndef _MF_UI_EDIT
#define _MF_UI_EDIT

#include "MFUIBase.h"
#include "MFUIText.h"

USING_NS_CC;

namespace mf_uiedit
{
	using namespace std;
	using namespace cocos2d;
	using namespace mf;

	class CC_DLL UIEdit;

	class CC_DLL UEComponent : public UICompoment
	{
		friend class UIEdit;
	protected:
		string name;		//#名字
		string euser_data;
		int    euser_tag;
	//	UILayerValue layerValue;
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
	public:
		UEComponent();
		string	getName();
		string	getEditUserData();
		int		getEditUserTag();
		//XMLNode* getChild(XMLNode* e,string const &childName);
		//UILayerValue getLayerValue();

		// 根据子节点名字查找子节点
		// @childName 节点名字
		// @recursion 是否递归
		UEComponent* findChild(string const &childName, bool recursion);
	};

//	###############################################################
//	### 根节点
//	###############################################################
	class CC_DLL UERoot : public UEComponent
	{
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
	public:
		UERoot();
	};
//	###############################################################
//	### 普通按钮
//	###############################################################
	class CC_DLL UEButton : public UEComponent
	{
	protected:
		Color		textColor;		//# 文本颜色
		string		text;			//# 文本
		string		text_anchor;	//# 文本 对齐方式   
		int			text_offset_x;	//# 文本 绘制偏移坐标
		int			text_offset_y;	//# 文本 绘制偏移坐标
		UILayerRect	layout_down;	//# 按下时的 样式
		//UILayerValue layerValue_down;	//# 按下时的 样式
		bool		touchPressed;
		CCLabelTTF* cclable;
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
		virtual void onTouchBegan(TouchEvent const *cp);
		virtual void onTouchMoved(TouchEvent const *cp);
		virtual void onTouchEnded(TouchEvent const *cp);
		virtual void render(Graphics2D *g);
	public:
		UEButton();
		string getText();
		//UILayerValue getlayerValueDown();
	};
	


// ###############################################################
// ### 状态切换按钮
// ###############################################################
	class CC_DLL UEToggleButton : public UEButton
	{
	protected:
		bool		isChecked;		//# 是否为按下状态
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
		virtual void onTouchBegan(TouchEvent const *cp);
		virtual void onTouchMoved(TouchEvent const *cp);
		virtual void onTouchEnded(TouchEvent const *cp);
		virtual void render(Graphics2D *g);
	public:
		UEToggleButton();
	};

//	###############################################################
//	### 图片框
//	###############################################################
	class CC_DLL UEImageBox : public UEComponent
	{
	protected:
		IImage*		imageSrc;
		string		imagePath;		//# 图片地址
		float		x_rotate;		//# 图片旋转角度0～360
		float		x_scaleX;		//# 图片拉伸X 0～100%
		float		x_scaleY;		//# 图片拉伸Y 0～100%
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
		virtual void render(Graphics2D *g);
	public:
		UEImageBox();
	};
		

		

// 	###############################################################
// 	### 文本标签
// 	###############################################################
	class CC_DLL UELabel : public UEComponent
	{
	protected:
		string		text;			//# 文本
		Color		textColor;		//# 文本颜色
		string		text_anchor;	//# 文本对齐方式
		CCLabelTTF* cclable;
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
	public:
		UELabel();
	};
		
		



// 	###############################################################
// 	### 普通容器
// 	###############################################################
	class CC_DLL UECanvas : public UEComponent
	{
	protected:

	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
	public:
		UECanvas();
	};



// 	###############################################################
// 	### 单行文本输入框
// 	###############################################################
	class CC_DLL UETextInput : public UEComponent
	{
	protected:
		string text;
		bool isPassword;	//# 是否显示为密码
		bool is_readonly;	//# 是否只读，不能被编辑
		Color textColor;	//# 文本颜色
		CCLabelTTF* cclable;
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
	public:
		UETextInput();

	};
		


// 	###############################################################
// 	### 多行文本框，不能编辑
// 	###############################################################
	class CC_DLL UETextBox : public UEComponent
	{
	protected:
		Color textColor;			//# 文本颜色
		float text_shadow_alpha;	//# 文本阴影alpha
		Color text_shadow_color;	//# 文本阴影颜色
		float text_shadow_x;		//# 文本阴影偏移坐标X
		float text_shadow_y;		//# 文本阴影偏移坐标Y
		CCLabelTTF* cclable;
	protected:
		virtual void onRead(UIEdit* edit, XMLNode* e);
	public:
		UETextBox();
	};

	
	class CC_DLL UIEdit
	{
	private:
		string res_root;

	public:

		UIEdit();
		UIEdit(string const &res_root);
		void setRoot(string const &res_root);

		virtual CCNode*			createFromFile(const string &fileName);

		virtual CCNode*			createFromXML(XMLNode* node);

		///////////////////////////////////////////////////////////////////////

		virtual IImage*			getUIResImage(string const &name);

		virtual UILayerStyle	getUILayerStyle(string const& name);

		virtual bool			getLayout(XMLNode* rect, UILayerRect& layout);

		// 君王II特性相关代码全部转移到君王II项目的子类实现
		// 此处修改导致其他同样用该库项目运行不正常
		//virtual bool			getLayout(XMLNode* rect, UILayerValue& layout);		

	protected:
		virtual CCNode*			createComponent(XMLNode* e);
		virtual CCNode*			readInternal(XMLNode* e);

//		virtual CCNode*			createBaseComponent(XMLNode* e);
// 		virtual CCNode*			createCanvas(UEComponent* ui,XMLNode* child);
// 		virtual CCNode*			createButton(UEComponent* ui);
// 		virtual CCNode*			createImageBox(UEComponent* ui);
// 		virtual CCNode*			createLabel(UEComponent* ui);
// 		
// 		virtual UEComponent*	getUEComponent(XMLNode* e);
	};



}; // namespace mf


#endif // #define _MF_UI_BASE

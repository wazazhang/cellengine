#include "HelloWorldScene.h"
#include "MyNode.h"
#include "TouchTestNode.h"
#include "SceneTestNode.h"
#include "UITestNode.h"
#include "Resource.h"

using namespace cocos2d;

CCScene* HelloWorld::scene()
{
    CCScene * scene = NULL;
    do 
    {
        // 'scene' is an autorelease object
        scene = CCScene::node();
        CC_BREAK_IF(! scene);

        // 'layer' is an autorelease object
        HelloWorld *layer = HelloWorld::node();
        CC_BREAK_IF(! layer);

        // add layer as a child to scene
        scene->addChild(layer);
    } while (0);

    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool HelloWorld::init()
{
	cout.imbue(locale("chs"));
	wcout.imbue(locale("chs"));

	CCSize size = CCDirector::sharedDirector()->getWinSize();

	//setPosition(0, size.height);
	setScaleY(-1);

	MyResourceManager::getInstance();

    bool bRet = false;
    do 
    {
        //////////////////////////////////////////////////////////////////////////
        // super init first
        //////////////////////////////////////////////////////////////////////////

        CC_BREAK_IF(! CCLayer::init());

		
        //////////////////////////////////////////////////////////////////////////
        // add your codes below...
        //////////////////////////////////////////////////////////////////////////

        // 1. Add a menu item with "X" image, which is clicked to quit the program.

        // Create a "close" menu item with close icon, it's an auto release object.
        CCMenuItemImage *pCloseItem = CCMenuItemImage::itemFromNormalImage(
            "CloseNormal.png",
            "CloseSelected.png",
            this,
            menu_selector(HelloWorld::menuCloseCallback));
        CC_BREAK_IF(! pCloseItem);

        // Place the menu item bottom-right conner.
        pCloseItem->setPosition(ccp(size.width - 20, 20));

        // Create a menu with the "close" menu item, it's an auto release object.
        CCMenu* pMenu = CCMenu::menuWithItems(pCloseItem, NULL);
        pMenu->setPosition(CCPointZero);
        CC_BREAK_IF(! pMenu);

        // Add the menu to HelloWorld layer as a child layer.
        this->addChild(pMenu, 1);


#if 0
			// 2. Add a label shows "Hello World".
			
			// Create a label and initialize with string "Hello World".
			CCLabelTTF* pLabel = CCLabelTTF::labelWithString("Hello World", "Thonburi", 64);
			CC_BREAK_IF(! pLabel);

			// Get window size and place the label upper. 
			pLabel->setPosition(ccp(size.width / 2, size.height - 20));

			// Add the label to HelloWorld layer as a child layer.
			this->addChild(pLabel, 1);
#endif
			
#if 0
			// 3. Add add a splash screen, show the cocos2d splash image.
			CCSprite* pSprite = CCSprite::spriteWithFile("HelloWorld.png");
			CC_BREAK_IF(! pSprite);

			// Place the sprite on the center of the screen
			pSprite->setPosition(ccp(size.width/2, size.height/2));

			// Add the sprite to HelloWorld layer as a child layer.
			this->addChild(pSprite, 0);
#endif

			//MyNode* md = new MyNode();
			//CCNode* md = new SceneTestNode();
			//CCNode* md = new UITestNode();
			//CCNode* md = new TouchTestNode();
			//this->addChild(md, 0);
	


        bRet = true;
    } while (0);

    return bRet;
}

void HelloWorld::menuCloseCallback(CCObject* pSender)
{
    // "close" menu item clicked
    CCDirector::sharedDirector()->end();
}


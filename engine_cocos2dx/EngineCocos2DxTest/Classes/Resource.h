
#ifndef _MY_RESOURCE_MANAGER_H
#define _MY_RESOURCE_MANAGER_H

#include "cocos2d.h"
#include "MFEngine.h"

using namespace mf;
using namespace cocos2d;

class MyResourceManager : public ResourceManager
{
private:
	static MyResourceManager* instance;
	MyResourceManager();
public:
	static MyResourceManager* getInstance();

};

class MyUILayerRectManager : public UILayerRectManager
{
public:
	virtual UILayerRect* getDefaultRect(UICompoment* comp);

};

#endif // _MY_RESOURCE_MANAGER_H

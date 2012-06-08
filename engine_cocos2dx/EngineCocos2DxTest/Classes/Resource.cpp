
#include "Resource.h"

void initEmbendResource();

MyResourceManager* MyResourceManager::instance = NULL;
MyResourceManager::MyResourceManager(){}
MyResourceManager* MyResourceManager::getInstance()
{
	if (MyResourceManager::instance == NULL) 
	{
		MyResourceManager::instance = new MyResourceManager();

		initEmbendResource();
	}
	return MyResourceManager::instance;
}


/////////////////////////////////////////////////////////////////////////////////////////////

// ¶ÁÈëUIRECT
UILayerRect* loadUILayerRect(string const &key, string const &path, UILayerStyle style, u16 clipSize)
{
	IImage* img = IImage::createFromFile(path);
	if (img != NULL) {
		UILayerRect* rect = UILayerRect::createWithImage(
			img, UILAYER_STYLE_IMAGE_ALL_9, clipSize);
		UILayerRectManager::getInstance()->addRect(
			key, rect);
		return rect;
	}
	return NULL;
}

/////////////////////////////////////////////////////////////////////////////////////////////

UILayerRect* MyUILayerRectManager::getDefaultRect(UICompoment* comp)
{
	return UILayerRectManager::getDefaultRect(comp);
}

void initEmbendResource()
{
	// init ui layer rects
	new MyUILayerRectManager();

	loadUILayerRect("default", "pan_soldier_hd.png", UILAYER_STYLE_IMAGE_ALL_9, 10);
}

/////////////////////////////////////////////////////////////////////////////////////////////


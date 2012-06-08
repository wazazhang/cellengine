#include "main.h"

#include "AppDelegate.h"

int APIENTRY _tWinMain(HINSTANCE hInstance,
					   HINSTANCE hPrevInstance,
					   LPTSTR    lpCmdLine,
					   int       nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);

    // create the application instance
    AppDelegate app;

	if(AllocConsole())
	{
		freopen("CONOUT$","w",stdout);
	}

    return cocos2d::CCApplication::sharedApplication().run();
}

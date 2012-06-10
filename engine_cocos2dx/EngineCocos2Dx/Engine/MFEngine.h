#ifndef _MF_ENGINE
#define _MF_ENGINE


//////////////////////////////////////////////////////////////////////////
// CORE
//////////////////////////////////////////////////////////////////////////

#include "MFMath.h"				// 实用数学方法
#include "MFType.h"				// 各种类型定义
#include "MFUtil.h"				// 使用字符串、数组、字符转换方法
#include "MFXMLParser.h"		// 解析XML工具类

#include "MFGraphics2D.h"		// 2D图形显示类，封装了绘制几何图形和材质的方法，
								// 也可以创建RenderToTexture

#include "MFNode.h"

//////////////////////////////////////////////////////////////////////////
// GAME
//////////////////////////////////////////////////////////////////////////
#include "MFGameObject.h"		// 定义基础游戏单位和碰撞快
#include "MFCellResourceXML.h"	// 用于解析xml格式的编辑器资源
#include "MFCellResource.h"		// 编辑器资源管理
#include "MFCellSprite.h"		// 编辑器的精灵显示
#include "MFCellMap.h"			// 编辑器的地图显示	
#include "MFCellWorld.h"		// 编辑器的场景显示

//////////////////////////////////////////////////////////////////////////
// UI
//////////////////////////////////////////////////////////////////////////

#include "MFUIBase.h"			// 基础UI支持
#include "MFUIText.h"			// 文本UI支持
//#include "MFUIEdit.h"			// UI编辑器支持

//////////////////////////////////////////////////////////////////////////

namespace mf
{
// 	class Engine
// 	{
// 	public:
// 
// 		inline static void init() 
// 		{
// 			Graphics2D::setScreenGraphics(new Graphics2D());
// 		}
// 
// 		inline static void destory() 
// 		{
// 			delete Graphics2D::getScreenGraphics();
// 		}
// 	};
};


#endif // _MF_ENGINE
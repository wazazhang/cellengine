//
//  CDisplayObject.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-23.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_DISPLAY_OBJECT
#define _COM_CELL_DISPLAY_OBJECT

#include "CGraphics2D.h"

namespace com_cell
{
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  game screen manager
	//  ----------------------------------------------------------------------------------------------------------------------------//
	// 显示列表基类
    class DisplayObject
	{
	public:
		virtual ~DisplayObject();
		
		
	// abstract methods
	public:
		virtual void added(DisplayObject const &parent) = 0;
		
		virtual void removed(DisplayObject const &parent) = 0;
		
		virtual void render() = 0;
		
		virtual void update() = 0;
	
	// containers
	public:
		
		
	// internal
	private:
		
	};

    
};

#endif // #define _COM_CELL_DISPLAY_OBJECT

//
//  CDisplayObject.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-23.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_DISPLAY_OBJECT
#define _COM_CELL_DISPLAY_OBJECT

#include <vector>
#include <list>
#include <map>
#include <queue>

#include "CGraphics2D.h"
#include "CScreenManager.h"



namespace com_cell
{
	using namespace std;
	
	class DisplayObjectContainer;
	class DisplayObjectSorter;
	class DisplayObjectEvent;
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  DisplayObject
	//  ----------------------------------------------------------------------------------------------------------------------------//
	// 显示列表基类
    class DisplayObject
	{
		friend class DisplayObjectContainer;
		
	// fields
	public:
		
		Vector3D pos;
		
		DisplayObject();
		
		virtual ~DisplayObject(){}
		
	// abstract methods
	protected:
		
		virtual void onAdded(DisplayObject *object) = 0;
		
		virtual void onRemoved(DisplayObject *object) = 0;
		
		virtual void onRender(Graphics2D &g) = 0;
		
		virtual void onUpdate(float interval_ms) = 0;
		
	// internal
	private:
		
		virtual void call_added(DisplayObject *object);
		
		virtual void call_removed(DisplayObject *object);
		
		virtual void call_update(DisplayObjectContainer* parent, float interval_ms);
		
		virtual void call_render(DisplayObjectContainer* parent, Graphics2D &g);
		
	// method
	public:
		
		inline DisplayObjectContainer* getParent() {
			return m_parent;
		}
		
		inline ScreenManager* getRoot() {
			return m_root;
		}
		
		inline IScreen* getScreen() {
			return m_screen;
		}
		
		virtual bool removeFromParent();
		
	// internal
	private:
		
		DisplayObjectContainer*		m_parent;
		ScreenManager*				m_root;
		IScreen*					m_screen;

	};
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  DisplayObjectContainer
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	
	class DisplayObjectContainer : public DisplayObject
	{
		
	// abstract methods
	public:
		
		DisplayObjectContainer();
		
		virtual ~DisplayObjectContainer(){}
		
//		virtual void onAdded(DisplayObject *object) = 0;
//		
//		virtual void onRemoved(DisplayObject *object) = 0;
//		
//		virtual void onRender(Graphics2D &g) = 0;
		
		virtual void onBeginRender(Graphics2D &g) = 0;
		
//		virtual void onUpdate(float interval_ms) = 0;
		
	private:
		
		virtual void call_added(DisplayObject *object);
		
		virtual void call_removed(DisplayObject *object);
		
		virtual void call_update(DisplayObjectContainer* parent, float interval_ms);
		
		virtual void call_render(DisplayObjectContainer* parent, Graphics2D &g);

	// containers
	public:
		
		void					sort();
		
		void					sort(DisplayObjectSorter* sorter);
		
		bool					addChild(DisplayObject* child);
		
		bool					removeChild(DisplayObject* child);

		DisplayObject*			getChild(int i);
		
		int						childIndex(DisplayObject* child);
		
		int						clearChilds();
		
		void					swarpChild(DisplayObject* a, DisplayObject* b);
		
		void					swarpChild(int ia, int ib);
		
		bool					contains(DisplayObject* child);
		
		int						getChildCount();
	
		vector<DisplayObject*>	getChilds();
		
	private:
		
		bool						update_childs;
		
		DisplayObjectSorter*		sorter;
		
		// 显示列表
		vector<DisplayObject*>		childs_display;
		
		// 当前实时集合
		vector<DisplayObject*>		childs_set;
		
	private:
		
		void updateDisplayList();
		
		vector<DisplayObject*>::iterator find(DisplayObject* o);

	};
	
	
	
	class DisplayObjectSorter 
	{
	public:
		
		virtual ~DisplayObjectSorter(){}
		
		virtual bool operator() (DisplayObject* a, DisplayObject* b)
		{
			return (a->pos.z > b->pos.z);
		}
	};
	

	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  Sprite
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	class Sprite
	{
		

		
	};

	
};

#endif // #define _COM_CELL_DISPLAY_OBJECT

//
//  CDisplayObject.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-23.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CDisplayObject.h"
#include <iostream>
#include <algorithm>


namespace com_cell
{
	using namespace std;
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  DisplayObject
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	DisplayObject::DisplayObject()
	{
		m_parent	= NULL;
		m_root		= NULL;
		m_screen	= NULL;
		pos.x		= 0;
		pos.y		= 0;
		pos.z		= 0;
	}
	
	bool DisplayObject::removeFromParent()
	{
		if (m_parent != NULL) {
			return m_parent->removeChild(this);
		}
		return false;
	}
	
	void DisplayObject::call_added(DisplayObject *object)
	{
		onAdded(object);
	}
	
	void DisplayObject::call_removed(DisplayObject *object)
	{
		onRemoved(object);
	}
	
	void DisplayObject::call_update(DisplayObjectContainer* parent, float interval_ms)
	{
		m_parent 	= parent;
		m_root 		= parent->m_root;
		m_screen	= parent->m_screen;
		onUpdate(interval_ms);
	}
	
	void DisplayObject::call_render(DisplayObjectContainer* parent, Graphics2D &g)
	{
		g.pushTransform();
		g.translate(pos.x, pos.y);
		onRender(g);
		g.popTransform();
	}
	

	
	
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  DisplayObjectContainer
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	DisplayObjectContainer::DisplayObjectContainer()
	{
		sorter = NULL;
		update_childs = true;
	}
	
	
	void DisplayObjectContainer::call_added(DisplayObject *object)
	{
		vector<DisplayObject*>::reverse_iterator it;
		for (it = childs_display.rbegin(); it!=childs_display.rend(); --it) {
			DisplayObject* o = (*it);
			o->call_added(object);
		}
		onAdded(object);
	}
	
	void DisplayObjectContainer::call_removed(DisplayObject *object)
	{
		vector<DisplayObject*>::reverse_iterator it;
		for (it = childs_display.rbegin(); it!=childs_display.rend(); --it) {
			DisplayObject* o = (*it);
			o->call_removed(object);
		}
		onRemoved(object);
	}
	
	void DisplayObjectContainer::call_update(DisplayObjectContainer* parent, float interval_ms)
	{
		m_parent 	= parent;
		m_root 		= parent->m_root;
		m_screen	= parent->m_screen;
		updateDisplayList();
		vector<DisplayObject*>::reverse_iterator it;
		for (it = childs_display.rbegin(); it!=childs_display.rend(); --it) {
			DisplayObject* o = (*it);
			o->call_update(this, interval_ms);
		}
		onUpdate(interval_ms);
	}
	
	void DisplayObjectContainer::call_render(DisplayObjectContainer* parent, Graphics2D &g)
	{
		g.pushTransform();
		g.translate(pos.x, pos.y);
		onBeginRender(g);
		vector<DisplayObject*>::iterator it;
		for (it = childs_display.begin(); it!=childs_display.end(); ++it) {
			DisplayObject* o = (*it);
			o->call_render(this, g);
		}
		onRender(g);
		g.popTransform();
	}
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	void DisplayObjectContainer::updateDisplayList()
	{
		if (update_childs) {
			childs_display = childs_set;
			update_childs = false;
		}
	}
	
	vector<DisplayObject*>::iterator DisplayObjectContainer::find(DisplayObject* o)
	{
		vector<DisplayObject*>::iterator it;
		for (it = childs_set.begin(); it!=childs_set.end(); ++it) {
			if (o == (*it)) {
				return it;
			}
		}
		return childs_set.end();
	}
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	

	void DisplayObjectContainer::sort()
	{
		if (sorter != NULL) {
			std::sort(childs_set.begin(), childs_set.end(), *sorter);
			update_childs = true;
		}
	}
	
	void DisplayObjectContainer::sort(DisplayObjectSorter* cmp)
	{
		sorter = cmp;
		if (sorter != NULL) {
			std::sort(childs_set.begin(), childs_set.end(), *sorter);
		}
	}

	bool DisplayObjectContainer::addChild(DisplayObject* child)
	{
		
	}
	
	bool DisplayObjectContainer::removeChild(DisplayObject* child)
	{
		
	}
	
	DisplayObject* DisplayObjectContainer::getChild(int i)
	{
		
	}
	
	int	DisplayObjectContainer::childIndex(DisplayObject* child)
	{
		
	}
	
	int	DisplayObjectContainer::clearChilds()
	{
		
	}
	
	bool DisplayObjectContainer::contains(DisplayObject* child)
	{
		
	}
	
	int DisplayObjectContainer::getChildCount()
	{
		
	}
		
	vector<DisplayObject*>	DisplayObjectContainer::getChilds()
	{
		
	}

		
};
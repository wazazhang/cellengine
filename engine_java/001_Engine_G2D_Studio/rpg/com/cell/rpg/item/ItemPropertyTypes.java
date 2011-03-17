package com.cell.rpg.item;

import java.util.ArrayList;
import java.util.HashMap;

import com.cell.rpg.item.anno.ItemType;
import com.g2d.annotation.Property;

public class ItemPropertyTypes 
{
	
//	-------------------------------------------------------------------------------------
	
	private static HashMap<Integer, Class<?>> 
								item_property_types_map = new HashMap<Integer, Class<?>>();
	
	private static Class<?>[]	item_property_types;
	private static Class<?>[]	item_property_types_list;
	
	private static ItemPropertyManager item_property_manager;
	
	public static ItemPropertyManager getItemPropertyManager(){
		return item_property_manager;
	}
	
	
	public static void setItemPropertyTypes(ItemPropertyManager manager) throws Exception
	{
		item_property_manager = manager;
		
		for (Class<?> type : manager.getAllTypes()) {						
			ItemType itype = type.getAnnotation(ItemType.class);
			if (itype!=null) {
				if (ItemPropertyTemplate.class.isAssignableFrom(type)) {
					if (item_property_types_map.containsKey(itype.value())) {
						throw new Exception(type.getName() + " [" + itype.value() + "] is already exist !");
					} else {
						item_property_types_map.put(itype.value(), type);
//						System.out.println("add item property : " + type + " : " + type.getClassLoader());
					}
				}
			}
		}
		item_property_types = item_property_types_map.values().toArray(new Class<?>[item_property_types_map.size()]);
		
		ArrayList<Class<?>> show_types = new ArrayList<Class<?>>();
		for (Class<?> type : item_property_types) {
			Property itype = type.getAnnotation(Property.class);
			if (itype != null) {
				show_types.add(type);
			}
		}
		item_property_types_list = show_types.toArray(new Class<?>[show_types.size()]);
	}

	
	public static Class<?> getItemPropertyType(int type) {
		return item_property_types_map.get(type);
	}
	
	
	/**
	 * 所有的类
	 * @return
	 */
	public static Class<?>[] getAllItemPropertyTypes() {
		return item_property_types;
	}
	
	/**
	 * 在编辑器中显示的类
	 * @return
	 */
	public static Class<?>[] getItemPropertyTypesList() {
		return item_property_types_list;
	}
	
//	-------------------------------------------------------------------------------------
}

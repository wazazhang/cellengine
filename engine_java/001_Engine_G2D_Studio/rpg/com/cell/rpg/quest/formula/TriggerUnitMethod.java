package com.cell.rpg.quest.formula;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import com.cell.rpg.formula.ObjectMethod;
import com.cell.rpg.quest.TriggerUnitType;
import com.g2d.annotation.Property;

@Property("单位-函数")
public class TriggerUnitMethod extends ObjectMethod
{
	@Property("单位类型")
	public TriggerUnitType trigger_unit_type = TriggerUnitType.PLAYER;
	
	@Override
	public String toString() {
		return trigger_unit_type+"."+super.toString();
	}
//	
//	@Override
//	protected Map<String, Method> getMethods() {
//		switch (trigger_unit_type) {
//		case PLAYER:
//		case PLAYER_GROUP:
//			return player_methods;
//		case PET:
//			return pet_methods;
//		case TRIGGERING_NPC:
//			return npc_methods;
//		}
//		return null;
//	}
//	
//	
//	-----------------------------------------------------------------------------------------------------
//	Edit Mode
	
	static private LinkedHashMap<String, Method> player_methods	= new LinkedHashMap<String, Method>();
	static private LinkedHashMap<String, Method> pet_methods 	= new LinkedHashMap<String, Method>();
	static private LinkedHashMap<String, Method> npc_methods 	= new LinkedHashMap<String, Method>();
	
	public static void setTriggerUnitClass(
			Class<?> player_class,
			Class<?> pet_class,
			Class<?> npc_class)
	{
		if (player_class!=null) {
			for (Method method : player_class.getMethods()) {
				if (validateMethod(method)) {
					player_methods.put(method.getName(), method);
//					System.out.println("PLAYER Method - " + method);
				}
			}
		} 
		if (pet_class!=null) {
			for (Method method : pet_class.getMethods()) {
				if (validateMethod(method)) {
					pet_methods.put(method.getName(), method);
//					System.out.println("PET Method - " + method);
				}
			}
		} 
		if (npc_class!=null) {
			for (Method method : npc_class.getMethods()) {
				if (validateMethod(method)) {
					npc_methods.put(method.getName(), method);
//					System.out.println("NPC Method - " + method);
				}
			}
		} 
	}

	public static LinkedHashMap<String, Method> getPlayerMethods() {
		return player_methods;
	}
	public static LinkedHashMap<String, Method> getPetMethods() {
		return pet_methods;
	}
	public static LinkedHashMap<String, Method> getNpcMethods() {
		return npc_methods;
	}


}

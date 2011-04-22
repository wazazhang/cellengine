package com.cell.rpg.ability;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import com.cell.exception.NotImplementedException;
import com.cell.rpg.RPGConfig;

/**
 * 该类无法将新的类型加入到老的版本序列化，<br>
 * 新版本要兼容老版本只能通过反序列化完成后使用setTypes(Class<?> ... sub_types)来达成新类型的加入<br>
 * @author WAZA
 */
@Deprecated()
public class AbilitiesVector implements Abilities, Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**将显示在单位属性的Ability面板*/
	private Vector<AbstractAbility> abilities = new Vector<AbstractAbility>();
	
	transient AbstractAbility[]		static_abilities;

	private Class<?>[] 				sub_types;

	final protected Object writeReplace() throws ObjectStreamException 
	{
		return this;
	}

	final protected Object readResolve() throws ObjectStreamException 
	{
		for (int i = abilities.size() - 1; i >= 0; --i) {
			if (abilities.get(i)==null) {
				abilities.remove(i);
			}
		}
		return this;
	}
	
	@Deprecated()
	public AbilitiesVector(Class<?> ... sub_types) 
	{
		setTypes(sub_types);
	}

	protected void setTypes(Class<?> ... sub_types) 
	{
		this.sub_types = sub_types;
	}
	
	final public void clearAbilities() 
	{
		abilities.clear();
	}

	final public void addAbility(AbstractAbility element) 
	{
		abilities.add(element);
	}

	final public void removeAbility(AbstractAbility element) 
	{
		abilities.remove(element);
	}
	
	@Override
	final public int moveAbility(int index, int offset) throws NotImplementedException 
	{
		int total = abilities.size();
		
		if ( (0<=index) && (index<total) ) 
		{
			if (offset == 0)
				return 0;
			
			if (offset > 0) // 向前
			{
				int new_index = index + offset + 1;
				
				if (new_index > total)
					return -2;
				
				AbstractAbility ability = (AbstractAbility)abilities.get(index);
				abilities.add(new_index, ability);
				abilities.remove(index);
				
				return 1;				
			}
			else // 向后
			{
				int new_index = index + offset;
				
				if (new_index < 0)
					return -2;
				
				AbstractAbility ability = (AbstractAbility)abilities.get(index);
				abilities.add(new_index, ability);
				abilities.remove(index+1);
				
				return 1;
			}
		}
		
		return -1;
	}	
	
	final public AbstractAbility[] getAbilities() 
	{
		if (!RPGConfig.IS_EDIT_MODE) {
			if (static_abilities == null) {
				static_abilities = abilities.toArray(new AbstractAbility[abilities.size()]);
			}
			return static_abilities;
		}
		AbstractAbility[] abilities_data = abilities.toArray(new AbstractAbility[abilities.size()]);

		return abilities_data;
	}

	@SuppressWarnings("unchecked")
	final public <T> T getAbility(Class<T> type) 
	{
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				return (T) a;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	final public <T> ArrayList<T> getAbilities(Class<T> type) 
	{
		ArrayList<T> ret = new ArrayList<T>(abilities.size());
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				ret.add((T)a);
			}
		}
		return ret;
	}
	
	final public int getAbilitiesCount() 
	{
		return abilities.size();
	}
	
	@Override
	final public Class<?>[] getSubAbilityTypes() 
	{
		return sub_types;
	}
	
	@Override
	public String toString() 
	{
		StringBuffer buffer = new StringBuffer(getAbilitiesCount() + "个单位 : ");
		for (AbstractAbility a : abilities) {
			buffer.append(a.toString() + ";");
		}
		return buffer.toString();
	}
	
	
	
	
}

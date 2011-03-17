package com.cell.rpg.ability;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.cell.rpg.RPGConfig;

/**
 * 一组能力的集合，如果子类实现该接口，则在
 * {@link AbilityPanel}里就可以添加集合成员
 * @author WAZA
 * @see AbilityPanel
 */
public abstract class AbilitiesList implements Abilities, Serializable
{
	private static final long serialVersionUID = 1L;

	/**将显示在单位属性的Ability面板*/
	protected ArrayList<AbstractAbility>	abilities = new ArrayList<AbstractAbility>();

	transient AbstractAbility[]			static_abilities;
	
	public AbilitiesList() {}

	final protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	final protected Object readResolve() throws ObjectStreamException {
		for (int i = abilities.size() - 1; i >= 0; --i) {
			if (abilities.get(i)==null) {
				abilities.remove(i);
			}
		}
		return this;
	}
	
	final public void clearAbilities() {
		abilities.clear();
	}
	
	final public void addAbility(AbstractAbility element) {
		abilities.add(element);
	}
	
	final public void removeAbility(AbstractAbility element) {
		abilities.remove(element);
	}
	
	final public AbstractAbility[] getAbilities() {
		if (!RPGConfig.IS_EDIT_MODE) {
			if (static_abilities == null) {
				static_abilities = abilities.toArray(new AbstractAbility[abilities.size()]);
			}
			return static_abilities;
		}
		AbstractAbility[] abilities_data = abilities.toArray(new AbstractAbility[abilities.size()]);
		Arrays.sort(abilities_data);
		return abilities_data;
	}

	final public <T> T getAbility(Class<T> type) {
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				return type.cast(a);
			}
		}
		return null;
	}
	
	final public <T> ArrayList<T> getAbilities(Class<T> type) {
		ArrayList<T> ret = new ArrayList<T>(abilities.size());
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				ret.add(type.cast(a));
			}
		}
		return ret;
	}
	
	final public int getAbilitiesCount() {
		return abilities.size();
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (AbstractAbility a : abilities) {
			buffer.append(a.toString() + ";");
		}
		return buffer.toString();
	}
	
	
	
}

package com.cell.rpg.ability;

import java.util.ArrayList;

/**
 * 一组能力的集合，如果子类实现该接口，则在
 * {@link AbilityPanel}里就可以添加集合成员
 * @author WAZA
 * @see AbilityPanel
 */
public interface Abilities
{
	public void clearAbilities();
	
	public void addAbility(AbstractAbility element);

    public void removeAbility(AbstractAbility element);

    public AbstractAbility[] getAbilities();
	
	public <T> T getAbility(Class<T> type);

	public <T> ArrayList<T> getAbilities(Class<T> type) ;
	
	public Class<?>[] getSubAbilityTypes();
	
	public int getAbilitiesCount();
}

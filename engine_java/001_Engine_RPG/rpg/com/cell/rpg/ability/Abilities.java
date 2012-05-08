package com.cell.rpg.ability;

import java.util.ArrayList;

import com.cell.exception.NotImplementedException;
import com.cell.net.io.MutualMessage;

/**
 * 一组能力的集合，如果子类实现该接口，则在
 * {@link AbilityPanel}里就可以添加集合成员
 * @author WAZA
 * @see AbilityPanel
 */
public interface Abilities extends MutualMessage
{
	public void clearAbilities();
	
	public void addAbility(AbstractAbility element);

    public void removeAbility(AbstractAbility element);
    
    /**
     * 将第几个位置的能力移动位置
     * @param index 第几个位置
     * @param offset 正数向前移动，负数往后移动
     * @return 成功返回>=0, 失败返回负数
     * @throws NotImplementedException
     */
    public int moveAbility(int index, int offset) throws NotImplementedException;

    public AbstractAbility[] getAbilities();
	
	public <T> T getAbility(Class<T> type);

	public <T> ArrayList<T> getAbilities(Class<T> type) ;
	
	public Class<?>[] getSubAbilityTypes();
	
	public int getAbilitiesCount();
}

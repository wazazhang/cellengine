package com.cell.rpg.formula.helper;

import com.cell.rpg.formula.AbstractValue;

/**
 * 用于公式计算的适配器
 * @author WAZA
 *
 */
public interface IFormulaAdapter 
{
	public Number getValue(AbstractValue value) throws Throwable;

}

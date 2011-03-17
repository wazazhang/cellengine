package com.cell.rpg.template.ability;

import java.util.HashMap;
import java.util.Vector;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.util.Pair;
import com.g2d.annotation.Property;



public class ItemCountCollection extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;
	
	@Property("道具<ID,数量>列表")
	public Vector<Pair<Integer, Integer>> items_ = new Vector<Pair<Integer, Integer>>();
	
	@Property("道具名字列表")
	public Vector<String> item_names_ = new Vector<String>();
		

	public ItemCountCollection() {}
	
	@Override
	public boolean isMultiField() 
	{
		return true;
	}
	
	@Override
	public String toString() 
	{
		return super.toString() + " [" + items_.size() + "]";
	}

}; // class ItemCountCollection




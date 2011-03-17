package com.cell.rpg.template.ability;

import java.util.HashMap;
import java.util.Vector;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;


/**
 * 一个道具列表，每个道具都有其概率值
 * @author WORSTER
 */
@Property("概率性道具的列表")
public class ItemRateCollection extends AbstractAbility
{
	private static final long serialVersionUID = 1L;
	
	@Property("道具ID列表")
	public Vector<Integer> item_ids_ = new Vector<Integer>();
	
	@Property("道具数量列表")
	public Vector<Integer> item_counts_ = new Vector<Integer>();
	
	@Property("道具概率列表")
	public Vector<Integer> item_rates_ = new Vector<Integer>();
	
	
	
	
	public ItemRateCollection() {}
	
	@Override
	public boolean isMultiField() 
	{
		return true;
	}
	
	@Override
	public String toString() 
	{
		return super.toString() + " [" + item_ids_.size() + "]";
	}
	
	public HashMap<Integer, Integer> getItemIDCountMap()
	{
		HashMap<Integer, Integer> ret = new HashMap<Integer, Integer>();
		
		if (item_ids_.size() > 0)
		{
			int i = 0;
			
			for ( Integer id : item_ids_ )
			{
				ret.put(id, item_counts_.elementAt(i));
				
				++i;
			}
		}
		
		return ret;
	}
	
	public HashMap<Integer, Integer> getItemIDRateMap()
	{
		HashMap<Integer, Integer> ret = new HashMap<Integer, Integer>();
		
		if (item_ids_.size() > 0)
		{
			int i = 0;
			
			for ( Integer id : item_ids_ )
			{
				ret.put(id, item_rates_.elementAt(i));
				
				++i;
			}
		}
		
		return ret;
	}	

}; // class






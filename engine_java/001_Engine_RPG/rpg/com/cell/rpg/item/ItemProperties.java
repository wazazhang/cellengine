package com.cell.rpg.item;

import java.util.ArrayList;

import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;

public class ItemProperties extends RPGObject implements NamedObject
{
	final private int	tid;
	
	/** */
	public String		name;
	
	transient
	private ItemPropertyTemplate[] properties;
	
	public ItemProperties(int tid, String name) {
		super(tid+"");
		this.tid	= tid;
		this.name	= name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public int getIntID() {
		return tid;
	}

	@Override
	public Class<?>[] getSubAbilityTypes() {
		return ItemPropertyTypes.getItemPropertyTypesList();
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	synchronized public ItemPropertyTemplate[] getItemProperties() {
		if (properties == null) {
			ArrayList<ItemPropertyTemplate> list = getAbilities(ItemPropertyTemplate.class);
			properties = list.toArray(new ItemPropertyTemplate[list.size()]);
		}
		return properties;
	}
	
//	/***
//	 * 生成该道具时，产生一组模板内的道具属性数据。
//	 * @return
//	 */
//	synchronized public ItemPropertyData[] createItemPropertiesData() throws Exception {
//		getItemProperties();
//		ItemPropertyData[] ret = new ItemPropertyData[properties.length];
//		for (int i = 0; i < ret.length; i++) {
//			ItemPropertyTemplate t = properties[i];
//			ret[i] = t.createData();
//		}
//		return ret;
//	}
//	-----------------------------------------------------------------------------------------------------------------------

}

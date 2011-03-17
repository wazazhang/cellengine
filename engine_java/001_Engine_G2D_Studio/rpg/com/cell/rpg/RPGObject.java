package com.cell.rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.DObject;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.io.RPGSerializationListener;
import com.cell.rpg.res.ResourceManager;
import com.cell.util.zip.ZipNode;

public abstract class RPGObject extends DObject implements Abilities, ZipNode, RPGSerializationListener
{
//	------------------------------------------------------------------------------------------------------------------
	
	final public String id;
	
	/**将显示在单位属性的Ability面板*/
	private Vector<AbstractAbility> 			abilities;

	transient AbstractAbility[]					static_abilities;
	
	transient Vector<RPGSerializationListener> 	seriListeners;
	
	private String[]							show_path;
	
//	------------------------------------------------------------------------------------------------------------------
	
	public RPGObject(String id) {
		this.id = id;
	}
	
	/**
	 * 在ResourceManager读取该数据时，设置编辑器中的可视路径
	 * @param xml_path
	 */
	public void loadTreePath(ResourceManager res, String tree_path) {
		this.show_path = CUtil.splitString(tree_path, "/");
	}
	
	/**
	 * 在ResourceManager读取该数据后，获得编辑器中的可视路径
	 * @return
	 */
	public String[] getTreePath() {
		return show_path;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (abilities == null) {
			this.abilities = new Vector<AbstractAbility>();
		}
		for (int i = abilities.size() - 1; i >= 0; --i) {
			if (abilities.get(i)==null) {
				abilities.remove(i);
			}
		}
	}
	
	@Override
	final public String getEntryName() {
		return id+".xml";
	}

//	------------------------------------------------------------------------------------------------------------------
	
	public void clearAbilities() {
		abilities.clear();
	}
	public void addAbility(AbstractAbility element) {
		abilities.add(element);
	}
	public void removeAbility(AbstractAbility element) {
		abilities.remove(element);
	}
	public AbstractAbility[] getAbilities() {
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

	public <T> T getAbility(Class<T> type) {
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				return type.cast(a);
			}
		}
		return null;
	}
	
	public <T> ArrayList<T> getAbilities(Class<T> type) {
		ArrayList<T> ret = new ArrayList<T>();
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				ret.add(type.cast(a));
			}
		}
		return ret;
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
	public int getAbilitiesCount() {
		return abilities.size();
	}
	
	public void addRPGSerializationListener(RPGSerializationListener listener) {
		if (seriListeners == null) {
			seriListeners = new Vector<RPGSerializationListener>();
		}
		seriListeners.add(listener);
	}
	
	public Vector<RPGSerializationListener> getRPGSerializationListeners() {
		return seriListeners;
	}
	
	@Override
	public void onReadComplete(RPGObject object) {}
	@Override
	public void onWriteBefore(RPGObject object) {}

//	------------------------------------------------------------------------------------------------------------------
	
}

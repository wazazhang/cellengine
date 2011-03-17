package com.cell.rpg.ability;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.cell.CUtil;
import com.g2d.editor.property.Util;

/**
 * 子类必须有默认构造函数，该对象将要放到ObjectPropertyPanel里编辑
 * @author WAZA
 *
 */
public abstract class AbstractAbility implements Serializable, Comparable<AbstractAbility>
{
	private static final long serialVersionUID = 1L;

	/**
	 * 是否允许多个字段在同一个单位中
	 * @return
	 */
	abstract public boolean isMultiField() ;
	
	@Override
	public String toString() {
		return getEditName(getClass());
	}
	
//	----------------------------------------------------------------------------------------------------------------
	
	@Override
	public int compareTo(AbstractAbility o) {
		return CUtil.getStringCompare().compare(o.toString(), this.toString());
	}
	
//	protected Object writeReplace() throws ObjectStreamException {
//		return this;
//	}
//	
//	protected Object readResolve() throws ObjectStreamException {
//		return this;
//	}

//	----------------------------------------------------------------------------------------------------------------
	
	public static Field[] getEditFields(Class<?> cls) {
		return Util.getEditFields(cls);
	}
	
	public static String getEditName(Class<?> cls) {
		return Util.getEditName(cls);
	}
	
	public static <T extends AbstractAbility> T createAbility(Class<T> cls) {
		try {
			T ability = cls.newInstance();
			return ability;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}

package com.cell.rpg.item;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.item.anno.ItemPersistenceField;
import com.cell.rpg.item.anno.ItemType;
import com.g2d.annotation.Property;

/**
 * 描述一个道具属性模板，子类必须标注签名{@link ItemType}，子类也必须有默认构造函数，
 * 该类不表示一个道具实体的属性，而是生成该道具的模板，
 * 模板功能有，提供数据存储格式，提供数据计算公式
 * @author WAZA
 *
 */
public abstract class ItemPropertyTemplate extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

//	public static Random RANDOM = new Random();
//	
//	private transient Integer	save_type 	= null;
//	private transient Field[]	save_fields = null;
	
	@Override
	public boolean isMultiField() 
	{
		return true;
	}
	
//	final public Integer getSaveType() 
//	{
//		if (save_type == null) {
//			save_type = getType(getClass());
//		}
//		return save_type;
//	}
//	
//	final public Field[] getSaveFields() 
//	{
//		if (save_fields == null) {
//			ArrayList<Field> fields = new ArrayList<Field>();
//			for (Field field : getClass().getDeclaredFields()) {
//				ItemPersistenceField pro = field.getAnnotation(ItemPersistenceField.class);
//				if (pro != null) {
//					fields.add(field);
//				}
//			}
//			save_fields = fields.toArray(new Field[fields.size()]);
//		}
//		return save_fields;
//	}
//	
//	final public ItemPropertyData createData() throws Exception 
//	{
//		ItemPropertyData data = new ItemPropertyData();
//		
//		data.property_type	= getSaveType();
//
//		Field[]	save_fields = getSaveFields();
//
//		data.args			= new Object[save_fields.length];
//		
//		for (int i=save_fields.length-1; i>=0; --i) {
//			data.args[i] = createFieldData(save_fields[i]);
//		}
//		
//		return data;
//	}
//	
//	/**
//	 * 子类可以自定义存储自己的复杂格式参数
//	 * @param field
//	 * @return
//	 * @throws Exception
//	 */
//	protected Object createFieldData(Field field) throws Exception
//	{
//		Object data = field.get(this);
//		if (data instanceof ArgTemplate<?>) {
//			return ((ArgTemplate<?>)data).getValue();
//		}
//		return data;
//	}
//	
//	/**
//	 * 子类实现道具计算公式及能力，可以用做攻击时，装备时，使用时等。
//	 * @param data 输入任意参数
//	 * @param args 输出任意参数
//	 * @return
//	 */
//	public abstract Object[] caculate(ItemPropertyData data, Object[] args);
//	
//	/**
//	 * 子类实现道具描述文本。
//	 * @param data
//	 * @param args
//	 * @return
//	 */
//	public abstract String toDescription(ItemPropertyData data, Object[] args);
	
//	-----------------------------------------------------------------------------------
	
	/**
	 * 属性参数模板
	 * @author WAZA
	 * @param <T>
	 */
	public static class ArgTemplate<T extends Number> implements Serializable
	{
		private static final long serialVersionUID = 1L;

		/**参数类型*/
		final private Class<T> value_type;
		
		/**参数初始化时最小值*/
		private T arg_create_min;
		/**参数初始化时最大值*/
		private T arg_create_max;
		
		@SuppressWarnings("unchecked")
		public ArgTemplate(T dmin, T dmax) 
		{
			this.value_type = (Class<T>)dmin.getClass();
			this.arg_create_min = dmin;
			this.arg_create_max = dmax;
		}
		public ArgTemplate(T value) 
		{
			this(value, value);
		}
		
		public T getArgCreateMax() 
		{
			return arg_create_max;
		}
		public T getArgCreateMin()
		{
			return arg_create_min;
		}
		public Class<T> getArgType() 
		{
			return value_type;
		}
		
		public T getValue() 
		{
			return Parser.castNumber(CUtil.getRandom(
					CUtil.getRandom(), 
					arg_create_min.doubleValue(),
					arg_create_max.doubleValue()), 
					value_type);
		}
	
		@Override
		public String toString() 
		{
			return arg_create_min + " - " + arg_create_max + " ("+value_type.getSimpleName()+")";
		}
		
		public boolean fromString(String text) throws Exception 
		{
			String[] lr = CUtil.splitString(text, "-");
			T tmin = Parser.stringToObject(lr[0].trim(), value_type);
			T tmax = Parser.stringToObject(lr[1].trim(), value_type);
			if (tmin.doubleValue() <= tmax.doubleValue()) {
				this.arg_create_min = tmin;
				this.arg_create_max = tmax;
			} else {
				this.arg_create_min = tmax;
				this.arg_create_max = tmin;
			}
			return true;
		}
	}
	
//	-----------------------------------------------------------------------------------
//
//	-----------------------------------------------------------------------------------
//	
//	public static void main(String[] args)
//	{
//		ArgTemplate<Integer> var = new ArgTemplate<Integer>(1, 1);
//		
//		var.fromString("0 - 1");
//		
//		System.out.println(var);
//		
//		for (int i=0; i<100; i++) {
//			double random = CUtil.getRandom(RANDOM, 10.0, 10.1);
//			System.out.println(random);
//		}
//	}

}


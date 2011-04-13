package com.cell.rpg.formula.helper;

import java.lang.reflect.Method;

import com.cell.rpg.formula.AbstractMethod;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.Arithmetic;
import com.cell.rpg.formula.ObjectIDValue;
import com.cell.rpg.formula.ObjectMethod;
import com.cell.rpg.formula.ObjectProperty;
import com.cell.rpg.formula.StaticMethod;
import com.cell.rpg.formula.TimeDurationValue;
import com.cell.rpg.formula.TimeValue;
import com.cell.rpg.formula.Value;
import com.cell.rpg.formula.SystemMethod.ISystemMethodAdapter;

/**
 * 基础的计算工具
 * @author WAZA
 *
 */
public abstract class AFormulaAdapterImpl implements IFormulaAdapter
{
	
	public Number getValue(AbstractValue value) throws Throwable
	{
		if (value instanceof Value) {
			return calculateValue((Value)value);
		}
		if (value instanceof ObjectIDValue) {
			return calculateObjectIDValue((ObjectIDValue)value);
		}
		if (value instanceof TimeValue) {
			return calculateTimeValue((TimeValue)value);
		}
		if (value instanceof TimeDurationValue) {
			return calculateTimeDurationValue((TimeDurationValue)value);
		}
		if (value instanceof StaticMethod) {
			return calculateStaticMethod((StaticMethod)value);
		}
		if (value instanceof Arithmetic) {
			return calculateArithmetic((Arithmetic)value);
		}
		if (value instanceof ObjectProperty) {
			return calculateObjectProperty((ObjectProperty)value);
		}
		if (value instanceof ObjectMethod) {
			return calculateObjectMethod((ObjectMethod)value);
		}
		return 0;
	}

	protected Number calculateValue(Value value) throws Throwable
	{
		return value.value;
	}
	
	protected Number calculateObjectIDValue(ObjectIDValue value) throws Throwable
	{
		return value.object_id;
	}
	
	protected Number calculateTimeValue(TimeValue value) throws Throwable
	{
		return value.time;
	}
	
	protected Number calculateTimeDurationValue(TimeDurationValue value) throws Throwable
	{
		if (value.time_unit!=null) {
			return value.time_unit.toMillis(value.duration);
		}
		return value.duration;
	}
	
	protected Number calculateSyntheticMethod(Object object, AbstractMethod value) throws Throwable {
		if (value.method_info.getReturnSynthetic() != null) {
			Method method	= value.getMethod();
			// 先获得符合类型的值
			Object ret		= method.invoke(object, value.getInvokeParams(this));
			// 将得到的复合类型带入下次运算
			return calculateSyntheticMethod(ret, value.return_object_method);
		} else {
			// 没有下一个子函数的根函数
			Method method = value.getMethod();
			Object ret = method.invoke(object, value.getInvokeParams(this));
			return (Number)ret;
		}
	}
	
	protected Number calculateStaticMethod(StaticMethod value) throws Throwable {
		return calculateSyntheticMethod(null, value);
	}
	
	protected Number calculateArithmetic(Arithmetic value) throws Throwable {
		return value.op.calculat(value.left.getValue(this), value.right.getValue(this));
	}
	
	@Override
	public Object invokeMethod(Object object, AbstractMethod value) throws Throwable {
		if (value.method_info.getReturnSynthetic() != null) {
			Method method	= value.getMethod();
			// 先获得符合类型的值
			Object ret		= method.invoke(object, value.getInvokeParams(this));
			// 将得到的复合类型带入下次运算
			return invokeMethod(ret, value.return_object_method);
		} else {
			// 没有下一个子函数的根函数
			Method method = value.getMethod();
			Object ret = method.invoke(object, value.getInvokeParams(this));
			return ret;
		}
	}
	
	abstract protected Number calculateObjectProperty(ObjectProperty value) throws Throwable;

	abstract protected Number calculateObjectMethod(ObjectMethod value) throws Throwable;
	
	abstract protected ISystemMethodAdapter getSystemMethodAdapter();
	
	
}

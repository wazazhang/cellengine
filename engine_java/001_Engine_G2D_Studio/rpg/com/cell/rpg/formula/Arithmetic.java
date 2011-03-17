package com.cell.rpg.formula;

import com.g2d.annotation.Property;

@Property("数学-运算")
public class Arithmetic extends AbstractValue
{
	@Property("被运算数")
	public AbstractValue	left	= new Value(1);
	@Property("运算数")
	public AbstractValue	right	= new Value(1);
	@Property("运算符")
	public Operator			op		= Operator.ADD;

//	---------------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "(" + left + " " + op + " " + right + ")";
	}
	
//	---------------------------------------------------------------------------------------------

	public static enum Operator
	{
		ADD("+"),
		SUB("-"),
		MUL("x"),
		DIV("/"),
		MOD("%"),
		;

		final private String text;
		private Operator(String text) {
			this.text = text;
		}
		@Override
		public String toString() {
			return text;
		}
		
		public Number calculat(Number src_value, Number dst_value)
		{
//			if (left.isReal() || right.isReal()) {
				switch(this) {
				case ADD: return src_value.doubleValue() + dst_value.doubleValue();
				case SUB: return src_value.doubleValue() - dst_value.doubleValue();
				case MUL: return src_value.doubleValue() * dst_value.doubleValue();
				case DIV: return src_value.doubleValue() / dst_value.doubleValue();
				case MOD: return src_value.doubleValue() % dst_value.doubleValue();
				}
//			} else {
//				switch(this) {
//				case ADD: return src_value.longValue() + dst_value.longValue();
//				case SUB: return src_value.longValue() - dst_value.longValue();
//				case MUL: return src_value.longValue() * dst_value.longValue();
//				case DIV: return src_value.longValue() / dst_value.longValue();
//				case MOD: return src_value.longValue() % dst_value.longValue();
//				}
//			}
			return 0;
		}
		
	}
	
//	---------------------------------------------------------------------------------------------
//	public static void main(String[] args)
//	{
//		System.out.println(cc().getClass());
//	}
//	
//	public static Number cc() {
//		return 1;
//	}
}

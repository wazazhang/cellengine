package com.cell.rpg.struct;

public enum Comparison 
{
	EQUAL						("等于"),
	NOT_EQUAL					("不等于"),
	
	GREATER_THAN 				("大于"),
	LESSER_THAN					("小于"),
	
	EQUAL_OR_GREATER_THAN		("大于等于"),
	EQUAL_OR_LESSER_THAN 		("小于等于"),
	;
	
	
	final private String text;
	private Comparison(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
	
	public boolean compare(Object src, Object dst)
	{
		if (src == null || dst == null) {
			switch (this) {
			case EQUAL:
				return dst == src;
			case NOT_EQUAL:
				return dst != src;
			default:
				return false;
			}
		}
		if (src instanceof Number && dst instanceof Number) {
			double src_n = ((Number)src).doubleValue();
			double dst_n = ((Number)dst).doubleValue();
			switch (this) {
			case GREATER_THAN:	
				return src_n > dst_n;
			case LESSER_THAN:	
				return src_n < dst_n;
			case EQUAL_OR_GREATER_THAN:
				return src_n >= dst_n;
			case EQUAL_OR_LESSER_THAN:
				return src_n <= dst_n;
			case EQUAL:
				return src_n == dst_n;
			case NOT_EQUAL:
				return src_n != dst_n;
			default:
				return false;
			}
		} 
		else {
			switch (this) {
			case EQUAL:		
				return src.equals(dst);
			case NOT_EQUAL:
				return !src.equals(dst);
			default:
				return false;
			}
		}
	}
	
}

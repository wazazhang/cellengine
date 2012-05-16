package com.cell.util;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.cell.util.EnumManager.ValueEnum;

public class DateUtil
{
	public static class TimeObject implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		public long 		time_value;	
		public TimeUnit 	time_unit;
		
		public TimeObject(long value, TimeUnit unit) {
			this.time_value	= value;
			this.time_unit	= unit;
		}
		
		public TimeObject() {
			this.time_value	= 1;	
			this.time_unit	= TimeUnit.HOURS;
		}
		
		@Override
		public String toString() {
			return time_value + "(" + time_unit + ")";
		}
		
		public long toMillis() {
			return this.time_unit.toMillis(time_value);
		}
	}
	
	public static enum DayOfWeek implements ValueEnum<Byte>
	{
		SUNDAY(1), 
		MONDAY(2), 
		TUESDAY(3), 
		WEDNESDAY(4), 
		THURSDAY(5), 
		FRIDAY(6), 
		SATURDAY(7),
		;
		
		final Byte value;
		private DayOfWeek(Integer value) {
			this.value = value.byteValue();
		}
		@Override
		public Byte getValue() {
			return value;
		}
		@Override
		public String toString() {
			switch(this) {
			case SUNDAY:	return "星期日";
			case MONDAY:	return "星期一";
			case TUESDAY:	return "星期二";
			case WEDNESDAY:	return "星期三";
			case THURSDAY:	return "星期四";
			case FRIDAY:	return "星期五";
			case SATURDAY:	return "星期六";
			}
			return super.toString();
		}
		
		public static DayOfWeek fromValue(int value) {
			return EnumManager.getEnum(DayOfWeek.class, (byte)value);
		}
		
	}
	
	public static enum MonthOfYear implements ValueEnum<Byte> 
	{
		JANUARY(1), 
		FEBRUARY(2),
		MARCH(3), 
		APRIL(4), 
		MAY(5), 
		JUNE(6), 
		JULY(7), 
		AUGUST(8), 
		SEPTEMBER(9), 
		OCTOBER(10),
		NOVEMBER(11), 
		DECEMBER(12), ;

		final Byte value;
		private MonthOfYear(int value) {
			this.value = (byte)value;
		}
		@Override
		public Byte getValue() {
			return value;
		}
		@Override
		public String toString() {
			switch(this) {
			case JANUARY:	return "一月";
			case FEBRUARY:	return "二月";
			case MARCH:		return "三月";
			case APRIL:		return "四月";
			case MAY:		return "五月";
			case JUNE:		return "六月";
			case JULY:		return "七月";
			case AUGUST:	return "八月";
			case SEPTEMBER:	return "九月";
			case OCTOBER:	return "十月";
			case NOVEMBER:	return "十一月";
			case DECEMBER:	return "十二月";
			}
			return super.toString();
		}
		public static MonthOfYear fromValue(int value) {
			return EnumManager.getEnum(MonthOfYear.class, (byte)value);
		}
	}
}

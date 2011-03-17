package com.cell.rpg.formula;

import java.text.DateFormat;
import java.util.Date;

import com.g2d.annotation.Property;

@Property("时间-特定的瞬间")
public class TimeValue extends AbstractValue
{
//	--------------------------------------------------------------------------------------------
	
	@Property("毫秒时间")
	public long time = 0;

//	--------------------------------------------------------------------------------------------
	
	public TimeValue() {
		
	}
	
	public TimeValue(long time) {
		this.time = time;
	}
	
	public TimeValue(Date date) {
		this.setDate(date);
	}
	
//	--------------------------------------------------------------------------------------------
//	visual
	private transient Date date;
	
	public Date getDate() {
		if (date == null) {
			date = new Date(time);
		} else {
			date.setTime(time);
		}
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
		this.time = date.getTime();
	}
	@Override
	public String toString() {
		return DateFormat.getDateInstance().format(getDate());
	}

//	--------------------------------------------------------------------------------------------
	
}

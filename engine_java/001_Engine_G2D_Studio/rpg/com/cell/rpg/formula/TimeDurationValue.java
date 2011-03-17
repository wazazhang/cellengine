package com.cell.rpg.formula;

import java.util.concurrent.TimeUnit;

import com.g2d.annotation.Property;

@Property("时间-时间长度")
public class TimeDurationValue extends AbstractValue
{
//	----------------------------------------------------------------------------------
	
	@Property("时间长度")
	public long 	duration 	= 0;
	
	@Property("时间单位")
	public TimeUnit time_unit	= TimeUnit.MINUTES;
	
//	----------------------------------------------------------------------------------
	
	public TimeDurationValue() {}
	
	public TimeDurationValue(long duration, TimeUnit time_unit) {
		this.duration = duration;
		this.time_unit = time_unit;
	}

//	----------------------------------------------------------------------------------

	public long toMillis() {
		return time_unit.toMillis(duration);
	}

	@Override
	public String toString() {
		if (time_unit!=null) {
			return duration + "(" + time_unit.toString()+")";
		}
		return duration + "(ms)";
	}
}

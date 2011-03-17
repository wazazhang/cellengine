package com.cell.util.task;

import java.util.Calendar;
import java.util.Date;

import com.cell.util.Pair;


public class CronScheduler 
{

	private long GetNextTime(CronExpression expression) 
	{
		Calendar cal = Calendar.getInstance();
		
		System.out.println(cal.getTime());
		
		int next_year			= expression.year.getKey();
		int next_month			= expression.month.getKey();
		int next_day_of_month	= expression.day_of_month.getKey();
		int next_week_of_year	= expression.week_of_year.getKey();
		int next_week_of_month	= expression.week_of_month.getKey();
		int next_day_of_week	= expression.day_of_week.getKey();
		int next_hour			= expression.hour.getKey();
		int next_minute			= expression.minute.getKey();
		int next_second			= expression.second.getKey();
		
		if (expression.year.getValue()) {
			next_year = cal.get(Calendar.YEAR) + 1;
		}

		Calendar next_cal = Calendar.getInstance();
		
		
		
		
		return 0;
	}
	
	
	static public void main(String ... args)
	{

		Calendar cal = Calendar.getInstance();
		
		System.out.println(cal.getTime());
		
		
	}
}

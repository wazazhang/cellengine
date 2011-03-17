package com.cell.util.task;

import java.io.Serializable;

import com.cell.util.Pair;
import com.cell.util.DateUtil.DayOfWeek;
import com.cell.util.DateUtil.MonthOfYear;

public class CronExpression implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static enum DateType
	{
		DAY_OF_MONTH, 
//		WEEK_OF_YEAR,
		WEEK_OF_MONTH,
		;
		public String toString() {
			switch(this){
			case DAY_OF_MONTH:
				return "日期时间";
//			case WEEK_OF_YEAR:
//				return "年的第几周";
			case WEEK_OF_MONTH:
				return "月的第几周";
			}
			return super.toString();
		}
	}

	public DateType			type			= DateType.DAY_OF_MONTH;
	
	/** the year minus 1900.*/
	final public Pair<Short, Boolean> year;
	
	
	/** the month between 1-12.*/
	final public Pair<Byte, Boolean> 	month;
	/** the day of the month between 1-31.*/
	final public Pair<Byte, Boolean> 	day_of_month;
	

//	/** the week between 1-54.*/
//	final public Pair<Byte, Boolean> 	week_of_year;

	/** the week of month between 1-5.*/
	final public Pair<Byte, Boolean> 	week_of_month;

	/** the day of the week between 1-7. (SUNDAY is 1) */
	final public Pair<Byte, Boolean> 	day_of_week;
	
	/** the hours between 0-23.*/
	final public Pair<Byte, Boolean> 	hour;
	/** the minutes between 0-59.*/
	final public Pair<Byte, Boolean> 	minute;
	/** the seconds between 0-59.*/
	final public Pair<Byte, Boolean> 	second;
	
	
	public CronExpression() {
		year			= new Pair<Short, Boolean>((short)2009, true);
		month 			= new Pair<Byte, Boolean>(MonthOfYear.JANUARY.getValue(), false);
		day_of_month 	= new Pair<Byte, Boolean>((byte)1, false);
//		week_of_year 	= new Pair<Byte, Boolean>((byte)1, true);
		week_of_month	= new Pair<Byte, Boolean>((byte)1, true);
		day_of_week 	= new Pair<Byte, Boolean>(DayOfWeek.SUNDAY.getValue(), false);
		hour 			= new Pair<Byte, Boolean>((byte)0, false);
		minute 			= new Pair<Byte, Boolean>((byte)0, false);
		second 			= new Pair<Byte, Boolean>((byte)0, false);
	}
	
	@Override
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		
		if (year.getValue()) {
			sb.append("每年 ");
		} else {
			sb.append(year.getKey() + "年 ");
		}
		
		switch (type) {
		case DAY_OF_MONTH:
			if (month.getValue()) {
				sb.append("每月 ");
			} else {
				sb.append(MonthOfYear.fromValue(month.getKey())+" ");
			}
			
			if (day_of_month.getValue()) {
				sb.append("每日 ");
			} else {
				sb.append(day_of_month.getKey()+"日 ");
			}
			break;

		case WEEK_OF_MONTH:
			if (month.getValue()) {
				sb.append("每月 ");
			} else {
				sb.append(MonthOfYear.fromValue(month.getKey())+" ");
			}
			
			if (day_of_week.getValue()) {
				sb.append("每天 ");
			} else {
				if (week_of_month.getValue()) {
					sb.append("每周 ");
				} else {
					sb.append("第"+week_of_month.getKey()+"个");
				}
				sb.append(DayOfWeek.fromValue(day_of_week.getKey())+" ");
			}
			break;
		}
		
			
			
		
		
		if (hour.getValue()) {
			sb.append("每小时 ");
		} else {
			sb.append(hour.getKey()+"时 ");
		}
		
		if (minute.getValue()) {
			sb.append("每分钟 ");
		} else {
			sb.append(minute.getKey()+"分 ");
		}
		
		if (second.getValue()) {
			sb.append("每秒 ");
		} else {
			sb.append(second.getKey()+"秒 ");
		}
		
		return sb.toString();
	}

	

//	----------------------------------------------------------------------------------------------
//	Seconds    0-59    , - * / 
//	Minutes    0-59    , - * / 
//	Hours    0-23    , - * / 
//	Day-of-month    1-31    , - * ? / L W 
//	Month    1-12 or JAN-DEC    , - * / 
//	Day-of-Week    1-7 or SUN-SAT    , - * ? / L # 
//	Year (Optional)    empty, 1970-2099    , - * / 
//	----------------------------------------------------------------------------------------------

	/**
	 * <table cellspacing="8">
	 * <tr>
	 * <th align="left">Field Name</th>
	 * <th align="left">&nbsp;</th>
	 * <th align="left">Allowed Values</th>
	 * <th align="left">&nbsp;</th>
	 * <th align="left">Allowed Special Characters</th>
	 * </tr>
	 * <tr>
	 * <td align="left"><code>Seconds</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>0-59</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>, - * /</code></td>
	 * </tr>
	 * <tr>
	 * <td align="left"><code>Minutes</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>0-59</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>, - * /</code></td>
	 * </tr>
	 * <tr>
	 * <td align="left"><code>Hours</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>0-23</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>, - * /</code></td>
	 * </tr>
	 * <tr>
	 * <td align="left"><code>Day-of-month</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>1-31</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>, - * ? / L W</code></td>
	 * </tr>
	 * <tr>
	 * <td align="left"><code>Month</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>1-12 or JAN-DEC</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>, - * /</code></td>
	 * </tr>
	 * <tr>
	 * <td align="left"><code>Day-of-Week</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>1-7 or SUN-SAT</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>, - * ? / L #</code></td>
	 * </tr>
	 * <tr>
	 * <td align="left"><code>Year (Optional)</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>empty, 1970-2099</code></td>
	 * <td align="left">&nbsp;</th>
	 * <td align="left"><code>, - * /</code></td>
	 * </tr>
	 * </table>
	 * 
	 * 
	 * @author WAZA
	 */
	public String toCronExpression() 
	{
		CronExpression cron = this;
		
		StringBuffer sb = new StringBuffer();
		
		// second
		if (cron.second.getValue())
			sb.append("* ");
		else
			sb.append(cron.second.getKey() + " ");
		
		// minute
		if (cron.minute.getValue())
			sb.append("* ");
		else
			sb.append(cron.minute.getKey() + " ");

		// hour
		if (cron.hour.getValue())
			sb.append("* ");
		else
			sb.append(cron.hour.getKey() + " ");
		
		
		switch (cron.type) {
		case DAY_OF_MONTH:
			
			// day_of_month
			if (cron.day_of_month.getValue())
				sb.append("* ");
			else
				sb.append(cron.day_of_month.getKey() + " ");
			
			// month
			if (cron.month.getValue())
				sb.append("* ");
			else
				sb.append(cron.month.getKey() + " ");
			
			// ignore week
			sb.append("? ");
			
			break;
		case WEEK_OF_MONTH:
			
			// ignore day_of_month
			sb.append("? ");
			
			// month
			if (cron.month.getValue())
				sb.append("* ");
			else
				sb.append(cron.month.getKey() + " ");
			
			// day_of_week
			if (cron.day_of_week.getValue()) {
				sb.append("* ");
			} else {
				sb.append(cron.day_of_week.getKey());
				if (cron.week_of_month.getValue())
					sb.append(" ");
				else
					sb.append("#" + cron.week_of_month.getKey() + " ");
			}
			
			break;
		}
		
		if (cron.year.getValue()) {
			sb.append("* ");
		} else {
			sb.append(cron.year.getKey() + " ");
		}

		return sb.toString();
	}
}

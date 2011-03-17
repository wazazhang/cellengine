package com.cell.rpg.quest;

import java.util.concurrent.TimeUnit;

import com.cell.rpg.ability.AbilitiesList;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.util.DateUtil.TimeObject;
import com.cell.util.task.CronExpression;
import com.g2d.annotation.Property;

public class QuestGenerator extends AbilitiesList
{
	public QuestGenerator() {
		addAbility(new System());
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				System.class,
				Scheduled.class,
				Festival.class,
		};
	}
	
//	-----------------------------------------------------------------------------------
	static abstract class QuestGeneratorAbility extends AbstractAbility {
		private static final long serialVersionUID = 1L;
	}
	
//	-----------------------------------------------------------------------------------

	/**
	 * {"[任务类型] 系统任务", "满足条件后，立即可以做"}
	 * @author WAZA
	 */
	@Property({"[任务触发] 系统任务", "满足条件后，立即可以做"})
	public static class System extends QuestGeneratorAbility {
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isMultiField() {
			return false;
		}
	}

//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 定时任务", "服务器开机后，每隔一定时间后，可重新做"}
	 * @author WAZA
	 */
	@Property({"[任务触发] 定时任务", "每隔一定时间后，可重新做"})
	public static class Scheduled extends QuestGeneratorAbility
	{
		private static final long serialVersionUID = 1L;
		@Property("刷新时间")
		public TimeObject	refresh_time		= new TimeObject(1, TimeUnit.DAYS);
		
		@Property("持续时间")
		public TimeObject	persist_time		= new TimeObject(1, TimeUnit.DAYS);
		
		public long getRefreshTimeMillis() {
			return refresh_time.time_unit.toMillis(refresh_time.time_value);
		}
		@Override
		public boolean isMultiField() {
			return false;
		}
	}


//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 节日任务", "在某个时间段内，重复刷新"}<br>
	 * @author WAZA
	 */
	@Property({"[任务触发] 节日任务", "在某个时间段内，重复刷新"})
	public static class Festival extends QuestGeneratorAbility
	{
		private static final long serialVersionUID = 1L;
		
		public static class FestivalDate extends CronExpression{
			private static final long serialVersionUID = 1L;
		}
		
		@Property("日期")
		public FestivalDate	date_time		= new FestivalDate();

		@Property("持续时间")
		public TimeObject	persist_time	= new TimeObject(1, TimeUnit.DAYS);
		
		@Property("在持续时间内可重复的次数，0表示仅一次")
		public int			repeat_count	= 0;

		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	

//	-----------------------------------------------------------------------------------
	
//	public static void main(String[] args)
//	{
//		Date date = new Date(java.lang.System.currentTimeMillis());
//		Calendar ca = DateFormat.getDateInstance().getCalendar();
//		java.lang.System.out.println(ca.get(Calendar.DAY_OF_WEEK));
//	}
}

package com.cell.rpg.scene.instance;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.cell.rpg.instance.zones.Data;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.Triggers;
import com.cell.rpg.scene.TriggersPackage;
import com.cell.util.DateUtil.TimeObject;
import com.cell.util.task.CronExpression;
import com.g2d.annotation.Property;


@Property("副本")
public class InstanceZone extends RPGObject implements NamedObject, TriggersPackage
{
	private String name;
	
	final private int id;

//	-------------------------------------------------------------------------------
	
	@Property("进入此副本的最大人数")
	public int player_count_max = 10;

	@Property("进入此副本的最低玩家等级")
	public int player_level_min = 10;
	
	@Property("进入此副本的最高玩家等级")
	public int player_level_max	= 100;
	
	@PropertyAdapter(PropertyType.TIME_TASK)
	@Property({"副本的刷新时间(任务)", "系统自动重置，该副本持续有效一定时间"})
	public CronExpression	flush_time_task		= new CronExpression();
	
	@PropertyAdapter(PropertyType.TIME_OBJECT)
	@Property({"副本的刷新后的持续时间", "系统自动重置，该副本持续有效一定时间"})
	public TimeObject		flush_persistance_time = new TimeObject();
//	-------------------------------------------------------------------------------
	
	@Property("副本是否可由玩家重置")
	public boolean			resetable			= false;
	
	@PropertyAdapter(PropertyType.TIME_OBJECT)
	@Property({"玩家重置时间", "比如在一小时内可进入2次"})
	public TimeObject		reset_clean_time 	= new TimeObject();

	@Property({"玩家在重置时间内可重置多少次", "比如在一小时内可进入2次"})
	public int				reset_count		 	= 2;
	
//	-------------------------------------------------------------------------------
	

	private String 				discussion			= "";

	private Triggers			triggers_package 	= new Triggers();
	
	private TriggerGenerator	binded_triggers 	= new TriggerGenerator();

	private LinkedHashMap<Integer, BindedScene> scenes	= new LinkedHashMap<Integer, BindedScene>();
	
	private Data				data_map			= new Data();
	
//	-------------------------------------------------------------------------------
//	/**临时变量，此副本下一次CD时间*/
//	transient private Date 		next_flush_time = null;
	
//	-------------------------------------------------------------------------------
	
	public InstanceZone(int id) {
		super(id+"");
		this.id = id;
		this.flush_time_task.day_of_month.setValue(true);
		this.flush_time_task.day_of_week.setValue(true);
		this.flush_time_task.week_of_month.setValue(true);
		this.flush_time_task.month.setValue(true);
		this.flush_time_task.year.setValue(true);
		this.flush_time_task.hour.set((byte)6, false);
		this.flush_persistance_time.time_unit = TimeUnit.DAYS;
		this.flush_persistance_time.time_value = 7;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (triggers_package == null) {
			triggers_package = new Triggers();
		}
		if (binded_triggers == null) {
			binded_triggers = new TriggerGenerator();
		}
		if (scenes == null) {
			scenes = new LinkedHashMap<Integer, BindedScene>();
		}
		if (data_map == null) {
			data_map = new Data();
		}
		if (flush_time_task == null) {
			this.flush_time_task = new CronExpression();
			this.flush_time_task.day_of_month.setValue(true);
			this.flush_time_task.day_of_week.setValue(true);
			this.flush_time_task.week_of_month.setValue(true);
			this.flush_time_task.month.setValue(true);
			this.flush_time_task.year.setValue(true);
			this.flush_time_task.hour.set((byte)6, false);
			
			this.flush_persistance_time = new TimeObject(7, TimeUnit.DAYS);
		}
		if (reset_clean_time == null) {
			this.reset_clean_time = new TimeObject(1, TimeUnit.HOURS);
			this.reset_count = 2;
		}
		
	}
	
	public Data getData() {
		return data_map;
	}
	
	public int getIntID() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{};
	}

//	-------------------------------------------------------------------------------
	
	public void setDiscussion(String discussion) {
		this.discussion = discussion;
	}

	public String getDiscussion() {
		return discussion;
	}
	
	public Triggers getTriggersPackage() {
		return triggers_package;
	}

	public TriggerGenerator getBindedTriggers() {
		return binded_triggers;
	}

	public HashMap<Integer, BindedScene> getScenes() {
		return scenes;
	}

//	-------------------------------------------------------------------------------
	
//	/**
//	 * 临时变量，此副本下一次CD时间
//	 */
//	public Date getNextFlushTime() {
//		return next_flush_time;
//	}
//
//	/**
//	 * 临时变量，此副本下一次CD时间
//	 */
//	public void setNextFlushTime(Date nextFlushTime) {
//		next_flush_time = nextFlushTime;
//	}

//	-------------------------------------------------------------------------------
	
	public static class BindedScene implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		final public int 	scene_id;
		
		public int 			edit_x = 0;
		
		public int 			edit_y = 0;
		
		public BindedScene(Scene scene) {
			this.scene_id = scene.getIntID();
		}
	}
	
}

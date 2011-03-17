package com.g2d.display.particle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.g2d.display.particle.appearance.GeometryRound;

/**
 * 包含多个例子的视图
 * @author WAZA
 */
public class Layer implements Serializable
{
	private static final long serialVersionUID = 1L;

	/** 设置别名 */
	public String	alias	= "Layer";
	

	public ParticleAppearance appearance	= new GeometryRound();
	
//	------------------------------------------------------------------------------------------------------------------
//	Scene
//	------------------------------------------------------------------------------------------------------------------

	/** 粒子生命周期时间范围(帧)*/
	public int		particle_min_age 		= 30, particle_max_age = 60;
	
	/** 每帧释放多少个粒子 */
	public int		particles_per_frame 	= 10;
	
	/** 粒子容量 */
	public int 		particles_capacity 		= 300;

	/** 持续释放粒子 */
	public boolean	particles_continued		= true;
	
	/**粒子是否释放到本地坐标系统*/
	public boolean	is_local_coordinate		= false;
	
//	------------------------------------------------------------------------------------------------------------------
//	Origin
//	------------------------------------------------------------------------------------------------------------------
	
	/** 发射基地位置 */
	public float 		origin_x = 0, origin_y = 0;
	
	/** 发射基地变换角度 (弧度)*/
	public float 		origin_rotation_angle = 0;
	
	/** 发射基地拉伸 */
	public float 		origin_scale_x = 1, origin_scale_y = 1;
	
	/** 发射基地几何造型 */
	public OriginShape	origin_shape = new OriginShape.Ring();
	
//	------------------------------------------------------------------------------------------------------------------
//	Spawn
//	------------------------------------------------------------------------------------------------------------------
	
	/** 以原点发射，选用该项后，发射角度为以原点为基础的偏角，spawn_angle和 spawn_angle_range无效*/
	public boolean spawn_orgin_angle	= false;
	
	/** 发射角度(弧度) */
	public float spawn_angle			= (float)(-Math.PI / 2);
	/** 发射角度随机范围(弧度) */
	public float spawn_angle_range		= (float)(-Math.PI / 8);
	
	/** 发射速度 */
	public float spawn_velocity			= 4.0f;
	/** 发射速度随机范围 */
	public float spawn_velocity_range	= 1.0f;
	
	/** 发射加速度 */
	public float spawn_acc				= 0.0f;
	/** 发射加速度随机范围 */
	public float spawn_acc_range		= 0.0f;
	
	/** 发射阻尼 */
	public float spawn_damp				= 1.0f;
	/** 发射阻尼随机范围 */
	public float spawn_damp_range		= 0.0f;
	
//	------------------------------------------------------------------------------------------------------------------
//	TimeLine And Affect
//	------------------------------------------------------------------------------------------------------------------

	final public TimeLine timeline = new TimeLine();
	
	
	final public ArrayList<ParticleAffect> affects = new ArrayList<ParticleAffect>();
	
//	------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		if (alias!=null) {
			return alias;
		}
		return super.toString();
	}
	
//	------------------------------------------------------------------------------------------------------------------

	public static class TimeLine implements Iterable<TimeNode> , Serializable
	{
		private static final long serialVersionUID = 1L;
		
		final private TimeNode start	= new TimeNode(0);
		
		final private TimeNode end		= new TimeNode(1);
		
		final private ArrayList<TimeNode> nodes = new ArrayList<TimeNode>();
		
		public TimeLine() {
			nodes.add(start);
			nodes.add(end);
		}
		
		synchronized
		public boolean addTimeNode(TimeNode node) {
			if (!nodes.contains(node)) {
				if (node != start && node != end) {
					if (node.position >= 0 && node.position <= 1) {
						nodes.add(node);
						Collections.sort(nodes);
						return true;
					}
				}
			}
			return false;
		}
		
		synchronized
		public boolean removeTimeNode(TimeNode node) {
			if (nodes.contains(node)) {
				if (node != start && node != end) {
					nodes.remove(node);
					return true;
				}
			}
			return false;
		}
		
		public TimeNode getStart() {
			return start;
		}
		
		public TimeNode getEnd() {
			return end;
		}
		
		@Override
		public Iterator<TimeNode> iterator() {
			return nodes.iterator();
		}
		
		public int size() {
			return nodes.size();
		}
	}
	
	/**
	 * 在整个粒子的生存周期内的变化，只对单个粒子有效。
	 */
	public static class TimeNode implements Comparable<TimeNode>, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		/** 播放位置 */
		public float 	position;
		
		/** 颜色变化 */
		public int		color			= 0xffffffff;
		public boolean	enable_color	= true;
		
		/** 缩放变化 */
		public float	size			= 1;
		public boolean	enable_size		= true;
		
		/** Alpha变化 */
		public float	alpha			= 1;
		public boolean	enable_alpha	= true;
		
		/** 旋转变化 */
		public float	spin			= 0;
		public boolean	enable_spin		= true;
		
		public TimeNode(float pos) {
			this.position = pos;
		}
		
		/** 播放位置(0f~1.0f) */
		public float getPosition() {
			return position;
		}
		
		@Override
		public int compareTo(TimeNode o) {
			return (int)(this.position*1000000 - o.position*1000000);
		}
	}
	
}

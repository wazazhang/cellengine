/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell.game;

import com.cell.CObject;

/**
 * 帧组合类型的父类
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CGroup extends CObject {

	protected int Frames[][];
	
	protected int SubIndex; //当前子精灵标号
	protected int SubCount; //组合精灵的子精灵数
	
	protected short w_left = 0;
	protected short w_top = 0;
	protected short w_bottom = 1;
	protected short w_right = 1;
	
	
	/**
	 * 功能描述
	 * @param left
	 * @param top
	 * @param right
	 * @param botton 
	 */
	protected void fixArea(int left, int top, int right, int botton) {
		if (left < w_left)
			w_left = (short) left;
		if (top < w_top)
			w_top = (short) top;
		if (right > w_right)
			w_right = (short) right;
		if (botton > w_bottom)
			w_bottom = (short) botton;
	}
	
	/**
	 * fast detect 2 collides's area specify frame, 
	 * area is every part within a frame's max scope. 
	 * </br>
	 * 检测该碰撞组的最大范围和另一个碰撞组是否相交。常用于快速排斥
	 * @param c1
	 * @param index1
	 * @param x1 发生碰撞的x坐标
	 * @param y1 发生碰撞的y坐标
	 * @param c2
	 * @param index2
	 * @param x2
	 * @param y2
	 * @return 
	 */
	static public boolean touchArea(
			CGroup c1,int x1,int y1,
			CGroup c2,int x2,int y2){
		if(CCD.cdRect(
				x1 + c1.w_left,  
				y1 + c1.w_top, 
				x1 + c1.w_right,  
				y1 + c1.w_bottom, 
				x2 + c2.w_left, 
				y2 + c2.w_top, 
				x2 + c2.w_right, 
				y2 + c2.w_bottom)){
			return true;
		}
		return false;
	}
	
	/**
	 * set frame sequence, frames[frame id][part id] = groupted object. </br> 
	 * e.g. : animates's image id ; collides's CCD object ;</br>
	 * 
	 * 设置帧序列： frame[帧号][部件号] = 资源部件（图片或碰撞块）
	 * @param frames frames[frame id][part id]
	 */
	public void setFrames(int[][] frames){
		Frames = frames;
	}
	
	public int[][] getFrames(){
		return Frames;
	}
	
	/**
	 * set part sequence specify frame index</br>
	 * 设置组合帧（一帧由N个部件组成的）
	 * @param frame frames[frame id][part id]
	 * @param index frame id
	 */
	public void setComboFrame(int[] frame,int index){
		Frames[index] = frame;
	}
	
	/**
	 * get frames count</br>
	 * 得到帧数量
	 * @return count
	 */
	public int getCount(){
		return Frames.length;
	}
	
}

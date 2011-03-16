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
/*
 * 张翼飞
 * Created on 2005-7-29
 *
 */
package com.cell.game;

import com.cell.CObject;


/**
 * 基本游戏单元，每个单元包含独立的状态机。
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CUnit extends CObject
{
	/** Unique ID */
	public int ID = this.hashCode();// unique id
	
	/**精灵属于哪个世界*/
	public CWorld world = null;

	/** 是否显示 */
	public boolean Visible 			= true;
	
	/** 是否活动 */
	public boolean Active 			= true; 
	
	/**色块*/
	public int BackColor = 0xff00ff00;
	
//	---------------------------------------------------------------------------------------------

	private IState state = null;
	
	public void setState(IState state){
		this.state = state;
	}
	
	public void delState(){
		this.state = null;
	}
	
	public void updateState(){
		if(state!=null){
			state.update();
		}
	}
	
//	---------------------------------------------------------------------------------------------
	
	
	
	
	
}


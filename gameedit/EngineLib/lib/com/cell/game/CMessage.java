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
 * Created on 2005-8-4
 *
 */
package com.cell.game;

import com.cell.CObject;


/**
 * 内部逻辑消息包
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CMessage extends CObject{
	/** 消息名字 */
	public String name;
	/** 发送者ID */
	public CUnit sender;
	/** 接收者ID */
	public CUnit receiver;
	/** 延时时间 */
	public int deliveryTime;
	/** 消息的范围 */
	public int state;

}


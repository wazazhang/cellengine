/*
 * 张翼飞
 * Created on 2005-8-4
 *
 */
package Cell.Game;

import Cell.CObject;

/**
 * @author 张翼飞
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CMsg extends CObject{
	
	/** 消息名字 */
	public int name;
	/** 发送者ID */
	public CUnit sender;
	/** 接收者ID */
	public CUnit receiver;
	/** 延时时间 */
	public int deliveryTime;
	/** 消息的范围 */
	public int state;

}


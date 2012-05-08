package com.cell.rpg.io;

import com.cell.rpg.RPGObject;


public interface RPGSerializationListener {

	/**
	 * 最后添加的将被最后执行
	 * @param object
	 * @param xml_file
	 */
	public void onReadComplete(RPGObject object);

	/**
	 * 最后添加的将被最先执行
	 * @param object
	 * @param xml_file
	 */
	public void onWriteBefore(RPGObject object);

}

package com.cell.sound;

/**
 * 可用于播放流媒体的播放器，声音系统的可选IPlayer实现
 * @author WAZA
 */
public interface IStreamPlayer extends IPlayer {

	/**
	 * 将声音数据缓冲到到播放器。该方法和setSound有类似的功能。
	 * @param sound
	 */
	public void queue(ISound sound);
	
}

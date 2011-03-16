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
 * WAZA
 * Created on 2006-7-8
 *
 */
package com.cell.musicgame;

/**
 * @author WAZA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * <summary> MGPlayer监听者。
 */
public interface MGPlayerListener{
    /**
     * <summary> (call back)每四分之一拍调用一次。
     * @param 这首曲子当前总共进行多少次beat。
     */
    public void beatBPM(int beatCount);
    
    /**
     * <summary> (call back)音符丢失的时候调用。
     * @param 哪一个游戏轨道。
     */
    public void lostNote(int line);
    
    /**
     * <summary> (call back)指定轨道的按钮按下。
     * @param line 游戏轨道号，小于LineCount。
     * @param acc  误差% , 100% = perfect
     */
    public void hitNote(int line,int acc);
    
    /**
     * <summary> (call back)显示音符位置。
     * @param 	
     * pos[游戏轨道数][同时显示音符数]：距离目标点的距离，单位像素。
     * 负数：还未到。
     * 正数：已经超过。
     * 零：音符刚好处在原点。
     * 如果在按下指定按钮的时候，该值的绝对值小于或等于MGPlayer.BeatScope，那么该音符就被HIT。
     * 如果该值大于MGPlayer.LostScope，那么该音符就被丢失。
     */
    public void showNote(int[][] pos);
}

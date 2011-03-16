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
 * Created on 2006-7-3
 *
 */
package com.cell.musicgame;

import java.util.Vector;

import com.cell.CUtil;

/**
 * @author WAZA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * <summary> 音符事件。
 */
class Note {
    /**音符开始时间，单位豪秒*/
    public int time;// ms
    /**音符处在的游戏的轨道*/
    public byte pos;
    /**音调*/
    public byte data;
}


/**
 * <summary> 控制事件。
 */
class Meta {
    /**播放速度改变事件*/
    final static public byte META_TYPE_TEMPO = (byte)(0x80 + 1) ;
    
    
    /**激发时间，单位豪秒*/
    public int time;
    /**类型*/
    public byte type;
    /**值*/
    public int data;
}



/**
 * <summary> 主运行逻辑。
 */
public class DJZPlayer {

    private MGPlayerListener Screen;
    
    private Vector Events;
    private Vector Controls;
    
    private int LineCount;
    private int BPM = 120;

    private long PerBeatTime;
    private long BeatTick;

    public long EndTime ;
    
    /**速度*/
    public int Speed = 100;
    
    /**按键的判定范围，单位ms*/
    public int BeatScope = 500;//0.5s
    /**音符MISS的范围，单位ms。在lostscope之后没有按，音符算丢失。*/
    public int LostScope = 2000;//2s

    
    private int BuffSize;//同时处理音符数

    private int[][] Pos;
    private int[] hitted;
    private int[] losted;
    private int[] hitAcc;
    
    /**自动演奏*/
    public boolean AutoPlay = false;
    /**自动演奏误差，单位ms*/
    public int AutoPlayHoldTime = 75;
    
    private boolean isBeat = false;
    
    /**这首曲子当前总共进行多少次beat*/
    private int beatCount = 0 ;

    /**
     * <summary>
     * @param	屏幕
     * @param	djz 2.0 文件路径
     * @param	游戏轨道数
     * @param	同时处理音符的数量，同屏幕最大显示数。
     */
    public DJZPlayer(MGPlayerListener screen,String DjzFile,int lineCount,int buffSize){
        
        Screen = screen;
        

        byte[] data = com.cell.CIO.loadFile(DjzFile);
        int P = 0 ;
        
        int FileSize 	= CUtil.read32(data, P); P += 4;//文件大小 
        EndTime 	= CUtil.read32(data, P); P += 4;//结束时间
        
        int NoteCount 	= CUtil.read32(data, P); P += 4;//音符数量
        Events = new Vector(NoteCount);
        for (int i = 0; i < NoteCount; i++){//音符数据序列
            Note note = new Note();
            note.time 	= CUtil.read32(data, P); P += 4;
            note.pos 	= CUtil.read8(data, P); P += 1;
            note.data 	= CUtil.read8(data, P); P += 1;
            Events.addElement(note);       
        }

        int MetaCount = CUtil.read32(data, P); P += 4;//控制数量
        Controls = new Vector(MetaCount);
        for (int i = 0; i < MetaCount; i++){//控制数据序列
            Meta meta = new Meta();
            meta.time 	= CUtil.read32(data, P); P += 4;
            meta.type 	= CUtil.read8(data, P); P += 1;
            meta.data 	= CUtil.read32(data, P); P += 4;
            Controls.addElement(meta);       
        }
        
        
        LineCount = lineCount;
        BuffSize = buffSize;
        
        Pos = new int[LineCount][BuffSize];
        hitted = new int[LineCount];
        losted = new int[LineCount];
        hitAcc = new int[LineCount];
        PerBeatTime = 0;
        BeatTick = 1000 * 60 / BPM;

        Speed = 100;
    }
    
    /**
     * <summary> 主循环中每周期调用一次。
     * @param	当前歌曲播放时间
     */
    public void update(long CurPlayTime){
//        control track
        if (Controls.isEmpty()==false && ((Meta)Controls.elementAt(0)).time <= CurPlayTime ){
            switch (((Meta)Controls.elementAt(0)).type){
            case Meta.META_TYPE_TEMPO:
                setBPM(((Meta)Controls.elementAt(0)).data);
            	Controls.removeElementAt(0);
                break;
            }
        }

//        event
        for (int t = LineCount - 1; t >= 0; t--) {
            hitted[t] = -1;
            for (int i = BuffSize - 1; i >= 0; i--) {
                Pos[t][i] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < BuffSize; i++){
            if (i >= Events.size()) break;
            // 检测当前音符按下范围
            if (AutoPlay == false){
                if (hitted[((Note)Events.elementAt(i)).pos % LineCount] < 0 &&
                    Math.abs(((Note)Events.elementAt(i)).time - CurPlayTime) < BeatScope){
                    hitted[((Note)Events.elementAt(i)).pos % LineCount] = i;
                    hitAcc[((Note)Events.elementAt(i)).pos % LineCount] = (int)(((Note)Events.elementAt(i)).time - CurPlayTime);
                }
            }else{
                if (((Note)Events.elementAt(i)).time <= CurPlayTime){
                    hitted[((Note)Events.elementAt(i)).pos % LineCount] = i;
                }
            }

            // 检测音符是否已经过期
            if (((Note)Events.elementAt(i)).time < CurPlayTime - LostScope){
                losted[((Note)Events.elementAt(i)).pos % LineCount]++;
                // call back
                Screen.lostNote(((Note)Events.elementAt(i)).pos % LineCount);                
                Events.removeElementAt(i);
                continue;
            }
            
            // 根据时间计算音符的屏幕坐标
            Pos[((Note)Events.elementAt(i)).pos % LineCount][i]= 
                (int)((((Note)Events.elementAt(i)).time - CurPlayTime) 
                        * Speed / 1000 );
            
            // call back
            Screen.showNote(Pos);
              
        }
//      bpm
        if (CurPlayTime - PerBeatTime >= BeatTick){
            beatCount++;
            isBeat = true;
            PerBeatTime = CurPlayTime;
            
            // call back
            Screen.beatBPM(beatCount);
        }else{
            isBeat = false;
        }
    }

    private void setBPM(int bpm){
        BPM = bpm;
        BeatTick = 1000 * 60 / BPM;
    }

    /**
	 * <summary> 显示音符位置。
     * @param 	
     * pos[游戏轨道数][同时显示音符数]：距离目标点的距离，单位像素。
     * 负数：还未到。
     * 正数：已经超过。
     * 零：音符刚好处在原点。
     * 如果在按下指定按钮的时候，该值的绝对值小于或等于MGPlayer.BeatScope，那么该音符就被HIT。
     * 如果该值大于MGPlayer.LostScope，那么该音符就被丢失。
     */
    public int[][] getPos(){
        return Pos;
    }

    /**
     * <summary> 指定轨道的按钮按下。
     * @param track 游戏轨道号，小于LineCount。
     * @return 如果大于等于0：音调。小于0：没有HIT的音符。
     */
    public byte hit(int track){
        if (hitted[track] >= 0){
            Screen.hitNote(track,hitAcc[track]);
            byte ret = ((Note)Events.elementAt(hitted[track])).data;
         	Events.removeElementAt(hitted[track]);
            return ret;
        }
        return -1;
    }

    /**
     * <summary> 查寻音符丢失。
     * @param 哪一个游戏轨道。
     * @return 丢失了多少
     */
    public int lost(int track){
        if (losted[track] > 0){
            int ret = losted[track];
            losted[track] = 0;
            return ret;
        }
        return 0;
    }

    /**
     * <summary> 当前时间是否是四分之一拍。
     * @return 当前时间是否是四分之一拍。
     */
    public boolean beatBPM(){
        return isBeat;
    }

    /**
     * <summary> 得到游戏轨道数量。
     * @return 游戏轨道数
     */
    public int getLineCount(){
        return LineCount;
    }

    /**
     * <summary> 得到当前BPM值。
     * @return BPM
     */
    public int getBPM(){
        return BPM;
    }
}

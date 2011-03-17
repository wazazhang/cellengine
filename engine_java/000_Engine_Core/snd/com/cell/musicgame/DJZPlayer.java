
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
 * <summary> 
 */
class Note {
    public int time; // ms
    public byte pos; // track
    public byte data; // sound data
}


/**
 * <summary> 
 */
class Meta {
    /** event */
    final static public byte META_TYPE_TEMPO = (byte)(0x80 + 1) ;
    
    public int time; // ms
    public byte type; // meta type
    public int data; // meta data
}



/**
 * <summary> 
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
    
    public int Speed = 100;
    
    /**hit key check scope*/
    public int BeatScope = 500;//0.5s
    /**loss check scope*/
    public int LostScope = 2000;//2s

    
    private int BuffSize;

    private int[][] Pos;
    private int[] hitted;
    private int[] losted;
    private int[] hitAcc;
    
    /***/
    public boolean AutoPlay = false;
    /**auto play check time ms*/
    public int AutoPlayHoldTime = 75;
    
    private boolean isBeat = false;
    
    private int beatCount = 0 ;

    public DJZPlayer(MGPlayerListener screen,String DjzFile,int lineCount,int buffSize){
        
        Screen = screen;
        

        byte[] data = com.cell.CIO.loadData(DjzFile);
        int P = 0 ;
        
        int FileSize 	= CUtil.read32(data, P); P += 4;
        EndTime 	= CUtil.read32(data, P); P += 4;
        
        int NoteCount 	= CUtil.read32(data, P); P += 4;
        Events = new Vector(NoteCount);
        for (int i = 0; i < NoteCount; i++){
            Note note = new Note();
            note.time 	= CUtil.read32(data, P); P += 4;
            note.pos 	= CUtil.read8(data, P); P += 1;
            note.data 	= CUtil.read8(data, P); P += 1;
            Events.addElement(note);       
        }

        int MetaCount = CUtil.read32(data, P); P += 4;
        Controls = new Vector(MetaCount);
        for (int i = 0; i < MetaCount; i++){
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
            // test key hit
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

            // test node loss
            if (((Note)Events.elementAt(i)).time < CurPlayTime - LostScope){
                losted[((Note)Events.elementAt(i)).pos % LineCount]++;
                // call back
                Screen.lostNote(((Note)Events.elementAt(i)).pos % LineCount);                
                Events.removeElementAt(i);
                continue;
            }
            
            // set node screen pos
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

    public int[][] getPos(){
        return Pos;
    }

    public byte hit(int track){
        if (hitted[track] >= 0){
            Screen.hitNote(track,hitAcc[track]);
            byte ret = ((Note)Events.elementAt(hitted[track])).data;
         	Events.removeElementAt(hitted[track]);
            return ret;
        }
        return -1;
    }

    public int lost(int track){
        if (losted[track] > 0){
            int ret = losted[track];
            losted[track] = 0;
            return ret;
        }
        return 0;
    }

    public boolean beatBPM(){
        return isBeat;
    }

    public int getLineCount(){
        return LineCount;
    }

    public int getBPM(){
        return BPM;
    }
}

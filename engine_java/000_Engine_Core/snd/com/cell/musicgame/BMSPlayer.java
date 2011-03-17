
/*
 * WAZA
 * Created on 2006-7-12
 *
 */
package com.cell.musicgame;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;


import com.cell.CObject;
import com.cell.CUtil;



/**
 * @author WAZA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
class Key {
    long position;
    int type;
    String data;
}



public class BMSPlayer extends CObject {
//    01 = BG NOTE 
//
//    03 = 0~255（0h~FFh） BPM 
//
//    04 = BGA   Image Index
//    06 = POOR  Image Index 
//    07 = Layer Image Index
//
//    08 = BPM index ,decimal BPM or big than 255 BPM （index of #BPMXX define）
//    09 = STOP Time （index of #STOPXX）
//
//    11 = 1P KEY 1
//    12 = 1P KEY 2
//    13 = 1P KEY 3
//    14 = 1P KEY 4
//    15 = 1P KEY 5
//    16 = 1P KEY SC
//    18 = 1P KEY 6
//    19 = 1P KEY 7
//
//    21 = 2P KEY 1
//    22 = 2P KEY 2
//    23 = 2P KEY 3
//    24 = 2P KEY 4
//    25 = 2P KEY 5
//    26 = 2P KEY SC
//    28 = 2P KEY 6
//    29 = 2P KEY 7
//
//    51 = 1P LONG KEY 1
//    52 = 1P LONG KEY 2
//    53 = 1P LONG KEY 3
//    54 = 1P LONG KEY 4
//    55 = 1P LONG KEY 5
//    56 = 1P LONG KEY SC
//    58 = 1P LONG KEY 6
//    59 = 1P LONG KEY 7
//
//    61 = 2P LONG KEY 1
//    62 = 2P LONG KEY 2
//    63 = 2P LONG KEY 3
//    64 = 2P LONG KEY 4
//    65 = 2P LONG KEY 5
//    66 = 2P LONG KEY SC
//    68 = 2P LONG KEY 6
//    69 = 2P LONG KEY 7

//    *---------------------- HEADER FIELD
//
//    #PLAYER 2
//    #GENRE 123
//    #TITLE 123
//    #ARTIST 123
//    #BPM 120
//    #PLAYLEVEL 1
//    #RANK 3
//    #TOTAL 123
//    #VOLWAV 123
//    #STAGEFILE 123
//
//    #WAV01 Ba_b_4.wav
//    #WAV02 Ba_b_8.wav
//    #WAV03 Ba_c#_4.wav
//
//    #BMP00 123
//    #BMP01 back.bmp
//    #BMP02 end01.bmp
//    #BMP03 Fade01.bmp
//
//
//    #BPM01 256
//
//    #STOP01 123
//
//
//
//
//    *---------------------- MAIN DATA FIELD
//
//    #00008:0001
//    #00009:00010000
//    #00022:00010000
//    #00061:01
//
//    #00161:01
    final static private char[] HEADER_PLAYER	= {'P','L','A','Y','E','R',' '};
    final static private char[] HEADER_GENRE	= {'G','E','N','R','E',' '};
    final static private char[] HEADER_TITLE 	= {'T','I','T','L','E',' '};
    final static private char[] HEADER_ARTIST 	= {'A','R','T','I','S','T',' '};
    final static private char[] HEADER_BPM 		= {'B','P','M',' '};
    final static private char[] HEADER_PLAYLEVEL= {'P','L','A','Y','L','E','V','E','L',' '};
    final static private char[] HEADER_RANK 	= {'R','A','N','K',' '};
    final static private char[] HEADER_TOTAL 	= {'T','O','T','A','L',' '};
    final static private char[] HEADER_VOLWAV 	= {'V','O','L','W','A','V',' '};
    final static private char[] HEADER_STAGEFILE= {'S','T','A','G','E','F','I','L','E',' '};
    
    final static private char[] NOTE_TYPE_WAV	= {'W','A','V'};
    final static private char[] NOTE_TYPE_BMP	= {'B','M','P'};
    final static private char[] NOTE_TYPE_BPM	= {'B','P','M'};
    final static private char[] NOTE_TYPE_STOP	= {'S','T','O','P'};
//    final static public char[] NOTE_EXPAND;
    
    final static private int KEY_NOTE_BGKEY	= 1;
    final static private int KEY_BPM_BYTE	= 3;
    final static private int KEY_BMP_BGA	= 4;
    final static private int KEY_BMP_LAYER	= 6;
    final static private int KEY_BMP_POOR	= 7;
    final static private int KEY_BPM		= 8;
    final static private int KEY_STOP		= 9;

    final static private int KEY_1P_1		= 11;
    final static private int KEY_1P_2		= 12;
    final static private int KEY_1P_3		= 13;
    final static private int KEY_1P_4		= 14;
    final static private int KEY_1P_5		= 15;
    final static private int KEY_1P_SC		= 16;
    final static private int KEY_1P_6		= 18;
    final static private int KEY_1P_7		= 19;

    final static private int KEY_2P_1		= 21;
    final static private int KEY_2P_2		= 22;
    final static private int KEY_2P_3		= 23;
    final static private int KEY_2P_4		= 24;
    final static private int KEY_2P_5		= 25;
    final static private int KEY_2P_SC		= 26;
    final static private int KEY_2P_6		= 28;
    final static private int KEY_2P_7		= 29;
    
    final static private int KEY_1P_LONG_1	= 51;
    final static private int KEY_1P_LONG_2	= 52;
    final static private int KEY_1P_LONG_3	= 53;
    final static private int KEY_1P_LONG_4	= 54;
    final static private int KEY_1P_LONG_5	= 55;
    final static private int KEY_1P_LONG_SC	= 56;
    final static private int KEY_1P_LONG_6	= 58;
    final static private int KEY_1P_LONG_7	= 59;
    
    final static private int KEY_2P_LONG_1	= 61;
    final static private int KEY_2P_LONG_2	= 62;
    final static private int KEY_2P_LONG_3	= 63;
    final static private int KEY_2P_LONG_4	= 64;
    final static private int KEY_2P_LONG_5	= 65;
    final static private int KEY_2P_LONG_SC	= 66;
    final static private int KEY_2P_LONG_6	= 68;
    final static private int KEY_2P_LONG_7	= 69;
    
    //--------------------------------------------------------------------------------------------

    public String player;
    public String genre;
    public String title;
    public String artist;
    public String bpm;
    public String playlevel;
    public String rank;
    public String total;
    public String volwav;
    public String stagefile;
      
    private Hashtable notes_wav 	= new Hashtable();
    private Hashtable notes_bmp		= new Hashtable();
    private Hashtable notes_bpm		= new Hashtable();
    private Hashtable notes_stop	= new Hashtable();
    //private Hashtable notes_expand	= new Hashtable();
             
    private Vector keyBGM[];
    private Vector keyPLAYER[];
    private Vector keyBPMBYTE = new Vector();
    private Vector keyBPM = new Vector();
    private Vector keySTOP = new Vector();
    private Vector keyBMP = new Vector();
//    private Vector keyBMPLAYER = new Vector();
//    private Vector keyBMPPOOR = new Vector();
    
    
    //----------------------------------------------------------------------------------
    
    
    private String dir;
    
//    private Image[] image;
//	private Player[] soundman;
//	private VolumeControl volumeControl;
    
    private MGPlayerListener Screen;

    private int LineCount;

    private int LineOffset = 0;
    
    /***/
    public int EndTime ;
    
    /**real time direct sound 32+16 (channel) */
    public boolean realTimeSound = false ;
    
    /**Speed*/
    public int Speed ;
    
    /** hit scope 1/beat */
    public int BeatScope = 16;//delta

    private int BuffSize;

    private int[][] Pos;
//    private int[] hitted;
    private int[] losted;
//    private int[] hitAcc;
    
    public boolean AutoPlay = false;
    
    private boolean isBeat = false;
    
    /**total beat count*/
    private int beatCount = 0 ;

//-------------------------------------------------------------------------------------------------
    
    public BMSPlayer (
            MGPlayerListener screen,
            String bmsFile,
            int lineCount,
            int buffSize
            ){    
        try {
            dir = bmsFile.substring(0,bmsFile.lastIndexOf('/')+1);
            println("Song dir = "+dir);
            
            Screen = screen;

            LineCount = lineCount;
            BuffSize = buffSize;
            
            Pos = new int[LineCount][BuffSize];
            losted = new int[LineCount];

            setBPM1000(120000);
            Speed = 1;
            
            keyBGM = new Vector[32];
            for(int i=keyBGM.length-1;i>=0;i--){
                keyBGM[i] = new Vector();
            }
            
            keyPLAYER = new Vector[LineCount];
            for(int i=keyPLAYER.length-1;i>=0;i--){
                keyPLAYER[i] = new Vector();
            }
            
            println("BMS file start !");
            String data = new String(com.cell.CIO.loadData(bmsFile));
            char[] bms = data.toCharArray();
            //System.out.print(bms);
            int p = 0;
            boolean headerOK = false;
            boolean dataOK = false;
            while(p<bms.length){
                switch(bms[p++]){
                case '#':
                    int line = CUtil.charArrayIndexOf(bms,'\r',p);
                    for(int i=p;i<line;i++){
                        // header
                        if(headerOK == false){
                            if((player==null) && (player = setHeader(bms,p,line,HEADER_PLAYER))!=null)break;
                            if((genre==null) && (genre =setHeader(bms,p,line,HEADER_GENRE))!=null)break;
                            if((title==null) && (title = setHeader(bms,p,line,HEADER_TITLE))!=null)break;	                    
                            if((artist==null) && (artist = setHeader(bms,p,line,HEADER_ARTIST))!=null)break;
                            if((bpm==null) && (bpm = setHeader(bms,p,line,HEADER_BPM))!=null)break;
                            if((playlevel==null) && (playlevel = setHeader(bms,p,line,HEADER_PLAYLEVEL))!=null)break;
                            if((rank==null) && (rank = setHeader(bms,p,line,HEADER_RANK))!=null)break;
                            if((total==null) && (total = setHeader(bms,p,line,HEADER_TOTAL))!=null)break;
                            if((volwav==null) && (volwav = setHeader(bms,p,line,HEADER_VOLWAV))!=null)break;
                            if((stagefile==null) && (stagefile = setHeader(bms,p,line,HEADER_STAGEFILE))!=null){
//                                try {
//                                    IImage img = Image.createImage(dir+stagefile);
//                                    println("Load Stage Image " + dir+stagefile);
//                                    if(curBMP==null){
//                                        curBMP = img;
//                                    }
//                                } catch (IOException e1) {
//                                    e1.printStackTrace();
//                                }
                                break;
                            }
                   
                            String[] kv = null;
                            if((kv = setNotes(bms,p,line,NOTE_TYPE_WAV))!=null){
//                                try {
//                                    CSoundPlayer sp = new CSoundPlayer(dir+kv[1],CSoundPlayer.TYPE_WAV,1);
//                                    println("Load Sound " + dir+kv[1]);
//                                    notes_wav.put(kv[0],sp);
//                                } catch (RuntimeException e1) {
//                                    e1.printStackTrace();
//                                }
                                break;
                            }
                            if((kv = setNotes(bms,p,line,NOTE_TYPE_BMP))!=null){
//                                try {
//                                    Image img = Image.createImage(dir+kv[1]);
//                                    println("Load Image " + dir+kv[1]);
//                                    notes_bmp.put(kv[0],img);
//                                    if(curBMP==null){
//                                        curBMP = img;
//                                    }
//                                } catch (IOException e1) {
//                                    e1.printStackTrace();
//                                }
                                break;
                            }
                            if((kv = setNotes(bms,p,line,NOTE_TYPE_BPM))!=null){
                                try {
                                    long nbpm = CUtil.stringDecimalToInt(kv[1],0,kv[1].length(),1000);
                                    notes_bpm.put(kv[0],new Long(nbpm));
                                    println("BPM Note " + kv[0] + " " + nbpm);
                                } catch (RuntimeException e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            }
                            if((kv = setNotes(bms,p,line,NOTE_TYPE_STOP))!=null){
                                try {
                                    long nstop = CUtil.stringDecimalToInt(kv[1],0,kv[1].length(),1000);
                                    notes_stop.put(kv[0],new Long(nstop));
                                    println("STOP Note " + kv[0] + " " + nstop);
                                } catch (RuntimeException e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            }
                        }
                        //data
                        if(dataOK == false){
                            if(setKey(bms,p,line,keyPLAYER)){
                                headerOK = true;
                                break;
                            }
                        }
                    }
                    p=line;
                    break;
//            case '%':
//                break;
//            case '\n':
//                println("ln");
//                break;
//            case '\r':
//              println("rt");
//              break;
                }
            }
            
            println("BMS file end ! process "+p+"(bytes)");
            
            setBPM1000(CUtil.stringDecimalToInt(bpm,0,bpm.length(),1000));
            println("BPM = " + BPM);

        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
//        	if (curBMP == null){
//            curBMP = Image.createImage(1, 1);
//	        }  
//	        if (curBMPLayer == null){
//	            curBMP = Image.createImage(1, 1);
//	        }
//	        if (curBMPPoor == null){
//	            curBMP = Image.createImage(1, 1);
//	        }
        }catch(Exception err){
        
        }
        
    }

    private String setHeader(char[] src,int p,int line,char[] HEADER){
        if(CUtil.charArrayCMP(
                src,p,HEADER.length,
                HEADER,0,HEADER.length)){
            int blank = p+HEADER.length;
            while((src[blank]==' ' || src[blank]=='\t') && blank<line){
                blank++;
            }
            String des = String.valueOf(src,blank,line-blank);
//            println("#" + String.valueOf(HEADER) + des);
            return des;
        }
        return null;
    }
    
    private String[] setNotes(char[] src,int p,int line,char[] NOTE_TYPE){
        if(line<p+NOTE_TYPE.length+3)return null;
        if(CUtil.charArrayCMP(
                src,p,NOTE_TYPE.length,
                NOTE_TYPE,0,NOTE_TYPE.length)){
            String kv[] = new String[2];
            kv[0] = String.valueOf(src,p+NOTE_TYPE.length,2);
            int blank = p+NOTE_TYPE.length+3;
            while((src[blank]==' ' || src[blank]=='\t') && blank<line){
                blank++;
            }
            kv[1] = String.valueOf(src,blank,line-blank);
//            println("#" + String.valueOf(NOTE_TYPE) + kv[0] + " " + kv[1]);
            return kv;
        }
        return null;
    }
    
    private int lineCount = 0;
    private int lineBGM = 0;
    private boolean setKey(char[] src,int p,int line,Vector[] des){
        if (CUtil.charArrayIsDigit(src,p,5)==5){
            int m = CUtil.charArrayIndexOf(src,':',p+5,line-p);
            if(m>-1){
                int type = CUtil.charArrayDigitToInt(src,p+3,2);
                if(lineCount<CUtil.charArrayDigitToInt(src,p,3)){
                    lineCount = CUtil.charArrayDigitToInt(src,p,3);
                    lineBGM = 0;
                }
//                CSCUtil.print("Type"+src[p+3]+src[p+4]+":");
                int blank = m+1;
                while((src[blank]==' ' || src[blank]=='\t') && blank<line){
                    blank++;
                }
                int len = CUtil.charArrayIsWordNum(src,blank,line-blank);
                if(len>=2){
                    len = len - len%2;
                    for(int d=blank;d<blank+len;d+=2){
                        if(src[d+0]!='0' || src[d+1]!='0'){
                            Key k = new Key();
    	                    k.position 	= 
    	                        DivFullNote*F*lineCount+
    	                        DivFullNote*F*(d-blank)/len;
    	                    k.type 	= type;
    	                    k.data 	= String.valueOf(src,d,2);
//	                        CSCUtil.print(
////	                                " " + CUtil.charArrayDigitToInt(src,p+0,3) +
////	                                " " + (d-blank) +
//	                                k.data
//	                                );
    	                    switch(k.type){
		                      case KEY_NOTE_BGKEY:
		                          keyBGM[lineBGM%keyBGM.length].addElement(k);
		                          break;
		                      case KEY_BPM_BYTE:
		                          keyBPMBYTE.addElement(k);
		                          break;
		                      case KEY_BMP_BGA:
		                      case KEY_BMP_LAYER:
		                      case KEY_BMP_POOR:
		                          keyBMP.addElement(k);
		                          break;
		                      case KEY_BPM:
		                          keyBPM.addElement(k);
		                          break;
		                      case KEY_STOP:
		                          keySTOP.addElement(k);
		                          break;
		                      // 1p 0~7
		                      case KEY_1P_SC:if(0+0 < LineCount)keyPLAYER[0+0].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_1:if(0+1 < LineCount)keyPLAYER[0+1].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_2:if(0+2 < LineCount)keyPLAYER[0+2].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_3:if(0+3 < LineCount)keyPLAYER[0+3].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_4:if(0+4 < LineCount)keyPLAYER[0+4].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_5:if(0+5 < LineCount)keyPLAYER[0+5].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_6:if(0+6 < LineCount)keyPLAYER[0+6].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_7:if(0+7 < LineCount)keyPLAYER[0+7].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      
		                      case KEY_1P_LONG_SC:if(0+0 < LineCount)keyPLAYER[0+0].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_LONG_1:if(0+1 < LineCount)keyPLAYER[0+1].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_LONG_2:if(0+2 < LineCount)keyPLAYER[0+2].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_LONG_3:if(0+3 < LineCount)keyPLAYER[0+3].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_LONG_4:if(0+4 < LineCount)keyPLAYER[0+4].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_LONG_5:if(0+5 < LineCount)keyPLAYER[0+5].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_LONG_6:if(0+6 < LineCount)keyPLAYER[0+6].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_1P_LONG_7:if(0+7 < LineCount)keyPLAYER[0+7].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      // 2p 8~15
		                      case KEY_2P_SC:if(8+0 < LineCount)keyPLAYER[8+0].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_1:if(8+1 < LineCount)keyPLAYER[8+1].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_2:if(8+2 < LineCount)keyPLAYER[8+2].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_3:if(8+3 < LineCount)keyPLAYER[8+3].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_4:if(8+4 < LineCount)keyPLAYER[8+4].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_5:if(8+5 < LineCount)keyPLAYER[8+5].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_6:if(8+6 < LineCount)keyPLAYER[8+6].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_7:if(8+7 < LineCount)keyPLAYER[8+7].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      
		                      case KEY_2P_LONG_SC:if(8+0 < LineCount)keyPLAYER[8+0].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_LONG_1:if(8+1 < LineCount)keyPLAYER[8+1].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_LONG_2:if(8+2 < LineCount)keyPLAYER[8+2].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_LONG_3:if(8+3 < LineCount)keyPLAYER[8+3].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_LONG_4:if(8+4 < LineCount)keyPLAYER[8+4].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_LONG_5:if(8+5 < LineCount)keyPLAYER[8+5].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_LONG_6:if(8+6 < LineCount)keyPLAYER[8+6].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;
		                      case KEY_2P_LONG_7:if(8+7 < LineCount)keyPLAYER[8+7].addElement(k);else keyBGM[lineBGM%keyBGM.length].addElement(k);break;

		                      }
	                    }else{
//	                        CSCUtil.print("  ");
	                    }
  	                }
                }
                if(type==KEY_NOTE_BGKEY)lineBGM++;
                else lineBGM=0;
//                println();
            }
//            println("#"+String.valueOf(src,p,line-p));
            return true;
        }
        return false;
    }
    
    //bpm
    private long BPM ;
    private long PerBeatTime;
    private long BeatTick;

    //pos
    private long DivFullNote 	= 192;
    private long F				= 1000;
    
    private long Position = 0;
    private long CurPosition = 0;
    private long PreDeltaTime = 0;
    private long DeltaTick ;
    private long StopDuration = 0;
    
//    private Image curBMP ;
//    private Image curBMPLayer ;
//    private Image curBMPPoor ;

    public void update(long CurPlayTime){
        DeltaTick = CurPlayTime - PreDeltaTime;
        PreDeltaTime = CurPlayTime;
        
        if(BPM!=0){
            BeatTick = 1000000 * 60 / BPM;//ms
//            PosTick = 1000*DeltaTick*F*DivFullNote/(1000000*60*4/BPM) ;
//            BeatPos/Time = F*DivFullNote/(1000000*60*4/BPM) ;
            if(StopDuration>0){
                StopDuration -= 1000*DeltaTick*F*DivFullNote*BPM/(1000000*60*4);
                CurPosition += 0;
            }else if(StopDuration<0){
                CurPosition -= StopDuration;
                CurPosition += 1000*DeltaTick*F*DivFullNote*BPM/(1000000*60*4) ;
                StopDuration = 0;
            }else{
                CurPosition += 1000*DeltaTick*F*DivFullNote*BPM/(1000000*60*4) ;
                StopDuration = 0;
            }
        }else {
            BeatTick = Integer.MAX_VALUE;
            CurPosition += 0 ;
        }
        
        Position = CurPosition/1000 ;
        
        println(
                " CurPlayTime:"+CurPlayTime+
                " DeltaTick:"+DeltaTick+
                " CurPosition:"+CurPosition+
//                " Position:"+Position+
//                " Time:"+CurPlayTime+
                ""
                );
        
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
        
//		control    
        if(setControl(keyBPMBYTE,Position)){
            int bpm = CUtil.stringHexToInt((String)((Key)keyBPMBYTE.elementAt(0)).data,0,2);
            setBPM1000(bpm*1000);
            keyBPMBYTE.removeElementAt(0);
            println("BPM Byte = " + BPM);
        }
        if(setControl(keyBPM,Position)){
            String sbpm = (String)notes_bpm.get((String)((Key)keyBPM.elementAt(0)).data);
            setBPM1000(CUtil.stringDecimalToInt(sbpm,0,sbpm.length(),1000));
            keyBPM.removeElementAt(0);
            println("BPM = " + BPM);
        }
        if(setControl(keySTOP,Position)){
            String sstop = (String)notes_stop.get((String)((Key)keySTOP.elementAt(0)).data);
            StopDuration = F*CUtil.stringDecimalToInt(sstop,0,sstop.length(),1000);
            keySTOP.removeElementAt(0);
            println("STOP = " + StopDuration);
        }
        if(setControl(keyBMP,Position)){
//            if((Image)notes_bmp.get((String)((Key)keyBMP.elementAt(0)).data)!=null){
//                switch(((Key)keyBMP.elementAt(0)).type){
//                case KEY_BMP_BGA:
//                    curBMP = (Image)notes_bmp.get((String)((Key)keyBMP.elementAt(0)).data);
//                    break;
//                case KEY_BMP_LAYER:
//                    curBMPLayer = (Image)notes_bmp.get((String)((Key)keyBMP.elementAt(0)).data);
//                    break;
//                case KEY_BMP_POOR:
//                    curBMPPoor = (Image)notes_bmp.get((String)((Key)keyBMP.elementAt(0)).data);
//                    break;
//                }
//            }
            keyBMP.removeElementAt(0);
//            println("BMP");
        }
        for(int i=keyBGM.length-1;i>=0;i--){
            if(setControl(keyBGM[i],Position)){
//                if(realTimeSound && ((CSoundPlayer)notes_wav.get(((Key)keyBGM[i].elementAt(0)).data))!=null){
//                    ((CSoundPlayer)notes_wav.get(((Key)keyBGM[i].elementAt(0)).data)).replay();
//                }
                
                keyBGM[i].removeElementAt(0);
//                println("BMP");
            }
        }

//        event
        for(int line=keyPLAYER.length-1;line>=0;line--){    
//          test node time out
            if (!keyPLAYER[line].isEmpty() && ((Key)keyPLAYER[line].elementAt(0)).position <= Position - F*DivFullNote/BeatScope){
                losted[line]++;
//                 call back
                Screen.lostNote(line);                
                keyPLAYER[line].removeElementAt(0);
                continue;
            }
//          time to screen pos
            for (int i = 0; i < BuffSize; i++){
                Pos[line][i] = Integer.MAX_VALUE;
                if (i >= keyPLAYER[line].size()) break;
                if( Speed >  F )Speed =  ((int)F - 1) ;
                if( Speed < -F )Speed = -((int)F - 1);
                Pos[line][i]= (int)(   
                        (((Key)keyPLAYER[line].elementAt(i)).position - Position) 
                        / (F - Speed) 
                );
            }
        }
//      call back
        Screen.showNote(Pos);
    }

    private boolean setControl(Vector src,long pos){
        if ( !src.isEmpty() && ((Key)src.elementAt(0)).position <= pos ){
            return true;
        }
        return false;
    }
    
    private void setBPM1000(int bpm){
        BPM = bpm;
//        println("BPM = "+BPM);
    }

    /**
	 * <summary> 
     * @param 	
     * pos[track][node count] = how pix to arrive destnation line
     * - : not arrive
     * + : already arrive
     * zero : in destnation line
     * if key beat the node time less than MGPlayer.BeatScope, means the node will be hitted,
     * in other worlds big than MGPlayer.LostScope, means the node loss
     */
    public int[][] getPos(){
        return Pos;
    }

    /**
     * <summary> key hit
     * @param track track number
     * @return 
     * 
     * >0 : hitted 
     * <0 : not hitted
     */
    public int hit(int track){
//      detect scope
        if(track < LineCount && !keyPLAYER[track].isEmpty()){
//            if(realTimeSound && ((CSoundPlayer)notes_wav.get(((Key)keyPLAYER[track].elementAt(0)).data))!=null){
//                ((CSoundPlayer)notes_wav.get(((Key)keyPLAYER[track].elementAt(0)).data)).replay();
//            }
            // (FullNotePos/BeatScope) / delta (%)
            long delta = Math.abs(((Key)keyPLAYER[track].elementAt(0)).position - Position);
            long acc = 100 * F*DivFullNote / BeatScope / (delta>0?delta:1) ;
            if (Math.abs(((Key)keyPLAYER[track].elementAt(0)).position - Position) <= F*DivFullNote/BeatScope){
//              call back
                Screen.hitNote(track,(int)acc);
                keyPLAYER[track].removeElementAt(0);
                return (int)acc;
            }
        }
        return -1;
    }

    public int hitAuto(int track){
        if(track < LineCount && !keyPLAYER[track].isEmpty()){
            // (FullNotePos/BeatScope) / delta (%)
            long delta = Math.abs(((Key)keyPLAYER[track].elementAt(0)).position - Position);
            long acc = 100 * F*DivFullNote / BeatScope / (delta>0?delta:1) ;
            if (((Key)keyPLAYER[track].elementAt(0)).position <= Position){
//                if(realTimeSound && ((CSoundPlayer)notes_wav.get(((Key)keyPLAYER[track].elementAt(0)).data))!=null){
//                    ((CSoundPlayer)notes_wav.get(((Key)keyPLAYER[track].elementAt(0)).data)).replay();
//                }
//              call back
                Screen.hitNote(track,(int)acc);
                keyPLAYER[track].removeElementAt(0);
                return (int)acc;
            }
        }
        return -1;
    }
    
    /**
     * <summary> check loss node
     * @param
     * @return loss count 
     *      */
    public int lost(int track){
        if (losted[track] > 0){
            int ret = losted[track];
            losted[track] = 0;
            return ret;
        }
        return 0;
    }

    /**
     * <summary> current time has been beatted
     * @return 
     */
    public boolean beatBPM(){
        return isBeat;
    }

    public int getLineCount(){
        return LineCount;
    }

    public int getBPM1000(){
        return (int)BPM;
    }

    
    public int[] getFullNoteLine(){
        
        return null;
    }
    
    
//    public Image getBMP(){
//        return curBMP;
//    }
    
//    public Image getBMPLayer(){ 
//        return curBMPLayer;
//    }
    
//    public Image getBMPPoor(){
//        return curBMPPoor;
//    }
 
}

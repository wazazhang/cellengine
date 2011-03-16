/*
 * WAZA
 * Created on 2006-7-12
 *
 */
package Cell.LibBMSPlayer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import Cell.*;
import System.Drawing.Image;

import Microsoft.DirectX.DirectSound.*;
import Microsoft.DirectX.AudioVideoPlayback.*;

/**
 * @author WAZA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
class Key
{
	long position;
	long length;
	int type;
	boolean downed = false;
	boolean upped = false;
	String data;
}


class SoundPlayer
{

	public SoundPlayer()
	{

	}

	public void setBuffer(Buffer buff)
	{

	}

	public void play()
	{

	}

	public void stop()
	{

	}

	public void close()
	{

	}

	public void setVolume(Volume level)
	{

	}

	public boolean isPlaying()
	{
		return false;
	}

	public boolean isClosed()
	{
		return false;
	}
}

public class BMSPlayer
{
	//    01 = 背景NOTE音。
	//
	//    03 = 0~255（0h~FFh）整数BPM变化。
	//
	//    04 = 改变BGA的图片索引。
	//    06 = 改变POOR的图片索引。
	//    07 = 改变Layer的图片索引。
	//
	//    08 = 改变的BPM索引。表示小数的BPM变化或者大于255的BPM。（前方定义的 #BPMXX）
	//    09 = STOP停止的时间索引。（前方定义的 #STOPXX）
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
	final static private char[] HEADER_PLAYER = { 'P', 'L', 'A', 'Y', 'E', 'R', ' ' };
	final static private char[] HEADER_GENRE = { 'G', 'E', 'N', 'R', 'E', ' ' };
	final static private char[] HEADER_TITLE = { 'T', 'I', 'T', 'L', 'E', ' ' };
	final static private char[] HEADER_ARTIST = { 'A', 'R', 'T', 'I', 'S', 'T', ' ' };
	final static private char[] HEADER_BPM = { 'B', 'P', 'M', ' ' };
	final static private char[] HEADER_PLAYLEVEL = { 'P', 'L', 'A', 'Y', 'L', 'E', 'V', 'E', 'L', ' ' };
	final static private char[] HEADER_RANK = { 'R', 'A', 'N', 'K', ' ' };
	final static private char[] HEADER_TOTAL = { 'T', 'O', 'T', 'A', 'L', ' ' };
	final static private char[] HEADER_VOLWAV = { 'V', 'O', 'L', 'W', 'A', 'V', ' ' };
	final static private char[] HEADER_STAGEFILE = { 'S', 'T', 'A', 'G', 'E', 'F', 'I', 'L', 'E', ' ' };

	final static private char[] NOTE_TYPE_WAV = { 'W', 'A', 'V' };
	final static private char[] NOTE_TYPE_BMP = { 'B', 'M', 'P' };
	final static private char[] NOTE_TYPE_BPM = { 'B', 'P', 'M' };
	final static private char[] NOTE_TYPE_STOP = { 'S', 'T', 'O', 'P' };
	//    final static public char[] NOTE_EXPAND;

	final static private int KEY_NOTE_BGKEY = 1;
	final static private int KEY_BPM_BYTE = 3;
	final static private int KEY_BMP_BGA = 4;
	final static private int KEY_BMP_LAYER = 6;
	final static private int KEY_BMP_POOR = 7;
	final static private int KEY_BPM = 8;
	final static private int KEY_STOP = 9;

	final static private int KEY_1P_1 = 11;
	final static private int KEY_1P_2 = 12;
	final static private int KEY_1P_3 = 13;
	final static private int KEY_1P_4 = 14;
	final static private int KEY_1P_5 = 15;
	final static private int KEY_1P_SC = 16;
	final static private int KEY_1P_6 = 18;
	final static private int KEY_1P_7 = 19;

	final static private int KEY_2P_1 = 21;
	final static private int KEY_2P_2 = 22;
	final static private int KEY_2P_3 = 23;
	final static private int KEY_2P_4 = 24;
	final static private int KEY_2P_5 = 25;
	final static private int KEY_2P_SC = 26;
	final static private int KEY_2P_6 = 28;
	final static private int KEY_2P_7 = 29;

	final static private int KEY_1P_LONG_1 = 51;
	final static private int KEY_1P_LONG_2 = 52;
	final static private int KEY_1P_LONG_3 = 53;
	final static private int KEY_1P_LONG_4 = 54;
	final static private int KEY_1P_LONG_5 = 55;
	final static private int KEY_1P_LONG_SC = 56;
	final static private int KEY_1P_LONG_6 = 58;
	final static private int KEY_1P_LONG_7 = 59;

	final static private int KEY_2P_LONG_1 = 61;
	final static private int KEY_2P_LONG_2 = 62;
	final static private int KEY_2P_LONG_3 = 63;
	final static private int KEY_2P_LONG_4 = 64;
	final static private int KEY_2P_LONG_5 = 65;
	final static private int KEY_2P_LONG_SC = 66;
	final static private int KEY_2P_LONG_6 = 68;
	final static private int KEY_2P_LONG_7 = 69;

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

	private Hashtable notes_wav = new Hashtable();
	private Hashtable notes_bmp = new Hashtable();
	private Hashtable notes_bpm = new Hashtable();
	private Hashtable notes_stop = new Hashtable();
	//private Hashtable notes_expand	= new Hashtable();
	private Hashtable notes_snd = new Hashtable();

	private Vector keyBGM[];
	private Vector keyPLAYER[];
	private Vector keyPLAYER_LONG[];
	private Vector keyBPMBYTE = new Vector();
	private Vector keyBPM = new Vector();
	private Vector keySTOP = new Vector();
	private Vector keyBMP = new Vector();
	private Vector keyBMPLAYER = new Vector();
	private Vector keyBMPPOOR = new Vector();

	private int maxSoundMix = 1;
	private SoundPlayer soundman[];

	//----------------------------------------------------------------------------------


	private String dir;

	//    private Image[] image;
	//	private Player[] soundman;
	//	private VolumeControl volumeControl;

	private MGPlayerListener Screen;

	private int LineCount;

	private int LineOffset = 0;

	//    /***/
	//    public int EndTime ;

	/**是否实时播放声音，要求硬件达到同时播放LineCount个轨道*/
	public boolean realTimeKeySound = true;
	/**是否实时播放声音，要求硬件达到同时播放32个轨道*/
	public boolean realTimeBGMSound = true;

	/**屏幕高度*/
	public int ScreenHeight;

	/**速度*/
	private int Speed;

	/**按键的判定范围，单位 x分之一拍 */
	public int BeatScope = 16;//delta default 16

	private int[][] Pos;
	private int[][] PosLong;

	private int[] losted;

	/**自动演奏*/
	//public boolean AutoPlay = false;

	private boolean isBeat = false;


	private int VolumeBGM = 100;
	private int VolumeKEY = 100;
	//-------------------------------------------------------------------------------------------------
	static private Device deviceSound;
	static private BufferDescription bufferDesc;
	static private EffectDescription[] effectsDesc;

	static public void setDevice(Device device,BufferDescription buffer_desc,EffectDescription[] effects)
	{
		deviceSound = device;
		bufferDesc = buffer_desc;
		effectsDesc = effects;
	}
	//-------------------------------------------------------------------------------------------------
	public BMSPlayer(
			MGPlayerListener screen,
			String bmsFile,
			int lineCount
			)
	{
		try
		{

			dir = "";
			if (bmsFile.Contains("\\"))
			{
				dir = bmsFile.substring(0, bmsFile.lastIndexOf('\\') + 1);
			}
			if (bmsFile.Contains("/"))
			{
				dir = bmsFile.substring(0, bmsFile.lastIndexOf('/') + 1);
			}

			Util.println("Song dir = " + dir);

			Screen = screen;

			LineCount = lineCount;
			ScreenHeight = 192;

			Pos = new int[LineCount][4 * ( int ) DivFullNote];
			PosLong = new int[LineCount][4 * ( int ) DivFullNote];

			losted = new int[LineCount];

			setBPM1000(120000);
			Speed = 10;

			soundman = new SoundPlayer[maxSoundMix];
			for (int i = soundman.length - 1; i >= 0; i--)
			{
				soundman[i] = new SoundPlayer();
			}


			keyBGM = new Vector[32];
			for (int i = keyBGM.length - 1; i >= 0; i--)
			{
				keyBGM[i] = new Vector();
			}


			keyPLAYER = new Vector[LineCount];
			keyPLAYER_LONG = new Vector[LineCount];
			for (int i = keyPLAYER.length - 1; i >= 0; i--)
			{
				keyPLAYER[i] = new Vector();
				keyPLAYER_LONG[i] = new Vector();
			}

			Util.println("BMS file start !");
			String data = new String(Util.loadFile(bmsFile));
			char[] bms = data.toCharArray();
			//System.out.print(bms);
			int p = 0;
			boolean headerOK = false;
			boolean dataOK = false;
			while (p < bms.length)
			{
				switch (bms[p++])
				{
					case '#':
						int line = Util.charArrayIndexOf(bms, '\r', p);
						for (int i = p; i < line; i++)
						{
							// header
							if (headerOK == false)
							{
								if ((player == null) && (player = setHeader(bms, p, line, HEADER_PLAYER)) != null) break;
								if ((genre == null) && (genre = setHeader(bms, p, line, HEADER_GENRE)) != null) break;
								if ((title == null) && (title = setHeader(bms, p, line, HEADER_TITLE)) != null) break;
								if ((artist == null) && (artist = setHeader(bms, p, line, HEADER_ARTIST)) != null) break;
								if ((bpm == null) && (bpm = setHeader(bms, p, line, HEADER_BPM)) != null) break;
								if ((playlevel == null) && (playlevel = setHeader(bms, p, line, HEADER_PLAYLEVEL)) != null) break;
								if ((rank == null) && (rank = setHeader(bms, p, line, HEADER_RANK)) != null) break;
								if ((total == null) && (total = setHeader(bms, p, line, HEADER_TOTAL)) != null) break;
								if ((volwav == null) && (volwav = setHeader(bms, p, line, HEADER_VOLWAV)) != null) break;
								if ((stagefile == null) && (stagefile = setHeader(bms, p, line, HEADER_STAGEFILE)) != null)
								{
									//                                try {
									//                                    if(Util.stringIsWordNum(stagefile,0,stagefile.length())>0){
									//                                        Image img = Image.createImage(dir+stagefile);
									////                                      Image img = Image.createImage(1, 1);
									//                                      Util.println("Load Stage Image " + dir+stagefile);
									//                                      if(curBMP==null){
									//                                          curBMP = img;
									//                                      }
									//                                    }
									//                                } catch (IOException e1) {
									//                                    e1.printStackTrace();
									//                                }
									break;
								}

								String[] kv = null;
								if ((kv = setNotes(bms, p, line, NOTE_TYPE_WAV)) != null)
								{
									String wav = dir + kv[1];
									Util.println("Load Sound " + wav);

									Util.println("Ext = " + System.IO.Path.GetExtension(wav));

									if (System.IO.Path.GetExtension(wav).Contains(".mid")  ||
										System.IO.Path.GetExtension(wav).Contains(".midi")||
										System.IO.Path.GetExtension(wav).Contains(".MID" )||
										System.IO.Path.GetExtension(wav) .Contains(".MIDI")||
										System.IO.Path.GetExtension(wav).Contains( ".mp3" )||
										System.IO.Path.GetExtension(wav).Contains( ".MP3" )||
										System.IO.Path.GetExtension(wav) .Contains( ".wma" )||
										System.IO.Path.GetExtension(wav) .Contains(".WMA"))
									{
										setSoundAudio(kv[0], wav);
									}
									else
									{
									    setSound(kv[0], wav);
									}
									

									break;
								}
								if ((kv = setNotes(bms, p, line, NOTE_TYPE_BMP)) != null)
								{
									String image = dir + kv[1];
									Util.println("Load Image " + image);

									try
									{
										Image img = Image.FromFile(image);
										notes_bmp.put(kv[0], img);
										if (curBMP == null)
										{
											curBMP = img;
										}
									}
									catch (Exception e1)
									{
										Util.println(e1.get_Message() + ":" + image);
									}

									break;
								}
								if ((kv = setNotes(bms, p, line, NOTE_TYPE_BPM)) != null)
								{
									try
									{
										long nbpm = Util.stringDecimalToInt(kv[1], 0, kv[1].length(), 1000);
										notes_bpm.put(kv[0], new Long(nbpm));
										Util.println("BPM Note " + kv[0] + " " + nbpm);
									}
									catch (RuntimeException e1)
									{
										e1.printStackTrace();
									}
									break;
								}
								if ((kv = setNotes(bms, p, line, NOTE_TYPE_STOP)) != null)
								{
									try
									{
										long nstop = Util.stringDecimalToInt(kv[1], 0, kv[1].length(), 1000);
										notes_stop.put(kv[0], new Long(nstop));
										Util.println("STOP Note " + kv[0] + " " + nstop);
									}
									catch (RuntimeException e1)
									{
										e1.printStackTrace();
									}
									break;
								}
							}
							//data
							if (dataOK == false)
							{
								if (setKey(bms, p, line, keyPLAYER))
								{
									headerOK = true;
									break;
								}
							}
						}
						p = line;
						break;
					//            case '%':
					//                break;
					//            case '\n':
					//                Util.println("ln");
					//                break;
					//            case '\r':
					//              Util.println("rt");
					//              break;
					default:
						break;
				}
			}

			Util.println("BMS file end ! process " + p + "(bytes)");

			setBPM1000(Util.stringDecimalToInt(bpm, 0, bpm.length(), 1000));
			Util.println("BPM = " + BPM);

		}
		catch (RuntimeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initSound();
	}





	private String setHeader(char[] src, int p, int line, char[] HEADER)
	{
		if (Util.charArrayCMP(
				src, p, HEADER.length,
				HEADER, 0, HEADER.length))
		{
			int blank = p + HEADER.length;
			while ((src[blank] == ' ' || src[blank] == '\t') && blank < line)
			{
				blank++;
			}
			String des = String.valueOf(src, blank, line - blank);
			//            Util.println("#" + String.valueOf(HEADER) + des);
			return des;
		}
		return null;
	}

	private String[] setNotes(char[] src, int p, int line, char[] NOTE_TYPE)
	{
		if (line < p + NOTE_TYPE.length + 3) return null;
		if (Util.charArrayCMP(
				src, p, NOTE_TYPE.length,
				NOTE_TYPE, 0, NOTE_TYPE.length))
		{
			String kv[] = new String[2];
			kv[0] = String.valueOf(src, p + NOTE_TYPE.length, 2);
			int blank = p + NOTE_TYPE.length + 3;
			while ((src[blank] == ' ' || src[blank] == '\t') && blank < line)
			{
				blank++;
			}
			kv[1] = String.valueOf(src, blank, line - blank);
			//            Util.println("#" + String.valueOf(NOTE_TYPE) + kv[0] + " " + kv[1]);
			return kv;
		}
		return null;
	}

	private int lineCount = 0;
	private int lineBGM = 0;
	
	private boolean setKey(char[] src, int p, int line, Vector[] des)
	{
		if (Util.charArrayIsDigit(src, p, 5) == 5)
		{
			int m = Util.charArrayIndexOf(src, ':', p + 5, line - p);
			if (m > -1)
			{
				int type = Util.charArrayDigitToInt(src, p + 3, 2);
				if (lineCount < Util.charArrayDigitToInt(src, p, 3))
				{
					lineCount = Util.charArrayDigitToInt(src, p, 3);
					lineBGM = 0;
				}
				//                Util.print("Type"+src[p+3]+src[p+4]+":");
				int blank = m + 1;
				while ((src[blank] == ' ' || src[blank] == '\t') && blank < line)
				{
					blank++;
				}
				int len = Util.charArrayIsWordNum(src, blank, line - blank);
				if (len >= 2)
				{
					len = len - len % 2;
					for (int d = blank; d < blank + len; d += 2)
					{
						if (src[d + 0] != '0' || src[d + 1] != '0')
						{
							Key k = new Key();
							k.position =
								DivFullNote * F * lineCount +
								DivFullNote * F * (d - blank) / len;
							k.type = type;
							k.data = String.valueOf(src, d, 2);
							k.length = 0;
							
							//	                        Util.print(
							////	                                " " + Util.charArrayDigitToInt(src,p+0,3) +
							////	                                " " + (d-blank) +
							//	                                k.data
							//	                                );
							switch (k.type)
							{
								case KEY_NOTE_BGKEY:
									keyBGM[lineBGM % keyBGM.length].addElement(k);
									break;
								case KEY_BPM_BYTE:
									keyBPMBYTE.addElement(k);
									break;
								case KEY_BMP_BGA:
									keyBMP.addElement(k);
									break;
								case KEY_BMP_LAYER:
									keyBMPLAYER.addElement(k);
									break;
								case KEY_BMP_POOR:
									keyBMPPOOR.addElement(k);
									break;
								case KEY_BPM:
									keyBPM.addElement(k);
									break;
								case KEY_STOP:
									keySTOP.addElement(k);
									break;
								// 1p 0~7
								case KEY_1P_SC: if (0 + 0 < LineCount) keyPLAYER[0 + 0].addElement(k); break;
								case KEY_1P_1: if (0 + 1 < LineCount) keyPLAYER[0 + 1].addElement(k); break;
								case KEY_1P_2: if (0 + 2 < LineCount) keyPLAYER[0 + 2].addElement(k); break;
								case KEY_1P_3: if (0 + 3 < LineCount) keyPLAYER[0 + 3].addElement(k); break;
								case KEY_1P_4: if (0 + 4 < LineCount) keyPLAYER[0 + 4].addElement(k); break;
								case KEY_1P_5: if (0 + 5 < LineCount) keyPLAYER[0 + 5].addElement(k); break;
								case KEY_1P_6: if (0 + 6 < LineCount) keyPLAYER[0 + 6].addElement(k); break;
								case KEY_1P_7: if (0 + 7 < LineCount) keyPLAYER[0 + 7].addElement(k); break;

								case KEY_1P_LONG_SC: if (0 + 0 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 0); } break;
								case KEY_1P_LONG_1: if (0 + 1 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 1); } break;
								case KEY_1P_LONG_2: if (0 + 2 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 2); } break;
								case KEY_1P_LONG_3: if (0 + 3 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 3); } break;
								case KEY_1P_LONG_4: if (0 + 4 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 4); } break;
								case KEY_1P_LONG_5: if (0 + 5 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 5); } break;
								case KEY_1P_LONG_6: if (0 + 6 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 6); } break;
								case KEY_1P_LONG_7: if (0 + 7 < LineCount) { setLongKey(keyPLAYER_LONG, k, 0 + 7); } break;
								// 2p 8~15

								case KEY_2P_1: if (8 + 0 < LineCount) keyPLAYER[8 + 0].addElement(k); break;
								case KEY_2P_2: if (8 + 1 < LineCount) keyPLAYER[8 + 1].addElement(k); break;
								case KEY_2P_3: if (8 + 2 < LineCount) keyPLAYER[8 + 2].addElement(k); break;
								case KEY_2P_4: if (8 + 3 < LineCount) keyPLAYER[8 + 3].addElement(k); break;
								case KEY_2P_5: if (8 + 4 < LineCount) keyPLAYER[8 + 4].addElement(k); break;
								case KEY_2P_6: if (8 + 5 < LineCount) keyPLAYER[8 + 5].addElement(k); break;
								case KEY_2P_7: if (8 + 6 < LineCount) keyPLAYER[8 + 6].addElement(k); break;
								case KEY_2P_SC: if (8 + 7 < LineCount) keyPLAYER[8 + 7].addElement(k); break;


								case KEY_2P_LONG_1: if (8 + 0 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 0); } break;
								case KEY_2P_LONG_2: if (8 + 1 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 1); } break;
								case KEY_2P_LONG_3: if (8 + 2 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 2); } break;
								case KEY_2P_LONG_4: if (8 + 3 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 3); } break;
								case KEY_2P_LONG_5: if (8 + 4 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 4); } break;
								case KEY_2P_LONG_6: if (8 + 5 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 5); } break;
								case KEY_2P_LONG_7: if (8 + 6 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 6); } break;
								case KEY_2P_LONG_SC: if (8 + 7 < LineCount) { setLongKey(keyPLAYER_LONG, k, 8 + 7); } break;
								default:
									break;
							}
						}
						else
						{
							//	                        Util.print("  ");
						}
					}
				}
				if (type == KEY_NOTE_BGKEY) lineBGM++;
				else lineBGM = 0;
				//                Util.println();
			}
			//            Util.println("#"+String.valueOf(src,p,line-p));
			return true;
		}
		return false;
	}

	private int[] Single = new int[]{
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
	};
	private void setLongKey(Vector[] kl , Key k ,int line)
	{
		if (Single[line] % 2 == 0)
		{
			keyPLAYER_LONG[line].addElement(k);
		}
		else
		{
			((Key)keyPLAYER_LONG[line].lastElement()).length = k.position - ((Key)keyPLAYER_LONG[line].lastElement()).position;
			//Util.println("Length = " + ((Key)keyPLAYER_LONG[line].lastElement()).length);
		}

		Single[line]++; 
	}

	private void initSound()
	{
	}

	

	private void setSoundAudio(String K_name, String V_wav)
	{
		try{
			Audio audio = new Audio(V_wav);
			audio.Play();
			audio.Pause();
			audio.set_CurrentPosition(0);
			notes_snd.put(K_name, audio);
		}catch(Microsoft.DirectX.DirectXException err){
			Util.println(err.get_Message() + ":" + V_wav);
		}
		
	}

	private void playSoundAudio(Vector sequence, int index, int volume)
	{
		Audio note = ((Audio)notes_snd.get(((Key)sequence.elementAt(index)).data));
		if (note != null)
		{
			note.set_Volume(volume);
			note.Play();
		}
	}
	private void setSound(String K_name, String V_wav)
	{
		try
		{
			if (Util.stringIsWordNum(V_wav, 0, V_wav.length()) > 0)
			{
				//Create and setup the buffer for playing the sound.
				//SecondaryBuffer buffer = new SecondaryBuffer(V_wav, bufferDesc, deviceSound);
				System.IO.FileStream fs = new System.IO.FileStream(V_wav, System.IO.FileMode.Open);
				ubyte data[] = new ubyte[(int)fs.get_Length()];
				fs.Read(data, 0, data.length);
				fs.Close();

				System.IO.MemoryStream ms = new System.IO.MemoryStream(data, false);

				SecondaryBuffer buffer = new SecondaryBuffer(
					ms,
					(int)ms.get_Length(),
					bufferDesc,
					deviceSound);

				//buffer.Read(0,ms,(int)ms.get_Length(),LockFlag.EntireBuffer);
				//buffer.Write(0, ms, (int)ms.get_Length(), LockFlag.EntireBuffer);

				if (effectsDesc != null)
				{
					try
					{
						buffer.SetEffects(effectsDesc);
					}
					catch (EffectsUnavailableException e1)
					{
					}
				}
				notes_wav.put(K_name, buffer);
			}
		}
		catch (BufferTooSmallException e1)
		{
			Util.println(e1.get_Message() + ":" + V_wav);
		}
		catch (BadFormatException e2)
		{
			Util.println(e2.get_Message() + ":" + V_wav);
		}
		catch (BadSendBufferGuidException e3)
		{
			Util.println(e3.get_Message() + ":" + V_wav);
		}
		catch (BufferLostException e4)
		{
			Util.println(e4.get_Message() + ":" + V_wav);
		}
		catch (SoundException e5)
		{
			Util.println(e5.get_Message() + ":" + V_wav);
		}
		catch (Exception err)
		{
			Util.println(err.get_Message() + ":" + V_wav);
		}
	}

	private void playSound(Vector sequence, int index, int volume)
	{
		SecondaryBuffer note = ((SecondaryBuffer)notes_wav.get(((Key)sequence.elementAt(index)).data));
		if (note != null)
		{
			note.set_Volume(volume);
			note.SetCurrentPosition(0);
			note.Play(0, BufferPlayFlags.Default);
		}

	}

	public void CloseSound()
	{
		java.util.Enumeration enu = notes_wav.elements();
		while (enu.hasMoreElements())
		{
			SecondaryBuffer temp = ((SecondaryBuffer)enu.nextElement());
			if (temp != null)
			{
				temp.Stop();
				temp.Dispose();
			}
		}
		enu = notes_snd.elements();
		while (enu.hasMoreElements())
		{
			Audio temp = ((Audio)enu.nextElement());
			if (temp != null)
			{
				temp.Stop();
				temp.Dispose();
			}
		}
	}

	//bpm
	private long BPM;
	private long PerBeatTime;
	private long BeatTick;
	private int BeatCount = 0;

	//pos
	private long DivFullNote = 192;
	private long F = 1000;

	private long Position = 0;
	private long CurPosition = 0;
	private long PreDeltaTime = 0;
	private long DeltaTick;
	private long StopDuration = 0;

	private int FullNoteCount = 0;



	private Image curBMP;
	private Image curBMPLayer;
	private Image curBMPPoor;
	/**
     * <summary> 主循环中每周期调用一次。
     * @param	当前歌曲播放时间
     */
	public void update(long CurPlayTime)
	{
		DeltaTick = CurPlayTime - PreDeltaTime;
		PreDeltaTime = CurPlayTime;

		if (BPM != 0)
		{
			BeatTick = 1000000 * 60 / BPM;//ms
			//            PosTick = 1000*DeltaTick*F*DivFullNote/(1000000*60*4/BPM) ;
			//            BeatPos/Time = F*DivFullNote/(1000000*60*4/BPM) ;
			if (StopDuration > 0)
			{
				StopDuration -= 1000 * DeltaTick * F * DivFullNote * BPM / (1000000 * 60 * 4);
				CurPosition += 0;
			}
			else if (StopDuration < 0)
			{
				CurPosition -= StopDuration;
				CurPosition += 1000 * DeltaTick * F * DivFullNote * BPM / (1000000 * 60 * 4);
				StopDuration = 0;
			}
			else
			{
				CurPosition += 1000 * DeltaTick * F * DivFullNote * BPM / (1000000 * 60 * 4);
				StopDuration = 0;
			}
		}
		else
		{
			BeatTick = Integer.MAX_VALUE;
			CurPosition += 0;
		}

		Position = CurPosition / 1000;
		FullNoteCount = (int)(Position / (DivFullNote * F));
		BeatCount = (int)(Position * 4 / (DivFullNote * F));

		//        Util.println(
		////                " CurPlayTime:"+CurPlayTime+
		//                " DeltaTick:"+DeltaTick+
		//                " CurPosition:"+CurPosition+
		////                " Position:"+Position+
		////                " Time:"+CurPlayTime+
		//                ""
		//                );

		//      bpm
		if (CurPlayTime - PerBeatTime >= BeatTick)
		{
			isBeat = true;
			PerBeatTime = CurPlayTime;
			// call back
			Screen.beatBPM(BeatCount);
		}
		else
		{
			isBeat = false;
		}



		//		control    
		// bpm byte
		while (tryControl(keyBPMBYTE, Position))
		{
			int bpm = Util.stringHexToInt((String)((Key)keyBPMBYTE.elementAt(0)).data, 0, 2);
			setBPM1000(bpm * 1000);
			keyBPMBYTE.removeElementAt(0);
			Util.println("BPM Byte = " + BPM);
		}
		//bpm 
		while (tryControl(keyBPM, Position))
		{
			Long sbpm = (Long)notes_bpm.get((String)((Key)keyBPM.elementAt(0)).data);
			setBPM1000(sbpm.intValue());
			keyBPM.removeElementAt(0);
			Util.println("BPM = " + BPM);
		}
		//stop
		while (tryControl(keySTOP, Position))
		{
			Long sstop = (Long)notes_stop.get((String)((Key)keySTOP.elementAt(0)).data);
			StopDuration = F * sstop.intValue();
			keySTOP.removeElementAt(0);
			Util.println("STOP = " + StopDuration);
		}
		// img  bg
		while (tryControl(keyBMP, Position))
		{
			if ((Image)notes_bmp.get((String)((Key)keyBMP.elementAt(0)).data) != null)
			{
				curBMP = (Image)notes_bmp.get((String)((Key)keyBMP.elementAt(0)).data);
					
			}
			keyBMP.removeElementAt(0);
		}
		// img layer
		while (tryControl(keyBMPLAYER, Position))
		{
			if ((Image)notes_bmp.get((String)((Key)keyBMPLAYER.elementAt(0)).data) != null)
			{
				curBMPLayer = (Image)notes_bmp.get((String)((Key)keyBMPLAYER.elementAt(0)).data);
			}
			keyBMPLAYER.removeElementAt(0);
		}
		// img poor
		while (tryControl(keyBMPPOOR, Position))
		{
			if ((Image)notes_bmp.get((String)((Key)keyBMPPOOR.elementAt(0)).data) != null)
			{
				curBMPPoor = (Image)notes_bmp.get((String)((Key)keyBMPPOOR.elementAt(0)).data);
			}
			keyBMPPOOR.removeElementAt(0);
		}
		// bgm key
		for (int i = keyBGM.length - 1; i >= 0; i--)
		{
			while (tryControl(keyBGM[i], Position))
			{
				if (realTimeBGMSound)
				{
					playSound(keyBGM[i], 0, VolumeBGM);
					playSoundAudio(keyBGM[i], 0, VolumeBGM);
				}
				keyBGM[i].removeElementAt(0);
				//Util.println("BGM");
			}
		}


		//  计算音符丢失
		for (int line = keyPLAYER.length - 1; line >= 0; line--)
		{
			while (!keyPLAYER[line].isEmpty() &&
				((Key)keyPLAYER[line].elementAt(0)).position <= Position - F * DivFullNote / BeatScope)
			{
				losted[line]++;
				keyPLAYER[line].removeElementAt(0);
				//call back
				Screen.lostNote(line);
				//Util.println("Lost at : " + line);
			}

			for (int i = 0; i < keyPLAYER_LONG[line].size();i++ )
			{
				if (((Key)keyPLAYER_LONG[line].elementAt(i)).position <= Position - F * DivFullNote / BeatScope)
				{
					if (((Key)keyPLAYER_LONG[line].elementAt(i)).downed == false)
					{
						losted[line]++;
						keyPLAYER_LONG[line].removeElementAt(i);
						i--;
						//call back
						Screen.lostNote(line);
						//Util.println("Lost long at : " + line);
					}
				}
				else
				{
					break;
				}
			}

			while (!keyPLAYER_LONG[line].isEmpty() && 
				((Key)keyPLAYER_LONG[line].elementAt(0)).position + ((Key)keyPLAYER_LONG[line].elementAt(0)).length <= Position - F * DivFullNote / BeatScope)
			{
				losted[line]++;
				keyPLAYER_LONG[line].removeElementAt(0);
				//call back
				Screen.lostNote(line);
				//Util.println("Lost long at : " + line);
			}
		}

		//Util.print("Key[" + 0 + "] Capacity = " + keyPLAYER[0].capacity());
		//Util.println(" Size = " + keyPLAYER[0].size());

		//          根据时间计算音符的屏幕坐标
		for (int line = keyPLAYER.length - 1; line >= 0; line--)
		{
			for (int i = 0; i < Pos[line].length; i++)
			{
				Pos[line][i] = ScreenHeight + 1;
				if (i < keyPLAYER[line].size())
				{
					Pos[line][i] = (int)((((Key)keyPLAYER[line].elementAt(i)).position - Position) / F * Speed / 10);
				}
				if (Pos[line][i] > ScreenHeight)
				{
					break;
				}
			}

			for (int i = 0; i < PosLong[line].length; i+=2)
			{
				PosLong[line][i + 0] = ScreenHeight + 1;
				PosLong[line][i + 1] = 0;

				if (i / 2 < keyPLAYER_LONG[line].size())
				{
					PosLong[line][i + 0] = (int)((((Key)keyPLAYER_LONG[line].elementAt(i / 2)).position - Position) / F * Speed / 10);
					PosLong[line][i + 1] = (int)((((Key)keyPLAYER_LONG[line].elementAt(i / 2)).length) / F * Speed / 10);
					//Util.println("Long at " + PosLong[line][i + 0]);
				}
				if (PosLong[line][i + 0] > ScreenHeight)
				{
					break;
				}
			}
		}
		//      call back
		Screen.showNote(Pos);
	}

	private boolean tryControl(Vector src, long pos)
	{
		if (!src.isEmpty() && ((Key)src.elementAt(0)).position <= pos)
		{
			return true;
		}
		return false;
	}

	private void setBPM1000(int bpm)
	{
		BPM = bpm;
		//        Util.println("BPM = "+BPM);
	}


	/**
	 * <summary> 显示音符位置。
     * @param 	
     * pos[游戏轨道数][同时显示音符数]：距离目标点的距离，单位像素。
     * 负数：已经超过。
     * 正数：还未到。
     * 零：音符刚好处在原点。
     * 如果在按下指定按钮的时候，该值的绝对值小于或等于MGPlayer.BeatScope，那么该音符就被HIT。
     * 如果该值大于MGPlayer.LostScope，那么该音符就被丢失。
     */
	public int[][] getPos()
	{
		return Pos;
	}

	/**
	 * <summary> 显示长音符位置。
     * @param 	
     * pos[游戏轨道数][同时显示音符数/2]：位置信息，单位象素。
	 * 奇数：距离目标点的距离，单位像素。
	 * 偶数：长度。
     * 负数：已经超过。
     * 正数：还未到。
     * 零：音符刚好处在原点。
     * 如果在按下指定按钮的时候，该值的绝对值小于或等于MGPlayer.BeatScope，那么该音符就被HIT。
     * 如果该值大于MGPlayer.LostScope，那么该音符就被丢失。
     */
	public int[][] getPosLong()
	{
		return PosLong;
	}


	/**
     * <summary> 指定轨道的按钮按下。
     * @param track 游戏轨道号，小于LineCount。
     * @return 如果大于等于0：误差。小于0：没有HIT的音符。
     */
	public int keyDown(int track)
	{
		//      检测当前音符按下范围
		if (track < LineCount && !keyPLAYER[track].isEmpty())
		{
			if (realTimeKeySound)
			{
				playSound(keyPLAYER[track], 0, VolumeKEY);
				playSoundAudio(keyPLAYER[track], 0, VolumeKEY);
			}
			if (Math.abs(((Key)keyPLAYER[track].elementAt(0)).position - Position) <= F * DivFullNote / BeatScope)
			{
				// (beatscope - delta) / beatscope ;
				// beatscope/beatscope - delta/beatscope
				long delta = Math.abs(((Key)keyPLAYER[track].elementAt(0)).position - Position);
				long acc = 100 - 100 * delta * BeatScope / (F * DivFullNote);
				//              call back
				Screen.hitNote(track, (int)acc);
				keyPLAYER[track].removeElementAt(0);
				return (int)acc;
			}
		}
		//      检测当前音符按下范围
		if (track < LineCount && !keyPLAYER_LONG[track].isEmpty())
		{
			if (((Key)keyPLAYER_LONG[track].elementAt(0)).downed == false&&
				((Key)keyPLAYER_LONG[track].elementAt(0)).upped == false)
			{
				if (realTimeKeySound)
				{
					playSound(keyPLAYER_LONG[track], 0, VolumeKEY);
					playSoundAudio(keyPLAYER_LONG[track], 0, VolumeKEY);
				}
				if (Math.abs(((Key)keyPLAYER_LONG[track].elementAt(0)).position - Position) <= F * DivFullNote / BeatScope)
				{
					((Key)keyPLAYER_LONG[track].elementAt(0)).downed = true;
					// (beatscope - delta) / beatscope ;
					// beatscope/beatscope - delta/beatscope
					long delta = Math.abs(((Key)keyPLAYER_LONG[track].elementAt(0)).position - Position);
					long acc = 100 - 100 * delta * BeatScope / (F * DivFullNote);
					//              call back
					Screen.hitNote(track, (int)acc);
					//keyPLAYER_LONG[track].removeElementAt(0);
					
					return (int)acc;
				}
			}
			
		}
		return -1;
		
	}

	public int keyUp(int track)
	{
		//      检测当前音符按下范围
		if (track < LineCount && !keyPLAYER_LONG[track].isEmpty())
		{
			if (((Key)keyPLAYER_LONG[track].elementAt(0)).upped == false &&
				((Key)keyPLAYER_LONG[track].elementAt(0)).downed == true)
			{
				if (Math.abs(((Key)keyPLAYER_LONG[track].elementAt(0)).position + ((Key)keyPLAYER_LONG[track].elementAt(0)).length - Position) <= F * DivFullNote / BeatScope)
				{
					// (beatscope - delta) / beatscope ;
					// beatscope/beatscope - delta/beatscope
					long delta = Math.abs(((Key)keyPLAYER_LONG[track].elementAt(0)).position + ((Key)keyPLAYER_LONG[track].elementAt(0)).length - Position);
					long acc = 100 - 100 * delta * BeatScope / (F * DivFullNote);
					// call back
					Screen.releaseNote(track, (int)acc);
					keyPLAYER_LONG[track].removeElementAt(0);
					return (int)acc;
				}
				else
				{
					losted[track]++;
					keyPLAYER_LONG[track].removeElementAt(0);
					//call back
					Screen.lostNote(track);
					return (int)-1;
				}
			}
		}
		return -1;
	}

	public int keyHold(int track)
	{
		//      检测当前音符按下范围
		if (track < LineCount && !keyPLAYER_LONG[track].isEmpty())
		{
			if (((Key)keyPLAYER_LONG[track].elementAt(0)).downed == true && 
				((Key)keyPLAYER_LONG[track].elementAt(0)).upped == false)
			{
				// call back
				Screen.holdNote(track);
				return 0;
			}
		}
		return -1;
	}




	/**
     * <summary> 自动检测指定轨道的按钮按下。
     * @param track 游戏轨道号，小于LineCount。
     * @return 如果大于等于0：误差。小于0：没有HIT的音符。
     */
	public void hitAuto(int track)
	{
		//      检测当前音符按下范围 key
		if (track < LineCount)
		{
			while (!keyPLAYER[track].isEmpty())
			{
				if (((Key)keyPLAYER[track].elementAt(0)).position <= Position)
				{
					if (realTimeKeySound)
					{
						playSound(keyPLAYER[track], 0, VolumeKEY);
						playSoundAudio(keyPLAYER[track], 0, VolumeKEY);
					}
					long delta = Math.abs(((Key)keyPLAYER[track].elementAt(0)).position - Position);
					long acc = 100 - 100 * delta * BeatScope / (F * DivFullNote);
					//              call back
					Screen.hitNote(track, (int)acc);
					keyPLAYER[track].removeElementAt(0);
				}
				else
				{
					break;
				}
			}
			//      检测当前音符按下范围 key long
			if (!keyPLAYER_LONG[track].isEmpty())
			{
				if (((Key)keyPLAYER_LONG[track].elementAt(0)).downed == false &&
					((Key)keyPLAYER_LONG[track].elementAt(0)).upped == false)
				{
					if (((Key)keyPLAYER_LONG[track].elementAt(0)).position <= Position)
					{
						if (realTimeKeySound)
						{
							playSound(keyPLAYER_LONG[track], 0, VolumeKEY);
							playSoundAudio(keyPLAYER_LONG[track], 0, VolumeKEY);
						}
						((Key)keyPLAYER_LONG[track].elementAt(0)).downed = true;
						// (beatscope - delta) / beatscope ;
						// beatscope/beatscope - delta/beatscope
						long delta = Math.abs(((Key)keyPLAYER_LONG[track].elementAt(0)).position - Position);
						long acc = 100 - 100 * delta * BeatScope / (F * DivFullNote);
						//              call back
						Screen.hitNote(track, (int)acc);
						//keyPLAYER_LONG[track].removeElementAt(0);
					}
				}
			}
			//      检测当前音符按下范围 key long hold
			if (!keyPLAYER_LONG[track].isEmpty())
			{
				if (((Key)keyPLAYER_LONG[track].elementAt(0)).downed == true &&
					((Key)keyPLAYER_LONG[track].elementAt(0)).upped == false)
				{
					// call back
					Screen.holdNote(track);
				}
			}
			//      检测当前音符按下范围 key long up
			if (!keyPLAYER_LONG[track].isEmpty())
			{
				if (((Key)keyPLAYER_LONG[track].elementAt(0)).upped == false &&
					((Key)keyPLAYER_LONG[track].elementAt(0)).downed == true)
				{
					if (((Key)keyPLAYER_LONG[track].elementAt(0)).position + ((Key)keyPLAYER_LONG[track].elementAt(0)).length <= Position)
					{
						// (beatscope - delta) / beatscope ;
						// beatscope/beatscope - delta/beatscope
						long delta = Math.abs(((Key)keyPLAYER_LONG[track].elementAt(0)).position + ((Key)keyPLAYER_LONG[track].elementAt(0)).length - Position);
						long acc = 100 - 100 * delta * BeatScope / (F * DivFullNote);
						// call back
						Screen.releaseNote(track, (int)acc);
						keyPLAYER_LONG[track].removeElementAt(0);
					}
				}
			}
		}
		
	}

	/**
     * <summary> 查寻音符丢失。
     * @param 哪一个游戏轨道。
     * @return 丢失了多少
     */
	public int lost(int track)
	{
		if (losted[track] > 0)
		{
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
		public boolean beatBPM()
	{
				return isBeat;
		}

		/**
	 * <summary> 得到游戏轨道数量。
     * @return 游戏轨道数
     */
	public int getLineCount()
	{
		return LineCount;
	}

		/**
	 * <summary> 得到当前BPM值。
	 * @return BPM
	 */
	public int getBPM1000()
	{
		return (int)BPM;
	}


	public int[] getFullNoteLine()
	{
		int[] noteLine = new int[(int)(ScreenHeight/(DivFullNote*Speed/10))+2];
		for (int i = 0; i < noteLine.length; i++)
		{
			noteLine[i] = (int)(((FullNoteCount + i) * DivFullNote * F - Position) / F * Speed / 10);
		}
		return noteLine;
	}

	public int getBeatCount()
	{
		return BeatCount;
	}

	public int getFullNoteCount()
	{
		return FullNoteCount;
	}

	// img
	public Image getBMP()
	{
		return curBMP;
	}

	public Image getBMPLayer()
	{
		return curBMPLayer;
	}
	public Image getBMPPoor()
	{
		return curBMPPoor;
	}


	public boolean isEnd()
	{
		boolean ret = true;
		for (int i = keyBGM.length - 1; i >= 0; i--)
		{
			if (keyBGM[i].size() > 0) ret = false;
		}
		for (int i = keyPLAYER.length - 1; i >= 0; i--)
		{
			if (keyPLAYER[i].size() > 0) ret = false;
		}
		return ret;
	}

	public void setSpeed(int speed)
	{
		Speed = speed;
		if (Speed < 1) Speed = 1;
		if (Speed > 100) Speed = 100;
	}

	public int getSpeed()
	{
		return Speed;
	}

	public void setVolumeBGM(int volume)
	{
		VolumeBGM = volume;
		if (VolumeBGM < 0) VolumeBGM = 0;
		if (VolumeBGM > 100) VolumeBGM = 100;

		VolumeBGM = -10000 + (int)Math.sqrt(VolumeBGM * 100) * 100;

	}
	//public int getVolumeBGM()
	//{
	//    return VolumeBGM;
	//}

	public void setVolumeKEY(int volume)
	{
		VolumeKEY = volume;
		if (VolumeKEY < 0) VolumeKEY = 0;
		if (VolumeKEY > 100) VolumeKEY = 100;

		VolumeKEY = -10000 + (int)Math.sqrt(VolumeKEY * 100) * 100;

	}
	//public int getVolumeKEY()
	//{
	//    return VolumeKEY;
	//}
}

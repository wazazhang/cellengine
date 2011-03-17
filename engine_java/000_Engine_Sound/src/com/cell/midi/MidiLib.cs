using System;
using System.Collections;

namespace MidiLibrary
{

	/*------------------------------------------------------------------*/
	/*                         Header Chunk                             */
	/*------------------------------------------------------------------*/
	/* Header Chunk */
	public class HeaderChunk 
	{
		public static byte[]	CHUNK_TYPE_HEADER = {0x4D,0x54,0x68,0x64} ;
		public static Int16	MIDI_TYPE_0			= 0 ;
		public static Int16	MIDI_TYPE_1			= 1 ;
		public static Int16	MIDI_TYPE_2			= 2 ;

		//		public static Int16	SMPTE_ON						= 0x8000;
		public static Int16	SMPTE_FPS_24					= -24<<8;
		public static Int16	SMPTE_FPS_25					= -25<<8;
		public static Int16	SMPTE_FPS_30_DROP_FRAME			= -29<<8;
		public static Int16	SMPTE_FPS_30_NONE_DROP_FRAME	= -30<<8;


		public byte[]	ChunkType;
		public Int32	DataSize;

		public Int16	MidiType;
		public Int16	Tracks;
		public Int16	Division;

		//		public Int16	DTPT;//delta-time per ticks
		//		public Int16	smpte_on  : 1;
		public Int16	Smpte_fps;
		public Int16	Smpte_tpf;

		public HeaderChunk(Int32 dataSize)
		{
			ChunkType = new byte[4];
			System.Array.Copy(CHUNK_TYPE_HEADER,ChunkType,4);
			DataSize = dataSize;

			Console.Write("HeaderSize  : ");
			Console.WriteLine(this.DataSize);

			MidiType = 0;
			Tracks = 0;
			Division = 0;

			Smpte_fps = 0;
			Smpte_tpf = 0;
		}

		public Int32 LoadChunkData(byte[] data,Int32 p)
		{
			int P = p ;
			MidiType = Midi.readMSB16(data,P);P+=2;
			Tracks   = Midi.readMSB16(data,P);P+=2;
			Division = Midi.readMSB16(data,P);P+=2;

			Console.Write("Midi Format : ");
			Console.WriteLine(this.MidiType);

			Console.Write("Traks       : ");
			Console.WriteLine(this.Tracks);

			if((Division&0x8000)==0)
			{
				Console.Write("Division    : ");
				Console.WriteLine(this.Division);
			}
			else
			{
				Smpte_fps = (Int16)(Division>>8);
				Smpte_tpf = (Int16)(Division&0x00ff);

				Console.Write("SMPTE FPS   : ");
				Console.WriteLine(this.Smpte_fps);

				Console.Write("SMPTE TPF   : ");
				Console.WriteLine(this.Smpte_tpf);
			}
			return P - p ;
		}
	};


	/*------------------------------------------------------------------*/
	/*                         Track Chunk                              */
	/*------------------------------------------------------------------*/

	/* Track Chunk */
	public class TrackChunk 
	{
	
		public static byte[] CHUNK_TYPE_TRACK = {0x4D,0x54,0x72,0x6B};

		public static string TextMeta	= "Meta";
		public static string TextSYSEX	= "SYSEX";

		public byte[]	ChunkType;
		public Int32	DataSize;

		public ArrayList DeltaTime;
		public ArrayList Event;
		public ArrayList Discription;
		public ArrayList EData;


		public TrackChunk(Int32 dataSize)
		{
			ChunkType = new byte[4];
			System.Array.Copy(CHUNK_TYPE_TRACK,ChunkType,4);
			DataSize = dataSize;
			
			DeltaTime = new ArrayList();
			Event = new ArrayList();
			Discription = new ArrayList();
			EData = new ArrayList();

			Console.Write("TrackSize   : ");
			Console.WriteLine(this.DataSize);
		}

		public Int32 LoadChunkData(byte[] data,Int32 p)
		{
			
			Int32 P = p ;
			byte MidiStatus = 0;



			while((P-p)<DataSize)
			{
				int EStart = P;

				/* delta time */
				Int32 val = 0;

				Int32[] vlq = Midi.readVLQ(data,P);
				P+=vlq[0];
				val = vlq[1];

				String dt = val.ToString();
				DeltaTime.Add(dt);
				//DeltaTime.
				//	Console.Write("DeltaTime   : ");
				//	Console.WriteLine(value);

				/* events */
				byte CurByte = Midi.readMSB8(data,P);

				//midi status changed
				if( CurByte>=0x80 )
				{
					MidiStatus = CurByte ;
					P++;
				}

			

				//8x      1000xxxx    nn vv        音符关闭 (释放键盘) nn=音符号 vv=速度
				if(MidiStatus>=0x80 && MidiStatus<=0x8f)
				{
						
					Event.Add("Note Off");
					Discription.Add(
						"Channel="+(MidiStatus&0x0f)+","+
						"Note="+Midi.readMSB8(data,P++)+","+
						"Velocity="+Midi.readMSB8(data,P++)
						);

					//P+=2;

				}
				//9x      1001xxxx    nn vv        音符打开 (按下键盘) nn=音符号 vv=速度
				if(MidiStatus>=0x90 && MidiStatus<=0x9f)
				{
					Event.Add("Note On");
					Discription.Add(
						"Channel="+(MidiStatus&0x0f)+","+
						"Note="+Midi.readMSB8(data,P++)+","+
						"Velocity="+Midi.readMSB8(data,P++)
						);
					//P+=2;
				}
				//Ax      1010xxxx    nn vv        触摸键盘以后 nn=音符号 vv=速度
				if(MidiStatus>=0xa0 && MidiStatus<=0xaf)
				{
					Event.Add("Polyphone Key Pressure(Aftertouch)");
					Discription.Add(
						"Channel="+(MidiStatus&0x0f)+","+
						"Note="+Midi.readMSB8(data,P++)+","+
						"Velocity="+Midi.readMSB8(data,P++)
						);
					//P+=2;
				}
				//Bx      1011xxxx    cc vv        调换控制 cc=控制号 vv=新值
				if(MidiStatus>=0xb0 && MidiStatus<=0xbf)
				{
					Event.Add("Control Change");
					Discription.Add(
						"Channel="+(MidiStatus&0x0f)+","+
						"Controller Number="+Midi.readMSB8(data,P++)+","+
						"New Value="+Midi.readMSB8(data,P++)
						);
					//P+=2;
				}
				//Cx      1100xxxx    pp            改变程序（片断） pp=新的程序号
				if(MidiStatus>=0xc0 && MidiStatus<=0xcf)
				{
					Event.Add("Program Change");
					Discription.Add(
						"Channel="+(MidiStatus&0x0f)+","+
						"New Program Number="+Midi.readMSB8(data,P++)
						);
					//P+=1;
				}
				//Dx      1101xxxx    cc            在通道后接触 cc=管道号
				if(MidiStatus>=0xd0 && MidiStatus<=0xdf)
				{
					Event.Add("Channel Pressure(Aftertouch)");
					Discription.Add(
						"Channel="+(MidiStatus&0x0f)+","+
						"cc="+Midi.readMSB8(data,P++)
						);
					P+=1;
				}
				//Ex      1110xxxx    bb tt        改变互相咬和的齿轮 (2000H 表明缺省或没有改变)(什么意思搞不懂:)
				//        bb=值的低7位(least sig) 
				//        tt=值的高7位 (most sig) 
				if(MidiStatus>=0xe0 && MidiStatus<=0xef)
				{
					Event.Add("Pitch Wheel Change");
					Discription.Add(
						"Channel="+(MidiStatus&0x0f)+","+
						"Bottom="+Midi.readMSB8(data,P++)+","+
						"Top="+Midi.readMSB8(data,P++)
						);
					//P+=2;
				}



				//F0      系统专用信息
				//f0 SYSEX events
				if(MidiStatus==0xf0)
				{
					int len = 0 ;
					vlq = Midi.readVLQ(data,P);
					P+=vlq[0];

					Event.Add("SYSEX : (0xf0)");
					Discription.Add("...");

					len = vlq[1];
					P+=len;
				}
				//F1      MIDI时间代码转换信息
				if(MidiStatus==0xf1)
				{
					Event.Add("SYSEX : MIDI Time Code");
					Discription.Add("");
				}
				//F2      乐曲位置指针信息
				if(MidiStatus==0xf2)
				{
					Event.Add("SYSEX : Song Position Pointer");
					Discription.Add("...");
					P+=2;
				}
				//F3      乐曲选择信息
				if(MidiStatus==0xf3)
				{
					Event.Add("SYSEX : Song Select");
					Discription.Add("Song="+Midi.readMSB8(data,P++));
				}
				//F4      (未定义)
				if(MidiStatus==0xf4)
				{
					Event.Add("(Undefined) (0xf4)");
					Discription.Add("...");
				}
				//F5      (未定义)
				if(MidiStatus==0xf5)
				{
					Event.Add("(Undefined) (0xf5)");
					Discription.Add("...");
				}
				//F6      音调调整要求信息
				if(MidiStatus==0xf6)
				{
					Event.Add("SYSEX : Tune Request");
					Discription.Add("");
				}
				//F7      结束系统专用信息
				if(MidiStatus==0xf7)
				{
					Event.Add("SYSEX : End of Exclusive");
					Discription.Add("");
				}
				//F8      同步所必须的计时器
				if(MidiStatus==0xf8)
				{
					Event.Add("SYSEX : Synchronise Timer");
					Discription.Add("");
				}
				//F9      (未定义)
				if(MidiStatus==0xf9)
				{
					Event.Add("(Undefined) (0xf9)");
					Discription.Add("...");
				}
				//FA      开始当前的队列
				if(MidiStatus==0xfa)
				{
					Event.Add("SYSEX : Start");
					Discription.Add("");
				}
				//FB      从停止的地方继续一个队列
				if(MidiStatus==0xfb)
				{
					Event.Add("SYSEX : Continue");
					Discription.Add("");
				}
				//FC      停止一个队列
				if(MidiStatus==0xfc)
				{
					Event.Add("SYSEX : Stop");
					Discription.Add("");
				}
				//FD      (未定义)
				if(MidiStatus==0xfd)
				{
					Event.Add("(Undefined) (0xfd)");
					Discription.Add("...");
				}
				//FE      联系激活信息
				if(MidiStatus==0xfe)
				{
					Event.Add("SYSEX : Active Sending");
					Discription.Add("");
				}
				//FF       复位信息
				//if(MidiStatus==0xff){}
				//ff Meta-Events
				if(MidiStatus==0xff)
				{
					byte MetaType = Midi.readMSB8(data,P++);
					byte[] MetaData;

					int len = 0 ;
					vlq = Midi.readVLQ(data,P);
					P+=vlq[0];
					len = vlq[1];

					MetaData = new byte[len];
					System.Array.Copy(data,P,MetaData,0,len);

					P+=len;

					//int mp = 0;
					String text = "";
					switch(MetaType)
					{
					//00 nn ssss      设定轨道的序号 nn=02 (两字节长度的序号) ssss=序号
					case 0x00:
						Event.Add("Meta : Set Track Number");
						Discription.Add("");
						break;
					//01 nn tt ..      你需要的所有文本事件  nn=以字节为单位的文本长度 tt=文本字符
					case 0x01:
						Event.Add("Meta : Text");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					//02 nn tt ..      同文本的事件, 但是用于版权信息 nn tt=同文本事件
					case 0x02:
						Event.Add("Meta : Copyright");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					//03 nn tt ..      序列或者轨道名 nn tt=同文本事件
					case 0x03:
						Event.Add("Meta : Track Name");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					//04 nn tt ..      轨道乐器名 nn tt=同文本事件
					case 0x04:
						Event.Add("Meta : Instrument Name");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					//05 nn tt ..      歌词 nn tt=同文本事件
					case 0x05:
						Event.Add("Meta : Lyric");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					//06 nn tt ..      标签 nn tt=同文本事件
					case 0x06:
						Event.Add("Meta : Label");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					//07 nn tt ..      浮点音符 nn tt=同文本事件
					case 0x07:
						Event.Add("Meta : Float Note");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					//20 01 cc         MIDI Channel 前缀
					case 0x20:
						Event.Add("Meta : MIDI Channel Prefixal");
						Discription.Add(MetaData[0].ToString());
						break;
					//2F 00            这个事件一定在每个轨道的结尾出现
					case 0x2f:
						Event.Add("Meta : Track End");
						Discription.Add("");
						break;
					//51 03 tttttt    设定拍子 tttttt=微秒/四分音符
					case 0x51:
						Event.Add("Meta : Set Tempo");
						int tempo = Midi.readMSB24(MetaData,0);
						Discription.Add("Microseconds/Quarter Note="+tempo+"");
						break;
					//54 05 hh mm ss fr ff  SMTPE 偏移量 
					case 0x54:
						Event.Add("Meta : SMTPE Offset");
						Discription.Add(
							"HH="+MetaData[0]+","+
							"MM="+MetaData[1]+","+
							"SS="+MetaData[2]+","+
							"FR="+MetaData[3]+","+
							"FF="+MetaData[4]
							);
						break;
					//58 04 nn dd cc bb 拍子记号 nn=拍子记号分子 dd=拍子记号分母2=四分之一 3=8分拍, 等等. cc=节拍器的节奏 bb=对四分之一音符标注的第32号数字
					case 0x58:
						Event.Add("Meta : Time Signature");
						Discription.Add(
							"Numerator="+MetaData[0]+","+
							"Denominator="+MetaData[1]+","+
							"Number of Ticks in Metronome Click="+MetaData[2]+","+
							"Number of 32nd Notes to the Quarternote="+MetaData[3]
							);
						break;
					//59 02 sf mi      音调符号 sf=升调/降调(-7=7 降调, 0=基准C调,7=7 升调) mi=大调/小调(0=大调, 1=小调)
					case 0x59:
						Event.Add("Meta : Key Signature");
						Discription.Add(
							"Sharps or Flats="+MetaData[0]+","+
							"Major or Minor="+MetaData[1]
							);
						break;
					//7F xx dd ..      音序器的详细信息 xx=被发送的字节数 dd=数据
					case 0x7f:
						Event.Add("Meta : Synthesizer Info");
						for(int i=0;i<MetaData.Length;i++)
						{
							text+=((char)MetaData[i]).ToString();
						}
						Discription.Add(text);
						break;
					default:
						Event.Add("Meta : "+MetaType.ToString("X2"));
						Discription.Add("");
						break;
					}
	
				}

				String st = "";
				for(int i=0;i<P-EStart;i++)
				{
					st+=(data[EStart+i]).ToString("X2");
					st+=" ";
				}
				EData.Add(st);
			}
				
			
			
			//P += DataSize;
			return P - p ;
		}

	};

 

	/*------------------------------------------------------------------*/
	/*                         Midi                                     */
	/*------------------------------------------------------------------*/

	/* Midi */
	public class Midi
	{



		/*------------------------------------------------------------------*/
		/*                         Function                                 */
		/*------------------------------------------------------------------*/

		/// <summary> 往一个data数组里写一个Byte
		/// <param>	  data[]
		/// <param>   value
		public static void writeMSB(byte[] data,Int32 p,byte  val)
		{
			data[p++] = val;
		}
		/// <summary> 往一个data数组里写一个Int16
		/// <param>	  data[]
		/// <param>   value
		public static void writeMSB(byte[] data,Int32 p,Int16 val)
		{
			data[p++] = (byte)(val>>8);
			data[p++] = (byte)(val>>0);
		}

		/// <summary> 往一个data数组里写一个Int24
		/// <param>	  data[]
		/// <param>   value
		public static void writeMSB24(byte[] data,Int32 p,Int32 val)
		{
			data[p++] = (byte)(val>>16);
			data[p++] = (byte)(val>>8) ;
			data[p++] = (byte)(val>>0) ;
		}

		/// <summary> 往一个data数组里写一个Int32
		/// <param>	  data[]
		/// <param>   value
		public static void writeMSB(byte[] data,Int32 p,Int32 val)
		{
			data[p++] = (byte)(val>>24);
			data[p++] = (byte)(val>>16);
			data[p++] = (byte)(val>>8) ;
			data[p++] = (byte)(val>>0) ;
		}

		/// <summary> 从一个data数组里读一个Byte
		/// <param>	  data[]    源数据
		/// <param>   p         位置
		/// <return>  
		public static byte  readMSB8(byte[] data,Int32 p)
		{
			return data[p++] ;
		}

		/// <summary> 从一个data数组里读一个Int16
		/// <param>	  data[]    源数据
		/// <param>   p         位置
		/// <return>  
		public static Int16 readMSB16(byte[] data,Int32 p)
		{
			Int16 val = 0;
			val += (Int16)(data[p++]<<8);
			val += (Int16)(data[p++]<<0);
			return val ;
		}

		/// <summary> 从一个data数组里读一个Int24
		/// <param>	  data[]    源数据
		/// <param>   p         位置
		/// <return>  
		public static Int32 readMSB24(byte[] data,Int32 p)
		{
			Int32 value = 0;
			value += data[p++]<<16;
			value += data[p++]<<8;
			value += data[p++]<<0;
			return value ;
		}

		/// <summary> 从一个data数组里读一个Int32
		/// <param>	  data[]    源数据
		/// <param>   p         位置
		/// <return>  
		public static Int32 readMSB32(byte[] data,Int32 p)
		{
			Int32 value = 0;
			value += data[p++]<<24;
			value += data[p++]<<16;
			value += data[p++]<<8;
			value += data[p++]<<0;
			return value ;
		}



		/// <summary> 在一个data数组里写一个可变长度数字 variable length quantity
		/// <param>	  data[]    源数据
		/// <param>   p         位置
		/// <param>   vaule     数据
		/// <return>            写了多少字节
		public static Int32 writeVLQ(byte[] data,Int32 p,Int32 vaule)
		{
			byte[] temp = new Byte[4];
			Int32 length = 0;
			for(int i=0;i<4;i++)
			{
				temp[i] = (byte)(vaule>>(7*i));
				length++;

				if((vaule>>(7*(i+1)))<=0)
				{
					temp[i] = (byte)(temp[i]&0x7f);
					break;
				}
				else
				{
					temp[i] = (byte)(temp[i]|0x80);
				}
			}
				
			for(int i=0;i<length;i++)
			{
				data[p++] = temp[(length-1)-i];
			}

			return length;
		}

		/// <summary> 从一个data数组里读一个可变长度数字 variable length quantity
		/// <param>	  data[]    源数据
		/// <param>   p         位置
		/// <return>            [0]读了多少字节[1]值
		public static Int32[] readVLQ(byte[] data,Int32 p)
		{
			Int32 Vaule = 0;
			Int32 length = 0;
			for(int i=0;i<4;i++)
			{
				byte temp = data[p++];
				Vaule = (Vaule<<7)|(temp&0x7f);
				length++;

				if((temp&0x80)==0)
				{
					break;
				}
			}	
			Int32[] ret = new Int32[2];
			ret[0] = length;
			ret[1] = Vaule;
			return ret;
		}	
		/*------------------------------------------------------------------*/
		/*                         Function                                 */
		/*------------------------------------------------------------------*/




		public HeaderChunk header;
		public TrackChunk[] track;
		public byte[] Data;

		public Midi()
		{
		}


		public Int32 LoadData(byte[] data)
		{
			Console.WriteLine();
			Console.Write("Load data ");
			Console.Write(data.Length);
			Console.WriteLine("(Bytes)");
			
			int  P = 0;
//*
			//header
			for(int i=0;i<4;i++)
			{
				if(data[P++]!=HeaderChunk.CHUNK_TYPE_HEADER[i])
				{
					Console.WriteLine("    Error ! Bad Header Chunk .");
					System.Exception err = new System.Exception("Bad Header Chunk !");
					throw(err);
				}
			}
			header = new HeaderChunk(Midi.readMSB32(data,P));P+=4;
			P+=header.LoadChunkData(data,P);

			if(header.MidiType!=HeaderChunk.MIDI_TYPE_0 && header.MidiType!=HeaderChunk.MIDI_TYPE_1)
			{
				Console.WriteLine("    Error ! Only support Midi Format 0 or 1 .");
				System.Exception err = new System.Exception("Only support Midi Format 0 or 1 !");
				throw(err);
			}
//*			
			//track
			track = new TrackChunk[header.Tracks];
			for(int t=0;t<header.Tracks;t++)
			{
				for(int i=0;i<4;i++)
				{
					if(data[P++]!=TrackChunk.CHUNK_TYPE_TRACK[i])
					{
						Console.Write("    Error ! Bad Track Chunk.("+t+") .");
						System.Exception err = new System.Exception("Bad Track Chunk.("+t+") !");
						throw(err);
					}
				}
				track[t] = new TrackChunk(Midi.readMSB32(data,P));P+=4;
				P+=track[t].LoadChunkData(data,P);
			}
//*/

			Data = data ;

			Console.Write("Midi Created ! Process ");
			Console.Write(P);
			Console.WriteLine("(Bytes)");

			return P;
		}

	}	


}

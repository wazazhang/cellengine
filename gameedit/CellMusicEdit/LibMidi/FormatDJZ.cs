using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using Cell.LibMidi;

namespace Cell.LibMidi
{
    public class EventDJZ
    {
        public const byte CONTROL_BPM = 0x80 + 1;

        public UInt32 time;// ms
        public UInt32 length;


        public byte state;

        public byte program;
        public byte channel;
        public byte note;
        public byte velo;

        public byte pos;//key位置
        
        public int metadata;
        public int fullnote;

        public int DeltaTime;
        public int DeltaLength;
        public int BPM1000;

        public bool used;
        public bool selected;
        public bool dragged;
        public int track;//midi轨道


    }

    public class EventComparerDJZTime : IComparer
    {

        // Calls CaseInsensitiveComparer.Compare with the parameters reversed.
        int IComparer.Compare(Object x, Object y)
        {
            return ((int)((EventDJZ)x).time) - ((int)((EventDJZ)y).time);
        }

    }
    public class EventComparerDJZChannelProgramNote : IComparer
    {

        // Calls CaseInsensitiveComparer.Compare with the parameters reversed.
        int IComparer.Compare(Object x, Object y)
        {
            if (((int)((EventDJZ)x).channel) - ((int)((EventDJZ)y).channel) == 0)
            {
                if (((int)((EventDJZ)x).program) - ((int)((EventDJZ)y).program) == 0)
                {
                    return ((int)((EventDJZ)x).note) - ((int)((EventDJZ)y).note);
                }
                else
                {
                    return ((int)((EventDJZ)x).program) - ((int)((EventDJZ)y).program);
                }
            }
            else
            {
                return ((int)((EventDJZ)x).channel) - ((int)((EventDJZ)y).channel);
            }
            
        }

    }

    public class EventComparerDJZMetaBPM : IComparer
    {

        // Calls CaseInsensitiveComparer.Compare with the parameters reversed.
        int IComparer.Compare(Object x, Object y)
        {
            if (((int)((EventDJZ)x).metadata) == EventDJZ.CONTROL_BPM &&
                ((int)((EventDJZ)y).metadata) == EventDJZ.CONTROL_BPM)
            {
                return ((int)((EventDJZ)x).BPM1000) - ((int)((EventDJZ)y).BPM1000);
            }
            else
            {
                return ((int)((EventDJZ)x).metadata) - ((int)((EventDJZ)y).metadata);
            }

        }

    }

    public class DJZFile
    {
        public int LineCount;
        public UInt32 EndTime;
        public UInt32 BPM;
        public ArrayList Events;
        public ArrayList Controls;
        public DJZ curDJZ;


        public DJZFile(DJZ djz,int lineCount)
        {
            LineCount = lineCount;
            curDJZ = djz;
            EndTime = (UInt32)djz.LastTime;
            BPM = (UInt32)djz.BPM;
            Events = (ArrayList)djz.PlayNode.Clone();
            Controls = (ArrayList)djz.Controls.Clone();

            if (Events == null || Controls == null)
            {
                throw (new Exception("Null ArrayList !"));
            }

        }

        public byte[] toDJZ10()
        {
            ////文件结构

            ////*文件开始*//
            //u32	FileSize	;	//文件大小
            //u32	BPM		    ;	//歌曲速度 (Beat Per Min 拍/分钟)
            //u32	NoteCount	;	//音符数量
            //Node*	Notes   	;	//音符数据
            ////*文件结束*//

            ////文件字节流按照 高位存高字节 低位存低字节的顺序

            ////音符结构体 (就是游戏中落下的块)
            //typedef struct {
            //u32	Time	;	//时间 单位 ms , 该音符在什么时间激发
            //u8	Line	;	//游戏中的轨道 ：将由编辑器编辑
            //u8	Data	;	//发音 (就是MIDI事件的发音)
            //}Node;

            EventComparerDJZTime ec = new EventComparerDJZTime();
            Events.Sort(ec);

            Int32 FileSize = 4 + 4 + 4 + Events.Count * 6;
            byte[] data = new byte[FileSize];
            int P = 0;

            Midi.write32(data, P, (Int32)FileSize); P += 4;//文件大小
            Midi.write32(data, P, (Int32)BPM); P += 4;//歌曲速度 (Beat Per Min 拍/分钟)

            Midi.write32(data, P, (Int32)Events.Count); P += 4;//音符数量
            for (int i = 0; i < Events.Count; i++)//音符数据序列
            {
                Midi.write32(data, P, (Int32)(((EventDJZ)Events[i]).time)); P += 4;
                Midi.write8(data, P, ((EventDJZ)Events[i]).pos); P += 1;
                Midi.write8(data, P, ((EventDJZ)Events[i]).note); P += 1;
                //Console.WriteLine("Note[" + i + "].time = " + (Int32)(((Event)Events[i]).time));
            }

            return data;
        }

        public byte[] toDJZ20()
        {
            ////文件结构

            ////*文件开始*//
            //u32	FileSize	;	//文件大小
            //u32   EndTime     ;   //结束时间
            //u32	NoteCount	;	//音符数量
            //Note*	Notes   	;	//音符数据
            //u32	MetaCount	;	//控制数量
            //Meta*	Controls	;	//控制数据
            ////*文件结束*//

            ////文件字节流按照 高位存高字节 低位存低字节的顺序

            ////音符结构体 (就是游戏中落下的块)
            //typedef struct {
            //u32	Time	;	//时间 单位 ms , 该音符在什么时间激发
            //u8	Line	;	//游戏中的轨道：将由编辑器编辑
            //u8	Data	;	//发音 (就是MIDI事件的发音)
            //}Note;

            ////控制结构体 (就是游戏中落下的块)
            //typedef struct {
            //u32	Time	;	//时间 单位 ms , 在什么时间激发
            //u8	Type	;	//类型
            //u32   Data    ;   //数据
            //}Meta;

            EventComparerDJZTime ec = new EventComparerDJZTime();
            Events.Sort(ec);
            Controls.Sort(ec);

            Int32 FileSize = 4 + 4 + 4 + Events.Count * 6 + 4 + Controls.Count * 9;
            byte[] data = new byte[FileSize];
            int P = 0;

            Midi.write32(data, P, (Int32)FileSize); P += 4;//文件大小
            Midi.write32(data, P, (Int32)EndTime); P += 4;//结束时间

            Midi.write32(data, P, (Int32)Events.Count); P += 4;//音符数量
            for (int i = 0; i < Events.Count; i++)//音符数据序列
            {
                Midi.write32(data, P, (Int32)(((EventDJZ)Events[i]).time)); P += 4;
                Midi.write8(data, P, ((EventDJZ)Events[i]).pos); P += 1;
                Midi.write8(data, P, ((EventDJZ)Events[i]).note); P += 1;
            }

            Midi.write32(data, P, (Int32)Controls.Count); P += 4;//控制数量
            for (int i = 0; i < Controls.Count; i++)//控制数据序列
            {
                Midi.write32(data, P, (Int32)(((EventDJZ)Controls[i]).time)); P += 4;
                Midi.write8(data, P, ((EventDJZ)Controls[i]).note); P += 1;
                Midi.write32(data, P, ((EventDJZ)Controls[i]).metadata); P += 4;
            }

            return data;
        }

        public string toBMS(Boolean isOld,String mainSong)
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

       


            EventComparerDJZTime ecT = new EventComparerDJZTime();
            EventComparerDJZChannelProgramNote ecCPN = new EventComparerDJZChannelProgramNote();
            EventComparerDJZMetaBPM ecBPM = new EventComparerDJZMetaBPM();

            Events.Sort(ecT);
            Controls.Sort(ecT);

            Hashtable WAV = new Hashtable();
            Hashtable BPM = new Hashtable();
           
            int HDBPMIndex = 1 ;
            for (int i = 0; i < Controls.Count; i++)
            {
                switch(((EventDJZ)Controls[i]).note){
                    case EventDJZ.CONTROL_BPM:
                        if (!haveValue(BPM, (EventDJZ)Controls[i], ecBPM))
                        {
                            try
                            {
                                if (isOld)
                                {
                                    BPM.Add((EventDJZ)Controls[i], valTo00FF(HDBPMIndex));
                                }
                                else
                                {
                                    BPM.Add((EventDJZ)Controls[i], valTo00ZZ(HDBPMIndex));
                                }
                                HDBPMIndex++;
                            }
                            catch (Exception err)
                            {
                                Util.println(err.Message + " Out Of Head Max ! " + HDBPMIndex);
                            }
                            
                        }
                        break;
                }
            }

            int HDWAVIndex = 2 ;
            for (int i = 0; i < Events.Count; i++)
            {
                if (!haveValue(WAV, (EventDJZ)Events[i], ecCPN))
                {
                    try
                    {
                        if (isOld)
                        {
                            WAV.Add((EventDJZ)Events[i], valTo00FF(HDWAVIndex));
                        }
                        else
                        {
                            WAV.Add((EventDJZ)Events[i],valTo00ZZ(HDWAVIndex));
                        }
                        HDWAVIndex++;
                    }
                    catch (Exception err)
                    {
                        Util.println(err.Message + " Out Of Head Max ! " + HDWAVIndex);
                    }
                    
                }
            }

            String str = "";
            String ret = "\r\n";
            str += ret;
            str += "*---------------------- HEADER FIELD" + ret;
            str += ret;
            str += "#PLAYER 2" + ret;
            str += "#GENRE WAZA" + ret;
            str += "#TITLE "+ ret;
            str += "#ARTIST " + ret;
            str += "#BPM 120" + ret;
            str += "#PLAYLEVEL 1" + ret;
            str += "#RANK " + ret;
            str += "#TOTAL " + Events.Count + ret;
            str += "#STAGEFILE " + ret;
            str += ret;

            str += "#WAV01 " + mainSong + ret;

            IDictionaryEnumerator ide_wav = WAV.GetEnumerator();
            ide_wav.Reset();
            while (ide_wav.MoveNext())
            {
                String wav =
                        "c" + ((EventDJZ)ide_wav.Key).channel.ToString("D2") +
                        "p" + ((EventDJZ)ide_wav.Key).program.ToString("D3") +
                        "n" + ((EventDJZ)ide_wav.Key).note.ToString("D3") + 
                        ".mid";

                str += "#WAV" + ((String)ide_wav.Value)
                     + " " +
                     wav + ret;
            }

            str += ret;

            IDictionaryEnumerator ide_bpm = BPM.GetEnumerator();
            ide_bpm.Reset();
            while (ide_bpm.MoveNext())
            {
                float value = ((float)((EventDJZ)ide_bpm.Key).BPM1000) / 1000;
                String bpm = value.ToString("F");

                str += "#BPM" +
                    ((String)ide_bpm.Value) + " " +
                    bpm + ret;
            }


            str += ret;
            str += "*---------------------- MAIN DATA FIELD" + ret;
            str += ret;

            str += "#00001:" + "01" + ret;
            

            int fulldiv = curDJZ.midi.header.Division*4;
            int fullnote = 0;
            
            while(true)
            {
                // bpm
                String LineBPM = setKey(Controls, fullnote, fulldiv, BPM, ecBPM, 0);
                if(LineBPM != "")str += "#" + fullnote.ToString("D3") + "08:" + LineBPM + ret;

                // 1p
                String Line1PKeySC = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 0);
                if (Line1PKeySC != "") 
                    str += "#" + fullnote.ToString("D3") + "16:" + Line1PKeySC + ret;

                String Line1PKey1 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 1);
                if (Line1PKey1 != "") 
                    str += "#" + fullnote.ToString("D3") + "11:" + Line1PKey1 + ret;

                String Line1PKey2 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 2);
                if (Line1PKey2 != "") 
                    str += "#" + fullnote.ToString("D3") + "12:" + Line1PKey2 + ret;

                String Line1PKey3 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 3);
                if (Line1PKey3 != "") 
                    str += "#" + fullnote.ToString("D3") + "13:" + Line1PKey3 + ret;

                String Line1PKey4 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 4);
                if (Line1PKey4 != "") 
                    str += "#" + fullnote.ToString("D3") + "14:" + Line1PKey4 + ret;

                String Line1PKey5 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 5);
                if (Line1PKey5 != "") 
                    str += "#" + fullnote.ToString("D3") + "15:" + Line1PKey5 + ret;

                String Line1PKey6 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 6);
                if (Line1PKey6 != "") 
                    str += "#" + fullnote.ToString("D3") + "18:" + Line1PKey6 + ret;

                String Line1PKey7 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 7);
                if (Line1PKey7 != "") 
                    str += "#" + fullnote.ToString("D3") + "19:" + Line1PKey7 + ret;

                // 2p
                String Line2PKey1 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 8);
                if (Line2PKey1 != "")
                    str += "#" + fullnote.ToString("D3") + "21:" + Line2PKey1 + ret;

                String Line2PKey2 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 9);
                if (Line2PKey2 != "")
                    str += "#" + fullnote.ToString("D3") + "22:" + Line2PKey2 + ret;

                String Line2PKey3 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 10);
                if (Line2PKey3 != "")
                    str += "#" + fullnote.ToString("D3") + "23:" + Line2PKey3 + ret;

                String Line2PKey4 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 11);
                if (Line2PKey4 != "")
                    str += "#" + fullnote.ToString("D3") + "24:" + Line2PKey4 + ret;

                String Line2PKey5 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 12);
                if (Line2PKey5 != "")
                    str += "#" + fullnote.ToString("D3") + "25:" + Line2PKey5 + ret;

                String Line2PKey6 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 13);
                if (Line2PKey6 != "")
                    str += "#" + fullnote.ToString("D3") + "28:" + Line2PKey6 + ret;

                String Line2PKey7 = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 14);
                if (Line2PKey7 != "")
                    str += "#" + fullnote.ToString("D3") + "29:" + Line2PKey7 + ret;

                String Line2PKeySC = setKey(Events, fullnote, fulldiv, WAV, ecCPN, 15);
                if (Line2PKeySC != "")
                    str += "#" + fullnote.ToString("D3") + "26:" + Line2PKeySC + ret;

                str += ret;
                fullnote++;
                if (fullnote > 999) break;
                if (Controls.Count <= 0 && Events.Count <= 0) break;
            }

            return str;
        }

        private String setKey(
            ArrayList events,
            int fullnote,
            int fulldiv,
            Hashtable hd,
            IComparer ic,
            int pos)
        {
            String notes = "";
            ArrayList Seq = new ArrayList(0);

            for (int i = 0; i < events.Count;i++ )
            {
                if (((EventDJZ)events[i]).fullnote == fullnote)
                {
                    if (((EventDJZ)events[i]).pos == pos)
                    {
                        Seq.Add(events[i]);
                        events.RemoveAt(i);
                        i--;
                    }
                }
                else
                {
                    break;
                }
            }

            if (Seq.Count < 1) return "";

            int[] line = new int[Seq.Count];
            for (int i = 0; i < line.Length;i++ )
            {
                line[i] = ((EventDJZ)Seq[i]).DeltaTime * 192 / fulldiv % 192;
            }

            int maxGCD = gcd(line, 192);

            for (int i = 0; i < 192 ; i+=maxGCD)
            {
                if (Seq.Count > 0 && ((EventDJZ)Seq[0]).DeltaTime * 192 / fulldiv % 192 == i)
                {
                    Object Key = null;
                    IEnumerator ie = hd.Keys.GetEnumerator();
                    ie.Reset();
                    while (ie.MoveNext())
                    {
                        if (ic.Compare(ie.Current, Seq[0]) == 0)
                        {
                            notes += (String)hd[ie.Current];
                            Seq.RemoveAt(0);
                            break;
                        }
                    }
                }
                else
                {
                    notes += "00";
                }
                
            }

            return notes;
        }


        private int gcd(int[] seq,int max)
        {
            for (int gcb = max; gcb >=1; gcb /= 2)
            {
                bool ok = true;
                for (int n = 0; n < seq.Length; n++)
                {
                    if (seq[n] % gcb != 0)
                    {
                        ok = false;
                        break;
                    }
                }
                if (ok)
                {
                    return gcb ;
                }
            }
            return 1;
        }

        private bool haveValue(Hashtable ht,EventDJZ ev,IComparer ic)
        {
            IEnumerator ie = ht.Keys.GetEnumerator();
            ie.Reset();
            while(ie.MoveNext())
            {
                if (ic.Compare(ie.Current, ev)==0)
                {
                    return true;
                }
            }
            return false;
        }

        private String valTo00ZZ(int value)
        {
            int n = 10 + 26 ;
            

            if (value > n*n-1 || value < 1)
            {
                return "00";
            }
            else
            {
                int h = value/n;
                int l = value%n;

                String H = "0";
                String L = "0";

                if (h >= 0 && h <= 9) H = h.ToString();
                else if (h >= 10 && h <= 35) H = ((char)(h - 10 + 'A')).ToString();

                if (l >= 0 && l <= 9) L = l.ToString();
                else if (l >= 10 && l <= 35) L = ((char)(l - 10 + 'A')).ToString();

                return H + L;
            }
        }

        private String valTo00FF(int value)
        {
            if (value > 255 || value < 1)
            {
                return "00";
            }
            else
            {
                return value.ToString("X2");
            }
        }
    }

    public class DJZ
    {
        


        public Midi midi;

        private UInt32 Clock;//Timer count
        private UInt32 Division;// delta time / quarter
        private UInt32 Tempo;// microsecond / quarter
        

        private double TickTime ;// microsecond / delta time


        public int TrackCount;
        
        public ArrayList[] Events;
        private byte[] MidiStatus;//track status
        private int[] TrackIndex;//track index
        private UInt32[] CurClock;
        private double[] CurTime;
        

        public UInt32 BPM = 120;
        public double LastTime;

        public ArrayList PlayNode;
        public ArrayList Controls;
        public ArrayList Lines;


        private byte[] Program;

        private int LineCount;

        public DJZ(Midi mid,int lineCount)
        {
            midi = mid;
            LineCount = lineCount;
            Clock = 0;

            Division = (UInt32)midi.header.Division;
            Tempo = 500000;
            SetTempo(Tempo);

            TrackCount = midi.header.Tracks ;

            Events = new ArrayList[TrackCount];
            MidiStatus = new byte[TrackCount];
            TrackIndex = new int[TrackCount];
            CurClock = new UInt32[TrackCount];
            CurTime = new double[TrackCount];
            

            PlayNode = new ArrayList();
            Controls = new ArrayList();
            Lines = new ArrayList();

            LastTime = 0;


            for (int i = 0; i < TrackCount;i++ )
            {
                Events[i] = new ArrayList();
                MidiStatus[i] = 0;
                TrackIndex[i] = 0;
                CurClock[i] = 0;
                CurTime[i] = 0;
            }

            Int32 newTempo = -1;

            Program = new byte[16];
            for (int i = 0; i < Program.Length; i++)
            {
                Program[i] = 0;
            }

            while (true)
            {
                bool end = true;
                for (int t = 0; t < TrackCount; t++)
                {
                    while(TrackIndex[t] < midi.track[t].dData.Count)
                    {
                        //End of track
                        end = false;

                        int p = 0;
                        byte[] data = (byte[])(midi.track[t].dData[TrackIndex[t]]);
                        Int32[] vlq = Midi.readVLQ(data, p); p += vlq[0];
                        Int32 deltatime = vlq[1];

                        //Track timer is zero
                        if (CurClock[t] == deltatime)
                        {
                            TrackIndex[t]++;
                            CurClock[t] = 0;

                            byte state = Midi.readMSB8(data, p);

                            //midi status changed
                            if (state >= 0x80)
                            {
                                MidiStatus[t] = state;
                                p += 1;
                            }
                            else
                            {
                                state = MidiStatus[t];
                            }

                            //note on/off
                            if (state >= 0x80 && state <= 0x9f)
                            {
                                EventDJZ ev = new EventDJZ();
                                ev.time = (UInt32)(CurTime[t] / 1000);
                                ev.state = MidiStatus[t];
                                
                                ev.note = Midi.readMSB8(data, p); p += 1;
                                ev.channel = (byte)(state & 0x0f);
                                ev.program = Program[ev.channel];

                                ev.pos = (byte)(ev.note%LineCount);
                                ev.track = t;

                                ev.metadata = 0;
                                ev.fullnote = (int)(Clock / (Division * 4));

                                ev.used = false;
                                ev.selected = false;
                                ev.dragged = false;

                                ev.DeltaTime = (int)Clock;
                                
                                
                                ev.velo = Midi.readMSB8(data, p); p += 1;

                                //Console.WriteLine("Note : " + ev.time + " : " + ev.note + " : " + ev.track);

                                if (ev.velo != 0 && (state >= 0x90 && state <= 0x9f) )
                                {
                                    Events[t].Add(ev);
                                }
                                else//note off
                                {
                                    setLastNoteOnLength(Events[t], ev);
                                }
                            }
                            //program change
                            if (state >= 0xc0 && state <= 0xcf)
                            {
                                int channel = (byte)(state & 0x0f);
                                int program = Midi.readMSB8(data, p); p += 1;
                                Program[channel] = (byte)program;
                            }

                            //meta 
                            if (state == 0xff)
                            {
                                byte meta = Midi.readMSB8(data, p); p += 1;
                                vlq = Midi.readVLQ(data, p); p += vlq[0];

                                //meta tempo change
                                if (meta == 0x51)
                                {
                                    EventDJZ ev = new EventDJZ();
                                    ev.time = (UInt32)(CurTime[t] / 1000);
                                    ev.state = MidiStatus[t];
                                    ev.note = EventDJZ.CONTROL_BPM;
                                    ev.pos = 0;
                                    ev.track = 0;
                                    ev.metadata = 0;
                                    ev.fullnote = (int)(Clock / (Division * 4));
                                    ev.used = false;
                                    ev.selected = false;
                                    ev.dragged = false;

                                    ev.DeltaTime = (int)Clock;


                                    newTempo = (Int32)Midi.readMSB24(data, p); p += 3;
                                    SetTempo((UInt32)newTempo);//Tempo changed !
                                    //Console.WriteLine("Tempo Changed : " + newTempo);

                                    ev.metadata = (int)BPM;
                                    ev.BPM1000 = (int)(60000000000 / newTempo);


                                    Controls.Add(ev);
                                }
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    CurTime[t] += TickTime;
                    CurClock[t]++;
                }//track end


                if (Clock % (Division * 4) == 0)
                {
                    EventDJZ ev = new EventDJZ();
                    ev.time = (UInt32)(LastTime / 1000);
                    ev.fullnote = (int)(Clock / (Division * 4));
                    ev.DeltaTime = (int)Clock;
                    Lines.Add(ev);
                    // Util.println("count = " + ev.metadata);
                }



                LastTime += TickTime;
                Clock++;
                if (end) break;
            }
            LastTime /= 1000;
            Console.WriteLine("LastTime=" + LastTime+"(ms)");

        }


        private void setLastNoteOnLength(ArrayList events,EventDJZ off)
        {
            for (int i = events.Count - 1; i >= 0;i-- )
            {
                if (off.channel == ((EventDJZ)events[i]).channel &&
                    off.note == ((EventDJZ)events[i]).note)
                {
                    ((EventDJZ)events[i]).length = off.time - ((EventDJZ)events[i]).time;
                    ((EventDJZ)events[i]).DeltaLength = off.DeltaTime - ((EventDJZ)events[i]).DeltaTime;
                }
            }
        }

        private void SetTempo(UInt32 newTempo)
        {
            Tempo = newTempo;
            TickTime = ((float)Tempo)/((float)Division);
            BPM = 60000000 / Tempo;
        }


        public DJZFile toDJZFile()
        {
            return null;
        }

    }
}

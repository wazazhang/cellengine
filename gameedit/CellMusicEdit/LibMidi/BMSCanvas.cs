using System;
using System.Collections;
using System.ComponentModel;
using System.Data;

using Cell.LibMidi;

namespace Cell.LibMidi
{
    public class BMSCanvas
    {	
        /**屏幕高度*/
        public int ScreenHeight = 192;

        public ArrayList Events;
        public ArrayList Tempos;
        private int LineCount;


        public int Speed = 100;

        public int BeatScope = 500;//0.5s
        public int LostScope = 2000;//2s

        // private ArrayList Buffer;
        public int BuffSize = 64;//同时处理音符数
        public int ControlBuffSize = 1;

        private ArrayList[] Pos;
        private ArrayList NoteLine = new ArrayList(32);
        public bool AutoPlay = false;
        public int AutoPlayHoldTime = 75;

        //private bool[] hit;
        private int[] hittedAuto;


        public BMSCanvas(BMS bms,ArrayList evts, ArrayList ctrls, int lineCount)
        {
            Events = evts;
            Tempos = ctrls;
            LineCount = lineCount;

            DivFullNote = bms.midi.header.Division*4;

            Speed = 100;

            //hit = new bool[trackCount];
            hittedAuto = new int[lineCount];
          
            Pos = new ArrayList[LineCount];
            for (int t = 0; t < LineCount; t++)
            {
                //hit[t] = false;
                hittedAuto[t] = -1;
                Pos[t] = new ArrayList(BuffSize);
            }

            BuffSize = Events.Count;
            ControlBuffSize = Tempos.Count;

        }


        //bpm
        private long Tempo = 500000;
        private long BPM ;
        private long PerBeatTime;
        private long BeatTick;

        //pos
        private long DivFullNote ;

        private long CurPosition = 0;

        private long PreDeltaTime = 0;
        private long DeltaTick;

        private int FullNoteCount = 0;


        public void UpdatePosition(long CurPlayTime)
        {
            DeltaTick = CurPlayTime - PreDeltaTime;
            PreDeltaTime = CurPlayTime;

            BPM = 60000000000 / Tempo;

            if (BPM != 0)
            {
                BeatTick = Tempo / 1000;//ms
                CurPosition += 1000 * DeltaTick * DivFullNote * BPM / (1000000 * 60 * 4);
            }
            else
            {
                BeatTick = int.MaxValue;
            }

           
            //control track
            for (int i = 0; i < ControlBuffSize; i++)
            {
                if (i >= Tempos.Count) break;
                // 得到最近的控制
                if (((EventBMS)Tempos[i]).time <= CurPosition/1000)
                {
                    Tempo = ((EventBMS)Tempos[i]).metadata;
                }
                if (((EventBMS)Tempos[i]).time > CurPosition / 1000)
                {
                    break;
                }
            }
        }

        public long Position()
        {
            return CurPosition/1000;
        }

        public void Update(long Position)
        {

            FullNoteCount = (int)(Position / DivFullNote);

            //event
            for (int t = 0; t < LineCount; t++)
            {
                hittedAuto[t] = -1;
                Pos[t].Clear();
            }
            for (int i = 0; i < BuffSize; i++)
            {
                if (i >= Events.Count) break;

                // 得到自动hit
                if (((EventBMS)Events[i]).time <= Position &&
                    ((EventBMS)Events[i]).time >= Position - AutoPlayHoldTime)
                {
                    hittedAuto[((EventBMS)Events[i]).pos % LineCount] = i;
                }

                // 根据时间计算音符的屏幕坐标
                //          根据时间计算音符的屏幕坐标
               int p = (int)((((EventBMS)Events[i]).time - Position) * Speed / 1000);
               Pos[((EventBMS)Events[i]).pos % LineCount].Add(new int[] { p, i });
               
            }
            // note line
            NoteLine.Clear();
            int line = (int)(ScreenHeight / (DivFullNote * Speed / 1000)) + 2;
            for (int i = 0; i < line; i++)
            {
                NoteLine.Add((int)(((FullNoteCount + i) * DivFullNote - Position) * Speed / 1000));
            }
        }

        

        public int[][] GetPos(int track)
        {
            if (track < LineCount)
            {
                return (int[][])(Pos[track].ToArray(typeof(int[])));
            }
            else
            {
                return null;
            }
        }

        public int[] getFullNoteLine()
        {
            //(int)((((EventBMS)Events[i]).time - Position) * Speed / 1000);

            
            return (int[])(NoteLine.ToArray(typeof(int)));

        }

        public int getFullNoteCount()
        {
            return FullNoteCount;
        }

        //
        public Boolean HitAuto(int track)
        {
            if (hittedAuto[track] >= 0)
            {
                return true;
            }
            return false;
        }

        public int GetLineCount()
        {
            return LineCount;
        }

        public int GetBPM()
        {
            return (int)BPM/1000;
        }

    }
}

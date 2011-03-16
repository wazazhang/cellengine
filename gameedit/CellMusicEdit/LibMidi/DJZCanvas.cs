using System;
using System.Collections;
using System.ComponentModel;
using System.Data;

using Cell.LibMidi;

namespace Cell.LibMidi
{
    public class DJZCanvas
    {

        public ArrayList Events;
        public ArrayList Controls;
        public ArrayList Lines;

        private int LineCount;
        private int BPM = 120;

        private int PerBeatTime;
        private int BeatTick;


        public int Speed = 100;

        public int BeatScope = 500;//0.5s
        public int LostScope = 2000;//2s

        // private ArrayList Buffer;
        public int BuffSize = 64;//同时处理音符数
        public int ControlBuffSize = 1;
        public int LineBuffSize = 1;

        private int Losted = 0;
        private ArrayList[] Pos;
        private ArrayList FullNoteLine;
        private int FullNoteCount = 0;

        public bool AutoPlay = false;
        public int AutoPlayHoldTime = 75;
        //private bool[] hit;
        private int[] hittedAuto;
        private int[] hittedManu;

        private Boolean isBeat = false;


        public DJZCanvas(ArrayList evts, ArrayList ctrls,ArrayList lines, int lineCount)
        {
            Events = evts;
            Controls = ctrls;
            Lines = lines;

            LineCount = lineCount;

            //BPM = bpm;
            PerBeatTime = 0;
            BeatTick = 1000 * 60 / BPM;

            Speed = 100;

            //hit = new bool[trackCount];
            hittedAuto = new int[lineCount];
            hittedManu = new int[lineCount];

            Pos = new ArrayList[LineCount];
            for (int t = 0; t < LineCount; t++)
            {
                //hit[t] = false;
                hittedAuto[t] = -1;
                hittedManu[t] = -1;
                Pos[t] = new ArrayList(BuffSize);
            }
            FullNoteLine = new ArrayList();
            //Buffer = new ArrayList(BuffSize);
            BuffSize = Events.Count;
            ControlBuffSize = Controls.Count;
            LineBuffSize = Lines.Count;
        }



        public void Update(int CurPlayTime)
        {

            // bpm
            if (CurPlayTime - PerBeatTime >= BeatTick)
            {
                isBeat = true;
                PerBeatTime = CurPlayTime;
            }
            //control track
            for (int i = 0; i < ControlBuffSize; i++)
            {
                if (i >= Controls.Count) break;
                // 得到最近的控制
                if (((EventDJZ)Controls[i]).time <= CurPlayTime)
                {
                    switch (((EventDJZ)Controls[i]).note)
                    {
                        case EventDJZ.CONTROL_BPM:
                            SetBPM(((EventDJZ)Controls[i]).metadata);
                            break;

                    }
                }
            }

            //full note line
            FullNoteLine.Clear();
            for (int i = 0; i < LineBuffSize; i++)
            {
                if (((EventDJZ)Lines[i]).time <= CurPlayTime)
                {
                    FullNoteCount = ((EventDJZ)Lines[i]).fullnote;
                }

                // 根据时间计算音符的屏幕坐标
                FullNoteLine.Add(
                       new int[]{
                        (int)((((EventDJZ)Lines[i]).time - CurPlayTime) * Speed / 1000),
                        ((EventDJZ)Lines[i]).fullnote
                        }
                       );
            }

            //event
            for (int t = 0; t < LineCount; t++)
            {
                hittedAuto[t] = -1;
                Pos[t].Clear();
            }
            for (int i = 0; i < BuffSize; i++)
            {
                if (i >= Events.Count) break;

                // 检测当前音符按下范围
                if (AutoPlay == false)
                {
                    if (hittedManu[((EventDJZ)Events[i]).pos % LineCount] < 0 &&
                        Math.Abs(((EventDJZ)Events[i]).time - CurPlayTime) < BeatScope)
                    {
                        hittedManu[((EventDJZ)Events[i]).pos % LineCount] = i;
                    }
                }
                else
                {
                    if (((EventDJZ)Events[i]).time <= CurPlayTime)
                    {
                        hittedManu[((EventDJZ)Events[i]).pos % LineCount] = i;
                    }
                }

                // 得到自动hit
                if (((EventDJZ)Events[i]).time <= CurPlayTime &&
                    ((EventDJZ)Events[i]).time >= CurPlayTime - AutoPlayHoldTime)
                {
                    hittedAuto[((EventDJZ)Events[i]).pos % LineCount] = i;
                }


                // 检测音符是否已经过期
                if (((EventDJZ)Events[i]).time < CurPlayTime - LostScope)
                {
                    //Console.WriteLine("LostAt  " + CurPlayTime + ":" + ((Event)Events[i]).time);
                    Events.RemoveAt(i);
                    Losted++;
                    continue;
                }

                // 根据时间计算音符的屏幕坐标
                Pos[((EventDJZ)Events[i]).pos % LineCount].Add(
                       new int[]{
                        (int)((((EventDJZ)Events[i]).time - CurPlayTime) * Speed / 1000),
                        i
                        }
                       );

            }

        }

        public void SetBPM(int bpm)
        {
            BPM = bpm;
            BeatTick = 1000 * 60 / BPM;
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

        //
        public Boolean Hit(int track)
        {
            if (hittedManu[track] >= 0)
            {
                Events.RemoveAt(hittedManu[track]);
                return true;
            }
            return false;
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

        //
        public int Lost()
        {
            if (Losted > 0)
            {
                int ret = Losted;
                Losted = 0;
                return ret;
            }
            return 0;
        }

        //
        public Boolean BeatPerMin()
        {
            if (isBeat)
            {
                isBeat = false;
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
            return BPM;
        }

        public int[][] getFullNoteLine()
        {

            return (int[][])(FullNoteLine.ToArray(typeof(int[])));

        }

        public int getFullNoteCount()
        {
            return FullNoteCount;
        }
    }
}

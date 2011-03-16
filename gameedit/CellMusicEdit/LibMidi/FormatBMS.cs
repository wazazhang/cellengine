using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using Cell.LibMidi;


namespace Cell.LibMidi
{
    public class EventBMS
    {
        //public int Note;
        //public int Delta;
        //public int Type;
        //public int Data;

        public int time;

        public byte state;
        public byte note;
        public byte pos;
        public int track;
        public int metadata;

        public bool used;
        public bool selected;
        public bool dragged;

    }

    public class EventComparerBMS : IComparer
    {
        // Calls CaseInsensitiveComparer.Compare with the parameters reversed.
        int IComparer.Compare(Object x, Object y)
        {
            return ((int)((EventBMS)x).time) - ((int)((EventBMS)y).time);
        }
    }


    public class BMSFile
    {




        public BMSFile(BMS bms)
        {
           

        }

        public string toBMS()
        {
            String file = "aaaaaaaaaaaaa";

            return file;
        }

    }

    public class BMS
    {
        public Midi midi;

        public ArrayList[] Events;

        public ArrayList keyBPM = new ArrayList();
        public ArrayList keyPlayer = new ArrayList();

        public int EndPos = 0;

        public BMS(Midi mid)
        {
            midi = mid;

            Events = new ArrayList[midi.header.Tracks];


            for (int t = 0; t < midi.header.Tracks; t++)
            {
                Events[t] = new ArrayList();

                int position = 0;


                for (int i = 0; i < midi.track[t].DeltaTime.Count; i++)
                {
                    int deltatime = Util.stringDigitToInt(((string)midi.track[t].DeltaTime[i]), 0, ((string)midi.track[t].DeltaTime[i]).Length);

                    position += deltatime;

                    if (EndPos < position) EndPos = position;

                    if (midi.track[t].Event[i].ToString() == "Note On")
                    {
                        string str = (string)midi.track[t].Discription[i];
                        
                        int c = str.IndexOf("Channel=");
                        int n = str.IndexOf("Note=");
                        int v = str.IndexOf("Velocity=");

                       
                        c = Util.stringDigitToInt(str, c + "Channel=".Length, str.Length);
                        n = Util.stringDigitToInt(str, n + "Note=".Length, str.Length);
                        v = Util.stringDigitToInt(str, v + "Velocity=".Length, str.Length);
                        
                        //Console.WriteLine(" c=" + c + " n=" + n + " v=" + v);

                        if (v > 0)
                        {
                            EventBMS ev = new EventBMS();

                            ev.time = position;
                            ev.note = (byte)n;
                            ev.pos = (byte)n;
                            ev.track = t;

                            ev.used = false;
                            ev.selected = false;
                            ev.dragged = false;

                            Events[t].Add(ev);
                        }

                    }
                    if (midi.track[t].Event[i].ToString() == "Meta : Set Tempo")
                    {
                        string str = (string)midi.track[t].Discription[i];
                        
                        int tempo = str.IndexOf("Microseconds/Quarter Note=");
                        tempo = Util.stringDigitToInt(str, tempo + "Microseconds/Quarter Note=".Length, str.Length);
                       

                        //Console.WriteLine("tempo="+tempo);

                        EventBMS ev = new EventBMS();

                        ev.time = position;
                        ev.metadata = tempo;

                        keyBPM.Add(ev);
                    }
                }
            }
            



        }


    }
}

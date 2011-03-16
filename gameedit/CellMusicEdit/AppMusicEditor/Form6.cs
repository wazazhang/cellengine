using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Cell.LibMidi;

namespace Cell.AppMusicEditor
{
    public partial class Form6 : Form
    {


        private ListViewItem[][] listViewItem;


        public Form6(Midi midi)
        {
            InitializeComponent();

            //GUI
            System.Windows.Forms.TreeNode[] tracks = new TreeNode[midi.header.Tracks];
            for (int i = 0; i < tracks.Length; i++)
            {
                tracks[i] = new TreeNode("Track " + i + " (" + midi.track[i].DataSize + ")", 2, 2);
            }

            string hdstr = "Header";
      
            System.Windows.Forms.TreeNode header =
                new System.Windows.Forms.TreeNode(hdstr, 1, 1, tracks);

            this.treeView1.Nodes.AddRange(
                new System.Windows.Forms.TreeNode[] 
				    {
					    header
				    }
                );

            this.treeView1.ExpandAll();

            //list view
            this.listViewItem = new ListViewItem[midi.header.Tracks][];

            for (int l = 0; l < listViewItem.Length; l++)
            {
                listViewItem[l] = new ListViewItem[midi.track[l].DeltaTime.Count];

                string[] dt = new string[listViewItem[l].Length];
                string[] ev = new string[listViewItem[l].Length];
                string[] ds = new string[listViewItem[l].Length];
                string[] ed = new string[listViewItem[l].Length];

                for (int i = 0; i < listViewItem[l].Length; i++)
                {
                    dt[i] = midi.track[l].DeltaTime[i].ToString();
                    ev[i] = midi.track[l].Event[i].ToString();
                    ds[i] = midi.track[l].Discription[i].ToString();
                    ed[i] = midi.track[l].HexData[i].ToString();

                    listViewItem[l][i] = new ListViewItem(new string[] { dt[i], ev[i], ds[i], ed[i] });

                    if (ev[i].IndexOf(TrackChunk.TextMeta) == 0)
                    {
                        listViewItem[l][i].ImageIndex = 5;
                    }
                    else if (ev[i].IndexOf(TrackChunk.TextSYSEX) == 0)
                    {
                        listViewItem[l][i].ImageIndex = 4;
                    }
                    else
                    {
                        listViewItem[l][i].ImageIndex = 3;
                    }
                }

            }
        }


        private void Form6_Load(object sender, EventArgs e)
        {

        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            //e.Node.Index;
            if (e.Node.Parent != null)
            {
                this.listView1.Items.Clear();
                this.listView1.Items.AddRange(listViewItem[e.Node.Index]);

            }
        }

        private void listView1_DoubleClick(object sender, EventArgs e)
        {
            string[][] text = new string[4][];

            text[0] = new string[2];
            text[0][0] = "(HexData)";
            text[0][1] = this.listView1.FocusedItem.SubItems[3].Text;

            text[1] = new string[2];
            text[1][0] = "Deta-Time";
            text[1][1] = this.listView1.FocusedItem.SubItems[0].Text;

            text[2] = new string[2];
            text[2][0] = "Event";
            text[2][1] = this.listView1.FocusedItem.SubItems[1].Text;

            text[3] = new string[2];
            text[3][0] = "Discription";
            text[3][1] = this.listView1.FocusedItem.SubItems[2].Text;

            Form3 info = new Form3(text);
            info.ShowDialog(this);
        }
    }
}
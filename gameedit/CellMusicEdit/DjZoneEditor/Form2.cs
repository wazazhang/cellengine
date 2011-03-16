using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using Microsoft.DirectX.AudioVideoPlayback;

using Cell.LibMidi;


namespace DjZoneEditor 
{
	/// <summary>
	/// Form2 的摘要说明。
	/// </summary>
	public class Form2 : System.Windows.Forms.Form
	{

		private Audio soundman1;
		private Midi midi ;
		private string midiFileName;


		private ListViewItem[][] listViewItem;

		private System.Windows.Forms.ToolBar toolBar1;
		private System.Windows.Forms.Panel panel1;
		private System.Windows.Forms.Splitter splitter1;
		private System.Windows.Forms.Panel panel2;
		private System.Windows.Forms.Panel panel3;
		private System.Windows.Forms.Panel panel5;
		private System.Windows.Forms.Splitter splitter3;
		private System.Windows.Forms.Panel panel6;
		private System.Windows.Forms.Panel panel7;
		private System.Windows.Forms.ListView listView1;
		private System.Windows.Forms.ImageList imageList1;
		private System.Windows.Forms.ImageList imageList2;
		private System.ComponentModel.IContainer components;


		private System.Windows.Forms.ColumnHeader columnHeader1;
		private System.Windows.Forms.Button button1;
		private System.Windows.Forms.TrackBar trackBar1;
		private System.Windows.Forms.Button button3;
		private System.Windows.Forms.Button button4;
		private System.Windows.Forms.ImageList imageList3;
		private System.Windows.Forms.Panel panel8;
		private System.Windows.Forms.TrackBar trackBar3;
		private System.Windows.Forms.Splitter splitter2;
		private System.Windows.Forms.TreeView treeView1;
		private System.Windows.Forms.Panel panel4;
		private System.Windows.Forms.ColumnHeader columnHeader3;
		private System.Windows.Forms.Button button5;
		private System.Timers.Timer timer1;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.ColumnHeader columnHeader2;




		public Form2(Midi mid,string file)
		{
			//
			// Windows 窗体设计器支持所必需的
			//
			InitializeComponent();

			//
			// TODO: 在 InitializeComponent 调用后添加任何构造函数代码
			//
            try
            {
                
                midi = mid;
                midiFileName = file;

            }catch(Exception err)
            {
                System.Windows.Forms.MessageBox.Show(err.Message);
            }


			
		}

		/// <summary>
		/// 清理所有正在使用的资源。
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			Console.Write("Closing...... ");
			if( disposing )
			{
				try
				{
                    if (soundman1 != null)
                    {
                        soundman1.Stop();
                        soundman1.Dispose();
                    }
					if(components != null)
					{
						components.Dispose();
					}
				}
				catch(Exception err)
				{
					throw(err);
				}
			
			}
			base.Dispose( disposing );
			Console.WriteLine("OK !");
		}

		#region Windows 窗体设计器生成的代码
		/// <summary>
		/// 设计器支持所需的方法 - 不要使用代码编辑器修改
		/// 此方法的内容。
		/// </summary>
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(Form2));
			this.toolBar1 = new System.Windows.Forms.ToolBar();
			this.imageList1 = new System.Windows.Forms.ImageList(this.components);
			this.panel1 = new System.Windows.Forms.Panel();
			this.panel5 = new System.Windows.Forms.Panel();
			this.splitter2 = new System.Windows.Forms.Splitter();
			this.panel4 = new System.Windows.Forms.Panel();
			this.treeView1 = new System.Windows.Forms.TreeView();
			this.imageList2 = new System.Windows.Forms.ImageList(this.components);
			this.listView1 = new System.Windows.Forms.ListView();
			this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader3 = new System.Windows.Forms.ColumnHeader();
			this.splitter1 = new System.Windows.Forms.Splitter();
			this.panel2 = new System.Windows.Forms.Panel();
			this.panel6 = new System.Windows.Forms.Panel();
			this.splitter3 = new System.Windows.Forms.Splitter();
			this.panel3 = new System.Windows.Forms.Panel();
			this.label1 = new System.Windows.Forms.Label();
			this.button5 = new System.Windows.Forms.Button();
			this.trackBar3 = new System.Windows.Forms.TrackBar();
			this.button4 = new System.Windows.Forms.Button();
			this.button3 = new System.Windows.Forms.Button();
			this.button1 = new System.Windows.Forms.Button();
			this.panel7 = new System.Windows.Forms.Panel();
			this.panel8 = new System.Windows.Forms.Panel();
			this.trackBar1 = new System.Windows.Forms.TrackBar();
			this.imageList3 = new System.Windows.Forms.ImageList(this.components);
			this.timer1 = new System.Timers.Timer();
			this.panel1.SuspendLayout();
			this.panel5.SuspendLayout();
			this.panel4.SuspendLayout();
			this.panel2.SuspendLayout();
			this.panel6.SuspendLayout();
			this.panel3.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.trackBar3)).BeginInit();
			this.panel7.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.trackBar1)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.timer1)).BeginInit();
			this.SuspendLayout();
			// 
			// toolBar1
			// 
			this.toolBar1.Appearance = System.Windows.Forms.ToolBarAppearance.Flat;
			this.toolBar1.ButtonSize = new System.Drawing.Size(15, 15);
			this.toolBar1.DropDownArrows = true;
			this.toolBar1.ImageList = this.imageList1;
			this.toolBar1.Location = new System.Drawing.Point(0, 0);
			this.toolBar1.Name = "toolBar1";
			this.toolBar1.ShowToolTips = true;
			this.toolBar1.Size = new System.Drawing.Size(792, 21);
			this.toolBar1.TabIndex = 0;
			this.toolBar1.Wrappable = false;
			// 
			// imageList1
			// 
			this.imageList1.ImageSize = new System.Drawing.Size(15, 15);
			this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
			this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// panel1
			// 
			this.panel1.Controls.Add(this.panel5);
			this.panel1.Controls.Add(this.panel4);
			this.panel1.Dock = System.Windows.Forms.DockStyle.Left;
			this.panel1.Location = new System.Drawing.Point(0, 21);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(136, 473);
			this.panel1.TabIndex = 1;
			// 
			// panel5
			// 
			this.panel5.Controls.Add(this.splitter2);
			this.panel5.Dock = System.Windows.Forms.DockStyle.Fill;
			this.panel5.Location = new System.Drawing.Point(0, 320);
			this.panel5.Name = "panel5";
			this.panel5.Size = new System.Drawing.Size(136, 153);
			this.panel5.TabIndex = 2;
			// 
			// splitter2
			// 
			this.splitter2.Dock = System.Windows.Forms.DockStyle.Top;
			this.splitter2.Location = new System.Drawing.Point(0, 0);
			this.splitter2.Name = "splitter2";
			this.splitter2.Size = new System.Drawing.Size(136, 8);
			this.splitter2.TabIndex = 0;
			this.splitter2.TabStop = false;
			// 
			// panel4
			// 
			this.panel4.Controls.Add(this.treeView1);
			this.panel4.Dock = System.Windows.Forms.DockStyle.Top;
			this.panel4.Location = new System.Drawing.Point(0, 0);
			this.panel4.Name = "panel4";
			this.panel4.Size = new System.Drawing.Size(136, 320);
			this.panel4.TabIndex = 0;
			// 
			// treeView1
			// 
			this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.treeView1.ImageList = this.imageList2;
			this.treeView1.ItemHeight = 16;
			this.treeView1.Location = new System.Drawing.Point(0, 0);
			this.treeView1.Name = "treeView1";
			this.treeView1.Size = new System.Drawing.Size(136, 320);
			this.treeView1.TabIndex = 0;
			this.treeView1.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeView1_AfterSelect);
			// 
			// imageList2
			// 
			this.imageList2.ImageSize = new System.Drawing.Size(16, 16);
			this.imageList2.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList2.ImageStream")));
			this.imageList2.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// listView1
			// 
			this.listView1.AutoArrange = false;
			this.listView1.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																						this.columnHeader1,
																						this.columnHeader2,
																						this.columnHeader3});
			this.listView1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.listView1.FullRowSelect = true;
			this.listView1.Location = new System.Drawing.Point(0, 0);
			this.listView1.MultiSelect = false;
			this.listView1.Name = "listView1";
			this.listView1.Size = new System.Drawing.Size(160, 473);
			this.listView1.SmallImageList = this.imageList2;
			this.listView1.TabIndex = 0;
			this.listView1.View = System.Windows.Forms.View.Details;
			this.listView1.ItemActivate += new System.EventHandler(this.listView1_ItemActivate);
			this.listView1.DoubleClick += new System.EventHandler(this.listView1_DoubleClick);
			this.listView1.SelectedIndexChanged += new System.EventHandler(this.listView1_SelectedIndexChanged_1);
			// 
			// columnHeader1
			// 
			this.columnHeader1.Text = "Delta-Time";
			this.columnHeader1.Width = 74;
			// 
			// columnHeader2
			// 
			this.columnHeader2.Text = "Event";
			this.columnHeader2.Width = 80;
			// 
			// columnHeader3
			// 
			this.columnHeader3.Text = "Discription";
			this.columnHeader3.Width = 600;
			// 
			// splitter1
			// 
			this.splitter1.Location = new System.Drawing.Point(136, 21);
			this.splitter1.Name = "splitter1";
			this.splitter1.Size = new System.Drawing.Size(8, 473);
			this.splitter1.TabIndex = 2;
			this.splitter1.TabStop = false;
			// 
			// panel2
			// 
			this.panel2.Controls.Add(this.panel6);
			this.panel2.Controls.Add(this.splitter3);
			this.panel2.Controls.Add(this.panel3);
			this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.panel2.Location = new System.Drawing.Point(144, 21);
			this.panel2.Name = "panel2";
			this.panel2.Size = new System.Drawing.Size(648, 473);
			this.panel2.TabIndex = 3;
			this.panel2.Paint += new System.Windows.Forms.PaintEventHandler(this.panel2_Paint);
			// 
			// panel6
			// 
			this.panel6.Controls.Add(this.listView1);
			this.panel6.Dock = System.Windows.Forms.DockStyle.Fill;
			this.panel6.Location = new System.Drawing.Point(488, 0);
			this.panel6.Name = "panel6";
			this.panel6.Size = new System.Drawing.Size(160, 473);
			this.panel6.TabIndex = 2;
			// 
			// splitter3
			// 
			this.splitter3.Location = new System.Drawing.Point(480, 0);
			this.splitter3.Name = "splitter3";
			this.splitter3.Size = new System.Drawing.Size(8, 473);
			this.splitter3.TabIndex = 1;
			this.splitter3.TabStop = false;
			// 
			// panel3
			// 
			this.panel3.Controls.Add(this.label1);
			this.panel3.Controls.Add(this.button5);
			this.panel3.Controls.Add(this.trackBar3);
			this.panel3.Controls.Add(this.button4);
			this.panel3.Controls.Add(this.button3);
			this.panel3.Controls.Add(this.button1);
			this.panel3.Controls.Add(this.panel7);
			this.panel3.Dock = System.Windows.Forms.DockStyle.Left;
			this.panel3.Location = new System.Drawing.Point(0, 0);
			this.panel3.Name = "panel3";
			this.panel3.Size = new System.Drawing.Size(480, 473);
			this.panel3.TabIndex = 0;
			// 
			// label1
			// 
			this.label1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.label1.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.label1.Location = new System.Drawing.Point(0, 457);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(480, 16);
			this.label1.TabIndex = 8;
			this.label1.Text = "label1";
			// 
			// button5
			// 
			this.button5.Location = new System.Drawing.Point(112, 360);
			this.button5.Name = "button5";
			this.button5.Size = new System.Drawing.Size(56, 24);
			this.button5.TabIndex = 7;
			this.button5.Text = "Del All";
			// 
			// trackBar3
			// 
			this.trackBar3.Cursor = System.Windows.Forms.Cursors.Hand;
			this.trackBar3.Location = new System.Drawing.Point(0, 328);
			this.trackBar3.Name = "trackBar3";
			this.trackBar3.Orientation = System.Windows.Forms.Orientation.Vertical;
			this.trackBar3.Size = new System.Drawing.Size(42, 88);
			this.trackBar3.TabIndex = 6;
			this.trackBar3.TickStyle = System.Windows.Forms.TickStyle.Both;
			// 
			// button4
			// 
			this.button4.Location = new System.Drawing.Point(112, 328);
			this.button4.Name = "button4";
			this.button4.Size = new System.Drawing.Size(56, 24);
			this.button4.TabIndex = 4;
			this.button4.Text = "Stop";
			this.button4.Click += new System.EventHandler(this.button4_Click);
			// 
			// button3
			// 
			this.button3.Location = new System.Drawing.Point(48, 360);
			this.button3.Name = "button3";
			this.button3.Size = new System.Drawing.Size(56, 24);
			this.button3.TabIndex = 3;
			this.button3.Text = "Add All";
			// 
			// button1
			// 
			this.button1.Location = new System.Drawing.Point(48, 328);
			this.button1.Name = "button1";
			this.button1.Size = new System.Drawing.Size(56, 24);
			this.button1.TabIndex = 1;
			this.button1.Text = "Play";
			this.button1.Click += new System.EventHandler(this.button1_Click);
			// 
			// panel7
			// 
			this.panel7.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.panel7.Controls.Add(this.panel8);
			this.panel7.Controls.Add(this.trackBar1);
			this.panel7.Dock = System.Windows.Forms.DockStyle.Top;
			this.panel7.Location = new System.Drawing.Point(0, 0);
			this.panel7.Name = "panel7";
			this.panel7.Size = new System.Drawing.Size(480, 320);
			this.panel7.TabIndex = 0;
			// 
			// panel8
			// 
			this.panel8.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(0)), ((System.Byte)(192)), ((System.Byte)(192)));
			this.panel8.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.panel8.Location = new System.Drawing.Point(56, 8);
			this.panel8.Name = "panel8";
			this.panel8.Size = new System.Drawing.Size(376, 272);
			this.panel8.TabIndex = 4;
			// 
			// trackBar1
			// 
			this.trackBar1.Cursor = System.Windows.Forms.Cursors.Hand;
			this.trackBar1.Dock = System.Windows.Forms.DockStyle.Left;
			this.trackBar1.Location = new System.Drawing.Point(0, 0);
			this.trackBar1.Maximum = 100;
			this.trackBar1.Name = "trackBar1";
			this.trackBar1.Orientation = System.Windows.Forms.Orientation.Vertical;
			this.trackBar1.Size = new System.Drawing.Size(42, 316);
			this.trackBar1.TabIndex = 3;
			this.trackBar1.TickFrequency = 2;
			this.trackBar1.TickStyle = System.Windows.Forms.TickStyle.Both;
			this.trackBar1.Scroll += new System.EventHandler(this.trackBar1_Scroll);
			// 
			// imageList3
			// 
			this.imageList3.ImageSize = new System.Drawing.Size(16, 16);
			this.imageList3.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// timer1
			// 
			this.timer1.Enabled = true;
			this.timer1.Interval = 1;
			this.timer1.SynchronizingObject = this;
			this.timer1.Elapsed += new System.Timers.ElapsedEventHandler(this.timer1_Elapsed);
			// 
			// Form2
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
			this.AutoScroll = true;
			this.ClientSize = new System.Drawing.Size(792, 494);
			this.Controls.Add(this.panel2);
			this.Controls.Add(this.splitter1);
			this.Controls.Add(this.panel1);
			this.Controls.Add(this.toolBar1);
			this.Name = "Form2";
			this.Text = "Form2";
			this.Load += new System.EventHandler(this.Form2_Load);
			this.Closed += new System.EventHandler(this.Form2_Closed);
			this.VisibleChanged += new System.EventHandler(this.Form2_VisibleChanged);
			this.panel1.ResumeLayout(false);
			this.panel5.ResumeLayout(false);
			this.panel4.ResumeLayout(false);
			this.panel2.ResumeLayout(false);
			this.panel6.ResumeLayout(false);
			this.panel3.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.trackBar3)).EndInit();
			this.panel7.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.trackBar1)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.timer1)).EndInit();
			this.ResumeLayout(false);

		}
		#endregion

		private void listView1_SelectedIndexChanged(object sender, System.EventArgs e)
		{
		
		}

		private void panel2_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
		
		}


		private void Form2_Load(object sender, System.EventArgs e)
		{
			this.Text = midiFileName;

			//tree view
			System.Windows.Forms.TreeNode[] tracks = new TreeNode[midi.header.Tracks];
			for(int i=0;i<tracks.Length;i++)
			{
				tracks[i] = new TreeNode("Track "+i+" ("+midi.track[i].DataSize+")",2,2);
			}
			
			string hdstr = "";
			try
			{
				hdstr = midiFileName.Substring(midiFileName.LastIndexOf("\\")+1);
			}
			catch(Exception err)
			{
				hdstr = "Header";
				Console.WriteLine(err.Message);
			}
			

			System.Windows.Forms.TreeNode header = 
				new System.Windows.Forms.TreeNode(hdstr,1,1,tracks);

			this.treeView1.Nodes.AddRange(
				new System.Windows.Forms.TreeNode[] 
				{
					header
				}
				);

			this.treeView1.ExpandAll();

			//list view
			this.listViewItem = new ListViewItem[midi.header.Tracks][];

			for(int l=0;l<listViewItem.Length;l++)
			{
				listViewItem[l] = new ListViewItem[midi.track[l].DeltaTime.Count];

				string[] dt = new string[listViewItem[l].Length];
				string[] ev = new string[listViewItem[l].Length];
				string[] ds = new string[listViewItem[l].Length];
				string[] ed = new string[listViewItem[l].Length];

				for(int i=0;i<listViewItem[l].Length;i++)
				{
					dt[i] = midi.track[l].DeltaTime[i].ToString();
					ev[i] = midi.track[l].Event[i].ToString();
					ds[i] = midi.track[l].Discription[i].ToString();
					ed[i] = midi.track[l].EData[i].ToString();

					listViewItem[l][i] = new ListViewItem(new string[]{dt[i],ev[i],ds[i],ed[i]});
	
					if(ev[i].IndexOf(TrackChunk.TextMeta)==0)
					{
						listViewItem[l][i].ImageIndex = 5 ;
					}
					else if(ev[i].IndexOf(TrackChunk.TextSYSEX)==0)
					{
						listViewItem[l][i].ImageIndex = 4 ;
					}
					else
					{
						listViewItem[l][i].ImageIndex = 3 ;
					}
				}

			}

            soundman1 = new Audio(midiFileName);

			this.trackBar1.Minimum = 0;
			this.trackBar1.Maximum = (int)(soundman1.Duration*1000);
			this.trackBar1.TickFrequency = this.trackBar1.Maximum/100*2 ;
			this.trackBar1.SmallChange = this.trackBar1.Maximum/100 ;
			this.trackBar1.Value = 0;

            
		}



		private void Form2_Closed(object sender, System.EventArgs e)
		{
            timer1.Stop();
		}

		private void Form2_VisibleChanged(object sender, System.EventArgs e)
		{
	
		}

		private void treeView1_AfterSelect(object sender, System.Windows.Forms.TreeViewEventArgs e)
		{
			//e.Node.Index;
			if(e.Node.Parent!=null)
			{
				//Console.WriteLine(e.Node.Index);

				this.listView1.Items.Clear();
				this.listView1.Items.AddRange(listViewItem[e.Node.Index]);
				
			}
			
		}



		private void listBox1_SelectedIndexChanged(object sender, System.EventArgs e)
		{
		
		}

		private void listView1_SelectedIndexChanged_1(object sender, System.EventArgs e)
		{
			

		}

		private void listView1_DoubleClick(object sender, System.EventArgs e)
		{
			
			string[][] text = new string[4][];

			text[0] = new string[2];
			text[0][0] ="(HexData)";
			text[0][1] =this.listView1.FocusedItem.SubItems[3].Text;

			text[1] = new string[2];
			text[1][0] ="Deta-Time";
			text[1][1] =this.listView1.FocusedItem.SubItems[0].Text;

			text[2] = new string[2];
			text[2][0] ="Event";
			text[2][1] =this.listView1.FocusedItem.SubItems[1].Text;

			text[3] = new string[2];
			text[3][0] ="Discription";
			text[3][1] =this.listView1.FocusedItem.SubItems[2].Text;

			Form3 info = new Form3(text);
			info.ShowDialog(this);
		}

		private void listView1_ItemActivate(object sender, System.EventArgs e)
		{
			
		}

		private void button1_Click(object sender, System.EventArgs e)
		{
			if(soundman1!=null)
			{
				if(!soundman1.Playing)
				{
					
					soundman1.Play();
					this.trackBar1.Value = (int)(soundman1.CurrentPosition*1000);
					this.button1.Text = "Pause";
				}
				else
				{
					soundman1.Pause();
					this.trackBar1.Value = (int)(soundman1.CurrentPosition*1000);
					this.button1.Text = "Play";
				}
				
			}
		}


		private void button4_Click(object sender, System.EventArgs e)
		{
			if(soundman1!=null)
			{
				if(!soundman1.Stopped)
				{
					soundman1.Stop();
					this.trackBar1.Value = (int)(soundman1.CurrentPosition*1000);
				}
			}
		}

		private void trackBar1_Scroll(object sender, System.EventArgs e)
		{
			if(soundman1!=null)
			{
				if(!soundman1.Playing)
				{
					soundman1.CurrentPosition = ((double)this.trackBar1.Value)/1000;
				}
				
			}
		}

		private void timer1_Elapsed(object sender, System.Timers.ElapsedEventArgs e)
		{
			this.label1.Text = "" ;
			if(soundman1!=null)
			{
				if(soundman1.Playing)
				{
					this.label1.Text += "Playing , ";
					this.trackBar1.Value = (int)(soundman1.CurrentPosition*1000);
				}
				if(soundman1.Paused)
				{
					this.label1.Text += "Paused , ";
				}
				if(soundman1.Stopped)
				{
					this.label1.Text += "Stoped , ";
				}

				this.label1.Text += soundman1.CurrentPosition*1000;
				this.label1.Text += "/";
				this.label1.Text += soundman1.StopPosition/10000+" , ";
				this.label1.Text += "Time="+soundman1.Duration+"(s)";

			}



		}
//---------------------------------------------------------------------------------------------------------------------------------


	}
}

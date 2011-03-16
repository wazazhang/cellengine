using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using Microsoft.DirectX.AudioVideoPlayback;
using Microsoft.DirectX.DirectSound;
using Microsoft.DirectX.Direct3D;

using Cell.LibMidi;
using Cell.LibGame;

namespace Cell.AppMusicEditor 
{
	/// <summary>
	/// Form2 的摘要说明。
	/// </summary>
	public class Form2 : System.Windows.Forms.Form 
	{
        public string workPath;
        public string midiFileName;
        public string djzFileName;

		private Audio soundman1 = null;
		private Midi midi ;

        private DJZ djz;

        private int LineCount = 12;
        private int ChannelIndex = 0;
        private DJZCanvas[] srcCanvas;
        private DJZCanvas desCanvas;
        private Tiles tiles1;
        private Tiles tiles2;
        private int tile_w;
        private int tile_h;
       

		private System.Windows.Forms.ToolBar toolBar1;
		private System.Windows.Forms.Panel panel1;
		private System.Windows.Forms.Splitter splitter1;
		private System.Windows.Forms.Panel panel2;
		private System.Windows.Forms.Panel panel3;
		private System.Windows.Forms.Panel panel5;
		private System.Windows.Forms.Splitter splitter3;
        private System.Windows.Forms.Panel panel6;
		private System.Windows.Forms.ImageList imageList1;
		private System.Windows.Forms.ImageList imageList2;
        private System.ComponentModel.IContainer components;
		private System.Windows.Forms.Button button1;
		private System.Windows.Forms.TrackBar trackBar1;
		private System.Windows.Forms.Button button3;
		private System.Windows.Forms.Button button4;
        private System.Windows.Forms.ImageList imageList3;
        private System.Windows.Forms.TrackBar trackBar3;
        private System.Windows.Forms.TreeView treeView1;
        private System.Windows.Forms.Button button5;
        private PictureBox pictureBox1;
        private PictureBox pictureBox2;
        private Timer timer1;
        private SplitContainer splitContainer1;
        private Splitter splitter2;
        private Button button2;
        private Panel panel4;
        private Splitter splitter4;
        private Panel panel9;
        private Label label1;
        private Panel panel7;
        private Panel panel8;
        private Button button6;
        private Button button7;
        private Button button8;
        private NumericUpDown numericUpDown1;
        private TextBox textBox1;
        private ContextMenuStrip contextMenuStrip1;
        private ToolStripMenuItem delThisLineNearToolStripMenuItem;
        private ToolStripMenuItem delThisLineToolStripMenuItem;
        private ContextMenuStrip contextMenuStrip2;
        private ToolStripMenuItem toolStripMenuItem1;
        private ToolStripMenuItem delThisLineToolStripMenuItem1;
        private ImageList imageList4;




		public Form2(string file,int lineCount)
		{
			//
			// Windows 窗体设计器支持所必需的
			//
			InitializeComponent();

			//
			// TODO: 在 InitializeComponent 调用后添加任何构造函数代码
			//
            LineCount = lineCount;
            midiFileName = file;
            workPath = System.IO.Path.GetFullPath(file);
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form2));
            this.toolBar1 = new System.Windows.Forms.ToolBar();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel5 = new System.Windows.Forms.Panel();
            this.panel4 = new System.Windows.Forms.Panel();
            this.button7 = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.splitter2 = new System.Windows.Forms.Splitter();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.imageList2 = new System.Windows.Forms.ImageList(this.components);
            this.button2 = new System.Windows.Forms.Button();
            this.button5 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.splitter1 = new System.Windows.Forms.Splitter();
            this.panel2 = new System.Windows.Forms.Panel();
            this.panel6 = new System.Windows.Forms.Panel();
            this.splitter3 = new System.Windows.Forms.Splitter();
            this.panel3 = new System.Windows.Forms.Panel();
            this.splitter4 = new System.Windows.Forms.Splitter();
            this.panel9 = new System.Windows.Forms.Panel();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
            this.button8 = new System.Windows.Forms.Button();
            this.button6 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.panel7 = new System.Windows.Forms.Panel();
            this.panel8 = new System.Windows.Forms.Panel();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.contextMenuStrip2 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.delThisLineToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.delThisLineToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.delThisLineNearToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.trackBar3 = new System.Windows.Forms.TrackBar();
            this.trackBar1 = new System.Windows.Forms.TrackBar();
            this.imageList3 = new System.Windows.Forms.ImageList(this.components);
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.imageList4 = new System.Windows.Forms.ImageList(this.components);
            this.panel1.SuspendLayout();
            this.panel5.SuspendLayout();
            this.panel4.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel3.SuspendLayout();
            this.panel9.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
            this.panel7.SuspendLayout();
            this.panel8.SuspendLayout();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            this.contextMenuStrip2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.contextMenuStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBar3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.trackBar1)).BeginInit();
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
            this.toolBar1.Size = new System.Drawing.Size(992, 21);
            this.toolBar1.TabIndex = 0;
            this.toolBar1.Wrappable = false;
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "");
            this.imageList1.Images.SetKeyName(1, "");
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.panel5);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Left;
            this.panel1.Location = new System.Drawing.Point(0, 21);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(222, 589);
            this.panel1.TabIndex = 1;
            // 
            // panel5
            // 
            this.panel5.Controls.Add(this.panel4);
            this.panel5.Controls.Add(this.splitter2);
            this.panel5.Controls.Add(this.treeView1);
            this.panel5.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel5.Location = new System.Drawing.Point(0, 0);
            this.panel5.Name = "panel5";
            this.panel5.Size = new System.Drawing.Size(222, 589);
            this.panel5.TabIndex = 2;
            // 
            // panel4
            // 
            this.panel4.AutoScroll = true;
            this.panel4.Controls.Add(this.button7);
            this.panel4.Controls.Add(this.button1);
            this.panel4.Controls.Add(this.button4);
            this.panel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel4.Location = new System.Drawing.Point(0, 330);
            this.panel4.Name = "panel4";
            this.panel4.Size = new System.Drawing.Size(222, 259);
            this.panel4.TabIndex = 2;
            // 
            // button7
            // 
            this.button7.Location = new System.Drawing.Point(12, 36);
            this.button7.Name = "button7";
            this.button7.Size = new System.Drawing.Size(83, 24);
            this.button7.TabIndex = 5;
            this.button7.Text = "Pause";
            this.button7.Click += new System.EventHandler(this.button7_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(12, 6);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(83, 24);
            this.button1.TabIndex = 1;
            this.button1.Text = "Play";
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(12, 66);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(83, 24);
            this.button4.TabIndex = 4;
            this.button4.Text = "Stop";
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // splitter2
            // 
            this.splitter2.Dock = System.Windows.Forms.DockStyle.Top;
            this.splitter2.Location = new System.Drawing.Point(0, 320);
            this.splitter2.Name = "splitter2";
            this.splitter2.Size = new System.Drawing.Size(222, 10);
            this.splitter2.TabIndex = 1;
            this.splitter2.TabStop = false;
            // 
            // treeView1
            // 
            this.treeView1.Dock = System.Windows.Forms.DockStyle.Top;
            this.treeView1.ImageIndex = 0;
            this.treeView1.ImageList = this.imageList2;
            this.treeView1.ItemHeight = 16;
            this.treeView1.Location = new System.Drawing.Point(0, 0);
            this.treeView1.Name = "treeView1";
            this.treeView1.SelectedImageIndex = 0;
            this.treeView1.Size = new System.Drawing.Size(222, 320);
            this.treeView1.TabIndex = 0;
            this.treeView1.NodeMouseDoubleClick += new System.Windows.Forms.TreeNodeMouseClickEventHandler(this.treeView1_NodeMouseDoubleClick);
            this.treeView1.DoubleClick += new System.EventHandler(this.treeView1_DoubleClick);
            this.treeView1.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeView1_AfterSelect);
            // 
            // imageList2
            // 
            this.imageList2.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList2.ImageStream")));
            this.imageList2.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList2.Images.SetKeyName(0, "");
            this.imageList2.Images.SetKeyName(1, "");
            this.imageList2.Images.SetKeyName(2, "");
            this.imageList2.Images.SetKeyName(3, "");
            this.imageList2.Images.SetKeyName(4, "");
            this.imageList2.Images.SetKeyName(5, "");
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(95, 46);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(83, 24);
            this.button2.TabIndex = 8;
            this.button2.Text = "Del All";
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button5
            // 
            this.button5.Location = new System.Drawing.Point(6, 46);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(83, 24);
            this.button5.TabIndex = 7;
            this.button5.Text = "Del Track";
            this.button5.Click += new System.EventHandler(this.button5_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(6, 16);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(83, 24);
            this.button3.TabIndex = 3;
            this.button3.Text = "Add Track";
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // splitter1
            // 
            this.splitter1.Location = new System.Drawing.Point(222, 21);
            this.splitter1.Name = "splitter1";
            this.splitter1.Size = new System.Drawing.Size(10, 589);
            this.splitter1.TabIndex = 2;
            this.splitter1.TabStop = false;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.panel6);
            this.panel2.Controls.Add(this.splitter3);
            this.panel2.Controls.Add(this.panel3);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(232, 21);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(760, 589);
            this.panel2.TabIndex = 3;
            // 
            // panel6
            // 
            this.panel6.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel6.Location = new System.Drawing.Point(663, 0);
            this.panel6.Name = "panel6";
            this.panel6.Size = new System.Drawing.Size(97, 589);
            this.panel6.TabIndex = 2;
            // 
            // splitter3
            // 
            this.splitter3.Location = new System.Drawing.Point(653, 0);
            this.splitter3.Name = "splitter3";
            this.splitter3.Size = new System.Drawing.Size(10, 589);
            this.splitter3.TabIndex = 1;
            this.splitter3.TabStop = false;
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.splitter4);
            this.panel3.Controls.Add(this.panel9);
            this.panel3.Controls.Add(this.panel7);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Left;
            this.panel3.Location = new System.Drawing.Point(0, 0);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(653, 589);
            this.panel3.TabIndex = 0;
            // 
            // splitter4
            // 
            this.splitter4.Dock = System.Windows.Forms.DockStyle.Top;
            this.splitter4.Location = new System.Drawing.Point(0, 320);
            this.splitter4.Name = "splitter4";
            this.splitter4.Size = new System.Drawing.Size(653, 10);
            this.splitter4.TabIndex = 10;
            this.splitter4.TabStop = false;
            // 
            // panel9
            // 
            this.panel9.AutoScroll = true;
            this.panel9.Controls.Add(this.textBox1);
            this.panel9.Controls.Add(this.numericUpDown1);
            this.panel9.Controls.Add(this.button8);
            this.panel9.Controls.Add(this.button6);
            this.panel9.Controls.Add(this.label1);
            this.panel9.Controls.Add(this.button2);
            this.panel9.Controls.Add(this.button3);
            this.panel9.Controls.Add(this.button5);
            this.panel9.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel9.Location = new System.Drawing.Point(0, 320);
            this.panel9.Name = "panel9";
            this.panel9.Size = new System.Drawing.Size(653, 269);
            this.panel9.TabIndex = 9;
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(487, 16);
            this.textBox1.Name = "textBox1";
            this.textBox1.ReadOnly = true;
            this.textBox1.Size = new System.Drawing.Size(41, 21);
            this.textBox1.TabIndex = 14;
            this.textBox1.Text = "(ms)";
            // 
            // numericUpDown1
            // 
            this.numericUpDown1.Increment = new decimal(new int[] {
            100,
            0,
            0,
            0});
            this.numericUpDown1.Location = new System.Drawing.Point(413, 16);
            this.numericUpDown1.Maximum = new decimal(new int[] {
            10000,
            0,
            0,
            0});
            this.numericUpDown1.Name = "numericUpDown1";
            this.numericUpDown1.Size = new System.Drawing.Size(68, 21);
            this.numericUpDown1.TabIndex = 12;
            this.numericUpDown1.Value = new decimal(new int[] {
            100,
            0,
            0,
            0});
            // 
            // button8
            // 
            this.button8.Location = new System.Drawing.Point(324, 16);
            this.button8.Name = "button8";
            this.button8.Size = new System.Drawing.Size(83, 24);
            this.button8.TabIndex = 11;
            this.button8.Text = "Del Near";
            this.button8.UseVisualStyleBackColor = true;
            this.button8.Click += new System.EventHandler(this.button8_Click);
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(95, 16);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(83, 24);
            this.button6.TabIndex = 10;
            this.button6.Text = "Add All";
            this.button6.Click += new System.EventHandler(this.button6_Click);
            // 
            // label1
            // 
            this.label1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.label1.Location = new System.Drawing.Point(0, 248);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(653, 21);
            this.label1.TabIndex = 9;
            this.label1.Text = "lable";
            // 
            // panel7
            // 
            this.panel7.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.panel7.Controls.Add(this.panel8);
            this.panel7.Controls.Add(this.trackBar3);
            this.panel7.Controls.Add(this.trackBar1);
            this.panel7.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel7.Location = new System.Drawing.Point(0, 0);
            this.panel7.Name = "panel7";
            this.panel7.Size = new System.Drawing.Size(653, 320);
            this.panel7.TabIndex = 0;
            // 
            // panel8
            // 
            this.panel8.AutoScroll = true;
            this.panel8.Controls.Add(this.splitContainer1);
            this.panel8.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel8.Location = new System.Drawing.Point(42, 0);
            this.panel8.Name = "panel8";
            this.panel8.Size = new System.Drawing.Size(565, 316);
            this.panel8.TabIndex = 7;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.AutoScroll = true;
            this.splitContainer1.Panel1.Controls.Add(this.pictureBox2);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.AutoScroll = true;
            this.splitContainer1.Panel2.Controls.Add(this.pictureBox1);
            this.splitContainer1.Size = new System.Drawing.Size(565, 316);
            this.splitContainer1.SplitterDistance = 276;
            this.splitContainer1.TabIndex = 0;
            // 
            // pictureBox2
            // 
            this.pictureBox2.BackColor = System.Drawing.Color.Blue;
            this.pictureBox2.ContextMenuStrip = this.contextMenuStrip2;
            this.pictureBox2.Dock = System.Windows.Forms.DockStyle.Left;
            this.pictureBox2.Location = new System.Drawing.Point(0, 0);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(225, 316);
            this.pictureBox2.TabIndex = 1;
            this.pictureBox2.TabStop = false;
            this.pictureBox2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pictureBox2_MouseDown);
            this.pictureBox2.MouseMove += new System.Windows.Forms.MouseEventHandler(this.pictureBox2_MouseMove);
            this.pictureBox2.Paint += new System.Windows.Forms.PaintEventHandler(this.pictureBox2_Paint);
            this.pictureBox2.MouseUp += new System.Windows.Forms.MouseEventHandler(this.pictureBox2_MouseUp);
            // 
            // contextMenuStrip2
            // 
            this.contextMenuStrip2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripMenuItem1,
            this.delThisLineToolStripMenuItem1});
            this.contextMenuStrip2.Name = "contextMenuStrip1";
            this.contextMenuStrip2.ShowImageMargin = false;
            this.contextMenuStrip2.Size = new System.Drawing.Size(113, 48);
            this.contextMenuStrip2.Closed += new System.Windows.Forms.ToolStripDropDownClosedEventHandler(this.contextMenuStrip2_Closed);
            // 
            // toolStripMenuItem1
            // 
            this.toolStripMenuItem1.Name = "toolStripMenuItem1";
            this.toolStripMenuItem1.Size = new System.Drawing.Size(112, 22);
            this.toolStripMenuItem1.Text = "Add This Line";
            this.toolStripMenuItem1.Click += new System.EventHandler(this.toolStripMenuItem1_Click);
            // 
            // delThisLineToolStripMenuItem1
            // 
            this.delThisLineToolStripMenuItem1.Name = "delThisLineToolStripMenuItem1";
            this.delThisLineToolStripMenuItem1.Size = new System.Drawing.Size(112, 22);
            this.delThisLineToolStripMenuItem1.Text = "Del This Line";
            this.delThisLineToolStripMenuItem1.Click += new System.EventHandler(this.delThisLineToolStripMenuItem1_Click);
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.Color.Blue;
            this.pictureBox1.ContextMenuStrip = this.contextMenuStrip1;
            this.pictureBox1.Dock = System.Windows.Forms.DockStyle.Left;
            this.pictureBox1.Location = new System.Drawing.Point(0, 0);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(232, 316);
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            this.pictureBox1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseDown);
            this.pictureBox1.MouseMove += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseMove);
            this.pictureBox1.Paint += new System.Windows.Forms.PaintEventHandler(this.pictureBox1_Paint);
            this.pictureBox1.MouseUp += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseUp);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.delThisLineToolStripMenuItem,
            this.delThisLineNearToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.ShowImageMargin = false;
            this.contextMenuStrip1.Size = new System.Drawing.Size(135, 48);
            this.contextMenuStrip1.Closed += new System.Windows.Forms.ToolStripDropDownClosedEventHandler(this.contextMenuStrip1_Closed);
            // 
            // delThisLineToolStripMenuItem
            // 
            this.delThisLineToolStripMenuItem.Name = "delThisLineToolStripMenuItem";
            this.delThisLineToolStripMenuItem.Size = new System.Drawing.Size(134, 22);
            this.delThisLineToolStripMenuItem.Text = "Del This Line";
            this.delThisLineToolStripMenuItem.Click += new System.EventHandler(this.delThisLineToolStripMenuItem_Click);
            // 
            // delThisLineNearToolStripMenuItem
            // 
            this.delThisLineNearToolStripMenuItem.Name = "delThisLineNearToolStripMenuItem";
            this.delThisLineNearToolStripMenuItem.Size = new System.Drawing.Size(134, 22);
            this.delThisLineNearToolStripMenuItem.Text = "Del This Line Near";
            this.delThisLineNearToolStripMenuItem.Click += new System.EventHandler(this.delThisLineNearToolStripMenuItem_Click);
            // 
            // trackBar3
            // 
            this.trackBar3.Cursor = System.Windows.Forms.Cursors.Hand;
            this.trackBar3.Dock = System.Windows.Forms.DockStyle.Right;
            this.trackBar3.LargeChange = 10;
            this.trackBar3.Location = new System.Drawing.Point(607, 0);
            this.trackBar3.Maximum = 1000;
            this.trackBar3.Minimum = 10;
            this.trackBar3.Name = "trackBar3";
            this.trackBar3.Orientation = System.Windows.Forms.Orientation.Vertical;
            this.trackBar3.Size = new System.Drawing.Size(42, 316);
            this.trackBar3.TabIndex = 6;
            this.trackBar3.TickFrequency = 20;
            this.trackBar3.TickStyle = System.Windows.Forms.TickStyle.Both;
            this.trackBar3.Value = 100;
            this.trackBar3.Scroll += new System.EventHandler(this.trackBar3_Scroll);
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
            this.imageList3.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList3.ImageStream")));
            this.imageList3.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList3.Images.SetKeyName(0, "Note.bmp");
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 20;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // imageList4
            // 
            this.imageList4.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList4.ImageStream")));
            this.imageList4.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList4.Images.SetKeyName(0, "Null.bmp");
            // 
            // Form2
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(992, 610);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.splitter1);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.toolBar1);
            this.Name = "Form2";
            this.Text = "Form2";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Form2_Closed);
            this.Load += new System.EventHandler(this.Form2_Load);
            this.panel1.ResumeLayout(false);
            this.panel5.ResumeLayout(false);
            this.panel4.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel3.ResumeLayout(false);
            this.panel9.ResumeLayout(false);
            this.panel9.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
            this.panel7.ResumeLayout(false);
            this.panel7.PerformLayout();
            this.panel8.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            this.contextMenuStrip2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.contextMenuStrip1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.trackBar3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.trackBar1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion


		private void Form2_Load(object sender, System.EventArgs e)
		{
            try
            {
                
                //Game
                this.Text = midiFileName;
                

                Console.WriteLine(" ");
                Console.WriteLine("File : "+System.IO.Path.GetFileName(midiFileName));

                byte[] data = System.IO.File.ReadAllBytes(midiFileName);
                soundman1 = new Audio(midiFileName);

                midi = new Midi();
                midi.LoadData(data);
                djz = new DJZ(midi,LineCount);


                srcCanvas = new DJZCanvas[djz.Events.Length];
                for (int t = 0; t < srcCanvas.Length; t++)
                {
                    srcCanvas[t] = new DJZCanvas(djz.Events[t],djz.Controls,djz.Lines, LineCount);
                    srcCanvas[t].LostScope = int.MaxValue;
                    srcCanvas[t].BuffSize = djz.Events[t].Count;
                }

                if (srcCanvas.Length > 1)
                {
                    ChannelIndex = 1;
                }
                else
                {
                    ChannelIndex = 0;
                }

                desCanvas = new DJZCanvas(djz.PlayNode,djz.Controls,djz.Lines, LineCount);
                desCanvas.LostScope = int.MaxValue;
                desCanvas.BuffSize = djz.PlayNode.Count;
               

                tiles1 = new Tiles(srcCanvas.Length);
                tiles2 = new Tiles(srcCanvas.Length);
                for(int i=0;i<srcCanvas.Length;i+=16){
                    tiles1.addTile(imageList3.Images[0], 0, 0, 18, 80, 18, 5);
                    tiles2.addTile(imageList4.Images[0], 0, 0, 18, 80, 18, 5);
                }
                tile_w = 18;
                tile_h = 5;
                
                
                //GUI
                for (int i = 0; i < LineCount; i++)
                {
                    //this.checkedListBox1.Items.Add("Line "+i.ToString());
                }

                this.pictureBox1.Width = LineCount * (tile_w + 1) + 1;
                this.pictureBox2.Width = LineCount * (tile_w + 1) + 1;


                System.Windows.Forms.TreeNode[] tracks = new TreeNode[midi.header.Tracks];
                for (int i = 0; i < tracks.Length; i++)
                {
                    tracks[i] = new TreeNode("Track " + i + " (" + djz.Events[i].Count + ")", 2, 2);
                }

                string hdstr = "";
                try
                {
                    hdstr = midiFileName.Substring(midiFileName.LastIndexOf("\\") + 1);
                }
                catch (Exception err)
                {
                    hdstr = "Header";
                    Console.WriteLine(err.Message);
                }


                System.Windows.Forms.TreeNode header =
                    new System.Windows.Forms.TreeNode(hdstr, 1, 1, tracks);

                this.treeView1.Nodes.AddRange(
                    new System.Windows.Forms.TreeNode[] 
				    {
					    header
				    }
                    );

                this.treeView1.ExpandAll();


                if (soundman1 != null)
                {
                    this.trackBar1.Minimum = 0;
                    this.trackBar1.Maximum = (int)(soundman1.StopPosition /10000);
                    this.trackBar1.TickFrequency = this.trackBar1.Maximum / 100 * 2;
                    this.trackBar1.SmallChange = 1 ;
                    this.trackBar1.LargeChange = 1000;
                    this.trackBar1.Value = 0;

                    this.trackBar3.TickFrequency = this.trackBar3.Maximum / 100 * 2;
                }

                

                //Microsoft.DirectX.DirectSound.Buffer soundBuffer = new Microsoft.DirectX.DirectSound.Buffer( 

               
            }
            catch (Exception err)
            {
                //System.Windows.Forms.MessageBox.Show(err.Message);
                throw (err);
                
            }
		}

        private void Form2_Closed(object sender, FormClosedEventArgs e)
        {
            if (soundman1 != null)
            {
                
                soundman1.Stop();
                soundman1.Dispose();
                soundman1 = null;
                //Console.WriteLine("Close audio");
               
                
            }
        }

        private int FileType = 0;

        public void SaveDJZ()
        {
            System.Windows.Forms.SaveFileDialog saveFileDialog1 = new SaveFileDialog();
            saveFileDialog1.Filter = 
                "BMS new(00~ZZ) (*.bms)|*.bms|"+
                "BMS old(00~FF) (*.bms)|*.bms|"+
                "DJ-ZONE 1.0 (*.djz)|*.djz|"+
                "DJ-ZONE 2.0 (*.djz)|*.djz";
            saveFileDialog1.FileName = System.IO.Path.GetFileNameWithoutExtension(midiFileName);

            if (DialogResult.OK == saveFileDialog1.ShowDialog())
            {
                FileType = saveFileDialog1.FilterIndex;

                Console.WriteLine("FileType = "+FileType);
                djzFileName = saveFileDialog1.FileName;
                DJZFile djzFile = new DJZFile(djz,LineCount);

                byte[] data;
                string str;

                switch (FileType)
                {
                    case 1:
                        str = djzFile.toBMS(false, System.IO.Path.GetFileName(midiFileName));
                        System.IO.File.WriteAllText(djzFileName, str);
                        System.Console.WriteLine("save " + str.Length);
                        break;
                    case 2:
                        str = djzFile.toBMS(true, System.IO.Path.GetFileName(midiFileName));
                        System.IO.File.WriteAllText(djzFileName, str);
                        System.Console.WriteLine("save " + str.Length);
                        break;
                    case 3:
                        data = djzFile.toDJZ10();
                        System.IO.File.WriteAllBytes(djzFileName, data);
                        System.Console.WriteLine("save " + data.Length);
                        break;
                    case 4:
                        data = djzFile.toDJZ20();
                        System.IO.File.WriteAllBytes(djzFileName, data);
                        System.Console.WriteLine("save " + data.Length);
                        break;
                }
                
            }
        }

        public void SaveDJZQuick()
        {
            if (djzFileName != null && System.IO.File.Exists(djzFileName))
            {
                DJZFile djzFile = new DJZFile(djz, LineCount);

                byte[] data;
                string str;

                switch (FileType)
                {
                    case 1:
                        str = djzFile.toBMS(false, System.IO.Path.GetFileName(midiFileName));
                        System.IO.File.WriteAllText(djzFileName, str);
                        System.Console.WriteLine("save " + str.Length);
                        break;
                    case 2:
                        str = djzFile.toBMS(true, System.IO.Path.GetFileName(midiFileName));
                        System.IO.File.WriteAllText(djzFileName, str);
                        System.Console.WriteLine("save " + str.Length);
                        break;
                    case 3:
                        data = djzFile.toDJZ10();
                        System.IO.File.WriteAllBytes(djzFileName, data);
                        System.Console.WriteLine("save " + data.Length);
                        break;
                    case 4:
                        data = djzFile.toDJZ20();
                        System.IO.File.WriteAllBytes(djzFileName, data);
                        System.Console.WriteLine("save " + data.Length);
                        break;
                }
                
            }
            else
            {
                SaveDJZ();
            }
        }



		private void treeView1_AfterSelect(object sender, System.Windows.Forms.TreeViewEventArgs e)
		{
			//e.Node.Index;
			if(e.Node.Parent!=null)
			{
                ChannelIndex = e.Node.Index;
				
			}
			
		}

        private void treeView1_NodeMouseDoubleClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            
        }

        private void treeView1_DoubleClick(object sender, EventArgs e)
        {
            if (midi != null)
            {
                Form6 dump = new Form6(midi);
                dump.ShowDialog(this);
            }
            
            
        }

		private void listBox1_SelectedIndexChanged(object sender, System.EventArgs e)
		{
		
		}


		private void button1_Click(object sender, System.EventArgs e)
		{
            //
            //play
            //
            if (soundman1 != null)
            {
                if (!soundman1.Playing)
                {
                    soundman1.Play();
                    this.trackBar1.Value = (int)(soundman1.CurrentPosition * 1000);
                }
                else
                {
                    soundman1.Pause();
                    this.trackBar1.Value = (int)(soundman1.CurrentPosition * 1000);
                }

            }
            
		}

        private void button7_Click(object sender, EventArgs e)
        {
            //
            //pause
            //
            if (soundman1 != null)
            {
                if (soundman1.Playing)
                {
                    soundman1.Pause();
                    this.trackBar1.Value = (int)(soundman1.CurrentPosition * 1000);
                }

            }
        }

		private void button4_Click(object sender, System.EventArgs e)
		{
            //
            //stop
            //
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
					//soundman1.CurrentPosition = (double)(((double)this.trackBar1.Value)/((double)1000));
                    soundman1.CurrentPosition = (double)((this.trackBar1.Value / 1000));
                    
				}
			}
		}
        private void trackBar3_Scroll(object sender, EventArgs e)
        {
            if (desCanvas != null)
            {
                desCanvas.Speed = trackBar3.Value;
            }
            if (srcCanvas != null )
            {
                for (int i = 0; i < srcCanvas.Length; i++ )
                {
                    if (srcCanvas[i] != null)
                    {
                        srcCanvas[i].Speed = trackBar3.Value;
                    }
                }
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {

            this.pictureBox1.Invalidate();
            this.pictureBox2.Invalidate();

           
        }


        
        //des
        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;
            g.Clear(Color.White);

            int ww = pictureBox1.Width;
            int wh = pictureBox1.Height;

            if (desCanvas != null && soundman1 != null)
            {
                int SelectNote = -1 ;
                Rectangle src_rect = new Rectangle(
                    Math.Min(src_sx, src_dx),
                    Math.Min(src_sy, src_dy),
                    Math.Abs(src_sx - src_dx),
                    Math.Abs(src_sy - src_dy)
                    );
                //Rectangle des_rect = new Rectangle(
                //   Math.Min(des_sx, des_dx),
                //   Math.Min(des_sy, des_dy),
                //   Math.Abs(des_sx - des_dx),
                //   Math.Abs(des_sy - des_dy)
                //   );
                Rectangle des_rect = new Rectangle(
                   des_dx,
                   des_dy,
                   1,
                   1
                   );

                // gameCanvas
                g.FillRectangle(
                    new SolidBrush(Color.Red),
                    0,
                    pictureBox1.Height - tile_h,
                    (tile_w + 1) * srcCanvas[ChannelIndex].GetLineCount(),
                    tile_h
                    );
                

                desCanvas.Update((int)(soundman1.CurrentPosition * 1000));


                int[][] NoteLine = desCanvas.getFullNoteLine();
                for (int l = 0; l < NoteLine.Length; l++)
                {
                    // draw full note line
                    if (wh - NoteLine[l][0] > 0 && wh - NoteLine[l][0] < wh)
                    {
                        g.DrawLine(
                            new Pen(Brushes.Gray),
                            1 + (tile_w + 1) * 0,
                                wh - NoteLine[l][0],
                            1 + (tile_w + 1) * LineCount - 1,
                                wh - NoteLine[l][0]
                                );
                        g.DrawString(
                                NoteLine[l][1] + "",
                                this.Font,
                                Brushes.Gray,
                                1,
                                wh - NoteLine[l][0] - this.Font.Height
                                );
                    }
                }


                int[][][] Pos = new int[desCanvas.GetLineCount()][][];

                for (int i = 0; i < Pos.Length; i++)
                {
                    g.DrawLine(
                        new Pen(Color.Black),
                        i * (tile_w + 1),
                        0,
                        i * (tile_w + 1), 
                        pictureBox1.Height
                        );

                    Pos[i] = desCanvas.GetPos(i);
                    

                    for (int j = 0; j < Pos[i].Length; j++)
                    {
                        if (pictureBox1.Height - Pos[i][j][0] + tile_h > 0 && 
                            pictureBox1.Height - Pos[i][j][0] < pictureBox1.Height)
                        {
                            if (((EventDJZ)desCanvas.Events[Pos[i][j][1]]).dragged == true)
                            {
                                tiles2.render(g,
                                    ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).track,
                                    1 + i * (tile_w + 1),
                                    pictureBox1.Height - Pos[i][j][0] - tile_h + 1
                                    );
                            }
                            else
                            {
                                tiles1.render(g,
                                    ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).track,
                                    1 + i * (tile_w + 1),
                                    pictureBox1.Height - Pos[i][j][0] - tile_h + 1
                                    );
                            }

                            Rectangle note = new Rectangle(
                                1 + i * (tile_w + 1),
                                pictureBox2.Height - Pos[i][j][0] - tile_h + 1,
                                tile_w,
                                tile_h
                                );
                            Rectangle track = new Rectangle(
                                1 + i * (tile_w + 1),
                                0,
                                tile_w,
                                pictureBox1.Height
                                );

                            if (des_down)
                            {
                                ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).dragged = false;
                                if (SelectNote<0 && des_rect.IntersectsWith(note))
                                {
                                    SelectNote = Pos[i][j][1];
                                    ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).dragged = true;
                                }
                            }

                            if (des_hold)
                            {
                                if (((EventDJZ)desCanvas.Events[Pos[i][j][1]]).dragged == true)
                                {
                                    if (des_dx > 0 && des_dx < Pos.Length*(tile_w + 1))
                                    {
                                        ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).pos = (byte)(des_dx / (tile_w + 1));
                                    }
                                }
                            }

                            if (src_hold && src_rect.IntersectsWith(note))
                            {
                                if (((EventDJZ)desCanvas.Events[Pos[i][j][1]]).selected == false)
                                {
                                    
                                    if (((EventDJZ)desCanvas.Events[Pos[i][j][1]]).used == true)
                                    {
                                        ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).used = false;
                                    }
                                    else
                                    {
                                        ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).used = true;
                                    }
                                }
                                ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).selected = true;
                            }
                            else
                            {
                                ((EventDJZ)desCanvas.Events[Pos[i][j][1]]).selected = false;
                            }
                        }
                    }
                    
                }

                for (int i = 0; i < desCanvas.Events.Count;i++ )
                {
                    if (((EventDJZ)desCanvas.Events[i]).used == false)
                    {
                        if (((EventDJZ)desCanvas.Events[i]).track == ChannelIndex)
                        {
                            desCanvas.Events.RemoveAt(i);
                            i--;
                        }
                    }
                }
               
                g.DrawLine(
                        new Pen(Color.Black),
                        Pos.Length * (tile_w + 1),
                        0,
                        Pos.Length * (tile_w + 1),
                        pictureBox1.Height
                        );

                if (soundman1.Playing)
                {
                    for (int t = 0; t < LineCount; t++)
                    {
                        if (desCanvas.HitAuto(t))
                        {
                            g.FillRectangle(
                                new SolidBrush(Color.FromArgb(192, 0x80, 0x80, 0x80)),
                                1 + t * (tile_w + 1),
                                0,
                                tile_w,
                                pictureBox1.Height
                                );
                        }
                    }
                }
                if (des_line > -1)
                {
                    g.FillRectangle(
                                new SolidBrush(Color.FromArgb(0x80, 0x00, 0xff, 0x00)),
                                1 + des_line * (tile_w + 1),
                                0,
                                tile_w,
                                pictureBox1.Height
                                );
                }
                //if (des_down)
                //{
                //    g.DrawLine(new Pen(Color.Green), des_sx, des_sy, des_sx, des_dy);
                //    g.DrawLine(new Pen(Color.Green), des_sx, des_sy, des_dx, des_sy);
                //    g.DrawLine(new Pen(Color.Green), des_dx, des_sy, des_dx, des_dy);
                //    g.DrawLine(new Pen(Color.Green), des_sx, des_dy, des_dx, des_dy);
                //}

                // GUI
                this.label1.Text = "";
                if (soundman1.Playing)
                {
                    this.label1.Text += "Playing ; ";
                    this.trackBar1.Value = (int)(soundman1.CurrentPosition * 1000);
                }
                if (soundman1.Paused)
                {
                    this.label1.Text += "Paused ; ";
                }
                if (soundman1.Stopped)
                {
                    this.label1.Text += "Stoped ; ";
                }

                this.label1.Text += soundman1.CurrentPosition * 1000;
                this.label1.Text += "/";
                this.label1.Text += soundman1.StopPosition / 10000 + "(ms) ; ";
                this.label1.Text += "Time=" + soundman1.Duration + "(s) ; ";
                this.label1.Text += "Speed=" + this.trackBar3.Value + " ; ";
                this.label1.Text += "BPM=" + desCanvas.GetBPM() + " ; ";

            }

            des_down = false;
            des_up = false;
        }
      


        // src
        private void pictureBox2_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;
            g.Clear(Color.White);

            int ww = pictureBox2.Width;
            int wh = pictureBox2.Height;

            if (desCanvas != null && soundman1 != null)
            {
                Rectangle src_rect = new Rectangle(
                    Math.Min(src_sx, src_dx),
                    Math.Min(src_sy, src_dy),
                    Math.Abs(src_sx - src_dx),
                    Math.Abs(src_sy - src_dy)
                    );
                //Rectangle des_rect = new Rectangle(
                //   Math.Min(des_sx, des_dx),
                //   Math.Min(des_sy, des_dy),
                //   Math.Abs(des_sx - des_dx),
                //   Math.Abs(des_sy - des_dy)
                //   );

                // gameCanvas
                g.FillRectangle(
                    new SolidBrush(Color.Red),
                    0,
                    pictureBox2.Height - tile_h,
                    (tile_w + 1) * srcCanvas[ChannelIndex].GetLineCount(),
                    tile_w
                    );

                srcCanvas[ChannelIndex].Update((int)(soundman1.CurrentPosition * 1000));

                int[][] NoteLine = srcCanvas[ChannelIndex].getFullNoteLine();
                for (int l = 0; l < NoteLine.Length; l++)
                {
                    // draw full note line
                    if (wh - NoteLine[l][0] > 0 && wh - NoteLine[l][0] < wh)
                    {
                        g.DrawLine(
                            new Pen(Brushes.Gray),
                            1 + (tile_w + 1) * 0,
                                wh - NoteLine[l][0],
                            1 + (tile_w + 1) * LineCount - 1,
                                wh - NoteLine[l][0]
                                );
                        g.DrawString(
                                NoteLine[l][1] + "",
                                this.Font,
                                Brushes.Gray,
                                1,
                                wh - NoteLine[l][0] - this.Font.Height
                                );
                    }
                }

                int[][][] Pos = new int[srcCanvas[ChannelIndex].GetLineCount()][][];
    
                for (int i = 0; i < Pos.Length; i++)
                {
                    if (this.treeView1.SelectedNode.Parent != null)
                    {
                        g.DrawLine(
                        new Pen(Color.Black),
                        i * (tile_w + 1),
                        0,
                        i * (tile_w + 1),
                        pictureBox2.Height
                        );
                    }
                    

                    if (treeView1.SelectedNode.Parent != null)
                    {

                        Pos[i] = srcCanvas[ChannelIndex].GetPos(i);

                        for (int j = 0; j < Pos[i].Length; j++)
                        {
                            if (pictureBox2.Height - Pos[i][j][0] + tile_h > 0 &&
                                pictureBox2.Height - Pos[i][j][0] < pictureBox2.Height)
                            {
                                if (((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).used == true)
                                {
                                    tiles2.render(g,
                                        ((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).track,
                                        1 + i * (tile_w + 1),
                                        pictureBox2.Height - Pos[i][j][0] - tile_h + 1
                                        );
                                }
                                else
                                {
                                    tiles1.render(g,
                                        ((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).track,
                                        1 + i * (tile_w + 1),
                                        pictureBox2.Height - Pos[i][j][0] - tile_h + 1
                                        );
                                }
                                

                                Rectangle note = new Rectangle(
                                    1 + i * (tile_w + 1),
                                    pictureBox2.Height - Pos[i][j][0] - tile_h + 1,
                                    tile_w,
                                    tile_h
                                    );

                                if (src_hold && src_rect.IntersectsWith(note))
                                {
                                    if (((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).selected == false)
                                    {
                                        if (((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).used == true)
                                        {
                                            ((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).used = false;
                                        }
                                        else
                                        {
                                            desCanvas.Events.Add((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]);
                                            desCanvas.BuffSize = desCanvas.Events.Count;
                                            ((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).used = true;
                                        }
                                    }
                                    ((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).selected = true;
                                }
                                else
                                {
                                    ((EventDJZ)srcCanvas[ChannelIndex].Events[Pos[i][j][1]]).selected = false;
                                }
    
                            }
                        }

                    }
                }

                if (this.treeView1.SelectedNode.Parent != null)
                {
                    g.DrawLine(
                          new Pen(Color.Black),
                          Pos.Length * (tile_w + 1),
                          0,
                          Pos.Length * (tile_w + 1),
                          pictureBox2.Height
                          );
                }
               
                if (soundman1.Playing && treeView1.SelectedNode.Parent!=null)
                {
                    for (int t = 0; t < LineCount; t++)
                    {
                        if (srcCanvas[ChannelIndex].HitAuto(t))
                        {
                            g.FillRectangle(
                                new SolidBrush(Color.FromArgb(192, 0x80, 0x80, 0x80)),
                                1 + t * (tile_w + 1),
                                0,
                                tile_w,
                                pictureBox2.Height
                                );


                        }
                    }
                }
                if (src_line > -1)
                {
                    g.FillRectangle(
                                new SolidBrush(Color.FromArgb(0x80, 0x00, 0xff, 0x00)),
                                1 + src_line * (tile_w + 1),
                                0,
                                tile_w,
                                pictureBox2.Height
                                );
                }
            }
            if (src_hold)
            {
                g.DrawLine(new Pen(Color.Green), src_sx, src_sy, src_sx, src_dy);
                g.DrawLine(new Pen(Color.Green), src_sx, src_sy, src_dx, src_sy);
                g.DrawLine(new Pen(Color.Green), src_dx, src_sy, src_dx, src_dy);
                g.DrawLine(new Pen(Color.Green), src_sx, src_dy, src_dx, src_dy);
            }

            src_down = false;
            src_up = false;
       
        }

        Boolean src_hold;
        Boolean src_down;
        Boolean src_up;
        int src_time = 0;
        int src_sx;
        int src_sy;
        int src_dx;
        int src_dy;


        private void pictureBox2_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                src_hold = true;
                src_down = true;
                src_sx = e.X;
                src_sy = e.Y;
                src_dx = e.X;
                src_dy = e.Y;
            }

            src_line = -1;
            des_line = -1;
            if (e.Button == MouseButtons.Right)
            {
                if (e.X > 0 && e.X < LineCount * (tile_w + 1) && this.treeView1.SelectedNode.Parent!=null)
                {
                    src_line = e.X / (tile_w + 1);
                    for (int i = 0; i < this.contextMenuStrip2.Items.Count; i++)
                    {
                        this.contextMenuStrip2.Items[i].Available = true;
                    }
                }
                else
                {
                    for (int i = 0; i < this.contextMenuStrip2.Items.Count; i++)
                    {
                        this.contextMenuStrip2.Items[i].Available = false;
                    }
                }
            }
        }

        private void pictureBox2_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                src_dx = e.X;
                src_dy = e.Y;
            }
       
        }

        private void pictureBox2_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                src_hold = false;
                src_up = true;

                src_sx = e.X;
                src_sy = e.Y;
                src_dx = e.X;
                src_dy = e.Y;

                src_time++;
            }
          
        }


        Boolean des_hold;
        Boolean des_down;
        Boolean des_up;

        int des_time = 0;
        int des_sx;
        int des_sy;
        int des_dx;
        int des_dy;

        private void pictureBox1_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                des_hold = true;
                des_down = true;
               
                des_sx = e.X;
                des_sy = e.Y;
                des_dx = e.X;
                des_dy = e.Y;
            }

            src_line = -1;
            des_line = -1;
            if (e.Button == MouseButtons.Right)
            {
                if (e.X > 0 && e.X < LineCount * (tile_w + 1))
                {
                    des_line = e.X / (tile_w + 1);
                    for (int i = 0; i < this.contextMenuStrip1.Items.Count; i++)
                    {
                        this.contextMenuStrip1.Items[i].Available = true;
                    }
                }
                else
                {
                    for (int i = 0; i < this.contextMenuStrip1.Items.Count; i++)
                    {
                        this.contextMenuStrip1.Items[i].Available = false;
                    }
                }
            }

        }

        private void pictureBox1_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                des_dx = e.X;
                des_dy = e.Y;
            }

        }

        private void pictureBox1_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                des_hold = false;
                des_up = true;

                des_sx = e.X;
                des_sy = e.Y;
                des_dx = e.X;
                src_dy = e.Y;

                des_time++;
            }

        }

        private void button3_Click(object sender, EventArgs e)
        {
            //
            //add track
            //
            for (int i = 0; i < srcCanvas[ChannelIndex].Events.Count; i++)
            {
                if (((EventDJZ)srcCanvas[ChannelIndex].Events[i]).used == false)
                {
                    ((EventDJZ)srcCanvas[ChannelIndex].Events[i]).used = true;
                    desCanvas.Events.Add(srcCanvas[ChannelIndex].Events[i]);
                    desCanvas.BuffSize = desCanvas.Events.Count;
                }
            }
        }

        private void button5_Click(object sender, EventArgs e)
        {
            //
            //del track
            //
            for (int i = 0; i < desCanvas.Events.Count; i++)
            {
                if (((EventDJZ)desCanvas.Events[i]).track == ChannelIndex)
                {
                    ((EventDJZ)desCanvas.Events[i]).used = false;
                    desCanvas.Events.RemoveAt(i);
                    desCanvas.BuffSize = desCanvas.Events.Count;
                    i--;
                }
            }
           
        }

        private void button2_Click(object sender, EventArgs e)
        {
            //
            //del all
            //
            while (desCanvas.Events.Count > 0)
            {
                ((EventDJZ)desCanvas.Events[0]).used = false;
                desCanvas.Events.RemoveAt(0);
                desCanvas.BuffSize = desCanvas.Events.Count;
            }

           
            
        }

        private void button6_Click(object sender, EventArgs e)
        {
            //
            //add all
            //

            for (int t = 0; t < srcCanvas.Length; t++)
            {
                for (int i = 0; i < srcCanvas[t].Events.Count; i++)
                {
                    if (((EventDJZ)srcCanvas[t].Events[i]).used == false)
                    {
                        ((EventDJZ)srcCanvas[t].Events[i]).used = true;
                        desCanvas.Events.Add(srcCanvas[t].Events[i]);
                        desCanvas.BuffSize = desCanvas.Events.Count;
                    }
                }
            }
        }

        private void button8_Click(object sender, EventArgs e)
        {
            //
            //del near
            //
            EventComparerDJZTime ec = new EventComparerDJZTime();
            desCanvas.Events.Sort(ec);

            int i = 1; int j = 0;
            while (i < desCanvas.Events.Count && j < desCanvas.Events.Count)
            {
                if (i == j)
                {
                    i++;
                    continue;
                }
                if (Math.Abs(((EventDJZ)desCanvas.Events[i]).time - ((EventDJZ)desCanvas.Events[j]).time) < Math.Abs(this.numericUpDown1.Value))
                {
                    ((EventDJZ)desCanvas.Events[i]).used = false;
                    desCanvas.Events.RemoveAt(i);
                    desCanvas.BuffSize = desCanvas.Events.Count;
                    continue;
                }
             
                j++;
                i++;
                
            }
        }

        int des_line = -1;
        private void delThisLineNearToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //
            //del line near
            //
            int line = des_line;
            EventComparerDJZTime ec = new EventComparerDJZTime();
            desCanvas.Events.Sort(ec);

            int i = 1; int j = 0;
            while (i < desCanvas.Events.Count && j < desCanvas.Events.Count)
            {
                if (((EventDJZ)desCanvas.Events[i]).pos % desCanvas.GetLineCount() != line)
                {
                    i++;
                    continue;
                }
                if (((EventDJZ)desCanvas.Events[j]).pos % desCanvas.GetLineCount() != line) 
                { 
                    j++;
                    continue;
                }
                if (i == j)
                {
                    i++;
                    continue;
                }

                if (Math.Abs(((EventDJZ)desCanvas.Events[i]).time - ((EventDJZ)desCanvas.Events[j]).time) < Math.Abs(this.numericUpDown1.Value))
                {
                    ((EventDJZ)desCanvas.Events[i]).used = false;
                    desCanvas.Events.RemoveAt(i);
                    desCanvas.BuffSize = desCanvas.Events.Count;
                    continue;
                }
               
                j++;
                i++;

            }
            src_line = -1;
            des_line = -1;
        }

        private void delThisLineToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //
            //del line all
            //
            int line = des_line;
            int i = 0;
            while (i<desCanvas.Events.Count)
            {
                if (((EventDJZ)desCanvas.Events[i]).pos % desCanvas.GetLineCount() != line)
                {
                    i++;
                    continue;
                }
                ((EventDJZ)desCanvas.Events[i]).used = false;
                desCanvas.Events.RemoveAt(i);
                desCanvas.BuffSize = desCanvas.Events.Count;
            }
            src_line = -1;
            des_line = -1;
        }


        int src_line = -1;
        private void toolStripMenuItem1_Click(object sender, EventArgs e)
        {
            //
            //add Track line
            //
            int line = src_line;
            for (int i = 0; i < srcCanvas[ChannelIndex].Events.Count; i++)
            {
                if (((EventDJZ)srcCanvas[ChannelIndex].Events[i]).used == false &&
                    ((EventDJZ)srcCanvas[ChannelIndex].Events[i]).pos % srcCanvas[ChannelIndex].GetLineCount() == line)
                {
                    ((EventDJZ)srcCanvas[ChannelIndex].Events[i]).used = true;
                    desCanvas.Events.Add(srcCanvas[ChannelIndex].Events[i]);
                    desCanvas.BuffSize = desCanvas.Events.Count;
                }
            }

            src_line = -1;
            des_line = -1;
        }

        private void delThisLineToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            //
            //del track line
            //
            int line = src_line;
            int i = 0;
            while (i < desCanvas.Events.Count)
            {
                if (((EventDJZ)desCanvas.Events[i]).pos % desCanvas.GetLineCount() != line ||
                    ((EventDJZ)desCanvas.Events[i]).track != ChannelIndex)
                {
                    i++;
                    continue;
                }
                ((EventDJZ)desCanvas.Events[i]).used = false;
                desCanvas.Events.RemoveAt(i);
                desCanvas.BuffSize = desCanvas.Events.Count;
            }

            src_line = -1;
            des_line = -1;
        }

        private void contextMenuStrip1_Closed(object sender, ToolStripDropDownClosedEventArgs e)
        {
            if (e.CloseReason != ToolStripDropDownCloseReason.ItemClicked)
            {
                src_line = -1;
                des_line = -1;
            }
           
        }

        private void contextMenuStrip2_Closed(object sender, ToolStripDropDownClosedEventArgs e)
        {
            if (e.CloseReason != ToolStripDropDownCloseReason.ItemClicked)
            {
                src_line = -1;
                des_line = -1;
            }
        }

  









        
	}
}

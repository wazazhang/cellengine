using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace DjZoneEditor
{
	/// <summary>
	/// Form1 的摘要说明。
	/// </summary>
	public class Form1 : System.Windows.Forms.Form
	{
		private System.IO.StringWriter stringWriter ;
		private System.Timers.Timer timer1;
		private System.Windows.Forms.TextBox textBox1;
		private System.Windows.Forms.Splitter splitter1;
		private System.Windows.Forms.MainMenu mainMenu1;
		private System.Windows.Forms.MenuItem menuItem1;
		private System.Windows.Forms.MenuItem menuItem2;
		private System.Windows.Forms.MenuItem menuItem3;
		private System.Windows.Forms.MenuItem menuItem4;
		private System.Windows.Forms.MenuItem menuItem5;
		private System.Windows.Forms.MenuItem menuItem6;
		private System.Windows.Forms.MenuItem menuItem7;
		private System.Windows.Forms.MenuItem menuItem8;
		private System.Windows.Forms.MenuItem menuItem9;
		private System.Windows.Forms.MenuItem menuItem10;
		private System.Windows.Forms.MenuItem menuItem12;
		private System.Windows.Forms.MenuItem menuItem11;
		private System.Windows.Forms.MenuItem menuItem13;
		private System.Windows.Forms.MenuItem menuItem14;
		/// <summary>
		/// 必需的设计器变量。
		/// </summary>
		private System.ComponentModel.Container components = null;

		public Form1()
		{
			//
			// Windows 窗体设计器支持所必需的
			//
			InitializeComponent();

			//
			// TODO: 在 InitializeComponent 调用后添加任何构造函数代码
			//
			this.stringWriter = new System.IO.StringWriter();
			System.Console.SetOut(stringWriter);

	
		}

		/// <summary>
		/// 清理所有正在使用的资源。
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows 窗体设计器生成的代码
		/// <summary>
		/// 设计器支持所需的方法 - 不要使用代码编辑器修改
		/// 此方法的内容。
		/// </summary>
		private void InitializeComponent()
		{
			this.timer1 = new System.Timers.Timer();
			this.textBox1 = new System.Windows.Forms.TextBox();
			this.splitter1 = new System.Windows.Forms.Splitter();
			this.mainMenu1 = new System.Windows.Forms.MainMenu();
			this.menuItem1 = new System.Windows.Forms.MenuItem();
			this.menuItem4 = new System.Windows.Forms.MenuItem();
			this.menuItem8 = new System.Windows.Forms.MenuItem();
			this.menuItem9 = new System.Windows.Forms.MenuItem();
			this.menuItem10 = new System.Windows.Forms.MenuItem();
			this.menuItem13 = new System.Windows.Forms.MenuItem();
			this.menuItem5 = new System.Windows.Forms.MenuItem();
			this.menuItem6 = new System.Windows.Forms.MenuItem();
			this.menuItem7 = new System.Windows.Forms.MenuItem();
			this.menuItem2 = new System.Windows.Forms.MenuItem();
			this.menuItem12 = new System.Windows.Forms.MenuItem();
			this.menuItem3 = new System.Windows.Forms.MenuItem();
			this.menuItem11 = new System.Windows.Forms.MenuItem();
			this.menuItem14 = new System.Windows.Forms.MenuItem();
			((System.ComponentModel.ISupportInitialize)(this.timer1)).BeginInit();
			this.SuspendLayout();
			// 
			// timer1
			// 
			this.timer1.Enabled = true;
			this.timer1.SynchronizingObject = this;
			this.timer1.Elapsed += new System.Timers.ElapsedEventHandler(this.timer1_Elapsed);
			// 
			// textBox1
			// 
			this.textBox1.AcceptsReturn = true;
			this.textBox1.AcceptsTab = true;
			this.textBox1.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.textBox1.Location = new System.Drawing.Point(0, 417);
			this.textBox1.Multiline = true;
			this.textBox1.Name = "textBox1";
			this.textBox1.ReadOnly = true;
			this.textBox1.ScrollBars = System.Windows.Forms.ScrollBars.Both;
			this.textBox1.Size = new System.Drawing.Size(792, 128);
			this.textBox1.TabIndex = 0;
			this.textBox1.Text = "textBox1";
			this.textBox1.WordWrap = false;
			// 
			// splitter1
			// 
			this.splitter1.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.splitter1.Location = new System.Drawing.Point(0, 409);
			this.splitter1.Name = "splitter1";
			this.splitter1.Size = new System.Drawing.Size(792, 8);
			this.splitter1.TabIndex = 2;
			this.splitter1.TabStop = false;
			// 
			// mainMenu1
			// 
			this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem1,
																					  this.menuItem2,
																					  this.menuItem3,
																					  this.menuItem14});
			// 
			// menuItem1
			// 
			this.menuItem1.Index = 0;
			this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem4,
																					  this.menuItem8,
																					  this.menuItem9,
																					  this.menuItem10,
																					  this.menuItem13,
																					  this.menuItem5,
																					  this.menuItem6,
																					  this.menuItem7});
			this.menuItem1.Text = "File";
			this.menuItem1.Click += new System.EventHandler(this.menuItem1_Click);
			// 
			// menuItem4
			// 
			this.menuItem4.Index = 0;
			this.menuItem4.Text = "Load Midi...";
			this.menuItem4.Click += new System.EventHandler(this.menuItem4_Click);
			// 
			// menuItem8
			// 
			this.menuItem8.Index = 1;
			this.menuItem8.Text = "Close";
			this.menuItem8.Click += new System.EventHandler(this.menuItem8_Click);
			// 
			// menuItem9
			// 
			this.menuItem9.Index = 2;
			this.menuItem9.Text = "Close All";
			this.menuItem9.Click += new System.EventHandler(this.menuItem9_Click);
			// 
			// menuItem10
			// 
			this.menuItem10.Index = 3;
			this.menuItem10.Text = "-";
			// 
			// menuItem13
			// 
			this.menuItem13.Index = 4;
			this.menuItem13.Text = "Save";
			// 
			// menuItem5
			// 
			this.menuItem5.Index = 5;
			this.menuItem5.Text = "Save As...";
			this.menuItem5.Click += new System.EventHandler(this.menuItem5_Click);
			// 
			// menuItem6
			// 
			this.menuItem6.Index = 6;
			this.menuItem6.Text = "-";
			// 
			// menuItem7
			// 
			this.menuItem7.Index = 7;
			this.menuItem7.Text = "Exit";
			this.menuItem7.Click += new System.EventHandler(this.menuItem7_Click);
			// 
			// menuItem2
			// 
			this.menuItem2.Index = 1;
			this.menuItem2.MdiList = true;
			this.menuItem2.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem12});
			this.menuItem2.Text = "Window";
			// 
			// menuItem12
			// 
			this.menuItem12.Index = 0;
			this.menuItem12.Text = "a";
			this.menuItem12.Click += new System.EventHandler(this.menuItem12_Click);
			// 
			// menuItem3
			// 
			this.menuItem3.Index = 2;
			this.menuItem3.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem11});
			this.menuItem3.Text = "Help";
			// 
			// menuItem11
			// 
			this.menuItem11.Index = 0;
			this.menuItem11.Text = "About";
			this.menuItem11.Click += new System.EventHandler(this.menuItem11_Click);
			// 
			// menuItem14
			// 
			this.menuItem14.Index = 3;
			this.menuItem14.Text = "";
			// 
			// Form1
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
			this.ClientSize = new System.Drawing.Size(792, 545);
			this.Controls.Add(this.splitter1);
			this.Controls.Add(this.textBox1);
			this.IsMdiContainer = true;
			this.Menu = this.mainMenu1;
			this.Name = "Form1";
			this.Text = "Form1";
			this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
			this.Click += new System.EventHandler(this.Form1_Click);
			this.Load += new System.EventHandler(this.Form1_Load);
			this.Paint += new System.Windows.Forms.PaintEventHandler(this.Form1_Paint);
			((System.ComponentModel.ISupportInitialize)(this.timer1)).EndInit();
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// 应用程序的主入口点。
		/// </summary>
		[STAThread]
		static void Main() 
		{

			Application.Run(new Form1());
//			using (GameForm frm = new GameForm())
//			{
//				if (!frm.InitializeGraphics()) // Initialize Direct3D
//				{
//					MessageBox.Show("Could not initialize Direct3D.  This tutorial will exit.");
//					return;
//				}
//				frm.Show();
//
//				// While the form is still valid, render and process messages
//				while(frm.Created)
//				{
//					frm.Render();
//					Application.DoEvents();
//				}
//			}

		}

		private void Form1_Load(object sender, System.EventArgs e)
		{

			
		}

		private void Form1_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{

		}

		private void timer1_Elapsed(object sender, System.Timers.ElapsedEventArgs e)
		{
			if(this.textBox1.Focused==true)
			{
				this.textBox1.Text = this.stringWriter.ToString();
			}else{
				this.textBox1.Text = this.stringWriter.ToString();
				this.textBox1.AppendText("");
				this.textBox1.ScrollToCaret();

			}
		}

		private void Form1_Click(object sender, System.EventArgs e)
		{
			System.Console.WriteLine("out!");
		}

		private void menuItem4_Click(object sender, System.EventArgs e)
		{

			System.Windows.Forms.OpenFileDialog openFileDialog1 = new OpenFileDialog();
            
			openFileDialog1.Filter = "MIDI File (*.mid)|*.mid|MIDI File (*.midi)|*.midi|All File (*.*)|*.*";

			if(DialogResult.OK == openFileDialog1.ShowDialog())
			{
				try
				{
					this.Text = openFileDialog1.FileName;

					System.IO.FileStream fs = new System.IO.FileStream(openFileDialog1.FileName,System.IO.FileMode.Open);
					byte[] data = new byte[fs.Length];
					fs.Read(data,0,data.Length);
					fs.Close();

                    Cell.LibMidi.Midi midi = new Cell.LibMidi.Midi();
					midi.LoadData(data);

                    
					
					Form2 newMDIChild = new Form2(midi,openFileDialog1.FileName);
					newMDIChild.MdiParent = this;
					newMDIChild.Show();
					
				}
				catch(Exception err)
				{
					//Console.WriteLine("Error Open File : "+openFileDialog1.FileName+" !");
					//Console.WriteLine(err.Message);
					System.Windows.Forms.MessageBox.Show(err.Message);
					//this.Close();
					//this.Dispose();
				}
				
			}


		}

		private void menuItem1_Click(object sender, System.EventArgs e)
		{
		
		}

		private void menuItem7_Click(object sender, System.EventArgs e)
		{
			Application.Exit();
		}

		private void menuItem5_Click(object sender, System.EventArgs e)
		{
			System.Windows.Forms.SaveFileDialog saveFileDialog1 = new SaveFileDialog();

			saveFileDialog1.Filter = "DJ-ZONE File (*.djz)|*.djz";

			if(DialogResult.OK == saveFileDialog1.ShowDialog())
			{
				System.IO.FileStream fs = new System.IO.FileStream(saveFileDialog1.FileName,System.IO.FileMode.Create);
				byte[] data = new byte[100];
				fs.Write(data,0,data.Length);
				fs.Close();
				
				System.Console.WriteLine("save "+data.Length);
			}

			
		}

		private void menuItem8_Click(object sender, System.EventArgs e)
		{
			if(this.ActiveMdiChild!=null)
			{
				this.ActiveMdiChild.Close();
			}
			
		}

		private void menuItem12_Click(object sender, System.EventArgs e)
		{

		}

		private void menuItem9_Click(object sender, System.EventArgs e)
		{
			while(this.ActiveMdiChild!=null)
			{
				this.ActiveMdiChild.Close();
			}
		}

		private void menuItem11_Click(object sender, System.EventArgs e)
		{
			Form4 about = new Form4();
			about.ShowDialog(this);
		}



	
	}
}

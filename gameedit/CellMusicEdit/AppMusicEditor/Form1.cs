using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace Cell.AppMusicEditor
{
	/// <summary>
	/// Form1 的摘要说明。
	/// </summary>
	partial class Form1 : System.Windows.Forms.Form
	{
        //static int PlayTrack;

		private System.IO.StringWriter stringWriter ;
		private System.Timers.Timer timer1;
		private System.Windows.Forms.TextBox textBox1;
		private System.Windows.Forms.Splitter splitter1;
		private System.Windows.Forms.MainMenu mainMenu1;
		private System.Windows.Forms.MenuItem menuItem1;
		private System.Windows.Forms.MenuItem menuItem2;
        private System.Windows.Forms.MenuItem menuItem3;
		private System.Windows.Forms.MenuItem menuItem5;
		private System.Windows.Forms.MenuItem menuItem6;
		private System.Windows.Forms.MenuItem menuItem7;
		private System.Windows.Forms.MenuItem menuItem8;
		private System.Windows.Forms.MenuItem menuItem9;
        private System.Windows.Forms.MenuItem menuItem10;
		private System.Windows.Forms.MenuItem menuItem11;
        private System.Windows.Forms.MenuItem menuItem13;
        private MenuItem menuItem12;
        private MenuItem menuItem15;
        private IContainer components;
        private MenuItem menuItem14;
        private MenuItem menuItem16;
        private MenuItem menuItem17;

        public static Settings1 config = new Settings1();

        

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

            //Console.WriteLine("Line Count = " + config.LineCount);

            
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
            this.components = new System.ComponentModel.Container();
            this.timer1 = new System.Timers.Timer();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.splitter1 = new System.Windows.Forms.Splitter();
            this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
            this.menuItem1 = new System.Windows.Forms.MenuItem();
            this.menuItem17 = new System.Windows.Forms.MenuItem();
            this.menuItem8 = new System.Windows.Forms.MenuItem();
            this.menuItem9 = new System.Windows.Forms.MenuItem();
            this.menuItem10 = new System.Windows.Forms.MenuItem();
            this.menuItem13 = new System.Windows.Forms.MenuItem();
            this.menuItem5 = new System.Windows.Forms.MenuItem();
            this.menuItem6 = new System.Windows.Forms.MenuItem();
            this.menuItem7 = new System.Windows.Forms.MenuItem();
            this.menuItem2 = new System.Windows.Forms.MenuItem();
            this.menuItem12 = new System.Windows.Forms.MenuItem();
            this.menuItem15 = new System.Windows.Forms.MenuItem();
            this.menuItem3 = new System.Windows.Forms.MenuItem();
            this.menuItem14 = new System.Windows.Forms.MenuItem();
            this.menuItem16 = new System.Windows.Forms.MenuItem();
            this.menuItem11 = new System.Windows.Forms.MenuItem();
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
            this.textBox1.Location = new System.Drawing.Point(0, 375);
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
            this.splitter1.Location = new System.Drawing.Point(0, 367);
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
            this.menuItem12,
            this.menuItem3});
            // 
            // menuItem1
            // 
            this.menuItem1.Index = 0;
            this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.menuItem17,
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
            // menuItem17
            // 
            this.menuItem17.Index = 0;
            this.menuItem17.Text = "Open...";
            this.menuItem17.Click += new System.EventHandler(this.menuItem17_Click);
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
            this.menuItem13.Click += new System.EventHandler(this.menuItem13_Click);
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
            this.menuItem2.Text = "Window";
            // 
            // menuItem12
            // 
            this.menuItem12.Index = 2;
            this.menuItem12.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.menuItem15});
            this.menuItem12.Text = "Tools";
            // 
            // menuItem15
            // 
            this.menuItem15.Index = 0;
            this.menuItem15.Text = "Config";
            this.menuItem15.Click += new System.EventHandler(this.menuItem15_Click);
            // 
            // menuItem3
            // 
            this.menuItem3.Index = 3;
            this.menuItem3.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.menuItem14,
            this.menuItem16,
            this.menuItem11});
            this.menuItem3.Text = "Help";
            // 
            // menuItem14
            // 
            this.menuItem14.Index = 0;
            this.menuItem14.Text = "Help";
            this.menuItem14.Click += new System.EventHandler(this.menuItem14_Click);
            // 
            // menuItem16
            // 
            this.menuItem16.Index = 1;
            this.menuItem16.Text = "-";
            // 
            // menuItem11
            // 
            this.menuItem11.Index = 2;
            this.menuItem11.Text = "About";
            this.menuItem11.Click += new System.EventHandler(this.menuItem11_Click);
            // 
            // Form1
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
            this.ClientSize = new System.Drawing.Size(792, 503);
            this.Controls.Add(this.splitter1);
            this.Controls.Add(this.textBox1);
            this.IsMdiContainer = true;
            this.Menu = this.mainMenu1;
            this.Name = "Form1";
            this.Text = "MusicEditor(Beta)";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Form1_FormClosed);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.Form1_Paint);
            this.Click += new System.EventHandler(this.Form1_Click);
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.timer1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion


		private void Form1_Load(object sender, System.EventArgs e)
		{
            timer1.Start();
			
		}

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            timer1.Stop();
        }

		private void Form1_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{

		}

		private void timer1_Elapsed(object sender, System.Timers.ElapsedEventArgs e)
		{
            if (this.textBox1.Text!=null)
            {
                if (this.textBox1.Focused == true)
                {
                    this.textBox1.Text = this.stringWriter.ToString();
                }
                else
                {
                    this.textBox1.Text = stringWriter.ToString();
                    this.textBox1.Select(this.textBox1.Text.Length, 0);
                    this.textBox1.ScrollToCaret();
                    //this.textBox1.Text = this.stringWriter.ToString();
                    ////this.textBox1.AppendText(" ");
                }
            }
            

            //if (this.ActiveMdiChild != null)
            //{
           //     this.Text = ActiveMdiChild.Text;
           // }
           // else
           // {
           //     this.Text = "";
           // }

		}

		private void Form1_Click(object sender, System.EventArgs e)
		{
			System.Console.WriteLine("out!");
		}

		private void menuItem4_Click(object sender, System.EventArgs e)
		{

            //System.Windows.Forms.OpenFileDialog openFileDialog1 = new OpenFileDialog();
            
            //openFileDialog1.Filter = "MIDI File (*.mid)|*.mid|MIDI File (*.midi)|*.midi|All File (*.*)|*.*";

            //if(DialogResult.OK == openFileDialog1.ShowDialog())
            //{
            //    Form2 newMDIChild = null;
            //    try
            //    {
            //        newMDIChild = new Form2(openFileDialog1.FileName,config.LineCount);
            //        newMDIChild.MdiParent = this;
            //        newMDIChild.Show();
            //    }
            //    catch(Exception err)
            //    {
            //        if (newMDIChild != null)
            //        {
            //            newMDIChild.Close();
            //        }
            //        //Console.WriteLine("Error Open File : "+openFileDialog1.FileName+" !");
            //        //Console.WriteLine(err.Message);
            //        System.Windows.Forms.MessageBox.Show(err.Message);
            //        //this.Close();
            //        //this.Dispose();
            //    }
				
            //}


		}

		private void menuItem1_Click(object sender, System.EventArgs e)
		{
		
		}

		private void menuItem7_Click(object sender, System.EventArgs e)
		{
			Application.Exit();
		}

        private void menuItem13_Click(object sender, EventArgs e)
        {
            if (this.ActiveMdiChild != null)
            {
                if (ActiveMdiChild.GetType() == typeof(Form2))
                {
                    ((Form2)this.ActiveMdiChild).SaveDJZQuick();
                }
            }
        }

		private void menuItem5_Click(object sender, System.EventArgs e)
		{
            if (this.ActiveMdiChild != null)
            {
                if (ActiveMdiChild.GetType() == typeof(Form2))
                {
                    ((Form2)this.ActiveMdiChild).SaveDJZ();
                }
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
                //this.ActiveMdiChild.Dispose();
                //this.ActiveMdiChild = null;
                //System.GC.Collect();
                //System.Threading.Thread.Sleep(0);
			}
		}

		private void menuItem11_Click(object sender, System.EventArgs e)
		{
            AboutBox1 about = new AboutBox1();
			about.ShowDialog(this);
		}

        private void menuItem15_Click(object sender, EventArgs e)
        {
            Form5 cfg = new Form5();
            cfg.ShowDialog(this);
        }

        private void menuItem14_Click(object sender, EventArgs e)
        {
            
        }

        private void menuItem4_Click_1(object sender, EventArgs e)
        {
            

        }

        private void menuItem17_Click(object sender, EventArgs e)
        {
            System.Windows.Forms.OpenFileDialog openFileDialog1 = new OpenFileDialog();

            openFileDialog1.Filter = "MIDI File (*.mid)|*.mid|MIDI File (*.midi)|*.midi|All File (*.*)|*.*";

            if (DialogResult.OK == openFileDialog1.ShowDialog())
            {
                Form2 newMDIChild = null;
                try
                {
                    newMDIChild = new Form2(openFileDialog1.FileName, config.DJZLineCount);
                    newMDIChild.MdiParent = this;
                    newMDIChild.Show();
                }
                catch (Exception err)
                {
                    if (newMDIChild != null)
                    {
                        newMDIChild.Close();
                    }
                    System.Windows.Forms.MessageBox.Show(err.Message);

                }

            }
        }

        private void menuItem18_Click(object sender, EventArgs e)
        {
            System.Windows.Forms.OpenFileDialog openFileDialog1 = new OpenFileDialog();

            openFileDialog1.Filter = "MIDI File (*.mid)|*.mid|MIDI File (*.midi)|*.midi|All File (*.*)|*.*";

            if (DialogResult.OK == openFileDialog1.ShowDialog())
            {

                Form7 newMDIChild = null;
                try
                {
                    newMDIChild = new Form7(openFileDialog1.FileName, config.BMSLineCount);
                    newMDIChild.MdiParent = this;
                    newMDIChild.Show();
                }
                catch (Exception err)
                {
                    if (newMDIChild != null)
                    {
                        newMDIChild.Close();
                    }
                    System.Windows.Forms.MessageBox.Show(err.Message);

                }

            }
        }

 





	
	}
}

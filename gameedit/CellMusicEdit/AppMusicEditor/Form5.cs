using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Cell.AppMusicEditor
{
    public partial class Form5 : Form
    {
        public Form5()
        {
            InitializeComponent();
            this.numericUpDown1.Value = Form1.config.DJZLineCount;
            //this.numericUpDown2.Value = Form1.config.BMSLineCount;
            this.timer1.Start();
        }

        private void Form5_Load(object sender, EventArgs e)
        {
            
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Form1.config.DJZLineCount = (int)(this.numericUpDown1.Value);
            //Form1.config.BMSLineCount = (int)(this.numericUpDown2.Value);
            Form1.config.Save();

            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {

        }
    }
}
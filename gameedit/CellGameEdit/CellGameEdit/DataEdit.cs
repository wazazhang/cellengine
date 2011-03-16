using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit
{
    public partial class DataEdit : Form
    {
        StringBuilder sb;

        public DataEdit( StringBuilder data)
        {
            InitializeComponent();
            sb = data;
            this.richTextBox1.Text = sb.ToString();
        }

        private void DataEdit_FormClosed(object sender, FormClosedEventArgs e)
        {
            sb.Remove(0,sb.Length);
            sb.Append(this.richTextBox1.Text);
        }

        private void richTextBox1_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Escape)
            {
                this.Close(); 
            }
        }
    }
}
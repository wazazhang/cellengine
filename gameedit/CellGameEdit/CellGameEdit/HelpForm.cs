using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit
{
    public partial class HelpForm : Form
    {
        public HelpForm()
        {
            InitializeComponent();
            richTextBox1.Text = Resource1.TextFileScriptHelp;
        }
    }
}
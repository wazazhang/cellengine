using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit.PM
{
    public partial class DataGridConfig : Form
    {
        public DataGridConfig()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterParent;
        }

        public DialogResult showDialog()
        {
            base.ShowDialog();
            return this.DialogResult;
        }

        public DataGridViewColumn getColumn()
        {
            return null;
        }
    }
}
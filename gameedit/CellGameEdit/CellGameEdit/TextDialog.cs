using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit.PM
{
    public partial class TextDialog : Form
    {
        public TextDialog(string name)
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterParent;
            this.textBox1.Text = name;
            this.textBox1.SelectAll();
            this.textBox1.Focus();
            this.textBox2.Visible = false;
        }

        DataGridView DataGrid = null;
        int ColumnIndex = 0;

        public TextDialog(DataGridView datagrid, int columnindex)
        {
            InitializeComponent();
            this.Text = "转换";
            this.StartPosition = FormStartPosition.CenterParent;

            DataGrid = datagrid;
            ColumnIndex = columnindex;
        }

        public string getText()
        {
            return textBox1.Text;
        }

        public DialogResult showDialog()
        {
            base.ShowDialog();
            return this.DialogResult;
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (DataGrid != null)
            {
                foreach (DataGridViewRow r in DataGrid.Rows)
                {
                    try
                    {
                        DataGridViewCell cell = r.Cells[ColumnIndex];

                        String src = cell.Value.ToString();

                        src = src.Replace(textBox1.Text, textBox2.Text);

                        if (cell.ValueType != null) {
                            cell.Value = Convert.ChangeType(src, cell.ValueType); 
                        }
                        
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(err.Message + "\n" + err.StackTrace);
                    }

                }
            }
        }
    }
}
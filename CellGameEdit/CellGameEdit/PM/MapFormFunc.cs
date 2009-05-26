using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit.PM
{
    public partial class MapFormFunc : Form
    {
        MapForm CurMap;

        public MapFormFunc(MapForm map)
        {
            InitializeComponent();

            CurMap = map;
        }

        public MapFormFunc(MapForm map, String script)
        {
            InitializeComponent();
            CurMap = map;
            TextScript.Text = script;
        }

        private void MapFormFunc_Load(object sender, EventArgs e)
        {
        }

        private void BtnLoad_Click(object sender, EventArgs e)
        {
            try
            {
                OpenFileDialog open = new OpenFileDialog();
                if (open.ShowDialog() == DialogResult.OK)
                {
                    TextScript.Text = System.IO.File.ReadAllText(open.FileName);
                }
            }
            catch (Exception err) 
            {
                MessageBox.Show(err.Message + "\n" + err.StackTrace);
            }
            
        }

        private void BtnSave_Click(object sender, EventArgs e)
        {
            try
            {
                SaveFileDialog save = new SaveFileDialog();
                if (save.ShowDialog() == DialogResult.OK)
                {
                    System.IO.File.WriteAllLines(save.FileName, TextScript.Lines);
                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message + "\n" + err.StackTrace);
            }
        }

        private void BtnRun_Click(object sender, EventArgs e)
        {
            String res = CurMap.scriptRun(TextScript.Text);
            MessageBox.Show(res);
        }

     
        // -------------------------------------------------------------------------


    }
}
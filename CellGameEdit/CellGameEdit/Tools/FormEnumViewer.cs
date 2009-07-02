using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Collections;
using CellGameEdit.PM;

namespace CellGameEdit.Tools
{
    public partial class FormEnumViewer : Form
    {
        public FormEnumViewer()
        {
            InitializeComponent();
        }

        private void FormEnumViewer_Load(object sender, EventArgs e)
        {
            refreshEnums();
        }



        //--------------------------------------------------------------------------------------------------------------------------------------
        #region 主菜单


        private void 总在最前ToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void 总在最前ToolStripMenuItem_CheckedChanged(object sender, EventArgs e)
        {
            this.TopMost = menuTopMost.Checked;
        }



        private void 十进制ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            foreach(ListViewItem item in listView1.Items )
            {
                try
                {
                    GameEnum gameenum = (GameEnum)item.Tag;
                    item.SubItems[1].Text = gameenum.Value.ToString("d");
                }
                catch (Exception err) { }
            }
        }

        private void 十六进制ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem item in listView1.Items)
            {
                try
                {
                    GameEnum gameenum = (GameEnum)item.Tag;
                    item.SubItems[1].Text = "0x"+gameenum.Value.ToString("X8");
                }
                catch (Exception err) { }
            }
        }

        private void 二进制ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem item in listView1.Items)
            {
                try
                {
                    GameEnum gameenum = (GameEnum)item.Tag;
                    long v = gameenum.Value;
                    String bin = "";

                    for (int i = 0; i < 32 || v!=0; i++)
                    {
                        bin = (v&0x01) + bin;
                        v = (v >> 1);
                    }
                    item.SubItems[1].Text = bin + "(b)";
                }
                catch (Exception err) { }
            }
        }

        #endregion
        //--------------------------------------------------------------------------------------------------------------------------------------

        

        public void refreshEnums()
        {
            try
            {
                this.treeView1.Nodes.Clear();
                this.listView1.Items.Clear();

                Hashtable files = GameEnum.getEnums();

                int i = 0;
                foreach(object key in files.Keys)
                {
                    try
                    {
                        Hashtable value = (Hashtable)files[key];

                        TreeNode node = new TreeNode(key.ToString());
                        node.Tag = value;
                        treeView1.Nodes.Add(node);

                        if(i==0)
                        {
                            refreshLines(value);
                        }

                        i++;
                    }
                    catch (Exception err)
                    {
                    }
                }

                toolStripStatusLabel1.Text = "(" + files.Count + ")个对象";

               
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message + "\n" + err.StackTrace + "\n");
            }
        }

        public void refreshLines(Hashtable file)
        {
            try
            {
                this.listView1.Items.Clear();
              
                foreach(object key in file.Keys)
                {
                    try
                    {
                        GameEnum value = (GameEnum)file[key];

                        String info = value.Key;
                        String type = value.Value.ToString();
                        String reson = value.Rem;

                        ListViewItem item = new ListViewItem(new string[] { info, type, reson });
                        item.Tag = value;
                        listView1.Items.Add(item);
                    }
                    catch (Exception err)
                    {
                    }
                }

                listView1.Columns[0].Text = "(" + file.Count + ")条枚举";

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message + "\n" + err.StackTrace + "\n");
            }
        }


        //--------------------------------------------------------------------------------------------------------------------------------------
        // events

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            refreshEnums();
        }


        // select enum file
        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            try
            {
                Hashtable ht = (Hashtable)e.Node.Tag;
                refreshLines(ht);
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message + "\n" + err.StackTrace + "\n");
            }
            
        }



        // sum selected 
        private void toolStripButton2_Click_1(object sender, EventArgs e)
        {
            Boolean isHex = false;

            long sum = 0;
            foreach (ListViewItem item in listView1.Items)
            {
                try
                {
                    if (item.SubItems[1].Text.Contains("x"))
                    {
                        isHex = true;
                    }

                    if (item.Checked)
                    {
                        GameEnum gameenum = (GameEnum)item.Tag;
                        sum = sum | gameenum.Value;
                    }
                }
                catch (Exception err) { }
            }

            if (isHex)
            {
                TextBoxSum.Text = sum.ToString("X8");
            }
            else
            {
                TextBoxSum.Text = sum.ToString();
            }
          

        }

        // select or value
        private void toolStripButton3_Click_1(object sender, EventArgs e)
        {
            long sum = 0;

            try
            {
                if (TextBoxOR.Text.Contains("0x"))
                {
                    sum = long.Parse(TextBoxOR.Text.Substring(2), System.Globalization.NumberStyles.AllowHexSpecifier);
                }
                else
                {
                    sum = long.Parse(TextBoxOR.Text);
                }

                foreach (ListViewItem item in listView1.Items)
                {
                    try
                    {
                        GameEnum gameenum = (GameEnum)item.Tag;
                        item.Checked = (gameenum.Value & sum) != 0;
                    }
                    catch (Exception err) { }
                }

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message+ "\n");
            }

        }

        //clear
        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem item in listView1.Items)
            {
                item.Checked = false;
            }
        }

        // select all
        private void toolStripButton5_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem item in listView1.Items)
            {
                item.Checked = true;
            }

        }


   



        #region util



        #endregion

    }
}


namespace CellGameEdit
{
    public class GameEnum
    {
        public string Key = "";
        public long Value = 0;
        public string Rem = "";



        // util
        public static Hashtable getEnums()
        {
            try
            {
                String dir = ProjectForm.getEnumsDir();

                Hashtable ret = new Hashtable();

                String[] files = Directory.GetFiles(dir, "*.enum", SearchOption.TopDirectoryOnly);

                for (int i = 0; i < files.Length; i++)
                {
                    try
                    {
                        Hashtable ht = getEnum(files[i]);

                        if (ht != null)
                        {
                            ret.Add(Path.GetFileNameWithoutExtension(files[i]), ht);
                        }
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(err.Message + "\n" + err.StackTrace + "\n");
                    }
                }

               
                return ret;
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message + "\n" + err.StackTrace + "\n");

                return null;
            }
        }

        public static Hashtable getEnum(String file)
        {
            try
            {
                Hashtable ht = new Hashtable();

                string[] lines = File.ReadAllLines(file);

                for (int j = 0; j < lines.Length; j++)
                {
                    if (lines[j].Contains("="))
                    {
                        try
                        {

                            String key = lines[j].Substring(0, lines[j].IndexOf("=")).Trim();
                            String value = Util.getIncludeString(lines[j], "=", ",").Trim();

                            String rem = "";
                            if (lines[j].Contains(@"//"))
                            {
                                try { rem = lines[j].Substring(lines[j].IndexOf(@"//") + 2); }
                                catch (Exception err) {}
                            }

                            GameEnum gameenum = new GameEnum();
                            gameenum.Key = key;
                            gameenum.Rem = rem;

                            if (value.Contains("0x"))
                            {
                                value = value.Substring(value.IndexOf("0x") + 2);
                                gameenum.Value = long.Parse(value.Trim(), System.Globalization.NumberStyles.AllowHexSpecifier);
                            }
                            else
                            {
                                gameenum.Value = long.Parse(value.Trim());
                            }

                            ht.Add(key, gameenum);

                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(err.Message + "\n" + err.StackTrace + "\n");
                        }
                    }
                }

                return ht;
            }
            catch (Exception err)
            {
                Console.WriteLine(err.Message + "\n" + err.StackTrace + "\n");

                return null;
            }
        }
     
    }

}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization.Formatters.Soap;

using System.Threading;

using CellGameEdit.PM;

namespace CellGameEdit
{
    public partial class Form1 : Form
    {
        private static Form1 instance;

        private ProjectForm prjForm;
        private string[] open_args;

        public Form1()
        {
            InitializeComponent();
            instance = this;
            initGobalImageConvertScript();
        }

        public Form1(string file)
        {
            InitializeComponent();
            instance = this;
            initGobalImageConvertScript();
            init(file, new string[0]);
        }

        public Form1(string file, string[] args)
        {
            InitializeComponent();
            instance = this;
            initGobalImageConvertScript();
            init(file, args);
        }

        private void init(string file, string[] args)
        {
            string name = System.IO.Path.GetFileName(file);
            string dir = System.IO.Path.GetDirectoryName(file);


            ProjectForm.workSpace = dir;
            ProjectForm.workName = file;
            SoapFormatter formatter = new SoapFormatter();
            Stream stream = new FileStream(file, FileMode.Open, FileAccess.Read, FileShare.Read);

            if (stream.Length != 0)
            {
                prjForm = (ProjectForm)formatter.Deserialize(stream);
            }
            else
            {
                prjForm = new ProjectForm();
            }

            stream.Close();

            open_args = args;
        }

//      -------------------------------------------------------------------------------------------------------------------------------------------


        static void run_convert()
        {
            ProjectForm.is_console = true;
            instance.SuspendLayout();
            instance.保存ToolStripMenuItem_Click(null, null);
            instance.Close();
            Application.Exit();
        }

        void run_args()
        {
            if (open_args != null && open_args.Length > 0)
            {
                if (open_args[0].Trim().Equals("-convert"))
                {
                    Thread t = new Thread(new ThreadStart(run_convert));
                    t.Start();
                }
            }
        }

//      -------------------------------------------------------------------------------------------------------------------------------------------

        private void Form1_Load(object sender, EventArgs e)
        {
            timer1.Start();

            CfgOutputEncoding.Checked = Config.Default.IsOutEncodingInfo;
            ProjectForm.IsOutEncodingInfo = CfgOutputEncoding.Checked;
			try
			{
				javax.microedition.lcdui.Graphics.font = Config.Default.GraphicsFont;
			}
			catch (Exception err) { 
			
			}
        }

        private void Form1_Shown(object sender, EventArgs e)
        {
            if (prjForm != null)
            {
                prjForm.MdiParent = this;
                prjForm.Show();
            }

            run_args();
        }


        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            if (prjForm != null)
            {
                if (outputThread != null)
                {
                    EndOutputProjectScript();
                }
            }
        }

        //------------------------------------------------------------------------------------------------------------------------------------------------

        private void 文件ToolStripMenuItem_DropDownOpening(object sender, EventArgs e)
        {

        }

        private void 新建ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (prjForm == null || prjForm.Visible == false)
            {
                //FolderBrowserDialog dir = new FolderBrowserDialog();
                // dir.ShowNewFolderButton = true;
                //dir.Description = "新建工程文件夹";

                //if (dir.ShowDialog() == DialogResult.OK)
                //{
                //if (System.IO.File.Exists(dir.SelectedPath + "\\Project.cpj"))
                //{
                //    MessageBox.Show("已经存在一个工程文件 Project.cpj");
                //}
                //else
                //{

                //    System.IO.Directory.CreateDirectory(dir.SelectedPath);
                //   System.IO.Directory.CreateDirectory(dir.SelectedPath + "\\script");

                ProjectForm.workSpace = "";
                ProjectForm.workName = "";
                prjForm = new ProjectForm();
                prjForm.MdiParent = this;
                prjForm.Show();
                //}

                //}
            }
            else
            {
            }
        }

        private void 打开ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (prjForm == null || prjForm.Visible == false)
            {
                OpenFileDialog openFileDialog1 = new OpenFileDialog();
                openFileDialog1.Filter = "CPJ files (*.cpj)|*.cpj";
                try
                {
                    openFileDialog1.InitialDirectory = System.IO.Path.GetDirectoryName(Config.Default.LastProjectOpenDir);
                }catch(Exception err){}
                if (openFileDialog1.ShowDialog() == DialogResult.OK)
                {
                    try
                    {
                        ProjectForm.workSpace = System.IO.Path.GetDirectoryName(openFileDialog1.FileName);
                        ProjectForm.workName = openFileDialog1.FileName;
						Config.Default.LastProjectOpenDir = openFileDialog1.FileName;
						Config.Default.Save();

                        SoapFormatter formatter = new SoapFormatter();
                        FileStream stream = new FileStream(openFileDialog1.FileName, FileMode.Open, FileAccess.Read, FileShare.Read);
                        prjForm = (ProjectForm)formatter.Deserialize(stream);
                        stream.Close();
                        prjForm.MdiParent = this;
                        prjForm.Show();
                    }
                    catch (Exception err)
                    {
                        MessageBox.Show(err.Message + "\n" + err.StackTrace + "  at  " + err.Message);
                    }
                }

            }
        }


        private void 清理ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (prjForm != null && prjForm.Visible == true)
                {
                    if (ProjectForm.workName == "")
                    {
                        SaveFileDialog sfd = new SaveFileDialog();
                        sfd.Filter = "CPJ files (*.cpj)|*.cpj";
                        if (sfd.ShowDialog() == DialogResult.OK)
                        {
                            ProjectForm.workSpace = System.IO.Path.GetDirectoryName(sfd.FileName);
                            ProjectForm.workName = sfd.FileName;
                        }
                    }

                    String dir = ProjectForm.workSpace + "\\tiles";
                    String[] dirs = System.IO.Directory.GetDirectories(dir);
                    for (int i = 0; i < dirs.Length; i++)
                    {
                        try
                        {
                            if (System.IO.Directory.Exists(dirs[i]))
                            {
                                Console.Write("Delete : ");
                                String[] tiles = System.IO.Directory.GetFiles(dirs[i], "*.tile", SearchOption.TopDirectoryOnly);
                                for (int j = 0; j < tiles.Length; j++)
                                {
                                    try
                                    {
                                        if (System.IO.File.Exists(tiles[j]))
                                        {
                                            Console.Write(" " + System.IO.Path.GetFileName(tiles[j]) + " ");
                                            System.IO.File.Delete(tiles[j]);
                                        }
                                    }
                                    catch (Exception err) { Console.WriteLine(); Console.WriteLine("Error Delete : " + tiles[j] + " " + err.Message); }
                                }
                                Console.WriteLine("");
                            }
                        }
                        catch (Exception err) { Console.WriteLine(); Console.WriteLine("Error Delete : " + dirs[i] + " " + err.Message); }

                    }

                }

            }
            catch (Exception err) { MessageBox.Show(err.Message); }

            保存ToolStripMenuItem_Click(sender, e);
        }

        private void 保存ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (prjForm != null && prjForm.Visible == true)
            {

                this.Enabled = false;

                try
                {

                    if (ProjectForm.workName == "")
                    {
                        SaveFileDialog sfd = new SaveFileDialog();
                        sfd.Filter = "CPJ files (*.cpj)|*.cpj";
                        if (sfd.ShowDialog() == DialogResult.OK)
                        {
                            ProjectForm.workSpace = System.IO.Path.GetDirectoryName(sfd.FileName);
                            ProjectForm.workName = sfd.FileName;
                        }
                    }
                    if (ProjectForm.workName != "")
                    {
                        SoapFormatter formatter = new SoapFormatter();
                        MemoryStream stream = new MemoryStream();
                        try
                        {
                            this.progressBar1.Value = (this.progressBar1.Maximum / 4);

                            formatter.Serialize(stream, prjForm);

                            this.progressBar1.Value = (this.progressBar1.Maximum / 2);

                            FileStream fs = new FileStream(ProjectForm.workName, FileMode.Create, FileAccess.Write, FileShare.None);
                            stream.Seek(0, SeekOrigin.Begin);
                            while (true)
                            {
                                int data = stream.ReadByte();
                                if (data > 0)
                                {
                                    fs.WriteByte((byte)data);
                                    try
                                    {
                                        this.progressBar1.Value = (int)((this.progressBar1.Maximum / 2) + (this.progressBar1.Maximum / 2) * stream.Position / stream.Length);
                                    }
                                    catch (Exception err) { }
                                }
                                else
                                {
                                    break;
                                }
                            }

                            fs.Close();

                            this.progressBar1.Value = this.progressBar1.Maximum;

                        }
                        catch (Exception err)
                        {
                            MessageBox.Show(err.StackTrace + "  at  " + err.Message);
                        }
                        stream.Close();
                    }
                }
                catch (Exception err)
                {
                    MessageBox.Show("目录错误 " + err.StackTrace + "  at  " + err.Message);
                }

                this.Enabled = true;
            }
            else
            {
            }

        }

        private void 另存为toolStripMenuItem8_Click(object sender, EventArgs e)
        {
            if (prjForm != null && prjForm.Visible == true)
            {
                try
                {
                    SaveFileDialog sfd = new SaveFileDialog();
                    sfd.Filter = "CPJ files (*.cpj)|*.cpj";
                    if (sfd.ShowDialog() == DialogResult.OK)
                    {
                        ProjectForm.workSpace = System.IO.Path.GetDirectoryName(sfd.FileName);
                        ProjectForm.workName = sfd.FileName;

                        //
                        if (ProjectForm.workName != "")
                        {
                            SoapFormatter formatter = new SoapFormatter();
                            Stream stream = new MemoryStream();
                            try
                            {
                                formatter.Serialize(stream, prjForm);

                                FileStream fs = new FileStream(ProjectForm.workName, FileMode.Create, FileAccess.Write, FileShare.None);
                                stream.Seek(0, SeekOrigin.Begin);
                                while (true)
                                {
                                    int data = stream.ReadByte();
                                    if (data > 0)
                                    {
                                        fs.WriteByte((byte)data);
                                    }
                                    else
                                    {
                                        break;
                                    }
                                }

                                fs.Close();
                            }
                            catch (Exception err)
                            {
                                MessageBox.Show(err.StackTrace + "  at  " + err.Message);
                            }
                            stream.Close();
                        }
                        //
                    }
                }
                catch (Exception err)
                {
                    MessageBox.Show("目录错误 " + err.StackTrace + "  at  " + err.Message);
                }
            }
            else
            {
            }
        }

        private void 关闭ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (prjForm != null)
            {
                prjForm.Close();
                prjForm = null;
            }
        }

        private void 退出ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void 显示输出ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            OutputForm output = new OutputForm();
            output.MdiParent = this;
            output.Show();
        }

        private void 文件ToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        //------------------------------------------------------------------------------------------------------------------------------------------------
        //input script
        private void 脚本ToolStripMenuItem_Click(object sender, EventArgs e)
        {

            try
            {
                OpenFileDialog openFileDialog1 = new OpenFileDialog();
                openFileDialog1.Title = "选择脚本文件";
                openFileDialog1.Filter = "Txt files (*.txt)|*.txt|All files (*.*)|*.*";
                openFileDialog1.RestoreDirectory = true;
                openFileDialog1.Multiselect = false;

                if (openFileDialog1.ShowDialog() == DialogResult.OK)
                {
                    String name = System.IO.Path.GetFileName(openFileDialog1.FileName);
                    String src = System.IO.Path.GetDirectoryName(openFileDialog1.FileName) + "\\";
                    String dst = Application.StartupPath + "\\script\\";

                    System.IO.File.Copy(src + name, dst + name, true);
                }
            }
            catch (Exception err)
            {
                MessageBox.Show("脚本导入错误：" + err.StackTrace + "  at  " + err.Message);
            }
        }
        // del script
        private void 删除ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                String name = Application.StartupPath + "\\script\\" + toolStripComboBox1.Text;

                if (File.Exists(name))
                {
                    if (MessageBox.Show(
                        "确认删除\"" + toolStripComboBox1.Text + "\"？",
                        "Wanning",
                        MessageBoxButtons.OKCancel,
                        MessageBoxIcon.Warning,
                        MessageBoxDefaultButton.Button2
                        ) == DialogResult.OK)
                    {
                        System.IO.File.Delete(name);
                        toolStripComboBox1.Text = "";
                        toolStripComboBox1.Items.Clear();
                    }
                }


            }
            catch (Exception err)
            {
                MessageBox.Show("脚本删除错误：" + err.StackTrace + "  at  " + err.Message);
            }
        }


        // out put script
        private void javaToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (prjForm != null && prjForm.Visible)
            {
                //prjForm.Output();
            }
        }

        private void toolStripComboBox1_DropDown(object sender, EventArgs e)
        {
            toolStripComboBox1.Items.Clear();

            try
            {

                String dir = Application.StartupPath + "\\script\\";

                if (System.IO.Directory.Exists(dir))
                {
                    String[] scriptFiles;

                    scriptFiles = System.IO.Directory.GetFiles(dir);
                    for (int i = 0; i < scriptFiles.Length; i++)
                    {
                        scriptFiles[i] = System.IO.Path.GetFileName(scriptFiles[i]);
                    }




                    toolStripComboBox1.Items.AddRange(scriptFiles);
                }
            }
            catch (Exception err)
            {
            }


        }

        private void 当前工程脚本ToolStripMenuItem_DropDownOpening(object sender, EventArgs e)
        {
            try
            {
                当前工程脚本ToolStripMenuItem.DropDownItems.Clear();
                当前工程脚本ToolStripMenuItem.Enabled = false;

                if (prjForm != null && prjForm.Visible)
                {

                    String dir = ProjectForm.workSpace + "\\script\\";

                    if (System.IO.Directory.Exists(dir))
                    {
                        String[] scriptFiles = System.IO.Directory.GetFiles(dir);
                        ToolStripItem[] items = new ToolStripItem[scriptFiles.Length];
                        for (int i = 0; i < scriptFiles.Length; i++)
                        {
                            scriptFiles[i] = System.IO.Path.GetFileName(scriptFiles[i]);
                            items[i] = new ToolStripMenuItem();
                            items[i].Text = scriptFiles[i];
                            items[i].AutoSize = true;
                            items[i].Click += new EventHandler(outputLocalProjectScript);

                            当前工程脚本ToolStripMenuItem.Enabled = true;
                        }

                        当前工程脚本ToolStripMenuItem.DropDownItems.AddRange(items);
                    }
                }
            }
            catch (Exception err)
            {
            }
        }

        private void 关于ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            AboutBox1 about = new AboutBox1();
            about.ShowDialog();
        }
        //help
        private void toolStripMenuItem7_Click(object sender, EventArgs e)
        {
            HelpForm help = new HelpForm();
            help.ShowDialog();
        }


        Thread outputThread;
        String outputDir;

        void ShowOutput()
        {
            prjForm.OutputCustom(outputDir);

        }

        void StartOutputProjectScript(String dir)
        {
            this.Cursor = Cursors.WaitCursor;
            this.progressBar1.Value = 0;

            prjForm.RefreshNodeName();
            prjForm.sortTreeView();
            prjForm.lockForms();

            OutputForm output = new OutputForm();
            //output.WindowState = FormWindowState.Maximized;
            output.StartPosition = FormStartPosition.CenterScreen;
            //output.MdiParent = this;

            outputThread = new Thread(new ThreadStart(ShowOutput));
            outputThread.Start();

            output.ShowDialog(this);

            //prjForm.OutputCustom(outputDir);
            //this.progressBar1.Value = this.progressBar1.Maximum;
            //this.Cursor = Cursors.Default;
        }

        void EndOutputProjectScript()
        {

            outputThread.Abort();
            outputThread = null;
            prjForm.unlockForms();

            this.progressBar1.Value = this.progressBar1.Maximum;
            this.Cursor = Cursors.Default;

        }

        private void 自定义脚本ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (prjForm != null && prjForm.Visible && toolStripComboBox1.SelectedItem != null)
                {
                    outputDir = Application.StartupPath + "\\script\\" + toolStripComboBox1.Text;
                    StartOutputProjectScript(outputDir);

                }
            }
            catch (Exception err) { Console.WriteLine(err.StackTrace + "  at  " + err.Message); }
        }

        private void outputLocalProjectScript(object sender, EventArgs e)
        {
            try
            {
                if (prjForm != null && prjForm.Visible)
                {
                    outputDir = ProjectForm.workSpace + "\\script\\" + ((ToolStripMenuItem)sender).Text;
                    StartOutputProjectScript(outputDir);

                }
            }
            catch (Exception err) { Console.WriteLine(err.StackTrace + "  at  " + err.Message); }
        }



        // update
        private void timer1_Tick(object sender, EventArgs e)
        {
            if (prjForm != null)
            {
                if (outputThread != null)
                {
                    if (!outputThread.IsAlive)
                    {
                        EndOutputProjectScript();
                    }
                    else
                    {
                        if (this.progressBar1.Value < this.progressBar1.Maximum - 10)
                        {
                            this.progressBar1.Increment(1);
                        }
                    }
                }
                else
                {
                    if (prjForm.Visible == false)
                    {
                        prjForm.Dispose();
                        prjForm = null;
                    }
                    else
                    {
                        this.Text = ProjectForm.workSpace;
                    }
                }
            }
            else
            {
                this.Text = "Cell Game Edit";
            }


        }

        private void CfgOutputEncoding_CheckedChanged(object sender, EventArgs e)
        {
            ProjectForm.IsOutEncodingInfo = CfgOutputEncoding.Checked;

            Config.Default.IsOutEncodingInfo = CfgOutputEncoding.Checked;

            Config.Default.Save();
        }

        private void 枚举查看器ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Tools.FormEnumViewer form = new CellGameEdit.Tools.FormEnumViewer();
            form.Show();
        }

        private void 改变渲染字体ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            FontDialog fd = new FontDialog();
            fd.Font = javax.microedition.lcdui.Graphics.font;

            if (fd.ShowDialog() == DialogResult.OK)
            {
                javax.microedition.lcdui.Graphics.font = fd.Font;
                Config.Default.GraphicsFont = fd.Font;

                Config.Default.Save();
            }
        }

        //-----------------------------------------------------------------------------------------

        static String[] image_convert_list = new String[0];

        public void initGobalImageConvertScript()
        {
            try
            {
                String dir = Application.StartupPath + "\\converter\\";

                if (System.IO.Directory.Exists(dir))
                {
                    String[] scriptFiles = System.IO.Directory.GetFiles(dir);
                    menuItemGobalImgeConvertScript.DropDown.Items.Clear();

                    for (int i = 0; i < scriptFiles.Length; i++)
                    {
                        scriptFiles[i] = System.IO.Path.GetFileName(scriptFiles[i]);

                        ToolStripMenuItem scitem = new ToolStripMenuItem(scriptFiles[i]);
                        scitem.CheckOnClick = true;
                        menuItemGobalImgeConvertScript.DropDown.Items.Add(scitem);
                        if (scriptFiles[i].Equals( Config.Default.GobalImageConvertScriptFile ))
                        {
                            scitem.Checked = true;
                        }
                        scitem.CheckStateChanged += new EventHandler(onImageConvertChanged);
                    }

                    image_convert_list = scriptFiles;
                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }

        private void onImageConvertChanged(object sender, EventArgs e)
        {
            ToolStripMenuItem scitem = ( ToolStripMenuItem )sender;
            if (scitem.Checked) {
                foreach (ToolStripMenuItem im in menuItemGobalImgeConvertScript.DropDown.Items) {
                    if (im != scitem) {
                        im.Checked = false;
                    }
                }
                Config.Default.GobalImageConvertScriptFile = scitem.Text;
                Config.Default.Save();
            } else {
                foreach (ToolStripMenuItem im in menuItemGobalImgeConvertScript.DropDown.Items)
                {
                    if (im.Checked == true)
                    {
                        return;
                    }
                }
                Config.Default.GobalImageConvertScriptFile = "";
                Config.Default.Save();
            }
        }

        static public String[] getImageConvertScriptList()
        {
            return image_convert_list;
        }

        static public String getGobalImageConvertScriptFile()
        {
            return Config.Default.GobalImageConvertScriptFile;
        }


    }
}
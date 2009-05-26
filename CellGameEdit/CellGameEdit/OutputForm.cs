using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using CellGameEdit.PM;
using System.IO;
using System.Runtime.Serialization.Formatters.Soap;

namespace CellGameEdit
{
    public partial class OutputForm : Form
    {
        System.IO.StringWriter sw;

        int srcIndex = 0;
        int dstIndex = 0;

        Thread outputThread;

        public OutputForm()
        {
            InitializeComponent();

            sw = new System.IO.StringWriter();
            System.Console.SetOut(sw);
            timer1.Start();

            this.textBox1.Text = "";

        }

        public OutputForm(String file, String[] scripts)
        {
            InitializeComponent();

            sw = new System.IO.StringWriter();
            System.Console.SetOut(sw);
            timer1.Start();

            this.textBox1.Text = "";


            //--------------------
            FileName = Path.GetFullPath(file);
            Scripts = scripts;
            //--------------------

            this.Text = FileName;

            ProjectForm.IsOutEncodingInfo = Config.Default.IsOutEncodingInfo;
        }


        private void OutputForm_FormClosed(object sender, FormClosedEventArgs e)
        {
          
            try
            {
                outputThread.Abort();
                outputThread = null;
            }
            catch (Exception err) { Console.WriteLine(err.Message); }


        }

        private void Output_FormClosing(object sender, FormClosingEventArgs e)
        {
           
        }

        private void Output_Load(object sender, EventArgs e)
        {
            if (FileName != null && Scripts != null)
            {
                outputThread = new Thread(new ThreadStart(DirectOutput));
                outputThread.Start();
            }
           
        }

        

        private void timer1_Tick_1(object sender, EventArgs e)
        {
            srcIndex = sw.ToString().Length;
            
            if (this.textBox1.Text.Length != sw.ToString().Length) 
            {
                this.textBox1.AppendText(sw.ToString().Substring(dstIndex,srcIndex-dstIndex));
                this.textBox1.ScrollToCaret();

                dstIndex = srcIndex;
            }


            if (outputThread != null)
            {
                if (outputThread.ThreadState == ThreadState.Stopped)
                {
                    this.Close();
                }
            }
          

            //this.textBox1.Select(this.textBox1.Text.Length , 0);
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            this.textBox1.Text = "";

            sw.Close();
            sw = null;
            sw = new System.IO.StringWriter();
            System.Console.SetOut(sw);

            srcIndex = 0;
            dstIndex = 0;
        }

        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            
        }


        public void clearText()
        {
            this.textBox1.Clear();
        }

//      --------------------------------------------------------------------------------------------------------------------------------------------

        static string FileName;
        static string[] Scripts;

        public static void DirectOutput()
        {
            try
            {
                if (FileName != null && Scripts != null)
                {
                    Console.WriteLine("Loding : " + FileName);

                    string name = System.IO.Path.GetFileName(FileName);
                    string dir = System.IO.Path.GetDirectoryName(FileName);

                    ProjectForm project = null;

                    ProjectForm.workSpace = dir;
                    ProjectForm.workName = FileName;
                    SoapFormatter formatter = new SoapFormatter();
                    Stream stream = new FileStream(FileName, FileMode.Open, FileAccess.Read, FileShare.Read);

                    if (stream.Length != 0)
                    {
                        project = (ProjectForm)formatter.Deserialize(stream);
                    }

                    stream.Close();

                    if (project != null)
                    {
                        for (int i = 0; i < Scripts.Length; i++)
                        {
                            String script = Scripts[i];

                            if (!File.Exists(script))
                            {
                                script = Application.StartupPath + @"\script\" + script;
                            }

                            Console.WriteLine("Output Script File : " + script);

                            try
                            {
                                project.OutputCustom(script);
                            }
                            catch (Exception err) { Console.WriteLine(err.Message); }
                        }
                    }

                }
            }
            finally
            {
                FileName = null;
                Scripts = null;
                Console.WriteLine("Complete !");
            }
             
        }


    }


}
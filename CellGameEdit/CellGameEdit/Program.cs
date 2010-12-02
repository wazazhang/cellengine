using System;
using System.Collections.Generic;
using System.Windows.Forms;
using CellGameEdit.PM;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization.Formatters.Soap;
using System.Threading;

namespace CellGameEdit
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            try
            {
                if (args != null && args.Length > 0)
                {
                    if (args.Length > 1)
                    {
                        if (args[0].Trim().StartsWith("-"))
                        {
                            openConvert(args);
                        }
                        else
                        {
                            openOutput(args);
                        }
                    }
                    else if (args.Length == 1)
                    {
                        openProject(args);
                    }
                }
                else
                {
                    openMainForm();
                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message + "\n" + err.StackTrace + "  at  " + err.Message);
            }

        }

//      ---------------------------------------------------------------------------------------------------------------------

        private static void openConvert(string[] args)
        {
            string filePath = args[args.Length - 1];

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(filePath, args));

        }

        private static void openOutput(string[] args)
        {
            string filePath = args[0];

            string[] scripts = new string[args.Length - 1];
            for (int i = 1; i < args.Length; i++)
            {
                scripts[i - 1] = args[i];
                Console.Out.WriteLine("Load script file : " + scripts[i - 1]);
            }

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new OutputForm(filePath, scripts));

        }

        private static void openProject(string[] args)
        {
            string filePath = args[0];

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(filePath));

        }

        private static void openMainForm()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }

    }


}
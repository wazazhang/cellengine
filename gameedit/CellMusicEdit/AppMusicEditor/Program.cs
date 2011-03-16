using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Cell.AppMusicEditor
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main()
        {
            
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            

            Form1 frm = new Form1();
            frm.Show();

            Application.Run(frm);
        }


    }
}
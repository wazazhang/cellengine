using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace CellGameOutput
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args != null && args.Length > 1)
            {
                string filePath = args[0];

                Console.Out.WriteLine("output : " + filePath);
                try
                {
                    Console.SetOut(new System.IO.StringWriter());

                    string[] scripts = new string[args.Length - 1];

                    for (int i = 1; i < args.Length; i++)
                    {
                        scripts[i - 1] = args[i];
                        Console.Out.WriteLine("Load script file : " + scripts[i - 1]);
                    }

                    new Output(filePath, scripts);

                    return;
                }
                catch (Exception err)
                {
                    Console.WriteLine(err.Message + "\n" + err.StackTrace + "  at  " + err.Message);
                }
            }

            Console.Out.WriteLine("usage : CellGameOutput <cpj file> <script files...>");
        }
    }
}

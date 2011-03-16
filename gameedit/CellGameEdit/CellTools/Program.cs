using System;
using System.Collections.Generic;
using System.Text;

namespace CellTools
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args == null || args.Length == 0)
            {
                Console.WriteLine(
                    "Cell Console Tools \n" +
                    "by WAZA\n" +
                    "wazazhang@gmail.com\n" +
                    "Usage : \n" +
                    "\n"+
                    "-FileToText -file [filename] -format [head] [byte split] [tail] \n"
                    );
            }
            if (args[0] == "-FileToText") FileToText(args);


        }

        static void FileToText(string[] args)
        {
        }









    }

}


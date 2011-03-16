using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using CellGameEdit.PM;
using System.IO;
using System.Runtime.Serialization.Formatters.Soap;

namespace CellGameOutput
{
    class Output
    {
        public Output(String file, String[] scripts)
        {
            ProjectForm.IsOutEncodingInfo = false;

            string FileName = Path.GetFullPath(file);
            string[] Scripts = scripts;

            if (FileName != null && Scripts != null)
            {
               DirectOutput(FileName, Scripts);
            }
           
        }

        private void DirectOutput(string FileName, string[] Scripts)
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
                    ProjectForm.is_console = true;
                    SoapFormatter formatter = new SoapFormatter();
                    Stream stream = new FileStream(FileName, FileMode.Open, FileAccess.Read, FileShare.Read);

                    if (stream.Length != 0)
                    {
                        project = (ProjectForm)formatter.Deserialize(stream);
                    }

                    stream.Close();



                    if (project != null)
                    {
                        try
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
                        finally
                        {
                            project.Close();
                            project.Dispose();
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

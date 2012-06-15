using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
using System.IO;
using System.Collections;
using CellGameEdit.PM.plugin;


namespace CellGameEdit.PM
{
    public partial class FormEventTemplate : Form, EventTemplatePlugin
    {
        private Hashtable MapEventFile = new Hashtable();
        private Hashtable Images = new Hashtable();

        public FormEventTemplate()
        {
            InitializeComponent();
        }
        
        public void initPlugin(string workdir) {
            refreshFiles();
        }

        private void FormEventTemplate_Load(object sender, EventArgs e)
        {
            
        }

        private void FormEventTemplate_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.Hide();
            }
        }

        private void toolComboEventFiles_DropDown(object sender, EventArgs e)
        {
           
        }

        private void toolComboEventFiles_SelectedIndexChanged(object sender, EventArgs e)
        {
            refreshFile(toolComboEventFiles.SelectedItem.ToString());
        }

        private void btnRefreshFiles_Click(object sender, EventArgs e)
        {
            refreshFiles();
        }


        private void refreshFiles()
        {
            Images.Clear();
            MapEventFile.Clear();
            toolComboEventFiles.Items.Clear();
            try
            {
                String dir = Application.StartupPath + "\\events";

                if (System.IO.Directory.Exists(dir))
                {
                    String[] eventFiles = System.IO.Directory.GetFiles(dir, "*.txt");
                    for (int i = 0; i < eventFiles.Length; i++)
                    {
                        eventFiles[i] = System.IO.Path.GetFileName(eventFiles[i]);
                        MapEventFile[eventFiles[i]] = genEventTemplates(eventFiles[i]);
                    }
                    toolComboEventFiles.Items.AddRange(eventFiles);
                    toolComboEventFiles.SelectedIndex = 0;
                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }
        
        private Hashtable genEventTemplates(String subfile)
        {
            Hashtable ret = new Hashtable();
            String path = Application.StartupPath + "\\events\\" + subfile;
            try
            {
                if (System.IO.File.Exists(path))
                {
                    string[] lines = File.ReadAllLines(path);
                    for (int j = 0; j < lines.Length; j++)
                    {
                        string line = lines[j].Trim();
                        if (!lines[j].StartsWith("#"))
                        {
                            string[] columns = line.Split(new char[] { '|' });
                            EventTemplate et = new EventTemplate(subfile, columns);
                            javax.microedition.lcdui.Image icon = (javax.microedition.lcdui.Image)Images[et.imageKey];
                            if (icon == null && et.imageKey.Length > 0)
                            {
                                try
                                {
                                    icon = new javax.microedition.lcdui.Image(
                                        Image.FromFile(Application.StartupPath + "\\events\\" + et.imageKey)
                                        );
                                    imageList1.Images.Add(et.imageKey, icon.dimg);
                                }
                                catch (Exception err)
                                {
                                }
                            }
                            et.icon = icon;
                            ret[et.name] = et;
                        }
                        else
                        {
                            // this line is comment
                        }
                    }
                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message + "\n" + path);
            }
            return ret;
        }

        private void refreshFile(String subfile)
        {
            Hashtable lines = (Hashtable)MapEventFile[subfile];

            listView1.Items.Clear();
            foreach (EventTemplate et in lines.Values)
            {
                String[] newcc;

                if (et.columns.Length > 2)
                {
                    newcc = new String[et.columns.Length - 2]; 
                    Array.Copy(et.columns, 2, newcc, 0, newcc.Length);
                }
                else
                {
                    newcc = new String[0];
                }
                
                ListViewItem item = new ListViewItem(new String[]{
					et.name, Util.toArray1D(ref newcc)
				});
                item.Tag = et;
                item.ImageKey = et.imageKey;
                listView1.Items.Add(item);
            }
            listView1.Refresh();
        }

        public EventTemplate getEventTemplate(String file, String name)
        {
            Hashtable events = (Hashtable)MapEventFile[file];
            if (events != null)
            {
                return (EventTemplate)events[name];
            }
            return null;
        }

        public EventTemplate getCurrentEventTemplate()
        {
            if (listView1.SelectedItems.Count > 0) {
                return (EventTemplate)listView1.SelectedItems[0].Tag;
            }
            return null;
        }

        public ImageList getShareImageList()
        {
            return imageList1;
        }

        public ArrayList getAllEventTemplates()
        {
            ArrayList ret = new ArrayList();
            foreach (Hashtable fht in MapEventFile.Values)
            {
                foreach (EventTemplate et in fht.Values)
                {
                    ret.Add(et);
                }
            }
            return ret;
        }

        public EventNode toEventNode(EventTemplate et)
        {
            if (et != null) {
                EventNode en = new EventNode();
                en.name = et.filename + "-" + et.name;
                en.value = Util.toStringArray1D(ref et.columns);
                en.iconKey = et.imageKey;
                return en;
            }
            return null;
        }

//       ------------------------------------------------------------------------------
 
        public string getClassName()
        {
            return GetType().ToString();
        }

        public Form asForm()
        {
            return this;
        }

        public EventNode getEvent(string name)
        {
            string[] sp = name.Split(new char[]{'-'});
            if (sp.Length >= 2) {
                return toEventNode(getEventTemplate(sp[0], sp[1]));
            }
            return null;
        }

        public EventNode getSelectedEvent()
        {
            return toEventNode(getCurrentEventTemplate());
        }

        public void saveWorldEvents(World events)
        {

        }

        public ImageList getImageList()
        {
            return getShareImageList();
        }
    }

    public class EventTemplate
    {
        public String filename;
        public String name;
        public String imageKey;
        public javax.microedition.lcdui.Image icon;

        public String[] columns;

        public EventTemplate(String filename, String[] columns)
        {
            for (int i = 0; i < columns.Length; i++ )
            {
                columns[i] = columns[i].Trim();
            }
            this.filename = filename;
            this.columns = columns;
            this.name = columns[0];
            if (columns.Length > 1) {
                this.imageKey = columns[1]; 
            } else {
                this.imageKey = "";
            }
        }
    }

}

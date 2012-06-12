using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using CellGameEdit.PM.plugin;

namespace KingIIEventTemplate.PM.KingII
{
    public partial class KingIIEventTemplate : Form, EventTemplatePlugin
    {
        public KingIIEventTemplate()
        {
            InitializeComponent();
        }

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
            return null;
        }

        public EventNode getSelectedEvent()
        {
            return null;
        }

        public void saveWorldEvents(List<WorldEvent> events)
        {

        }

        public ImageList getImageList()
        {
            return null;
        }
    }
}

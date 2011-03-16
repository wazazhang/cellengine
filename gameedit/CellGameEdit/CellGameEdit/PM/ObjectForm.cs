using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Runtime.Serialization;
using System.Security.Permissions;
using System.Runtime.Serialization.Formatters;
using System.IO;

namespace CellGameEdit.PM
{
    [Serializable]
    public partial class ObjectForm : Form, ISerializable , IEditForm
    {
        public string id;

        public ObjectForm(String name)
        {
            id = name; this.Text = id;
            InitializeComponent();
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected ObjectForm(SerializationInfo info, StreamingContext context)
        {
            id = (String)info.GetValue("id", typeof(String));
            this.Text = id;
            InitializeComponent();
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            info.AddValue("id", id);
        }

        public String getID()
        {
            return id;
        }

        public Form getForm()
        {
            return this;
        }

        private void ObjectForm_Load(object sender, EventArgs e)
        {
        }

        private void ObjectForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.Hide();
            }
        }

        public void Render(javax.microedition.lcdui.Graphics g,int x,int y)
        {
           
        }
    }
}
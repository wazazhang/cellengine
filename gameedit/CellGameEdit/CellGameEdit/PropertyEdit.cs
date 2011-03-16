using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit
{
    public partial class PropertyEdit : Form
    {
        public PropertyEdit(object obj)
        {
            InitializeComponent();
            this.propertyGrid1.SelectedObject = obj;
        }
    }
}
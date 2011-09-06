using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit.PM
{
    public partial class ImageFlipToolStripButton : ToolStripDropDownButton
    {
        public ImageFlipToolStripButton()
        {
            InitializeComponent();

            toolStripMenuItem10.Checked = true;
        }

        public int getFlipIndex()
        {
            //
            if (toolStripMenuItem10.Checked) return 0;
            if (toolStripMenuItem11.Checked) return 1;
            if (toolStripMenuItem12.Checked) return 2;
            if (toolStripMenuItem13.Checked) return 3;
            if (toolStripMenuItem14.Checked) return 4;
            if (toolStripMenuItem15.Checked) return 5;
            if (toolStripMenuItem16.Checked) return 6;
            if (toolStripMenuItem17.Checked) return 7;

            return -1;
        }

        public void setFlipIndex(int flip)
        {
            switch (flip)
            {
                case 0: select(toolStripMenuItem10); break;
                case 1: select(toolStripMenuItem11); break;
                case 2: select(toolStripMenuItem12); break;
                case 3: select(toolStripMenuItem13); break;
                case 4: select(toolStripMenuItem14); break;
                case 5: select(toolStripMenuItem15); break;
                case 6: select(toolStripMenuItem16); break;
                case 7: select(toolStripMenuItem17); break;
            }
        }

        public int prewFlipIndex()
        {
            if (toolStripMenuItem10.Checked)
                select(toolStripMenuItem17);
            else if (toolStripMenuItem11.Checked)
                select(toolStripMenuItem10);
            else if (toolStripMenuItem12.Checked)
                select(toolStripMenuItem11);
            else if (toolStripMenuItem13.Checked)
                select(toolStripMenuItem12);
            else if (toolStripMenuItem14.Checked)
                select(toolStripMenuItem13);
            else if (toolStripMenuItem15.Checked)
                select(toolStripMenuItem14);
            else if (toolStripMenuItem16.Checked)
                select(toolStripMenuItem15);
            else if (toolStripMenuItem17.Checked)
                select(toolStripMenuItem16);
            return getFlipIndex();
        }

        public int nextFlipIndex()
        {
            if (toolStripMenuItem10.Checked)
                select(toolStripMenuItem11);
            else if (toolStripMenuItem11.Checked)
                select(toolStripMenuItem12);
            else if (toolStripMenuItem12.Checked)
                select(toolStripMenuItem13);
            else if (toolStripMenuItem13.Checked)
                select(toolStripMenuItem14);
            else if (toolStripMenuItem14.Checked)
                select(toolStripMenuItem15);
            else if (toolStripMenuItem15.Checked)
                select(toolStripMenuItem16);
            else if (toolStripMenuItem16.Checked)
                select(toolStripMenuItem17);
            else if (toolStripMenuItem17.Checked)
                select(toolStripMenuItem10);
            return getFlipIndex();
        }

        protected override void OnPaint(PaintEventArgs pe)
        {
            base.OnPaint(pe);
        }

        private void ImageFlipToolStripButton_DropDownItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {

            select((ToolStripMenuItem)e.ClickedItem);
                
        }

        private void select(ToolStripMenuItem item)
        {
            try
            {
                if (!item.Equals(toolStripMenuItem10))
                    toolStripMenuItem10.Checked = false;

                if (!item.Equals(toolStripMenuItem11))
                    toolStripMenuItem11.Checked = false;

                if (!item.Equals(toolStripMenuItem12))
                    toolStripMenuItem12.Checked = false;

                if (!item.Equals(toolStripMenuItem13))
                    toolStripMenuItem13.Checked = false;

                if (!item.Equals(toolStripMenuItem14))
                    toolStripMenuItem14.Checked = false;

                if (!item.Equals(toolStripMenuItem15))
                    toolStripMenuItem15.Checked = false;

                if (!item.Equals(toolStripMenuItem16))
                    toolStripMenuItem16.Checked = false;

                if (!item.Equals(toolStripMenuItem17))
                    toolStripMenuItem17.Checked = false;

                this.Image = item.Image;

                item.Checked = true;
            }
            catch (Exception err)
            {
            }
        }
    }
}

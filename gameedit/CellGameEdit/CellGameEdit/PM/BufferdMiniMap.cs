using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit.PM
{
    public partial class BufferdMiniMap : Form
    {

        MapForm CurMap;

        public BufferdMiniMap(MapForm map)
        {
            InitializeComponent();
            
            CurMap = map;
            CurMap.MiniView = this;
        }
        private void BufferdMiniMap_FormClosed(object sender, FormClosedEventArgs e)
        {
            CurMap.MiniView = null;
        }


        private void BufferdMiniMap_Load(object sender, EventArgs e)
        {
            pictureBox1.Image = new Bitmap(CurMap.XCount, CurMap.YCount);

            Graphics g = Graphics.FromImage(pictureBox1.Image);
			CurMap.renderToMiniMap(g);
			/*
            for (int x = 0; x < CurMap.XCount; x++)
            {
                for (int y = 0; y < CurMap.YCount; y++)
                {
                    javax.microedition.lcdui.Image img = CurMap.getTileImage(x, y);

                    if (img != null)
                    {
                        g.FillRectangle(img.getColorKeyBrush(), x, y, 1, 1);
                    }
                }
            }*/

            pictureBox1.Refresh();
            this.TopMost = true;
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            this.TopMost = toolStripButton1.Checked;
        }



        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            try
            {
                int sx1 = 0;
                int sx2 = CurMap.getMapSize().Width;
                int sw = Math.Abs(sx2 - sx1);

                int sy1 = 0;
                int sy2 = CurMap.getMapSize().Height;
                int sh = Math.Abs(sy2 - sy1);

                int cx = CurMap.getMapViewRectangle().X;
                int cy = CurMap.getMapViewRectangle().Y;
                int cw = CurMap.getMapViewRectangle().Width;
                int ch = CurMap.getMapViewRectangle().Height;

                float rw = pictureBox1.Width / (float)sw;
                float rh = pictureBox1.Height / (float)sh;

                Rectangle rect = new Rectangle(
                    (int)(cx * rw),
                    (int)(cy * rh),
                    (int)(cw * rw),
                    (int)(ch * rh)
                    );

                e.Graphics.DrawRectangle(Pens.White, rect);

                //Console.WriteLine(rect.ToString());
            }
            catch (Exception err)
            {
                //Console.WriteLine(err.StackTrace);
            }
        }

        private void pictureBox1_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                locateMap(e.X, e.Y);
            }
        }

        private void pictureBox1_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            { 
                locateMap(e.X, e.Y); 
            }
           
        }

        public void locateMap(int x, int y)
        {
            try
            {
                int sx1 = 0;
                int sx2 = CurMap.getMapSize().Width;
                int sw = Math.Abs(sx2 - sx1);

                int sy1 = 0;
                int sy2 = CurMap.getMapSize().Height;
                int sh = Math.Abs(sy2 - sy1);

                float rw = (float)sw / pictureBox1.Width;
                float rh = (float)sh / pictureBox1.Height;

                int cw = CurMap.getMapViewRectangle().Width;
                int ch = CurMap.getMapViewRectangle().Height;
                int cx = (int)(sx1 + (x * rw) - cw / 2);
                int cy = (int)(sy1 + (y * rh) - ch / 2);

                cx = Math.Max(cx, sx1);
                cx = Math.Min(cx, sx2);
                cy = Math.Max(cy, sy1);
                cy = Math.Min(cy, sy2);

                //CurMap.getViewPanel().HorizontalScroll.Value = cx;
               // CurMap.getViewPanel().VerticalScroll.Value = cy;

                CurMap.setMapViewLoc(cx, cy);

                //CurMap.getViewPanel().Refresh();
                pictureBox1.Refresh();
            }
            catch (Exception err)
            {
            }
          
        }
       

        public void rebuff(int blockx, int blocky, javax.microedition.lcdui.Image img)
        {
            Graphics g = Graphics.FromImage(pictureBox1.Image);

            if (img != null)
            {
                g.FillRectangle(img.getColorKeyBrush(), blockx, blocky, 1, 1);
            }

            pictureBox1.Refresh();

            //Console.WriteLine("rebuff");
        }


        public void relocation(MapForm map)
        {
            pictureBox1.Refresh();
        }

  

    

    }
}
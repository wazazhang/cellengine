using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace CellGameEdit.PM
{
    public partial class MapMini : Form
    {
        Image map;
        float scale = 1.0f;
        float x = 0;
        float y = 0;

        public MapMini(Image image)
        {
            InitializeComponent();
            map = image;

            pictureBox1.Width = map.Width;
            pictureBox1.Height = map.Height;
            pictureBox1.Image = map;

            
        }
        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            
            e.Graphics.DrawString(
                (scale * 100) + "%", 
                this.Font, 
                System.Drawing.Brushes.Black, 
                -pictureBox1.Location.X+1, 
                -pictureBox1.Location.Y+1
                );

        }


        private void MapMini_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Escape || e.KeyCode == Keys.Enter)
            {
                this.Close();
            }

            if (e.KeyCode == Keys.Add )
            {
                scale += 0.1f;
                if (scale > 1) scale = 1;

                pictureBox1.Width = (int)(map.Width * scale);
                pictureBox1.Height = (int)(map.Height * scale);
                //int x = 0;
                //int y = 0;
                //if (MapRegion.Width < panel1.Width) x = (panel1.Width - MapRegion.Width) / 2;
                //if (MapRegion.Height < panel1.Height) y = (panel1.Height - MapRegion.Height) / 2;
                //MapRegion.Location = new Point(x, y);
                pictureBox1.Refresh();
            }
            if(e.KeyCode == Keys.Subtract)
            {
                scale -= 0.1f;
                if (scale < 0.1) scale = 0.1f;

                pictureBox1.Width = (int)(map.Width * scale);
                pictureBox1.Height = (int)(map.Height * scale);
                //int x = 0;
                //int y = 0;
                //if (MapRegion.Width < panel1.Width) x = (panel1.Width - MapRegion.Width) / 2;
                //if (MapRegion.Height < panel1.Height) y = (panel1.Height - MapRegion.Height) / 2;
                //MapRegion.Location = new Point(x, y);
                pictureBox1.Refresh();
            }
            
        }


       
    }
}
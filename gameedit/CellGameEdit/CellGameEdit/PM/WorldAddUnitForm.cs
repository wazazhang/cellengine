using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Text;
using System.Windows.Forms;

using javax.microedition.lcdui;

namespace CellGameEdit.PM
{
    public partial class WorldAddUnitForm : Form
    {
        private ImagesForm currentImages;
        System.Drawing.Rectangle currentImageRect;
        System.Drawing.Rectangle currentImageIndex;

        public WorldAddUnitForm()
        {
            InitializeComponent();
        }

        private void WorldAddUnitForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.Hide();
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////////
        // src tile

        private void srcRender(Graphics g, int index, int flip, int x, int y, Boolean showimageborder)
        {
            Image img = srcGetImage(index);
            if (img != null && !img.killed && flip < Graphics.FlipTable.Length)
            {
                g.drawImageScale(img, x, y, Graphics.FlipTable[flip], srcScale);

                if (showimageborder)
                {
                    g.setColor(0x80, 0xff, 0xff, 0xff);
                    g.drawRect(x, y, img.getWidth(), img.getHeight());
                }
            }
        }
        private void srcRenderAll(Graphics g, int x, int y, System.Drawing.Rectangle screen, Boolean showimageborder)
        {
            for (int i = currentImages.getDstImageCount() - 1; i >= 0; i--)
            {
                Image img = currentImages.getDstImage(i);
                if (img != null &&
                    screen.IntersectsWith(new System.Drawing.Rectangle(
                        x + img.x,
                        y + img.y,
                        img.getWidth(),
                        img.getHeight())
                    ))
                {
                    srcRender(g, i, 0,
                        x + img.x,
                        y + img.y, 
                        showimageborder);
                }
            }

        }

        private void srcBox_MouseDown(object sender, MouseEventArgs e)
        {
            if (currentImages != null)
            {
                if (e.Button == MouseButtons.Left)
                {
                    System.Drawing.Rectangle dst = new System.Drawing.Rectangle(0, 0, 1, 1);
                    for (int i = 0; i < currentImages.getDstImageCount(); i++)
                    {
                        Image srcImage = currentImages.getDstImage(i);
                        if (srcImage != null && srcImage.killed == false)
                        {
                            dst.X = srcImage.x;
                            dst.Y = srcImage.y;
                            dst.Width = srcImage.getWidth();
                            dst.Height = srcImage.getHeight();

                            if (dst.Contains(e.X, e.Y))
                            {
                                currentImageRect = dst;
                                currentImageIndex = i;
                                pictureBox1.Refresh();
                                break;
                            }
                        }
                    }
                }
            }
        }

        private void srcBox_Paint(object sender, PaintEventArgs e)
        {
            if (currentImages!=null)
            {
                for (int i = currentImages.getDstImageCount() - 1; i >= 0; i--)
                {
                    Image img = currentImages.getDstImage(i);
                    if (img != null)
                    {
                        pictureBox1.Width = Math.Max(
                            pictureBox1.Width,
                            (img.x + img.getWidth())
                            );
                        pictureBox1.Height = Math.Max(
                            pictureBox1.Height,
                            (img.y + img.getHeight())
                            );
                        //break;
                    }
                }

                Graphics g = new Graphics(e.Graphics);

                System.Drawing.Rectangle srect = new System.Drawing.Rectangle(
                        -pictureBox1.Location.X,
                        -pictureBox1.Location.Y,
                        (int)(panel1.Width),
                        (int)(panel1.Height)
                    );

                srcRenderAll(g, 0, 0, srect, true);

                System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xFF, 0xFF, 0xFF, 0xFF));
                System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff)).Brush;

                e.Graphics.FillRectangle(brush, currentImageRect);
                e.Graphics.DrawRectangle(pen, currentImageRect);
            }
            else
            {

            }
        }

      
    }
}

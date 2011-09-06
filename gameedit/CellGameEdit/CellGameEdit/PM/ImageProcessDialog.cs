using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;



namespace CellGameEdit.PM
{
    public partial class ImageProcessDialog : Form
    {
        public ImageProcessDialog(ImagesForm src, ArrayList dstImages)
        {
            InitializeComponent();
        
       

           
        
        }

        private void ImageProcessDialog_Load(object sender, EventArgs e)
        {

        }

        public void processImage()
        {
            /*
            for (int i = 0; i < dstImages.Count; i++)
            {
                try
                {
                    Image img = getDstImage(i);
                    if (img.selected)
                    {
                        //img.setTransparentColor(color);
                    }
                }
                catch (Exception err)
                {
                    Console.WriteLine(this.id + " : " + i + " : " + err.StackTrace + "  at  " + err.Message);
                }
            }
            */

        }

        private void ImageProcessDialog_FormClosing(object sender, FormClosingEventArgs e)
        {
            Console.WriteLine(" : ");

            
        }

        private void buttonOK_Click(object sender, EventArgs e)
        {

            this.Close();
        }

    }
}

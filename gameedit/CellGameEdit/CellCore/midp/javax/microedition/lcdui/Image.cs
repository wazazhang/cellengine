using System;
namespace javax.microedition.lcdui
{

public class Image
{
	
	public System.Drawing.Image _dimg;
	public System.Drawing.Image dimg
	{
		get { return _dimg; }
	}
	private bool mutable = true;

	public bool selected = false;
	public int x = 0;
	public int y = 0;

	public bool used = false;
	public int indexOfImages;
	public bool killed = false;

	private bool hasColorKey = false;
	private System.Drawing.Color ColorKey;

	System.Drawing.Brush brush = null;


	public Image(System.Drawing.Image DImage)
	{
		_dimg = DImage;
		mutable = false;
		asBitmap();
	}


	public static javax.microedition.lcdui.Image createImage(byte[] imageData, int imageOffset, int imageLength)
	{
		
		byte[] data = new byte[imageLength];

		for (int i = 0; i < imageLength; i++)
		{
			data[i] = (byte)imageData[imageOffset+i];
		}


		System.IO.MemoryStream ms = new System.IO.MemoryStream(data);

		System.Drawing.Image image = System.Drawing.Image.FromStream(ms);
		
		Image ret = new Image(image);

		ret.mutable = false;
		return ret;
		
	}

	public static javax.microedition.lcdui.Image createImage(int width, int height)
	{
        System.Drawing.Image image = new System.Drawing.Bitmap(width, height,
            System.Drawing.Imaging.PixelFormat.Format32bppArgb);
		Image ret = new Image(image);
		return ret;
	}

	public static javax.microedition.lcdui.Image createImage(string name) 
	{
		System.Drawing.Image image = System.Drawing.Image.FromFile(name);

		Image ret = new Image(image);
		ret.mutable = false;
		return ret;
	}

	public static javax.microedition.lcdui.Image createRGBImage(int[] rgb, int width, int height, bool processAlpha)
	{
		System.Drawing.Image image = new System.Drawing.Bitmap(width, height);

		Image ret = new Image(image);

		return ret;
	}

	public static javax.microedition.lcdui.Image createImage(javax.microedition.lcdui.Image image, 
		int x, int y, int width, int height)
	{

		Image ret = Image.createImage(width, height);

		ret.getGraphics().drawImageRegion(image, 0, 0, x, y, width, height, 0);


		return ret;

	}


    //-----------------------------------------------------------------------------------------------------------------------------------
	public void changeDimg(System.Drawing.Image image)
	{
		_dimg = image;
		asBitmap();
	}

	public Image subImage(int x, int y, int w, int h)
	{
		if (x >= 0 && (x + w) <= getWidth() && y >= 0 && (y + h) <= getHeight())
		{
			Image buff = createImage(w, h);
			Graphics bg = buff.getGraphics();
			bg.drawImage(this, -x, -y);
			return buff;
		}
		return null;
	}

    public void swapColor(int src_argb, int dstcolor)
    {
        System.Drawing.Bitmap image = asBitmap();
        System.Drawing.Color zeroc = System.Drawing.Color.FromArgb(dstcolor);

        for (int x = image.Width - 1; x >= 0; --x )
        {
            for (int y= image.Height - 1; y >= 0; --y)
            {
                System.Drawing.Color c = image.GetPixel(x, y);

                if (c.ToArgb() == src_argb) 
                {
                    image.SetPixel(x, y, zeroc);
                }
            }
        }

		_dimg = image;
    }

    public void flipSelf(int transform)
    {
		int width = getWidth();
		int height = getHeight();

		switch (Graphics.FlipTable[transform])
		{
			case System.Drawing.RotateFlipType.Rotate90FlipNone://1
			case System.Drawing.RotateFlipType.Rotate270FlipNone://3
			case System.Drawing.RotateFlipType.Rotate90FlipX://5
			case System.Drawing.RotateFlipType.Rotate270FlipX://7
				width = getHeight();
				height = getWidth();
				break;
		}

		Image dst = createImage(width, height);
        Graphics g = dst.getGraphics();
        g.drawImageTrans(this, 0, 0, transform);
		_dimg = dst.dimg;
    }

    public System.Drawing.Rectangle cutTransparentImageSize(int broadPixel)
	{
		System.Drawing.Bitmap image = asBitmap();

		System.Drawing.Rectangle ret = new System.Drawing.Rectangle();

		int left = 0;
		int right = image.Width - 1;
		int top = 0;
		int bottom = image.Height - 1;
		int x = 0;
		int y = 0;
		System.Drawing.Color c;

        #region findTBLR
        bool finded = false;
		// find left
		for (x = 0; x < image.Width; x++)
		{
			for (y = image.Height - 1; y >= 0; --y)
			{
				c = image.GetPixel(x, y);
				if (c.A != 0)
				{
					left = x;
					finded = true;
					break;
				}
			}
			if (finded)
			{
				break;
			}
		}
		
		finded = false;
		// right
		for (x = image.Width - 1; x >= 0; --x)
		{
			for (y = image.Height - 1; y >= 0; --y)
			{
				c = image.GetPixel(x, y);
				if (c.A != 0)
				{
					right = x;
					finded = true;
					break;
				}
			}
			if (finded)
			{
				break;
			}
		} 
		
		finded = false;
		// top
		for (y = 0; y < image.Height; y++)
		{
			for (x = image.Width - 1; x >= 0; --x)
			{
				c = image.GetPixel(x, y);
				if (c.A != 0)
				{
					top = y;
					finded = true;
					break;
				}
			}
			if (finded)
			{
				break;
			}
		} 
		
		finded = false;
		// bottom
		for (y = image.Height - 1; y >= 0; --y)
		{
			for (x = image.Width - 1; x >= 0; --x)
			{
				c = image.GetPixel(x, y);
				if (c.A != 0)
				{
					bottom = y;
					finded = true;
					break;
				}
			}
			if (finded)
			{
				break;
			}
		}
        #endregion

        left    = Math.Max(0, left - broadPixel);
        right   = Math.Min(image.Width-1, right + broadPixel);
        top     = Math.Max(0, top - broadPixel);
        bottom  = Math.Min(image.Height - 1, bottom + broadPixel);

		ret.X = left;
		ret.Y = top;
		ret.Width = right - left + 1;
		ret.Height = bottom - top + 1;
		Image dst = createImage(ret.Width, ret.Height);
		Graphics dstg = dst.getGraphics();
		dstg.drawImage(this, -left, -top);
		_dimg = dst.dimg;
		dst = null;

		return ret;
	}


    public System.Drawing.Color getPixel(int x, int y)
    {
        System.Drawing.Bitmap image = asBitmap();

        return image.GetPixel(x, y);
    }

    public System.Drawing.Bitmap asBitmap()
    {
        System.Drawing.Bitmap image;

        if (!(dimg is System.Drawing.Bitmap) || dimg.Palette != null)
        {
            image = new System.Drawing.Bitmap(dimg.Width, dimg.Height, System.Drawing.Imaging.PixelFormat.Format32bppArgb);

            System.Drawing.Graphics g = System.Drawing.Graphics.FromImage(image);

            g.DrawImage(
                dimg,
                new System.Drawing.Rectangle(0, 0, dimg.Width, dimg.Height),
                new System.Drawing.Rectangle(0, 0, dimg.Width, dimg.Height),
                System.Drawing.GraphicsUnit.Pixel);

			_dimg = image;
        }
        else
        {
            image = (System.Drawing.Bitmap)dimg;
        }

        return image;
    }
	//-----------------------------------------------------------------------------------------------------------------------------------

	public Graphics getGraphics()
	{
		Graphics g = new Graphics(System.Drawing.Graphics.FromImage(dimg));

		return g;
	}

	

	public int getHeight()
	{
		return dimg.Height;
	}
	public int getWidth()
	{
		return dimg.Width;
	}

	public System.Drawing.Image getDImage()
	{
		return dimg;
	}

	public System.Drawing.Color getColorKey()
	{
		if (!hasColorKey)
		{
			hasColorKey = true;
			try
			{
				System.Drawing.Bitmap bm = new System.Drawing.Bitmap(1, 1);
				System.Drawing.Graphics g = System.Drawing.Graphics.FromImage(bm);

				g.DrawImage(dimg,
					new System.Drawing.Rectangle(0, 0, 1, 1),
					new System.Drawing.Rectangle(dimg.Width / 2, dimg.Height / 2, 1, 1),
					System.Drawing.GraphicsUnit.Pixel
					);

				ColorKey = bm.GetPixel(0, 0);

				g = null;
				bm = null;
			}
			catch (Exception err) { }
		}

		return ColorKey;
	}


	public System.Drawing.Brush getColorKeyBrush() 
	{
		if (brush == null)
		{
			brush = (new System.Drawing.Pen(getColorKey())).Brush;
		}
		return brush;
	}
}

}
using System;
namespace javax.microedition.lcdui
{

public class Image
{

	public System.Drawing.Image dimg;
	private bool mutable = true;

	public bool selected = false;
	public int x = 0;
	public int y = 0;

	public bool used = false;

	public bool killed = false;

	private bool hasColorKey = false;
	private System.Drawing.Color ColorKey;

	System.Drawing.Brush brush = null;


	public Image(System.Drawing.Image DImage)
	{
		dimg = DImage;
		mutable = false;

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
		System.Drawing.Image image = new System.Drawing.Bitmap(width, height);
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

	public static javax.microedition.lcdui.Image createImage(javax.microedition.lcdui.Image image, int x, int y, int width, int height, int transform)
	{

		Image ret = Image.createImage(width, height);

		ret.getGraphics().drawRegion(image, x, y, width, height, transform, 0, 0,0);


		return ret;

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

	public bool isMutable()
	{
		return true;
	}

	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height)
	{
	
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
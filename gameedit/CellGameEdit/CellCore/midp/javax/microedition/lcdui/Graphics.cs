namespace javax.microedition.lcdui{

/**
 * Graphics 的摘要说明。
 */
public class Graphics
{
	public static System.Drawing.Font font = new System.Drawing.Font(System.Drawing.FontFamily.GenericMonospace, 8);


	public System.Drawing.Graphics dg;

	public System.Drawing.Pen pen = System.Drawing.Pens.Black;
    public System.Drawing.Brush brush = System.Drawing.Brushes.Black;
    public System.Drawing.Color color = System.Drawing.Color.Black;

	const  byte  TRANS_NONE 	 = 0;
	const  byte TRANS_90 		 = 1;
	const  byte TRANS_180 		 = 2; 
	const  byte TRANS_270 		 = 3;
	const  byte TRANS_H 		 = 4;
	const  byte TRANS_H90 		 = 5;
	const  byte TRANS_H180 		 = 6;
    const byte TRANS_H270       = 7;


	//    public static Graphics g;
	//public static int BASELINE	;
	//public static int BOTTOM	;
	//public static int DOTTED	;
	//public static int HCENTER	;
	public static int LEFT		;
	//public static int RIGHT		;
	//public static int SOLID		;
	public static int TOP		;
	//public static int VCENTER	;


	public Graphics(System.Drawing.Graphics sdg)
	{
		dg = sdg;
		dg.CompositingMode = (System.Drawing.Drawing2D.CompositingMode.SourceOver);
		dg.CompositingQuality=( System.Drawing.Drawing2D.CompositingQuality.HighSpeed);
		dg.SmoothingMode=(System.Drawing.Drawing2D.SmoothingMode.None);
		dg.InterpolationMode=( System.Drawing.Drawing2D.InterpolationMode.NearestNeighbor);
		dg.PixelOffsetMode=(System.Drawing.Drawing2D.PixelOffsetMode.Half);
	}


	public void drawImage(javax.microedition.lcdui.Image img, float x, float y, int anchor)
	{
		if (img.killed) return;
		dg.DrawImage(img.dimg,
			new System.Drawing.RectangleF(x , y , img.getWidth() , img.getHeight() ),
			new System.Drawing.RectangleF(0, 0, img.getWidth() , img.getHeight() ),
			System.Drawing.GraphicsUnit.Pixel
			);
	}
	

	public void drawImage(
		javax.microedition.lcdui.Image img,
		float x, float y,
		System.Drawing.RotateFlipType transform,
		int anchor,
		float scale)
	{
		if (img.killed) return;
		img.dimg.RotateFlip(transform);

		if (scale == 1) 
		{
			dg.DrawImage(img.dimg,
					new System.Drawing.RectangleF(x , y , img.getWidth() , img.getHeight() ),
					new System.Drawing.RectangleF(0, 0, img.getWidth(), img.getHeight()),
					System.Drawing.GraphicsUnit.Pixel
					);
		}
		else
		{
			//dg.DrawImage(img.dimg,
			//        new System.Drawing.RectangleF(x + scale / 2, y, img.getWidth() * scale, img.getHeight() * scale),
			//        new System.Drawing.RectangleF(0, 0, img.getWidth(), 1),
			//        System.Drawing.GraphicsUnit.Pixel
			//        );
			//dg.DrawImage(img.dimg,
			//        new System.Drawing.RectangleF(x, y + scale / 2, img.getWidth() * scale, img.getHeight() * scale),
			//        new System.Drawing.RectangleF(0, 0, 1, img.getHeight()),
			//        System.Drawing.GraphicsUnit.Pixel
			//        );
			//dg.DrawImage(img.dimg,
			//        new System.Drawing.RectangleF(x + scale / 2, y + scale / 2, img.getWidth() * scale, img.getHeight() * scale),
			//        new System.Drawing.RectangleF(0, 0, img.getWidth(), img.getHeight()),
			//        System.Drawing.GraphicsUnit.Display
			//        );


			dg.DrawImage(img.dimg,
					new System.Drawing.RectangleF(x , y , img.getWidth() * scale, img.getHeight() * scale),
					new System.Drawing.RectangleF(0, 0, img.getWidth(), img.getHeight()),
					System.Drawing.GraphicsUnit.Pixel
					);
		}

		switch (transform)
		{
			case System.Drawing.RotateFlipType.RotateNoneFlipX://4
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipX);
				break;
			case System.Drawing.RotateFlipType.RotateNoneFlipY://6
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipY);
				break;
			//case TRANS_180:
			case System.Drawing.RotateFlipType.RotateNoneFlipXY://2
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipXY);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipNone://1
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipNone://3
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipX://5
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipX);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipX://7
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipX);
				break;
		}
	}

	public void drawImage(
		javax.microedition.lcdui.Image img,
		float x, float y, 
		System.Drawing.RotateFlipType transform,
		int anchor)
	{
		if (img.killed) return;
		img.dimg.RotateFlip(transform);


		dg.DrawImage(img.dimg,
			new System.Drawing.RectangleF(x , y , img.getWidth() , img.getHeight() ),
			new System.Drawing.RectangleF(0, 0, img.getWidth() , img.getHeight() ),
			System.Drawing.GraphicsUnit.Pixel
			);

		switch (transform)
		{
			case System.Drawing.RotateFlipType.RotateNoneFlipX://4
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipX);
				break;
			case System.Drawing.RotateFlipType.RotateNoneFlipY://6
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipY);
				break;
			//case TRANS_180:
			case System.Drawing.RotateFlipType.RotateNoneFlipXY://2
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipXY);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipNone://1
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipNone://3
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipX://5
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipX);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipX://7
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipX);
				break;
		}
		//img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipNone);

	}


	public void drawImage(
		javax.microedition.lcdui.Image img,
		float x, float y, float sx, float sy, float sw, float sh,
		System.Drawing.RotateFlipType transform,
		int anchor)
	{
		if (img.killed) return;
		img.dimg.RotateFlip(transform);


		dg.DrawImage(img.dimg,
			new System.Drawing.RectangleF(x, y, sw, sh),
			new System.Drawing.RectangleF(sx, sy, sw, sh),
			System.Drawing.GraphicsUnit.Pixel
			);

		switch (transform)
		{
			case System.Drawing.RotateFlipType.RotateNoneFlipX://4
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipX);
				break;
			case System.Drawing.RotateFlipType.RotateNoneFlipY://6
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipY);
				break;
			//case TRANS_180:
			case System.Drawing.RotateFlipType.RotateNoneFlipXY://2
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipXY);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipNone://1
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipNone://3
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipX://5
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipX);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipX://7
				img.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipX);
				break;
		}
		//img.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipNone);

	}


	public void drawImageTrans(javax.microedition.lcdui.Image src, float x, float y, int transform)
    {
        drawRegion(src, 0, 0, src.getWidth(), src.getHeight(), transform, x, y, 0);
    }

	public void drawRegion(javax.microedition.lcdui.Image src, 
		float x_src, float y_src, float width, float height,
		int transform,
		float x_dest, float y_dest, 
		int anchor)
	{
		if (src.killed) return;
		System.Drawing.RotateFlipType rt = System.Drawing.RotateFlipType.RotateNoneFlipNone;
        rt = FlipTable[transform];
        src.dimg.RotateFlip(rt);
		
		dg.DrawImage(src.dimg,
			new System.Drawing.RectangleF(0, 0, width , height ),
			new System.Drawing.RectangleF(x_src , y_src , width , height ),
			System.Drawing.GraphicsUnit.Pixel);
		switch (rt)
		{
			case System.Drawing.RotateFlipType.RotateNoneFlipX://4
				src.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipX);
				break;
			case System.Drawing.RotateFlipType.RotateNoneFlipY://6
				src.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipY);
				break;
			//case TRANS_180:
			case System.Drawing.RotateFlipType.RotateNoneFlipXY://2
				src.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipXY);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipNone://1
				src.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipNone://3
				src.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipNone);
				break;
			case System.Drawing.RotateFlipType.Rotate90FlipX://5
				src.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate90FlipX);
				break;
			case System.Drawing.RotateFlipType.Rotate270FlipX://7
				src.dimg.RotateFlip(System.Drawing.RotateFlipType.Rotate270FlipX);
				break;
		}
		//src.dimg.RotateFlip(System.Drawing.RotateFlipType.RotateNoneFlipNone);
		
		
	}

	public void drawString(string str, float x, float y, int anchor)
	{
		
		dg.DrawString(str, font, brush, x , y );
	}

	public void drawLine(float x1, float y1, float x2, float y2)
	{
		dg.DrawLine(pen, x1, y1, x2, y2);
	}

	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		dg.DrawArc(pen, x, y, width, height, startAngle, arcAngle);
	}

	public void drawRect(float x, float y, float width, float height)
	{
		dg.DrawRectangle(pen, x, y, width, height);
	}
	public void fillArc(float x, float y, float width, float height, float startAngle, float arcAngle)
	{
		dg.FillPie(brush, x , y , width , height , startAngle, arcAngle);
	}
	public void fillRect(float x, float y, float width, float height)
	{
		dg.FillRectangle(brush, x , y , width , height );
	}
	// float


	public void drawArc(float x, float y, float width, float height, float startAngle, float arcAngle)
	{
		dg.DrawArc(pen, x, y, width, height, startAngle, arcAngle);
	}

	
	
	public int getClipHeight()
	{
		return (int)dg.ClipBounds.Height;
	}
	public int getClipWidth()
	{
		return (int)dg.ClipBounds.Width;
	}
	public int getClipX()
	{
		return (int)dg.ClipBounds.X;
	}
	public int getClipY()
	{
		return (int)dg.ClipBounds.Y;
	}

	public void setClip(float x, float y, float width, float height)
	{
		dg.SetClip(new System.Drawing.RectangleF(x , y , width , height ));
	}
	public void setColor(int RGB)
	{
		color = System.Drawing.Color.FromArgb(RGB);
		pen = new System.Drawing.Pen(color);
		brush = pen.Brush;
	}

	public void setColor(int red, int green, int blue)
	{
		color = System.Drawing.Color.FromArgb(red, green, blue);
		pen = new System.Drawing.Pen(color);
		brush = pen.Brush;
	}
	public void setColor(int alpha,int red, int green, int blue)
	{
		color = System.Drawing.Color.FromArgb(alpha,red, green, blue);
		pen = new System.Drawing.Pen(color);
		brush = pen.Brush;
	}
	public void setColor(int alpha, int rgb)
	{
		int r = (rgb & 0xff0000) >> 16;
		int g = (rgb & 0x00ff00) >> 8;
		int b = (rgb & 0x0000ff) >> 0;
		color = System.Drawing.Color.FromArgb(alpha, r, g, b);
		pen = new System.Drawing.Pen(color);
		brush = pen.Brush;
	}

	public void setDColor(System.Drawing.Color c)  {
		color = c;
		pen = new System.Drawing.Pen(color);
		brush = pen.Brush;
	}
	public void setDColor(int alpha, System.Drawing.Color c)
	{
		color = System.Drawing.Color.FromArgb(alpha, c);
		pen = new System.Drawing.Pen(color);
		brush = pen.Brush;
	}


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    static public System.Drawing.RotateFlipType[] FlipTable = new System.Drawing.RotateFlipType[]
        {
            System.Drawing.RotateFlipType.RotateNoneFlipNone,//
            System.Drawing.RotateFlipType.Rotate90FlipNone,//
            System.Drawing.RotateFlipType.Rotate180FlipNone,
            System.Drawing.RotateFlipType.Rotate270FlipNone,//

            System.Drawing.RotateFlipType.RotateNoneFlipX,
            System.Drawing.RotateFlipType.Rotate270FlipX,//
            System.Drawing.RotateFlipType.Rotate180FlipX,
            System.Drawing.RotateFlipType.Rotate90FlipX,//
        };
     
    static public string[] FlipTextTable = new string[]
        {
            "无",
            "90",
            "180",
            "270",
            "水平",
            "H 90",
            "H 180",
            "H 270",
        };

}
}
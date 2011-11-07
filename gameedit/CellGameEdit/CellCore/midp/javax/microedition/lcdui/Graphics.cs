using System.Drawing.Imaging;
using System.Drawing;
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

	private System.Collections.Stack stack_transform = new System.Collections.Stack();
	private System.Drawing.Imaging.ImageAttributes imgAttr = new System.Drawing.Imaging.ImageAttributes();
	private float imgAlpha = -1;

	public Graphics(System.Drawing.Graphics sdg)
	{
		dg = sdg;
		dg.CompositingMode = (System.Drawing.Drawing2D.CompositingMode.SourceOver);
		dg.CompositingQuality=( System.Drawing.Drawing2D.CompositingQuality.HighSpeed);
		dg.SmoothingMode=(System.Drawing.Drawing2D.SmoothingMode.None);
		dg.InterpolationMode=( System.Drawing.Drawing2D.InterpolationMode.NearestNeighbor);
		dg.PixelOffsetMode=(System.Drawing.Drawing2D.PixelOffsetMode.Half);
		dg.PageUnit = GraphicsUnit.Pixel;
		setAlpha(1);
	}
	//------------------------------------------------------------------------------------------------------------------
	
	public void setAlpha(float alpha)
	{
		if (imgAlpha != alpha)
		{
			this.imgAlpha = alpha;
			if (alpha == 1)
			{
				this.imgAttr = null;
			}
			else
			{
				// Initialize the color matrix.
				// Note the value 0.8 in row 4, column 4.
				float[][] matrixItems ={ 
				   new float[] {1, 0, 0, 0, 0},
				   new float[] {0, 1, 0, 0, 0},
				   new float[] {0, 0, 1, 0, 0},
				   new float[] {0, 0, 0, alpha, 0}, 
				   new float[] {0, 0, 0, 0, 1}};
				ColorMatrix colorMatrix = new ColorMatrix(matrixItems);

				// Create an ImageAttributes object and set its color matrix.
				ImageAttributes imageAtt = new ImageAttributes();
				imageAtt.SetColorMatrix(
				   colorMatrix,
				   ColorMatrixFlag.Default,
				   ColorAdjustType.Bitmap);

				this.imgAttr = imageAtt;
			}

		}
		
	}
	public void translate(float x, float y)
	{
		dg.TranslateTransform(x, y);
	}
	public void rotate(float angle)
	{
		dg.RotateTransform(angle);
	}

	public void scale(float x, float y)
	{
		dg.ScaleTransform(x, y);
	}

	public void pushTransform()
	{
		stack_transform.Push(dg.Transform);
	}

	public void popTransform()
	{
		System.Drawing.Drawing2D.Matrix matrix = (System.Drawing.Drawing2D.Matrix)stack_transform.Pop();
		dg.Transform = matrix;
	}

	//------------------------------------------------------------------------------------------------------------------

	private void _drawImage(System.Drawing.Image dimg,
		float dx, float dy, float dw, float dh,
		float sx, float sy, float sw, float sh)
	{

		// Create parallelogram for drawing original image.
		PointF ulCorner1 = new PointF(dx, dy);
		PointF urCorner1 = new PointF(dx + dw, dy);
		PointF llCorner1 = new PointF(dx, dy + dh);
		PointF[] destPara1 = {ulCorner1, urCorner1, llCorner1};

		// Create rectangle for source image.
		RectangleF srcRect = new RectangleF(sx, sy, sw, sh);

		dg.DrawImage(dimg, destPara1, srcRect, System.Drawing.GraphicsUnit.Pixel, imgAttr);

	}

	public void drawImage(javax.microedition.lcdui.Image img, float x, float y)
	{
		if (img.killed) return;
		_drawImage(
			img.dimg,
			x, y, img.getWidth(), img.getHeight(),
			0, 0, img.getWidth() , img.getHeight()
			);
	}
	


	public void drawImageScale(
		javax.microedition.lcdui.Image img,
		float x, float y,
		System.Drawing.RotateFlipType transform,
		float scale)
	{
		if (img.killed) return;
		img.dimg.RotateFlip(transform);

		if (scale == 1) 
		{
			_drawImage(img.dimg,
					x , y , img.getWidth() , img.getHeight() ,
					0, 0, img.getWidth(), img.getHeight()
					);
		}
		else
		{
			_drawImage(img.dimg,
					x , y , img.getWidth() * scale, img.getHeight() * scale,
					0, 0, img.getWidth(), img.getHeight());
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

	public void drawImageRegion(
		javax.microedition.lcdui.Image img,
		float x, float y, float sx, float sy, float sw, float sh,
		System.Drawing.RotateFlipType transform)
	{
		if (img.killed) return;
		img.dimg.RotateFlip(transform);


		_drawImage(img.dimg,
			x, y, sw, sh,
			sx, sy, sw, sh
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
		drawImageRegion(src, x, y, 0, 0, src.getWidth(), src.getHeight(), FlipTable[transform]);
    }
	/*
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

		_drawImage(src.dimg,
			0, 0, width , height ,
			x_src , y_src , width , height );
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
		
		
	}*/

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
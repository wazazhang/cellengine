package com.cell.gfx.gui;


import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class ImageBox extends Item 
{
//	public static UIRect CommonUserRect 		= new UIRect();
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xffffffff;
//	}
	final static public int ALIGN_CENTER 	= 0;
	final static public int ALIGN_LT		= 1;
	final static public int ALIGN_LB		= 2;
	final static public int ALIGN_RT		= 3;
	final static public int ALIGN_RB		= 4;
	final static public int ALIGN_LC		= 7;
	final static public int ALIGN_RC		= 8;
	final static public int ALIGN_CT		= 5;
	final static public int ALIGN_CB		= 6;
	
	public IImage BackImage;

	public byte Transform;
	
	public int Align = 0;
	
	public ImageBox() {
		this(null, 0);
	}
	
	public ImageBox(IImage image, int transform)
	{
		BackImage = image;
		//X = x;
		//Y = y;
		if(BackImage!=null){
			W = image.getWidth();
			H = image.getHeight();
		}
		Transform = (byte)transform;
		
		CanFocus = false;
	}
	
	public ImageBox(IImage image, int transform, int align)
	{
		BackImage = image;
		//X = x;
		//Y = y;
		if(BackImage!=null){
			W = image.getWidth();
			H = image.getHeight();
		}
		Transform = (byte)transform;
		
		Align = align;
		
		CanFocus = false;
	}
	
	public void resize(int width, int height) {
		W = width;
		H = height;
	}

	public void setImage(IImage img){
		BackImage = img;
		//if(img!=null)
		//	resize(img.getWidth(),img.getHeight());
	}
	
	public void clicked(Form sender,int key,int x,int y){
		if(Listener!=null){
			Listener.itemCommandAction(Click,this);
		}
	}
	public boolean update(Form form) {
		return true;
	}
	
	public void render(IGraphics g, int x, int y) {
		if(BackImage!=null)
		{
			int ox = (W-BackImage.getWidth())/2;
			int oy = (H-BackImage.getHeight())/2;
			
			switch(Align)
			{
			case ALIGN_LT:
				ox = 0; 
				oy = 0;
				break;
			case ALIGN_LB:
				ox = 0; 
				oy = H-BackImage.getHeight();
				break;
			case ALIGN_RT:
				ox = W-BackImage.getWidth(); 
				oy = 0;
				break;
			case ALIGN_RB:
				ox = W-BackImage.getWidth(); 
				oy = H-BackImage.getHeight();
				break;
			case ALIGN_LC:
				ox = 0; 
				break;
			case ALIGN_RC:
				ox = W-BackImage.getWidth(); 
				break;
			case ALIGN_CT:
				oy = 0;
				break;
			case ALIGN_CB:
				oy = H-BackImage.getHeight();
				break;
			}
			
			g.drawImage(BackImage, x+ox, y+oy, Transform);
			
		}else{

//#ifdef _DEBUG
			if(IsDebug){
				g.setColor(0xffff0000);
				g.drawRect(x, y, W, H);
				g.drawLine(x, y, x+W, y+H);
				g.drawLine(x+W, y, x, y+H);
			}
//#endif
		}
	}

}

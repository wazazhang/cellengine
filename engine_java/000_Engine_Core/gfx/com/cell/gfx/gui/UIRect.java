package com.cell.gfx.gui;

import com.cell.CObject;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class UIRect extends CObject
{
	public static UIRect createBlankRect(int backcolor){
		UIRect rect = new UIRect(true);
		rect.BackColor = backcolor;
		rect.BorderColor = 0;
		rect.IsLayouted = true;
		return rect;
	}
	public static UIRect createBlankRect(){
		return createBlankRect(0);
	}
	
	
	
//	public static boolean IsFullyAlphaMode = false;
	
	public boolean IsLayouted = false;
	
	//body
	public int BackColor		= 0xffc0c0c0;
	public IImage BackImage;
//	int[] BackAlphaColor ;
	
	//border
	public int BorderSize		= 1;
	public int BorderColor		= 0xff000000;
	
	private boolean isBorderImage = false;
	
	public IImage BorderT;
	public IImage BorderB;
	public IImage BorderL;
	public IImage BorderR;
	
	public IImage BorderTL;
	public IImage BorderTR;
	public IImage BorderBL;
	public IImage BorderBR;
	
	
//	#########################################################################################################
	
	UIRect(boolean isLayouted){
		IsLayouted		= isLayouted;
	}
	
	public UIRect(){
		IsLayouted = true;
	}
	
	public UIRect(UIRect set){
		this.set(set);
	}
	
	public void set(UIRect set){
		IsLayouted		= set.IsLayouted;
//		body
		BackColor		= set.BackColor;
		BackImage		= set.BackImage;
//		BackAlphaColor 	= set.BackAlphaColor;
		
//		border
		BorderSize		= set.BorderSize;
		BorderColor		= set.BorderColor;
		
		BorderT			= set.BorderT;
		BorderB			= set.BorderB;
		BorderL			= set.BorderL;
		BorderR			= set.BorderR;
		
		BorderTL		= set.BorderTL;
		BorderTR		= set.BorderTR;
		BorderBL		= set.BorderBL;
		BorderBR		= set.BorderBR;
		
		if(	BorderT==null || BorderB==null || 
				BorderL==null || BorderR==null ||
				BorderTL==null || BorderTR==null || 
				BorderBL==null || BorderBR==null  ){
			isBorderImage = false;
		} else{
			isBorderImage = true;
		}
	}
	
	/**
	 * @param images
	 * 
	 * Rect                                  </br>
	 *                                       </br>
	 * TopLeft----------Top---------TopRight </br>
	 * |                                   | </br>
	 * Left         Background         Right </br>
	 * |                                   | </br>
	 * BottomLeft-----Bottom-----BottomRight </br>
	 *                                       </br>
	 * images[0] images[1] images[2]         </br>
	 * images[3] images[4] images[5]         </br>
	 * images[6] images[7] images[8]         </br>
	 * 
	 */
	public void setImages(IImage[] images){
		if(images==null)return;
		if(images.length<9){
			System.err.println("UIRect : Set Images Error ! less than 9 images");
		}
		BorderTL = images[0];BorderT = images[1];BorderTR = images[2];//
		BorderL  = images[3];BackImage=images[4];BorderR  = images[5];//
		BorderBL = images[6];BorderB = images[7];BorderBR = images[8];//
		
		if(	BorderT==null || BorderB==null || 
				BorderL==null || BorderR==null ||
				BorderTL==null || BorderTR==null || 
				BorderBL==null || BorderBR==null  ){
			isBorderImage = false;
		} else{
			isBorderImage = true;
		}
	}

	public void exchangeBackImage2Color(int x,int y){
		if(BackImage!=null){
			int rgb[] = new int[1];
			BackImage.getRGB(rgb, 0, 1, x, y, 1, 1);
			BackColor = rgb[0];
			BackImage = null;
//			println("Exchage BackImage --> 0x"+Integer.toHexString(BackColor));
		}
	}
	
	public void renderBody(IGraphics g, int x, int y, int W, int H){
		
		int width = W - BorderSize*2;
		int height = H - BorderSize*2;
		
		if ( (width > 0) && (height > 0))
		{
			if(BackImage!=null){
				g.drawRoundImage(BackImage, x+BorderSize, y+BorderSize, width, height, 0);
			}else{
				if(BackColor!=0){
					if((BackColor&0xff000000)!=0xff000000){
						g.fillRectAlpha(BackColor, x+BorderSize, y+BorderSize, width, height);
					}else{
						g.setColor(BackColor);
						g.fillRect(x+BorderSize, y+BorderSize, width, height);
					}
				}
			}
		}

		
	}
	
	
	public void renderBorder(IGraphics g,int x,int y,int W,int H)
	{
		if(	!isBorderImage )
		{
			g.setColor(BorderColor);
			g.drawRect(x, y, W ,H);
		}
		else
		{
			g.drawImage(BorderTL, x, y, 0);
			g.drawImage(BorderBL, x, y+H-BorderBL.getHeight(), 0);
			g.drawImage(BorderTR, x+W-BorderTR.getWidth(), y, 0);
			g.drawImage(BorderBR, x+W-BorderBR.getWidth(), y+H-BorderBR.getHeight(), 0);
			
			g.drawRoundImage(BorderL, 
					x, 
					y+BorderTL.getHeight(),
					BorderL.getWidth(),
					H-BorderTL.getHeight()-BorderBL.getHeight(), 
					0);
			g.drawRoundImage(BorderR, 
					x+W-BorderR.getWidth(), 
					y+BorderTR.getHeight(),
					BorderR.getWidth(),
					H-BorderTR.getHeight()-BorderBR.getHeight(),  
					0);
			g.drawRoundImage(BorderT, 
					x+BorderTL.getWidth(), 
					y, 
					W-BorderTL.getWidth()-BorderTR.getWidth(),
					BorderT.getHeight(),  //////////BorderB.getHeight() ---> BorderT.getHeight()
					0);
			g.drawRoundImage(BorderB, 
					x+BorderBL.getWidth(), 
					y+H-BorderB.getHeight(), 
					W-BorderBL.getWidth()-BorderBR.getWidth(),
					BorderB.getHeight(),
					0);		
		}
		
	}
	
	
	
}

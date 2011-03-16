/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.j_phone.util.GraphicsUtil;


public class CImagesJPhone extends CObject implements IImages {
	
	/** TILE块的个数 */
	protected int Count = 0;
	/**当前加入了多少张图片*/
	protected int CurIndex = 0;

	//原图
	protected Image srcImage = null;
	
	//图片组内容
//	protected int ImageCount = 0;
//	protected int ImageIndex = 0;
//	protected Image[] Tiles = null;

	protected short[] TileX;
	protected short[] TileY;
	protected short[] TileW;
	protected short[] TileH;
	
	static final private short[] TRANS_TABLE = {
		0,//TRANS_NONE
		1,//TRANS_V
		2,//TRANS_H
		3,//TRANS_HV
		4,//TRANS_H90
		5,//TRANS_270
		6,//TRANS_90
		7 //TRANS_V90
	};

	//--------------------------------------------------------------------------------------------------------

	/**
	 * override 方法
	 * @see com.cell.IImages#buildImages(javax.microedition.lcdui.Image, int)
	 */
	public void buildImages(Image srcImage, int count) {
		Count = count;
		CurIndex = 0;
		
		this.srcImage = srcImage;
		
		TileX = new short[Count];
		TileY = new short[Count];
		TileW = new short[Count];
		TileH = new short[Count];
	}

//	/**
//	 * override 方法
//	 * @see com.cell.IImages#getImage(int)
//	 */
//	public Image getSrcImage(){
//		return srcImage;
//	}
//	
//	public Image getImage(int index){
//		return srcImage;
//	}
	
	/**
	 * override 方法
	 * @see com.cell.IImages#getKeyColor(int, int)
	 */
	public int getPixel(int index, int x,int y){
		try{
			Image back = Image.createImage(1,1);
			Graphics bg = back.getGraphics();
			bg.drawImage(srcImage, -(TileX[index] + x), -(TileY[index] + y), 0);
			int c = GraphicsUtil.getPixel(bg, 0, 0);
			return c;
		}catch(Exception err){
			System.out.println(err.getMessage());
			return 0xffff0000;
		}
//		Graphics g = srcImage.getGraphics();
//		int c = GraphicsUtil.getPixel(
//				g, 
//				TileX[index] + x, 
//				TileY[index] + y
//				);
//		return c;
	}
	
	/**
	 * override 方法
	 * @see com.cell.IImages#getWidth(int)
	 */
	public int getWidth(int Index) {
		return TileW[Index];
	}

	/**
	 * override 方法
	 * @see com.cell.IImages#getHeight(int)
	 */
	public int getHeight(int Index) {
		return TileH[Index];
	}


	/**
	 * override 方法
	 * @see com.cell.IImages#getCount()
	 */
	public int getCount(){
		return CurIndex;
	}

	/**
	 * 把原图整张的添加到组里。
	 * 
	 * @return 是否未装满
	 */
	public boolean addTile() {
		return addTile(0,0,srcImage.getWidth(),srcImage.getHeight());
	}

	/**
	 * 把原图的一个区域切成小图，放进组里。
	 * 
	 * @param TileX
	 *            切原图的x起点
	 * @param TileY
	 *            切原图的y起点
	 * @param TileWidth
	 *            切原图的宽
	 * @param TileHeight
	 *            切原图的高
	 * @return false 是否未装满
	 */
	public boolean addTile(int TileX, int TileY, int TileWidth,int TileHeight) {
		if (CurIndex < Count) {
			if (TileWidth <= 0 || TileHeight <= 0) {
				this.TileX[CurIndex] = (short)0;
				this.TileY[CurIndex] = (short)0;
				this.TileW[CurIndex] = (short)0;
				this.TileH[CurIndex] = (short)0;
			} else {
				this.TileX[CurIndex] = (short)TileX;
				this.TileY[CurIndex] = (short)TileY;
				this.TileW[CurIndex] = (short)TileWidth;
				this.TileH[CurIndex] = (short)TileHeight;
			}
			CurIndex++;
		}
		if (CurIndex >= Count) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 把原图按照网格切成若干小图，放进组里。
	 * 
	 * @param ClipX
	 *            切原图的x起点
	 * @param ClipY
	 *            切原图的y起点
	 * @param ClipWidth
	 *            切原图的整个宽
	 * @param ClipHeight
	 *            切原图的整个高
	 * @param TileWidth
	 *            每块的宽
	 * @param TileHeight
	 *            每块的高
	 */
	public void addTile(
			int ClipX, int ClipY, 
			int ClipWidth, int ClipHeight, 
			int TileWidth, int TileHeight) {
		if (CurIndex < Count) {
			for (int j = 0; j < ClipHeight / TileHeight; j++) {
				for (int i = 0; i < ClipWidth / TileWidth; i++) {
					if (!addTile(
							ClipX + TileWidth * i, 
							ClipY + TileHeight * j,
							TileWidth, 
							TileHeight)) {
						return;
					}
				}
			}
		}
	}

	/**
	 * override 方法
	 * @see com.cell.IImages#render(javax.microedition.lcdui.Graphics, int, int, int)
	 */
	public void render(Graphics g, int Index, int PosX, int PosY) {//	不可选择对齐方式,不可旋转
		render(g,Index,PosX,PosY,0);
	}

	/**
	 * override 方法
	 * @see com.cell.IImages#render(javax.microedition.lcdui.Graphics, int, int, int, int)
	 */
	public void render(Graphics g, int Index, int PosX, int PosY, int Style) {
		try {
			GraphicsUtil.drawRegion(
					g,
					srcImage,
					TileX[Index], //
					TileY[Index], //
					TileW[Index], //
					TileH[Index], //
					TRANS_TABLE[Style], //
					PosX,//
					PosY,//
					Graphics.TOP | Graphics.LEFT//
					);
			
		} catch (RuntimeException e) {
			println("Null Tile at " + Index);
		}
	}

}

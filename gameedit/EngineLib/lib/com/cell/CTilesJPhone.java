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

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.j_phone.util.GraphicsUtil;
import com.j_phone.util.ImageUtil;

/**
 * @author yifeizhang
 * IImages的MIDP2.0实现
 */
public class CTilesJPhone extends CObject implements IImages {
	
	/** TILE块的个数 */
	protected int Count = 0;
	/**当前加入了多少张图片*/
	protected int CurIndex = 0;

	//原图
	protected Image TileImage = null;
	//图片组内容
	protected Image[] Tiles = null;

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
	 * @see com.morefuntek.cell.IImages#buildImages(javax.microedition.lcdui.Image, int)
	 */
	public void buildImages(Image srcImage, int count) {
		gc();
		Count = count;
		Tiles = new Image[Count];
		CurIndex = 0;
		setTileImage(srcImage);
		//println("Images Count = " + NewTileCount);
	}

	public void gc() {
		TileImage = null;
		System.gc();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public Image getSrcImage(){
//		return TileImage;
//	}
//	
//	
//	/**
//	 * override 方法
//	 * @see com.morefuntek.cell.IImages#getImage(int)
//	 */
//	public Image getImage(int index){
//		return Tiles[index];
//	}
	
	/**
	 * override 方法
	 * @see com.morefuntek.cell.IImages#getKeyColor(int, int)
	 */
	public int getPixel(int index, int x, int y){
		if(Tiles[index]==null)return 0;
		Graphics g = Tiles[index].getGraphics();
		int c = GraphicsUtil.getPixel(g, x, y);
		return c;
	}
	
	/**
	 * override 方法
	 * @see com.morefuntek.cell.IImages#getWidth(int)
	 */
	public int getWidth(int Index) {
		if(Tiles[Index]==null)return 0;
		return Tiles[Index].getWidth();
	}

	/**
	 * override 方法
	 * @see com.morefuntek.cell.IImages#getHeight(int)
	 */
	public int getHeight(int Index) {
		if(Tiles[Index]==null)return 0;
		return Tiles[Index].getHeight();
	}


	/**
	 * override 方法
	 * @see com.morefuntek.cell.IImages#getCount()
	 */
	public int getCount(){
		return CurIndex;
	}
	/**
	 * 设置另一张图片做为原图。
	 * 
	 * @param NewTileImage
	 */
	public void setTileImage(Image NewTileImage) {
		TileImage = NewTileImage;
		System.gc();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 把原图整张的添加到组里。
	 * 
	 * @return 是否未装满
	 */
	public boolean addTile() {
		if (CurIndex < Count) {
			if (TileImage == null) {
				Tiles[CurIndex] = null;
			} else {
				Tiles[CurIndex] = TileImage;
			}
			CurIndex++;
		}
		if (CurIndex >= Count) {
			gc();
			return false;
		} else {
			return true;
		}
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
	public boolean addTile(int TileX, int TileY, int TileWidth, int TileHeight) {
		if (CurIndex < Count) {
			if (TileWidth <= 0 || TileHeight <= 0) {
				Tiles[CurIndex] = null;
			} else {
//				Tiles[CurIndex] = Image.createImage(
//						TileImage, 
//						TileX, TileY,
//						TileWidth, TileHeight, 
//						0);
//				Image img = DirectUtils.createImage(TileWidth,TileHeight,0x00ff00ff);
//			    DirectGraphics dg = DirectUtils.getDirectGraphics(img.getGraphics());
//			    dg.drawImage(TileImage,-TileX,-TileY,0,0);
//			    Tiles[CurIndex] = img;
				try{
					
				    Image img = Image.createImage(TileWidth, TileHeight);
//				    byte[] mask = new byte[TileWidth * TileHeight];
//				    for(int i=0;i<mask.length;i++){
//				    	mask[i] = 0x00;
//				    }
//				    mask = CIO.loadFile("/a.png");
//				    img = ImageUtil.createMaskedImage(img, mask);
//				    img = ImageUtil.createMonotone(img,0);
//				    img = ImageUtil.reverseColor(img);
				    Graphics g = img.getGraphics();
				    GraphicsUtil.drawRegion(
							g,
							TileImage,
							TileX, //
							TileY, //
							TileWidth, 
							TileHeight,
							0, //
							0,//
							0,//
							0//
							);
				    Tiles[CurIndex] = img;
				}catch(Exception err){
					System.out.println(
							getClass().getName() + 
							" : " + 
							err.getMessage());
					Tiles[CurIndex] = Image.createImage(1, 1);
				}
			}
			CurIndex++;
		}
		if (CurIndex >= Count) {
			gc();
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
	public void addTile(int ClipX, int ClipY, int ClipWidth,
			int ClipHeight, int TileWidth, int TileHeight) {
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
	 * 
	 * @param images
	 */
	public void addTile(Image[] images) {
		if (CurIndex < Count) {
			for (int i = 0; i < images.length; i++) {
				setTileImage(images[i]);
				if (!addTile()) {
					return;
				}
			}
		}
	}


	/**
	 * override 方法
	 * @see com.morefuntek.cell.IImages#render(javax.microedition.lcdui.Graphics, int, int, int)
	 */
	public void render(Graphics g, int Index, int PosX, int PosY) {//	不可选择对齐方式,不可旋转
		try {
			g.drawImage(//
					Tiles[Index], //
					PosX,//
					PosY,//
					Graphics.TOP | Graphics.LEFT//
			);
		} catch (RuntimeException e) {
			println("Null Tile at " + Index);
		}
	}

	/**
	 * override 方法
	 * @see com.morefuntek.cell.IImages#render(javax.microedition.lcdui.Graphics, int, int, int, int)
	 */
	public void render(Graphics g, int Index, int PosX, int PosY, int Style) {
		try {
			GraphicsUtil.drawRegion(
					g,
					Tiles[Index],
					0, //
					0, //
					Tiles[Index].getWidth(), //
					Tiles[Index].getHeight(), //
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

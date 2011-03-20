/**
 * ��Ȩ����[2006][������]
 *
 * ����2.0�汾Apache����֤("����֤")��Ȩ��
 * ���ݱ�����֤���û����Բ�ʹ�ô��ļ���
 * �û��ɴ�������ַ�������֤������
 * http://www.apache.org/licenses/LICENSE-2.0
 * ���������÷�����Ҫ������ͬ�⣬
 * ��������֤�ַ��������ǻ���"��ԭ��"�����ṩ��
 * ���κ���ʾ�Ļ�ʾ�ı�֤��������
 * �����������֤�����£��ض����ԵĹ�ϽȨ�޺����ơ�
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
 * IImages��MIDP2.0ʵ��
 */
public class CTilesJPhone extends CObject implements IImages {
	
	/** TILE��ĸ��� */
	protected int Count = 0;
	/**��ǰ�����˶�����ͼƬ*/
	protected int CurIndex = 0;

	//ԭͼ
	protected Image TileImage = null;
	//ͼƬ������
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
	 * override ����
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
//	 * override ����
//	 * @see com.morefuntek.cell.IImages#getImage(int)
//	 */
//	public Image getImage(int index){
//		return Tiles[index];
//	}
	
	/**
	 * override ����
	 * @see com.morefuntek.cell.IImages#getKeyColor(int, int)
	 */
	public int getPixel(int index, int x, int y){
		if(Tiles[index]==null)return 0;
		Graphics g = Tiles[index].getGraphics();
		int c = GraphicsUtil.getPixel(g, x, y);
		return c;
	}
	
	/**
	 * override ����
	 * @see com.morefuntek.cell.IImages#getWidth(int)
	 */
	public int getWidth(int Index) {
		if(Tiles[Index]==null)return 0;
		return Tiles[Index].getWidth();
	}

	/**
	 * override ����
	 * @see com.morefuntek.cell.IImages#getHeight(int)
	 */
	public int getHeight(int Index) {
		if(Tiles[Index]==null)return 0;
		return Tiles[Index].getHeight();
	}


	/**
	 * override ����
	 * @see com.morefuntek.cell.IImages#getCount()
	 */
	public int getCount(){
		return CurIndex;
	}
	/**
	 * ������һ��ͼƬ��Ϊԭͼ��
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
	 * ��ԭͼ���ŵ����ӵ����
	 * 
	 * @return �Ƿ�δװ��
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
	 * ��ԭͼ��һ�������г�Сͼ���Ž����
	 * 
	 * @param TileX
	 *            ��ԭͼ��x���
	 * @param TileY
	 *            ��ԭͼ��y���
	 * @param TileWidth
	 *            ��ԭͼ�Ŀ�
	 * @param TileHeight
	 *            ��ԭͼ�ĸ�
	 * @return false �Ƿ�δװ��
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
	 * ��ԭͼ���������г�����Сͼ���Ž����
	 * 
	 * @param ClipX
	 *            ��ԭͼ��x���
	 * @param ClipY
	 *            ��ԭͼ��y���
	 * @param ClipWidth
	 *            ��ԭͼ��������
	 * @param ClipHeight
	 *            ��ԭͼ��������
	 * @param TileWidth
	 *            ÿ��Ŀ�
	 * @param TileHeight
	 *            ÿ��ĸ�
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
	 * override ����
	 * @see com.morefuntek.cell.IImages#render(javax.microedition.lcdui.Graphics, int, int, int)
	 */
	public void render(Graphics g, int Index, int PosX, int PosY) {//	����ѡ����뷽ʽ,������ת
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
	 * override ����
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
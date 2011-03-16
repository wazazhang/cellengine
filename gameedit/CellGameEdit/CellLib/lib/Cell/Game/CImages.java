package Cell.Game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import Cell.CObject;
import Cell.CIO;

/**
 * 最底层的对图片的处理。 里面包含了一组图片的集合。 
 * 可以把一个大Image作为原图切成若干块小Image，也可以使用若干Image.
 * 
 *  
 */
public class CImages extends CObject{
	/*
	 * 翻转参数
	 */
	/** 不翻转 */
	final public static int TRANS_NONE = 0;
	/** 水平翻转 */
	final public static int TRANS_H = 2;
	/** 垂直翻转 */
	final public static int TRANS_V = 1;
	/** 180度翻转 */
	final public static int TRANS_HV = 3;
	/** 逆时针90度翻转 */
	final public static int TRANS_90 = 6;
	/** 逆时针270度翻转 */
	final public static int TRANS_270 = 5;
	/** 先逆时针90度翻转，然后在水平翻转 */
	final public static int TRANS_H90 = 4;
	/** 先逆时针90度翻转，然后在垂直翻转 */
	final public static int TRANS_V90 = 7;
	/** 180度翻转 */
	final public static int TRANS_180 = 3; // 180 = HV
	//    public static Graphics g;

	/** TILE块的个数 */
	private int Count;
	private int CurIndex;

	private Image TileImage;
	private Image[] Tiles;

	
	//--------------------------------------------------------------------------------------------------------

	/**
	 * 生成一个TileGroup
	 * 
	 * @param NewTileImage
	 *            原图片
	 * @param NewTileCount
	 *            切出的图的个数
	 */
	public CImages(Image NewTileImage, int NewTileCount) {
		Count = NewTileCount;
		Tiles = new Image[NewTileCount];
		CurIndex = 0;
		setTileImage(NewTileImage);
		//println("Images Count = " + NewTileCount);
	}

	/**
	 * 清除垃圾内存。 初始化完TILE后必须做的事情。
	 *  
	 */
	final public void gc() {
		TileImage = null;
		System.gc();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final public Image getImage(int index){
		return Tiles[index];
	}
	
	/**
	 * 得到子图的宽度
	 * 
	 * @param Index
	 *            子图序号
	 * @return 宽度
	 */
	final public int getWidth(int Index) {
		return Tiles[Index].getWidth();
	}

	/**
	 * 得到子图的高度
	 * 
	 * @param Index
	 *            子图序号
	 * @return 高度
	 */
	final public int getHeight(int Index) {
		return Tiles[Index].getHeight();
	}

	public int getCount(){
		return CurIndex;
	}
	/**
	 * 设置另一张图片做为原图。
	 * 
	 * @param NewTileImage
	 */
	final public void setTileImage(Image NewTileImage) {
		TileImage = NewTileImage;
	}

	/**
	 * 把原图整张的添加到组里。
	 * 
	 * @return 是否未装满
	 */
	final public boolean addTile() {
		if (CurIndex < Count) {
			if (TileImage == null) {
				Tiles[CurIndex] = Image.createImage(1, 1);
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
	final public boolean addTile(int TileX, int TileY, int TileWidth,
			int TileHeight) {
		if (CurIndex < Count) {
			if (TileWidth <= 0 || TileHeight <= 0) {
				Tiles[CurIndex] = Image.createImage(1, 1);
			} else {
				//				Tiles[CurIndex] = Image.createImage(TileWidth, TileHeight);
				//				Graphics g = Tiles[CurIndex].getGraphics();
				//				g.setClip(0,0,TileWidth,TileHeight);
				//				g.drawImage(TileImage,-TileX,-TileY,0);
				Tiles[CurIndex] = Image.createImage(TileImage, TileX, TileY,TileWidth, TileHeight, 0);
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
	final public void addTile(int ClipX, int ClipY, int ClipWidth,
			int ClipHeight, int TileWidth, int TileHeight) {
		if (CurIndex < Count) {
			for (int j = 0; j < ClipHeight / TileHeight; j++) {
				for (int i = 0; i < ClipWidth / TileWidth; i++) {
					if (!addTile(ClipX + TileWidth * i, ClipY + TileHeight * j,
							TileWidth, TileHeight)) {
						return;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param pakfile
	 */
	final public void addTile(String pakfile) {
		if (CurIndex < Count) {
			String[] imglist = CIO.getPakFileNameList(pakfile);
			for (int i = 0; i < imglist.length; i++) {
				setTileImage(CIO.loadImage(pakfile, imglist[i]));
				if (!addTile()) {
					return;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param images
	 */
	final public void addTile(Image[] images) {
		if (CurIndex < Count) {
			for (int i = 0; i < images.length; i++) {
				setTileImage(images[i]);
				if (!addTile()) {
					return;
				}
			}
		}
	}

	final public boolean addTile(Image img, int Alpha) {
		if (CurIndex < Tiles.length) {
			if (img == null) {
				System.out
						.println("CSImageGprup : load image Error ! null img arg -_-!");
				Tiles[CurIndex] = Image.createImage(1, 1);
			} else {
				if (Alpha >= 0xff) {
					Tiles[CurIndex] = img;
				} else {
					int[] argb = new int[img.getWidth() * img.getHeight()];
					img.getRGB(argb, 0, img.getWidth(), 0, 0, img.getWidth(),
							img.getHeight());
					//			        int transparent = argb[0];
					for (int i = argb.length - 1; i >= 0; i--) {
						//			            argb[i] = argb[i]|(Alpha<<24);
						if ((argb[i] & 0x00ffffff) == 0x00ff00ff) {
							//			            if(argb[i]==transparent){
							argb[i] = 0;
						} else {
							argb[i] = argb[i]
									- (0xff000000 - Alpha * 0x01000000);
						}

					}
					Tiles[CurIndex] = Image.createRGBImage(argb,img.getWidth(), img.getHeight(), true);
				}
			}
			CurIndex++;
		}
		if (CurIndex >= Tiles.length) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 绘制到指定的Graphics上。
	 * 
	 * @param g
	 *            指定的graphics
	 * @param Index
	 *            序号
	 * @param PosX
	 *            X坐标
	 * @param PosY
	 *            Y坐标
	 */
	final public void render(Graphics g, int Index, int PosX, int PosY) {//	不可选择对齐方式,不可旋转
		try {
			g.drawRegion(//
					Tiles[Index], //
					0, //
					0, //
					Tiles[Index].getWidth(), //
					Tiles[Index].getHeight(), //
					0, //
					PosX,//
					PosY,//
					Graphics.TOP | Graphics.LEFT//
			);
		} catch (RuntimeException e) {
		}
	}

	/**
	 * 绘制到指定的Graphics上。
	 * 
	 * @param g
	 *            指定的graphics
	 * @param Index
	 *            序号
	 * @param PosX
	 *            X坐标
	 * @param PosY
	 *            Y坐标
	 * @param Style
	 *            翻转方式，参考 CSSprite 里的翻转参数。
	 */
	final public void render(Graphics g, int Index, int PosX, int PosY, int Style) {
		try {
			g.drawRegion(
					Tiles[Index], //
					0, 0, //
					Tiles[Index].getWidth(), //
					Tiles[Index].getHeight(), //
					Style, //
					PosX, PosY,//
					Graphics.TOP | Graphics.LEFT//
			);
		} catch (RuntimeException e) {
			println("Null Tile at " + Index);
		}
	}

}
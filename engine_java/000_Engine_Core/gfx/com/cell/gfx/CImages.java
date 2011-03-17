package com.cell.gfx;



import com.cell.CObject;


public class CImages extends CObject implements IImages {

	protected int Count = 0;

	protected int CurIndex = 0;

	//ԭͼ
	protected IImage srcImage = null;

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
	 * override ����
	 * @see com.cell.gfx.IImages#buildImages(IImage, int)
	 */
	public void buildImages(IImage srcImage, int count) {
		Count = count;
		CurIndex = 0;
		
		this.srcImage = srcImage;
		
		TileX = new short[Count];
		TileY = new short[Count];
		TileW = new short[Count];
		TileH = new short[Count];
		
//		println("CImages : Build Images Count="+count);
	}

//	public int setMode(int mode){
//		return srcImage.setMode(mode);
//	}
	
	public void setPalette(IPalette palette)
	{
		srcImage.setPalette(palette);
	}

	public IImage getImage(int index){
		return srcImage.subImage(TileX[index], TileY[index], TileW[index], TileH[index]);
	}
	
	/**
	 * override ����
	 * 
	 * @see com.cell.gfx.IImages#getKeyColor(int, int)
	 */
	public int getPixel(int index, int x,int y){
		int[] c = new int[1];
		srcImage.getRGB(c, 0, 1, TileX[index]+x, TileY[index]+y, 1, 1);
		return c[0];
	}
	
	/**
	 * override ����
	 * @see com.cell.gfx.IImages#getWidth(int)
	 */
	public int getWidth(int Index) {
		return TileW[Index];
	}

	/**
	 * override ����
	 * @see com.cell.gfx.IImages#getHeight(int)
	 */
	public int getHeight(int Index) {
		return TileH[Index];
	}


	/**
	 * override ����
	 * @see com.cell.gfx.IImages#getCount()
	 */
	public int getCount(){
		return CurIndex;
	}

	/**
	 * ��ԭͼ���ŵ���ӵ����
	 * 
	 * @return �Ƿ�δװ��
	 */
	public boolean addTile() {
		return addTile(0,0,srcImage.getWidth(),srcImage.getHeight());
	}

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
	 * override ����
	 * @see com.cell.gfx.IImages#render(javax.microedition.lcdui.Graphics, int, int, int)
	 */
	public void render(IGraphics g, int Index, int PosX, int PosY) {//	����ѡ����뷽ʽ,������ת
		if( TileW[Index]!=0 && TileH[Index]!=0) {
			g.drawRegion(
					srcImage,
					TileX[Index], //
					TileY[Index], //
					TileW[Index], //
					TileH[Index], //
					TRANS_TABLE[0], //
					PosX,//
					PosY
					);
		} else {
//			println("Null Tile at " + Index);
		}
	}

	/**
	 * override ����
	 * @see com.cell.gfx.IImages#render(javax.microedition.lcdui.Graphics, int, int, int, int)
	 */
	public void render(IGraphics g, int Index, int PosX, int PosY, int Style) {
		if( TileW[Index]!=0 && TileH[Index]!=0) {
			g.drawRegion(
					srcImage,
					TileX[Index], //
					TileY[Index], //
					TileW[Index], //
					TileH[Index], //
					TRANS_TABLE[Style], //
					PosX,//
					PosY
					);
			
		} else {
//			println("Null Tile at " + Index);
		}
	}

	@Override
	public IImages deepClone() 
	{
		CImages new_one = new CImages();
		
		new_one.Count = this.Count;

		new_one.CurIndex = this.CurIndex;

		new_one.srcImage = (this.srcImage==null)? null : this.srcImage.newInstance();

		new_one.TileX = (this.TileX==null)? null : this.TileX.clone();
		new_one.TileY = (this.TileY==null)? null : this.TileY.clone();
		new_one.TileW = (this.TileW==null)? null : this.TileW.clone();
		new_one.TileH = (this.TileH==null)? null : this.TileH.clone();
		
		return new_one;
	}

	@Override
	public IImages clone() throws CloneNotSupportedException 
	{
		CImages new_one = new CImages();
		
		new_one.Count = this.Count;

		new_one.CurIndex = this.CurIndex;

		new_one.srcImage = this.srcImage;

		new_one.TileX = (this.TileX==null)? null : this.TileX.clone();
		new_one.TileY = (this.TileY==null)? null : this.TileY.clone();
		new_one.TileW = (this.TileW==null)? null : this.TileW.clone();
		new_one.TileH = (this.TileH==null)? null : this.TileH.clone();	
		
		return new_one;
	}

	
	
}

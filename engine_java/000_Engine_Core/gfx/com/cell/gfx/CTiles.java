package com.cell.gfx;

import com.cell.CObject;


public class CTiles extends CObject implements IImages {
	
	protected int Count = 0;
	protected int CurIndex = 0;
	
	protected IImage TileImage = null;
	protected IImage[] Tiles = null;

	//--------------------------------------------------------------------------------------------------------

	/**
	 * override ����
	 * @see com.cell.gfx.morefuntek.cell.IImages#buildImages(IImage, int)
	 */
	public void buildImages(IImage srcImage, int count) {
		if(Tiles!=null)gc();
		Count = count;
		Tiles = new IImage[Count];
		CurIndex = 0;
		setTileImage(srcImage);
//		println("CTiles : Build Images Count="+count);
	}

//	public int setMode(int mode){
//		for(int i=0;i<Tiles.length;i++){
//			if(Tiles[i]!=null)Tiles[i].setMode(mode);
//		}
//		return mode;
//	}
	
	public void setPalette(IPalette palette)
	{
		for ( int i=0; i<Tiles.length; ++i )
			if (Tiles[i] != null)
				Tiles[i].setPalette(palette);
	}
	
	public void gc() {
		if(TileImage!=null){
			TileImage = null;
			System.gc();
			try {
//				println("CTiles : Free SrcImage !");
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public IImage getImage(int index){
		return Tiles[index];
	}
	
	/**
	 * override ����
	 * @see com.cell.gfx.morefuntek.cell.IImages#getKeyColor(int, int)
	 */
	public int getPixel(int index, int x, int y){
		if(Tiles[index]==null)return 0;
		int[] c = new int[1];
		Tiles[index].getRGB(c, 0, 1, x, y, 1, 1);
		return c[0];
	}
	
	/**
	 * override ����
	 * @see com.cell.gfx.morefuntek.cell.IImages#getWidth(int)
	 */
	public int getWidth(int Index) {
		if(Tiles[Index]==null)return 0;
		return Tiles[Index].getWidth();
	}

	/**
	 * override ����
	 * @see com.cell.gfx.morefuntek.cell.IImages#getHeight(int)
	 */
	public int getHeight(int Index) {
		if(Tiles[Index]==null)return 0;
		return Tiles[Index].getHeight();
	}


	/**
	 * override ����
	 * @see com.cell.gfx.morefuntek.cell.IImages#getCount()
	 */
	public int getCount(){
		return CurIndex;
	}

	public void setTileImage(IImage NewTileImage) {
		TileImage = NewTileImage;
		System.gc();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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


	public boolean addTile(int TileX, int TileY, int TileWidth, int TileHeight) {
		if (CurIndex < Count) {
			if (TileWidth <= 0 || TileHeight <= 0) {
				Tiles[CurIndex] = null;
			} else {
				Tiles[CurIndex] = TileImage.subImage(TileX, TileY, TileWidth, TileHeight);
//				Tiles[CurIndex] = IImage.createImage(
//						TileImage, 
//						TileX, TileY,
//						TileWidth, TileHeight, 
//						0);
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


	public void addTile(int ClipX, int ClipY, int ClipWidth,
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


	public void addTile(IImage[] images) {
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
	 * @see com.cell.gfx.morefuntek.cell.IImages#render(javax.microedition.lcdui.Graphics, int, int, int)
	 */
	public void render(IGraphics g, int Index, int PosX, int PosY) {
		if(Tiles[Index]!=null){
			g.drawImage(//
					Tiles[Index], //
					PosX,//
					PosY,
					0
			);
		} else {
//			println("Null Tile at " + Index);
		}
	}

	/**
	 * override ����
	 * @see com.cell.gfx.morefuntek.cell.IImages#render(javax.microedition.lcdui.Graphics, int, int, int, int)
	 */
	public void render(IGraphics g, int Index, int PosX, int PosY, int Style) {
		if(Tiles[Index]!=null){
			g.drawImage(//
					Tiles[Index], //
					PosX,//
					PosY,
					Style
			);
		} else {
//			println("Null Tile at " + Index);
		}
	}
	
	
	@Override
	public IImages deepClone() 
	{
		CTiles new_one = new CTiles();
		
		new_one.Count = this.Count;
		new_one.CurIndex = this.CurIndex;

		new_one.TileImage = (this.TileImage==null)? null : this.TileImage.newInstance();

		if (this.Tiles == null)
		{
			new_one.Tiles = null;
		}
		else
		{
			boolean hold_same = true;
			for ( int i=0; i<this.Tiles.length; ++i )
			{
				if (this.Tiles[i] != this.TileImage)
				{
					hold_same = false;
					break;
				}
			}
			
			if (hold_same)
			{
				new_one.Tiles = new IImage[this.Tiles.length];
				for ( int i=0; i<new_one.Tiles.length; ++i )
					new_one.Tiles[i] = new_one.TileImage;
			}
			else
			{
				new_one.Tiles = new IImage[this.Tiles.length];
				for ( int i=0; i<new_one.Tiles.length; ++i )
					new_one.Tiles[i] = (this.Tiles[i]==null)? null : this.Tiles[i].newInstance();				
			}
		}
		
		return new_one;
	}

	@Override
	public IImages clone() throws CloneNotSupportedException 
	{
		CTiles new_one = new CTiles();
		
		new_one.Count = this.Count;
		new_one.CurIndex = this.CurIndex;

		new_one.TileImage = this.TileImage;
		new_one.Tiles = (this.Tiles==null)? null : this.Tiles.clone();
		
		return new_one;
	}	

}

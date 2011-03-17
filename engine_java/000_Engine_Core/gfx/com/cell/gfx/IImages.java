
package com.cell.gfx;


/**
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public interface IImages 
{
	
//	public int setMode(int mode);
	
	public void setPalette(IPalette palette);
	
	public void buildImages(IImage srcImage,int count);

	public IImage getImage(int index);
	
	public int getPixel(int index, int x,int y);
	
	public int getWidth(int Index);

	public int getHeight(int Index);

	public int getCount();

	public boolean addTile();

	public boolean addTile(int TileX, int TileY, int TileWidth, int TileHeight);

	public void addTile(int ClipX, int ClipY, int ClipWidth, int ClipHeight, int TileWidth, int TileHeight) ;

	public void render(IGraphics g, int Index, int PosX, int PosY, int Style);
	
	/**
	 * 克隆整个对象，但对象对其管理的IImage对象仍旧保持引用而不克隆
	 * @return
	 */
	public IImages clone() throws CloneNotSupportedException;
	
	/**
	 * 克隆整个对象，包括对其管理的IImage对象
	 * @return
	 */
	public IImages deepClone() throws CloneNotSupportedException;

}

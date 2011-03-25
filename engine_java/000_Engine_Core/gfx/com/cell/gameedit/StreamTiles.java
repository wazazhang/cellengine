package com.cell.gameedit;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.gameedit.object.ImagesSet;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.IPalette;


/**
 * @author WAZA
 * 支持网络缓冲的图片组
 */
public abstract class StreamTiles implements IImages, Runnable
{
	final protected SetResource		set;
	final protected ImagesSet		img;
	final protected IImage[]		images;
	
	final protected AtomicBoolean	is_loaded	= new AtomicBoolean(false);
	final protected AtomicBoolean	is_loading	= new AtomicBoolean(false);
	
//	protected AtomicInteger			mode_ = new AtomicInteger(0);
	
	final protected AtomicReference<IPalette> palette_ = new AtomicReference<IPalette>(null);
	
	
	public StreamTiles(ImagesSet img, SetResource res) {
		this.set	= res;
		this.images	= new IImage[img.getCount()];
		this.img	= img;
	}
	
	/**
	 * 子类可以覆盖为自己的加载图片方法，注意，该方法已获得
	 * CellSetResource， StreamTiles 的锁
	 */
	abstract protected void initImages();
	
	public boolean isLoaded() {
		return is_loaded.get();
	}

	public boolean isLoading() {
		return is_loading.get();
	}
	
	public void run() 
	{
		if (is_loading.getAndSet(true)) {
			return;
		}
		try {
			if (!is_loaded.get()) {
				synchronized (images) {
					initImages();
					is_loaded.set(true);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			is_loading.set(false);
		}
	}
	
	public void unloadAllImages() {
		synchronized (images) {
			is_loaded.set(false);
			for (int i = 0; i < images.length; i++) {
				images[i] = null;
			}
		}
	}
	
	public IImage getImage(int index){
		return images[index];
	}
	
	public int getWidth(int Index) {
		return img.getClipW(Index);
	}

	public int getHeight(int Index) {
		return img.getClipH(Index);
	}

	public int getCount(){
		return images.length;
	}
	
//	public void render(IGraphics g, int Index, int PosX, int PosY) {
//		IImage buff = images[Index];
//		if (buff != null) {
//			g.drawImage(buff, PosX, PosY, 0);
//		}else{
//			drawLoading(g, PosX, PosY, img.getClipW(Index), img.getClipH(Index));
//		}
//	}

	public void render(IGraphics g, int Index, int PosX, int PosY, int Style) {
		IImage buff = images[Index];
		if (buff != null) {
			g.drawImage(buff, PosX, PosY, Style);
		}else{
			drawLoading(g, PosX, PosY, img.getClipW(Index), img.getClipH(Index));
		}
	}

	protected void drawLoading(IGraphics g, int x, int y, int w, int h) 
	{
		g.setColor(0xff808080);
		g.fillRect(x, y, w, h);
		g.setColor(0xffffffff);
		g.drawString("loading...", x, y);
	}
	
	public int getPixel(int index, int x, int y){
		IImage buff = images[index];
		if (buff != null) {
			int[] rgb = new int[1];
			buff.getRGB(rgb, 0, 1, x, y, 1, 1);
			return rgb[0];
		}
		return 0;
	}
	
	public boolean	addTile() {return false;}
	public boolean	addTile(int TileX, int TileY, int TileWidth, int TileHeight) {return false;}
	public void		addTile(int ClipX, int ClipY, int ClipWidth, int ClipHeight, int TileWidth, int TileHeight) {}
	
	public void buildImages(IImage srcImage, int count) {}
	
//	public int setMode(int mode)
//	{
//		int ori_mode = mode_.getAndSet(mode);
//		
//		for ( int i=0; i<images.length; ++i )
//		{
//			IImage image = images[i];
//			if (image != null)
//				image.setMode(mode);
//		}
//		
//		return ori_mode;
//	}
//	
//	public int getMode()
//	{
//		return mode_.get();
//	}
	
	public void setPalette(IPalette palette) 
	{
		palette_.set(palette);
	
		for ( int i=0; i<images.length; ++i )
		{
			IImage image = images[i];
			if (image != null)
				image.setPalette(palette);
		}		
	}
	
	public IPalette getPalette()
	{
		return palette_.get();
	}

	@Override
	public IImages deepClone() throws CloneNotSupportedException 
	{	
		synchronized (is_loaded) {
			if (!isLoaded()) {
				throw new CloneNotSupportedException("src images are not initialized !");
			}
			return new CloneTiles(this, true);
		}
	}

	@Override
	public IImages clone() throws CloneNotSupportedException 
	{
		synchronized (is_loaded) {
			if (!isLoaded()) {
				throw new CloneNotSupportedException("src images are not initialized !");
			}
			return new CloneTiles(this, false);
		}
	}
	
	static public class CloneTiles extends StreamTiles
	{
		public CloneTiles(StreamTiles src, boolean deep) 
		{
			super(src.img, src.set);
			for (int i = src.images.length - 1; i >= 0; --i) {
				if ( src.images[i] == null) {
					this.images[i] = null;
				} else {
					if (deep) {
						this.images[i] = src.images[i].newInstance();
					} else {
						this.images[i] = src.images[i];
					}
				}
			}
			this.is_loaded.set(src.is_loaded.get());
			this.is_loading.set(src.is_loading.get());
			IPalette src_p = src.palette_.get();
			if (src_p != null) {
				if (deep) {
					this.palette_.set(src_p.clone());
				} else {
					this.palette_.set(src_p);
				}
			}
		}
		
		@Override
		protected void initImages() {}
	}
	
}




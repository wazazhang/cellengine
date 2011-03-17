package com.g2d.cell;

import java.io.ByteArrayInputStream;

import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gfx.IPalette;
import com.g2d.Engine;
import com.g2d.Image;

public class CellStreamTiles extends StreamTiles
{
	public CellStreamTiles(ImagesSet img, CellSetResource res) {
		super(img, res);
	}
	
	@Override
	protected void initImages()
	{
		try {
			byte[] idata = set.getOutput().loadRes(img.getName()+".png", null);
			Image src = Engine.getEngine().createImage(new ByteArrayInputStream(idata));
			IPalette palette = this.getPalette();
			if (palette != null)
				src.setPalette(palette);			
			for (int i=0; i<images.length; i++){
				if (img.getClipW(i)>0 && img.getClipH(i)>0){
					images[i] = src.subImage(img.getClipX(i), img.getClipY(i), img.getClipW(i), img.getClipH(i));
//					images[i].setMode(this.getMode());
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
}

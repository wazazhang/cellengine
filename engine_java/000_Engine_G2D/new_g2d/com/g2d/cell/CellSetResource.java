package com.g2d.cell;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.gameedit.OutputLoader;
import com.cell.gameedit.SetResource;
import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gfx.game.CSprite;
import com.cell.util.concurrent.ThreadPoolService;

/**
 * 对应output.properties的资源文件
 * @author WAZA
 */
public class CellSetResource extends SetResource
{
//	-------------------------------------------------------------------------------------
	
	public CellSetResource(OutputLoader output) throws Exception
	{
		super(output);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " : " + output_adapter.getPath();
	}
	
	public String getPath() {
		return output_adapter.getPath();
	}
	
	@Override
	protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
		return new CellStreamTiles(img, this);
	}
}

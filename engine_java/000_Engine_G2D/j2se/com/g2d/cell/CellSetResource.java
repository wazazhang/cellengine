package com.g2d.cell;


import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.gameedit.Output;
import com.cell.gameedit.SetResource;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.output.OutputProperties;
import com.cell.gameedit.output.OutputPropertiesDir;
import com.cell.gfx.game.CSprite;
import com.cell.util.concurrent.ThreadPoolService;

/**
 * 对应output.properties的资源文件
 * @author WAZA
 */
public class CellSetResource extends SetResource
{
	final private String Path;

//	-------------------------------------------------------------------------------------
	
	public CellSetResource(String file) throws Exception
	{
		this(file, null);
	}
	
	public CellSetResource(File file, ThreadPoolService loading_service) throws Exception
	{
		this(file.getPath(), loading_service);
	}
	
	public CellSetResource(String file, ThreadPoolService loading_service) throws Exception
	{
		super(new OutputPropertiesDir(file), loading_service);
		this.Path = ((OutputProperties) getOutput()).path;
	}
	
	public CellSetResource(Output output, String path, ThreadPoolService loading_service) throws Exception
	{
		super(output, loading_service);
		this.Path = path;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " : " + Path;
	}
	
	public String getPath() {
		return Path;
	}
}

package com.cell.rpg.res;

import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

import com.cell.util.concurrent.ThreadPool;
import com.cell.util.concurrent.ThreadPoolService;
import com.g2d.cell.CellSetResource;


public abstract class Resource extends CellSetResource
{
	/**
	 * @param file
	 * @param async true 代表是异步获得图片
	 * @throws Exception
	 */
	public Resource(String file, ThreadPoolService async) throws Exception
	{
		super(file, async);
	}
	
	public Resource(File file, ThreadPoolService stream_image) throws Exception
	{
		super(file, stream_image);
	}
	
	
	
}

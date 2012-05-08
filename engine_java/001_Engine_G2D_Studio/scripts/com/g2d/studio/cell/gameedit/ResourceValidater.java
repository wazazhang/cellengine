package com.g2d.studio.cell.gameedit;

import com.cell.CObject;
import com.cell.gameedit.output.OutputXmlDir;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.rpg.res.ResourceManager;
import com.cell.sql.SQLDriverManager;
import com.cell.util.concurrent.ThreadPoolService;
import com.cell.xstream.XStreamAdapter;
import com.g2d.cell.CellSetResource;
import com.g2d.studio.StudioConfig;

public class ResourceValidater extends ResourceManager
{
	public ResourceValidater(String res_root) throws Exception
	{
		super(XStreamAdapter.class.getCanonicalName(), 
				res_root, StudioConfig.G2D_SAVE_NAME,
				true, true, true, true);
	}
	
	
	@Override
	protected CellSetResource createSet(String path) throws Exception {
		if (path.endsWith(".xml")) {
			return new CellSetResource(new OutputXmlDir(path), path);
		} else {
			return null;
		}
	}
	
	
	public static void main(String[] args) throws Exception
	{
		CAppBridge.init();
		// 初始化服务器配置文件
		SQLDriverManager.init();
		ResourceValidater res = new ResourceValidater(args[0]);
		res.check();
		
		System.out.println("validate ok !");
	}
	
}

package com.g2d.studio.old.scene;

import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.studio.Config;
import com.g2d.studio.ResourceManager;
import com.g2d.studio.old.ATreeNodeGroup;
import com.g2d.studio.old.ATreeNodeLeaf;
import com.g2d.studio.old.ATreeNodeSet;
import com.g2d.studio.old.Studio;
import com.g2d.studio.old.Studio.SetResource;


public class FormSceneViewerGroup  extends ATreeNodeGroup<FormSceneViewer>
{
	public FormSceneViewerGroup(Studio studio)  throws Exception
	{
		super(studio, Config.SCENE_ROOT, Config.SCENE_FPJ_PREFIX);
	}
	
	
	

	@Override
	public ATreeNodeLeaf<FormSceneViewer>[] createViewers(ATreeNodeSet<FormSceneViewer> parent) throws Exception
	{
		SetResource res = CellSetResourceManager.getManager().getSet(parent.path + "/" + Config.SCENE_OUT_SUFFIX);
		
		FormSceneViewer[] viewers = new FormSceneViewer[res.WorldTable.size()];
		FormSceneViewerLeaf[] leafs = new FormSceneViewerLeaf[res.WorldTable.size()];
		                               
		int i=0;
		for (CellSetResource.WorldSet world : res.WorldTable.values()) {
			try{
				leafs[i] = new FormSceneViewerLeaf(world.Name, parent);
				viewers[i] = new FormSceneViewer(leafs[i], res, world.Name);
				leafs[i].setUserObject(viewers[i]);
			}catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		
		return leafs;
	
	}
	
	
}

package com.g2d.studio.scene.entity;


import com.g2d.studio.io.File;
import com.g2d.studio.scene.SceneManager;
import com.g2d.studio.swing.G2DTreeNodeGroup;

public class SceneGroup extends G2DTreeNodeGroup<SceneNode>
{
	private static final long serialVersionUID = 1L;
	
	public SceneGroup(String name) {
		super(name);
	}
	
	@Override
	protected G2DTreeNodeGroup<?> pathCreateGroupNode(String name) {
		return new SceneGroup(name);
	}
	
	@Override
	protected boolean pathAddLeafNode(String name, int index, int length) {
		if (name.endsWith(".xml")) {
			SceneManager manager = SceneManager.getInstance();
			File scene_dir = manager.scene_dir;
			File file = scene_dir.getChildFile(name);
			if (file.exists()) {
				manager.addScene(file, this);
				return true;
			}
		}
		return false;
	}
	
//	@Override
//	protected boolean createObjectNode(String key, Scene data) {
//		SceneManager manager = SceneManager.getInstance();
//		manager.addScene(file, this);
//		return false;
//	}
//	
//	@Override
//	protected boolean addSubNode(String name) {
//		if (name.endsWith(".xml")) {
//			SceneManager manager = SceneManager.getInstance();
//			File scene_dir = manager.scene_dir;
//			File file = new File(scene_dir, name);
//			if (file.exists()) {
//				manager.addScene(file, this);
//				return true;
//			}
//		}
//		return false;
//	}
	
}

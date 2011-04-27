package com.g2d.studio.scene.entity;


import com.cell.CUtil;
import com.g2d.studio.gameedit.entity.ObjectGroup;
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
	protected G2DTreeNodeGroup<?> createGroupNode(String name) {
		return new SceneGroup(name);
	}
	
//	public void loadPath(String node_path) {
//		String[] id_name = CUtil.splitString(node_path, "?");
//		if (id_name.length > 1) {
//			node_path = id_name[0];
//		}
//		SceneGroup group = this;
//		String[] path = fromPathString(node_path.trim(), "/");
//		for (int i=0; i<path.length; i++) {
//			String file_name = path[i].trim();
//			if (group.pathAddLeafNode(file_name, i, path.length)) {
//				return;
//			} else {
//				G2DTreeNodeGroup<?> g = group.findChild(file_name);
//				if (g == null) {
//					g = createGroupNode(file_name);
//					group.add(g);
//				}
//				group = (SceneGroup)g;
//			}
//		}
//	}

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

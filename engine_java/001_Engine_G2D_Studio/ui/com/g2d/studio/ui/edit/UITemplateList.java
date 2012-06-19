package com.g2d.studio.ui.edit;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.ui.edit.gui.UEFileNode;
import com.g2d.studio.ui.edit.gui.UERoot;

public class UITemplateList extends G2DTree
{
	private UIEdit edit;
	
	private DefaultMutableTreeNode template = new DefaultMutableTreeNode("基础控件", true);
	private HashMap<Class<?>, UITemplate> tempate_map = new HashMap<Class<?>, UITemplate>();
	
	private DefaultMutableTreeNode userdefile = new DefaultMutableTreeNode("用户控件", true);
	private HashMap<String, UITemplate> userdefile_map = new HashMap<String, UITemplate>();
	
	public UITemplateList(UIEdit edit)
	{
		super(new DefaultMutableTreeNode());
		super.setRootVisible(false);
		this.edit = edit;
		
		initTemplate();
		
		initUserDefine();

		reload();

		expandAll();
		
		setDragEnabled(true);
		
	}
	
	public UITemplate getTemplate(Class<?> type) {
		return tempate_map.get(type);
	}

	public UITemplate getTemplateFileName(String fileName) {
		return userdefile_map.get(fileName);
	}
	
	private void initTemplate() 
	{
		tempate_map.put(UERoot.class,
				new UITemplate(new UILayout(ImageStyle.NULL), UERoot.class, "root"));
		tempate_map.put(UEFileNode.class, 
				new UITemplate(new UILayout(ImageStyle.NULL), UEFileNode.class, "file"));
		for (UITemplate ut : edit.getLayoutManager().getTemplates()) {
			template.add(ut);
			tempate_map.put(ut.getUIType(), ut);
		}
		getRoot().add(template);
	}
	
	private void initUserDefine() {
		for (File f : edit.workdir.listFiles()) {
			if (f.getName().endsWith(".gui.xml")) {
				try {
					UITemplate ut = new UITemplate(f, f.getName());
					userdefile.add(ut);
					userdefile_map.put(f.getName(), ut);
				} catch (Exception e) {
					System.err.println("load ui failed : " + f.getName());
					System.err.println(e.getMessage());
				}
			}
		}
		getRoot().add(userdefile);
	}
	
	public void reloadUserDefine() {
		userdefile_map.clear();
		userdefile.removeAllChildren();
		initUserDefine();
		reload();
	}
	
	@Override
	protected boolean checkDrag(DropTarget evt_source, Transferable trans,
			Object src, Object dst, int position) {
		return false;
	}
}

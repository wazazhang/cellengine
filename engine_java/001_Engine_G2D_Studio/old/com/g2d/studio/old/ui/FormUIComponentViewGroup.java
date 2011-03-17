package com.g2d.studio.old.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import com.g2d.display.ui.Button;
import com.g2d.display.ui.CheckBox;
import com.g2d.display.ui.ComboBox;
import com.g2d.display.ui.Form;
import com.g2d.display.ui.Guage;
import com.g2d.display.ui.Label;
import com.g2d.display.ui.ListView;
import com.g2d.display.ui.PageSelectPanel;
import com.g2d.display.ui.Panel;
import com.g2d.display.ui.TextBox;
import com.g2d.display.ui.TextBoxSingle;
import com.g2d.display.ui.TrackBar;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.old.ATreeNodeGroup;
import com.g2d.studio.old.ATreeNodeLeaf;
import com.g2d.studio.old.ATreeNodeSet;
import com.g2d.studio.old.Studio;

public class FormUIComponentViewGroup extends ATreeNodeGroup<FormUIComponentView>
{
	ATreeNodeSet<FormUIComponentView> system_parent;
	
	
	JMenuItem menu_parent_add_comp =  new JMenuItem("添加控件");
	
	ATreeNodeSet<?> selected_parent;
	
	public FormUIComponentViewGroup(Studio studio) throws Exception
	{
		super(studio, "", "");
		
		createSystems();
		
		menu_parent_add_comp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("actionPerformed");
				if (selected_parent!=null) {
					createCustomComponent(selected_parent.getID(), "AddedForm");
			
				}
			}
		});
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ATreeNodeLeaf<FormUIComponentView>[] createViewers(ATreeNodeSet<FormUIComponentView> parent)  throws Exception
	{
		File[] files = studio.getFile(parent.path).listFiles();
		{
			Vector<File> fs = new Vector<File>();
			for (File file : files){
				if (!file.getPath().contains("svn")) {
					fs.add(file);
				}
			}
			files = fs.toArray(new File[fs.size()]);
		}
		
		ATreeNodeLeaf<FormUIComponentView>[] leafs = new ATreeNodeLeaf[files.length];
		
		for (int i=0; i<leafs.length; i++) 
		{
			try
			{
				leafs[i] = new ATreeNodeLeaf<FormUIComponentView>(parent.path + "/" + files[i].getName() + "/" + files[i].getName(), parent);
				FormUIComponentView view = new FormUIComponentEditView(leafs[i]);
				leafs[i].setUserObject(view);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return leafs;
	}
	
	
	
	public void createSystems()
	{
		Class[] types = new Class[]{
				Button.class,
				CheckBox.class,
				ComboBox.class,
				Form.class,
				Guage.class,
				Label.class,
				ListView.class,
				PageSelectPanel.class,
				Panel.class,
				TextBox.class,
				TextBoxSingle.class,
				TrackBar.class,
		};

		
		system_parent = new ATreeNodeSet<FormUIComponentView>(path+"/system", this);
		
		ATreeNodeLeaf<FormUIComponentView>[] leafs = new ATreeNodeLeaf[types.length];
		
		for (int i=0; i<leafs.length; i++) 
		{
			try
			{
				leafs[i] = new ATreeNodeLeaf<FormUIComponentView>(system_parent.path + "/" + types[i].getName()+ "/" + types[i].getName(), system_parent);
				FormUIComponentView view = new FormUIComponentView(leafs[i], (UIComponent)types[i].newInstance());
				leafs[i].setUserObject(view);
				
				system_parent.addChild(leafs[i]);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		addChild(system_parent);
	}
	
	
	public void createCustomComponent(String parent, String name)
	{
		ATreeNodeSet<FormUIComponentView> p = getChild(parent);
		
		
		if (p!=null)
		{
			String file = p.path + "/" + name + "/" + name;
			
			ATreeNodeLeaf<FormUIComponentView> leaf = new ATreeNodeLeaf<FormUIComponentView>(file, p);
			
			FormUIComponentView view = new FormUIComponentEditView(leaf);
			
			p.addChild(leaf);

		}
	}
	
	
	@Override
	public void onParentRightClicked(JTree tree, ATreeNodeSet<?> parent, MouseEvent e) 
	{
		if (parent != system_parent) 
		{
			selected_parent = parent;
			
			JPopupMenu menu = new JPopupMenu();
			menu.add(menu_parent_add_comp);
			menu.add(new JMenuItem("action 2"));
			menu.add(new JMenuItem("action 3"));
			menu.add(new JMenuItem("action 4"));
			menu.show(tree, e.getX(), e.getY());
			
			
		}
		
	}
	
	
	
	
}

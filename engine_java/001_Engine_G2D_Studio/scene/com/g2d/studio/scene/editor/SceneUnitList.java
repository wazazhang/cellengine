package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.cell.CUtil;
import com.g2d.cell.game.Scene;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.Version;
import com.g2d.studio.scene.units.SceneUnitTag;

public class SceneUnitList<T extends SceneUnitTag<?>> extends JPanel implements CUtil.ICompare<T, T>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	final SceneEditor		viewer;
	Scene 					scene;
	Class<T>				unit_type;
	
	JList 					list ;
	JScrollPane				scroll;

	JTextField				text_unit_name;

//	-----------------------------------------------------------------------------------------------------------------------------------------
	
	public SceneUnitList(SceneEditor viewer, Class<T> cls) 
	{	
		this.setLayout(new BorderLayout());
		
		this.viewer		= viewer;
		this.scene 		= viewer.getGameScene();
		this.unit_type	= cls;
		
		this.list 		= createList();
		this.scroll 	= new JScrollPane(list);
		
		this.add(scroll, BorderLayout.CENTER);
		
		text_unit_name = new JTextField();
		text_unit_name.setEditable(true);
		this.add(text_unit_name, BorderLayout.SOUTH);
		
		list.addMouseListener(new ListMouseListener());
	}

	protected JList createList() {
		return new JList();
	}
	
	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
		refreshList();
		super.repaint(tm, x, y, width, height);
	}
	
	public void refreshList()
	{
		if (scene != null && scene.getWorld()!=null) {
			scene.getWorld().processEvent();
			Vector<T> units = scene.getWorld().getChilds(unit_type);
			CUtil.sort(units, this);
			list.setListData(units);
		}
	}
	
	public void setSelecte(Unit unit) {
		if (unit_type.isInstance(unit)) {
			list.setSelectedValue(unit, true);
			if (unit != null) {
				text_unit_name.setText(unit.getID()+"");
			}
		}
	}

	public int compare(T a, T b) {
		return CUtil.getStringCompare().compare(a.getGameUnit().getID()+"", b.getGameUnit().getID()+"");
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------------------------------
	
	
	class ListMouseListener extends MouseAdapter
	{
		SceneUnitTag<?> unit;
		
		private Object getSelect(MouseEvent e)
		{
			try{
				int		index		= list.locationToIndex(e.getPoint());
				Object	selected	= list.getModel().getElementAt(index);
				return selected;
			}catch(Exception err){}
			return null;
		}
		
		@Override
		public void mousePressed(MouseEvent e) 
		{
			unit				= null;
			Object	selected	= getSelect(e);
			list.setSelectedValue(selected, false);
			if (selected instanceof SceneUnitTag<?>) {
				unit = (SceneUnitTag<?>)selected;
				viewer.selectUnit(unit, false);
				text_unit_name.setText(unit.getGameUnit().getID()+"");
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			Object	selected	= getSelect(e);
			if (selected == unit) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					new UnitListMenu(unit).show(list, e.getX(), e.getY());
				}
			}
		}
		
		public void mouseClicked(MouseEvent e) 
		{
			Object	selected	= getSelect(e);
			if (selected == unit && selected!=null) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2) {
						viewer.scene_panel.locationCameraCenter(unit.getGameUnit().getX(), unit.getGameUnit().getY());
					}
				} 
			}
		}
	}

	class UnitListMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		SceneUnitTag<?> unit;
		JMenuItem info 		= new JMenuItem();
		JMenuItem rename 	= new JMenuItem("重命名");
		JMenuItem property	= new JMenuItem("属性");
		JMenuItem delete	= new JMenuItem("删除");
		
		public UnitListMenu(SceneUnitTag<?> unit)
		{
			this.unit = unit;
			info.setText(unit.getClass().getSimpleName() + " : " + unit.getListName());
			info.setEnabled(false);
			
			rename.addActionListener(this);
			property.addActionListener(this);
			delete.addActionListener(this);
			
			this.add(info);
			this.add(rename);
			this.add(property);
			this.add(delete);
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == rename) {
				String new_name = JOptionPane.showInputDialog("input name", unit.getGameUnit().getID());
				if (new_name!=null) {
					if (!unit.getGameUnit().setID(scene.getWorld(), new_name)){
						JOptionPane.showMessageDialog(list, "bad name or duplicate !");
					} else {
						unit.getUnit().name = unit.getGameUnit().getID()+"";
					}
					viewer.refreshAll();
				}
			} 
			else if (e.getSource() == property) {
				DisplayObjectEditor<?> editor = unit.getEditorForm();
				editor.setCenter();
				editor.setAlwaysOnTop(true);
				editor.setVisible(true);
			}
			else if (e.getSource() == delete) {
				viewer.removeUnit(unit);
				viewer.refreshAll();
			}
		}
	}

}

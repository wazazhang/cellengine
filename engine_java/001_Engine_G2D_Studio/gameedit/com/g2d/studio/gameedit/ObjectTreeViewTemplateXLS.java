package com.g2d.studio.gameedit;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSColumns;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class ObjectTreeViewTemplateXLS<T extends XLSTemplateNode<D>, D extends TemplateNode> 
extends ObjectTreeView<T, D>
{
	private static final long serialVersionUID = 1L;
	
	final public File 		xls_file ;
	final public XLSColumns	xls_columns;
	Map<String, XLSFullRow> xls_row_map;
	
	public ObjectTreeViewTemplateXLS(
			String		title, 
			Class<T>	node_type, 
			Class<D>	data_type, 
			String		objects_dir,
			File		xls_file,
			ProgressForm progress) 
	{
		super(title, node_type, data_type, ObjectManager.toListFile(objects_dir, data_type), progress);
		
		this.xls_file = xls_file;
		this.xls_row_map = new TreeMap<String, XLSFullRow>();
		int i=0;
		
		xls_columns = XLSColumns.getXLSColumns(xls_file.getInputStream());
		Collection<XLSFullRow> list = XLSFullRow.getXLSRows(
				xls_file.getInputStream(), 
				xls_file.getName(),
				XLSFullRow.class);
		progress.setMaximum(title, list.size());
		for (XLSFullRow row : list) {
			xls_row_map.put(row.id, row);
			progress.setValue(row.id, i++);
		}
		getTreeRoot().loadList(progress);
		boolean new_xls_row = false;
		Collection<XLSFullRow> values = xls_row_map.values();
		progress.setMaximum(title, values.size());
		i = 0;
		for (XLSFullRow row : values) {
			if (getNode(Integer.parseInt(row.id))==null) {
				T node = createObjectFromRow(row.id, null);
				if (node != null) {
					addNode(getTreeRoot(), node);
					new_xls_row = true;
					System.out.println("find new xls row data : " + node.getID() + " : " + node.getName());
				}
			}
			progress.setValue(row.id, i++);
		}
		reload();
		getTree().setDragEnabled(true);
		if (new_xls_row) {
			saveAll(null);
		} else {
			saveListFile();
		}
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------------------------	
	
	@Override
	public void refresh(IProgress progress)
	{
		super.refresh(progress);		
		
		Collection<XLSFullRow> list = XLSFullRow.getXLSRows(xls_file.getInputStream(), xls_file.getName(), XLSFullRow.class);
		
		if ( (list == null) || list.isEmpty() )
			return;
		
		Map<String, XLSFullRow> tmp_xls_row_map = new TreeMap<String, XLSFullRow>();
		
		int progressMaximum = list.size() / 200;
		
		progress.setMaximum("refresh xls rows: ", progressMaximum);
		
		int i = 0;
		
		for (XLSFullRow row : list) 
		{
			xls_row_map.put(row.id, row);
			tmp_xls_row_map.put(row.id, row);
			
			progress.setValue("refresh xls rows: "+row.id, ++i/200);
		}
		
		
		
		progressMaximum = xls_row_map.size() / 200;
		
		progress.setMaximum("refreshing node: ", progressMaximum);
		
		i = 0;
		
		for (XLSFullRow row : xls_row_map.values()) {
			T node = getNode(Integer.parseInt(row.id));
			if (node==null) {
				node = createObjectFromRow(row.id, null);
				if (node != null) {
					addNode(getTreeRoot(), node);
				}
			} else {
				if (!tmp_xls_row_map.containsKey(row.id)){
					removeNode(getTreeRoot(), node);
				}					
			}
			
			progress.setValue("refreshing node: "+row.id, ++i/200);
		}
		reload();
		xls_row_map = tmp_xls_row_map;
		
		progress.setValue("refresh completed, (total " + xls_row_map.size() + ")  ", progressMaximum);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	protected XLSGroup createTreeRoot(String title) {
		return new XLSGroup(title);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	private T createObjectFromRow(String row_key, TemplateNode data) {
		return ObjectManager.createXLSObjectFromRow(node_type, xls_row_map, row_key, data);
	}

//	-----------------------------------------------------------------------------------------------------------------------------------

	class XLSGroup extends ObjectGroup<T, D>
	{
		private static final long serialVersionUID = 1L;

		public XLSGroup(String name) {
			super(name, 
					ObjectTreeViewTemplateXLS.this.list_file,
					ObjectTreeViewTemplateXLS.this.node_type, 
					ObjectTreeViewTemplateXLS.this.data_type);
		}
		
		protected boolean createObjectNode(String key, D data) {
			T node = createObjectFromRow(key, data);
			if (node!=null) {
				addNode(this, node);
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		protected XLSGroup pathCreateGroupNode(String name) {
			return new XLSGroup(name);
		}

		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new XLSGroupMenu(this).show(
						tree,
						e.getX(),
						e.getY());
			}
		}
	}
	
	
	class XLSGroupMenu extends GroupMenu
	{
		XLSGroup root;
		
		public XLSGroupMenu(XLSGroup root) {
			super(root, ObjectTreeViewTemplateXLS.this, getTree());
			this.root = root;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delete) {
				if (root.getChildCount()>0) {
					JOptionPane.showMessageDialog(ObjectTreeViewTemplateXLS.this, "不能删除该节点，过滤器不为空！");
					return;
				}
			}
			super.actionPerformed(e);
		}
	}
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	

	
	
	
	
	
	
	
	
	
	
}

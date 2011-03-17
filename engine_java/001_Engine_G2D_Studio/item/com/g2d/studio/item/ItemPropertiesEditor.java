package com.g2d.studio.item;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.cell.rpg.item.ItemPropertyTypes;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.PropertyEditor;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.rpg.ItemCountCollectionEdit;
import com.g2d.studio.rpg.ItemFormulaEdit;
import com.g2d.studio.rpg.ItemRateCollectionEdit;

public class ItemPropertiesEditor extends ObjectViewer<ItemPropertiesNode> implements ActionListener
{	
	JToolBar			toolbar = new JToolBar();

	
//	-------------------------------------------------------------------------------------
	
	public ItemPropertiesEditor(ItemPropertiesNode node) {
		super(node, getAdapters());
		this.add(toolbar, BorderLayout.NORTH);
	}
	
	@Override
	protected void appendPages(JTabbedPane table) {
		table.removeAll();
		table.addTab("道具属性", 	page_abilities);
		table.addTab("附加属性", page_object_panel);
		table.setSelectedComponent(page_abilities);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}
	
//	-------------------------------------------------------------------------------------
	
	private static CellEditAdapter<?>[] getAdapters() {
		if (ItemPropertyTypes.getItemPropertyManager() != null && 
			ItemPropertyTypes.getItemPropertyManager().getAllAdapters() != null) {
			HashSet<CellEditAdapter<?>> ret = new HashSet<CellEditAdapter<?>>();
			for (PropertyEditor<?> p : ItemPropertyTypes.getItemPropertyManager().getAllAdapters()) {
				if (p instanceof CellEditAdapter<?>) {
					ret.add((CellEditAdapter<?>)p);
				}
			}
			ret.add(new ItemPropertiesAdapter.ValueRangeAdapter());
			ret.add(new ItemFormulaEdit.ItemFormulaAdapter());
			ret.add(new ItemRateCollectionEdit.ItemRateCollectionAdapter());
			ret.add(new ItemCountCollectionEdit.ItemCountCollectionAdapter());
			ret.add(new XLSItemSelectCellEdit.XLSItemSelectAdapter());
			return ret.toArray(new CellEditAdapter<?>[ret.size()]);
		} else {
			return new CellEditAdapter<?>[]{
					new ItemPropertiesAdapter.ValueRangeAdapter(),
					new ItemFormulaEdit.ItemFormulaAdapter(),
			};
		}
	}
}

package com.g2d.studio.item;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.cell.rpg.item.ItemPropertyManager;
import com.cell.rpg.item.ItemPropertyTypes;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.PropertyEditor;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.rpg.AbilityAdapter;
import com.g2d.studio.rpg.ItemCountCollectionEdit;
import com.g2d.studio.rpg.ItemFormulaEdit;
import com.g2d.studio.rpg.ItemRateCollectionEdit;
import com.g2d.studio.rpg.PropertyAdapters;





public class ItemPropertiesEditor extends ObjectViewer<ItemPropertiesNode> implements ActionListener
{	
	private static final long serialVersionUID = 1L;
	

	JToolBar toolbar = new JToolBar();

	
//	-------------------------------------------------------------------------------------
	
	public ItemPropertiesEditor(ItemPropertiesNode node) 
	{
		super(node, getAdapters());
		this.add(toolbar, BorderLayout.NORTH);
	}
	
	@Override
	protected void appendPages(JTabbedPane table) 
	{
		table.removeAll();
		table.addTab("道具属性", 	page_abilities);
		table.addTab("附加属性", page_object_panel);
		table.setSelectedComponent(page_abilities);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}
	
//	-------------------------------------------------------------------------------------
	
	public static CellEditAdapter<?>[] getAdapters() 
	{
		ItemPropertyManager item_property_manager = null;
		Set<PropertyEditor<?>> adpaters = null;
		
		if ( ((item_property_manager=ItemPropertyTypes.getItemPropertyManager()) != null)
			 && ((adpaters=item_property_manager.getAllAdapters()) != null) )
		{
			HashSet<CellEditAdapter<?>> ret = new HashSet<CellEditAdapter<?>>();
			for (PropertyEditor<?> p : adpaters) 
			{
				if (p instanceof CellEditAdapter<?>)
					ret.add((CellEditAdapter<?>)p);
			}
			ret.add(new AbilityAdapter());
			ret.add(new AbilityAdapter.ObjectAdapter());
			ret.add(new PropertyAdapters.UnitTypeAdapter());
			ret.add(new ItemPropertiesAdapter.ValueRangeAdapter());
			ret.add(new ItemFormulaEdit.ItemFormulaAdapter());
			ret.add(new ItemRateCollectionEdit.ItemRateCollectionAdapter());
			ret.add(new ItemCountCollectionEdit.ItemCountCollectionAdapter());
			ret.add(new XLSItemSelectCellEdit.XLSItemSelectAdapter());
			return ret.toArray(new CellEditAdapter<?>[ret.size()]);
		}
		else 
		{
			return new CellEditAdapter<?>[]
			      {
					new AbilityAdapter(),
					new AbilityAdapter.ObjectAdapter(),	
					new PropertyAdapters.UnitTypeAdapter(),
					new ItemPropertiesAdapter.ValueRangeAdapter(),
					new ItemFormulaEdit.ItemFormulaAdapter(),
					new ItemRateCollectionEdit.ItemRateCollectionAdapter(),
					new ItemCountCollectionEdit.ItemCountCollectionAdapter(),
					new XLSItemSelectCellEdit.XLSItemSelectAdapter(),			
			      };
		}
	}
}

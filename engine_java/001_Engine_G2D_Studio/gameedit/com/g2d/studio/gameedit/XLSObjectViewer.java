package com.g2d.studio.gameedit;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.g2d.editor.property.CellEditAdapter;
import com.g2d.studio.gameedit.template.XLSTemplateNode;

public class XLSObjectViewer<T extends XLSTemplateNode<?>> extends ObjectViewer<T>
{
	private static final long serialVersionUID = 1L;
	
	protected JTable 		page_xls_list;

	public XLSObjectViewer(T object, CellEditAdapter<?> ... adapters ) 
	{
		super(object, adapters);
	}
	
	@Override
	protected void appendPages(JTabbedPane table) 
	{
		Vector<String> c0 = new Vector<String>(getRPGObject().getXLSRow().getColumns());
		String[][] datas = new String[c0.size()][];
		for (int i=0; i<c0.size(); i++) {
			String c = c0.get(i);
			datas[i] = new String[]{
					tobject.getXLSRow().getDesc(c),
					tobject.getXLSRow().getValue(c),
					};
		}
		page_xls_list = new JTable(
				datas,
				new String[]{"字段名","字段值"});
		table.addTab("XLS数据", new JScrollPane(page_xls_list));
	}
	
//	-------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	
}

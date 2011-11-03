package com.g2d.studio.gameedit.template;

import javax.swing.ImageIcon;

import com.cell.rpg.template.TShopItem;
import com.cell.rpg.template.TemplateNode;
import com.cell.xls.XLSFile;
import com.cell.xls.XLSFullRow;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectTreeView;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.XLSObjectViewer;

@SuppressWarnings("serial")
final public class XLSShopItem extends XLSTemplateNode<TShopItem>
{
	private XLSItem item_template = null;
	
	public XLSShopItem(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);
		getXLSItem();
	}
	
	@Override
	protected TShopItem newData(XLSFile xlsFile, XLSFullRow xlsRow) {
		return new TShopItem(getIntID(), xlsRow.desc);
	}
	
	@Override
	public String getName() {
		getXLSItem();
		if (item_template != null) {
			return item_template.getData().name + "(" + getIntID() + ")";
		}
		return super.getName();
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		getXLSItem();
		if (item_template != null) {
			return item_template.getIcon(update);
		}
		return super.getIcon(update);
	}

	public ObjectViewer<?> getEditComponent(){
		onOpenEdit();
		getXLSItem();
		if (edit_component == null) {
			edit_component = new XLSObjectViewer<XLSShopItem>(this);
		}
		return edit_component;
	}
	
	public XLSItem getXLSItem() {
		if (getData().item_count < 1) {
			getData().item_count = 1;
		}
		if (item_template==null || item_template.getIntID() != getData().item_template_id) {
			item_template = Studio.getInstance().getObjectManager().getObject(XLSItem.class, getData().item_template_id);
			ObjectTreeView<?,?> page = Studio.getInstance().getObjectManager().getPage(XLSShopItem.class);
			if (page != null) {
				page.reload();
			}
		}
		return item_template;
	}
}

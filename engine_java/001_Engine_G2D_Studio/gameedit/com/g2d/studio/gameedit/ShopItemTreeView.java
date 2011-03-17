package com.g2d.studio.gameedit;


import com.cell.rpg.template.TShopItem;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.template.XLSShopItem;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DTree;

@SuppressWarnings("serial")
public class ShopItemTreeView extends ObjectTreeViewTemplateXLS<XLSShopItem, TShopItem>
{
	
	public ShopItemTreeView(
			String		title, 
			String		objects_dir,
			File		xls_file,
			ProgressForm progress) {
		super(title, XLSShopItem.class, TShopItem.class, objects_dir, xls_file, progress);
	}

	@Override
	public G2DTree createTree(ObjectGroup<XLSShopItem, TShopItem> treeRoot) {
		return new G2DTree(treeRoot){
			@Override
			public String convertValueToText(Object value, boolean selected,
					boolean expanded, boolean leaf, int row, boolean hasFocus) {
				if (value instanceof XLSShopItem) {
					XLSShopItem node = (XLSShopItem)value;
					StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
					sb.append("<p>");
					sb.append(node.getName());
					sb.append("<font color=0000ff> - 数量(" + node.getData().item_count+ ")</font>");
					sb.append("</p>");
					sb.append("</body></html>");
					return sb.toString();
				}
				return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
			}
		};
	}
}

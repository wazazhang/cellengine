package com.cell.rpg.template;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

public class TShopItemList extends TemplateNode 
{	
	public TShopItemList(int id, String name) {
		super(id, name);
	}

	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class[] {
				ShopItem.class
		};
	}

	/**
	 * 掉落物品列表
	 * @author WAZA
	 */
	@Property("[单位能力] 售卖商品")
	static public class ShopItem extends AbstractAbility
	{
		private static final long serialVersionUID = 1L;
		
		@Property("商品ID")
		public int shop_item_id = -1;
		
		transient private TShopItem shop_item_show;
		
		public ShopItem() {}
		
		@Override
		public boolean isMultiField() {
			return true;
		}
		
		public void setShopItem(TShopItem shop_item_show) {
			if (shop_item_show != null) {
				this.shop_item_show = shop_item_show;
				this.shop_item_id	= shop_item_show.item_template_id;
			}
		}
		
		@Override
		public String toString() {
			if (shop_item_show!=null) {
				return super.toString() + " - " + shop_item_show.getName();
			} else {
				return super.toString() + " - (" + shop_item_id + ")";
			}
		}
	}
}

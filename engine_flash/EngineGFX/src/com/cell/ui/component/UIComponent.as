package com.cell.ui.component
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;

	public class UIComponent extends CellSprite
	{
		internal var bg : UIRect;
		
		public function UIComponent(bg:UIRect=null, width:int=200, height:int=200)
		{
			if (bg == null) {
				bg = UILayoutManager.getInstance().createDefaultBG(this);
			}
			this.bg  = bg;
			this.bg.createBuffer(width, height);
			this.addChild(bg);
		}
		
		public function resize(w:int, h:int) : void
		{
			bg.createBuffer(w, h);
		}
	}
}
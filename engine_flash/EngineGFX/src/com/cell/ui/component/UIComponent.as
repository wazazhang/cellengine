package com.cell.ui.component
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;

	public class UIComponent extends CellSprite
	{
		internal var bg : UIRect;
		
		public function UIComponent(rect:UIRect=null, width:int=200, height:int=200)
		{
			if (rect == null) {
				rect = UILayoutManager.getInstance().createDefaultBG(this);
			}
			this.bg  = rect;
			this.bg.createBuffer(width, height);
			this.addChild(bg);
		}
		
		public function resize(w:int, h:int) : void
		{
			bg.createBuffer(w, h);
		}
	}
}
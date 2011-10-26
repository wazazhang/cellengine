package com.cell.ui.component
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;

	public class UIForm extends CellSprite
	{		
		internal var bg : UIRect;

		public function UIForm(width:int=200, height:int=200)
		{
			bg  = UILayoutManager.getInstance().formCreateBG();
			this.addChild(bg);
			resize(width, height);
		}
		
		public function resize(w:int, h:int) : void
		{
			bg.createBuffer(w, h);
		}

	}
}
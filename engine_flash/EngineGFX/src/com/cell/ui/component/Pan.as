package com.cell.ui.component
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;

	public class Pan extends UIComponent
	{		

		public function Pan(width:int=200, height:int=200)
		{
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			resize(width, height, true);
		}
		

	}
}
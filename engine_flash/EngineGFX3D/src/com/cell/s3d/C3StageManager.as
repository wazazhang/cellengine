package com.cell.s3d
{
	import starling.display.Sprite;

	public class C3StageManager extends Sprite
	{
		public function C3StageManager()
		{
		}
		
		public function clearChilds() : void {
			while (this.numChildren>0){
				removeChildAt(0, true);
			}
		}
		
		public function changeStage(cls:Class, args:Array=null) : void 
		{
			clearChilds();
			var stg : C3Stage = new cls() as C3Stage;
			this.addChild(stg);
			stg.onEnter(this, args);
			
		}
	}
}
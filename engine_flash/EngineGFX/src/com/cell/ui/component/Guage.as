package com.cell.ui.component
{
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;

	public class Guage extends UIComponent
	{
			private var ui_strip 	: UIRect;
			
			private var value 		: Number = 0;
			private var range 		: Number = 1;
			private var min 		: Number = 0;
			private var max 		: Number = 1;
			
			
			public function Guage()
			{
				super(UILayoutManager.getInstance().createUI("com.cell.ui.component.Guage", this));
				this.ui_strip = UILayoutManager.getInstance().createUI("com.cell.ui.component.Guage.strip", this);
				this.addChild(ui_strip);
			}
			
			public function getValue() : Number
			{
				return value;
			}
			
			public function setValue(v:Number, len:Number) : void
			{
				v = Math.max(min, v);
				v = Math.min(max, v);
				if (this.value != v || this.range != len) {
					this.value = v;
					this.range = len;
					reset();
				}
			}
			
			public function getMax() : Number
			{
				return max;
			}
			
			public function getMin() : Number
			{
				return min;
			}
			
			public function setRange(min:Number, max:Number) : void
			{			
				max = Math.max(min, max);
				min = Math.min(min, max);
				if (this.max != max || this.min != min) {
					this.max = max;
					this.min = min;
					reset();
				}
			}
			
			override protected function resize(w:int, h:int, flush:Boolean):Boolean
			{
				if (super.resize(w, h, flush)) {
					reset();
					return true;
				}
				return false;
			}
			
			public function isValueIn() : Boolean
			{
				var tlen : Number = max - min;
				return (tlen > range);
			}
			
			
			public function reset() : void
			{
				var tlen : Number = max - min;
				if (tlen <= range) {
					ui_strip.visible = false;
				} else {
					var tval : Number = (value-min);
					ui_strip.x = tval/tlen*width;
					var tw : int = (range/tlen*width);
					if (tw <= 0) {
						ui_strip.visible = false;
					} else {
						ui_strip.visible = true;
						ui_strip.createBuffer(tw, ui_strip.height);
					}
				}
			}
		}
}
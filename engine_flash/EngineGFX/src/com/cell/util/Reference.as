package com.cell.util
{
	public class Reference
	{
		private var ref : Object;
		
		public function Reference(obj : Object = null) {
			this.ref = ref;
		}
		
		public function get() : Object {
			return ref;
		}
		
		public function set(obj : Object) : void {
			this.ref = ref;
		}
	}
}
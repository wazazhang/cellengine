package com.cell.game.ai.astar
{
	import flashx.textLayout.operations.FlowOperation;
	import flashx.textLayout.operations.UndoOperation;

	public class WayPointIterator
	{
		var wp:WayPoint ;
		
		public function WayPointIterator(wp:WayPoint) {
			this.wp = wp;
		}

		public function hasNext():Boolean {
			return wp != null;
		}

		public function next():WayPoint {
			var ret:WayPoint = wp;
			wp = wp.next;
			return ret;
		}

		public function remove():void {
//			throw new UnsupportedOperationException("remove()");
			throw new UndoOperation(null);
		}
	}
}
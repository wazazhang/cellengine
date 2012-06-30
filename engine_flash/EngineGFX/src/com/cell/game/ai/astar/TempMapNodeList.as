package com.cell.game.ai.astar
{
	final public class TempMapNodeList
	{
		public var list:Array;
		public var actives:Vector.<TempMapNode>;
		
		public function TempMapNodeList(size:int) {
			this.list 		= new Array(size);
			this.actives	= new Vector.<TempMapNode>(size);
		}
		
		public function add(node:TempMapNode):void {
//			actives.add(node);
			actives.push(node);
			list[node.node_index] = node;
		}
		
		public function remove(node:TempMapNode):void {
			actives.splice(actives.indexOf(node), 1); 
			list[node.node_index] = null;
		}
		
		public function clear():void {
			actives.splice(0,actives.length);		       //清除数组中的元素
//			Arrays.fill(list, 0, list.length, null);
			actives.splice(0,list.length);	
		}
		
		public function contains(node:TempMapNode):Boolean {
			return list[node.node_index] != null;
		}
		
		public function isEmpty():Boolean {
			return actives.length>0?true:false;
		}
		
		public function getMinF():TempMapNode{
			var min :int = int.MAX_VALUE;
			var ret:TempMapNode = null;
			
			for each(var a in actives) {
				var v:int = a.s_f;
				if (min > v) {
					ret = a;
					min = v;
				}
			}
			return ret;
		}
	}
}
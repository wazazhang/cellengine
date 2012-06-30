package com.cell.game.ai.astar
{
	import com.cell.util.Map;
	
	import flash.utils.Dictionary;

	public class AbstractAstar
	{
		
		private var map:AbstractAstarMap;
		
		public var all_node:Vector.<TempMapNode>;        //TempMapNode数组的转换
		private var src_open_list:TempMapNodeList;
		private var src_close_list:TempMapNodeList;
		
//		protected HashMap<AbstractAstarMapNode, TempMapNode> 
//			node_map;
		public var node_map:Map;                   //haspMap的转换
		public function AbstractAstar(map:AbstractAstarMap)
		{
			this.map			= map;
			
			this.all_node		= new Vector.<TempMapNode>(map.getNodeCount());
			this.src_open_list	= new TempMapNodeList(all_node.length);
			this.src_close_list	= new TempMapNodeList(all_node.length);
			this.node_map 		= new Map();                             // 为传入参数all_node.length
			
//			var  i :int= 0;
//			for (AbstractAstarMapNode node : map.getAllNodes()) {
//				all_node[i] = new TempMapNode(i, node);
//				node_map.put(node, all_node[i]);
//				i++;
//			}
			for(var i:int =0;i<map.getAllNodes().length;i++)
			{
				all_node[i] = new TempMapNode(i, (map.getAllNodes()[i] as AbstractAstarMapNode));
				node_map.push((map.getAllNodes()[i] as AbstractAstarMapNode), all_node[i]);
				i++;
			}
//			for (TempMapNode tnode : all_node) {
//				int j = 0;
//				for (AbstractAstarMapNode next : tnode.data.getNexts()) {
//					TempMapNode tnext = node_map.get(next);
//					tnode.nexts[j] = tnext;
//					j++;
//				}
//			}
			for each(var tnode:TempMapNode in all_node){		
				var j:int = 0;
				var d :TempMapNode= tnode as TempMapNode;
				for each(var next :AbstractAstarMapNode in d.data.getNexts()){	
					var tnext:TempMapNode = node_map[next];					
					tnode.nexts[j] = tnext;
					j++;
				}
			}
		}
		public function getMap():AbstractAstarMap {
			return this.map;
		}
		
		public function findPath(src_node:AbstractAstarMapNode,dst_node:AbstractAstarMapNode):Vector.<WayPoint>{
			return findPath_Tmn(getTempMapNode(src_node), getTempMapNode(dst_node));
		}
		
		/**
		 * 该方法可以被重构用来快速找到指定的TempMapNode
		 * @param node
		 * @return
		 */
		protected function getTempMapNode(node:AbstractAstarMapNode):TempMapNode {
			return node_map.get(node);
		}
		
		
		final protected function findPath_Tmn(src_node:TempMapNode, dst_node:TempMapNode): Vector.<WayPoint>
		{
			 var head:WayPoint = new WayPoint(src_node.data);
			
			if (src_node.data==dst_node.data) {
				return head.toVector();
			}
			
			src_open_list.clear();
			src_close_list.clear();
			
			src_node.setFather(src_node, dst_node);
			src_open_list.add(src_node);
			
			do{
				// search min F
				var cur_node:TempMapNode = src_open_list.getMinF();
				{
					// put the min F to closed
					src_open_list.remove(cur_node);
					src_close_list.add(cur_node);
					
					// find next node
					for each(var near in cur_node.nexts)
					{
						if (!near.data.testCross(cur_node.data)) {
							continue;
						}
						// ignore what if the block can not across or already in close table
						if (src_close_list.contains(near)) {
							continue;
						}
						// push and if is not in open table
						if (!src_open_list.contains(near)) {
							src_open_list.add(near);
							near.setFather(cur_node, dst_node);
						}
					}
				}
				
				// stop when :
				{
					// 1. dst node already in close list
					if (cur_node.data==dst_node.data)
					{
						// finded the path
						var end:WayPoint = null;
						
						for (var i:int = all_node.length - 1; i >= 0; i--) {
							// linked to head
							if( cur_node.data==src_node.data || cur_node.s_father == cur_node) {
								head.next = end;
								break;
							}else{
								var next:WayPoint = new WayPoint(cur_node.data);
								next.next = end;
								end = next;
							}
							cur_node = cur_node.s_father;
						}
						break;
					}
					// 2. open list is empty
					if(src_open_list.isEmpty()){
						// not find the path
						break;
					}
				}
			}while(true);
			
			return head.toVector();
		}
	}
}

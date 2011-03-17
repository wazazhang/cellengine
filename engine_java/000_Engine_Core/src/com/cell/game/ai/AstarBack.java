
package com.cell.game.ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class AstarBack
{
	public static interface AstarMap
	{
		public int getXCount() ;

		public int getYCount() ;

		public int getCellW() ;

		public int getCellH() ;
		
		public int getFlag(int bx, int by);
	}
	
	public static class WayPoint implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		public int BX;
		public int BY;
		public int X; 
		public int Y;
		
		public WayPoint	Next;
		
		transient	public Object	Data;
		
		public WayPoint(int bx, int by, int cellw, int cellh)
		{
			BX = bx;
			BY = by;
			X = BX*cellw + cellw/2;
			Y = BY*cellh + cellh/2;
		}
		
		public WayPoint(int x, int y)
		{
			X = x;
			Y = y;
		}
	}
	
	public class MapNode 
	{
		public short X;
		public short Y;
		public short Z;
		
		public MapNode sFather;
		public int sG = 0;
		public int sH = 0;
		public int sF = 0;

		public MapNode(int x,int y){
			X = (short)x;
			Y = (short)y;
		}
		
		public void putG(int bG, MapNode father, MapNode target)
		{
			this.sFather 	= father ;
			this.sG 		= bG;
			this.sH 		= (Math.abs(X - target.X) + Math.abs(Y - target.Y));
			this.sF 		= (sG + sH) ;
		}
	}
	
	AstarMap Map ;
	           
	MapNode AllNode[][] ;

	ArrayList<MapNode> srcOpenList  ;
	ArrayList<MapNode> srcCloseList ;
	
	final int[][] near = new int[][]{
			{-1,-1}, { 0,-1}, { 1,-1},
			{-1, 0}, /*{0,0}*/{ 1, 0},
			{-1, 1}, { 0, 1}, { 1, 1}
		};
	
	public AstarBack(AstarMap map)
	{
		Map = map;
		
		AllNode = new MapNode[Map.getYCount()][Map.getXCount()];
		for(int y=0;y<AllNode.length;y++){
			for(int x=0;x<AllNode[y].length;x++){
				AllNode[y][x] = new MapNode(x,y);
			}
		}
		
		srcOpenList  = new ArrayList<MapNode>(Map.getXCount()*Map.getYCount());
		srcCloseList = new ArrayList<MapNode>(Map.getXCount()*Map.getYCount());
	}
	
	synchronized public WayPoint findPath(int sx,int sy, int dx,int dy)
	{
		WayPoint head = new WayPoint(sx, sy, Map.getCellW(), Map.getCellH());
		
		srcOpenList.clear();
		srcCloseList.clear();
		
		if(sx == dx && sy == dy ) return head;
		if(Map.getFlag(dx, dy)!=0) return head;
		
		MapNode srcNode = AllNode[sy][sx];
		MapNode dstNode = AllNode[dy][dx];
		
		srcNode.putG(0, srcNode, dstNode);
		
//		srcNode.sG = 0;
//		srcNode.sH = (Math.abs(srcNode.X - dx) + Math.abs(srcNode.Y - dy));
//		srcNode.sF = (srcNode.sG + srcNode.sH) ;
//		srcNode.sFather = srcNode;
		srcOpenList.add(srcNode);

		// near 8 blocks
		int ndx = 0;
		int ndy = 0;
		do{
			try {
				// -----------------------------------------------------------------------------------------------
				// search min F
				MapNode curSrcNode = getMinF(srcOpenList);
				
				// put the min F to closed
				srcOpenList.remove(curSrcNode);
				srcCloseList.add(curSrcNode);
				
				// find near 8 blocks
				for(int i=0;i<near.length;i++)
				{
					ndx = near[i][0];
					ndy = near[i][1];
					
					if( curSrcNode.Y+ndy < 0 || curSrcNode.Y+ndy > Map.getYCount()-1 || //
						curSrcNode.X+ndx < 0 || curSrcNode.X+ndx > Map.getXCount()-1 ){ //
						continue;
					}
					MapNode block = AllNode[curSrcNode.Y + ndy][curSrcNode.X + ndx];
					
					// target is block
					if (Map.getFlag(block.X, block.Y) != 0) {
						continue;
					}
					// compute the F = G + H + cur.G
					int bG = curSrcNode.sG;
					if( ndx!=0 && ndy!=0 ){//
						bG += 14;
						try{
							// target is block
							if( Map.getFlag(curSrcNode.X, curSrcNode.Y+ndy)!=0 || //
								Map.getFlag(curSrcNode.X+ndx, curSrcNode.Y)!=0 ){ //
								continue;
							}
						}catch(Exception err){
							continue;
						}
					}else{
						bG += 10;
					}

					// ignore what if the block can not across or already in close table
					if( srcCloseList.contains(block)){
						continue;
					}
					
					// push and if is not in open table
					if(!srcOpenList.contains(block)){
						srcOpenList.add(block);
						block.putG(bG, curSrcNode, dstNode);
//						block.sFather = curSrcNode ;
//						block.sG = bG;
//						block.sH = (Math.abs(block.X - dx) + Math.abs(block.Y - dy));
//						block.sF = (block.sG + block.sH) ;
					}
					// check it better with org G if is in open table
					else if(bG<curSrcNode.sG){
						block.putG(bG, curSrcNode, dstNode);
//						block.sFather = curSrcNode ;
//						block.sG = bG;
//						block.sH = (Math.abs(block.X - dx) + Math.abs(block.Y - dy));
//						block.sF = (block.sG + block.sH) ;
					}	
				}
				
				// stop when :
				// 1. dst node already in close list
				// 2. open list is empty
				if( curSrcNode.X == dx && curSrcNode.Y == dy ){ //
					//TODO find the path
					WayPoint end = new WayPoint(curSrcNode.X, curSrcNode.Y, Map.getCellW(), Map.getCellH());
					for(int i=0;i<Map.getYCount()*Map.getXCount();i++){
						//println(" X=" + dstNode.X + " Y=" + dstNode.Y);
						if( curSrcNode.X==sx && curSrcNode.Y==sy || curSrcNode.sFather == curSrcNode) {
//							end.linkFrom(head);
							head.Next = end;
							break;
						}else{
							WayPoint next = new WayPoint(curSrcNode.X, curSrcNode.Y, Map.getCellW(), Map.getCellH());
//							end.linkFrom(next);
							next.Next = end;
							end = next;
						}
						curSrcNode = curSrcNode.sFather;
					}
					break;
				}				
				if(srcOpenList.isEmpty()){
					break;
				}

			} catch(Exception err) {
				err.printStackTrace();
				break;
			}
		}while(true);

		return head;
	}

	private MapNode getMinF(ArrayList<MapNode> list){
		int min = Integer.MAX_VALUE;
		MapNode ret = null;
		for(int i=0;i<list.size();i++){
			int v = list.get(i).sF;
			if(min>v){
				ret = list.get(i);
				min = v;
			}
		}
		return ret;
	}
	
//	private void putMinF(ArrayList<MapNode> list){
//		int min = Integer.MAX_VALUE;
//		MapNode ret = null;
//		for(int i=0;i<list.size();i++){
//			int v = (list.get(i)).sF;
//			if(min>v){
//				ret = (list.get(i));
//				min = v;
//			}
//		}
//	}
}






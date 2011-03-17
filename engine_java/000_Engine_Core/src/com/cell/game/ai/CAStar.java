
package com.cell.game.ai;

import java.util.Vector;

import com.cell.CObject;
import com.cell.game.CMap;
import com.cell.game.CWayPoint;


class MapNode {
	
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

}

public class CAStar extends CObject {
	
	CMap Map ;
	MapNode AllNode[][] ;

	Vector srcOpenList  = new Vector();
	Vector srcCloseList = new Vector();

	
	public CAStar(CMap map){
		Map = map;
		
		AllNode = new MapNode[Map.getYCount()][Map.getXCount()];
		for(int y=0;y<AllNode.length;y++){
			for(int x=0;x<AllNode[y].length;x++){
				AllNode[y][x] = new MapNode(x,y);
			}
		}
	}
	
	public CWayPoint findPath(int sx,int sy,int dx,int dy){

		CWayPoint head = new CWayPoint(sx*Map.getCellW(),sy*Map.getCellH());
		
		if(sx == dx && sy == dy ) return head;
		if(Map.getFlag(dx, dy)!=0) return head;
		
		srcOpenList.removeAllElements();
		srcCloseList.removeAllElements();
		MapNode srcNode = AllNode[sy][sx];
		srcNode.sG = 0;
		srcNode.sH = (Math.abs(srcNode.X - dx) + Math.abs(srcNode.Y - dy));
		srcNode.sF = (srcNode.sG + srcNode.sH) ;
		srcNode.sFather = srcNode;
		srcOpenList.addElement(srcNode);

		// near 8 blocks
		final int[][] near = new int[][]{
			{-1,-1}, { 0,-1}, { 1,-1},
			{-1, 0}, /*{0,0}*/{ 1, 0},
			{-1, 1}, { 0, 1}, { 1, 1}
		};
		do{
			try {

				// -----------------------------------------------------------------------------------------------
//				
				// src to dst
				
				// search min F
				MapNode curSrcNode = getMinF(srcOpenList);
				
				// put the min F to closed
				srcOpenList.removeElement(curSrcNode);
				srcCloseList.addElement(curSrcNode);
				
				// find near 8 blocks
				for(int i=0;i<near.length;i++){
					if( curSrcNode.Y+near[i][1] < 0 || curSrcNode.Y+near[i][1] > Map.getYCount()-1 || //
						curSrcNode.X+near[i][0] < 0 || curSrcNode.X+near[i][0] > Map.getXCount()-1 ){ //
						continue;
					}
					MapNode block = AllNode[curSrcNode.Y+near[i][1]][curSrcNode.X+near[i][0]];
					
					// ignore what if the block can not across or already in close table
					if( srcCloseList.contains(block)){
						continue;
					}
					if( Map.getFlag(block.X,block.Y)!=0 ){
						continue;
					}
					if( near[i][0]!=0 && near[i][1]!=0 ){//
						try{
							if( Map.getFlag(curSrcNode.X, curSrcNode.Y+near[i][1])!=0 || //
								Map.getFlag(curSrcNode.X+near[i][0], curSrcNode.Y)!=0 ){ //
								continue;
							}
						}catch(Exception err){
							continue;
						}
					}
					
					// compute the F = G + H + cur.G
					int bG = curSrcNode.sG;
					if(near[i][0]!=0 && near[i][1]!=0){
						bG += 14;
					}else{
						bG += 10;
					}
					// push and if is not in open table
					if(!srcOpenList.contains(block)){
						srcOpenList.addElement(block);
						block.sFather = curSrcNode ;
						block.sG = bG;
						block.sH = (Math.abs(block.X - dx) + Math.abs(block.Y - dy));
						block.sF = (block.sG + block.sH) ;
					}
					// check it better with org G if is in open table
					else if(bG<curSrcNode.sG){
						block.sFather = curSrcNode ;
						block.sG = bG;
						block.sH = (Math.abs(block.X - dx) + Math.abs(block.Y - dy));
						block.sF = (block.sG + block.sH) ;
					}	
				}
				
				// stop when :
				// 1. dst node already in close list
				// 2. open list is empty
				if( curSrcNode.X == dx && curSrcNode.Y == dy ){ //
					//TODO find the path
					CWayPoint end = new CWayPoint(
							curSrcNode.X*Map.getCellW(),
							curSrcNode.Y*Map.getCellH());
					for(int i=0;i<Map.getYCount()*Map.getXCount();i++){
						//println(" X=" + dstNode.X + " Y=" + dstNode.Y);
						if( curSrcNode.X==sx && curSrcNode.Y==sy || curSrcNode.sFather == curSrcNode) {
							end.linkFrom(head);
							break;
						}else{
							CWayPoint next = new CWayPoint(
									curSrcNode.X*Map.getCellW(),
									curSrcNode.Y*Map.getCellH());
							end.linkFrom(next);
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

	public MapNode getMinF(Vector list){
		int min = Integer.MAX_VALUE;
		MapNode ret = null;
		for(int i=0;i<list.size();i++){
			int v = ((MapNode)(list.elementAt(i))).sF;
			if(min>v){
				ret = ((MapNode)(list.elementAt(i)));
				min = v;
			}
		}
		return ret;
	}
	
	public void putMinF(Vector list){
		int min = Integer.MAX_VALUE;
		MapNode ret = null;
		for(int i=0;i<list.size();i++){
			int v = ((MapNode)(list.elementAt(i))).sF;
			if(min>v){
				ret = ((MapNode)(list.elementAt(i)));
				min = v;
			}
		}
	}
}







package com.cell.game.ai.pathfind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * 实现了曼哈顿算法的A*寻路算法
 * @author WAZA
 */
public class AstarManhattan extends AbstractAstar
{
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

//	----------------------------------------------------------------------------------------------------
	
	final static int[][] near_table = new int[][]{
			{-1,-1}, { 0,-1}, { 1,-1},
			{-1, 0}, /*{0,0}*/{ 1, 0},
			{-1, 1}, { 0, 1}, { 1, 1}
		};
	
	static class MMap implements AbstractAstarMap<MMapNode>
	{ 
		final AstarManhattanMap	map;
		final int		xcount;
		final int		ycount;
		
		final boolean 	is_static;
		final boolean	is_edge;
		
		
		final ArrayList<MMapNode> 
		
						all_nodes;

		final MMapNode
						all_nodes_map[][];
		
		public MMap(AstarManhattanMap map, boolean is_static, boolean is_edge) 
		{
			this.map		= map;
			this.xcount		= map.getXCount();
			this.ycount		= map.getYCount();
			this.is_static	= is_static;
			this.is_edge	= is_edge;
			
			this.all_nodes 		= new ArrayList<MMapNode>(xcount*ycount);
			this.all_nodes_map 	= new MMapNode[ycount][xcount];
			for (int y = 0; y < map.getYCount(); y++) {
				for (int x = 0; x < map.getXCount(); x++) {
					MMapNode node = new MMapNode(this, x, y);
					all_nodes_map[y][x] = node;
					all_nodes.add(node);
				}
			}
		
			for (int y = 0; y < map.getYCount(); y++) {
				for (int x = 0; x < map.getXCount(); x++) {
					MMapNode node = all_nodes_map[y][x];
					for (int[] np : near_table) {
						try{
							int ndx = np[0];
							int ndy = np[1];
							MMapNode near = all_nodes_map[y+ndy][x+ndx];
							if (is_static) {
								if (near.static_flag != false) {
									continue;
								}
								if (is_edge && ndx!=0 && ndy!=0) {
									MMapNode ta	= all_nodes_map[y][x+ndx];
									MMapNode tb = all_nodes_map[y+ndy][x];
									if (ta.static_flag!=false || tb.static_flag!=false ) {
										continue;
									}
								}
							}
							node.nexts.add(near);
						}catch(Exception err){}
					}
				}
			}
		}
		
		@Override
		public boolean containsNode(MMapNode node) {
			if (node.Y < ycount && node.Y >= 0) {
				if (node.X < xcount && node.X >= 0) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		public Collection<MMapNode> getAllNodes() {
			return all_nodes;
		}
		
		@Override
		public int getNodeCount() {
			return all_nodes.size();
		}
	}

//	----------------------------------------------------------------------------------------------------
	
	static class MMapNode implements AbstractAstarMapNode
	{
		MMap 		mmap;
		short 		X;
		short 		Y;
		boolean		static_flag;
		
		transient ArrayList<AbstractAstarMapNode> nexts = new ArrayList<AbstractAstarMapNode>(8);
		
		public MMapNode(MMap map, int x, int y)
		{
			this.mmap			= map;
			this.X 				= (short)x;
			this.Y 				= (short)y;
			this.static_flag	= mmap.map.getFlag(X, Y);
		}
		
		@Override
		public Collection<AbstractAstarMapNode> getNexts() {
			return nexts;
		}
		
		@Override
		public boolean testCross(AbstractAstarMapNode father) {
			if (mmap.is_static) {
				return static_flag == false;
			} else {
				if (mmap.map.getFlag(X, Y) != false) {
					return false;
				}
				if (mmap.is_edge) {
					MMapNode t = (MMapNode)father;
					int ndx = X - t.X;
					int ndy = Y - t.Y;
					if (ndx!=0 && ndy!=0) {
						MMapNode ta	= mmap.all_nodes_map[Y][t.X];
						MMapNode tb = mmap.all_nodes_map[t.Y][X];
						if (ta.static_flag!=false || tb.static_flag!=false ) {
							return false;
						}
					}
				}
				return true;
			}
		}
		
		@Override
		public int getDistance(AbstractAstarMapNode target) {
			MMapNode t = (MMapNode)target;
			int ndx = X - t.X;
			int ndy = Y - t.Y;
			return (Math.abs(ndx) + Math.abs(ndy));
		}
		
		@Override
		public int getPriority(AbstractAstarMapNode target) {
			MMapNode t = (MMapNode) target;
			if (X != t.X && Y != t.Y) {
				return 14;
			}
			return 10;
		}
	}

//	----------------------------------------------------------------------------------------------------
	
	TempMapNode[][] 	node_map;
	
	MMap 				mmap ;
	
	/**
	 * 创建一个以曼哈顿算法的A*寻路算法
	 * @param map		地图适配器
	 * @param is_static	是否是静态地图，即在运行过程中不会改变的
	 * @param is_edge	是否要检测斜边可以移动
	 */
	public AstarManhattan(AstarManhattanMap map, boolean is_static, boolean is_edge)
	{
		super(new MMap(map, is_static, is_edge));
		
		this.mmap = (MMap)getMap();
		
		this.node_map = new TempMapNode[mmap.ycount][mmap.xcount];
		
		for (TempMapNode tnode : all_node) {
			MMapNode mnode = (MMapNode)tnode.data;
			node_map[mnode.Y][mnode.X] = tnode;
		}
	}

	@Override
	protected TempMapNode getTempMapNode(AbstractAstarMapNode node) {
		MMapNode mnode = (MMapNode)node;
		return node_map[mnode.Y][mnode.X];
	}
	
	private WayPoint toWP(com.cell.game.ai.pathfind.AbstractAstar.WayPoint start)
	{
		WayPoint ret = null;
		WayPoint path = null;
		for (com.cell.game.ai.pathfind.AbstractAstar.WayPoint wp : start) {
			MMapNode mnpde = (MMapNode)wp.map_node;
//			System.out.println(mnpde.X+","+mnpde.Y);
			if (path != null) {
				path.Next = new WayPoint(mnpde.X, mnpde.Y, mmap.map.getCellW(), mmap.map.getCellH());
				path = path.Next;
			} else {
				path = new WayPoint(mnpde.X, mnpde.Y, mmap.map.getCellW(), mmap.map.getCellH());
				ret = path;
			}
		}
		return ret;
	}

	public WayPoint findPath(int sx, int sy, int dx, int dy) 
	{
		try{
			com.cell.game.ai.pathfind.AbstractAstar.WayPoint wp = super.findPath(
					node_map[sy][sx], 
					node_map[dy][dx]);
			return toWP(wp);
		}catch(Throwable err) {
			err.printStackTrace();
		}
		return null;
	}

//	@Override
//	protected com.cell.game.ai.pathfind.AbstractAstar.WayPoint findPath(MMapNode srcNode, MMapNode dstNode) {
//		try{
//			MMapNode ss = (MMapNode)srcNode;
//			MMapNode ds = (MMapNode)dstNode;
//			com.cell.game.ai.pathfind.AbstractAstar.WayPoint wp = super.findPath(
//					node_map[ss.Y][ss.X], 
//					node_map[ds.Y][ds.X]);
//			return wp;
//		}catch(Throwable err) {
//			err.printStackTrace();
//		}
//		return null;
//	}
}






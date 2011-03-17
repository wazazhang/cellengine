package com.cell.game.ai.pathfind;

import java.util.Collection;


/**
 * 抽象的地图，所有寻路操作都在该类中完成
 * @author WAZA
 */
public interface AbstractAstarMap<T extends AbstractAstarMapNode>
{
	/**
	 * 测试是否包含该节点
	 * @param node
	 * @return
	 */
	public boolean 			containsNode(T node);

	/**
	 * 得到所有的节点
	 * @return
	 */
	public Collection<T>	getAllNodes();

	/**
	 * 得到节点数量
	 * @return
	 */
	public int 				getNodeCount();
}


package com.cell.game.ai.pathfind;

import java.util.Collection;

/**
 * 抽象的地图节点
 * @author WAZA
 */
public interface AbstractAstarMapNode
{
	/**
	 * 得到所有通向的下一个节点
	 * 
	 * @return
	 */
	public Collection<AbstractAstarMapNode> getNexts();

	/**
	 * 测试是否可以过
	 * 
	 * @param father
	 *            父节点
	 * @return
	 */
	public boolean testCross(AbstractAstarMapNode father);

	/**
	 * 得到和目标节点的距离
	 * 
	 * @param target
	 * @return
	 */
	public int getDistance(AbstractAstarMapNode target);

	/**
	 * 得到和目标节点的优先链接值
	 * @param target
	 * @return
	 */
	public int getPriority(AbstractAstarMapNode target);
}

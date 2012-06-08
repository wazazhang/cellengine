
#ifndef _MF_PATH_FINDER
#define _MF_PATH_FINDER

#include "MFType.h"
#include "MFGraphics2D.h"
#include "MFGameObject.h"
#include <vector>

namespace mf
{
	using namespace std;

	////////////////////////////////////////////////////////////////////////////////////
	/**
	* 抽象的地图节点
	* @author WAZA
	*/
	class CC_DLL  AbstractAstarMapNode
	{
	public:
		
		virtual ~AbstractAstarMapNode();
		
		/**
		* 得到所有通向的下一个节点 
		* @return
		*/
		virtual list<AbstractAstarMapNode*> getNexts() = 0;

		/**
		* 测试是否可以过
		* @param father 父节点
		* @return
		*/
		virtual boolean testCross(AbstractAstarMapNode* father) = 0;

		/**
		* 得到和目标节点的距离 
		* @param target
		* @return
		*/
		virtual int getDistance(AbstractAstarMapNode* target) = 0;

		/**
		* 得到和目标节点的优先链接值
		* @param target
		* @return
		*/
		virtual int getPriority(AbstractAstarMapNode* target) = 0;
	}


	////////////////////////////////////////////////////////////////////////////////////
	/**
	* 抽象的地图，所有寻路操作都在该类中完成
	* @author WAZA
	*/
	template <typename T>
	class CC_DLL  AbstractAstarMap
	{
	public:
		virtual ~AbstractAstarMap();
		/**
		* 测试是否包含该节点
		* @param node
		* @return
		*/
		virtual boolean containsNode(T *node) = 0;

		/**
		* 得到所有的节点
		* @return
		*/
		virtual vector<T*> getAllNodes() = 0;

		/**
		* 得到节点数量
		* @return
		*/
		virtual int getNodeCount() = 0;
	}

	////////////////////////////////////////////////////////////////////////////////////
	/**
	* 抽象的A*寻路算法，子类可以自定义实现如何寻路。
	* @author WAZA
	*/
	class CC_DLL  AbstractAstar
	{
	private:
		AbstractAstarMap	*map;

		TempMapNode[]		all_node;
		TempMapNodeList		src_open_list;
		TempMapNodeList		src_close_list;

		HashMap<AbstractAstarMapNode, TempMapNode> node_map;

	public:

		AbstractAstar(AbstractAstarMap<?> map)
		{
			this.map			= map;

			this.all_node		= new TempMapNode[map.getNodeCount()];
			this.src_open_list	= new TempMapNodeList(all_node.length);
			this.src_close_list	= new TempMapNodeList(all_node.length);
			this.node_map 		= new HashMap<AbstractAstarMapNode, TempMapNode>(all_node.length);

			int i = 0;
			for (AbstractAstarMapNode node : map.getAllNodes()) {
				all_node[i] = new TempMapNode(i, node);
				node_map.put(node, all_node[i]);
				i++;
			}

			for (TempMapNode tnode : all_node) {
				int j = 0;
				for (AbstractAstarMapNode next : tnode.data.getNexts()) {
					TempMapNode tnext = node_map.get(next);
					tnode.nexts[j] = tnext;
					j++;
				}
			}
		}

		public AbstractAstarMap<?> getMap() {
			return this.map;
		}

		public WayPoint findPath(AbstractAstarMapNode src_node, AbstractAstarMapNode dst_node) throws Exception {
			return findPath(getTempMapNode(src_node), getTempMapNode(dst_node));
		}

		/**
		* 该方法可以被重构用来快速找到指定的TempMapNode
		* @param node
		* @return
		*/
		protected TempMapNode getTempMapNode(AbstractAstarMapNode node) {
			return node_map.get(node);
		}

		//	-----------------------------------------------------------------------------------------------------------------

		synchronized
			final protected WayPoint findPath(TempMapNode src_node, TempMapNode dst_node) throws Exception
		{
			WayPoint head = new WayPoint(src_node.data);

			if (src_node.data.equals(dst_node.data)) {
				return head;
			}

			src_open_list.clear();
			src_close_list.clear();

			src_node.setFather(src_node, dst_node);
			src_open_list.add(src_node);

			do{
				// search min F
				TempMapNode cur_node = src_open_list.getMinF();
				{
					// put the min F to closed
					src_open_list.remove(cur_node);
					src_close_list.add(cur_node);

					// find next node
					for (TempMapNode near : cur_node.nexts)
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
					if (cur_node.data.equals(dst_node.data))
					{
						// finded the path
						WayPoint end = null;

						for (int i = all_node.length - 1; i >= 0; i--) {
							// linked to head
							if( cur_node.data.equals(src_node.data) || cur_node.s_father == cur_node) {
								head.next = end;
								break;
							}else{
								WayPoint next = new WayPoint(cur_node.data);
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

			return head;
		}

		//	-----------------------------------------------------------------------------------------------------------------


		//	-----------------------------------------------------------------------------------------------------------------

		class CC_DLL  TempMapNode
		{
		private:
			/**从all_node里快速索引*/
			int							node_index;

			/**对应的地图数据*/
			AbstractAstarMapNode*		data;

			/**对应的下一个节点*/
			vector<TempMapNode*>		nexts;

			/**每次寻路时，暂存的上一个节点*/
			TempMapNode*	s_father;
			int 			s_g = 0;
			int 			s_h = 0;
			int 			s_f = 0;

		private:

			TempMapNode(int node_index, AbstractAstarMapNode* data) {
				this.node_index	= node_index;
				this.data		= data;
				this.nexts		= new TempMapNode[data.getNexts().size()];
			}

			private void setFather(TempMapNode father, TempMapNode target)
			{
				this.s_father	= father;
				this.s_g 		= father.s_g + data.getPriority(father.data);
				this.s_h 		= data.getDistance(target.data);
				this.s_f 		= (s_g + s_h) ;
			}
		}

		//	-----------------------------------------------------------------------------------------------------------------

		class  CC_DLL TempMapNodeList
		{
			final protected TempMapNode[]			list;
			final protected ArrayList<TempMapNode>	actives;

			private TempMapNodeList(int size) {
				this.list 		= new TempMapNode[size];
				this.actives	= new ArrayList<TempMapNode>(size);
			}

			final private void add(TempMapNode node) {
				actives.add(node);
				list[node.node_index] = node;
			}

			final private void remove(TempMapNode node) {
				actives.remove(node);
				list[node.node_index] = null;
			}

			final private void clear() {
				actives.clear();
				Arrays.fill(list, 0, list.length, null);
			}

			final private boolean contains(TempMapNode node) {
				return list[node.node_index] != null;
				//			return actives.contains(node);
			}

			final private boolean isEmpty() {
				return actives.isEmpty();
			}

			final private TempMapNode getMinF(){
				int min = Integer.MAX_VALUE;
				TempMapNode ret = null;
				for(TempMapNode a : actives){
					int v = a.s_f;
					if (min > v) {
						ret = a;
						min = v;
					}
				}
				return ret;
			}

		}


		//	-----------------------------------------------------------------------------------------------------------------

		class  CC_DLL WayPoint implements Iterable<WayPoint>, Serializable
		{
			final public AbstractAstarMapNode map_node;

			private WayPoint next;

			protected WayPoint(AbstractAstarMapNode map_node) {
				this.map_node = map_node;
			}

			@Override
				public Iterator<WayPoint> iterator() {
					return new WayPointIterator(this);
			}

			public WayPoint getNext() {
				return next;
			}
		}

		class CC_DLL  WayPointIterator implements Iterator<WayPoint>
		{
			WayPoint wp ;

			public WayPointIterator(WayPoint wp) {
				this.wp = wp;
			}

			@Override
				public boolean hasNext() {
					return wp != null;
			}

			@Override
				public WayPoint next() {
					WayPoint ret = wp;
					wp = wp.next;
					return ret;
			}

			@Override
				public void remove() {
					throw new UnsupportedOperationException("remove()");
			}
		}
	}








};

#endif // #define _MF_PATH_FINDER

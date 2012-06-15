using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using System.Windows.Forms;

namespace CellGameEdit.PM.plugin
{
    /// 事件节点数据
	public class EventNode
	{
		public string name;     // 名字，在此插件中独一无二的名字，比如：技能-003002
        public string value;    // 该事件节点的数据
		public string iconKey;  // 该事件节点对应的图片
	}

    // 世界事件实体，编辑器生成后的事件
	public class WorldEvent
	{
        public EventNode source; // 事件节点数据
        public string mapId;
		public int x;   // 该事件在场景中的坐标
        public int y;   // 该事件在场景中的坐标
		public string appendData; // 附加数据
	}

    // 事件插件，一般以窗体形式实现
	public interface EventTemplatePlugin
	{
        void initPlugin(string workdir);

        // 获取类名
		string getClassName();

        // 获取图片集合
        ImageList getImageList();

        // 获取编辑窗口
		Form asForm();

        // 获得对应名字的事件实体
		EventNode getEvent(string name);

        // 获取当前选择的事件
		EventNode getSelectedEvent();

        // 存储世界事件实体
		void saveWorldEvents(List<WorldEvent> events);
	}
}

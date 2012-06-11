using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using System.Windows.Forms;

namespace CellGameEdit.PM.plugin
{
	public class EventNode
	{
		public string name;
		public string value;
		public System.Drawing.Bitmap icon;
		public List<EventNode> childs;
	}

	public class WorldEvent
	{
		public EventNode source;
		public int x;
		public int y;
		public string appendData;
	}

	public interface EventTemplatePlugin
	{
		string getClassName();

		Form asForm();

		EventNode getEvent(string name);

		EventNode getSelectedEvent();

		void saveWorldEvents(List<WorldEvent> events);
	}
}

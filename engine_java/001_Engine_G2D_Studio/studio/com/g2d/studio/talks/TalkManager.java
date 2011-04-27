package com.g2d.studio.talks;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormList;
import com.g2d.studio.ManagerFormTreeList;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.fileobj.FileObjectView;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;

public class TalkManager extends ManagerFormTreeList<TalkFile>
{
	private static final long serialVersionUID = 1L;
		
	public TalkManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "对话管理器", Res.icon_talk, 
				Studio.getInstance().root_talk_path,
				Studio.getInstance().project_save_path.getChildFile("talks/talks.list")
				);
	}
	
	public Vector<TalkFile> getTalks() {
		return super.getNodes();
	}
	
	public TalkFile getTalk(String talk_name) {
		return super.getNode(talk_name);
	}

	@Override
	protected FileObjectView<TalkFile> createList(File resRoot,
			File saveListFile, ProgressForm progress) {
		return new TalkList("对话管理器", progress, resRoot, saveListFile);
	}
	
	public class TalkList extends FileObjectView<TalkFile>
	{
		private static final long serialVersionUID = 1L;

		public TalkList(String title, ProgressForm progress, File resRoot, File saveListFile) {
			super(title, progress, resRoot, saveListFile);
		}
		
		@Override
		public TalkFile createItem(File file) {
			if (file.getName().endsWith(Config.TALK_SUFFIX)) {
				TalkFile n = new TalkFile(file);
				return n;
			}
			return null;
		}
	}
}

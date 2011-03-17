package com.g2d.studio.talks;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormList;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;

public class TalkManager extends ManagerFormList<TalkFile>
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
	protected G2DList<TalkFile> createList(Vector<TalkFile> files) {
		return new TalkList(files);
	}
	@Override
	protected String asNodeName(File file) {
		return file.getName().substring(0, file.getName().length() - Config.TALK_SUFFIX.length());
	}
	@Override
	protected TalkFile createNode(File file) {
		if (file.getName().endsWith(Config.TALK_SUFFIX)) {
			return new TalkFile(asNodeName(file), file);
		}
		return null;
	}
	
	@Override
	protected String getSaveListName(TalkFile node) {
		return node.getListName();
	}
	
	
	
}

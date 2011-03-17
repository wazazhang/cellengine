package com.g2d.studio.sound;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormList;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;

public class SoundManager extends ManagerFormList<SoundFile>
{
	private static final long serialVersionUID = 1L;
	
	com.cell.sound.SoundManager sound_system = Studio.getInstance().getSoundSystem();
	
	SoundList sound_list;
	
	JButton btn_play	= new JButton("播放");
	JButton btn_stop	= new JButton("停止");
	
	public SoundManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "声音编辑器", Res.icons_bar[3],
				Studio.getInstance().root_sound_path,
				Studio.getInstance().project_save_path.getChildFile("sounds/sound.list")
				);
		btn_play.addActionListener(this);
		btn_stop.addActionListener(this);
		tool_bar.addSeparator();
		tool_bar.add(btn_play);
		tool_bar.add(btn_stop);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_play) {
			sound_list.playSelected();
		} else if (e.getSource() == btn_stop) {
			sound_list.stopSelected();
		} else {
			super.actionPerformed(e);
		}
	}
	
	public Vector<SoundFile> getSounds() {
		return super.getNodes();
	}
	
	public SoundFile getSound(String sound_name) {
		return super.getNode(sound_name);
	}
	
	@Override
	protected G2DList<SoundFile> createList(Vector<SoundFile> files) {
		sound_list = new SoundList(getSounds());
		return sound_list;
	}

	@Override
	protected String asNodeName(File file) {
		return file.getName().substring(0, file.getName().length() - Config.SOUND_SUFFIX.length());
	}
	
	@Override
	protected SoundFile createNode(File file) {
		if (file.getName().endsWith(Config.SOUND_SUFFIX)) {
			return new SoundFile(asNodeName(file));
		}
		return null;
	}
	
	@Override
	protected String getSaveListName(SoundFile node) {
		return node.getListName();
	}

	
}

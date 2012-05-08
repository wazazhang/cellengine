package com.g2d.studio.sound;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;

import com.cell.sound.util.StaticSoundPlayer;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormList;
import com.g2d.studio.ManagerFormTreeList;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.fileobj.FileObjectView;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.talks.TalkFile;

public class SoundManager extends ManagerFormTreeList<SoundFile>
{
	private static final long serialVersionUID = 1L;
	
	com.cell.sound.SoundManager sound_system = Studio.getInstance().getSoundSystem();
		
	JButton btn_play	= new JButton("播放");
	JButton btn_stop	= new JButton("停止");

	private StaticSoundPlayer playing_sound ;
	
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
			((SoundList)list).playSelected();
		} else if (e.getSource() == btn_stop) {
			((SoundList)list).stopSelected();
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
	protected FileObjectView<SoundFile> createList(File resRoot,
			File saveListFile, ProgressForm progress) {
		return new SoundList("声音编辑器", progress, resRoot, saveListFile);
	}
	
	public class SoundList extends FileObjectView<SoundFile>
	{
		private static final long serialVersionUID = 1L;

		public SoundList(String title, ProgressForm progress, File resRoot, File saveListFile) {
			super(title, progress, resRoot, saveListFile, StudioConfig.SOUND_SUFFIX);	
			getList().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (getSelectedItem() != null) {
							playSelected();
						}
					}
				}
			});
		}
		
		@Override
		public SoundFile createItem(File file) {
			if (file.getName().endsWith(StudioConfig.SOUND_SUFFIX)) {
				SoundFile n = new SoundFile(file);
				return n;
			}
			return null;
		}
		
		public SoundFile getSoundFile(String name) {
			ListModel model = getList().getModel();
			for (int i = 0; i < model.getSize(); i++) {
				SoundFile sound = (SoundFile) model.getElementAt(i);
				if (sound.getName().equals(name)) {
					return sound;
				}
			}
			return null;
		}

		public void playSelected() {
			if (playing_sound!=null) {
				playing_sound.dispose();
				playing_sound = null;
			}
			if (getSelectedItem()!=null) {
				playing_sound = getSelectedItem().createSoundPlayer();
				playing_sound.play(false);
			}
		}
		
		public void stopSelected() {
			if (playing_sound!=null) {
				playing_sound.dispose();
				playing_sound = null;
			}
		}
		

	}

}

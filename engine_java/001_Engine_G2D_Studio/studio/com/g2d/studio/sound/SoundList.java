package com.g2d.studio.sound;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ListModel;

import com.cell.sound.util.StaticSoundPlayer;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;

public class SoundList extends G2DList<SoundFile> implements MouseListener
{
	private static final long serialVersionUID = 1L;

	private StaticSoundPlayer playing_sound ;
	
	SoundList(Vector<SoundFile> sounds) {
		super(sounds);
		super.addMouseListener(this);
	}
	
	SoundList() {
		super(Studio.getInstance().getSoundManager().getSounds());
		super.addMouseListener(this);
	}
	
	public SoundFile getSoundFile(String name) {
		ListModel model = getModel();
		for (int i=0; i<model.getSize(); i++) {
			SoundFile sound = (SoundFile)model.getElementAt(i);
			if (sound.sound_file_name.equals(name)) {
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
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount()==2) {
			if (getSelectedItem()!=null) {
				 playSelected();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
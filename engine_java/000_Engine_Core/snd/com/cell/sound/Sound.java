package com.cell.sound;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;

import com.cell.CIO;

public class Sound 
{
//	---------------------------------------------------------------------------------------------------------------------------
	
	static ConcurrentHashMap<Sound, Sound> all_sounds = new ConcurrentHashMap<Sound, Sound>(); 
	
	public static void setAllMute(boolean mute) {
		for (Sound sound : all_sounds.values()) {
			sound.setMute(mute);
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	AudioInputStream	stream;
	Clip 				clip;
	AudioFormat 		format;
	
	public Sound(String file)
	{
		try {
			InputStream is = CIO.loadStream(file);
			if (is!=null) {
				init(is);
			}
		} catch (Exception e) {}
	}
	
	public Sound(InputStream is)
	{
		if (is!=null) {
			init(is);
		}
	}
	
	void init(InputStream is) 
	{
		try {
			stream = AudioSystem.getAudioInputStream(is);
			format = stream.getFormat();
			clip = AudioSystem.getClip();
			clip.open(stream);
			all_sounds.put(this, this);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (stream != null) {
					stream.close();
					stream = null;
				}
				if (clip != null) {
					clip.close();
					clip = null;
				}
			} catch (Exception e2) {
				e.printStackTrace();
			}
		}
	}
	
	public void setMute(boolean mute) {
		if (clip != null) {
			try {
				BooleanControl ctrl = (BooleanControl)clip.getControl(BooleanControl.Type.MUTE);
				ctrl.setValue(mute);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setPosition(long ms) {
		if (clip!=null) {
			clip.setMicrosecondPosition(ms);
		}
	}
	
	public long getPosition() {
		if (clip!=null) {
			return clip.getMicrosecondPosition();
		}
		return 0;
	}
	
	public long getLength() {
		if (clip!=null) {
			return clip.getMicrosecondLength();
		}
		return 0;
	}
	
	public void play() {
		if (clip!=null) {
			clip.setMicrosecondPosition(0);
			clip.start();
		}
	}
	
	public void play(int loopcount) {
		if (clip!=null) {
			clip.loop(loopcount);
		}
	}
	
	public void playAlwaysLoop() {
		if (clip!=null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void stop() {
		if (clip!=null) {
			clip.stop();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		try{
			System.out.println("finalize sound");
			clip.close();
			stream.close();
			clip = null;
			stream = null;
			all_sounds.remove(this);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

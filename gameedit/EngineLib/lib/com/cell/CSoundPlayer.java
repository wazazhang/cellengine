/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell;



import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.ToneControl;
import javax.microedition.media.control.VolumeControl;

public class CSoundPlayer extends CObject implements PlayerListener {

	static public boolean SoundEnable = true;
	
	/**
	 * Note data
	 */
	final static public byte C4 = ToneControl.C4; // C4
	final static public byte D4 = (byte) (C4 + 2); // D4
	final static public byte E4 = (byte) (C4 + 4); // E4
	final static public byte F4 = (byte) (C4 + 5); // F4
	final static public byte G4 = (byte) (C4 + 7); // G4
	final static public byte A4 = (byte) (C4 + 9); // A4
	final static public byte B4 = (byte) (C4 + 11);// B4
	final static public byte C4_H = (byte) (C4 + 1); // C#4
	final static public byte D4_H = (byte) (C4 + 3); // D#4
	final static public byte F4_H = (byte) (C4 + 6); // F#4
	final static public byte G4_H = (byte) (C4 + 8); // G#4
	final static public byte A4_H = (byte) (C4 + 10);// A#4

	/**
	 * Octachord data
	 */
	final static public byte[] OCTACHORD = { C4, D4, E4, F4, G4, A4, B4 };
	final static public byte[] OCTACHORD_FULL = { C4, C4_H, D4, D4_H, E4, F4,
			F4_H, G4, G4_H, A4, A4_H, B4 };

	/**
	 * Sample Music
	 */
	// "Mary Had A Little Lamb" has "ABAC" structure.
	// Use block to repeat "A" section.
	final static public byte tempo = 30; // set tempo to 120 bpm

	final static public byte d = 8; // eighth-note

	final static public byte rest = ToneControl.SILENCE; // rest

	final static public byte[] SampleMusic = { ToneControl.VERSION, 1, // version
																	   // 1
			ToneControl.TEMPO, tempo, // set tempo
			ToneControl.BLOCK_START, 0, // start define "A" section
			E4, d, D4, d, C4, d, E4, d, // content of "A" section
			E4, d, E4, d, E4, d, rest, d, ToneControl.BLOCK_END, 0, // end
																	// define
																	// "A"
																	// section
			ToneControl.PLAY_BLOCK, 0, // play "A" section
			D4, d, D4, d, D4, d, rest, d, // play "B" section
			E4, d, G4, d, G4, d, rest, d, ToneControl.PLAY_BLOCK, 0, // repeat
																	 // "A"
																	 // section
			D4, d, D4, d, E4, d, D4, d, C4, d // play "C" section
	};

	//	-------------------------------------------------------------------------------------------------------------------
	final static public String TYPE_WAV = "audio/x-wav";
	final static public String TYPE_AU = "audio/basic";
	final static public String TYPE_MP3 = "audio/mpeg";
	final static public String TYPE_MIDI = "audio/midi";
	final static public String TYPE_TONE_SEQ = "audio/x-tone-seq";

	//  -------------------------------------------------------------------------------------------------------------------

	final static public void playTone(byte note, int duration, int volume) {
		try {
			Manager.playTone(note, duration, volume);
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	---------------------------------------------------------------------------------------------------------------
	/**
	 *  
	 */
	private Player player;

	private VolumeControl vc;

	private ToneControl tc;

	/**
	 * 默认空构造类
	 */
	public CSoundPlayer() {
	}

	/**
	 * @param file
	 *            Resource file
	 * @param type
	 *            Wave audio files: audio/x-wav AU audio files: audio/basic MP3
	 *            audio files: audio/mpeg MIDI files: audio/midi Tone sequences:
	 *            audio/x-tone-seq
	 * @param loop
	 *            Loop Count
	 */
	public CSoundPlayer(String filename, String type, int loop) {
		try {
			if(SoundEnable){
				InputStream is = getClass().getResourceAsStream(filename);
				if (is == null) {
					println("Error Loading Sound File : " + filename);
				} else {
					player = Manager.createPlayer(is, type);
					player.addPlayerListener(this);
					player.stop();
					player.realize();
					player.prefetch();
					player.setLoopCount(loop);
					vc = (VolumeControl) player.getControl("VolumeControl");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param tone[]
	 *            tone = { ToneControl.VERSION, 1, // version 1
	 *            ToneControl.TEMPO, 30, // set tempo C4,8, //data start
	 *            {level,dot} C5,8 ... };
	 * @param loop
	 *            Loop Count
	 */
	public CSoundPlayer(byte[] tone, int loop) {
		try {
			if(SoundEnable){
			player = Manager.createPlayer(Manager.TONE_DEVICE_LOCATOR);
			player.addPlayerListener(this);
			player.stop();
			player.realize();
			//            player.prefetch();
			player.setLoopCount(loop);
			vc = (VolumeControl) player.getControl("VolumeControl");
			tc = (ToneControl) player.getControl("ToneControl");
			tc.setSequence(tone);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	------------------------------------------------------------------------------------------------------------------------------
	final public Player getPlayer() {
		return player;
	}

	final public void replay() {
		pause();
		try {
			if(SoundEnable){
			if (player != null && tc != null
					&& player.getState() == Player.REALIZED) {
				player.setMediaTime(-1);
				player.start();
			} else if (player != null && player.getState() == Player.PREFETCHED) {
				player.setMediaTime(-1);
				player.start();
			}
			}
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final public void play() {
		try {
			if(SoundEnable){
			if (player != null && tc != null
					&& player.getState() == Player.REALIZED) {
				//                player.setMediaTime(-1);
				player.start();
			} else if (player != null && player.getState() == Player.PREFETCHED) {
				//                player.setMediaTime(-1);
				player.start();
			}
			}
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//        pause();
		//        resume();
	}

	final public void pause() {
		try {
			if (player != null && player.getState() == Player.STARTED) {
				player.stop();
			}
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final public void resume() {
		try {
			if (player != null) {
				player.start();
			}
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final public void destroy() {
		try {
			if (player != null) {
				player.stop();
				player.close();
				player = null;
			}
			if (tc != null)
				tc = null;
			if (vc != null)
				vc = null;
			System.gc();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final public void setVolume(int level) {
		if (vc != null) {
			vc.setLevel(level);
		}
	}

	final public boolean isPlaying() {
		return (player != null && player.getState() == Player.STARTED);
	}

	final public void playerUpdate(Player player, String event, Object eventData) {

		if (event == VOLUME_CHANGED) {
			println("Volume Changed : " + vc.getLevel());
		}

	}

}
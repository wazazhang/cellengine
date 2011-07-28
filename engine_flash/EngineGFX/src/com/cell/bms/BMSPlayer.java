package com.cell.bms;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.cell.bms.BMSFile.HeadInfo;
import com.cell.bms.BMSFile.Note;
import com.cell.bms.BMSFile.NoteDefine;
import com.cell.math.Vector;
import com.cell.sound.SoundManager;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public class BMSPlayer
{
//	-------------------------------------------------------------------------------------------------
	
	final private BMSFile 	bms_file;
	
	final private ArrayList<BMSPlayerListener> 
							listeners	= new ArrayList<BMSPlayerListener>(1);
	
//	-------------------------------------------------------------------------------------------------
//	play refer
	
	/** 是否自动演奏 */
	public boolean			is_auto_play	= false;

	private ArrayList<Note>	play_tracks;
	private ArrayList<Note> play_removed;
	
	/** 丢弃Note单位的检查范围  */
	private double			play_drop_length;
	
	// dynamic
	private double 			play_bpm;
	private double			play_position;	
	private double			play_stop_time;
	private double			play_pre_beat_position;
	private int				play_beat_count;
	private double 			play_pre_record_time;
	
	
	private IDefineImage	play_bg_image;
	private IDefineImage	play_poor_image;
	private IDefineImage	play_layer_image;
	
//	-------------------------------------------------------------------------------------------------
//	game refer
	
	/** 基线 */
	Line2D			game_base_line;
	
	/** note 移动方向 */
	Point2D			geme_note_direct;
	
	double			game_speed		= 1;
	
//	-------------------------------------------------------------------------------------------------
	
	public BMSPlayer(BMSFile bms) 
	{
		bms_file 			= bms;
	}

	public BMSFile getBMSFile() {
		return bms_file;
	}
	
	public void addListener(BMSPlayerListener listener) {
		listeners.add(listener);
	}
	
	public BMSPlayerListener removeListener(BMSPlayerListener listener) {
		if (listeners.remove(listener)) {
			return listener;
		}
		return null;
	}
	
//	-------------------------------------------------------------------------------------------------
	
	synchronized public void start()
	{
		try{
			play_tracks				= bms_file.getAllNoteList();
			play_bpm				= bms_file.getHeadInfo(HeadInfo.BPM);
			play_drop_length		= bms_file.LINE_SPLIT_DIV;
			play_position			= 0;
			play_pre_beat_position	= 0;
			play_pre_record_time	= System.currentTimeMillis();
			play_stop_time			= 0;
			play_beat_count			= 0;
			play_removed			= new ArrayList<Note>(play_tracks.size());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	synchronized public void stop() 
	{
		try{			
			play_tracks = null;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	synchronized public void update()
	{
		if (play_tracks == null) {
			return;
		}
		
		try
		{
			double	cur_time		= System.currentTimeMillis();
			double	deta_time		= cur_time - play_pre_record_time; 
			play_pre_record_time	= cur_time;
			
			// 已缓冲的音符
			{
				ArrayList<Note> removed = new ArrayList<Note>();
				
				for (Note note : play_tracks) 
				{
					// 如果该音符过丢弃线
					if (note.getBeginPosition() <= play_position - play_drop_length) {
						removed.add(note);
						for (BMSPlayerListener listener : listeners) {
							listener.onDropNote(this, note);
						}
						continue;
					}
					// 如果该音符过线
					else if (note.getBeginPosition() <= play_position) {
						if (processSystemNote(note)) {
							// 如果是系统命令，则立即处理
							removed.add(note); 
							continue;
						}
						// 如果自动演奏，则提供一个命令
						else if (is_auto_play && processAutoHit(note)) {
							// 如果是按键命令，则立即处理
							removed.add(note); 
							continue;
						}
					}
					// 未到线的音符
					else {
						break;
					}
				}
				
				play_tracks.removeAll(removed);
				play_removed.addAll(removed);
			}
			
			if (play_stop_time>0) {
				play_stop_time	-= bms_file.timeToPosition(deta_time, play_bpm);
			} else {
				play_position	+= bms_file.timeToPosition(deta_time, play_bpm);
				if (play_position > play_pre_beat_position + bms_file.BEAT_DIV) {
					play_pre_beat_position = play_position;
					play_beat_count ++;
					for (BMSPlayerListener listener : listeners) {
						listener.onBeat(this, play_beat_count);
					}
				}
			}
			
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}

//	-------------------------------------------------------------------------------------------------

	private boolean processSystemNote(Note note) 
	{
		if (note.track<=9)
		{
			try{
				switch(note.command)
				{
				case BPM_CHANGE:
					play_bpm = Integer.parseInt(note.value, 16);
					System.out.println(note.command + " " + play_bpm);
					break;
					
				case INDEX_BPM:
					play_bpm = Double.parseDouble(note.note_define.value);
					System.out.println(note.command + " " + play_bpm);
					break;
	
				case INDEX_STOP:
					play_stop_time = Double.parseDouble(note.note_define.value);
					System.out.println(note.command + " " + play_stop_time);
					break;
					
				case INDEX_BMP_BG:
					play_bg_image		= (IDefineImage)note.note_define.value_object;
					break;
				case INDEX_BMP_LAYER:
					play_layer_image	= (IDefineImage)note.note_define.value_object;
					break;
				case INDEX_BMP_POOR:
					play_poor_image		= (IDefineImage)note.note_define.value_object;
					break;
					
				case INDEX_WAV_BG:
					IDefineSound sound	= (IDefineSound)note.note_define.value_object;
					sound.play();
					break;
					
	//			case INDEX_WAV_KEY_1P_:
	//			case INDEX_WAV_KEY_2P_:
	//			case INDEX_WAV_LONG_KEY_1P_:
	//			case INDEX_WAV_LONG_KEY_2P_:
				default:
					System.out.println(note.command + " " + note.value);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	private boolean processAutoHit(Note note) {
		switch(note.command) {
		case INDEX_WAV_KEY_1P_:
		case INDEX_WAV_KEY_2P_:
			IDefineSound sound	= (IDefineSound)note.note_define.value_object;
			sound.play();
			return true;
//		case INDEX_WAV_LONG_KEY_1P_:
//		case INDEX_WAV_LONG_KEY_2P_:
		}
		return false;
	}

//	private void stopNote(Note note) {
//		switch(note.command) {
//		case INDEX_WAV_KEY_1P_:
//		case INDEX_WAV_KEY_2P_:	
//		case INDEX_WAV_BG:
//		case INDEX_WAV_LONG_KEY_1P_:
//		case INDEX_WAV_LONG_KEY_2P_:
//			IDefineSound sound	= (IDefineSound)note.note_define.value_object;
//			sound.dispose();
//		}
//	}
	
//	-------------------------------------------------------------------------------------------------
	
	synchronized public ArrayList<Note> getPlayTracks(double length)
	{
		ArrayList<Note> ret = new ArrayList<Note>();
		if (play_tracks!=null) {
			for (Note note : play_tracks) {
				if (note.getBeginPosition() - play_position < length) {
					ret.add(note);
				} else {
					break;
				}
			}
		}
		return ret;
	}

	public double getPlayPosition() {
		return play_position;
	}
	
	public ArrayList<Note> getPlayTracks() {
		return getPlayTracks(Double.MAX_VALUE);
	}
	
	public IDefineImage getPlayBGImage() {
		return play_bg_image;
	}
	
	public IDefineImage getPlayLayerImage() {
		return play_layer_image;
	}
	
	public IDefineImage getPlayPoorImage() {
		return play_poor_image;
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	/**
	 * 击打信息
	 * @author WAZA
	 */
	public class HitInfo
	{
		/** 击打到的音符 */
		public Note		hit_note;
		
		/** 绝对值越小，则代表越完美 */
		public double	hit_deta_time;
	}
	

	/**
	 * 击打一个轨道，如果返回为空，则表示没有击打到，如果不为空，返回值内包含击打到的信息
	 * @param track
	 * @return
	 */
	public HitInfo hit(int track)
	{
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
}

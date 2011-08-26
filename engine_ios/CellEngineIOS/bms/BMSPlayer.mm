//
//  BMSPlayer.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-17.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "BMSPlayer.h"


namespace com_cell_bms
{
	
	BMSPlayer::BMSPlayer(BMSFile *bms, unsigned int max_sound)
	{	
		m_pBmsFile				= bms;
		m_listener				= NULL;
		m_is_auto_play			= false;		
		m_play_drop_length		= m_pBmsFile->getBeatDiv();
		m_hit_range				= m_pBmsFile->getBeatDiv();
		m_source_pool			= new SoundPlayerPool(max_sound);
		m_is_mute_bgm			= false;
	}
	
	BMSPlayer::~BMSPlayer()
	{
		stop();
		delete m_source_pool;
	}
	
	BMSFile* BMSPlayer::getBMSFile()
	{
		return m_pBmsFile;
	}
	
//	----------------------------------------------------------------------------------------

	void BMSPlayer::setListener(BMSPlayerListener *listener) 
	{
		m_listener = listener;
	}
	
	void BMSPlayer::setDropLength(float len)
	{
		m_play_drop_length = len;
	}
	
	float BMSPlayer::getDropLength()
	{
		return m_play_drop_length;
	}
	
	void BMSPlayer::setHitRange(float range) 
	{
		m_hit_range = range;
	}
	
	float BMSPlayer::getHitRange() 
	{
		return m_hit_range;
	}

//	----------------------------------------------------------------------------------------

	void BMSPlayer::start()
	{
		m_play_tracks				= m_pBmsFile->getAllNoteList();
		m_play_removed				.clear();
		m_play_bpm					= m_pBmsFile->getHeadInfoAsNumber(HEAD_INFO_BPM);
		m_play_position				= 0;
		m_play_pre_beat_position	= 0;
//		m_play_pre_record_time		= getCurrentTime();
		m_play_stop_time			= 0;
		m_play_beat_count			= 0;
		m_is_running				= true;
		m_play_bg_image				= NULL;
		m_play_poor_image			= NULL;
		m_play_layer_image			= NULL;
	}
	
	void BMSPlayer::stop() 
	{
		m_is_running				= false;
		m_play_tracks				.clear();
		m_play_removed				.clear();
		m_play_bg_image				= NULL;
		m_play_poor_image			= NULL;
		m_play_layer_image			= NULL;
		m_source_pool				->stopAllSound();
	}
	
	bool BMSPlayer::isRunning()
	{
		return m_is_running;
	}
	
	bool BMSPlayer::isEnd()
	{
		if (m_play_tracks.size() <= 0) {
			return m_source_pool->isAllFree();
		}
		return false;
	}
	
	//	----------------------------------------------------------------------------------------
	
	void BMSPlayer::update(double deta_time)
	{
		if (!m_is_running) {
			return;
		}
		
//		double	cur_time		= getCurrentTime();
//		double	deta_time		= cur_time - m_play_pre_record_time; 
//		m_play_pre_record_time	= cur_time;
		
		// 已缓冲的音符
//		std::list< int> List;
//		std::list< int>::iterator itList;
//		for( itList = List.begin(); itList != List.end(); )
//		{
//            if( WillDelete( *itList) )
//            {
//				itList = List.erase( itList);
//            }
//            else
//				itList++;
//		}
					
		for (std::list<Note*>::iterator it=m_play_tracks.begin(); it!=m_play_tracks.end(); ) 
		{
			Note* note = (*it);
			
			// 如果该音符过丢弃线
			if (note->getBeginPosition() <= m_play_position - m_play_drop_length) {
				m_play_removed.push_back(note);
				it = m_play_tracks.erase(it);
				onDropedNote(note);
				continue;
			}
			// 如果该音符过线
			else if (note->getBeginPosition() <= m_play_position) {
				if (processSystemNote(note)) {
					// 如果是系统命令，则立即处理
					m_play_removed.push_back(note);
					it = m_play_tracks.erase(it);
					continue;
				}
				// 如果自动演奏，则提供一个命令
				else if (processAutoHit(note)) {
					// 如果是按键命令，则立即处理
					m_play_removed.push_back(note);
					it = m_play_tracks.erase(it);
					continue;
				}
				else {
					++it;
				}
			}
			// 未到线的音符
			else {
				break;
			}
		}
			
		
		double deta_position = m_pBmsFile->timeToPosition(deta_time, m_play_bpm);
		if (m_play_stop_time>0) {
			m_play_stop_time	-= deta_position;
		} else {
			m_play_position		+= deta_position;
			if (m_play_position > m_play_pre_beat_position + m_pBmsFile->getBeatDiv()) {
				m_play_pre_beat_position = m_play_position;
				m_play_beat_count ++;
				onOneBeat(m_play_beat_count);
			}
		}
		
		
//		double pos = m_play_position;
//		int line_dtime = (int)(pos / m_pBmsFile->getLineSplitDiv());
//		double line_pos_offset = line_dtime  * m_pBmsFile->getLineSplitDiv();
//
//		NSLog(@"update interval : %lf - %lf - %lf - %lf", deta_time, deta_position, line_pos_offset, pos);
	}
	
	bool BMSPlayer::hit(int track, HitInfo &out_info)
	{
		for (std::list<Note*>::iterator it=m_play_tracks.begin(); it!=m_play_tracks.end(); ++it) 
		{
			Note* note = (*it);
			if (note->getBeginPosition() > m_play_position + m_hit_range) {
				break;
			}
			if (note->getTrack() == track) {
				float offset = ABS(m_play_position - note->getBeginPosition());
				if (offset <= m_hit_range) {
//					printf("note=%lf pos=%lf off=%lf \n", 
//						   note->getBeginPosition(),
//						   m_play_position,
//						   offset);
					m_play_removed.push_back(note);
					it = m_play_tracks.erase(it);
					IDefineSound* define_snd = (IDefineSound*)(note->getHeadDefine()->
															   getDefineResource());
					onPlaySound(note, define_snd->getSound());
					out_info.hit_note = note;
					out_info.hit_precision = offset / m_hit_range;
					if (m_listener!=NULL) {
						m_listener->onHit(out_info);
					}
					return true;
				}
 			}
		}
		return false;
	}
	
	bool BMSPlayer::hit(Note* note, HitInfo &out_info)
	{
		for (std::list<Note*>::iterator it=m_play_tracks.begin(); it!=m_play_tracks.end(); ++it) 
		{
			if (note == (*it))
			{
				float offset = ABS(m_play_position - note->getBeginPosition());
				if (offset <= m_hit_range) {
					m_play_removed.push_back(note);
					it = m_play_tracks.erase(it);
					IDefineSound* define_snd = (IDefineSound*)(note->getHeadDefine()->
															   getDefineResource());
					onPlaySound(note, define_snd->getSound());
					out_info.hit_note = note;
					out_info.hit_precision = offset / m_hit_range;
					if (m_listener!=NULL) {
						m_listener->onHit(out_info);
					}
					return true;
				}
			}
		}
		
		return false;
	}

	
//	-------------------------------------------------------------------------------------------------
	
	bool BMSPlayer::processSystemNote(Note *note) 
	{
		if (note->getTrack()<=9)
		{
			// play time position speed
			if (stringEquals(note->getCommand(), CMD_BPM_CHANGE))
			{
				float bpm = m_play_bpm;
				if (stringToFloat(note->getTrackValue(), bpm)) {
					m_play_bpm = bpm;
					printf("%s = %f\n", note->getCommand().c_str(), m_play_bpm);
				}
			}
			else if (stringEquals(note->getCommand(), CMD_INDEX_BPM)) 
			{
				IDefineNumber* define_number = (IDefineNumber*)(note->getHeadDefine()->
																getDefineResource());
				m_play_bpm = define_number->number;
				printf("%s = %f\n", note->getCommand().c_str(), m_play_bpm);
			}
			else if (stringEquals(note->getCommand(), CMD_INDEX_STOP)) 
			{
				IDefineNumber* define_number = (IDefineNumber*)(note->getHeadDefine()->
																getDefineResource());
				m_play_stop_time = define_number->number;
				printf("%s = %f\n", note->getCommand().c_str(), m_play_stop_time);
			}
			// play image
			else if (stringEquals(note->getCommand(), CMD_INDEX_BMP_BG)) 
			{				
				IDefineImage* define_img = (IDefineImage*)(note->getHeadDefine()->
															  getDefineResource());
				m_play_bg_image = define_img->getImage();
				
			}
			else if (stringEquals(note->getCommand(), CMD_INDEX_BMP_LAYER)) 
			{				
				IDefineImage* define_img = (IDefineImage*)(note->getHeadDefine()->
														   getDefineResource());
				m_play_layer_image = define_img->getImage();
			}
			else if (stringEquals(note->getCommand(), CMD_INDEX_BMP_POOR)) 
			{
				IDefineImage* define_img = (IDefineImage*)(note->getHeadDefine()->
														   getDefineResource());
				m_play_poor_image = define_img->getImage();
			}
			// play sound
			else if (stringEquals(note->getCommand(), CMD_INDEX_WAV_BG)) 
			{
				if (!m_is_mute_bgm) {
					IDefineSound* define_snd = (IDefineSound*)(note->getHeadDefine()->
															   getDefineResource());
					onPlaySound(note, define_snd->getSound());
				}
			}
			else
			{
				printf("%s = %s\n", note->getCommand().c_str(), note->toString().c_str());
			}
			return true;
		}
		return false;
	}
	
	bool BMSPlayer::processAutoHit(Note *note) 
	{
		if (!m_is_auto_play) {
			if (!isAutoPlayTrack(note->getTrack())) {
				return false;
			}
		}
		if (stringEquals(note->getCommand(), CMD_INDEX_WAV_KEY_1P_) ||
			stringEquals(note->getCommand(), CMD_INDEX_WAV_KEY_2P_)) 
		{
			if (!m_is_mute_bgm) {
				IDefineSound* define_snd = (IDefineSound*)(note->getHeadDefine()->
														   getDefineResource());
				onPlaySound(note, define_snd->getSound());
			}
			if (m_listener != NULL) {
				m_listener->onAutoHit(note);
			}
			return true;
		}
		return false;
	}
	
//	----------------------------------------------------------------------------------------------------
	
	void BMSPlayer::onPlaySound(Note *note, Sound* sound)
	{
		if (stringEquals(note->getCommand(), CMD_INDEX_WAV_BG)) {
			if (m_source_pool->playSoundImmediately(sound, false)) {
				printf("cut an another source!\n");
			}	
		} else {
			if (!m_source_pool->playSound(sound, false)) {
				printf("no free source to play : %s\n", 
					   note->getHeadDefine()->getValue().c_str());
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------
	
	void BMSPlayer::onDropedNote(Note *note)
	{
		if (m_listener != NULL) {
			m_listener->onDropNote(note);
		}
	}
	
	void BMSPlayer::onOneBeat(int beatcount)
	{
		if (m_listener != NULL) {
			m_listener->onBeat(beatcount);
		}
	}
	
	
//	-------------------------------------------------------------------------------------------------
	
	void BMSPlayer::getPlayTracks(float length, std::vector<Note*> &out_list)
	{
		if (m_is_running) {
			for (std::list<Note*>::iterator it=m_play_tracks.begin();
				 it!=m_play_tracks.end(); ++it) {
				Note* note = (*it);
				if (note->getBeginPosition() - m_play_position < length) {
					out_list.push_back(note);
				} else {
					break;
				}
			}
		}
	}
	
	void BMSPlayer::getPlayKeyTracks(float length, std::vector<Note*> &out_list)
	{
		if (m_is_running) {
			for (std::list<Note*>::iterator it=m_play_tracks.begin();
				 it!=m_play_tracks.end(); ++it) {
				Note* note = (*it);
				if (note->getBeginPosition() - m_play_position < length) {
					if (stringEquals(note->getCommand(), CMD_INDEX_WAV_KEY_1P_) ||
						stringEquals(note->getCommand(), CMD_INDEX_WAV_KEY_2P_) ||
						stringEquals(note->getCommand(), CMD_INDEX_WAV_LONG_KEY_1P_) ||
						stringEquals(note->getCommand(), CMD_INDEX_WAV_LONG_KEY_2P_)) {
						out_list.push_back(note);
					}
				} else {
					break;
				}
			}
		}
	}
	
	
	
	//	----------------------------------------------------------------------------------------
	
	
	void BMSPlayer::setAutoPlayTrack(int track, bool v)
	{
		m_auto_play_track_map[track] = v;
	}
	
	void BMSPlayer::setAutoPlay(bool v)
	{
		m_is_auto_play = v;
	}
	
	bool BMSPlayer::isAutoPlayTrack(int track)
	{
		map<int, bool>::const_iterator it = m_auto_play_track_map.find(track);
		if (it != m_auto_play_track_map.end()) {
			return it->second;
		}
		return false;
	}
	
	//	----------------------------------------------------------------------------------------
	
	double BMSPlayer::getPlayPosition() 
	{
		return m_play_position;
	}

	double BMSPlayer::getNextLinePosition(double pos) 
	{
		int cur_div_time = (int)(pos / m_pBmsFile->getLineSplitDiv()) + 1;
		
		return cur_div_time * m_pBmsFile->getLineSplitDiv();
	}
	
	/**得到此位置之前最近的一条线(小节)的位置*/
	double BMSPlayer::getPrewLinePosition(double pos)
	{
		int cur_div_time = (int)(pos / m_pBmsFile->getLineSplitDiv());
		
		return cur_div_time * m_pBmsFile->getLineSplitDiv();
	}
	
	
	Image*	BMSPlayer::getPlayBGImage() {
		return m_play_bg_image;
	}
	
	Image*	BMSPlayer::getPlayLayerImage() {
		return m_play_layer_image;
	}
	
	Image*	BMSPlayer::getPlayPoorImage() {
		return m_play_poor_image;
	}

	bool hit(int track, HitInfo &out_info)
	{

		return false;
	}
	
	float BMSPlayer::getDropNoteDisappear(Note* note)
	{		
		double nlen = m_play_position - note->getBeginPosition();
		
		double tlen = MAX(m_play_drop_length - nlen, 0);
		
		return (float)MIN(1, tlen / m_play_drop_length);
	}

	
}; // namespcace 

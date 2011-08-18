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
	
	BMSPlayer::BMSPlayer(BMSFile *bms)
	{	
		m_pBmsFile				= bms;
		m_listener				= NULL;
		m_is_auto_play			= true;		
		m_play_drop_length		= m_pBmsFile->getLineSplitDiv();
		m_source_pool			= new SoundPlayerPool();
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
	
	void BMSPlayer::setListener(BMSPlayerListener *listener) 
	{
		m_listener = listener;
	}
	
	void BMSPlayer::start()
	{
		m_play_tracks				= m_pBmsFile->getAllNoteList();
		m_play_removed				.clear();
		m_play_bpm					= m_pBmsFile->getHeadInfoAsNumber(HEAD_INFO_BPM);
		m_play_position				= 0;
		m_play_pre_beat_position	= 0;
		m_play_pre_record_time		= getCurrentTime();
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
	}
	
	bool BMSPlayer::isRunning()
	{
		return m_is_running;
	}
	
	void BMSPlayer::update()
	{
		if (!m_is_running) {
			return;
		}
		
		double	cur_time		= getCurrentTime();
		double	deta_time		= cur_time - m_play_pre_record_time; 
		m_play_pre_record_time	= cur_time;
		
		// 已缓冲的音符
		{
			std::vector<std::vector<Note*>::iterator> removed;
			
			for (std::vector<Note*>::iterator it=m_play_tracks.begin(); it!=m_play_tracks.end(); ++it) 
			{
				Note* note = (*it);
				
				// 如果该音符过丢弃线
				if (note->getBeginPosition() <= m_play_position - m_play_drop_length) {
					removed.push_back(it);
					onDropedNote(note);
					continue;
				}
				// 如果该音符过线
				else if (note->getBeginPosition() <= m_play_position) {
					if (processSystemNote(note)) {
						// 如果是系统命令，则立即处理
						removed.push_back(it);
						continue;
					}
					// 如果自动演奏，则提供一个命令
					else if (m_is_auto_play && processAutoHit(note)) {
						// 如果是按键命令，则立即处理
						removed.push_back(it);
						continue;
					}
				}
				// 未到线的音符
				else {
					break;
				}
			}
			
			for (std::vector<std::vector<Note*>::iterator>::iterator it=removed.begin(); 
				 it!=removed.end(); ++it) 
			{
				std::vector<Note*>::iterator sit = (*it);
				m_play_tracks.erase(sit);
				m_play_removed.push_back((*sit));
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
				IDefineSound* define_snd = (IDefineSound*)(note->getHeadDefine()->
														getDefineResource());
				onPlaySound(note, define_snd->getSound());
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
		if (stringEquals(note->getCommand(), CMD_INDEX_WAV_KEY_1P_) ||
			stringEquals(note->getCommand(), CMD_INDEX_WAV_KEY_2P_)) 
		{
			IDefineSound* define_snd = (IDefineSound*)(note->getHeadDefine()->
													   getDefineResource());
			onPlaySound(note, define_snd->getSound());
			if (m_listener != NULL) {
				m_listener->onAutoHit(note);
			}
			return true;
		}
		return false;
	}
	
	void BMSPlayer::onPlaySound(Note *note, Sound* sound)
	{
		m_source_pool->playSound(sound, false);
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
			for (std::vector<Note*>::iterator it=m_play_tracks.begin();
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
	
	double BMSPlayer::getPlayPosition() {
		return m_play_position;
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
	
	
	
}; // namespcace 

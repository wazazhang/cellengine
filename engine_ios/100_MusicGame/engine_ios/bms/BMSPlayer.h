//
//  BMSPlayer.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-17.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_BMS_PLAYER
#define _COM_CELL_BMS_PLAYER

#include <string>

#include "CSoundManager.h"
#include "CSoundPlayerPool.h"
#include "CGeometry.h"
#include "BMSFile.h"

namespace com_cell_bms
{
	using namespace com_cell;
	
	
	class BMSPlayerListener
	{
	public:
		virtual ~BMSPlayerListener(){}
		
		virtual void onBeat(int beat_count) = 0;
		
		virtual void onDropNote(Note *note) = 0;
		
		virtual void onHit(Note *note) = 0;
		
		virtual void onAutoHit(Note *note) = 0;
		
//		virtual void onPlaySound(Note *note, Sound* sound);
//		
//		virtual void onChangeImage(Note *note, Image* image);
	};
	
	/////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 击打信息
	 */
	typedef struct HitInfo
	{
		/** 击打到的音符 */
		Note	*hit_note;
		/** 绝对值越小，则代表越完美 */
		float	hit_deta_time;
	} HitInfo;

	/////////////////////////////////////////////////////////////////////////////
	
	class BMSPlayer
	{
	private:
		
		BMSFile					*m_pBmsFile;
		BMSPlayerListener		*m_listener;
		/** 是否自动演奏 */
		bool					m_is_auto_play;
		/** 丢弃Note单位的检查范围  */
		float					m_play_drop_length;
		
		//////////////////////////////////////////////////
		// dynamic
		std::vector<Note*>		m_play_tracks;
		std::vector<Note*>		m_play_removed;
		double					m_play_bpm;
		double					m_play_position;	
		double					m_play_stop_time;
		double					m_play_pre_beat_position;
		int						m_play_beat_count;
		double					m_play_pre_record_time;
		bool					m_is_running;
		
		Image*					m_play_bg_image;
		Image*					m_play_poor_image;
		Image*					m_play_layer_image;
		
		////////////////////////////////////////////////////	
		
		SoundPlayerPool*		m_source_pool;
		
		////////////////////////////////////////////////////	

	public:
		
		BMSPlayer(BMSFile *bms, unsigned int max_sound) ;	
		
		~BMSPlayer() ;	
		
		BMSFile* getBMSFile();		
		
		void	setListener(BMSPlayerListener *listener) ;
				
		void	start();		
		
		void	stop() ;	
		
		void	update();
		
		bool	isRunning();
		
		void			getPlayTracks(float length, std::vector<Note*> &out_list);
		
		double			getPlayPosition();
		
		Image*			getPlayBGImage() ;		
		
		Image*			getPlayLayerImage() ;	
		
		Image*			getPlayPoorImage() ;
		
		/**
		 * 击打一个轨道，如果返回为false，则表示没有击打到，如果不为空，返回值内包含击打到的信息
		 */
		bool hit(int track, HitInfo &out_info);		
		
	private:
		
		bool			processSystemNote(Note *note) ;
		
		bool			processAutoHit(Note *note) ;
		
		void			onPlaySound(Note *note, Sound* sound);
		
	protected:
		
		// 有未处理的音符过了最终丢弃区域，一般是没有击中
		void			onDropedNote(Note *note);
		
		// 每次到四分之一拍子，回调一次
		void			onOneBeat(int beatcount);
		
		
		
		
	};

	
	
	
}; // namespcace 

#endif // _COM_CELL_BMS_PLAYER

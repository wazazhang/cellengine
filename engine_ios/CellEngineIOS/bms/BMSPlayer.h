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
#include <list>

#include "CSoundManager.h"
#include "CSoundPlayerPool.h"
#include "CGeometry.h"
#include "BMSFile.h"

namespace com_cell_bms
{
	using namespace com_cell;
	
	/////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 击打信息
	 */
	typedef struct HitInfo
	{
		/** 击打到的音符 */
		Note	*hit_note;
		/** 绝对值越接近0，则代表越完美(0.0~1.0) */
		float	hit_precision;
	} HitInfo;

	
	class BMSPlayerListener
	{
	public:
		virtual ~BMSPlayerListener(){}
		
		virtual void onBeat(int beat_count) = 0;
		
		virtual void onDropNote(Note *note) = 0;
		
		virtual void onHit(HitInfo const &hit) = 0;
		
		virtual void onAutoHit(Note *note) = 0;
		
//		virtual void onPlaySound(Note *note, Sound* sound);
//		
//		virtual void onChangeImage(Note *note, Image* image);
	};
	
	/////////////////////////////////////////////////////////////////////////////
	
	class BMSPlayer
	{
	public:
	private:
		
		BMSFile					*m_pBmsFile;
		BMSPlayerListener		*m_listener;
		/** 丢弃Note单位的检查范围  */
		float					m_play_drop_length;
		/** 外界输入，击打范围 */
		float					m_hit_range;
		
		/** 是否自动演奏 */
		bool					m_is_auto_play;
		map<int, bool>			m_auto_play_track_map;
		bool					m_is_mute_bgm;

		//////////////////////////////////////////////////
		// dynamic
		std::list<Note*>		m_play_tracks;
		std::list<Note*>		m_play_removed;
		double					m_play_bpm;
		double					m_play_position;	
		double					m_play_stop_time;
		double					m_play_pre_beat_position;
		int						m_play_beat_count;
//		double					m_play_pre_record_time;
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
		
		BMSFile*		getBMSFile();		
		
		void			setListener(BMSPlayerListener *listener) ;
		
		void			setDropLength(float len);
		
		float			getDropLength();
		
		void			setHitRange(float range);
		
		float			getHitRange();
		
		//-------------------------
		
		void			start();		
		
		void			stop() ;	
		
		void			update(double deta_time);
		
		bool			isRunning();
		
		bool			isEnd();

		//--------------------------
		
		void			setAutoPlay(bool v);
		
		void			setAutoPlayTrack(int track, bool v);
		
		bool			isAutoPlayTrack(int track);
		
		void			getPlayTracks(float length, std::vector<Note*> &out_list);
		
		void			getPlayKeyTracks(float length, std::vector<Note*> &out_list);
		
		double			getPlayPosition();
		
		/**得到此位置之后最近的一条线(小节)的位置*/
		double			getNextLinePosition(double pos);
		
		/**得到此位置之前最近的一条线(小节)的位置*/
		double			getPrewLinePosition(double pos);
		
		Image*			getPlayBGImage() ;		
		
		Image*			getPlayLayerImage() ;	
		
		Image*			getPlayPoorImage() ;
		
		// 获得此音符过播放线后，消失(丢弃)强度，刚过线＝1，刚消失＝0
		float			getDropNoteDisappear(Note* note);
		
		//--------------------------
		
		/**
		 * 击打一个轨道，如果返回为false，则表示没有击打到，如果不为空，返回值内包含击打到的信息
		 */
		bool hit(int track, HitInfo &out_info);		
		
		/**
		 * 直接击打一个音符，适合音乐旋风类游戏
		 */
		bool hit(Note* note, HitInfo &out_info);		

		
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

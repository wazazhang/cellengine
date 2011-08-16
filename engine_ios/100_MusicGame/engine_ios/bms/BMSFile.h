//
//  BMSFile.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-16.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_BMS_BMS_FILE
#define _COM_CELL_BMS_BMS_FILE

#include <string>
#include <vector>
#include <map>

#include "CSoundManager.h"
#include "CImage.h"

namespace com_cell_bms
{
	using namespace com_cell;
	/**
	 ---------------------- HEADER FIELD ----------------------<br>
	 #PLAYER		WAZA<br>
	 #GENRE		WAZA<br>
	 #TITLE		song title<br>
	 #ARTIST		waza<br>
	 #BPM		120<br>
	 #PLAYLEVEL	1<br>
	 #RANK		3<br>
	 #TOTAL		123<br>
	 #VOLWAV		123<br>
	 #STAGEFILE	123<br>
	 <br>
	 #WAV01		Ba_b_4.wav<br>
	 #WAV02		Ba_b_8.wav<br>
	 #WAV03		Ba_c#_4.wav<br>
	 <br>
	 #BMP00		bg.bmp<br>
	 #BMP01		back.bmp<br>
	 #BMP02		end01.bmp<br>
	 #BMP03		Fade01.bmp<br>
	 <br>
	 #BPM01		256<br>
	 <br>
	 #STOP01		123<br>
	 <br>
	 <br>
	 ---------------------- MAIN DATA FIELD ----------------------<br>
	 - XXXCC:0001<br>
	 - 	XXX 	= 小节标记<br>
	 - 	CC 		= 指令<br>
	 -	0001	= 值(音符或者图片)<br>
	 <br>
	 #00008:0001			- 在2/2小节处，改变的BPM索引为01，也就是#BPM01对应的值<br>
	 #00009:00010000		- <br>
	 #00022:00010000		- <br>
	 #00061:01<br>
	 <br>
	 #00161:01<br>
	 <br>
	 <br>
	 ---------------------- MAIN DATA COMMAND ----------------------<br>
	 01 = 背景NOTE音。<br>
	 03 = 0~255（0h~FFh）整数BPM变化。<br>
	 04 = 改变BGA的图片索引。<br>
	 06 = 改变POOR的图片索引。<br>
	 07 = 改变Layer的图片索引。<br>
	 08 = 改变的BPM索引。表示小数的BPM变化或者大于255的BPM。（前方定义的 #BPMXX）<br>
	 09 = STOP停止的时间索引。（前方定义的 #STOPXX）<br>
	 <br>
	 11~19 = 1P KEY<br>
	 21~29 = 2P KEY<br>
	 51~59 = 1P LONG KEY<br>
	 61~69 = 2P LONG KEY<br>
	 <br>
	 */
	////////////////////////////////////////////////////////////////////////////////
	
	/** 
	 * 设置将每小节分割为多少份来处理	
	 * 将每BEAT分割为多少份来处理 (4 BEAT = 1 LINE)
	 */
	extern void setLineSplitDIV(double div);
	
    ////////////////////////////////////////////////////////////////////////////////
	
	typedef std::string HeaderInfoEnum;	
	
	const HeaderInfoEnum HEAD_INFO_PLAYER				= "PLAYER";
	const HeaderInfoEnum HEAD_INFO_GENRE				= "GENRE";
	const HeaderInfoEnum HEAD_INFO_TITLE				= "TITLE";
	const HeaderInfoEnum HEAD_INFO_ARTIST				= "ARTIST";
	const HeaderInfoEnum HEAD_INFO_BPM					= "BPM";
	const HeaderInfoEnum HEAD_INFO_PLAYLEVEL			= "PLAYLEVEL";
	const HeaderInfoEnum HEAD_INFO_RANK					= "RANK";
	const HeaderInfoEnum HEAD_INFO_TOTAL				= "TOTAL";
	const HeaderInfoEnum HEAD_INFO_VOLWAV				= "VOLWAV";
	const HeaderInfoEnum HEAD_INFO_STAGEFILE			= "STAGEFILE";
	
	
    ////////////////////////////////////////////////////////////////////////////////
	typedef std::string HeaderDefineEnum;
	
	const HeaderDefineEnum HEAD_DEFINE_WAV			= "WAV";
	const HeaderDefineEnum HEAD_DEFINE_BMP			= "BMP";
	const HeaderDefineEnum HEAD_DEFINE_BPM			= "BPM";
	const HeaderDefineEnum HEAD_DEFINE_STOP			= "STOP";
	
    ////////////////////////////////////////////////////////////////////////////////
	typedef std::string CommandEnum;
	

	/** 背景音索引 WAVXX */
	const CommandEnum CMD_INDEX_WAV_BG				= "01";
	/** 整数BPM变化。00h~FFh */
	const CommandEnum CMD_BPM_CHANGE				= "03";
	/** 改变BGA的图片索引。	BMPXX */
	const CommandEnum CMD_INDEX_BMP_BG				= "04";
	/** 改变POOR的图片索引。	BMPXX */
	const CommandEnum CMD_INDEX_BMP_POOR			= "06";
	/** 改变Layer的图片索引。	BMPXX */
	const CommandEnum CMD_INDEX_BMP_LAYER			= "07";
	/** 改变的BPM索引。表示小数的BPM变化或者大于255的BPM。前方定义的 #BPMXX */
	const CommandEnum CMD_INDEX_BPM					= "08";
	/** STOP停止的时间索引。前方定义的 #STOPXX */
	const CommandEnum CMD_INDEX_STOP				= "09";
		
	/** WAV KEY */
	const CommandEnum CMD_INDEX_WAV_KEY_1P_			= "1*";
	const CommandEnum CMD_INDEX_WAV_KEY_2P_			= "2*";
	const CommandEnum CMD_INDEX_WAV_LONG_KEY_1P_	= "5*";
	const CommandEnum CMD_INDEX_WAV_LONG_KEY_2P_	= "6*";
		
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// 
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	extern bool valueOfHeaderInfo(std::string const &str, HeaderInfoEnum &outvalue);
	
	extern bool valueOfHeaderDefine(std::string const &str, HeaderDefineEnum &outvalue);
	
	extern bool valueOfDataCommand(std::string const &str, CommandEnum &outvalue);

	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// IDefineNote class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// Head Define 定义的资源
	class IDefineResource 
	{
	};
	
	// Head Define 声音
	class IDefineSound : public IDefineResource
	{
	public:
		char const *filepath;
		Sound* pSound;
	public:
		IDefineSound(char const *file);
		~IDefineSound();
		void play();
	};

	// Head Define 图片
	class IDefineImage : public IDefineResource
	{
	public:
		char const *filepath;
		Image* pImage;
	public:
		IDefineImage(char const *file);
		~IDefineImage();
		Image* getImage() ;
	};
	
	// Head Define 数值
	class IDefineNumber : public IDefineResource
	{
	public:
		double number;
	public:
		IDefineNumber(double n);
		~IDefineNumber();		
	};

	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// HeaderDefine class
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 定义在 Header 的 HeaderDefine 
	 * @author WAZA
	 */
	class HeadDefine
	{
	public:
		// #[WAV]XX sound.caf
		HeaderDefineEnum	m_define;
		// #WAV[XX] sound.caf
		std::string			m_index;
		// #WAVXX [sound.caf]
		std::string			m_value;
		
		IDefineResource		*m_pValueObject;
		
	public:
		HeadDefine(HeaderDefineEnum def,
				   std::string index,
				   std::string value,
				   IDefineResource *m_pValueObject) ;
		
		~HeadDefine() ;
	};
	
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// Note class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 在数据区域内的音符数据
	 * @author WAZA
	 */
	class Note
	{		
	private:		
		
		// #010[01]:00010000
		CommandEnum			m_command;
		// #[010]01:00010000
		long				m_line;
		// #010[01]:00010000
		long				m_track;
		// #01001:00[01]0000
		std::string			m_track_value;

		double				m_begin_position;
		double				m_end_position;
		
		HeadDefine			*m_pHeadDefine;
		
	public:
		
		Note(CommandEnum const &command,
			 long line, 
			 long track, 
			 double begin_position,
			 double end_position,
			 HeadDefine	*pHeadDefine,
			 std::string const &track_value);
		
		~Note();
		
		double		getBeginPosition() ;		
		
		double		getEndPosition() ;		
			
		bool		validate();
		
		bool		isLongKey();
		
		int			compareTo(Note const *o) ;		
		
		std::string toString() ;
	};
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// LoadingListener class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	class BMSLoadListener 
	{
	public:
		virtual void beginLoading() = 0;
		virtual void endLoading() = 0;
		virtual void loadline(int max, int current, std::string const &line_data) = 0;
	};

	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// BMSFile class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	class BMSFile
	{
		friend class HeadDefine;
		friend class Note;
		
	private:
		
		std::string m_bms_file;
		std::string m_bms_dir;
		
		/** 将每小节分割为多少份来处理 */
		double		m_line_split_div;
		
		/** 将每BEAT分割为多少份来处理 (4 BEAT = 1 LINE)*/
		double 		m_beat_div;
		
		std::map<HeaderInfoEnum, std::string>		header_info;
		
		int			totoal_define_resource_count;
		int			totoal_head_define_count;
		int			totoal_note_count;
		
		// delete on destory
		std::vector<HeadDefine*>					resources;
		// delete on destory
		std::map<CommandEnum, std::vector<Note*> >	data_note_table;
		
		// fast map
		std::map<std::string, HeadDefine*>			header_wav_map;
		std::map<std::string, HeadDefine*>			header_img_map;
		std::map<std::string, HeadDefine*>			header_bpm_map;
		std::map<std::string, HeadDefine*>			header_stp_map;
		
	public:
		
		BMSFile(char const* file, BMSLoadListener *listener);
		
		~BMSFile();
		
		char const*			getBmsFile();
		
		// 获取文件信息
		std::string			getHeadInfo(HeaderInfoEnum const &head);
		
		// 获得头元素定义的值
		HeadDefine*			getHeadDefine(CommandEnum const &command, std::string const &track_value);
		
		// 获取某个轨道所有的音符
		std::vector<Note*>	getNoteList(CommandEnum const &track);		
		
		// 获取所有轨道的音符信息
		std::vector<Note*>	getAllNoteList() ;	
		
	protected:
		
		// 
		double barToPosition(int line, double npos, double ncount);
		
		// 
		double timeToPosition(double deta_time_ms, double bpm);
		

		bool initHeadInfo	(std::string const &k, std::string const &v);		
		bool initHeadMap	(std::string const &k, std::string const &v);		
		bool initDataLine	(std::string const &c, std::string const &v);		
		
		IDefineResource*	createDefineResource(HeaderDefineEnum const &def, 
												 std::string const &value);	
		
		HeadDefine*			createHeadDefine(HeaderDefineEnum const &def,
											 std::string const &index,
											 std::string const &value);	
		
		Note*				createNote(CommandEnum const &command,
									   std::string const &track_value,
									   long line, 
									   long track, 
									   int npos, 
									   int ncount);
		
	};
	
	
}; // namespcace 

#endif // _COM_CELL_BMS_BMS_FILE
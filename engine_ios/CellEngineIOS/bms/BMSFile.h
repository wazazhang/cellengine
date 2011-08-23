//
//  BMSFile.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-16.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_BMS_FILE
#define _COM_CELL_BMS_FILE

#include <string>
#include <vector>
#include <map>
#include <list>
#include <deque>


#include "CSoundManager.h"
#include "CImage.h"

namespace com_cell_bms
{
	using namespace com_cell;
	/**
	 ---------------------- HEADER FIELD ----------------------
	 #PLAYER		WAZA
	 #GENRE		WAZA
	 #TITLE		song title
	 #ARTIST		waza
	 #BPM		120
	 #PLAYLEVEL	1
	 #RANK		3
	 #TOTAL		123
	 #VOLWAV		123
	 #STAGEFILE	123
	 
	 #WAV01		Ba_b_4.wav
	 #WAV02		Ba_b_8.wav
	 #WAV03		Ba_c#_4.wav
	 
	 #BMP00		bg.bmp
	 #BMP01		back.bmp
	 #BMP02		end01.bmp
	 #BMP03		Fade01.bmp
	 
	 #BPM01		256
	 
	 #STOP01		123
	 
	 
	 ---------------------- MAIN DATA FIELD ----------------------
	 - XXXCC:0001
	 - 	XXX 	= 小节标记
	 - 	CC 		= 指令
	 -	0001	= 值(音符或者图片)
	 
	 #00008:0001			- 在2/2小节处，改变的BPM索引为01，也就是#BPM01对应的值
	 #00009:00010000		- 
	 #00022:00010000		- 
	 #00061:01
	 
	 #00161:01
	 
	 
	 ---------------------- MAIN DATA COMMAND ----------------------
	 01 = 背景NOTE音。
	 03 = 0~255（0h~FFh）整数BPM变化。
	 04 = 改变BGA的图片索引。
	 06 = 改变POOR的图片索引。
	 07 = 改变Layer的图片索引。
	 08 = 改变的BPM索引。表示小数的BPM变化或者大于255的BPM。（前方定义的 #BPMXX）
	 09 = STOP停止的时间索引。（前方定义的 #STOPXX）
	 
	 11~19 = 1P KEY
	 21~29 = 2P KEY
	 51~59 = 1P LONG KEY
	 61~69 = 2P LONG KEY
	 
	 */
	////////////////////////////////////////////////////////////////////////////////
	
	/** 
	 * 设置将每小节分割为多少份来处理	
	 * 将每BEAT分割为多少份来处理 (4 BEAT = 1 LINE)
	 */
	extern void setLineSplitDIV(float div);
	
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
	public:
		virtual ~IDefineResource(){}
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
		
		Sound* getSound();
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
		IDefineNumber(float n);
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
	private:
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
		
		HeaderDefineEnum&	getDefineType();
		
		IDefineResource*	getDefineResource();
		
		std::string&		getIndex();
		
		std::string&		getValue();
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
		int					m_line;
		// #010[01]:00010000
		int					m_track;
		// #01001:00[01]0000
		std::string			m_track_value;

		double				m_begin_position;
		double				m_end_position;
		
		HeadDefine			*m_pHeadDefine;
		
	public:
		
		Note(CommandEnum const &command,
			 int line, 
			 int track, 
			 double begin_position,
			 double end_position,
			 HeadDefine	*pHeadDefine,
			 std::string const &track_value);
		
		~Note();
		
		CommandEnum&	getCommand();
		
		HeadDefine*		getHeadDefine();
		
		std::string&	getTrackValue();

		int			getTrack();
		
		int			getLine();
		
		double		getBeginPosition() ;		
		
		double		getEndPosition() ;		
			
		bool		validate();
		
		bool		isLongKey();
			
		
		std::string toString() ;
	};
	
	//	bool cmp( int a, int b ) {
	//		return a > b;
	//	}

	extern bool compareNote(Note *a, Note *b) ;	

    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// LoadingListener class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	class BMSLoadListener 
	{
	public:
		virtual ~BMSLoadListener(){}
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
		float		m_line_split_div;
		
		/** 将每BEAT分割为多少份来处理 (4 BEAT = 1 LINE)*/
		float 		m_beat_div;
		
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
		
		float				getLineSplitDiv();
		float				getBeatDiv();
		
		char const*			getBmsFile();
		
		// 获取文件信息
		std::string			getHeadInfo(HeaderInfoEnum const &head);
		
		double				getHeadInfoAsNumber(HeaderInfoEnum const &head);
		
		// 获得头元素定义的值
		HeadDefine*			getHeadDefine(CommandEnum const &command, std::string const &track_value);
		
		// 获取某个轨道所有的音符
		std::list<Note*>	getNoteList(CommandEnum const &track);		
		
		// 获取所有轨道的音符信息
		std::list<Note*>	getAllNoteList() ;	
		
		// 
		double timeToPosition(float deta_time_ms, float bpm);
		
		// 
		double barToPosition(int line, float npos, float ncount);

	protected:
		

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
									   int line, 
									   int track, 
									   int npos, 
									   int ncount);
		
	};
	
	
}; // namespcace 

#endif // _COM_CELL_BMS_BMS_FILE
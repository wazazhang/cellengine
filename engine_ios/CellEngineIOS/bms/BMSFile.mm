//
//  BMSFile.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-16.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include <iostream.h>
#include "BMSFile.h"
#include "CUtil.h"
#include "CFile.h"
#include "CGFXManager.h"

namespace com_cell_bms
{
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// Gobal 
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	float G_LINE_SPLIT_DIV = 512.0;

	void setLineSplitDIV(float div)
	{
		if (div >= 32) {
			G_LINE_SPLIT_DIV = div;
		} else {
			printf("must >= 32\n");
		}
	}
	
	bool valueOfHeaderInfo(std::string const &str, HeaderInfoEnum &outvalue)
	{
		if (stringEquals(str, HEAD_INFO_PLAYER)) {
			outvalue = HEAD_INFO_PLAYER;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_GENRE)) {
			outvalue = HEAD_INFO_GENRE;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_TITLE)) {
			outvalue = HEAD_INFO_TITLE;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_ARTIST)) {
			outvalue = HEAD_INFO_ARTIST;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_BPM)) {
			outvalue = HEAD_INFO_BPM;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_PLAYLEVEL)) {
			outvalue = HEAD_INFO_PLAYLEVEL;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_RANK)) {
			outvalue = HEAD_INFO_RANK;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_TOTAL)) {
			outvalue = HEAD_INFO_TOTAL;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_VOLWAV)) {
			outvalue = HEAD_INFO_VOLWAV;
			return true;
		}
		if (stringEquals(str, HEAD_INFO_STAGEFILE)) {
			outvalue = HEAD_INFO_STAGEFILE;
			return true;
		}
		return false;
	}
	
	bool valueOfHeaderDefine(std::string const &str, HeaderDefineEnum &outvalue)
	{
		if (stringStartWith(str, HEAD_DEFINE_WAV)) {
			outvalue = HEAD_DEFINE_WAV;
			return true;
		}
		if (stringStartWith(str, HEAD_DEFINE_BMP)) {
			outvalue = HEAD_DEFINE_BMP;
			return true;
		}
		if (stringStartWith(str, HEAD_DEFINE_BPM)) {
			outvalue = HEAD_DEFINE_BPM;
			return true;
		}
		if (stringStartWith(str, HEAD_DEFINE_STOP)) {
			outvalue = HEAD_DEFINE_STOP;
			return true;
		}
		return false;
	}
	
	bool valueOfDataCommand(std::string const &str, CommandEnum &outvalue)
	{
		if (str.length()==2) {
			if (stringEquals(str, CMD_INDEX_WAV_BG)) {
				outvalue = CMD_INDEX_WAV_BG;
				return true;
			}
			if (stringEquals(str, CMD_BPM_CHANGE)) {
				outvalue = CMD_BPM_CHANGE;
				return true;
			}
			if (stringEquals(str, CMD_INDEX_BMP_BG)) {
				outvalue = CMD_INDEX_BMP_BG;
				return true;
			}
			if (stringEquals(str, CMD_INDEX_BMP_POOR)) {
				outvalue = CMD_INDEX_BMP_POOR;
				return true;
			}
			if (stringEquals(str, CMD_INDEX_BMP_LAYER)) {
				outvalue = CMD_INDEX_BMP_LAYER;
				return true;
			}
			if (stringEquals(str, CMD_INDEX_BPM)) {
				outvalue = CMD_INDEX_BPM;
				return true;
			}
			if (stringEquals(str, CMD_INDEX_STOP)) {
				outvalue = CMD_INDEX_STOP;
				return true;
			}
			if (stringStartWith(str, "1")) {
				outvalue = CMD_INDEX_WAV_KEY_1P_;
				return true;
			}
			if (stringStartWith(str, "2")) {
				outvalue = CMD_INDEX_WAV_KEY_2P_;
				return true;
			}
			if (stringStartWith(str, "5")) {
				outvalue = CMD_INDEX_WAV_LONG_KEY_1P_;
				return true;
			}
			if (stringStartWith(str, "6")) {
				outvalue = CMD_INDEX_WAV_LONG_KEY_2P_;
				return true;
			}
		}
		return false;
	}

    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// IDefineNote class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	IDefineSound::IDefineSound(char const *file)
	{
		filepath = file;
		SoundInfo*	sound_info = SoundManager::getInstance()->createSoundInfo(file);
		if (sound_info != NULL) {
			pSound = SoundManager::getInstance()->createSound(sound_info);
			delete sound_info;
		} else {
			pSound = NULL;
		}
	}
	
	IDefineSound::~IDefineSound()
	{
		if (pSound != NULL) {
			delete pSound;
		}
		//printf("delete sound : %s\n", filepath);
	}
	
	Sound* IDefineSound::getSound()
	{
		return pSound;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	
	IDefineImage::IDefineImage(char const *file)
	{
		filepath = file;
		pImage = GFXManager::createImage(file);
	}
	
	IDefineImage::~IDefineImage()
	{
		if (pImage != NULL) {
			delete pImage;
		}
		//printf("delete image : %s\n", filepath);
	}
	
	Image* IDefineImage::getImage() 
	{
		return pImage;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	
	IDefineNumber::IDefineNumber(float n)
	{
		number = n;
	}
	
	IDefineNumber::~IDefineNumber()
	{
		//printf("delete number : %f\n", number);
	}
	
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// HeadDefine class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	HeadDefine::HeadDefine(HeaderDefineEnum def,
						   std::string index,
						   std::string value,
						   IDefineResource *pValueObject) 
	{
		m_define		= def;
		m_index			= index;
		m_value			= value;
		m_pValueObject	= pValueObject;
	}
	
	HeadDefine::~HeadDefine()
	{
		if (m_pValueObject!=NULL) {
			delete m_pValueObject;
		}
//		printf("delete head define : %s %s - %s\n",
//			   m_define.c_str(), 
//			   m_index.c_str(), 
//			   m_value.c_str());
	}
	
	HeaderDefineEnum&	HeadDefine::getDefineType()
	{
		return m_define;
	}
	
	IDefineResource*	HeadDefine::getDefineResource()
	{
		return m_pValueObject;
	}
	
	std::string&		HeadDefine::getIndex()
	{
		return m_index;
	}
	
	std::string&		HeadDefine::getValue()
	{
		return m_value;
	}

	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// Note class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Note::Note(CommandEnum const &command,
			   int line, 
			   int track, 
			   double begin_position,
			   double end_position,
			   HeadDefine *pHeadDefine,
			   std::string const &track_value)
	{
		m_line				= line;
		m_track				= track;
		
		m_command			= command;
		
		m_begin_position	= begin_position;
		m_end_position		= end_position;
		
		m_pHeadDefine		= pHeadDefine;
		m_track_value		= track_value;
	}
	
	Note::~Note()
	{
//		printf("delete note : %li %li : %s\n",
//			   m_line, 
//			   m_track, 
//			   m_track_value.c_str());

	}
	
	CommandEnum&	Note::getCommand()
	{
		return m_command;
	}
	
	HeadDefine*		Note::getHeadDefine()
	{
		return m_pHeadDefine;
	}
	
	std::string&	Note::getTrackValue()
	{
		return m_track_value;
	}

	
	int Note::getTrack(){
		return m_track;
	}
	
	int Note::getLine(){
		return m_line;
	}

	double Note::getBeginPosition() {
		return m_begin_position;
	}
	
	double Note::getEndPosition() {
		return m_end_position;
	}

	bool Note::validate()
	{
		if (stringEquals(m_command, CMD_INDEX_BMP_BG) ||
			stringEquals(m_command, CMD_INDEX_BMP_POOR) ||
			stringEquals(m_command, CMD_INDEX_BMP_LAYER) ||
			stringEquals(m_command, CMD_INDEX_BPM) ||
			stringEquals(m_command, CMD_INDEX_WAV_BG) ||
			stringEquals(m_command, CMD_INDEX_WAV_KEY_1P_) ||
			stringEquals(m_command, CMD_INDEX_WAV_KEY_2P_) ||
			stringEquals(m_command, CMD_INDEX_WAV_LONG_KEY_1P_) ||
			stringEquals(m_command, CMD_INDEX_WAV_LONG_KEY_2P_)) {
			return m_pHeadDefine != NULL;
		}
		return true;
	}

	/** 是否为长音 */
	bool Note::isLongKey() {
		return m_begin_position != m_end_position;
	}

	std::string Note::toString() {
		return std::string("Note").
		append(": line=").append(intToString(m_line)).
		append(", track=").append(intToString(m_track)).
		append(", command=").append(m_command).
		append(", track_value=").append(m_track_value).
		append(", begin=").append(intToString(m_begin_position)).
		append(", end=").append(intToString(m_end_position));
	}
	
	bool compareNote(Note *a, Note *b) {
		return b->getBeginPosition() > a->getBeginPosition();
	}
	
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////
	// BMSFile class
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	///*
	BMSFile::BMSFile(char const* file, BMSLoadListener *listener)
	{
		m_line_split_div	= G_LINE_SPLIT_DIV;
		m_beat_div			= G_LINE_SPLIT_DIV / 4;
		
		m_bms_file			= std::string(file);
		int name_split		= m_bms_file.rfind("/");
		if (name_split != std::string::npos) {
			m_bms_dir		= subString(m_bms_file, 0, name_split+1);
		} else {
			m_bms_dir		= "";
		}
		
		totoal_define_resource_count = 0;
		totoal_head_define_count = 0;
		totoal_note_count = 0;

		if (listener!=NULL) {
			listener->beginLoading();
		}
		
		int line_index	= 0;
		
		std::vector<std::string> lines;

		if (loadAllTextLine(file, lines) > 0)
		{
			for (std::vector<std::string>::iterator it=lines.begin(); it < lines.end(); ++it)
			{
				std::string &line = *it;
				
				if (stringStartWith(line, "#"))
				{
					line = line.substr(1, line.length()-1);
					
					// header field
					if (line.find(":") == std::string::npos) 
					{					
						const std::vector<std::string> &kv = stringSplitRegx(line, "\\s");

						if (kv.size() > 1) {
							if (initHeadInfo(kv[0], kv[1]) || 
								initHeadMap (kv[0], kv[1])) {
								//cout << "#" << kv[0] << " = " << kv[1] << endl;
							}
						}
					}
					// data field
					else 
					{
						const std::vector<std::string> &kv = stringSplit(line, ":", 2);
						if (kv.size() > 1) {
							if (initDataLine(kv[0], kv[1])) {
								//cout << "#" << kv[0] << " = " << kv[1] << endl;
							}
						}
					}
					
				}
				
				line_index++;
				
				if (listener!=NULL) {
					listener->loadline(lines.size(), line_index, line);
				}
				
			}
		}
		
		if (listener!=NULL) {
			listener->endLoading();
		}
		
		for (std::map<CommandEnum, std::vector<Note*> >::iterator it=data_note_table.begin(); 
			 it!=data_note_table.end(); ++it) 
		{
			std::vector<Note*> &tlist = (it->second);
			std::sort( tlist.begin(), tlist.end() , compareNote);
		}

		
		cout << "done init : " << m_bms_file << "\n" <<
		"	totoal_define_resource_count - "<<totoal_define_resource_count<<"\n"<<
		"	totoal_head_define_count ----- "<<totoal_head_define_count<<"\n"<<
		"	totoal_note_count ------------ "<<totoal_note_count<<"\n"<<
		endl;
	}
	
	BMSFile::~BMSFile()
	{
		int delete_define_resource_count = 0;
		int delete_head_define_count = 0;
		int delete_note_count = 0;
		
		for (std::map<CommandEnum, std::vector<Note*> >::iterator it=data_note_table.begin(); 
			 it!=data_note_table.end(); ++it) 
		{
			std::vector<Note*> &tlist = (it->second);
			for (std::vector<Note*>::iterator st = tlist.begin(); 
				 st!=tlist.end(); ++st) {
				delete (*st);
				delete_note_count ++;
			}
		}
		
		for (std::vector<HeadDefine*>::iterator it=resources.begin(); 
			 it!=resources.end(); ++it) {
			HeadDefine* def = (*it);
			delete def;
			delete_define_resource_count++;
		}
		
		cout << "done destory : " << m_bms_file << "\n" <<
		"	totoal_define_resource_count - "<<totoal_define_resource_count<<""<<"\n"<<
		"	totoal_head_define_count ----- "<<totoal_head_define_count<<"\n"<<
		"	totoal_note_count ------------ "<<totoal_note_count<<"\n"<<
		"	delete_define_resource_count - "<<delete_define_resource_count<<""<<"\n"<<
		"	delete_head_define_count ----- "<<delete_head_define_count<<"\n"<<
		"	delete_note_count ------------ "<<delete_note_count<<"\n"<<
		endl;

		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	
	float BMSFile::getLineSplitDiv()
	{
		return m_line_split_div;
	}
	
	float BMSFile::getBeatDiv()
	{
		return m_beat_div;
	}
	
	std::string BMSFile::getHeadInfo(HeaderInfoEnum const &head)
	{
		return header_info[head];
	}
	
	double BMSFile::getHeadInfoAsNumber(HeaderInfoEnum const &head)
	{
		std::string info = header_info[head];
		double ret = 0;
		stringToDouble(info, ret);
		return ret;
	}
	
	HeadDefine * BMSFile::getHeadDefine(CommandEnum const &command, std::string const &track_value)
	{
		if (stringEquals(command, CMD_INDEX_BMP_BG) ||
			stringEquals(command, CMD_INDEX_BMP_POOR) ||
			stringEquals(command, CMD_INDEX_BMP_LAYER)) {
			return header_img_map[track_value];
		}
		else if (stringEquals(command, CMD_INDEX_BPM)) {
			return header_bpm_map[track_value];
		}
		else if (stringEquals(command, CMD_INDEX_STOP)) {
			return header_stp_map[track_value];
		}
		else if (stringEquals(command, CMD_INDEX_WAV_BG) ||
				 stringEquals(command, CMD_INDEX_WAV_KEY_1P_) ||
				 stringEquals(command, CMD_INDEX_WAV_KEY_2P_) ||
				 stringEquals(command, CMD_INDEX_WAV_LONG_KEY_1P_) ||
				 stringEquals(command, CMD_INDEX_WAV_LONG_KEY_2P_) ) {
			return header_wav_map[track_value];
		}
		else {
			return NULL;
		}
	}
	
	std::list<Note*> BMSFile::getNoteList(CommandEnum const &track) 
	{
		std::list<Note*> ret;
		
		std::map<CommandEnum, std::vector<Note*> >::iterator it = data_note_table.find(track);
		
		if (it != data_note_table.end()) {
			std::vector<Note*> &track = (it->second);
			for (std::vector<Note*>::iterator st = track.begin(); st!=track.end(); ++st) {
				ret.push_back(*st);
			}
		}

		return ret;
	}
	
	std::list<Note*> BMSFile::getAllNoteList()
	{
		std::list<Note*> ret;
		
		for (std::map<CommandEnum, std::vector<Note*> >::iterator it=data_note_table.begin(); 
			 it!=data_note_table.end(); 
			 ++it) 
		{
			std::vector<Note*> &track = (it->second);
			for (std::vector<Note*>::iterator st = track.begin(); st!=track.end(); ++st) {
				ret.push_back(*st);
			}
		}
		
		//std::sort( ret.begin(), ret.end() , compareNote);
		
		ret.sort(compareNote);
		
		return ret;
	}
	

	
	double BMSFile::timeToPosition(double deta_time_ms, double bpm)
	{
		float	deta_min = (deta_time_ms/60000);
		return deta_min * bpm * m_beat_div;
	}
	
	double BMSFile::barToPosition(int line, float npos, float ncount) 
	{
		return (line * m_line_split_div) + m_line_split_div * npos / ncount;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	bool BMSFile::initHeadInfo(std::string const &k, std::string const &v)
	{
		HeaderInfoEnum key;
		if (valueOfHeaderInfo(k, key)) {
			header_info[key] = stringTrim(v);
			return true;
		}
		return false;
	}
	
	bool BMSFile::initHeadMap(std::string const &k, std::string const &v)
	{
		if (k.length() > 2)
		{
			HeaderDefineEnum define;
			if (valueOfHeaderDefine(k, define)) {
				std::string index = subString(k, k.length()-2);
				HeadDefine* define_value = createHeadDefine(define, index, v);
				if (define_value != NULL) {
					resources.push_back(define_value);
					if (stringEquals(define, HEAD_DEFINE_WAV)) {
						header_wav_map[index] = define_value;
					}
					else if (stringEquals(define, HEAD_DEFINE_BMP)) {
						header_img_map[index] = define_value;				
					}
					else if (stringEquals(define, HEAD_DEFINE_BPM)) {
						header_bpm_map[index] = define_value;
					}
					else if (stringEquals(define, HEAD_DEFINE_STOP)) {
						header_stp_map[index] = define_value;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	bool BMSFile::initDataLine(std::string const &c, std::string const &v)
	{
		if (c.length()==5)
		{
			int line = 0;
			if (stringToInt(c.substr(0, 3), line)) {
				std::string cmd = c.substr(3, 2);
				CommandEnum command;
				if (valueOfDataCommand(cmd, command)) {
					int track;
					if (stringToInt(cmd, track)) {
						int ncount = (v.length()>>1);
						for (int npos = 0; npos < ncount; npos++) {
							int i = (npos<<1);
							std::string nvalue = v.substr(i, 2);
							if (!stringEquals(nvalue, "00")) {
								Note* note = createNote(command, nvalue, line, track, npos, ncount);
								//new Note(line, track, npos, ncount, command, nvalue);
								if (note!=NULL && note->validate()){
									data_note_table[command].push_back(note);
								}
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	IDefineResource* BMSFile::createDefineResource(HeaderDefineEnum const &def, 
												   std::string const &value)
	{
		if (stringEquals(HEAD_DEFINE_WAV, def)) {
			totoal_define_resource_count++;
			return new IDefineSound((m_bms_dir+value).c_str());
		}
		if (stringEquals(HEAD_DEFINE_BMP, def)) {
			totoal_define_resource_count++;
			return new IDefineImage((m_bms_dir+value).c_str());
		}
		if (stringEquals(HEAD_DEFINE_BPM, def) ||
			stringEquals(HEAD_DEFINE_STOP, def)) {
			float n = 0;
			if (stringToFloat(value, n)) {
				totoal_define_resource_count++;
				return new IDefineNumber(n);
			} else {
				printf("error convert number, %s = %s\n", def.c_str(), value.c_str());
			}
		}
		return NULL;
	}
	
	HeadDefine* BMSFile::createHeadDefine(HeaderDefineEnum const &def,
										 std::string const &index,
										 std::string const &value)
	{
		IDefineResource* res = createDefineResource(def, value);
		totoal_head_define_count++;
		HeadDefine* ret = new HeadDefine(def, index, value, res);
		return ret;
	}
	
	
	Note* BMSFile::createNote(CommandEnum const &command,
							  std::string const &track_value,
							  int line, 
							  int track, 
							  int npos, 
							  int ncount)
	{
		HeadDefine* hd = getHeadDefine(command, track_value);
		
		totoal_note_count++;
		Note* note = new Note(command, line, track, 
							  barToPosition(line, npos, ncount), 
							  barToPosition(line, npos, ncount), 
							  hd, track_value);
		return note;
	}


}; // namespcace 

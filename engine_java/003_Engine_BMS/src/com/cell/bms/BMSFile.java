package com.cell.bms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.j2se.CAppBridge;
import com.cell.reflect.Parser;
import com.cell.util.EnumManager;
import com.cell.util.EnumManager.ValueEnum;

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
public class BMSFile
{	
	public static enum HeadInfo
	{
		PLAYER(String.class), 
		GENRE(String.class), 
		TITLE(String.class),
		ARTIST(String.class),
		BPM(Double.class), 
		PLAYLEVEL(Integer.class),
		RANK(Integer.class),
		TOTAL(Integer.class), 
		VOLWAV(Integer.class),
		STAGEFILE(String.class),
		;
		
		final public Class<?> value_type;
		
		private HeadInfo(Class<?> type) {
			value_type = type;
		}
	}
	
	public static enum HeadDefine
	{
		WAV,
		BMP,
		BPM,
		STOP,
	}
	
	public static enum DataCommand implements ValueEnum<String>
	{
		/** 背景音索引 WAVXX */
		INDEX_WAV_BG			("01"),
		/** 整数BPM变化。00h~FFh */
		BPM_CHANGE				("03"),
		
		/** 改变BGA的图片索引。	BMPXX */
		INDEX_BMP_BG			("04"),
		/** 改变POOR的图片索引。	BMPXX */
		INDEX_BMP_POOR			("06"),
		/** 改变Layer的图片索引。	BMPXX */
		INDEX_BMP_LAYER			("07"),

		/** 改变的BPM索引。表示小数的BPM变化或者大于255的BPM。前方定义的 #BPMXX */
		INDEX_BPM				("08"),
		/** STOP停止的时间索引。前方定义的 #STOPXX */
		INDEX_STOP				("09"),

		/** WAV KEY */
		INDEX_WAV_KEY_1P_		("1*"),
		INDEX_WAV_KEY_2P_		("2*"),
		INDEX_WAV_LONG_KEY_1P_	("5*"),
		INDEX_WAV_LONG_KEY_2P_	("6*"),

		;
		
		final String value;
		private DataCommand(String value) {
			this.value = value;
		}
		
		@Override
		public String getValue() {
			return value;
		}
		
		public static DataCommand valueOfDataCommand(String str) {
			if (str.length()==2) {
				if (str.startsWith("1")) {
					return INDEX_WAV_KEY_1P_;
				}
				if (str.startsWith("2")) {
					return INDEX_WAV_KEY_2P_;
				}
				if (str.startsWith("5")) {
					return INDEX_WAV_LONG_KEY_1P_;
				}
				if (str.startsWith("6")) {
					return INDEX_WAV_LONG_KEY_2P_;
				}
				return EnumManager.getEnum(DataCommand.class, str);
			}
			return null;
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------

	/**
	 * 定义在 Header 的 HeaderDefine 
	 * @author WAZA
	 */
	public class NoteDefine implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		final public HeadDefine			command;
		final public String				index;
		final public String				value;
		final public IDefineNote		value_object;
		
		public NoteDefine(
				HeadDefine	command,
				String		index,
				String		value) 
		{
			this.command	= command;
			this.index		= index;
			this.value		= value;

			switch(command) {
			case BMP:
				value_object = new IDefineImage(BMSFile.this, value);
				break;
			case WAV:
				value_object = new IDefineSound(BMSFile.this, value);
				break;
			case BPM:
			case STOP:
			default:
				value_object = null; 
			}
		}
		
		void dispose() {
			if (value_object!=null) {
				value_object.dispose();
			}
		}
	}

//	--------------------------------------------------------------------------------------------------------------

	/**
	 * 在数据区域内的音符数据
	 * @author WAZA
	 */
	public class Note implements Comparable<Note>, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		final public int			line;
		final public int			track;
		final public DataCommand	command;
		final public String			value; // index of define
		final public NoteDefine		note_define;
		
		private double 				begin_position;
		private double 				end_position;
		
		/** 是否已经按下，仅LongKey有效 */
		public boolean 				hited;		
		
		public Note(
				int line,
				int track, 
				double npos, 
				double ncount,
				DataCommand command,
				String value)
		{
			this.line		= line;
			this.track		= track;
			this.command	= command;
			this.value		= value;
			
			switch(command)
			{
			case INDEX_BMP_BG:
			case INDEX_BMP_POOR:
			case INDEX_BMP_LAYER:
				note_define = header_img_map.get(value);
				break;
			case INDEX_BPM:
				note_define = header_bpm_map.get(value);
				break;
			case INDEX_STOP:
				note_define = header_stp_map.get(value);
				break;
			case INDEX_WAV_BG:
			case INDEX_WAV_KEY_1P_:
			case INDEX_WAV_KEY_2P_:
			case INDEX_WAV_LONG_KEY_1P_:
			case INDEX_WAV_LONG_KEY_2P_:
				note_define = header_wav_map.get(value);
				break;
			default:
				note_define = null;
			}
			
			setBeginPosition(npos, ncount);
		}
		
		public double getBeginPosition() {
			return begin_position;
		}
		public double getEndPosition() {
			return end_position;
		}

		boolean validate()
		{
			switch(command)
			{
			case INDEX_BMP_BG:
			case INDEX_BMP_POOR:
			case INDEX_BMP_LAYER:
			case INDEX_BPM:
			case INDEX_STOP:
			case INDEX_WAV_BG:
			case INDEX_WAV_KEY_1P_:
			case INDEX_WAV_KEY_2P_:
			case INDEX_WAV_LONG_KEY_1P_:
			case INDEX_WAV_LONG_KEY_2P_:
				return note_define != null;
			default:
				return true;
			}
		}
		
		void setBeginPosition(double npos, double ncount) {
			this.end_position = 
			this.begin_position = 
				(line * LINE_SPLIT_DIV) + LINE_SPLIT_DIV * npos / ncount;
			this.hited = true;
		}

		void setEndPosition(double npos, double ncount) {
			this.end_position = 
				(line * LINE_SPLIT_DIV) + LINE_SPLIT_DIV * npos / ncount;
			this.hited = false;
		}
		
		/** 是否为长音 */
		public boolean isLongKey() {
			return this.begin_position != this.end_position;
		}
		
		@Override
		public int compareTo(Note o) {
			return (int)(this.begin_position - o.begin_position);
		}

		@Override
		public String toString() {
			return line + " : " + track + " : " + command + " : " + value + " : " + begin_position;
		}
		
	}


//	--------------------------------------------------------------------------------------------------------------

	public static interface LoadingListener 
	{
		public void beginLoading(BMSFile bms_file);
		public void endLoading(BMSFile bms_file);
		public void loadline(BMSFile bms_file, int max, int current, String line_data);
	}

	
//	--------------------------------------------------------------------------------------------------------------
	
	final public String 		bms_file;
	final public String 		bms_dir;
	
	/** 将每小节分割为多少份来处理 */
	final public double			LINE_SPLIT_DIV;
	
	/** 将每BEAT分割为多少份来处理 (4 BEAT = 1 LINE)*/
	final public double 		BEAT_DIV;
	
	HashMap<HeadInfo, String> 	header_info		= new HashMap<HeadInfo, String>();
	
	HashMap<String, NoteDefine>	header_wav_map	= new HashMap<String, NoteDefine>();
	HashMap<String, NoteDefine>	header_img_map	= new HashMap<String, NoteDefine>();
	HashMap<String, NoteDefine>	header_bpm_map	= new HashMap<String, NoteDefine>();
	HashMap<String, NoteDefine>	header_stp_map	= new HashMap<String, NoteDefine>();
	
	HashMap<DataCommand, ArrayList<Note>> 
								data_note_table = new HashMap<DataCommand, ArrayList<Note>>();
	
//	--------------------------------------------------------------------------------------------------------------

	public BMSFile(String file)
	{
		this(file, 256);
	}
	
	public BMSFile(String file, LoadingListener ... listener) 
	{
		this(file, 256, listener);
	}
	
	public BMSFile(String file, double line_div, LoadingListener ... listener)
	{
		LINE_SPLIT_DIV		= line_div;
		BEAT_DIV			= line_div / 4;
		
		bms_file			= file.replace('\\', '/');
		bms_dir				= file.substring(0, bms_file.lastIndexOf("/"));
		
		for (DataCommand cmd : DataCommand.values()) {
			data_note_table.put(cmd, new ArrayList<Note>());
		}
		
		if (listener!=null) {
			for (LoadingListener l : listener) {
				l.beginLoading(this);
			}
		}
		
		String[] lines = CIO.readAllLine(bms_file);
		int line_index = 0;
		for (String line : lines)
		{
			if (line.startsWith("#"))
			{
				try
				{
					line = line.substring(1);
					
					// header field
					if (!line.contains(":")) 
					{
						String[] kv = line.split("\\s", 2);
						if (kv.length > 1) {
							if (initHeadInfo(kv[0], kv[1])) {
								System.out.println("#"+
										CUtil.snapStringRightSize(kv[0], 12, ' ') + " " +
										CUtil.snapStringRightSize(kv[1], 12, ' ') + " ");
							}
							else if (initHeadMap(kv[0], kv[1])){
//								System.out.println("#"+
//										CUtil.snapStringRightSize(kv[0], 12, ' ') + " " +
//										CUtil.snapStringRightSize(kv[1], 12, ' ') + " ");
							}
						}
					}
					// data field
					else
					{
						String[] kv = line.split(":", 2);
						if (kv.length > 1) {
							if (initDataLine(kv[0], kv[1])) {
//								System.out.println("#"+
//										CUtil.snapStringRightSize(kv[0], 12, ' ') + " " +
//										CUtil.snapStringRightSize(kv[1], 12, ' ') + " ");
							}
						}
						
					}
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (listener!=null) {
				for (LoadingListener l : listener) {
					l.loadline(this, lines.length, line_index++, line);
				}
			}
		}
		
		if (listener!=null) {
			for (LoadingListener l : listener) {
				l.endLoading(this);
			}
		}
	}
	
	public byte[] getResourceData(String res_name) {
		return CIO.loadData(bms_dir + "/" + res_name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getHeadInfo(HeadInfo head) {
		return (T)Parser.stringToObject(header_info.get(head), head.value_type);
	}
	
	public ArrayList<Note> getNoteList(DataCommand track) {
		ArrayList<Note> ret = data_note_table.get(track);
		Collections.sort(ret);
		return CIO.cloneObject(ret);
	}

	public ArrayList<Note> getAllNoteList() {
		ArrayList<Note> ret = new ArrayList<Note>();
		for (DataCommand cmd : DataCommand.values()) {
			ArrayList<Note> track = data_note_table.get(cmd);
			ret.addAll(track);
		}
		Collections.sort(ret);
		return ret;
	}
	
	boolean initHeadInfo(String k, String v)
	{
		try{
			HeadInfo head = HeadInfo.valueOf(k);
			header_info.put(head, v);
			return true;
		}catch (Exception e) {}
		return false;
	}
	
	boolean initHeadMap(String k, String v)
	{
		if (k.length() > 2)
		{
			try{
				HeadDefine	define		= HeadDefine.valueOf(k.substring(0, k.length()-2));
				String		index		= k.substring(k.length()-2);
				NoteDefine	note_value	= new NoteDefine(define, index, v);
				switch(define)
				{
				case WAV:	header_wav_map.put(index, note_value); break;
				case BMP:	header_img_map.put(index, note_value); break;
				case BPM:	header_bpm_map.put(index, note_value); break;
				case STOP:	header_stp_map.put(index, note_value); break;
				}
				return true;
			}catch (Exception e) {}
		}
		return false;
	}
	
	boolean initDataLine(String c, String v)
	{
		if (c.length()==5)
		{
			try{
				int			line	= Integer.parseInt(c.substring(0, 3));
				String		cmd		= c.substring(3);
				DataCommand	command	= DataCommand.valueOfDataCommand(cmd);
				int			track	= Integer.parseInt(cmd);
				int			ncount	= (v.length()>>1);
				for (int npos = 0; npos < ncount; npos++) {
					int i = (npos<<1);
					String nvalue = v.substring(i, i+2);
					if (!nvalue.equals("00")) {
						Note note = new Note(line, track, npos, ncount, command, nvalue);
						if (note.validate()){
							data_note_table.get(command).add(note);
						}
					}
				}
				return true;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public double timeToPosition(double deta_time_ms, double bpm)
	{
		double	deta_min = (deta_time_ms/60000);
		return deta_min * bpm * BEAT_DIV;
	}
	
	
	public void dispose()
	{
		for (NoteDefine define : header_wav_map.values()) {
			try{
				define.dispose();
			}catch(Exception err){
				System.err.println(err.getMessage());
			}
		}
		for (NoteDefine define : header_img_map.values()) {
			try{
				define.dispose();
			}catch(Exception err){
				System.err.println(err.getMessage());
			}
		}
		for (NoteDefine define : header_bpm_map.values()) {
			try{
				define.dispose();
			}catch(Exception err){
				System.err.println(err.getMessage());
			}
		}
		for (NoteDefine define : header_stp_map.values()) {
			try{
				define.dispose();
			}catch(Exception err){
				System.err.println(err.getMessage());
			}
		}
	}
	
	
	
}


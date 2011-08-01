package com.cell.bms.file
{
	import com.cell.util.Map;
	import com.cell.util.StringUtil;

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
	//	--------------------------------------------------------------------------------------------------------------
	
	//	--------------------------------------------------------------------------------------------------------------
	
	//	--------------------------------------------------------------------------------------------------------------
	
		/** 将每小节分割为多少份来处理 */
		private var LINE_SPLIT_DIV	: Number;
		
		/** 将每BEAT分割为多少份来处理 (4 BEAT = 1 LINE)*/
		private var BEAT_DIV		: Number;
		
		
		private var header_info		: Map	= new Map();
		
		private var	header_wav_map	: Map	= new Map();
		private var	header_img_map	: Map	= new Map();
		private var	header_bpm_map	: Map	= new Map();
		private var	header_stp_map	: Map	= new Map();
		
		/**HashMap<DataCommand, ArrayList<Note>> */
		private var data_note_table : Map	= new Map();
		
		private var res_manager		: IDefineResourceManager;
		
	//	--------------------------------------------------------------------------------------------------------------
	
		public function BMSFile(properties:String, 
								res_manager:IDefineResourceManager,
								line_div:int=256)
		{
			this.LINE_SPLIT_DIV		= line_div;
			this.BEAT_DIV			= line_div / 4;
			this.res_manager		= res_manager;
			for each (var cmd : String in DataCommand.values()) {
				data_note_table.put(cmd, new Array());
			}
			
			var lines : Array = StringUtil.splitString(properties, "\n");
			var line_index : int = 0;
			for each (var line : String in lines)
			{
				if (line.substring(0, 1) == "#")
				{
					line = line.substring(1);
					
					// header field
					if (line.indexOf(":") < 0) 
					{
						var kv : Array = line.split("\\s", 2);
						if (kv.length > 1) { 
							if (initHeadResource(kv[0], kv[1])){
							}
							else if (initHeadInfo(kv[0], kv[1])) {
							}
						}
					}
					// data field
					else
					{
						var kc : Array = line.split(":", 2);
						if (kc.length > 1) {
							if (initDataLine(kc[0], kc[1])) {
							}
						}
					}
				}
			}
		}
		
//		public byte[] getResourceData(String res_name) {
//			return CIO.loadData(bms_dir + "/" + res_name);
//		}
//		
//		@SuppressWarnings("unchecked")
//		public <T> T getHeadInfo(HeadInfo head) {
//			return (T)Parser.stringToObject(header_info.get(head), head.value_type);
//		}
//		
//		public ArrayList<Note> getNoteList(DataCommand track) {
//			ArrayList<Note> ret = data_note_table.get(track);
//			Collections.sort(ret);
//			return CIO.cloneObject(ret);
//		}
//	
//		public ArrayList<Note> getAllNoteList() {
//			ArrayList<Note> ret = new ArrayList<Note>();
//			for (DataCommand cmd : DataCommand.values()) {
//				ArrayList<Note> track = data_note_table.get(cmd);
//				ret.addAll(track);
//			}
//			Collections.sort(ret);
//			return ret;
//		}
		
		protected function initHeadInfo(k:String, v:String) : Boolean
		{
			header_info.put(k, v);
			return true;
		}
		
		protected function initHeadResource(k:String, v:String) : Boolean
		{
			if (k.length > 2)
			{
				var	define		: String 		= k.substring(0, k.length-2);
				var	index		: String		= k.substring(k.length-2);
				var value_res 	: IDefineResource	= null;
				if (define == HeadDefine.BMP) {
					value_res = res_manager.createDefineResource(define, index);
				}
				else if (define == HeadDefine.WAV) {
					value_res = res_manager.createDefineResource(define, index);
				}
				var	note_value : NoteDefine	= new NoteDefine(define, index, v, value_res);
				switch(define)
				{
					case HeadDefine.WAV:	
						header_wav_map.put(index, note_value); 
						return true;
					case HeadDefine.BMP:	
						header_img_map.put(index, note_value); 
						return true;
					case HeadDefine.BPM:	
						header_bpm_map.put(index, note_value); 
						return true;
					case HeadDefine.STOP:	
						header_stp_map.put(index, note_value); 
						return true;
				}
			}
			return false;
		}
		
		protected function initDataLine(c:String, v:String) : Boolean
		{
			if (c.length == 5)
			{
				var line	: int		= int(c.substring(0, 3));
				var cmd		: String	= c.substring(3);
				var	command	: String	= DataCommand.valueOfDataCommand(cmd);
				var track	: int		= int(cmd);
				var ncount	: int		= (v.length/2);
				for (var npos : int = 0; npos < ncount; npos++) {
					var i : int = (npos<<1);
					var nvalue : String = v.substring(i, i+2);
					if (nvalue != "00") {
						var note : Note = createNote(line, track, npos, ncount, command, nvalue);
						if (note.validate()){
							data_note_table.get(command).add(note);
						}
					}
				}
				return true;
			}
			return false;
		}
		
		
		
		public function createNote(
			line : int,
			track : int, 
			npos : Number, 
			ncount : Number,
			command : String,
			value : String) : Note
		{
			var note_define : NoteDefine = null;
			var begin_pos	: Number = getNotePosition(line, npos, ncount);
			switch(command)
			{
				case DataCommand.INDEX_BMP_BG:
				case DataCommand.INDEX_BMP_POOR:
				case DataCommand.INDEX_BMP_LAYER:
					note_define = header_img_map.get(value);
					break;
				case DataCommand.INDEX_BPM:
					note_define = header_bpm_map.get(value);
					break;
				case DataCommand.INDEX_STOP:
					note_define = header_stp_map.get(value);
					break;
				case DataCommand.INDEX_WAV_BG:
				case DataCommand.INDEX_WAV_KEY_1P_:
				case DataCommand.INDEX_WAV_KEY_2P_:
				case DataCommand.INDEX_WAV_LONG_KEY_1P_:
				case DataCommand.INDEX_WAV_LONG_KEY_2P_:
					note_define = header_wav_map.get(value);
					break;
				default:
					note_define = null;
			}
			
			var note : Note = new Note(line, track, begin_pos, begin_pos, command, value, note_define);
			
			return note;
		}
		
		final protected function getNotePosition(line : int, npos : Number, ncount : Number) : Number
		{
			return (line * LINE_SPLIT_DIV) + LINE_SPLIT_DIV * npos / ncount;
		}
		
//		public function timeToPosition(double deta_time_ms, double bpm)
//		{
//			double	deta_min = (deta_time_ms/60000);
//			return deta_min * bpm * BEAT_DIV;
//		}
		
		public function dispose() : void
		{
			for each (var define : NoteDefine in header_wav_map) {
				define.dispose();
			}
			for each (var define1 : NoteDefine in header_img_map) {
				define1.dispose();
			}
			for each (var define2 : NoteDefine in header_bpm_map) {
				define2.dispose();
			}
			for each (var define3 : NoteDefine in header_stp_map) {
				define3.dispose();
			}
		}
		
	}
}


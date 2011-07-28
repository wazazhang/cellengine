package com.cell.bms.file
{
	public class DataCommand
	{
		/** 背景音索引 WAVXX */
		public static const INDEX_WAV_BG			: String = ("01");
		/** 整数BPM变化。00h~FFh */
		public static const BPM_CHANGE				: String = ("03");
		
		/** 改变BGA的图片索引。	BMPXX */
		public static const INDEX_BMP_BG			: String = ("04");
		/** 改变POOR的图片索引。	BMPXX */
		public static const INDEX_BMP_POOR			: String = ("06");
		/** 改变Layer的图片索引。	BMPXX */
		public static const INDEX_BMP_LAYER			: String = ("07");
		
		/** 改变的BPM索引。表示小数的BPM变化或者大于255的BPM。前方定义的 #BPMXX */
		public static const INDEX_BPM				: String = ("08");
		/** STOP停止的时间索引。前方定义的 #STOPXX */
		public static const INDEX_STOP				: String = ("09");
		
		/** WAV KEY */
		public static const INDEX_WAV_KEY_1P_		: String = ("1*");
		/** WAV KEY */
		public static const INDEX_WAV_KEY_2P_		: String = ("2*");
		/** WAV KEY */
		public static const INDEX_WAV_LONG_KEY_1P_	: String = ("5*");
		/** WAV KEY */
		public static const INDEX_WAV_LONG_KEY_2P_	: String = ("6*");
		
		public static function values() : Array
		{
			return new Array(
				INDEX_WAV_BG,
				BPM_CHANGE,
				INDEX_BMP_BG,
				INDEX_BMP_POOR,
				INDEX_BMP_LAYER,
				INDEX_BPM,
				INDEX_STOP,
				
				INDEX_WAV_KEY_1P_,
				INDEX_WAV_KEY_2P_,
				INDEX_WAV_LONG_KEY_1P_,
				INDEX_WAV_LONG_KEY_2P_);
		}
		
		public static function valueOfDataCommand(str:String) : String
		{
			if (str.length==2) {
				var first : String = str.substring(0, 1);
				if (first == "1") {
					return INDEX_WAV_KEY_1P_;
				}
				if (first == "2") {
					return INDEX_WAV_KEY_2P_;
				}
				if (first == "5") {
					return INDEX_WAV_LONG_KEY_1P_;
				}
				if (first == "6") {
					return INDEX_WAV_LONG_KEY_2P_;
				}
				return str;
			}
			return null;
		}
	}
}
//
//  MFUtil.h
//
//  Created by wazazhang on 12-4-18.
//
#ifndef _MF_UTIL
#define _MF_UTIL

#include "MFType.h"
#include <string>
#include <vector>
#include <fstream>
#include <stdarg.h>

#include "cocos2d.h"

namespace mf
{
	using namespace std;

	// T 缓冲区类型
	// N 缓冲区大小
    template <typename T, int N>
	struct CC_DLL  Format
	{
		Format(T const *format, ...)
		{
			va_list argptr;
			va_start(argptr, format);
			vsprintf(this->Buffer, format, argptr);
			va_end(argptr);
		}

		T Buffer[N];

	}; // struct Format

    #define MF_INT2STR(i)	(::mf::Format<char, 1024>("%d", (i)).Buffer)
    #define MF_FLOAT2STR(f)	(::mf::Format<char, 1024>("%f", (f)).Buffer)



	inline unsigned long mfNextPOT(unsigned long x)
	{
		x = x - 1;
		x = x | (x >> 1);
		x = x | (x >> 2);
		x = x | (x >> 4);
		x = x | (x >> 8);
		x = x | (x >>16);
		return x + 1;
	}





	///////////////////////////////////////////////////////////////////////////////////////
	// array util
	///////////////////////////////////////////////////////////////////////////////////////


	/**
	* get [key : value] form str <\b> 
	* 
	* str = "FORCE=A"
	* key = "FORCE="
	* value = "A"
	* 
	* @param str
	* @param key
	* @param value
	*/
	CC_DLL extern bool stringGetKeyValue(
		string const &src, 
		string const &key, 
		string &value);

	// {}{}{}
	CC_DLL extern bool stringFindRegionFIFO(string const &src, 
		string const &begin, 
		string const &end, 
		int &beginIndex, 
		int &endIndex);

	// {{{}}}
	CC_DLL extern bool stringFindRegionFILO(
		string const &src, 
		string const &begin, 
		string const &end, 
		int &beginIndex, 
		int &endIndex);

	/**
	 * "{0%s} attack the {1%s}, getting {3%d} damage !"
	 * 参数类型 "{0%format}" 
	 * 参数包含在花括号内，花括号内地一个数字代表程序中的第几个参数。之后为printf的类型参数。
	 * format 参考 printf
	 */
	CC_DLL extern string stringFormat(string const src, ... );


	///////////////////////////////////////////////////////////////////////////////////////
	// string util
	///////////////////////////////////////////////////////////////////////////////////////


	CC_DLL extern bool				stringEquals(string const &src, string const &dst);


	////////////////////////////////////////////////////////////
	// split and replace
	CC_DLL extern vector<string>	stringSplit(string const &str,
										string const &separator);

	CC_DLL extern vector<string>	stringSplit(string const &str,
										string const &separator,
										int limit);

	CC_DLL extern vector<string>	stringSplitBlank(string const &str,
											 int limit);

	CC_DLL extern string			stringReplace(string const &str,
										  string const &target,
										  string const &replace);

	CC_DLL extern string			stringReplace(string const &str,
										  string const &target,
										  string const &replace,
										  int limit);


	////////////////////////////////////////////////////////////
	// convert

	CC_DLL extern string			intToString(long value);

	CC_DLL extern string			floatToString(double value);

	// 数字转换整型
	CC_DLL extern bool				stringToInt(string const &str, int &out_value);
	CC_DLL extern bool				stringHexToU32(string const &str, u32 &out_value);
	CC_DLL extern bool				stringHexToU64(string const &str, u64 &out_value);
	CC_DLL extern bool				stringToLong(string const &str, long &out_value);

	// 数字转换成小数
	CC_DLL extern bool				stringToPoint(string const &str, double &out_value);
	// 数字转换成浮点数
	CC_DLL extern bool				stringToFloat(string const &str, float &out_value);
	CC_DLL extern bool				stringToDouble(string const &str, double &out_value);

	CC_DLL extern bool				stringToBool(string const &str, bool &out_value);


	// 数字转换整型
	CC_DLL extern int				stringToInt(string const &str);
	CC_DLL extern u32				stringHexToU32(string const &str);
	CC_DLL extern u64				stringHexToU64(string const &str);
	CC_DLL extern long				stringToLong(string const &str);
	// 数字转换成小数
	CC_DLL extern double			stringToPoint(string const &str);
	// 数字转换成浮点数
	CC_DLL extern float				stringToFloat(string const &str);
	CC_DLL extern double			stringToDouble(string const &str);

	CC_DLL extern bool				stringToBool(string const &str);

	////////////////////////////////////////////////////////////
	// regex split and replace
	CC_DLL extern vector<string>	stringSplitRegx(string const &str,
											string const &regex);

	CC_DLL extern string			stringReplaceRegx(string const &str,
											  string const &regex,
											  string const &replace);

	CC_DLL extern string			stringTrim(string const &str);

    CC_DLL extern bool				stringStartWith(string const &str, string const &prefix);

    CC_DLL extern bool				stringEndWith(string const &str, string const &suffix);

	CC_DLL extern int				stringGetKeyValue(string const &s,
											  string const &k,
											  string const &separator,
											  string &v);

	////////////////////////////////////////////////////////////
	/**左闭右开， subString(“0123”, 1, 3) = "12"*/
	CC_DLL extern string	subString(string const &str, int begin_index, int end_index);

	CC_DLL extern string	subString(string const &str, int begin_index);



	///////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////
	// resource
	CC_DLL extern void*        loadData(char const *filename, int &out_size);
	// >0 is OK
	CC_DLL extern int          loadAllText(char const *filename, std::string &outString);
	// >0 is OK
	CC_DLL extern int          loadAllTextLine(char const *filename, std::vector<std::string> &lines);

	///////////////////////////////////////////////////////////////////////////////////////


	//CC_DLL extern string stringFromUnicode(const wstring& str)  ;


	//CC_DLL extern wstring stringToUnicode(const string& str)  ;


	///////////////////////////////////////////////////////////////////////////////////////
	// TextReader
	///////////////////////////////////////////////////////////////////////////////////////
	class CC_DLL TextReader
	{
	private:
		string	buffer;
		int		pos;

	public:

		TextReader(string const &src) ;

		bool	read(string &ret);

		bool	readLen(int len, string &ret);

		void	skip(int n);

		int		position();

		void	setPosition(int pos);

		int		remain();

	}; // class TextReader


	///////////////////////////////////////////////////////////////////////////////////////
	// TextDeserialize
	///////////////////////////////////////////////////////////////////////////////////////

	class CC_DLL TextDeserialize
	{
	private:
		static string getNext(TextReader &input) ;
	public:

		static string	getString(TextReader &input);

		static bool		getBoolean(TextReader &input);

		static char		getByte(TextReader &input);

		static short	getShort(TextReader &input);

		static int		getInt(TextReader &input);

		static long		getLong(TextReader &input);

		static float	getFloat(TextReader &input) ;

		static double	getDouble(TextReader &inpur) ;


		//////////////////////////////////////////////////////////////////////////////////////
		// get array

		static vector<string>	getStrings(TextReader &input);

		static vector<bool>		getBooleans(TextReader &input);

		static vector<char>		getBytes(TextReader &input);

		static vector<short>	getShorts(TextReader &input);

		static vector<int>		getInts(TextReader &input);

		static vector<long>		getLongs(TextReader &input);

		static vector<float>	getFloats(TextReader &input);

		static vector<double>	getDoubles(TextReader &input);


	}; // class TextDeserialize

	#define MF_LOCAL_CHS   "chs"

	class CC_DLL StringConvert
	{

	public:

		//// local[chs]
		//static std::string ws2s(const std::wstring& ws, const char* local = MF_LOCAL_CHS);
		//// local[chs]
		//static std::wstring s2ws(const std::string& s, const char* local = MF_LOCAL_CHS);


		static string	wsToUTF8(const mfstring& src);
		static mfstring	utf8ToWS(const string& src);

		static void		wsToUTF8(const mfstring& src, size_t index, size_t length, string& dest);
		static void		utf8ToWS(const string& src, size_t index, size_t length, mfstring& dest);

		//! convert from wstring to UTF8 using self-coding-converting
		static void		wsToUTF8Stream(const mfstring& src, string& dest);
		static void		utf8StreamToWS(const string& src, mfstring& dest);

	};



}; // namespace mf


#endif // #define _MF_UTIL

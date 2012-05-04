//
//  MFUtil.h
//
//  Created by wazazhang on 12-4-18.
//
#ifndef _MF_UTIL
#define _MF_UTIL

#include <string>
#include <vector>
#include <fstream>
#include <stdarg.h>

namespace mf
{
	using namespace std;

    template <typename T, int N>
	struct Format
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


    #define _INT2STR(i)		(::mf::Format<char,1024>("%d", (i)).Buffer)
    #define _FLOAT2STR(f)	(::mf::Format<char,1024>("%f", (f)).Buffer)


	template <class Class, typename T>
	inline bool instanceof(T const &object)
	{
		return dynamic_cast<Class const *>(&object);
	}


	///////////////////////////////////////////////////////////////////////////////////////
	// string util
	///////////////////////////////////////////////////////////////////////////////////////

	extern bool				stringEquals(string const &src, string const &dst);


	////////////////////////////////////////////////////////////
	// split and replace
	extern vector<string>	stringSplit(string const &str,
										string const &separator);

	extern vector<string>	stringSplit(string const &str,
										string const &separator,
										int limit);

	extern vector<string>	stringSplitBlank(string const &str,
											 int limit);

	extern string			stringReplace(string const &str,
										  string const &target,
										  string const &replace);

	extern string			stringReplace(string const &str,
										  string const &target,
										  string const &replace,
										  int limit);


	////////////////////////////////////////////////////////////
	// convert

	extern string			intToString(long value);

	extern string			floatToString(double value);

	// 数字转换整型
	extern bool				stringToInt(string const &str, int &out_value);
	extern bool				stringToLong(string const &str, long &out_value);
	// 数字转换成小数
	extern bool				stringToPoint(string const &str, double &out_value);
	// 数字转换成浮点数
	extern bool				stringToFloat(string const &str, float &out_value);
	extern bool				stringToDouble(string const &str, double &out_value);

	extern bool				stringToBool(string const &str, bool &out_value);


	// 数字转换整型
	extern int				stringToInt(string const &str);
	extern long				stringToLong(string const &str);
	// 数字转换成小数
	extern double			stringToPoint(string const &str);
	// 数字转换成浮点数
	extern float			stringToFloat(string const &str);
	extern double			stringToDouble(string const &str);

	extern bool				stringToBool(string const &str);

	////////////////////////////////////////////////////////////
	// regex split and replace
	extern vector<string>	stringSplitRegx(string const &str,
											string const &regex);

	extern string			stringReplaceRegx(string const &str,
											  string const &regex,
											  string const &replace);

	extern string			stringTrim(string const &str);

    extern bool				stringStartWith(string const &str, string const &prefix);

    extern bool				stringEndWith(string const &str, string const &suffix);

	extern int				stringGetKeyValue(string const &s,
											  string const &k,
											  string const &separator,
											  string &v);

	////////////////////////////////////////////////////////////
	/**左闭右开， subString(“0123”, 1, 3) = "12"*/
	extern string	subString(string const &str, int begin_index, int end_index);

	extern string	subString(string const &str, int begin_index);



	///////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////
	// resource
	extern void*        loadData(char const *filename, int &out_size);
	// >0 is OK
	extern int          loadAllText(char const *filename, std::string &outString);
	// >0 is OK
	extern int          loadAllTextLine(char const *filename, std::vector<std::string> &lines);

	///////////////////////////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////////////////////////
	// TextReader
	///////////////////////////////////////////////////////////////////////////////////////
	class TextReader
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

	class TextDeserialize
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

}; // namespace mf


#endif // #define _MF_UTIL

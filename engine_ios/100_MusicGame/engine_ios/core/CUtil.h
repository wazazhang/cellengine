//
//  CUtil.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_UTIL
#define _COM_CELL_UTIL

#include <string>
#include <vector>
#include <fstream>


#include "CType.h"

namespace com_cell
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
	
	
    #define _INT2STR(i)		(::com_cell::Format<char,1024>("%d", (i)).Buffer)
    #define _FLOAT2STR(f)	(::com_cell::Format<char,1024>("%f", (f)).Buffer)

    
	///////////////////////////////////////////////////////////////////////////////////////
	// time date
	///////////////////////////////////////////////////////////////////////////////////////
	
	extern double       getCurrentTime();
    
	
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
	
	extern string			stringReplace(string const &str,
										  string const &target,
										  string const &replace);
	
	extern string			stringReplace(string const &str,
										  string const &target,
										  string const &replace,
										  int limit);
	////////////////////////////////////////////////////////////
	// regex split and replace
	extern vector<string>	stringSplitRegx(string const &str, 
											string const &regex);

	extern string			stringReplaceRegx(string const &str,
											  string const &regex,
											  string const &replace);
	
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
	
	
	////////////////////////////////////////////////////////////
	
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
	// random and Number
	///////////////////////////////////////////////////////////////////////////////////////
	
	extern int          randomInt();
    
	extern void         randomSeed(int seed);			
    
	
	
}; // namespace com.cell


#endif // #define _COM_CELL_UTIL
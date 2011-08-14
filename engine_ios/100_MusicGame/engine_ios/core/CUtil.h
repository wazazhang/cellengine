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
	extern double       getCurrentTime();
    
	///////////////////////////////////////////////////////////////////////////////////////
	// string util
	extern bool			stringEquals		(std::string const &src, 
											 std::string const &dst);
	
	extern std::vector<std::string> 
                        stringSplit			(std::string const &str, 
											 std::string const &separator);
	
	extern std::string  intToString			(long value);
	extern std::string  floatToString		(double value);
	
	extern int          stringToInt			(std::string const &str, long &value);
	extern int          stringToFloat		(std::string const &str, double &value);
	
	extern std::string  stringTrim			(std::string const &str);
	
    extern bool         stringStartWidth	(std::string const &str, std::string const &prefix);
    extern bool         stringEndWidth		(std::string const &str, std::string const &suffix);
    
	extern int          stringGetKeyValue	(std::string const &s, 
											 std::string const &k, 
											 std::string const &separator, 
											 std::string &v);
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	// random
	extern int          randomInt();
    
	extern void         randomSeed(int seed);			
    
	
	
}; // namespace com.cell


#endif // #define _COM_CELL_UTIL
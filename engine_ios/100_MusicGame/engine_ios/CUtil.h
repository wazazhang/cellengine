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
    
	extern double       getCurrentTime();
    
    
    
	extern int          saveRMSData(char const *filename, char const *ext, const void* bytes, int size);
	extern int          saveRMSAllText(char const *filename, char const *ext, std::string const &str);
	extern int          saveRMSAllTextLine(char const *filename, char const *ext, std::vector<std::string> const &lines);
    
	extern char*        loadRMSData(char const *filename, char const *ext, int &size);
	extern int          loadRMSAllText(char const *filename, char const *ext, std::string &outString);
	extern int          loadRMSAllTextLine(char const *filename, char const *ext, std::vector<std::string> &lines);
	
	extern char*        loadData(char const *filename, char const *ext, int &size);
	extern int          loadAllText(char const *filename, char const *ext, std::string &outString);
	extern int          loadAllTextLine(char const *filename, char const *ext, std::vector<std::string> &lines);
    
	extern std::vector<std::string> 
                        stringSplit(std::string const &str, std::string const &separator);
	
	extern std::string  intToString(long value);
	extern std::string  floatToString(double value);
	
	extern int          stringToInt(std::string const &str, long &value);
	extern int          stringToFloat(std::string const &str, double &value);
	
	extern std::string  stringTrim(std::string const &str);
	
	extern int          stringGetKeyValue(std::string const &s, std::string const &k, std::string const &separator, std::string &v);
	
	extern int          randomInt();
    
	extern void         randomSeed(int seed);			
    
	
	
}; // namespace com.cell


#endif // #define _COM_CELL_UTIL
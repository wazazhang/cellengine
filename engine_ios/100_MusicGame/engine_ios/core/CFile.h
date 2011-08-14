//
//  CFile.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-15.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_FILE
#define _COM_CELL_FILE

#include <string>
#include <vector>
#include <fstream>

#include "CType.h"

namespace com_cell
{
	///////////////////////////////////////////////////////////////////////////////////////
	// application data
	extern int          saveRMSData(char const *filename, const void* bytes, int size);
	extern int          saveRMSAllText(char const *filename, std::string const &str);
	extern int          saveRMSAllTextLine(char const *filename, std::vector<std::string> const &lines);
    
	extern void*        loadRMSData(char const *filename, int &size);
	extern int          loadRMSAllText(char const *filename, std::string &outString);
	extern int          loadRMSAllTextLine(char const *filename, std::vector<std::string> &lines);
	
	///////////////////////////////////////////////////////////////////////////////////////
	// resource
	extern void*        loadData(char const *filename, int &size);
	extern int          loadAllText(char const *filename, std::string &outString);
	extern int          loadAllTextLine(char const *filename, std::vector<std::string> &lines);
	
	
}; // namespace com.cell


#endif // #define _COM_CELL_FILE
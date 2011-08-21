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
	// >0 is OK
	extern int          saveRMSData(char const *filename, const void* bytes, int size);
	// >0 is OK
	extern int          saveRMSAllText(char const *filename, std::string const &str);
	// >0 is OK
	extern int          saveRMSAllTextLine(char const *filename, std::vector<std::string> const &lines);
    
	extern void*        loadRMSData(char const *filename, int &out_size);
	// >0 is OK
	extern int          loadRMSAllText(char const *filename, std::string &outString);
	// >0 is OK
	extern int          loadRMSAllTextLine(char const *filename, std::vector<std::string> &lines);
	
	///////////////////////////////////////////////////////////////////////////////////////
	// resource
	extern void*        loadData(char const *filename, int &out_size);
	// >0 is OK
	extern int          loadAllText(char const *filename, std::string &outString);
	// >0 is OK
	extern int          loadAllTextLine(char const *filename, std::vector<std::string> &lines);
	
}; // namespace com.cell



#endif // #define _COM_CELL_FILE
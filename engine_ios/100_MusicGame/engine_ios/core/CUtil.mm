//
//  CUtil.cpp
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CUtil.h"

#include <Foundation/NSData.h>

namespace com_cell
{
	
	double getCurrentTime()
	{
		CFTimeInterval time = CFAbsoluteTimeGetCurrent();
		
		return (double)time;
	}
  
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	bool stringEquals(std::string const &src, std::string const &dst)
	{
		return src.compare(dst) == 0;
	}

	
	std::vector<std::string> stringSplit(std::string const &str, std::string const &separator)
	{
		std::vector<std::string> lines;
        
        for(int index=0, i=0; i<str.length(); i++)
        {
            i = str.find(separator, index);
            
            if(i != std::string::npos)
            {
                const std::string &line = str.substr(index, i-index);
                lines.push_back(line);
                index = i + 1;
                
                continue;
            }
            else
            {
                const std::string &line = str.substr(index, str.length()-index);
                lines.push_back(line);
				
                break;
            }
        }
        
		return lines;
        
	}
    
	std::string intToString(long value)
	{
		return _INT2STR(value);
	}
	
	std::string floatToString(double value)
	{
		return _FLOAT2STR(value);
	}
    
	int stringToInt(std::string const &str, long &value)
	{
		NSString *nsstr = [NSString stringWithUTF8String:str.c_str()];
		value = [nsstr intValue];
		//[nsstr release];
		return 1;
	}
	
	int stringToFloat(std::string const &str, double &value)
	{
		NSString *nsstr = [NSString stringWithUTF8String:str.c_str()];
		value = [nsstr floatValue];
		//[nsstr release];
		return 1;
	}
	
	std::string stringTrim(std::string const &str)
	{
		NSString *nsstr = [NSString stringWithUTF8String:str.c_str()];
		
		nsstr = [nsstr stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
		
		const std::string &ret = [nsstr UTF8String];
        
		//[nsstr release];
        
		return ret;
	}
	
	
	int stringGetKeyValue(std::string const &s, std::string const &k, std::string const &separator, std::string &v)
	{
		int loc1 = s.find(k);
		if (loc1 != std::string::npos)
		{
			int loc2 = s.find(separator, loc1+k.length());
			if (loc2 != std::string::npos)
			{
				if (loc1 + k.length() == loc2)
				{
					int index = loc2+separator.length();
					v = stringTrim(s.substr(index));
					return index;
				}
			}
		}
		return std::string::npos;
	}
    
    bool stringStartWidth(std::string const &str, std::string const &prefix)
    {
        return str.find(prefix) == 0;
    }
    
    bool stringEndWidth(std::string const &str, std::string const &suffix)
    {
        return str.rfind(suffix) == str.length() - suffix.length();
    }

	
	int randomInt()
	{
		return (int)rand();
	}
	
	void randomSeed(int seed)
	{
		srand(seed);
	}
	
	
}; 


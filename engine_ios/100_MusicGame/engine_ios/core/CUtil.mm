//
//  CUtil.cpp
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CUtil.h"

#include <Foundation/NSData.h>

#include "RegexKitLite.h"

namespace com_cell
{
	
	///////////////////////////////////////////////////////////////////////////////////////
	// time date
	///////////////////////////////////////////////////////////////////////////////////////
	
	double getCurrentTime()
	{
		CFTimeInterval time = CFAbsoluteTimeGetCurrent();
		
		return (double)time*1000;
	}
  
	
	///////////////////////////////////////////////////////////////////////////////////////
	// string util
	///////////////////////////////////////////////////////////////////////////////////////
	
	bool stringEquals(string const &src, string const &dst)
	{
		return src.compare(dst) == 0;
	}

	vector<string> stringSplit(string const &str, 
										 string const &separator)
	{
		return stringSplit(str, separator, INT32_MAX);
	}
	
	vector<string> stringSplit(string const &str, 
										 string const &separator,
										 int limit)
	{
		int count = 0;
		vector<string> lines;
        
        for(int index=0, i=0; i<str.length(); i++)
        {
			if (count < limit) 
			{
				i = str.find(separator, index);
				
				if(i != string::npos)
				{
					const string &line = str.substr(index, i-index);
					lines.push_back(line);
					index = i + 1;
					continue;
				}
				else
				{
					const string &line = str.substr(index, str.length()-index);
					lines.push_back(line);
					break;
				}
			}
			else 
			{
				lines.push_back(subString(str, i, str.length()));
				break;
			}
        }
        
		return lines;
	}
	
	
	string stringReplace(string const &str,
						 string const &target,
						 string const &replace)
	{
		return stringReplace(str, target, replace, INT32_MAX);
	}
	
	string stringReplace(string const &str,
						 string const &target,
						 string const &replace, 
						 int limit)
	{
		if (str.find(target) == string::npos) {
			return str;
		}
		
		int count = 0;
		
		string sb;
		
		for (size_t i = 0; i < str.length(); i++)
		{
			if (count < limit) {
				size_t dst = str.find(target, i);
				if (dst != string::npos) {
					sb.append(subString(str, i, dst)).append(replace);
					i = dst + target.length() - 1;
					count++;
				} else {
					sb.append(subString(str, i));
					break;
				}
			} else {
				sb.append(subString(str, i));
				break;
			}
		}
		
		return sb;
		
	}
	
	////////////////////////////////////////////////////////////
	
	vector<string>	stringSplitRegx(string const &str, 
									string const &regex)
	{
		vector<string> ret;
		
		NSString *csample	= [NSString stringWithUTF8String:str.c_str()];
		NSString *cregex	= [NSString stringWithUTF8String:regex.c_str()];
		NSArray *cresults	= [csample componentsSeparatedByRegex:cregex];
		//NSLog(@"results: %@", cresults);
		
		for (NSString* obj in cresults)
		{
			ret.push_back([obj UTF8String]);
		}
		
		return ret;
	}
	
	string stringReplaceRegx(string const &str,
							 string const &regex,
							 string const &replace)
	{
		NSString *cresult;
		NSString *csample	= [NSString stringWithUTF8String:str.c_str()];
		NSString *cregex	= [NSString stringWithUTF8String:regex.c_str()];
		NSString *creplace	= [NSString stringWithUTF8String:replace.c_str()];
		
		cresult = [csample stringByReplacingOccurrencesOfRegex:cregex withString:creplace];
		
		return [cresult UTF8String];
	}
	
	////////////////////////////////////////////////////////////
	
	string intToString(long value)
	{
		return _INT2STR(value);
	}
	
	string floatToString(double value)
	{
		return _FLOAT2STR(value);
	}
	
    bool stringToInt(string const &str, int &out_value)
	{
		long v = 0;
		if (stringToLong(str, v)) {
			out_value = v;
			return true;
		}
		return false;
	}
	
	bool stringToLong(string const &str, long &out_value)
	{
		long ret = 0;
		long b = 1;
		for (int i=str.length()-1; i>=0; --i) {
			char ch = str[i];
			if (ch >= '0' && ch <= '9') {
				ret += (ch - '0') * b;
				b *= 10;
			} else {
				return false;
			}
		}
		out_value = ret;
		return true;
	}
	
	bool stringToPoint(string const &str, double &out_value)
	{
		double ret = 0;
		double b = 0.1;
		for (int i=0; i<str.length(); ++i) {
			char ch = str[i];
			if (ch >= '0' && ch <= '9') {
				ret += (ch - '0') * b;
				b /= 10;
			} else {
				return false;
			}
		}
		out_value = ret;
		return true;
	}
	
	bool stringToFloat(string const &str, float &out_value)
	{
		double v = 0;
		if (stringToDouble(str, v)) {
			out_value = v;
			return true;
		}
		return false;
	}

	bool stringToDouble(string const &str, double &out_value)
	{	
		if (str.find(".") != string::npos) {
			vector<string> kv = stringSplit(str, ".");
			if (kv.size() > 1) {
//				printf("%s - %s \n", kv[0].c_str(), kv[1].c_str());
				long intvalue = 0;
				if (!stringToLong(kv[0], intvalue)) {
					return false;
				}
				double pointvalue = 0;
				if (!stringToPoint(kv[1], pointvalue)) {
					return false;
				}
				out_value = intvalue + pointvalue;
				return true;
			}
			return false;
		}
		else {
			long intvalue = 0;
			bool ret = stringToLong(str, intvalue);
			out_value = intvalue;
			return ret;
		}
	}
	
	bool stringToBool(string const &str, bool &out_value)
	{
		out_value = str[0] == 'T' || str[0] == 't';
		return true;
	}

	
	int		stringToInt(string const &str)
	{
		int ret = 0;
		stringToInt(str, ret);
		return ret;
	}
	
	long		stringToLong(string const &str)
	{
		long ret = 0;
		stringToLong(str, ret);
		return ret;
	}

	double		stringToPoint(string const &str)
	{
		double ret = 0;
		stringToPoint(str, ret);
		return ret;
	}

	float		stringToFloat(string const &str)
	{
		float ret = 0;
		stringToFloat(str, ret);
		return ret;
	}
	
	double		stringToDouble(string const &str)
	{
		double ret = 0;
		stringToDouble(str, ret);
		return ret;
	}
	
	bool		stringToBool(string const &str)
	{
		bool ret = 0;
		stringToBool(str, ret);
		return ret;
	}


	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	string stringTrim(string const &str)
	{
		NSString *nsstr = [NSString stringWithUTF8String:str.c_str()];
		
		nsstr = [nsstr stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
		
		const string &ret = [nsstr UTF8String];
        
		//[nsstr release];
        
		return ret;
	}
	
	
	int stringGetKeyValue(string const &s, string const &k, string const &separator, string &v)
	{
		int loc1 = s.find(k);
		if (loc1 != string::npos)
		{
			int loc2 = s.find(separator, loc1+k.length());
			if (loc2 != string::npos)
			{
				if (loc1 + k.length() == loc2)
				{
					int index = loc2+separator.length();
					v = stringTrim(s.substr(index));
					return index;
				}
			}
		}
		return string::npos;
	}
    
    bool stringStartWith(string const &str, string const &prefix)
    {
        return str.find(prefix) == 0;
    }
    
    bool stringEndWith(string const &str, string const &suffix)
    {
        return str.rfind(suffix) == str.length() - suffix.length();
    }
	
	
	string	subString(string const &str, int begin_index, int end_index)
	{
		return str.substr(begin_index, end_index - begin_index);
	}
	
	string subString(string const &str, int begin_index)
	{
		return subString(str, begin_index, str.length());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// random and Number
	///////////////////////////////////////////////////////////////////////////////////////
	
	int randomInt()
	{
		return (int)rand();
	}
	
	void randomSeed(int seed)
	{
		srand(seed);
	}
	
	
}; 


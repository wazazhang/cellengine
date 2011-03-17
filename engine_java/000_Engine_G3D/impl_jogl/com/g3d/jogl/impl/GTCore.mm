/*
 *  TGCore.c
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTCore.h"




namespace gt
{
	
	double getCurrentTime()
	{
		CFTimeInterval time = CFAbsoluteTimeGetCurrent();
		
		return (double)time;
	}

	bool writeApplicationData(NSData *data, NSString *fileName)
	{
		NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
		NSString *documentsDirectory = [paths objectAtIndex:0];
		if (!documentsDirectory) {
			NSLog(@"Documents directory not found!");
			return NO;
		}
		NSString *appFile = [documentsDirectory stringByAppendingPathComponent:fileName];
		return ([data writeToFile:appFile atomically:YES]);
	}
	
	NSData *applicationDataFromFile(NSString *fileName)
	{
		NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
		NSString *documentsDirectory = [paths objectAtIndex:0];
		NSString *appFile = [documentsDirectory stringByAppendingPathComponent:fileName];
		NSData *myData = [[[NSData alloc] initWithContentsOfFile:appFile] autorelease];
		return myData;
	}
	
	int saveRMSData(char const *filename, char const *ext, const void* bytes, u32 size)
	{
		NSData *data = [NSData dataWithBytes:bytes length:size];
	
		BOOL res = false;
		
		if(data != nil)
		{
			std::string path = filename;
			path+=".";
			path+=ext;
			
			res = writeApplicationData(data, [NSString stringWithUTF8String:path.c_str()]);
			
			
			/*
			NSBundle		*bundle		= [NSBundle mainBundle];
			NSString		*filePath	= [bundle pathForResource:[NSString stringWithUTF8String:filename] ofType:[NSString stringWithUTF8String:ext]];
			NSError			*error		= NULL;
			res = [data writeToFile:filePath options:NSAtomicWrite error:&error];
			if (error!=NULL)
			{
				NSString *errormsg = [error description];
				NSLog(errormsg);
			}		
			*/
		}
		
		return res == true ? 1 : -1;
	}

	int saveRMSAllText(char const *filename, char const *ext, std::string const &str)
	{
		NSString *nsstr = [NSString stringWithUTF8String:str.c_str()];
		
		NSUInteger size = [nsstr lengthOfBytesUsingEncoding: NSUTF8StringEncoding];
		
		void* bytes = malloc(size);
		
		BOOL res = [nsstr	getBytes:bytes 
							maxLength:size 
							usedLength:NULL 
							encoding: NSUTF8StringEncoding 
							options:NULL 
							range:NSMakeRange(0,size)
							remainingRange:NULL];
		if(res==YES)
		{
			res = saveRMSData(filename, ext, bytes, size);
		}					
		
		free(bytes);
		
		return res;
			
	}

	int saveRMSAllTextLine(char const *filename, char const *ext, std::vector<std::string> const &lines)
	{
		std::string all = "";
		
		for (std::vector<std::string>::const_iterator it=lines.begin(); it!=lines.end(); ++it)
		{
			all = all + (*it) + "\n";
		}
		
		return saveRMSAllText(filename, ext, all);
	}

	
	char* loadRMSData(char const *filename, char const *ext, u32 &size)
	{
		char* datas = NULL;
		size = 0;
		
		std::string path = filename;
		path+=".";
		path+=ext;
		
		NSData *data = applicationDataFromFile( [NSString stringWithUTF8String:path.c_str()]);
		
		if(data != nil)
		{
			size = [data length];
			datas = (char*)malloc(size);
			
			[data getBytes: datas];
		}
		
		return datas;
	}
	
	int loadRMSAllText(char const *filename, char const *ext, std::string &outString)
	{
		u32 size ;
		char* bytes = loadRMSData(filename, ext, size);
		
		if(bytes!=NULL)
		{
			outString.clear();
			outString += bytes;
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}
	
	int loadRMSAllTextLine(char const *filename, char const *ext, std::vector<std::string> &lines)
	{
		u32 size ;
		char* bytes = loadRMSData(filename, ext, size);
		
		if(bytes!=NULL)
		{
			std::string all = bytes;
			
			lines.clear();
			
			lines = stringSplit(all, "\n");
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	char* loadData(char const *filename, char const *ext, u32 &size)
	{
		NSBundle		*bundle		= [NSBundle mainBundle];
		NSFileManager	*manager	= [NSFileManager defaultManager];
		NSString		*filePath	= [bundle pathForResource:[NSString stringWithUTF8String:filename] ofType:[NSString stringWithUTF8String:ext]];
		NSData			*data		= [manager contentsAtPath:filePath];
		
		char* datas = NULL;
		size = 0;
		
		if(data != nil)
		{
			size = [data length];
			datas = (char*)malloc(size);
			
			[data getBytes: datas];
		}
		
		return datas;
		
	}

	
	int loadAllText(char const *filename, char const *ext, std::string &outString)
	{
		u32 size ;
		char* bytes = loadData(filename, ext, size);
	
		if(bytes!=NULL)
		{
			outString.clear();
			outString += bytes;
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}

	int loadAllTextLine(char const *filename, char const *ext, std::vector<std::string> &lines)
	{
		u32 size ;
		char* bytes = loadData(filename, ext, size);
	
		if(bytes!=NULL)
		{
			std::string all = bytes;

			lines.clear();
			
			lines = stringSplit(all, "\n");
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	std::vector<std::string> stringSplit(std::string const &str, std::string const &separator)
	{
		std::vector<std::string> lines;
							
			for(u32 index=0, i=0; i<str.length(); i++)
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
	
	
	u32 stringGetKeyValue(std::string const &s, std::string const &k, std::string const &separator, std::string &v)
	{
		u32 loc1 = s.find(k);
		if (loc1 != std::string::npos)
		{
			u32 loc2 = s.find(separator, loc1+k.length());
			if (loc2 != std::string::npos)
			{
				if (loc1 + k.length() == loc2)
				{
					u32 index = loc2+separator.length();
					v = stringTrim(s.substr(index));
					return index;
				}
			}
		}
		return std::string::npos;
	}
	
	
	u32 randomInt()
	{
		return (u32)rand();
	}
	
	void randomSeed(u32 seed)
	{
		srand(seed);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------//
	// thread
	
	
/*


	Thread::Thread(NSThread* thread)
	{
		m_thread = thread;
		m_runnable = NULL;
	}
	
	Thread::Thread()
	{
		m_thread =  [[NSThread alloc] initWithTarget:nil selector:@selector(run) object:nil];
		m_runnable = NULL;
	}
			
	Thread::Thread(IRunnable *runnable)
	{
		m_thread =  [[NSThread alloc] initWithTarget:nil selector:@selector(run) object:nil];
		m_runnable = runnable;
	}
	
			
	void Thread::run()
	{
	
	}
			
	void Thread::start()
	{
	
	}
			
			
			*/
	
}; // namespace gametiler


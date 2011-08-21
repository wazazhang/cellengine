//
//  CFile.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-15.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CFile.h"
#include "CUtil.h"
#include <Foundation/NSData.h>

namespace com_cell
{

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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	int saveRMSData(char const *filename, const void* bytes, int size)
	{
		NSData *data = [NSData dataWithBytes:bytes length:size];
        
		BOOL res = false;
		
		if(data != nil)
		{
			std::string path = filename;
			
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
    
	int saveRMSAllText(char const *filename, std::string const &str)
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
			res = saveRMSData(filename, bytes, size);
		}					
		
		free(bytes);
		
		return res;
        
	}
    
	int saveRMSAllTextLine(char const *filename, std::vector<std::string> const &lines)
	{
		std::string all = "";
		
		for (std::vector<std::string>::const_iterator it=lines.begin(); it!=lines.end(); ++it)
		{
			all = all + (*it) + "\n";
		}
		
		return saveRMSAllText(filename, all);
	}
    
	
	void* loadRMSData(char const *filename, int &size)
	{
		void* datas = NULL;
		size = 0;
		
		std::string path = filename;
		
		NSData *data = applicationDataFromFile( [NSString stringWithUTF8String:path.c_str()]);
		
		if(data != nil)
		{
			size = [data length];
			datas = (void*)malloc(size);
			
			[data getBytes: datas];
		}
		
		return datas;
	}
	
	int loadRMSAllText(char const *filename, std::string &outString)
	{
		int size ;
		void* bytes = loadRMSData(filename, size);
		
		if(bytes!=NULL)
		{
			outString.clear();
			outString += (char*)bytes;
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}
	
	int loadRMSAllTextLine(char const *filename, std::vector<std::string> &lines)
	{
		int size ;
		void* bytes = loadRMSData(filename, size);
		
		if(bytes!=NULL)
		{
			std::string all = (char*)bytes;
			
			lines.clear();
			
			lines = stringSplit(all, "\n");
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	void* loadData(char const *filename, int &out_size)
	{
		NSBundle		*bundle		= [NSBundle mainBundle];
		NSFileManager	*manager	= [NSFileManager defaultManager];
		NSString		*filePath	= [bundle pathForResource:[NSString stringWithUTF8String:filename] ofType:nil];
		NSData			*data		= [manager contentsAtPath:filePath];
		
		void* datas = NULL;
		out_size = 0;
		
		if(data != nil)
		{
			out_size = [data length];
			datas = (char*)malloc(out_size);
			
			[data getBytes: datas];
		}
		
		return datas;
		
	}
    
	
	int loadAllText(char const *filename, std::string &outString)
	{
		int size ;
		void* bytes = loadData(filename, size);
        
		if(bytes!=NULL)
		{
			outString.clear();
			outString += (char*)bytes;
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}
    
	int loadAllTextLine(char const *filename, std::vector<std::string> &lines)
	{
		int size ;
		void* bytes = loadData(filename, size);
        
		if(bytes!=NULL)
		{
			std::string all = (char*)bytes;
            
			lines.clear();
			
			lines = stringSplit(all, "\n");
			
			free(bytes);
			
			return 1;
		}
		
		return -1;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
}; 

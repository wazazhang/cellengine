/*
 *  TGCore.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#ifndef _GAMETILER_CORE
#define _GAMETILER_CORE

#include <Foundation/NSData.h>
#include <string>
#include <vector>
#include <fstream>

#include "GTType.h"

namespace gt
{

	extern double getCurrentTime();



	extern int saveRMSData(char const *filename, char const *ext, const void* bytes, u32 size);
	extern int saveRMSAllText(char const *filename, char const *ext, std::string const &str);
	extern int saveRMSAllTextLine(char const *filename, char const *ext, std::vector<std::string> const &lines);

	extern char* loadRMSData(char const *filename, char const *ext, u32 &size);
	extern int loadRMSAllText(char const *filename, char const *ext, std::string &outString);
	extern int loadRMSAllTextLine(char const *filename, char const *ext, std::vector<std::string> &lines);
	
	extern char* loadData(char const *filename, char const *ext, u32 &size);
	extern int loadAllText(char const *filename, char const *ext, std::string &outString);
	extern int loadAllTextLine(char const *filename, char const *ext, std::vector<std::string> &lines);

	extern std::vector<std::string> stringSplit(std::string const &str, std::string const &separator);
	
	extern std::string intToString(long value);
	extern std::string floatToString(double value);
	
	extern int stringToInt(std::string const &str, long &value);
	extern int stringToFloat(std::string const &str, double &value);
	
	extern std::string stringTrim(std::string const &str);
	
	extern u32 stringGetKeyValue(std::string const &s, std::string const &k, std::string const &separator, std::string &v);
	
	extern u32 randomInt();
				
	extern void randomSeed(u32 seed);			
				
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------//
	
	/*
	
	class IRunnable
	{
	public:
		virtual void run() = 0;
			
	}; 
	
	
	class Thread
	{
	public:
		static Thread* currentThread();
		
	private:
		NSThread* m_thread;
		IRunnable* m_runnable;
		
	private:
		Thread(NSThread* thread);
		
	public:
		Thread();
		Thread(IRunnable *runnable);
		~Thread();
		void run();
		void start();
	};
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
}; // namespace gametiler




#endif // #define _GAMETILER_CORE
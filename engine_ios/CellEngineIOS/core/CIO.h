//
//  CIO.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-22.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//


#ifndef _COM_CELL_IO
#define _COM_CELL_IO

#include <string>
#include <vector>
#include <fstream>

#include "CType.h"
#include "CUtil.h"


namespace com_cell
{
	using namespace std;
	
	///////////////////////////////////////////////////////////////////////////////////////
	// TextReader
	///////////////////////////////////////////////////////////////////////////////////////
	class TextReader
	{
	private:
		string	buffer;
		int		pos;
		
	public:
		
		TextReader(string const &src) ;		
		
		bool	read(string &ret);		
		
		bool	readLen(int len, string &ret);		
		
		void	skip(int n);		
		
		int		position();		
		
		void	setPosition(int pos);		
		
		int		remain();
		
	}; // class TextReader
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	// TextDeserialize
	///////////////////////////////////////////////////////////////////////////////////////

	class TextDeserialize
	{
	private:
		static string getNext(TextReader &input) ;		
	public:
		
		static string	getString(TextReader &input);
		
		static bool		getBoolean(TextReader &input);
		
		static char		getByte(TextReader &input);
		
		static short	getShort(TextReader &input);
		
		static int		getInt(TextReader &input);
		
		static long		getLong(TextReader &input);
		
		static float	getFloat(TextReader &input) ;
		
		static double	getDouble(TextReader &inpur) ;
		
		
		//////////////////////////////////////////////////////////////////////////////////////
		// get array
		
		static vector<string>	getStrings(TextReader &input);

		static vector<bool>		getBooleans(TextReader &input);
		
		static vector<char>		getBytes(TextReader &input);
		
		static vector<short>	getShorts(TextReader &input);
		
		static vector<int>		getInts(TextReader &input);
		
		static vector<long>		getLongs(TextReader &input);
		
		static vector<float>	getFloats(TextReader &input);
		
		static vector<double>	getDoubles(TextReader &input);
		
		
	}; // class TextDeserialize
	
}; // namespace com.cell



#endif // #define _COM_CELL_IO



//
//  CIO.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-22.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CIO.h"

namespace com_cell
{
	using namespace std;
	
	///////////////////////////////////////////////////////////////////////////////////////
	// TextReader
	///////////////////////////////////////////////////////////////////////////////////////
	
	TextReader::TextReader(string const &src) 
	{
		buffer = src;
		pos = 0;
	}
	
	bool TextReader::read(string &ret)
	{
		if (pos < buffer.size()) {
			ret = buffer.substr(pos, 1);
			pos ++;
			return true;
		}
		return false;
	}
	
	bool TextReader::readLen(int len, string &ret)	
	{
		if (pos < buffer.size() - len) {
			ret = buffer.substr(pos, len);
			pos += len;
			return true;
		} else {
			len = buffer.size() - pos;
			if (len > 0) {
				ret = buffer.substr(pos, len);
				pos = buffer.size();
				return true;
			}
			return false;
		}
	}
	
	void TextReader::skip(int n) {
		pos += n;
	}
	
	int TextReader::position() {
		return pos;
	}
	
	void TextReader::setPosition(int p) {
		pos = p;
	}
	
	int TextReader::remain() {
		return buffer.size() - pos;
	}

	
	///////////////////////////////////////////////////////////////////////////////////////
	// TextDeserialize
	///////////////////////////////////////////////////////////////////////////////////////

	string TextDeserialize::getNext(TextReader &input) 
	{
		string sb = "";
		while (true) {
			string r;
			if (!input.read(r)) {
				break;
			}
			if (stringEquals(r, ",")) {
				break;
			}
			sb += r;
		}
		return sb;
	}
	
	string TextDeserialize::getString(TextReader &input)
	{
		int len = getInt(input);
		string ret;
		if (input.readLen(len, ret)) {
			string ch;
			if (input.read(ch)) {
				
			}
		}
		return ret;
	}
	
	bool	TextDeserialize::getBoolean(TextReader &input)
	{
		return stringToBool(getNext(input));
	}
	
	char	TextDeserialize::getByte(TextReader &input)
	{
		return stringToInt(getNext(input));
	}
	
	short	TextDeserialize::getShort(TextReader &input)
	{
		return stringToInt(getNext(input));
	}
	
	int		TextDeserialize::getInt(TextReader &input)
	{
		return stringToInt(getNext(input));
	}
	
	long	TextDeserialize::getLong(TextReader &input)
	{
		return stringToInt(getNext(input));
	}
	
	float	TextDeserialize::getFloat(TextReader &input) 
	{
		return stringToFloat(getNext(input));
	}
	
	double	TextDeserialize::getDouble(TextReader &input) 
	{
		return stringToDouble(getNext(input));
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// get array
		
	vector<string> TextDeserialize::getStrings(TextReader &input)
	{
		int count = getInt(input);
		if (count > 0) {
			vector<string> values(count);
			for (int i=0; i<count; ++i ) {
				values[i] = getString(input);
			}
			return values;
		}
		return vector<string>(0);
	}
	
	vector<bool> TextDeserialize::getBooleans(TextReader &input)
	{
		int count = getInt(input);
		if (count > 0) {
			vector<bool> values(count);
			for (int i=0; i<count; ++i ) {
				values[i] = getBoolean(input);
			}
			return values;
		}
		return vector<bool>(0);
	}	
		

	
}; // namespace com.cell




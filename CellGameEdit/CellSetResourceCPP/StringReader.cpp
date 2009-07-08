
#include "StringReader.h"


#include <iostream>

namespace cellsetresource
{



	/// extern
	vector<string> splitString(string const &text, string const &split)
	{
		return splitString(text, split, 0);
	}


	/// extern
	vector<string> splitString(string const &text, string const &split, string::size_type limit)
	{
		vector<string> lines;

		for (string::size_type i = 0; i != string::npos ; i++)
		{
			string::size_type dst = text.find(split, i);
				
			if( dst != string::npos && ((limit<=0) || (limit>0 && lines.size()<(limit-1))))
			{
				lines.push_back(text.substr(i, dst-i));
				i = dst + split.length() - 1;
			}
			else
			{
				lines.push_back(text.substr(i,text.length()-i));
				break;
			}
		}	

		return lines;
	}



	/**
	 * @param text
	 * @param s
	 * @param d
	 * @return
	 */
	string replaceString(string const &text, string const &src_text, string const &dst_text)
	{
		string sb = "";
		
		for (string::size_type i = 0; i != string::npos ; i++)
		{
			string::size_type dst = text.find(src_text, i);
			
			if( dst != string::npos)
			{
				sb.append(text.substr(i,dst-i));
				sb.append(dst_text);

				i = dst + src_text.length() - 1;
			}
			else
			{
				sb.append(text.substr(i));
				break;
			}
		}
		
		return sb;
	}
	
	string trimString(string const &text)
	{
		string ret = replaceString(text, " ", "");
		ret = replaceString(ret, "\t", "");
		ret = replaceString(ret, "\r", "");
		ret = replaceString(ret, "\n", "");
		return ret;
	}

	string subString(string const &text, string::size_type begin, string::size_type end)
	{
		return text.substr(begin, end-begin);
	}

	string subString(string const &text, string::size_type begin)
	{
		return text.substr(begin);
	}





	int parseInt(string const &text)
	{
		string::size_type	length	= text.length();

		if (length<=0)
		{
			throw StringExcption("parseInt(" + text + ") : format error !");
		}

		bool				nagtive	= false;
		string::size_type	start	= 0;
		if (text[0] == '-')
		{
			if (length==1){
				throw StringExcption("parseInt(" + text + ") : format error !");
			}
			nagtive	= true;
			start	= 1;
		}

		int					value	= 0;

		for (string::size_type i=start; i<length; i++)
		{
			char ch = text[i];
			
			if (ch<='9' && ch>='0')
			{
				value *= 10;
				value += ch - '0';
			}
			else
			{
				throw StringExcption("parseInt(" + text + ") : format error !");
			}
		}

		if (nagtive)
		{
			value *= -1;
		}

		return value;

	}
    

	string			toString(int value)
	{
		return "";
	}

	string			formatString(string const &text, ...)
	{

		return "";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	StringExcption::StringExcption(std::string const &msg)
	{
		message = msg;
	}
		
	string StringExcption::getMessage()
	{
		return message;
	}
	

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	StringReader::StringReader(std::string const &src)
	{
		text	= src;
		pos		= 0;
		length	= text.length();
	}

	StringReader::StringReader(char const *src)
	{
		text	= string(src);
		pos		= 0;
		length	= text.length();
	}

	int StringReader::read()
	{
		int ret = text[pos];
		pos += 1;
		return ret;
	}

	string StringReader::read(string::size_type count)
	{
		string &ret = text.substr(pos, count);
		pos += count;
		return ret;
	}

	string StringReader::getNext()
	{
		string sb("");

		while(pos<length)
		{
			char r = (char)read();
			if (r == ',') break;
			sb.append(1, r);
		}
		
		return sb;
	}

	
	string StringReader::getByteString()
	{
		string::size_type stringLen = getInt();
		string ret;
		if (stringLen>0){
			ret = read(stringLen);
		}
		if (read() != ',') {
		}
		return ret;
	}


	vector<string> StringReader::getByteStrings()
	{
		vector<string> lines;

		string::size_type size = getInt();

		for (string::size_type i=0; i<size; i++)
		{
			lines.push_back(getByteString());
		}

		return lines;
	}

	int StringReader::getInt()
	{
		return parseInt(getNext());
	}




}; // namespace cellsetresource



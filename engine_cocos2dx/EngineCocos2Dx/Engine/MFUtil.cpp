//
//  CUtil.cpp
//  Created by wazazhang on 12-4-17.
//

#include "MFUtil.h"
#include "CCFileUtils.h"
namespace mf
{
	using namespace std;

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
		return stringSplit(str, separator, 65535);
	}

	vector<string> stringSplit(string const &str,
							   string const &separator,
							   int limit)
	{
		int count = 0;
		vector<string> lines;

        for (int i=0; i<str.length(); i++)
        {
			if (count < limit)
			{
				int dst = str.find(separator, i);

				if (dst != string::npos)
				{
					lines.push_back(subString(str, i, dst));
					i = dst + separator.length() - 1;
					count ++;
				}
				else
				{
					lines.push_back(subString(str, i, str.length()));
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

	vector<string>	stringSplitBlank(string const &str, int limit)
	{
		int count = 1;
		vector<string> lines;

        for (int i=0; i<str.length(); i++)
        {
			if (count < limit)
			{
				int dst = str.find(' ', i);
				if (dst == string::npos) {
					dst = str.find('\t', i);
				}
				if (dst == string::npos) {
					dst = str.find('\n', i);
				}
				if (dst == string::npos) {
					dst = str.find('\r', i);
				}
				if (dst != string::npos)
				{
					lines.push_back(subString(str, i, dst));
					i = dst;
					count ++;
				}
				else
				{
					lines.push_back(subString(str, i, str.length()));
					break;
				}
			}
			else
			{
				string last = subString(str, i, str.length());
				lines.push_back(stringTrim(last));
				break;
			}
        }

		return lines;

	}


//	if (count < limit) {
//		int dst = text.indexOf(separator, i);
//		if (dst >= 0) {
//			lines.addElement(text.substring(i, dst));
//			i = dst + separator.length() - 1;
//			count ++;
//		} else {
//			lines.addElement(text.substring(i, text.length()));
//			break;
//		}
//	} else {
//		lines.addElement(text.substring(i, text.length()));
//		break;
//	}


	string stringReplace(string const &str,
						 string const &target,
						 string const &replace)
	{
		return stringReplace(str, target, replace, 65535);
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
			}
			else if (ch == '-') {
				ret *= -1;
			}
			else {
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
			}
			else if (ch == '-') {
				ret *= -1;
			}
			else {
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


	int stringToInt(string const &str)
	{
		int ret = 0;
		stringToInt(str, ret);
		return ret;
	}

	long stringToLong(string const &str)
	{
		long ret = 0;
		stringToLong(str, ret);
		return ret;
	}

	double stringToPoint(string const &str)
	{
		double ret = 0;
		stringToPoint(str, ret);
		return ret;
	}

	float stringToFloat(string const &str)
	{
		float ret = 0;
		stringToFloat(str, ret);
		return ret;
	}

	double stringToDouble(string const &str)
	{
		double ret = 0;
		stringToDouble(str, ret);
		return ret;
	}

	bool stringToBool(string const &str)
	{
		bool ret = 0;
		stringToBool(str, ret);
		return ret;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////

	bool stringSplitRegx(string const &str,
									string const &regex,
									vector<string> &out)
	{
		return false;
	}

	bool stringReplaceRegx(string const &str,
							 string const &regex,
							 string const &replace,
							 string &out)
	{
		return false;
	}

	string stringTrim(string const &str)
	{
		return str;
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


	void* loadData(char const *filename, int &out_size)
	{
		unsigned long psize = 0;

		out_size = 0;

		void* datas = cocos2d::CCFileUtils::getFileData(filename, "r", &psize);

		out_size = psize;

		return datas;
	}


	int loadAllText(char const *filename, std::string &outString)
	{
		int size ;
		void* bytes = loadData(filename, size);
		if (bytes!=NULL)
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
		if (bytes!=NULL)
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


};


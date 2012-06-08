//
//  CUtil.cpp
//  Created by wazazhang on 12-4-17.
//

#include "MFUtil.h"
#include "CCFileUtils.h"
namespace mf
{
	using namespace std;

	bool stringGetKeyValue(
		string const &src, 
		string const &key, 
		string &value)
	{
		int index = src.find(key);
		if(index >= 0)
		{
			 value = src.substr(index + key.length());
			 return true;
		}
		else
		{
			return false;
		}
	}

	// {}{}{}
	bool stringFindRegionFIFO(
		string const &src, 
		string const &begin, 
		string const &end, 
		int &beginIndex, 
		int &endIndex)
	{
		int bi = src.find(begin);
		int ei = src.find(end);
		if (bi >= 0 && ei > 0) {
			beginIndex = bi;
			endIndex = ei + end.length();
			return true;
		}
		return false;
	}

	// {{{}}}
	bool stringFindRegionFILO(
		string const &src, 
		string const &begin, 
		string const &end, 
		int &beginIndex, 
		int &endIndex)
	{
		int bi = src.find(begin);
		int ei = src.rfind(end);
		if (bi >= 0 && ei > 0) {
			beginIndex = bi;
			endIndex = ei + end.length();
			return true;
		}
		return false;
	}


	string stringFormat(string const src, ... )
	{
		int argNum = 0;
		vector<int> argsIndex;
		vector<int> argsBI;
		vector<int> argsEI;
		vector<string> argsFmt;
		int len = src.length();
		for (int i = 0; i < len; i++)
		{
			int bi = src.find("{", i);
			int ei = src.find("}", i+1);
			if (bi >= 0 && ei > 1) {
				int argI = -1;
				vector<string> const &split = stringSplit(src.substr(bi + 1, ei - bi - 1), "%", 2);
				if (split.size() == 2) {
					if (stringToInt(split[0], argI)) {
						argsBI.push_back(bi);
						argsEI.push_back(ei + 1);
						argsIndex.push_back(argI);
						argsFmt.push_back("%" + split[1]);
						argNum++;
					}
				}
				i = ei;
			} else {
				break;
			}
		}

		if (argNum > 0) 
		{
			string arg_fstring;
			for(int i=0;i<argNum;i++) {
				int argI = argsIndex[i];
				arg_fstring.append(argsFmt[argI]);
				if (i < argNum-1) {
					arg_fstring.append("|");
				}
			}
			//const string * vap = &src;

			char buffer[1024];
			va_list ap;
			va_start(ap, src);  
			vsprintf(buffer, arg_fstring.c_str(), ap);
			va_end(ap);

			vector<string> args = stringSplit(buffer, "|");
			string ret;
			int lastI = 0;
			for(int i=0;i<argNum;i++) {  
				int argI = argsIndex[i];
				int bi = argsBI[i];
				int ei = argsEI[i];
				string arg = args[argI];
				ret += src.substr(lastI, bi - lastI) + arg;
				lastI = ei;
			}  
			ret += src.substr(lastI);
			return ret;
		}
		return src;
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
		return MF_INT2STR(value);
	}

	string floatToString(double value)
	{
		return MF_FLOAT2STR(value);
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

	bool stringHexToU64(string const &str, u64 &out_value)
	{
		u64 ret = 0;
		u64 b = 1;
		for (int i=str.length()-1; i>=0; --i) {
			char ch = str[i];
			if (ch >= '0' && ch <= '9') {
				ret += (ch - '0') * b;
				b *= 16;
			}
			else if (ch >= 'a' && ch <= 'f') {
				ret += (ch - 'a' + 10) * b;
				b *= 16;
			}
			else if (ch >= 'A' && ch <= 'F') {
				ret += (ch - 'A' + 10) * b;
				b *= 16;
			}
			else {
				return false;
			}
		}
		out_value = ret;
		return true;
	}

	bool stringHexToU32(string const &str, u32 &out_value)
	{
		u64 v = 0;
		if (stringHexToU64(str, v)) {
			out_value = (v & 0xffffffff);
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

	u32 stringHexToU32(string const &str)
	{
		u32 ret = 0;
		stringHexToU32(str, ret);
		return ret;
	}

	u64 stringHexToU64(string const &str)
	{
		u64 ret = 0;
		stringHexToU64(str, ret);
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
/*
	string stringFromUnicode(const wstring& str)  
	{  

		string desc;  

		setlocale(LC_ALL,"");     //设置本地默认  

		wcstombs(&desc[0],str.c_str(),sizeof(str) * 2);  

		setlocale(LC_ALL,"C");     //用完恢复  

		return desc.c_str();  

	};  

	wstring stringToUnicode(const string& str)  

	{  

		wchar_t* data =new wchar_t[sizeof(str) * 2];  

		setlocale(LC_ALL,"");     //设置本地默认  

		mbstowcs(data,str.c_str(),sizeof(str) * 2);  

		setlocale(LC_ALL,"C");     //用完恢复  



		wstringtmp(data);  

		delete data;  

		return mp.c_str();  

	};  
*/
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


/*
	std::string StringConvert::ws2s(const std::wstring& ws, const char* local)
	{
		std::string curLocale = setlocale(LC_ALL, NULL);        // curLocale = "C";
		setlocale(LC_ALL, local);
		const wchar_t* _Source = ws.c_str();
		size_t _Dsize = 2 * ws.size() + 1;
		char *_Dest = new char[_Dsize];
		memset(_Dest,0,_Dsize);
		wcstombs(_Dest,_Source,_Dsize);
		std::string result = _Dest;
		delete []_Dest;
		setlocale(LC_ALL, curLocale.c_str());
		return result;
	}

	std::wstring StringConvert::s2ws(const std::string& s, const char* local)
	{
		std::string curLocale = setlocale(LC_ALL, NULL);        // curLocale = "C";
		setlocale(LC_ALL, local); 
		const char* _Source = s.c_str();
		size_t _Dsize = s.size() + 1;
		wchar_t *_Dest = new wchar_t[_Dsize];
		wmemset(_Dest, 0, _Dsize);
		mbstowcs(_Dest,_Source,_Dsize);
		std::wstring result = _Dest;
		delete []_Dest;
		setlocale(LC_ALL, curLocale.c_str());
		return result;
	}
*/

	// 	UNICODE，根据前几个字节可以判断UNICODE字符集的各种编码，叫做Byte Order Mask方法BOM：
	// 		UTF-8: EFBBBF (符合UTF-8格式，请看上面。但没有含义在UCS即UNICODE中)
	// 		UTF-16 Big Endian：FEFF (没有含义在UCS-2中)
	// 		UTF-16 Little Endian：FFFE (没有含义在UCS-2中)
	// 		UTF-32 Big Endian：0000FEFF (没有含义在UCS-4中)
	// 		UTF-32 Little Endian：FFFE0000 (没有含义在UCS-4中)
	// 		GB2312：高字节和低字节的第1位都是1。
	// 		BIG5，GBK&GB18030：高字节的第1位为1。操作系统有默认的编码，常为GBK，
	//		可以下载别的并升级。通过判断高字节的第1位从而知道是ASCII或者汉字编码。

	//! convert from wstring to UTF8 using self-coding-converting
	void StringConvert::wsToUTF8Stream(const mfstring& src, string& dest)
	{
		dest.clear();

		// EF BB BF
		dest.push_back(0xEF);
		dest.push_back(0xBB);
		dest.push_back(0xBF);

		wsToUTF8(src, 0, src.length(), dest);
	}

	void StringConvert::utf8StreamToWS(const string& src, mfstring& dest)
	{
		dest.clear();
		size_t src_size = src.size();
		// EF BB BF
		if (src_size > 3)
		{
			utf8ToWS(src, 3, src.length()-3, dest);
		}
	}

	string	StringConvert::wsToUTF8(const mfstring& src)
	{
		string dest;
		wsToUTF8(src, 0, src.length(), dest);
		return dest;
	}

	mfstring StringConvert::utf8ToWS(const string& src)
	{
		mfstring dest;
		utf8ToWS(src, 0, src.length(), dest);
		return dest;
	}

	//! convert from wstring to UTF8 using self-coding-converting
	void StringConvert::wsToUTF8(const mfstring& src, size_t index, size_t length, 
		std::string& dest)
	{
		size_t endIndex = index + length;
		for (size_t i = index; i < endIndex; i++)
		{
			mfchar_t w = src[i];
			if (w <= 0x7f){
				dest.push_back((char)w);
			} 
			else if (w <= 0x7ff){
				dest.push_back(0xc0 | ((w >> 6)& 0x1f));
				dest.push_back(0x80| (w & 0x3f));
			}
			else if (w <= 0xffff){
				dest.push_back(0xe0 | ((w >> 12)& 0x0f));
				dest.push_back(0x80| ((w >> 6) & 0x3f));
				dest.push_back(0x80| (w & 0x3f));
			}
			else if (sizeof(mfchar_t) > 2 && w <= 0x10ffff){
				dest.push_back(0xf0 | ((w >> 18)& 0x07)); // wchar_t 4-bytes situation
				dest.push_back(0x80| ((w >> 12) & 0x3f));
				dest.push_back(0x80| ((w >> 6) & 0x3f));
				dest.push_back(0x80| (w & 0x3f));
			}
			else {
				dest.push_back('?');
			}
		}
	}

	void StringConvert::utf8ToWS(const string& src, size_t index, size_t length,
		mfstring& dest)
	{
		mfchar_t w = 0;
		mfchar_t err = L'?';
		int bytes = 0;
		size_t endIndex = index + length;
		for (size_t i = index; i < endIndex; i++)
		{
			unsigned char c = (unsigned char)src[i];
			if (c <= 0x7f){//first byte
				if (bytes){
					dest.push_back(err);
					bytes = 0;
				}
				dest.push_back((mfchar_t)c);
			}
			else if (c <= 0xbf){//second/third/etc byte
				if (bytes){
					w = ((w << 6)|(c & 0x3f));
					bytes--;
					if (bytes == 0)
						dest.push_back(w);
				}
				else
					dest.push_back(err);
			}
			else if (c <= 0xdf){//2byte sequence start
				bytes = 1;
				w = c & 0x1f;
			}
			else if (c <= 0xef){//3byte sequence start
				bytes = 2;
				w = c & 0x0f;
			}
			else if (c <= 0xf7){//3byte sequence start
				bytes = 3;
				w = c & 0x07;
			}
			else{
				dest.push_back(err);
				bytes = 0;
			}
		}
		if (bytes) {
			dest.push_back(err);
		}
	}




};




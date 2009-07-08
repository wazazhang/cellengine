#ifndef _STRING_READER_H
#define _STRING_READER_H

#include <string>
#include <vector>

using namespace std;

namespace cellsetresource
{	

//---------------------------------------------------------------------------------------------------------------------------------
// string util
//---------------------------------------------------------------------------------------------------------------------------------

	extern vector<string>	splitString(string const &text, string const &split);

	extern vector<string>	splitString(string const &text, string const &split, string::size_type limit);

	extern string			replaceString(string const &text, string const &src_text, string const &dst_text);

	extern string			trimString(string const &text);

	extern string			subString(string const &text, string::size_type begin, string::size_type end); // end is out

	extern string			subString(string const &text, string::size_type begin);

	extern int				parseInt(string const &text);

	extern string			toString(int value);

	extern string			formatString(string const &text, ...);


//---------------------------------------------------------------------------------------------------------------------------------

	class StringExcption
	{
	private:
		string					message;
	
	public:
		StringExcption(std::string const &msg);
		
		string			getMessage();

	};



//---------------------------------------------------------------------------------------------------------------------------------

	class StringReader
	{
	private:
		string					text;
		string::size_type		pos;
		string::size_type		length;

	public:

		StringReader(std::string const &src);

		StringReader(char const *src);

		int				read();

		string			read(string::size_type count);

		string			getNext();

		string			getByteString();

		vector<string>	getByteStrings();

		int				getInt();

	};

}; // namespace cellsetresource

#endif // #ifndef _STRING_READER_H
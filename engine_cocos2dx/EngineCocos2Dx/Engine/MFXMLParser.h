//
//  CXml.h
//  100_MusicGame
//  Created by wazazhang on 12-4-17.
//
#ifndef _MF_XMLPARSER_H
#define _MF_XMLPARSER_H


#include <string>
#include <vector>
#include <map>
#include <fstream>
#include <iostream>

namespace mf
{
	using namespace std;

	//////////////////////////////////////////////////////////////////////////////////
	// XMLNode
	//////////////////////////////////////////////////////////////////////////////////

	class XMLNode
	{
	public:
		string							value;
		
		map<string, string>	attributes;
		
	private:
		
		string							name;
		
		XMLNode								*parent;
		
		vector<XMLNode*>				childs;
		
	public:
		
		XMLNode(string const &name);
		
		~XMLNode();
		
		const string& getName();
		
		XMLNode* getParent();
		
		vector<XMLNode*>::const_iterator childBegin();
		
		vector<XMLNode*>::const_iterator childEnd();
		
		void addChild(XMLNode* child);
		
		const string&	getAttribute(string const &name);
		
		int getAttributeAsInt(string const &name);
		
		string toString();
		
	private:
		
		void getString(string &str, int deep);

	public:

		static XMLNode* parseFromFile(string const &filename);

		static XMLNode* parseFromText(string const &text);
	};
		
	

}; // namespace mf


#endif // #define _MF_XMLPARSER_H

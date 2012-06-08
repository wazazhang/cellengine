//
//  CXml.h
//  100_MusicGame
//  Created by wazazhang on 12-4-17.
//
#ifndef _MF_XMLPARSER_H
#define _MF_XMLPARSER_H

#include "MFType.h"

#include <string>
#include <vector>
#include <map>
#include <fstream>
#include <iostream>

#include "cocos2d.h"
namespace mf
{
	using namespace std;

	//////////////////////////////////////////////////////////////////////////////////
	// XMLNode
	//////////////////////////////////////////////////////////////////////////////////
	class CC_DLL XMLNode;

	typedef CC_DLL vector<XMLNode*>::const_iterator XMLIterator;

	
	class CC_DLL XMLNode
	{
	private:

		map<string, string>	attributes;

		string value;

		string name;
		
		XMLNode *parent;
		
		vector<XMLNode*> childs;
		
	public:
		
		XMLNode(string const &name);
		
		~XMLNode();
		
		

		XMLNode* getParent();
		
		XMLIterator childBegin();
		XMLIterator childEnd();
		XMLNode* findChild(string const &name);
		int childCount();

		string getName();
		string getValue();

		bool	isAttribute(string const &name);
		int		getAttributeAsInt(string const &name);
		float	getAttributeAsFloat(std::string const &name);
		u32		getAttributeAsHexU32(string const &name);
		bool	getAttributeAsBool(std::string const &name);
		string	getAttribute(string const &name);

		string toString();

	public: // write methods

		void addChild(XMLNode* child);

		void setValue(string const& value);

		void setAttribute(string const &key, string const& value);
		
	private:
		
		void getString(string &str, int deep);

	public:

		static XMLNode* parseFromFile(string const &filename);
		static XMLNode* parseFromText(string const &text);

	};
		

}; // namespace mf


#endif // #define _MF_XMLPARSER_H

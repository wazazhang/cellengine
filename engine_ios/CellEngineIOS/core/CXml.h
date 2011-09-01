//
//  CXml.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-21.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_XML
#define _COM_CELL_XML

#include <string>
#include <vector>
#include <map>
#include <fstream>

#include <iostream.h>

#include "CType.h"


namespace com_cell
{
	
	//////////////////////////////////////////////////////////////////////////////////
	// XMLNode
	//////////////////////////////////////////////////////////////////////////////////

	class XMLNode
	{
	public:
		std::string							value;
		
		std::map<std::string, std::string>	attributes;
		
	private:
		
		std::string							name;
		
		XMLNode								*parent;
		
		std::vector<XMLNode*>				childs;
		
	public:
		
		XMLNode(std::string const &name);
		
		~XMLNode();
		
		const std::string& getName();
		
		XMLNode* getParent();
		
		std::vector<XMLNode*>::const_iterator childBegin();
		
		std::vector<XMLNode*>::const_iterator childEnd();
		
		void addChild(XMLNode* child);
		
		const std::string&	getAttribute(std::string const &name);
		
		int getAttributeAsInt(std::string const &name);
		
		std::string toString();
		
	private:
		
		void getString(std::string &str, int deep);
		
	};
		
	XMLNode* parseXML(char const* filename);
	
}; // namespace com.cell


#endif // #define _COM_CELL_XML
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

#include <libxml/tree.h>
#include <libxml/parser.h>
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
		
		std::map<std::string, XMLNode*>		childs;
		
	public:
		
		XMLNode(std::string const &name);
		
		~XMLNode();
		
		const std::string& getName();
		
		XMLNode* getParent();
		
		std::map<std::string, XMLNode*>::iterator childBegin();
		
		std::map<std::string, XMLNode*>::iterator childEnd();

		XMLNode* getChild(std::string const &name);
		
		XMLNode* addChild(XMLNode* child);
		
		const std::string&	getAttribute(std::string const &name);
		
		int getAttributeAsInt(std::string const &name);
		
	};
		
	XMLNode* parseXML(char const* filename);
	
}; // namespace com.cell

//////////////////////////////////////////////////////////////////////////////////
// XMLHelper
//////////////////////////////////////////////////////////////////////////////////
@interface XMLHelper : NSObject <NSXMLParserDelegate> {
@private
	com_cell::XMLNode *current;
}
- (void)parse:(NSData *)data ;
- (com_cell::XMLNode *)getRoot;
@end
///////////////////////////////////////////////////////////////////

#endif // #define _COM_CELL_XML
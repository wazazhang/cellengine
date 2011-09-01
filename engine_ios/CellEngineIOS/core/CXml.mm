//
//  CXml.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-21.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CXml.h"

#include "CFile.h"
#include "CUtil.h"
#include <Foundation/NSData.h>

//////////////////////////////////////////////////////////////////////////////////
// XMLHelper
//////////////////////////////////////////////////////////////////////////////////
@interface XMLHelper : NSObject <NSXMLParserDelegate> {
	com_cell::XMLNode *current;
}
- (id) init ;
- (void)parse:(NSData *)data ;
- (com_cell::XMLNode *)getRoot;
@end
///////////////////////////////////////////////////////////////////

@implementation XMLHelper

using namespace com_cell;

- (id) init {
    [super init];   // 必須呼叫父類的init
	// do something here ...
	current = NULL;
    return self;
}

// 首先设置XML数据，并初始化NSXMLParser
- (void)parse:(NSData *)data 
{
	current = NULL;
	
	NSXMLParser *parser = [[NSXMLParser alloc] initWithData:data]; //设置XML数据
	[parser setShouldProcessNamespaces:NO];
	[parser setShouldReportNamespacePrefixes:NO];
	[parser setShouldResolveExternalEntities:NO];
	
	[parser setDelegate:self];
	[parser parse];
}

//遍例xml的节点
- (void)parser:(NSXMLParser *)parser
didStartElement:(NSString *)elementName 
  namespaceURI:(NSString *)namespaceURI 
 qualifiedName:(NSString *)qName 
	attributes:(NSDictionary *)attributeDict
{
	elementName = [elementName stringByTrimmingCharactersInSet:
				   [NSCharacterSet whitespaceAndNewlineCharacterSet]];
	if ([elementName length] > 0) {
		//NSLog(@"begin: <%@>",elementName);
		std::string name = string([elementName UTF8String]);
		if (current == NULL) {
			current = new XMLNode(name);
		} else {
			XMLNode* next = new XMLNode(name);
			current->addChild(next);
			current = next;
		}
		for (id key in attributeDict) {
			NSString *k = key;
			NSString *v = [attributeDict objectForKey:key];
			//			string k = [((NSString)key) UTF8String];
			//			string v = [((NSString)[attributeDict objectForKey:key]) UTF8String];
			//NSLog(@" attr: %@=%@",key,[attributeDict objectForKey:key]);
			current->attributes[string([k UTF8String])] = string([v UTF8String]);
		}
	}
	
}

//当xml节点有值时，则进入此句 
- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)vstr
{
	vstr = [vstr stringByTrimmingCharactersInSet:
			[NSCharacterSet whitespaceAndNewlineCharacterSet]];
	if ([vstr length] > 0) {
		if (current != NULL) {
			current->value = string([vstr UTF8String]);
			//NSLog(@"value: %@", vstr);
		}
	}
}


//当遇到结束标记时，进入此句
- (void)parser:(NSXMLParser *)parser 
 didEndElement:(NSString *)elementName 
  namespaceURI:(NSString *)namespaceURI 
 qualifiedName:(NSString *)qName
{
	elementName = [elementName stringByTrimmingCharactersInSet:
				   [NSCharacterSet whitespaceAndNewlineCharacterSet]];
	if ([elementName length] > 0) {
		//std::string name = [elementName UTF8String];
		if (current != NULL && current->getParent()!=NULL) {
			current = current->getParent();
			//NSLog(@"  end: </%@>",elementName);	
		}
	}
}	

- (XMLNode *)getRoot 
{
	return current;
}

@end
////////////////////////////////////////////////////////////////////////////////////

namespace com_cell
{
	//////////////////////////////////////////////////////////////////////////////////
	// class XMLNode
	//////////////////////////////////////////////////////////////////////////////////

	XMLNode::XMLNode(std::string const &n)
	{
		name = n;
		parent = NULL;
	}
	
	XMLNode::~XMLNode()
	{
		for (std::vector<XMLNode*>::iterator it = childs.begin(); 
			 it != childs.end(); ++it) {
			XMLNode* c = (*it);
			delete c;
		}
	}
	
	const std::string& XMLNode::getName()
	{
		return name;
	}
	
	XMLNode* XMLNode::getParent()
	{
		return parent;
	}
	
	std::vector<XMLNode*>::const_iterator XMLNode::childBegin()
	{
		return childs.begin();
	}
	
	std::vector<XMLNode*>::const_iterator XMLNode::childEnd()
	{
		return childs.end();
	}
		
	void XMLNode::addChild(XMLNode* child)
	{
		childs.push_back(child);
		child->parent = this;
	}
	
	const std::string&	XMLNode::getAttribute(std::string const &name)
	{
		return attributes[name];
	}
	
	int XMLNode::getAttributeAsInt(std::string const &name)
	{
		return stringToInt(attributes[name]);
	}
	
	
	std::string XMLNode::toString()
	{
		std::string str;
		getString(str, 0);
		return str;
	}
	
	
	void XMLNode::getString(std::string &str, int deep)
	{
		string tb("");
		for (int i=0; i<deep; i++) {
			tb += "\t";
		}
		// name
		str += tb + "<" + name;
		for (std::map<std::string, std::string>::const_iterator it=attributes.begin();
			 it != attributes.end(); ++it) {
			str += " " + (it->first) + "=\"" + (it->second) + "\"";
		}
		str += + ">\n";
		// value
		if (value.length() > 0) {
			str += tb + "\t" + value + "\n";
		}
		// childs
		for (std::vector<XMLNode*>::const_iterator it=childs.begin();
			 it!=childs.end(); ++it) {
			XMLNode* s = (*it);
			s->getString(str, deep+1);
		}
		str += tb + "</" + name + ">\n";
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	XMLNode* parseXML(char const* filename)
	{
		int		ssize;
		void*	sdata = loadData(filename, ssize);				
		if (sdata != NULL) {
			NSLog(@"read size %d", ssize);
			NSData			*data	= [NSData dataWithBytes:sdata length:ssize];
			XMLHelper		*helper = [[XMLHelper alloc] init];
			[helper parse:data]; //设置XML数据
			XMLNode* ret = [helper getRoot];
			free(sdata);
			return ret;
		}
		else {
			NSLog(@"can not found xml data : %s\n", filename);
		}
		return NULL;
	}
	
}; // namespace



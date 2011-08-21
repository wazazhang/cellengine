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


namespace com_cell
{
	//////////////////////////////////////////////////////////////////////////////////
	// class XMLNode
	//////////////////////////////////////////////////////////////////////////////////

	XMLNode::XMLNode(std::string const &n)
	{
		name = n;
	}
	
	XMLNode::~XMLNode()
	{
		for (std::map<std::string, XMLNode*>::iterator it = childs.begin(); 
			 it != childs.end(); ++it) {
			XMLNode* c = (it->second);
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
	
	std::map<std::string, XMLNode*>::iterator XMLNode::childBegin()
	{
		return childs.begin();
	}
	
	std::map<std::string, XMLNode*>::iterator XMLNode::childEnd()
	{
		return childs.end();
	}
	
	XMLNode* XMLNode::getChild(std::string const &name)
	{
		return childs[name];
	}
	
	XMLNode* XMLNode::addChild(XMLNode* child)
	{
		XMLNode* old = childs[child->name];
		childs[child->name] = child;
		child->parent = this;
		return old;
	}
	
	const std::string&	XMLNode::getAttribute(std::string const &name)
	{
		return attributes[name];
	}
	
	int XMLNode::getAttributeAsInt(std::string const &name)
	{
		return stringToInt(attributes[name]);
	}
	
	XMLNode* parseXML(char const* filename)
	{
		int		ssize;
		void*	sdata = loadData(filename, ssize);				
		if (sdata != NULL) {
			NSData			*data	= [NSData dataWithBytes:sdata length:ssize];
			XMLHelper		*helper = [XMLHelper alloc];
			[helper parse:data]; //设置XML数据
			free(sdata);
			return [helper getRoot];
		}
		else {
			NSLog(@"can not found xml data : %s\n", filename);
		}
		return NULL;
	}
	
}; // namespace


//////////////////////////////////////////////////////////////////////////////////
// XMLHelper
//////////////////////////////////////////////////////////////////////////////////
@implementation XMLHelper

using namespace com_cell;

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
	NSLog(@"begin: %@",elementName);
	std::string name = [elementName UTF8String];
	if (current == NULL) {
		current = new XMLNode(name);
	} else {
		XMLNode* next = new XMLNode(name);
		current->addChild(next);
		current = next;
	}
	
	for (id key in attributeDict) {
		NSLog(@" attr: %@=%@",key,[attributeDict objectForKey:key]);
	}
}

//当xml节点有值时，则进入此句 
- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)vstr
{
	vstr = [vstr stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
	if ([vstr length] > 0) {
		std::string value = [vstr UTF8String];
		if (current != NULL) {
			NSLog(@"value: %@", vstr);
		}
	}
}


//当遇到结束标记时，进入此句
- (void)parser:(NSXMLParser *)parser 
 didEndElement:(NSString *)elementName 
  namespaceURI:(NSString *)namespaceURI 
 qualifiedName:(NSString *)qName
{
	std::string name = [elementName UTF8String];
	if (current != NULL) {
		current = current->getParent();
		NSLog(@"  end: %@",elementName);	
	}
}	

- (XMLNode *)getRoot 
{
	return current;
}

@end
////////////////////////////////////////////////////////////////////////////////////



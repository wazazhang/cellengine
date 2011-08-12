/*
 *  GTStorage.mm
 *  Tetris
 *
 *  Created by WAZA on 08-4-22.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTStorage.h"

namespace gt
{

		PropertyMap::PropertyMap(char const *file, char const *ext)
		{
			m_file = file;
			m_ext = ext;
		}
		
		void PropertyMap::load()
		{
			m_Map.clear();
		
			std::vector<std::string> lines;
			
			int res = loadRMSAllTextLine(m_file, m_ext, lines);
			
			if (res>0)
			{
				for (std::vector<std::string>::const_iterator it=lines.begin(); it!=lines.end(); ++it)
				{
					std::vector<std::string> const &kv = stringSplit((*it),"="); 
					
					if(kv.size()>1)
					{
						m_Map[kv[0]] = kv[1];
					}
				}
			}
		}
	
		
		void PropertyMap::save()
		{
			std::vector<std::string> lines;
		
			for (std::map<std::string, std::string>::const_iterator it=m_Map.begin(); it!=m_Map.end(); ++it)
			{
				std::string const &k = (it->first);
				std::string const &v = (it->second);
				std::string line = k + "=" + v;
		
				lines.push_back(line);		
			}
			
			saveRMSAllTextLine(m_file, m_ext, lines);
		}
		
		void PropertyMap::put(std::string const &key, std::string const &value)
		{
			m_Map[key] = value;
		}
		
		void PropertyMap::putInt(std::string const &key, long value)
		{
			m_Map[key] = _INT2STR(value);
		}
		
		void PropertyMap::putFloat(std::string const &key, double value)
		{
			m_Map[key] = _FLOAT2STR(value);
		}
		
		
		std::string PropertyMap::get(std::string const &key)
		{
			std::map<std::string, std::string>::const_iterator it = m_Map.find(key);
	
			if (it != m_Map.end())
			{
				return (it->second);
			}
		
			return "";
		}
		
		long PropertyMap::getInt(std::string const &key)
		{
			long r = 0;
			stringToInt(get(key), r);
			return r;
		}
		
		double PropertyMap::getFloat(std::string const &key)
		{
			double r = 0;
			stringToFloat(get(key), r);
			return r;
		}




}; // namespace gt
/*
 *  GTStorage.h
 *  Tetris
 *
 *  Created by WAZA on 08-4-22.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _GAMETILER_STORAGE
#define _GAMETILER_STORAGE

#include <vector>
#include <string>
#include <map>
#include <math.h>

#include "GTCore.h"
#include "GTGfx.h"
#include "GTImage.h"
#include "GTBlock.h"
#include "GTTiles.h"
#include "GTSprite.h"

namespace gt
{


	class PropertyMap
	{
	
	protected:
	
		std::map<std::string, std::string> m_Map;
	
		const char *m_file;
		const char *m_ext;
	
	public:

		PropertyMap(char const *file, char const *ext);

		void load();
		void save();

		void put(std::string const &key, std::string const &value);		
		void putInt(std::string const &key, long value);		
		void putFloat(std::string const &key, double value);		

		std::string get(std::string const &key);		
		long getInt(std::string const &key);		
		double getFloat(std::string const &key);
		
	}; // class PropertyMap
	
	
	


}; // namespace gt


#endif // #define _GAMETILER_STORAGE


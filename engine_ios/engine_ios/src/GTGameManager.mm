/*
 *  GTGameManager.mm
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-16.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTGameManager.h"



namespace gt
{

 /*
{
	IMAGES=Clips
	
	COUNT=512
	C=0,0,0,32,32
	C=1,32,0,32,32
	C=2,64,0,32,32

}
*/



	u32 EntityManager::__findTiles(std::vector<std::string> const &lines, u32 begin, u32 end)
	{
		UIImage *srcImage = NULL;
		//Image *srcImage = NULL;
		
		Images tiles;
		std::string name = "";

		long count = -1;
		
		for (u32 l=begin+1; l<end && l<lines.size(); l++)
		{
			if (srcImage==NULL && stringGetKeyValue(lines[l], "IMAGES", "=", name) != std::string::npos)	
			{
				srcImage =  [UIImage imageNamed:[NSString stringWithUTF8String:((name+".png").c_str())]];;
				//srcImage = Image::createImage((name+".png").c_str());
				tiles.clear();
			}
			else
			{
				std::string value;
				
				// int value
				if (count==-1 && stringGetKeyValue(lines[l], "COUNT", "=", value) != std::string::npos)
				{
					stringToInt(value, count);
					
					tiles.assign(count, NULL);
				}
				
				// object
				else if (count>0 && stringGetKeyValue(lines[l], "C", "=", value) != std::string::npos)
				{
					std::vector<std::string> const &datas = stringSplit(value, ",");
					
					if(datas.size() >= 5)
					{
						long index	= 0;
						long src_x	= 0;
						long src_y	= 0;
						long w		= 0;
						long h		= 0;
						
						if(stringToInt(datas[0],index)>0)
						if(stringToInt(datas[1],src_x)>0)
						if(stringToInt(datas[2],src_y)>0)
						if(stringToInt(datas[3],w)>0)
						if(stringToInt(datas[4],h)>0)
						
						if(w!=0 && h!=0 && srcImage!=NULL)
						{
							Image* tile = Image::createImage(srcImage, src_x, src_y, w, h);
							tiles[index] = (tile);
						}
						else
						{
							tiles[index] = NULL;
						}
					}
				}
				
			}
		}
		
		if (srcImage!=NULL)	
		{
			//[srcImage release];
			//delete(srcImage);
			m_AllImages[name] = (tiles);
			return end;
		}
		
		return std::string::npos;
	}
	
/*
{
	SPRITE=Spr1
	IMAGES_NAME=Clips

	SCENE_PART_COUNT=8
	SP=0,-16,-16,0,0
	SP=1,-16,-16,1,0
	SP=2,-16,-16,31,0

	SCENE_FRAME_COUNT=8
	SF=0,0,
	SF=1,1,
	SF=2,2,
	
	
	CD_PART_COUNT=1
	CD=0,65535,-16,-16,32,32
	

	CD_FRAME_COUNT=2
	CF=0,0,
	CF=1,

	FRAME_ANIMATE={0,1,},{2,3,},{4,5,},{6,7,},
	FRAME_CD_MAP={0,0,},{0,0,},{0,0,},{0,0,},
	FRAME_CD_ATK={0,0,},{0,0,},{0,0,},{0,0,},
	FRAME_CD_DEF={0,0,},{0,0,},{0,0,},{0,0,},
	FRAME_CD_EXT={0,0,},{0,0,},{0,0,},{0,0,},
	FRAME_COUNTS=2,2,2,2,
}
*/	
	int getArray2D(std::string const &str, std::vector<std::vector<u32> > &arrarys)
	{
		arrarys.clear();
	
		u32 begin = std::string::npos;
		u32 end   = std::string::npos;
		
		for(u32 i=0; i<str.length(); i++)
		{
			if (str.at(i)=='{') begin = i;
			if (str.at(i)=='}') end = i;
				
			if (begin != std::string::npos && end != std::string::npos) 
			{
				std::string const &substr = str.substr(begin+1, end-begin-1);
				
				std::vector<std::string> const &datas = stringSplit(substr, ",");
				
				std::vector<u32> array;
				array.clear() ;
				
				for(u32 p=0; p<datas.size(); p++)
				{
					long id = 0;
					stringToInt(datas[p],id);
					
					array.push_back(id);
				}
				
				arrarys.push_back(array);
				
				begin = end = std::string::npos;
			}
		}
		
		return 1;
	}
	
	
	u32 EntityManager::__findSprite(std::vector<std::string> const &lines, u32 begin, u32 end)
	{
		Sprite2D sprite;
	
		bool finded = false;
		std::string name;
		
		Images *images = NULL;
		long scene_part_count = -1;
		long scene_frame_count = -1;
		long cd_part_count = -1;
		long cd_frame_count = -1;
		long animate_count = -1;
		
		for (u32 l=begin+1; l<end && l<lines.size(); l++)
		{
			if (finded==false && stringGetKeyValue(lines[l], "SPRITE", "=", name) != std::string::npos)	
			{
				finded = true;
			}
			else
			{
				std::string value;
				
				// string value
				if (images==NULL && stringGetKeyValue(lines[l], "IMAGES_NAME", "=", value) != std::string::npos)
				{
					images = getImages(value);
				}
				
				// int value
				else if (scene_part_count<0 && stringGetKeyValue(lines[l], "SCENE_PART_COUNT", "=", value) != std::string::npos)
				{
					stringToInt(value, scene_part_count);
					
					ImageTile tile;
					sprite.SceneParts.assign(scene_part_count, tile);
				}
				else if (scene_frame_count<0 && stringGetKeyValue(lines[l], "SCENE_FRAME_COUNT", "=", value) != std::string::npos)
				{
					stringToInt(value, scene_frame_count);
					
					SceneFrame frame;
					sprite.SceneFrames.assign(scene_frame_count, frame);
				}
				else if (cd_part_count<0 && stringGetKeyValue(lines[l], "CD_PART_COUNT", "=", value) != std::string::npos)
				{
					stringToInt(value, cd_part_count);	
					
					Block block;
					sprite.CDParts.assign(cd_part_count, block);
				}
				else if (cd_frame_count<0 && stringGetKeyValue(lines[l], "CD_FRAME_COUNT", "=", value) != std::string::npos)
				{
					stringToInt(value, cd_frame_count);
					
					CDFrame frame;
					sprite.CDFrames.assign(cd_frame_count, frame);
				}
				
				// object scene part
				else if (scene_part_count>=0 && stringGetKeyValue(lines[l], "SP", "=", value) != std::string::npos)
				{
					std::vector<std::string> const &datas = stringSplit(value, ",");
					//0,-16,-16,0,0
					if(datas.size() >= 5)
					{
						long index		= 0;
						long x			= 0;
						long y			= 0;
						long tileIndex	= 0;
						long trans		= 0;

						if(stringToInt(datas[0],index)>0)
						if(stringToInt(datas[1],x)>0)
						if(stringToInt(datas[2],y)>0)
						if(stringToInt(datas[3],tileIndex)>0)
						if(stringToInt(datas[4],trans)>0)
						{
							ImageTile::createImageTile((*images)[tileIndex], x, y, trans, sprite.SceneParts[index]);
						}

					}
				}
				// object scene frame
				else if (scene_frame_count>=0 && stringGetKeyValue(lines[l], "SF", "=", value) != std::string::npos)
				{
					std::vector<std::string> const &datas = stringSplit(value, ",");
					
					if(datas.size() >= 2)
					{
						long index	= 0;
						if(stringToInt(datas[0],index)>0)
						for (u32 p=1; p<datas.size(); p++)
						{
							long id = 0;
							if(stringToInt(datas[p],id)>0)
							sprite.SceneFrames[index].Parts.push_back(id);
						}
					}
				}
				// object cd part
				else if (cd_part_count>=0 && stringGetKeyValue(lines[l], "CD", "=", value) != std::string::npos)
				{
					std::vector<std::string> const &datas = stringSplit(value, ",");
					//0,65535,-16,-16,32,32
					if(datas.size() >= 6)
					{
						long index	= 0;
						long mask	= 0;
						long x		= 0;
						long y		= 0;
						long w		= 0;
						long h		= 0;
						
						if(stringToInt(datas[0],index)>0)
						if(stringToInt(datas[1],mask)>0)
						if(stringToInt(datas[2],x)>0)
						if(stringToInt(datas[3],y)>0)
						if(stringToInt(datas[4],w)>0)
						if(stringToInt(datas[5],h)>0)
						{
							Block::createBlockRect(mask, x, y, w, h, sprite.CDParts[index]);
						}

					}
				}
				// object cd frame
				else if (cd_frame_count>=0 && stringGetKeyValue(lines[l], "CF", "=", value) != std::string::npos)
				{
					std::vector<std::string> const &datas = stringSplit(value, ",");
					
					if(datas.size() >= 2)
					{
						long index	= 0;
						if(stringToInt(datas[0],index)>0)
						for (u32 p=1; p<datas.size(); p++)
						{
							long id = 0;
							if(stringToInt(datas[p],id)>0)
							sprite.CDFrames[index].Parts.push_back(id);
						}
					}
				}
				// frame counts
				else if (animate_count<0 && stringGetKeyValue(lines[l], "FRAME_COUNTS", "=", value) != std::string::npos)
				{
					animate_count = 0;
				}
				// Animates
				else if (animate_count>=0 && stringGetKeyValue(lines[l], "FRAME_ANIMATE", "=", value) != std::string::npos)
				{
					getArray2D(value, sprite.Animates);
				}
				
				
			}
		}
		
		if (finded!=false)	
		{
			m_AllSprite[name] = (sprite);
			return end;
		}
		
		return std::string::npos;

	}



//	------------------------------------------------------------------------------------------------------------------------------------------------------------------------


	EntityManager::EntityManager(char const *file, char const *ext)
	{
		m_AllImages.clear();
		m_AllSprite.clear();
	
		std::vector<std::string> lines;
		
		int res = loadAllTextLine(file, ext, lines);
		
		if(res>0)
		{
			u32 begin	= std::string::npos;
			u32 end		= std::string::npos;
			u32 l = 0;
			for (std::vector<std::string>::iterator it=lines.begin(); it!=lines.end(); it++, l++)
			{
				u32 len = (*it).length();
				if (len>0 && (*it).at(0)=='{') begin = l;
				if (len>0 && (*it).at(0)=='}') end = l;
				
				if (begin != std::string::npos && end != std::string::npos) 
				{
					if (__findTiles(lines, begin, end) != std::string::npos)
					{
						
					}
					else if (__findSprite(lines, begin, end) != std::string::npos)
					{
					
					}
					
					begin = end = std::string::npos;
				}
			}
		}
	}
	
	EntityManager::~EntityManager()
	{
		for (std::map<std::string, Images>::const_iterator it=m_AllImages.begin(); it!=m_AllImages.end(); it++)
		{
			Images const &images = (it->second);
		
			for (Images::const_iterator it2=images.begin(); it2!=images.end(); it2++)
			{
				Image* res = (*it2);
				delete(res);
			}
		
		}
		
		m_AllImages.clear();
		m_AllSprite.clear();
		
	}

	int EntityManager::createSprite(std::string const &name, Sprite2D &spr)
	{
		std::map<std::string, Sprite2D>::iterator it = m_AllSprite.find(name);
	
		if (it != m_AllSprite.end())
		{
			(it->second).clone(spr);
			
			return 1;
		}
		
		return -1;
	}
	
	Images* EntityManager::getImages(std::string const &name)
	{
		std::map<std::string, Images>::iterator it = m_AllImages.find(name);
	
		if (it != m_AllImages.end())
		{
			return &(it->second);
		}
		
		return NULL;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}; // namespace gt






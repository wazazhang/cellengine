//
//  CStaticSoundInfo.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-14.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_STATIC_SOUND_INFO
#define _COM_CELL_STATIC_SOUND_INFO

#include <string>
#include "CUtil.h"

namespace com_cell 
{
    class StaticSoundInfo : public SoundInfo
    {
	private:
		
		char*		file_path;
		
		ALsizei		channels;
		ALsizei		bitlength;
		ALsizei		freq;
		
		ALsizei		size;
		void*		data;
		
		
    public:
        
		inline StaticSoundInfo(char* const filepath, 
							   ALsizei achannels, 
							   ALsizei abitlength,
							   ALsizei afreq, 
							   ALsizei asize,
							   void* adata)
		{
			file_path	= filepath;
			channels	= achannels;
			bitlength	= abitlength;
			freq		= afreq;
			size		= asize;
			data		= adata;
		}
		
		inline ~StaticSoundInfo()
		{
			if (data != NULL) {
				free (data);
			}
		}
		
		inline virtual char* getFilePath() 
		{
			return file_path;
		}
        
		inline virtual int getChannels()
		{
			return channels;
		}
        
        inline virtual int getBitLength()
		{
			return bitlength;
		}
        
        inline virtual int getFrameRate() 
		{
			return freq;
		}
        
        inline virtual std::string getComment()
		{
			return "";
		}
        
        inline virtual void* getData()
		{
			return data;
		}
        
        inline virtual int getDataSize()
		{
			return size;
		}
		
		//////////////////////////////////////////////////////////////
		
		inline virtual bool isStatic()
		{
			return true;
		}
        
		// not impl
        inline virtual bool hasData()
		{
			return false;
		}
        
		// not impl
        inline virtual void resetData()
		{
			
		}
		
        inline virtual std::string toString()
		{
            std::string sb = std::string("SoundInfo : ");
            sb.append(getFilePath()).append("\n")
            .append("\t  channels : ").append(intToString(getChannels())).append("\n")
            .append("\tbit_length : ").append(intToString(getBitLength())).append("\n")
            .append("\tframe_rate : ").append(intToString(getFrameRate())).append("\n")
            .append(getComment());
            return sb;
        }

    };
    
    
}; // namespcace 

#endif // _COM_CELL_STATIC_SOUND_INFO
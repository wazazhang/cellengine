/*
 *  GTSprite.mm
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTSprite.h"

namespace gt 
{
	Sprite2D::Sprite2D()
	{
		SceneParts.clear();
		SceneFrames.clear();
		
		CDParts.clear();
		CDFrames.clear();
		
		// phisical
		X = 0;
		Y = 0;
		ScaleWidth	= 1;
		ScaleHeight	= 1;
		Angle		= 0;
		Mirror		= false;
		
		// animation 
		CurAnimate	= 0;
		CurFrame	= 0;
		
	}
	
	Sprite2D::~Sprite2D()
	{
		SceneParts.clear();
		CDParts.clear();
		
		SceneFrames.clear();
		CDFrames.clear();
		
		Animates.clear();
	}
	
	void Sprite2D::clone(Sprite2D &dst)
	{
		dst.SceneParts	= SceneParts;
		dst.CDParts		= CDParts;
		dst.SceneFrames	= SceneFrames;
		dst.CDFrames	= CDFrames;
		dst.Animates	= Animates;

	}
	
	
	
	void Sprite2D::move(float dx, float dy)
	{
		X += dx;
		Y += dy;
	}
	
	int Sprite2D::touch(float x, float y)
	{
		if (CurAnimate >= Animates.size()) return -1;
		if (CurFrame >= Animates[CurAnimate].size()) return -1;
		
		CDFrame const &cf = CDFrames[Animates[CurAnimate][CurFrame]];
		
		int i = -1;
		for (std::vector<u32>::const_iterator it=cf.Parts.begin(); it!=cf.Parts.end(); ++it, ++i)
		{
			u32 index = (*it);
			
			if (CDParts[index].touch(x, y))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	
	int Sprite2D::getFrameImage(u32 animate, u32 frame, u32 part, ImageTile* &pImage)
	{
		if (animate >= Animates.size()) return -1;
		if (frame >= Animates[animate].size()) return -1;
		
		SceneFrame const &sf = SceneFrames[Animates[animate][frame]];
		
		if (part >= sf.Parts.size()) return -1;
		
		u32 index = sf.Parts[part];
		
		if (index<SceneParts.size())
		{
			pImage = &(SceneParts[index]);
			return 1;
		}
		
		return -1;
	}
	
	int Sprite2D::getFrameBlock(u32 animate, u32 frame, u32 part, Block &block)
	{
		if (animate >= Animates.size()) return -1;
		if (frame >= Animates[animate].size()) return -1;
		
		CDFrame const &cf = CDFrames[Animates[animate][frame]];
		
		if (part >= cf.Parts.size()) return -1;
		
		u32 index = cf.Parts[part];
		
		if (index<CDParts.size())
		{
			block = CDParts[index];
			return 1;
		}
		
		return -1;
	}
	
	
	
	u32 Sprite2D::getFrameCount(u32 animate)
	{
		if(animate < Animates.size()){
			return Animates[animate].size();
		}
		return 0;
	}
	
	u32 Sprite2D::getAnimateCount()
	{
		return Animates.size();
	}
	
	void Sprite2D::setCurrentFrame(u32 animate, u32 frame)
	{
		CurAnimate = animate % Animates.size();
		CurFrame = animate % Animates[CurAnimate].size();
	}
	
	void Sprite2D::setCurrentAnimate(u32 animate)
	{
		CurAnimate = animate % Animates.size();
		CurFrame = CurFrame % Animates[CurAnimate].size();
	}
	
	bool Sprite2D::nextFrame(bool isCyc)
	{
		if(CurFrame+1 < Animates[CurAnimate].size())
		{
			CurFrame ++;
			return false;
		}
		else if(isCyc) 
		{
			CurFrame = 0;
		}
		return true;
	}
	
	bool Sprite2D::nextAnimate(bool isCyc)
	{
		if(CurAnimate+1 < Animates.size())
		{	
			CurAnimate ++; 
			return false;
		}
		else if(isCyc) 
		{
			CurAnimate = 0; 
		}
		return true;
	}
	
	
	
	
	
	void Sprite2D::render(Graphics2D &g)
	{
		render(g, X, Y, ScaleWidth, ScaleHeight, Mirror, Angle, CurAnimate, CurFrame);
	}
	
	void Sprite2D::render(Graphics2D &g, float x, float y, float w_scale, float h_scale, bool mirror, bool angle, u32 animate, u32 frame)
	{
		if(animate >= Animates.size()) return ;
		if(frame >= Animates[animate].size()) return ;
		
		SceneFrames[Animates[animate][frame]].render(g, SceneParts, x, y, w_scale, h_scale, mirror, angle);
		
	}
	
	
};
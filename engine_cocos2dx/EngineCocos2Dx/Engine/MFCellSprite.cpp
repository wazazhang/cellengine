//
//  CSprite.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-21.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "MFCellSprite.h"


namespace mf
{	    
	using namespace std;
	
	//	------------------------------------------------------------------------------------------
	
	CAnimates*	CSpriteMeta::getAnimates(){
		return &animates;
	}
	
	CCollides*	CSpriteMeta::getCollides(){
		return &collides;
	}
	
	ITiles*		CSpriteMeta::getTiles() {
		return animates.getTiles();
	}

	//	------------------------------------------------------------------------------------------
	
	int CSpriteMeta::getFrameImageCount(int anim, int frame){
		return animates.getFramesRef()[FrameAnimate[anim][frame]].size();
	}
	/*
	Image* CSpriteMeta::getFrameImage(int anim,int frame,int sub){
		return animates.getFrameImage(FrameAnimate[anim][frame], sub);
	}
	*/
	int CSpriteMeta::getFrameImageX(int anim,int frame,int sub){
		return animates.getFrameX(FrameAnimate[anim][frame], sub);
	}
	
	int CSpriteMeta::getFrameImageY(int anim,int frame,int sub){
		return animates.getFrameY(FrameAnimate[anim][frame], sub);
	}
	
	int CSpriteMeta::getFrameImageWidth(int anim, int frame, int sub) {
		return animates.getFrameW(FrameAnimate[anim][frame], sub);
	}
	
	int CSpriteMeta::getFrameImageHeight(int anim, int frame, int sub) {
		return animates.getFrameH(FrameAnimate[anim][frame], sub);
	}
	
	char CSpriteMeta::getFrameImageTransform(int anim, int frame, int sub) {
		return animates.getFrameTransform(FrameAnimate[anim][frame], sub);
	}
	
	//	------------------------------------------------------------------------------------------
	
	vector<vector<int> >* CSpriteMeta::getCDAnimates(char type)
	{
		switch(type){
			case CD_TYPE_MAP:
				return &FrameCDMap;
			case CD_TYPE_ATK:
				return &FrameCDAtk;
			case CD_TYPE_DEF:
				return &FrameCDDef;
			case CD_TYPE_EXT:
				return &FrameCDExt;
		}
		return NULL;
	}
	
	int CSpriteMeta::getFrameCDCount(int anim, int frame, char type)
	{
		vector<vector<int> > *out_animates = getCDAnimates(type);
		if (out_animates != NULL) {
			return out_animates->size();
		}
		return -1;
	}
	
	bool CSpriteMeta::getFrameCD(int anim, int frame, char type, int sub, CCD &outcd)
	{
		vector<vector<int> > *out_animates = getCDAnimates(type);
		if (out_animates != NULL) {
			return collides.getFrameCD((*out_animates)[anim][frame], sub, outcd);
		}
		return false;
	}
	
	//	------------------------------------------------------------------------------------------
	
	void CSpriteMeta::getVisibleBounds(CCD &out_bounds)
	{
		animates.getAllBounds(out_bounds);		
	}
	
	bool CSpriteMeta::getVisibleBounds(int anim, int frame, CCD &out_bounds)
	{
		if (anim < FrameAnimate.size())
		{
			if (frame < FrameAnimate[anim].size())
			{
				out_bounds.X1 = MF_INT32_MAX;
				out_bounds.X2 = MF_INT32_MIN;
				out_bounds.Y1 = MF_INT32_MAX;
				out_bounds.Y2 = MF_INT32_MIN;
				int frameid = FrameAnimate[anim][frame];
				int count = animates.getComboFrameCount(frameid);
				for (int i=0; i<count; i++) {
					out_bounds.X1 = MIN(out_bounds.X1, animates.getFrameX(frameid, i));
					out_bounds.Y1 = MIN(out_bounds.Y1, animates.getFrameY(frameid, i));
					out_bounds.X2 = MAX(out_bounds.X2, animates.getFrameX(frameid, i)+animates.getFrameW(frameid, i));
					out_bounds.Y2 = MAX(out_bounds.Y2, animates.getFrameY(frameid, i)+animates.getFrameH(frameid, i));
				}
				return true;
			}
		}
		
		return false;
	}

	bool CSpriteMeta::getVisibleBounds(int anim, CCD &out_bounds)
	{
		if (anim < FrameAnimate.size()) 
		{
			out_bounds.X1 = MF_INT32_MAX;
			out_bounds.X2 = MF_INT32_MIN;
			out_bounds.Y1 = MF_INT32_MAX;
			out_bounds.Y2 = MF_INT32_MIN;
			for (int f = getFrameCount(anim)-1; f >= 0 ; --f) {
				int frameid = FrameAnimate[anim][f];
				int count = animates.getComboFrameCount(frameid);
				for (int i=0; i<count; i++) {
					out_bounds.X1 = MIN(out_bounds.X1, animates.getFrameX(frameid, i));
					out_bounds.Y1 = MIN(out_bounds.Y1, animates.getFrameY(frameid, i));
					out_bounds.X2 = MAX(out_bounds.X2, animates.getFrameX(frameid, i)+animates.getFrameW(frameid, i));
					out_bounds.Y2 = MAX(out_bounds.Y2, animates.getFrameY(frameid, i)+animates.getFrameH(frameid, i));
				}
			}
			return true;
		}
		return false;
	}
	
	
	
	void CSpriteMeta::getCDBounds(CCD &out_bounds) 
	{
		collides.getAllBounds(out_bounds);	
	}
	
	int CSpriteMeta::getAnimateIndex(string animate_name) {
		for (int i = AnimateNames.size()-1; i>=0; --i) {
			if (stringEquals(AnimateNames[i], animate_name)) {
				return i;
			}
		}
		return -1;
	}
	
	string CSpriteMeta::getAnimateName(int anim) {
		return AnimateNames[anim];
	}

	
	int CSpriteMeta::getAnimateCount() {
		return FrameAnimate.size();
	}
	
	int CSpriteMeta::getFrameCount(int anim) {
		return FrameAnimate[anim].size();
	}
	
	void CSpriteMeta::render(Graphics2D *g, u32 anim, u32 frame) 
	{
		if ( (anim < FrameAnimate.size()) && (frame < FrameAnimate[anim].size()) )
		{
			animates.render(g, FrameAnimate[anim][frame]);
		}
	}
	
	
	void CSpriteMeta::renderDebug(Graphics2D *g, u32 anim, u32 frame) 
	{
		if ( (anim < FrameAnimate.size()) && (frame < FrameAnimate[anim].size()) )
		{
			collides.render(g,FrameCDMap[anim][frame], COLOR_GREEN);
			collides.render(g,FrameCDAtk[anim][frame], COLOR_RED);
			collides.render(g,FrameCDDef[anim][frame], COLOR_BLUE);
			collides.render(g,FrameCDExt[anim][frame], COLOR_WHITE);
			
			g->setColor(COLOR_WHITE);
			g->drawLine(-8, 0, 8, 0);
			g->drawLine(0, -8, 0, 8);
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// CSprite
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	CCellSprite::CCellSprite(CSpriteMeta *src) 
	{		
		meta			= src;
		CurAnimate		= 0;
		CurFrame		= 0;
		IsDebug			= false;
	}

	CCellSprite::~CCellSprite(void)
	{

	}

	CSpriteMeta* CCellSprite::getMeta()
	{
		return meta;
	}
	
	int CCellSprite::getCurrentFrame() {
		return CurFrame;
	}
	
	int CCellSprite::getCurrentAnimate() {
		return CurAnimate;
	}
	
	void CCellSprite::setCurrentAnimate(string const &anim_name) {
		int anim	= meta->getAnimateIndex(anim_name);
		if (anim >= 0) {
			setCurrentAnimate(anim);
		}
	}
	
	void	CCellSprite::setCurrentAnimate(int anim) {
		CurAnimate	= anim;
		CurAnimate	= Math::cycNum(CurAnimate, 0, meta->getAnimateCount());
		CurFrame	= Math::cycNum(CurFrame,   0, meta->getFrameCount(CurAnimate));
	}
	
	void	CCellSprite::setCurrentFrame(int anim, int index) {
		CurAnimate	=	Math::cycNum(anim, 0,		meta->getAnimateCount());
		CurFrame	=	Math::cycNum(index,   0, meta->getFrameCount(CurAnimate));
	}
	
	bool CCellSprite::isEndFrame() {
		if (CurFrame+1 >= meta->FrameAnimate[CurAnimate].size()) {
			return true;
		} else {
			return false;
		}
	}
	
	bool CCellSprite::nextFrame() {
		CurFrame++;
		if (CurFrame >= meta->FrameAnimate[CurAnimate].size() ) {
			CurFrame =  meta->FrameAnimate[CurAnimate].size() - 1;
			return true;
		} else {
			return false;
		}
	}
	
	void CCellSprite::nextCycFrame() {
		CurFrame++;
		if (meta->FrameAnimate[CurAnimate].size()>0){
			CurFrame%=meta->FrameAnimate[CurAnimate].size();
		}
	}
	
	void CCellSprite::nextCycFrame(int restart) {
		CurFrame++;
		if (CurFrame < meta->FrameAnimate[CurAnimate].size()) {
		} else {
			if (meta->FrameAnimate[CurAnimate].size()>0){
				CurFrame = (restart % meta->FrameAnimate[CurAnimate].size());
			}
		}
	}
	
	
	
	bool CCellSprite::prewFrame() {
		CurFrame--;
		if (CurFrame < 0 ) {
			CurFrame = 0;
			return true;
		} else {
			return false;
		}
	}
	
	
	void CCellSprite::prewCycFrame() {
		CurFrame--;
		if (CurFrame < 0 ) {
			CurFrame = meta->FrameAnimate[CurAnimate].size()-1;
		}
	}
	
	
	void CCellSprite::prewCycFrame(int restart) {
		CurFrame--;
		if (CurFrame < 0 ) {
			CurFrame = (restart % meta->FrameAnimate[CurAnimate].size());
		}
	}
	
	
	
	//	------------------------------------------------------------------------------------------
	/*
	void CSprite::render(Graphics2D *g, float x, float y)
	{
		render(g, x, y, CurAnimate, CurFrame);
	}
	
	void CSprite::render(Graphics2D *g, float x, float y, u32 anim, u32 frame) 
	{
		meta->render(g, x, y, anim, frame);
		if (IsDebug)
		{
			meta->renderDebug(g, x, y, anim, frame);
		}
	}
	*/

	void CCellSprite::draw(void)
	{
		mf::Graphics2D g;
		render(&g);
	}

	void CCellSprite::render(Graphics2D *g)
	{
		//g->pushTransform();
		//g->scale(1, -1);
		meta->render(g, CurAnimate, CurFrame);
		if (IsDebug) {
			meta->renderDebug(g, CurAnimate, CurFrame);
		}
		//g->popTransform();
	}
};

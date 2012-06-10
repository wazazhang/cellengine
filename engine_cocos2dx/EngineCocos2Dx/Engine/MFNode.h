
#ifndef _MF_NODE_
#define _MF_NODE_

#include "cocos2d.h"
#include "CCGeometry.h"


namespace mf
{
	class CC_DLL MFNode : public cocos2d::CCNode
	{
	public:

		virtual void init(void);

		virtual float getPositionX();
		virtual float getPositionY();

		virtual void  setPosition(float x, float y);
		virtual void  setPosition(cocos2d::CCPoint const &cp);
	};




}; // namespace mf


#endif // _MF_NODE_

#include "MFNode.h"


namespace mf
{
	void MFNode::init(void)
	{
	}

	float MFNode::getPositionX()
	{
		return m_tPosition.x;
	}

	float MFNode::getPositionY()
	{
		return m_tPosition.y;
	}

	void  MFNode::setPosition(float x, float y)
	{
		CCNode::setPosition(ccp(x, y));
	}
	
	void  MFNode::setPosition(cocos2d::CCPoint const &cp)
	{
		CCNode::setPosition(cp);
	}


}; // namespace mf

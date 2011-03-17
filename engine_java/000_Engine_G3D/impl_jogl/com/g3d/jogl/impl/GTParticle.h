/*
 *  GTParticle.h
 *  Tetris
 *
 *  Created by WAZA on 08-4-19.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#ifndef _GAMETILER_PARTICLE
#define _GAMETILER_PARTICLE

#include <vector>
#include <string>
#include <map>
#include <math.h>

#include "GTGfx.h"
#include "GTImage.h"
#include "GTBlock.h"
#include "GTTiles.h"
#include "GTSprite.h"

namespace gt
{

using namespace gt;

/*
class IParticle
{
	public:
		virtual void render(Graphics2D &g) = 0;
		
}; // IParticle
*/

class IParticle
{
public:

	float X;
	float Y;
	
	u32 MaxTimer;
	u32 Timer;
	
public:

	virtual void render(Graphics2D &g) = 0;

}; // class Particle




class ParticleSystem
{
	protected:
	
		std::vector<IParticle*> m_Particles;
		
	
	public:
		
		ParticleSystem();
		
		~ParticleSystem();
		
		void Spawn(IParticle *particle);		
		
		void render(Graphics2D &g);	
		
		
}; // class ParticleSystem



}; // namespace gt


#endif // #define _GAMETILER_PARTICLE


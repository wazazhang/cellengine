/*
 *  GTParticle.cpp
 *  Tetris
 *
 *  Created by WAZA on 08-4-19.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTParticle.h"

namespace gt
{

		ParticleSystem::ParticleSystem()
		{
			m_Particles.clear();
		}
		
		ParticleSystem::~ParticleSystem()
		{
			for(std::vector<IParticle*>::iterator it=m_Particles.begin(); it!=m_Particles.end(); )
			{
				m_Particles.erase(it);
				delete(*it);
			}
		}
		
		void ParticleSystem::Spawn(IParticle *particle)
		{
			m_Particles.push_back(particle);
		}
		
		void ParticleSystem::render(Graphics2D &g)
		{
			for(std::vector<IParticle*>::iterator it=m_Particles.begin(); it!=m_Particles.end(); it++)
			{
				IParticle *p = (*it);
				
				if(p!=NULL)
				{
					if (p->Timer < p->MaxTimer)
					{
						p->render(g);
						p->Timer++;
					}
				}
				else
				{
					m_Particles.erase(it);
					delete(p);
				}
				
			}
			
		}
		
		
}; // namespace gt
/**
 * 
 */
package game.unit;

import javax.microedition.lcdui.Graphics;

import com.cell.CMath;
import com.cell.CObject;
import com.cell.game.CAnimates;
import com.cell.game.CCollides;
import com.cell.game.CSprite;
import com.cell.game.IState;
import com.cell.particle.CParticle;
import com.cell.particle.CParticleSystem;
import com.cell.particle.IParticleLauncher;


/**
 * @author 张翼飞
 *
 */
abstract public class Unit extends CSprite  implements IState {
	
	public LevelManager world;
	
	
	public int HP 		= 100;

	
	public Unit(CSprite stuff) {
		super(stuff);
		Active = false;
   		Visible = false;
   		
	}

	abstract public void update();
	
	/*TODO 效果列表，对应编辑器Effects*/
	final public int EFFECT_DAMAGE_SWORD 	= 0;
	final public int EFFECT_DAMAGE_ICE		= 1;
	final public int EFFECT_DAMAGE_FIRE		= 2;
	
	final public int EFFECT_TAIL_SWORD 		= 3;
	final public int EFFECT_TAIL_ICE		= 4;
	final public int EFFECT_TAIL_FIRE		= 5;
	
	final public int EFFECT_ATTACK_SWORD	= 6;
	final public int EFFECT_ATTACK_ICE		= 7;
	final public int EFFECT_ATTACK_FIRE		= 8;
	
	final public int EFFECT_MONEY			= 12;
	
	
	final public int EFFECT_CRITICAL		= 100;
	
	public void EffectSpawn(int type,int x,int y,String text, int color){
		world.EffectType = type;
		world.EffectText = text;
		world.EffectColor = color;
		
		switch(type){
		case EFFECT_DAMAGE_SWORD:
		case EFFECT_DAMAGE_ICE:
		case EFFECT_DAMAGE_FIRE:
			world.ParticleSystem.spawn(1, world.TYPE_HOLD, x, y);
			break;
		case EFFECT_TAIL_SWORD:
		case EFFECT_TAIL_ICE:
		case EFFECT_TAIL_FIRE:
			world.ParticleSystem.spawn(1, world.TYPE_HOLD, x, y);
			break;
		case EFFECT_ATTACK_SWORD:
		case EFFECT_ATTACK_ICE:
		case EFFECT_ATTACK_FIRE:
			world.ParticleSystem.spawn(8, world.TYPE_EXP, x, y);
			break;
			
		case EFFECT_MONEY:
			world.ParticleSystem.spawn(1, world.TYPE_SINGLE_UP, x, y);
			world.ParticleSystem.spawn(1, world.TYPE_TEXT, 		x, y);
			break;
		case EFFECT_CRITICAL:
			world.ParticleSystem.spawn(1, world.TYPE_TEXT, 		x, y);
			break;
		default:
			world.ParticleSystem.spawn(1, world.TYPE_HOLD, x, y);
		}
		
		
	}

}



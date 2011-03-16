package game.unit;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

public class UnitTower extends CSprite implements IState {

	
	int state		= -1;
	
	public UnitTower(CSprite spr) {
		super(spr);
		super.setState(this);
	}

	public void update() {
		switch(state){
		case STATE_BUILD:
			if(!isEndBuild()){
				onBuild();
			}else{
				startDefence();
			}
			break;
		case STATE_ATTACK:
			if(!isEndAttack()){
				onAttack();
			}else{
				startDefence();
			}
			break;
		case STATE_DEFENCE:
			if(!isEndDefence()){
				onDefence();
			}else{
				startDefence();//end
			}
			break;
		}
	}
	
//	-----------------------------------------------------------------------------------------------
	
	
	
	
//	-----------------------------------------------------------------------------------------------
//	building
	final int STATE_BUILD	= 0;
	
	public void startBuild(int sx,int sy){
		state = STATE_BUILD;
		Active = true;
		Visible = true;

		tryMove(sx-X, sy-Y);

	}
	public boolean isEndBuild(){
		return true;
	}
	public void onBuild(){
	}

//	----------------------------------------------------------------------------------------------------
//	attack
	final int STATE_ATTACK		= 1;
	int AttackScope				= 100;
	int AttackForzenTime 		= 100;
	int AttackTime				= 1000;
	public void startAttack(UnitEnemy[] enemys,UnitShoot[] shoots){
		if(!isEndAttack())return;
		state = STATE_ATTACK;
		Active = true;
		Visible = true;
		AttackTime = 0;
		
		for(int i=0;i<shoots.length;i++){
			if(shoots[i].Active==false){
				int start = Math.abs(Random.nextInt()%enemys.length);
				for(int j=0;j<enemys.length;j++){
					int id = (j + start) % enemys.length;  
					if( enemys[id].Active==true && 
						CCD.cdRect(
							X-AttackScope, Y-AttackScope, 
							X+AttackScope, Y+AttackScope, 
							enemys[id].X-AttackScope, enemys[id].Y-AttackScope, 
							enemys[id].X+AttackScope, enemys[id].Y+AttackScope)
							){
						shoots[i].startMissile(
								X+collides.getCD(0).X1,
								Y+collides.getCD(0).Y1,
								enemys[id]);
						break;
					}
				}
				break;
			}
		}
		
	}
	public boolean isEndAttack(){
		return AttackTime>=AttackForzenTime;
	}
	public void onAttack(){
		AttackTime++;
	}
//	----------------------------------------------------------------------------------------------------
//	defence
	final int STATE_DEFENCE		= 2;
	int DefenceMaxTime 			= 100;
	int DefenceTime				= 100;
	public void startDefence(){
		state = STATE_DEFENCE;
		Active = true;
		Visible = true;
		
		DefenceTime = DefenceMaxTime;
	}
	public boolean isEndDefence(){
		return DefenceTime<0;
	}
	public void onDefence(){
		DefenceTime--;
	}
}

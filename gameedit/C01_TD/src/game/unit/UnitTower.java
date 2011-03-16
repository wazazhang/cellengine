package game.unit;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

public class UnitTower extends Unit  {

	
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
	boolean isEndBuild(){
		return true;
	}
	void onBuild(){
	}

//	----------------------------------------------------------------------------------------------------
//	attack
	final int STATE_ATTACK		= 1;
	int AttackRange				= 100;
	int AttackForzenTime 		= 10;
	int AttackTime				= 1000;
	public void startAttack(UnitEnemy[] enemys,UnitShoot[] shoots){
		if(!isEndAttack())return;
		state = STATE_ATTACK;
		Active = true;
		Visible = true;
		AttackTime = 0;
		//找到活动子弹
		for(int i=shoots.length-1;i>=0;i--){
			if(shoots[i].Active==false){
				//找到敌人
				for(int j=enemys.length-1;j>=0;j--){
					if( enemys[j].Active==true && 
						CCD.cdRectPoint(
							X-AttackRange, Y-AttackRange, 
							X+AttackRange, Y+AttackRange, 
							enemys[j].X, enemys[j].Y)
							){
						shoots[i].startFire(
								//TODO: set fire type
								Math.abs(Random.nextInt())%UnitShoot.TYPE53_FIRE,
								X+collides.getCD(0).X1,
								Y+collides.getCD(0).Y1,
								enemys,
								j);
						break;
					}
				}
				break;
			}
		}
		
	}
	boolean isEndAttack(){
		return AttackTime>=AttackForzenTime;
	}
	void onAttack(){
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
	boolean isEndDefence(){
		return DefenceTime<0;
	}
	void onDefence(){
		DefenceTime--;
	}
}

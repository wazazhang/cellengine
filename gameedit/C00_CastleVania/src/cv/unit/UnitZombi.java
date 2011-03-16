package cv.unit;

import javax.microedition.lcdui.Image;

import com.cell.CIO;
import com.cell.CSoundPlayer;
import com.cell.IImages;
import com.mascotcapsule.micro3d.v3.ActionTable;

public class UnitZombi extends Unit {
	
	boolean onDamage;
	boolean onAttack;
	int damageTime = 0;
	
	Image img_face ;
	CSoundPlayer snd_damage;
	
	public UnitZombi(){
		Team = 1;
		IsIncline = true;
		img_face = CIO.loadImage("/face2.png");
		snd_damage = new CSoundPlayer("/dingnigefei.wav",CSoundPlayer.TYPE_WAV,1);
	}
	
	public void update() {
		if(damageTime>0){
			damageTime--;
		}else{
			onAction();
		}
	}
	
	
	public void attack(Unit unit){
		
	}
	
	public void damage(Unit unit){
		damageTime = 5;
		onDamage = true;
		
		world.showMessage(
				"¶¥Äã¸ö·Î!", 
				img_face, 
				false);
		snd_damage.replay();
		
		HP-=unit.Attack;
		
		SpawnNumber(unit.Attack, X, Y - getVisibleHeight());
		
		if(HP<=0)startDead();
	}

//	-----------------------------------------------------------------------------------------

	final int STATE_START		= 0;
	final int STATE_WALKING		= 1;
	final int STATE_END 		= 2;
	final int STATE_DEAD		= 3;
	
	int state = -1;
	
	void onAction(){
		switch(state){
		case STATE_START:
			if(nextFrame()){
				state = STATE_WALKING;
				setCurrentFrame(state, 0);
			}
			break;
		case STATE_WALKING:
			nextCycFrame();
			if(actMoveX(WalkSpeed)){
				transform(IImages.TRANS_H);
				WalkSpeed*=-1;
			}
			if(--WalkTimer<0){
				state = STATE_END;
				setCurrentFrame(state, 0);
				WalkTimer = -1;
			}
			break;
		case STATE_END:
			if(nextFrame()){
				startHold();
			}
			break;
		case STATE_DEAD:
			if(nextFrame()){
				startHold();
			}
			SpawnFireU(X+Random.nextInt()%getVisibleWidth(), Y-Math.abs(Random.nextInt()%getVisibleHeight()));
			break;
		default:
			if(--HoldTimer<0){
				HoldTimer = (40*2) + Random.nextInt()%(40*2);
				startWalk();
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------
	int HoldTimer = -1;
	public void startHold(){
		state = -1;
		Active = false;
		Visible = false;
	}
	
//	-----------------------------------------------------------------------------------------
	int WalkTimer = -1;
	int WalkSpeed = 2;
	public void startWalk(){
		Active = true;
		Visible = true;
		state = STATE_START;
		setCurrentFrame(state, 0);
		WalkTimer = 4*40 + Random.nextInt()%2*40;
		WalkSpeed *= Math.abs(Random.nextInt()%2)==0?-1:1;
		
		if(WalkSpeed>0){
			if(getCurTransform()!=IImages.TRANS_H){
				transform(IImages.TRANS_H);
			}
		}
		if(WalkSpeed<0){
			if(getCurTransform()!=IImages.TRANS_NONE){
				transform(IImages.TRANS_H);
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------

	public void startDead(){
		Active = false;
		Visible = true;
		state = STATE_DEAD;
		setCurrentFrame(state, 0);
		
	}
	
}

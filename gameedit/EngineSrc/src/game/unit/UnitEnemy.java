package game.unit;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

public class UnitEnemy extends CSprite implements IState  {

	public int state = -1;
	
	public UnitEnemy(CSprite stuff){
		super(stuff);
		super.setState(this);
	}
	
	
	public void update() {
		switch(state){
		case STATE_HOLD:
			if(!isEndHold()){
				onHold();
			}else{
				startMove(NextWayPoint);
			}
			break;
		case STATE_MOVE:
			if(!isEndMove()){
				onMove();
			}else{
				startMove(NextWayPoint);
			}
			break;
//		case STATE_SWING:
//			if(!isEndSwing()){
//				onSwing();
//			}else{
//				startMove(NextWayPoint);
//			}
//			break;
		}
		
	}
	
//	---------------------------------------------------------------------------------------
//	hold
	final public int STATE_HOLD 	= 1;
	public int HoldTime = 100;
	public void startHold(){
		
		state = STATE_HOLD;
		Active = true;
		Visible = true;
		
		HoldTime = Math.abs(Random.nextInt()%200);
	}
	public boolean isEndHold(){
		return HoldTime<0;
	}
	public void onHold(){
		HoldTime--;
		setCurrentFrame(0, 0);
	}

	
//	------------------------------------------------------------------------------------
//	patrol
	final public int STATE_MOVE 	= 0;
	public CWayPoint NextWayPoint;
	private CWayPoint PrewWayPoint;
	public int MaxSpeed = 5 + Math.abs(Random.nextInt()%4) ;
	
	public void startMove(CWayPoint next){
			
		state = STATE_MOVE;
		Active = true;
		Visible = true;
		

		NextWayPoint = next ;
		if(NextWayPoint.getNextCount()>0){
			int id = Math.abs(Random.nextInt()%NextWayPoint.getNextCount());
			if(PrewWayPoint != NextWayPoint.getNextPoint(id)){
				PrewWayPoint = NextWayPoint;
				NextWayPoint = NextWayPoint.getNextPoint(id);
			}else{
				PrewWayPoint = NextWayPoint;
				NextWayPoint = NextWayPoint.getNextPoint((id+1)%NextWayPoint.getNextCount());
			}
		}
	}
	public boolean isEndMove(){
		return CCD.cdRect(
				X - MaxSpeed, Y - MaxSpeed, 
				X + MaxSpeed, Y + MaxSpeed, 
				NextWayPoint.X , NextWayPoint.Y , 
				NextWayPoint.X , NextWayPoint.Y );
	}
	
	public void onMove(){
		DirectX = NextWayPoint.X - X;
		DirectY = NextWayPoint.Y - Y;
	
		if(DirectX == 0 && DirectY == 0){
			setCurrentFrame(0, getCurrentFrame());
		}else if(DirectY < 0 ){
			setCurrentFrame(0, getCurrentFrame());
		}else if(DirectY > 0){
			setCurrentFrame(2, getCurrentFrame());
		}else if(DirectX < 0){
			setCurrentFrame(3, getCurrentFrame());
		}else if(DirectX > 0){
			setCurrentFrame(1, getCurrentFrame());
		}
		
		nextCycFrame();
		
		int dx = (DirectX==0 ? 0 : (DirectX>0 ? MaxSpeed : -MaxSpeed));
		int dy = (DirectY==0 ? 0 : (DirectY>0 ? MaxSpeed : -MaxSpeed));
		
		if(Math.abs(dx)>Math.abs(DirectX))dx = DirectX;
		if(Math.abs(dy)>Math.abs(DirectY))dy = DirectY;
		
		tryMove(dx, dy);

	}
	
	
//	------------------------------------------------------------------------------------
//	swing
	final public int STATE_SWING	= 2;

	public int SwingTime = 100;
	
	public void startSwing(){
		if(state!=STATE_SWING){
			state = STATE_SWING;
			Active = true;
			Visible = true;
			
			SwingTime = 20;
			HPos256 = X * 256 ; 
			VPos256 = Y * 256 ; 
			
			
		}
	}
	public boolean isEndSwing(){
		return SwingTime<0;
	}
	public void onSwing(){
		SwingTime--;
	
		HPos256 -= CMath.sinTimes256(SwingTime*90)*4;
		VPos256 -= CMath.cosTimes256(SwingTime*90)*4;
		X = HPos256 / 256 ;
		Y = VPos256 / 256 ;
		
		
	}

}

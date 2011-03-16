package Cell;

public class CObject {
	public boolean isDebug = true;
	public int DebugColor = 0xff8080ff;
//	------------------------------------------------------

	static private int Timer = 1;
	
	static public int getTimer(){
		return Timer;
	}
	static public void resetTimer(){
		Timer = 1;
	}
	
	static public void tickTimer(){
		Timer++;
	}
//	------------------------------------------------------

	static public void print(String str){
		System.out.print(str);
	}
	
	static public void println(String str){
		System.out.println(str);
	}
}

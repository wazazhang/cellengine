
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import com.cell.AScreen;
import com.cell.CCanvas20;
import com.cell.CCanvasJPhone;
import com.cell.CCanvasNokia;
import com.cell.CObject;

final public class GameMIDlet extends MIDlet  implements Runnable{
	
	Canvas canvas ;

//------------------------------------------------------------------------------------------
	
	
	public GameMIDlet() {
		CObject.IsDebug = false;
		CObject.IsDebug = true;
		
		System.out.println("Total Memory = "+(Runtime.getRuntime().totalMemory()/1024)+"(K byte)");

		canvas = new CCanvas20();
		canvas.setFullScreenMode(true);
		AScreen.SCREEN_WIDTH  = canvas.getWidth();
		AScreen.SCREEN_HEIGHT = canvas.getHeight();
		
//		canvas = new CCanvasNokia();
//		AScreen.SCREEN_WIDTH  = canvas.getWidth();
//		AScreen.SCREEN_HEIGHT = canvas.getHeight();
		
//		canvas = new CCanvasJPhone();
//		AScreen.SCREEN_WIDTH = 176;
//		AScreen.SCREEN_HEIGHT = 208;


		AScreen.SCREEN_HCENTER = AScreen.SCREEN_WIDTH/2;
		AScreen.SCREEN_VCENTER = AScreen.SCREEN_HEIGHT/2;
		System.out.println("Screen W = " + AScreen.SCREEN_WIDTH);
		System.out.println("Screen H = " + AScreen.SCREEN_HEIGHT);
		
		Display.getDisplay(this).setCurrent(canvas);

		//ÇÐ»»µ±Ç°ÆÁÄ»µ½ScreenSCWorld
		AScreen.ChangeSubScreen("ScreenLogo");

		(new Thread(this)).start();

	}
	final public void startApp() {
		System.out.println("Free Memory = "+(Runtime.getRuntime().freeMemory()/1024)+"(K byte)");
    }
	final public void pauseApp() {
	}
	final public void destroyApp(boolean unconditional) {
		System.out.println("Free Memory = "+(Runtime.getRuntime().freeMemory()/1024)+"(K byte)");
		notifyDestroyed();
	}
	
//---------------------------------------------------------------------------------------------------------------
	
	final public void run() {
		long UsedTime;
		try{
			while (!AScreen.ExitGame) {
				UsedTime = System.currentTimeMillis();

				canvas.repaint();
				canvas.serviceRepaints();
				
				UsedTime = System.currentTimeMillis() - UsedTime;
				
				if (UsedTime < AScreen.FrameDelay) {
					Thread.sleep(AScreen.FrameDelay - UsedTime);
				}else{
				    Thread.sleep(1);
				}
			}
		}catch(Exception err){
			System.out.println(err.getMessage());
		}
		destroyApp(true);
	}
	
}
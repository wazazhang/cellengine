
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import com.cell.CCanvas20;
import com.cell.CCanvasNokia;
import com.cell.CObject;
import com.cell.AScreen;

final public class GameMIDlet extends MIDlet  implements Runnable{
	
	Canvas canvas ;

//------------------------------------------------------------------------------------------
	
	
	public GameMIDlet() {
//#ifndef _DEBUG
		CObject.IsDebug = false;
//#else
		CObject.IsDebug = true;
//#endif	
		
////#ifdef _NOKIA_UI
//		CObject.IsNokia = true;
////#else
//		CObject.IsNokia = false;
////#endif		
		
		System.out.println("Total Memory = "+(Runtime.getRuntime().totalMemory()/1024)+"(K byte)");

//		if(CObject.IsNokia){
//			canvas = new CCanvasNokia();
//		}else{
			canvas = new CCanvas20();
			canvas.setFullScreenMode(true);
//		}

		
		AScreen.SCREEN_WIDTH = canvas.getWidth();
		AScreen.SCREEN_HEIGHT = canvas.getHeight();
		AScreen.SCREEN_HCENTER = canvas.getWidth()/2;
		AScreen.SCREEN_VCENTER = canvas.getHeight()/2;
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
				    //Thread.sleep(1);
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		destroyApp(true);
	}
	
}
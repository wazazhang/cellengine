using System;
using System.Windows.Forms;
using System.Drawing;
using System.Threading;

namespace GameLib
{

	public class GameEngine 
	{
		private IGameCanvas gameCanvas;

		int FPS = 30;
		int MSPF = 1000/30;
		int Timer = 0;

		bool exit = false;

		Thread thread ;

		public GameEngine(IGameCanvas gc)
		{
	
			gameCanvas = gc;
			gameCanvas.Init();
			thread = new Thread(new ThreadStart(run));
			thread.Start();
		
		}



		private void run()
		{
			int Time = 0;
			int SleepTime = 0;

			while(!exit)
			{
				Time = System.DateTime.Now.Millisecond;
				if(SleepTime>0)Thread.Sleep(SleepTime);

				//Main Logic
				gameCanvas.Logic();
	
				//Main Render
				gameCanvas.Render();

				
				SleepTime = MSPF - (System.DateTime.Now.Millisecond - Time);
				
			}
		}


		public void Exit()
		{	
			exit = true ;
			
		}

		public void SetFps(int fps)
		{
			FPS = fps ;
			MSPF = 1000/FPS;
		}

		public int GetTimer()
		{
			return Timer;
		}
	};





}

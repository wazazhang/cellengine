package com.cell.sound
{
	import com.cell.io.UrlManager;
	
	import flash.events.Event;
	import flash.media.Sound;
	import flash.media.SoundChannel;

	public class StreamSound
	{
		private var playing	: Boolean = false;
		
		private var bgS		: Sound;
		private var bgSC	: SoundChannel;
		
		public function StreamSound(url:String)
		{	
			var bg : Sound = new Sound();
			bg.addEventListener(Event.COMPLETE, bg_complete);
			bg.load(UrlManager.getUrl(url));
		}
		
		private function bg_complete(e:Event) : void
		{
			bgS = e.currentTarget as Sound;
			if (playing) {
				playBgMusic();
			}
		}
		
		
		public function playBgMusic():void
		{
			this.playing = true;
			if (bgS!=null) {
				if (bgSC == null) {
					bgSC = bgS.play(0, int.MAX_VALUE);	
				}
			}
		}
		
		public function stopBgMusic():void
		{
			this.playing = false;
			if (bgSC!=null) {	
				bgSC.stop();
				bgSC = null;
			}
		}
		
	}
}
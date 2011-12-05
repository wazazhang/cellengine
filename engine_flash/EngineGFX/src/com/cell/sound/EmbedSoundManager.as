package com.cell.sound
{
	import com.cell.util.Map;
	
	import flash.events.Event;
	import flash.media.Sound;
	import flash.media.SoundChannel;
	import flash.media.SoundTransform;

	public class EmbedSoundManager
	{
			private var enable : Boolean = true;
			
			private var sound_map : Map = new Map();
						
			public function addSoundClasses(soundclass : Array) : void
			{
				for each (var c : Class in soundclass) {
					addSoundClass(c);
				}
			}
			
			public function addSoundClass(c : Class) : void
			{
				var sd : Sound = new c as Sound;
				sd.play(0,0,new SoundTransform(0,0));
				sound_map.put(c, sd);
			}
			
			public function getEnable() : Boolean
			{
				return enable;
			}
			
			public function setEnable(e:Boolean) : void
			{
				enable = e;
			}
			
			public function playSound(cls : Class, loop:int=0) : SoundChannel
			{
				if (enable) {
					var sd : Sound = sound_map.get(cls);
					try {
						var sc : SoundChannel = sd.play(0, loop, null);	
						return sc;
					} catch (err:Error) {
						trace(err.getStackTrace());
					}
				}
				return null;
			}

	}
}
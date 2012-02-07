package com.cell.persistance
{
	import com.cell.openapi.SaveData;
	import com.cell.openapi.SaveDataEvent;
	
	import flash.net.SharedObject;
	import flash.utils.ByteArray;

	public class SaveDataShared extends SaveData
	{
		public function SaveDataShared(key:String) 
		{
			super(key);
		}
		
		override public function load():void
		{
			var evt : SaveDataEvent = new SaveDataEvent(SaveDataEvent.LOADED);
			evt.data = this;
			try {
				var so:SharedObject	= SharedObject.getLocal(getKey());
				var bin : ByteArray	= so.data.bin;
				var key : String 	= so.data.key;
				super._data = bin;
			} catch (e:Error) {
				throw e;
			}
			dispatchEvent(evt);
		}
		
		override public function save(data:ByteArray):void
		{
			var evt : SaveDataEvent = new SaveDataEvent(SaveDataEvent.SAVED);
			evt.data = this;
			this._data = data;
			try {
				var so:SharedObject = SharedObject.getLocal(getKey());
				so.clear();
				so.data.key = getKey();
				so.data.bin = data;
				so.flush();
			} catch (e:Error) {
				throw e;
			}
			this._data.position = 0;
			dispatchEvent(evt);
		}

				
			
		
	}
}
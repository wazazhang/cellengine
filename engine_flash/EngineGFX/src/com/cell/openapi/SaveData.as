package com.cell.openapi
{ 
	import flash.events.EventDispatcher;
	import flash.utils.ByteArray;
	
	[Event(name=SaveDataEvent.LOADED, type="com.cell.openapi.SaveDataEvent")]  
	[Event(name=SaveDataEvent.SAVED,  type="com.cell.openapi.SaveDataEvent")]  
	public class SaveData extends EventDispatcher
	{
		private var _key : String = null;
		
		protected var _data : ByteArray = null;
		
		public function SaveData(key:String) 
		{
			this._key = key;
		}
		
		public function getKey() : String
		{
			return _key;
		}
	
		public function getLoadedData() : ByteArray
		{
			return _data;
		}
		
		public function isLoaded() : Boolean
		{
			return _data != null;
		}
		
		public function load():void
		{
			throw new Error("Must implements this class");
		}
		
		public function save(data:ByteArray):void
		{
			throw new Error("Must implements this class");
		}
	}
}
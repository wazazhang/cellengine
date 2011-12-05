package com.cell.openapi
{
	import flash.events.EventDispatcher;
	
	[Event(name=TopPlayersEvent.LOADED, type="com.cell.openapi.TopPlayersEvent")]  
	[Event(name=TopPlayersEvent.SAVED,  type="com.cell.openapi.TopPlayersEvent")]  
	public class TopPlayers extends EventDispatcher
	{
		private var _key : String;
		
		protected var score_self : TopPlayerScore;
		
		protected var score_firends : Array = new Array();
		
		public function TopPlayers(key:String)
		{
			this._key = key;
		}
		
		public function getKey() : String
		{
			return _key;
		}
		
		public function getFirends() : Array
		{
			return score_firends;
		}
		
		public function getMyself() : TopPlayerScore
		{
			return score_self;
		}
		
//		---------------------------------------------------------------------------------------------------------------------
		
		public function commit(score:TopPlayerScore) : void
		{
			throw new Error("Must implements this class");
		}
		
		public function query() : void
		{
			throw new Error("Must implements this class");
		}
		
//		---------------------------------------------------------------------------------------------------------------------
	}
}
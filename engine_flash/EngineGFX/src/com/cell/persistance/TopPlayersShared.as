package com.cell.persistance
{
	import com.cell.openapi.TopPlayerScore;
	import com.cell.openapi.TopPlayers;
	import com.cell.openapi.TopPlayersEvent;
	
	public class TopPlayersShared extends TopPlayers
	{
		public function TopPlayersShared(key:String)
		{
			super(key);
		}
		
		override public function commit(score:TopPlayerScore) : void
		{
			var evt : TopPlayersEvent = new TopPlayersEvent(TopPlayersEvent.SAVED);
			evt.data = this;
			evt.error_message = "commit";
			dispatchEvent(evt);
		}
		
		override public function query() : void
		{
			var evt : TopPlayersEvent = new TopPlayersEvent(TopPlayersEvent.LOADED);
			evt.data = this;
			evt.error_message = "query";
			dispatchEvent(evt);

		}

	}
}
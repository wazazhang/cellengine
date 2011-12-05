package com.cell.openapi
{
   /**
	*  游戏玩家积分类。
	*/
	public class TopPlayerScore
	{
		public var player_id : *;
		
		/**
		 *  游戏玩家的昵称.
		 */
		public var nickName:String = "";
		
		/**
		 *  游戏玩家在当前游戏的最高积分.
		 */
		public var score:Number = 0;
		
		/**
		 *  获得此次积分的游戏时间.
		 *  <p>该值为至公元1970-01-01 00:00:00(UTC)算起到现在经过的秒数.</p>
		 */
		public var playTime:Number = 0;
		
		/**
		 *  当前玩家的积分排名.
		 */
		public var rank:uint = 0;
		

	}
}
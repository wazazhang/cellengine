package com.cell.openapi
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.io.UrlWrapper;
	import com.cell.persistance.SaveDataShared;
	import com.cell.persistance.TopPlayersShared;
	
	import flash.utils.ByteArray;

	/**abstract <br>
	 * 用于管理开放平台数据存储，资源管理等功能*/
	public class PlatformManager implements UrlWrapper
	{
		public function isDebug() : Boolean
		{
			return false;
		}
		
		//获取资源路径
		public function getWrapperUrl(src_url:String):String
		{
			return src_url;
		}
		
		public function createSaveData(key:String) : SaveData
		{
			return new SaveDataShared(key);
		}
		
		public function createTopPlayers(key:String) : TopPlayers
		{
			return new TopPlayersShared(key);
		}
		
		
		public function createResourceLoader(url:String, output:OutputLoader=null) : ResourceLoader
		{
			return new ResourceLoader(url, output);
		}
	}
}
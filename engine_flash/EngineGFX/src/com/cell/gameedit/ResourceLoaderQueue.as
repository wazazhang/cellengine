package com.cell.gameedit
{
	public class ResourceLoaderQueue
	{
		private var loaded : Vector.<ResourceLoader>;
		private var waited : Vector.<ResourceLoader>;
		
		public function ResourceLoaderQueue(list:Vector.<ResourceLoader>)
		{
			this.waited = list;	
		}
		
		private function onComplete(e:ResourceEvent) : void
		{
			
		}
	}
}
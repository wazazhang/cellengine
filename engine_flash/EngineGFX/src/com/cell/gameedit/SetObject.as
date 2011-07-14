package com.cell.gameedit
{
	import com.cell.net.io.MutualMessage;

	public interface SetObject extends MutualMessage
	{
		function 	getIndex() : int;
		
		function 	getName() : String;
	}
}

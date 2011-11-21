package com.cell.util
{
	import com.cell.ui.Anchor;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.geom.ColorTransform;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.utils.ByteArray;

	public class Util
	{
		public static function dumpHex(data : ByteArray) : String
		{
			var pos : int = data.position;
			data.position = 0;
			var ret : String = "";
			for (var i : int = 0; i<data.length; i++) {
				var dn : String = (0x1000 | data.readByte()).toString(16);
				ret += dn.substr(2,2) + " ";
			}
			data.position = pos;
			return ret;
		}
		
		public static function getRandom( min:Number,  max:Number) : Number
		{
			return min + Math.random() * (max - min);
		}
		
		public static function getRandomInt( min:int,  max:int) : int
		{
			return min + Math.round(Math.random() * (max - min));
		}
		
		
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		public static function clearChilds(container:DisplayObjectContainer) : int
		{
			var count : int = 0;
			while (container.numChildren>0) {
				container.removeChildAt(0);
				count ++;
			}
			return count;
		}
		
		/**
		 * Util.sortChilds(this, "y", Array.NUMERIC);
		 * Util.sortChilds(this, ["z", "y"], [Array.NUMERIC, Array.NUMERIC]);
		 */
		public static function sortOnChilds(container:DisplayObjectContainer, childFieldNames:*, options:*=0) : void
		{
			var numChildren:int = container.numChildren;
			//no need to sort (zero or one child)
			if( numChildren < 2 ) return;
			
			var depthsSwapped:Boolean;
			
			//create an Array to sort children
			var children:Array = new Array( numChildren );
			var i:int = -1;
			while( ++i < numChildren )
			{
				children[ i ] = container.getChildAt( i );
			}
			
			children.sortOn(childFieldNames, options);
			
			var child:DisplayObject;
			var count:int = (numChildren-1);
			i = -1;
			while( ++i < count)
			{
				child = DisplayObject( children[ i ] );
				//only set new depth if necessary
				if( i != container.getChildIndex( child ) )
				{
					//set their new position
					container.setChildIndex( child, i );
				}
				
			}
			
		}
		
		/**
		 * Util.sortChilds(this, compare, Array.NUMERIC);
		 */
		public static function sortChilds(container:DisplayObjectContainer, compare:Function, options:*=0) : void
		{
			var numChildren:int = container.numChildren;
			//no need to sort (zero or one child)
			if( numChildren < 2 ) return;
						
			//create an Array to sort children
			var children:Array = new Array( numChildren );
			var i:int = -1;
			while( ++i < numChildren )
			{
				children[ i ] = container.getChildAt( i );
			}
			
			children.sort(compare, options);
			
			var child:DisplayObject;
			var count:int = (numChildren-1);
			i = -1;
			while( ++i < count)
			{
				child = DisplayObject( children[ i ] );
				//only set new depth if necessary
				if( i != container.getChildIndex( child ) )
				{
					//set their new position
					container.setChildIndex( child, i );
				}
				
			}
			
		}
		
		/**深度拷贝*/
		public static function cloneObject(source:Object):*
		{
			var myBA:ByteArray = new ByteArray();
			myBA.writeObject(source);
			myBA.position = 0;
			return(myBA.readObject());
		}
		
		
		public static function sortChildsPop(container:DisplayObjectContainer, compare:Function) : void
		{
			var temp : DisplayObject;
			var tag : Boolean = true;
			var oj : DisplayObject;
			var oj1 : DisplayObject;
			for (var i : int = container.numChildren - 1; i >= 0; i--) {
				for (var j : int = 0; j < i; j++) {
					oj = container.getChildAt(j);
					oj1 = container.getChildAt(j + 1);
					if(compare.call(null, oj, oj1)>0){
						container.setChildIndex(oj, j+1);
						container.setChildIndex(oj1, j);
						tag = true;
					}
				}
				if(tag==false)break;
			}
		}

	}
}
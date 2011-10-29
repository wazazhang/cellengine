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
		
		/**
		 * 整合所有Bitmap Class
		 * */
		public static function combineImageClass(anchor:int, ... classes) : Bitmap
		{
			var array : Vector.<BitmapData> = new Vector.<BitmapData>();
			for each (var c : Class in classes) {
				array.push((new c() as Bitmap).bitmapData);
			}
			
			var bd : BitmapData = combineImage(anchor, array);
			
			return new Bitmap(bd);
			
		}
		
		/**
		 * 整合所有BitmapData
		 */
		public static function combineImageBitmapData(anchor:int, ... images) : BitmapData
		{
			var array : Vector.<BitmapData> = new Vector.<BitmapData>();
			for each (var c : Class in images) {
				array.push(c);
			}
			return combineImage(anchor, array);
		}
		
		/**
		 * 整合所有BitmapData
		 */
		public static function combineImage(anchor:int, images:Vector.<BitmapData>) : BitmapData
		{
			var drect : Rectangle = new Rectangle(0,0,1,1);
			
			for each (var o : BitmapData in images) {
				drect.width = Math.max(drect.width, o.width);
				drect.height = Math.max(drect.height, o.height);
			}
			
			var ret : BitmapData = new BitmapData(drect.width, drect.height, true, 0);
			
			for each (var o : BitmapData in images) 
			{
				var p : Point = new Point();
				
				if ((anchor & Anchor.ANCHOR_HCENTER)!=0) {
					p.x = ret.width/2 - o.width/2;
				} else if ((anchor & Anchor.ANCHOR_RIGHT)!=0) {
					p.x = ret.width-o.width;
				} else {
					p.x = 0;
				}
				if ((anchor & Anchor.ANCHOR_VCENTER)!=0) {
					p.y = ret.height/2 - o.height/2;
				} else if ((anchor & Anchor.ANCHOR_BOTTOM)!=0) {
					p.y = ret.height-o.height;
				} else {
					p.y = 0;
				}
				
				ret.copyPixels(o, 
					new Rectangle(0,0,o.width,o.height), 
					p, 
					null,
					null, 
					true);
			}
			
			return ret;
		}
		
		public static function subImage(src:BitmapData, x:int, y:int, w:int, h:int) : BitmapData
		{
			var ret : BitmapData = new BitmapData(w, h, src.transparent);
			ret.copyPixels(src, new Rectangle(x, y, w, h), new Point(0, 0), null, null, false);
			return ret;
		}
		
		public static function colorTransform(src:BitmapData, trans:ColorTransform) : BitmapData
		{
			var ret : BitmapData = subImage(src, 0, 0, src.width, src.height);
			var rect:Rectangle = new Rectangle(0, 0, src.width, src.height);
			ret.colorTransform(rect, trans);
			return ret;
		}
		
		
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
			
			var depthsSwapped:Boolean;
			
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
		

	}
}
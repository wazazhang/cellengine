package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.MapSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IImages;
	import com.cell.util.Map;
	import com.cell.util.NumberReference;
	
	import flash.utils.ByteArray;

	/**
	 * 如何将编辑器资源解析成单位
	 * @author WAZA
	 */
	public interface OutputLoader
	{
		function load(complete:Function, error:Function) : void;
		
		function getPercent() : Number;
		
		/***
		 * 是否单独输出每张图
		 * @return
		 */
		function isTile() : Boolean ;
		
		/**
		 * 是否输出整图
		 * @return
		 */
		function isGroup() : Boolean ;
		
		/**
		 * 获得导出图片文件类型
		 * @return
		 */
		function 		getImageExtentions() : String ;
		
		function		getImgTable() 	: Map;
		function		getSprTable() 	: Map;
		function		getMapTable() 	: Map;
		function		getWorldTable() : Map;
		
		
		function 		getCImages(set:String) : IImages;
		function 		getCSprite(set:String) : CSprite;
		function 		getCMap(map:String) : CMap;
		
		/**
		 * call by {@link SetResource}.dispose()
		 */
		function 		dispose() : void;
	
	}

}

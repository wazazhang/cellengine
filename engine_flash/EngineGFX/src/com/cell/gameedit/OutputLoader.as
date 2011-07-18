package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gfx.game.CImages;
	import com.cell.gfx.game.CSprite;
	import com.cell.util.Map;
	import com.cell.util.NumberReference;
	
	import flash.utils.ByteArray;

	/**
	 * 如何将编辑器资源解析成单位
	 * @author WAZA
	 */
	public interface OutputLoader
	{
		function load(complete:Function) : void;
		
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
		
		
		function 		createCImages(set:ImagesSet) : CImages;
		
		function 		createCSprite(set:SpriteSet, images:CImages) : CSprite;
		
		
		/**
		 * call by {@link SetResource}.dispose()
		 */
		function 	dispose() : void;
	
		
	}

}

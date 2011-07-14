package com.cell.gameedit
{
	import com.cell.util.Map;
	import com.cell.util.NumberReference;
	
	import flash.utils.ByteArray;

	/**
	 * 如何将编辑器资源解析成单位
	 * @author WAZA
	 */
	public interface Output
	{
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
		function getImageExtentions() : String ;
		
		/**
		 * 读取导出资源，比如图片什么的
		 * @param name 文件名
		 * @param percent 进度
		 * @return
		 */
		function loadRes( name:String,  percent:NumberReference) : ByteArray;
		
		
		function		getImgTable() 	: Map;
		function		getSprTable() 	: Map;
		function		getMapTable() 	: Map;
		function		getWorldTable() : Map;
		
		/**
		 * call by {@link SetResource}.dispose()
		 */
		function 	dispose() : void;
	
		
	}

}

package com.cell.ui.edit
{
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.Map;
	
	import flash.display.Bitmap;

	public class UIEdit
	{

		private var res_root 	: String;
		private var img_map 	: Map;
		
		
		public function UIEdit(res_root:String)
		{
			this.res_root = res_root;
		
		}
		
		public function createFromFile(fileName:String) : UIComponent
		{
			return null;
		}
		
		public function getImage(name:String) : Bitmap
		{
			return img_map[name];
		}
		
		
		public function getUILayerStyle(name:String) : int
		{
			switch (name) 
			{
			case "NULL":
				return UIRect.IMAGE_STYLE_NULL;
				
			case "COLOR":
				return UIRect.IMAGE_STYLE_COLOR;
				
			case "IMAGE_STYLE_ALL_9":
				return UIRect.IMAGE_STYLE_ALL_9;
				
			case "IMAGE_STYLE_ALL_8":
				return UIRect.IMAGE_STYLE_ALL_8;
				
			case "IMAGE_STYLE_H_012":
				return UIRect.IMAGE_STYLE_H_012;
				
			case "IMAGE_STYLE_V_036":
				return UIRect.IMAGE_STYLE_V_036;
				
			case "IMAGE_STYLE_HLM":
				return UIRect.IMAGE_STYLE_HLM;
				
			case "IMAGE_STYLE_VTM":
				return UIRect.IMAGE_STYLE_VTM;
				
			case "IMAGE_STYLE_BACK_4":
				return UIRect.IMAGE_STYLE_BACK_4;
				
			case "IMAGE_STYLE_BACK_4_CENTER":
				return UIRect.IMAGE_STYLE_BACK_4_CENTER;
			}
			
			return UIRect.IMAGE_STYLE_NULL;
		}
		
		
		public function getLayout(xml:XML) : UIRect
		{
			
			return null;
		}
		
		///////////////////////////////////////////////////////////////////////
		
		
		protected function createComponent(e:XML) : UIComponent
		{
			
			return null;
		}
		
		protected function readInternal(e:XML) : UIComponent
		{
			
			return null;
		}
	}
}
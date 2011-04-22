package com.g2d.display.ui;

import com.cell.CObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.text.MultiTextLayout.AttributedSegment;

/**
 * 用于监听特殊属性的字符段被点击的接口
 * @author WAZA
 */
public interface TextClickSegmentListener extends EventListener
{
	public void segmentClicked(MouseEvent event, UIComponent textbox, AttributedSegment segment);
	
	public static class URLClickSegmentListener implements TextClickSegmentListener
	{
		@Override
		public void segmentClicked(MouseEvent event, UIComponent textbox, AttributedSegment segment) {
			if (event.type == MouseEvent.EVENT_MOUSE_DOWN) {
				String url = segment.attribute_value + "";
				if (url.startsWith("http://")) {
					CObject.getAppBridge().openBrowser(url);
				}
			}
		}
	}
	
}

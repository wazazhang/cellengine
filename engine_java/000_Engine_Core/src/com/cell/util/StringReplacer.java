package com.cell.util;

public class StringReplacer
{
	public static class StringRegion
	{
		/**源字符串*/
		final public String src;
		/**起始字符*/
		final public String cbegin;
		/**起始字符*/
		final public String cend;
		
		/**起始位置*/
		private int ibegin		= -1;
		/**结束位置*/
		private int iend		= -1;
		/**起始和结束内的字符*/
		private String content	= null;
				
		public StringRegion(String src, String cbegin, String cend) 
		{
			this.src = src;
			this.cbegin = cbegin;
			this.cend = cend;
			
			this.ibegin = src.indexOf(cbegin);
			this.iend = src.lastIndexOf(cend);
			if (ibegin>=0 && iend >= 0) {
				content = src.substring(ibegin + cbegin.length(), iend);
				iend += cend.length();
			}
		}

		public boolean finded()
		{
			return ibegin>=0 && iend>=0 && ibegin <= iend;
		}
		
		/**最开始, [c]begin*/
		public int getBeginIndex() {
			return ibegin;
		}
		
		/**最结束, cend[]*/
		public int getEndIndex() {
			return iend;
		}
		
		/**除去最开始前和最开始字符，和最结束后的内容*/
		public String getContent() {
			return content;
		}
	}
	
	
	public static StringRegion getRegion(String src, String begin, String end)
	{
		return new StringRegion(src, begin, end);
	}
	
	
	public static String replaceRegion(String src, int begin ,int end, String replace) 
	{
		String ret = src.substring(0, begin);
		
		ret += replace;
		
		ret += src.substring(end);
		
		return ret;
	}
	
	
	
	

}

package com.cell.location;

public interface IStringCovert
{
	public String covert(String src);
	
	public static class DefaultCovert implements IStringCovert
	{
		public String covert(String src){
			return src;
		}
	}
}


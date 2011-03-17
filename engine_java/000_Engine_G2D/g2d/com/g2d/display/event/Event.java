package com.g2d.display.event;


public class Event<T>// implements Serializable
{
	//private static final long serialVersionUID = Version.Version;
	
	public T source;
	
	
//	-----------------------------------------------------------------------------------------------
	
	public Object 	user_data;
	public int		user_tag;
	
	
	@SuppressWarnings("unchecked")
	public<D> D getUserData() {
		return (D)user_data;
	}
	
	public<D> void setUserData(D data) {
		user_data = data;
	}
	
}

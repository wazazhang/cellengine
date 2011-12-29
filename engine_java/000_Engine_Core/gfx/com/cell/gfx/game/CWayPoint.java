
package com.cell.gfx.game;

import java.io.Serializable;
import java.util.Vector;

import com.cell.CObject;
import com.cell.gfx.IGraphics;

public class CWayPoint extends CObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public int X = 0;
	public int Y = 0;
	
	protected Vector<CWayPoint> Link = new Vector<CWayPoint>();
	
	public Object Data;
	
	public String SetData;
	
	public CWayPoint(int x,int y){
		X = x;
		Y = y;
	}
	
	public int getNextCount(){
		return Link.size();
	}
	
	public CWayPoint getNextPoint(int linkIndex){
		try{
			return (CWayPoint)Link.elementAt(linkIndex);
		}catch(Exception err){
			err.printStackTrace();
		}
		return null;
	}
	
	public void linkTo(CWayPoint point){
		if(point==null)return ;
		
		if(!this.Link.contains(point)){
			this.Link.addElement(point);
		}

	}

	public void linkFrom(CWayPoint point){
		if(point==null)return ;

		if(!point.Link.contains(this)){
			point.Link.addElement(this);
		}

	}

	public void link(CWayPoint point){
		if(point==null)return;
		
		if(!this.Link.contains(point)){
			this.Link.addElement(point);
		}

		
	}
	
	public void unlink(CWayPoint point){
		if(point==null)return ;
		
		if( this.Link.contains(point)){
			this.Link.removeElement(point);
		}
		if( point.Link.contains(this)){
			point.Link.removeElement(this);
		}
		
	}
	
	public void unlinkAll(){
		
		if(Link.size()==0)return ;

        for (int i=0;i<Link.size();i++){
        	try{
        		CWayPoint p = (CWayPoint)Link.elementAt(i);
        		p.unlink(this);
        	}catch(Exception err){}
        }

        this.Link.removeAllElements();
        
	}

	public void render(IGraphics g,int offsetX,int offsetY){
//#ifdef _DEBUG
		g.setColor(0xff808080);
		
		for (int i=0;i<Link.size();i++){
			try{
				CWayPoint p = (CWayPoint)Link.elementAt(i);
				g.drawLine(X+offsetX, Y+offsetY, p.X+offsetX, p.Y+offsetY);
			}catch(Exception err){}
		}

		g.fillRect(X-2+offsetX, Y-2+offsetY, 4, 4);
//#endif
	}

}

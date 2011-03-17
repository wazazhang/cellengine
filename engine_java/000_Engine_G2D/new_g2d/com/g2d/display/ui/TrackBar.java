package com.g2d.display.ui;

import com.g2d.Graphics2D;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;

public class TrackBar extends UIComponent 
{
	public Button 	btn_add;
	public Button 	btn_dec;
	public Button 	btn_strip;
	
	double 			min = -10;
	double 			max = 10;
	double 			value = 0;
	
	public TrackBar() 
	{
		btn_add 	= new Button("+"){
			protected void onMouseClick(MouseEvent event) {
				setValue(value+1);
			}
		};
		btn_dec 	= new Button("-"){
			protected void onMouseClick(MouseEvent event) {
				setValue(value-1);
			}
		};
		btn_strip 	= new Button(" "){
			public void added(DisplayObjectContainer parent) {
				super.added(parent);
				enable_drag = true;
			}
			protected void onMouseDraged(MouseMoveEvent event) {
				setStripPos((int)x);
			}
		};
		super.addChild(btn_add);
		super.addChild(btn_dec);
		super.addChild(btn_strip);
	}
	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	
	private void setStripPos(int pos){
		double sx = pos - btn_add.getWidth();
		double si = getWidth() - (btn_add.getWidth() + btn_dec.getWidth()) - btn_strip.getWidth();
		double sw = max - min;
		setValue(min + sw * sx / si);
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setRange(double max, double min) {
		this.max = Math.max(max, min);
		this.min = Math.min(max, min);
		this.value = Math.max(value, min);
		this.value = Math.min(value, max);
	}
	
	public void setValue(double value) {
		value = Math.max(value, min);
		value = Math.min(value, max);
		this.value = value;
	}
	
	public int getPercent() {
		double sw = max - min;
		double sx = value - min;
		return (int)(sx / sw * 100);
	}
	
	protected void onMouseDown(MouseEvent event) {
		if (getMouseX() < btn_strip.x) {
			setStripPos((int)btn_strip.x - btn_strip.getWidth());
		}else{
			setStripPos((int)btn_strip.x + btn_strip.getWidth());
		}
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		
		btn_add.setSize(btn_add.getWidth(), getHeight());
		btn_dec.setSize(btn_dec.getWidth(), getHeight());
		btn_strip.setSize(btn_strip.getWidth(), getHeight());
		
		double sw = max - min;
		double sv = value - min;
		
		int sx = btn_dec.getWidth();
		int si = getWidth() - (btn_add.getWidth() + btn_dec.getWidth()) - btn_strip.getWidth();
		si = (int)(si * sv / sw);
		
		btn_add.setLocation(
				getWidth()-btn_add.getWidth(), 
				0);
		btn_dec.setLocation(
				0, 
				0);
		btn_strip.setLocation(
				sx+si, 
				0);
		
	}
}

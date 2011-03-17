package com.g2d.display.ui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import com.cell.sound.Sound;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.EventListener;
import com.g2d.display.ui.event.ActionEvent;
import com.g2d.display.ui.event.ActionListener;

public class MessageBox extends Form implements ActionListener
{
	public static interface MessageBoxListener extends EventListener
	{
		public void onOK(MessageBox box);
		public void onCancel(MessageBox box);
	}
	
	
//	-------------------------------------------------------------------------------------------------------------------
	
	
	public static MessageBox showMessage(String msg, int type, DisplayObjectContainer screen){
		MessageBox box = new MessageBox(type);
		box.message.setText(msg, true);
		box.open(screen);
		return box;
	}
	
	public static MessageBox showMessage(String msg, DisplayObjectContainer screen) {
		return showMessage(msg, TYPE_MESSAGE, screen);
	}
	
	public static MessageBox showError(String msg, DisplayObjectContainer screen) {
		return showMessage(msg, TYPE_ERROR, screen);
	}
	
	public static MessageBox showWarning(String msg, DisplayObjectContainer screen) {
		return showMessage(msg, TYPE_WARNING, screen);
	}
	
//	-------------------------------------------------------------------------------------------------------------------
	
	final public static int TYPE_MESSAGE 	= 0;
	final public static int TYPE_WARNING 	= 1;
	final public static int TYPE_ERROR 		= 2;
	final public static int TYPE_DIALOG 	= 3;
	
//	-------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------

	final public int 		type;
	
	final public TextBox	message		= new TextBox();
	final public ImageBox 	icon		= new ImageBox();
	final public Button 	btn_ok		= new Button("确定");
	final public Button		btn_cancel	= new Button("取消");
	
	public Sound	click_out_bounds_sound;
	private		int	click_out_bounds_timer		= 0;
	protected	int	click_out_bounds_timer_max	= 20;
	
	transient Vector<MessageBoxListener> message_box_listeners = new Vector<MessageBoxListener>();
	
	public MessageBox(int type) 
	{
		this.type = type;
		
		this.setSize(400, 200);
		this.setCloseEnable(false);
		this.transition.duration = 5;
		this.always_in_screen = true;
		
		icon.setLocation(20, 20);
		icon.setSize(50, 50);
		addComponent(icon);
		
		message.setLocation(20+54, 20);
		message.setSize(getWidth()-20-20-50-4, getHeight()-20-20-32-4);
		message.is_readonly = true;
		message.is_show_link = true;
		addComponent(message);
		
		// button
		btn_ok.setLocation(20, getHeight()-20-32);
		btn_ok.setSize(100, 32);
		btn_ok.addEventListener(this);
		addComponent(btn_ok);
		
		btn_cancel.setLocation(20 + 120, getHeight()-20-32);
		btn_cancel.setSize(100, 32);
		btn_cancel.addEventListener(this);
		if (type == TYPE_DIALOG){
			addComponent(btn_cancel);
		}
		
	}
	
	
	@Override
	public void open(DisplayObjectContainer screen) {
		this.setCenter(screen);
		screen.addAlwaysTopFocus(this);
		super.open(screen);
	}
	
	@Override
	public void removed(DisplayObjectContainer parent) {
		super.removed(parent);
		parent.removeAlwaysTopFocus(this);
	}
	
	@Override
	public void update() {
		super.update();
		if (getRoot().isKeyDown(KeyEvent.VK_ENTER)) {
			itemAction(btn_ok, new ActionEvent(btn_ok, btn_ok.getAction())) ;
		}
		if (getRoot().isMouseDown(MouseEvent.BUTTON1)) {
			if (!isCatchedMouse()){
				click_out_bounds_timer = click_out_bounds_timer_max;
				if (click_out_bounds_sound!=null) {
					click_out_bounds_sound.play();
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (click_out_bounds_timer-->0) {
			double range = (double)click_out_bounds_timer / click_out_bounds_timer_max * 2;
			double dx = Math.sin(click_out_bounds_timer) * range;
			g.translate(dx, 0);
		}
		super.render(g);
	}
	
	public void itemAction(UIComponent item, ActionEvent event)
	{
		if (item == btn_ok) 
		{
			onOK();
			for (MessageBoxListener l : message_box_listeners) {
				l.onOK(this);
			}
			this.close();
		}
		else if (item == btn_cancel) 
		{
			onCancel();
			for (MessageBoxListener l : message_box_listeners) {
				l.onCancel(this);
			}
			this.close();
		}
	}
	
	@Override
	public void addEventListener(EventListener listener) {
		super.addEventListener(listener);
		if (listener instanceof MessageBoxListener) {
			message_box_listeners.add((MessageBoxListener)listener);
		}
	}
	
	@Override
	public void removeEventListener(EventListener listener) {
		super.removeEventListener(listener);
		if (listener instanceof MessageBoxListener) {
			message_box_listeners.remove((MessageBoxListener)listener);
		}
	}
	
	
	public void onOK() {}
	
	public void onCancel() {}
	
}

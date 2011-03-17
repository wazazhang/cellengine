package com.g2d.java2d.impl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.VolatileImage;

import com.g2d.AnimateCursor;
import com.g2d.display.Stage;
import com.g2d.java2d.CanvasAdapter;

public class AwtCanvas extends CanvasAdapter
{	
	private com.g2d.Font			defaultFont;
	private AnimateCursor			defaultCursor;
	private AnimateCursor			nextCursor;
	
	private VolatileImage			vm_buffer;
	
//	--------------------------------------------------------------------------------------------------------------------------
//	construction
	
	public AwtCanvas(Component container, int stage_width, int stage_height)
	{
		super(container, stage_width, stage_height);
		if (container.getFont() != null) {
			setDefaultFont(new AwtFont(container.getFont()));
		} else {
			Graphics g = AwtEngine.getEngine().getGC().createCompatibleImage(1, 1).getGraphics();
			setDefaultFont(new AwtFont(g.getFont()));
			g.dispose();
		}
	}
	
	public Image getVMBuffer() {
		return vm_buffer;
	}
	
	public void setDefaultCursor(AnimateCursor cursor) {
		defaultCursor = cursor;
	}
	
	public void setDefaultFont(com.g2d.Font font) {
		defaultFont = font;
	}

	public com.g2d.Font getDefaultFont() {
		return defaultFont;
	}
	
//	--------------------------------------------------------------------------------
//	substage manag

	/**
	 * 设置场景象素大小
	 * @param width
	 * @param height
	 */
	public void setStageSize(int width, int height)
	{
		synchronized (this) {
			destory_vm_buffer();
			super.setStageSize(width, height);
		}
	}
	
	/**
	 * 根据parentFrame的大小来设置stage，使得stage刚好包容在parentFrame里
	 * @param parentFrame
	 */
	public void fillStageSize(Container parentFrame)
	{
		synchronized (this) {
			if (parentFrame != null) {
				setStageSize(
				parentFrame.getWidth() - (parentFrame.getInsets().left+parentFrame.getInsets().right), 
				parentFrame.getHeight()- (parentFrame.getInsets().left+parentFrame.getInsets().right));
				getComponent().setSize(getStageWidth(), getStageHeight());
			}
		}
	}
	
//	--------------------------------------------------------------------------------
//	game
	
	protected void destory_vm_buffer() {
		if (vm_buffer != null) {
			vm_buffer.flush();
			vm_buffer = null;
			System.out.println("CanvasAdapter : destory_vm_buffer");
		}
	}
	
	protected VolatileImage create_vm_buffer(GraphicsConfiguration gc) {
		VolatileImage vm_buffer = gc.createCompatibleVolatileImage(getStageWidth(), getStageHeight(), Transparency.OPAQUE);
		System.out.println("CanvasAdapter : create_vm_buffer");
		return vm_buffer;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		destory_vm_buffer();
		System.out.println(getClass().getName() + " : finalize");
	}
	
	@Override
	protected void updateStage(java.awt.Graphics2D g, Stage currentStage)
	{
		GraphicsConfiguration gc = AwtEngine.getEngine().getGC();
		
		if (vm_buffer == null) {
			vm_buffer = create_vm_buffer(gc);
		} else if (vm_buffer.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
			destory_vm_buffer();
			vm_buffer = create_vm_buffer(gc);
		}
		
		nextCursor = null;
		
		synchronized (this)
		{
			Graphics2D g2d = vm_buffer.createGraphics();
			try
			{
				if (currentStage!=null)
				{
					AwtGraphics2D awt_g = new AwtGraphics2D(g2d);
					awt_g.setFont(defaultFont);
					
					currentStage.onUpdate(this, getStageWidth(), getStageHeight());
					currentStage.onRender(awt_g);

					nextCursor = currentStage.getCursor();
					
					if (!isFocusOwner()) {
						currentStage.renderLostFocus(awt_g);
					}
				}

			} finally {
				g2d.dispose();
			}
		}
		
		if (nextCursor instanceof AwtAnimateCursor) {
			getComponent().setCursor(((AwtAnimateCursor)nextCursor).update());
		} else if (defaultCursor instanceof AwtAnimateCursor) {
			getComponent().setCursor(((AwtAnimateCursor)defaultCursor).update());
		} else {
			getComponent().setCursor(Cursor.getDefaultCursor());
		}
	}
	
	
}

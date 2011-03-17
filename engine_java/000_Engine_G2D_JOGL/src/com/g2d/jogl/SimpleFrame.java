package com.g2d.jogl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.cell.j2se.CAppBridge;
import com.g2d.display.Stage;
import com.g2d.jogl.impl.GLEngine;

public class SimpleFrame extends JFrame implements WindowListener
{
    private SimpleCanvas canvas;

    public SimpleFrame(int cw, int ch)
    {
    	super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    	super.setLayout(null);
        
    	int frameWidth  = cw + (getInsets().left+getInsets().right);
		int frameHeight = ch + (getInsets().top+getInsets().bottom);
		this.setSize(frameWidth, frameHeight);
		this.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2
				);
		super.setSize(frameWidth, frameHeight);
	
        canvas = new SimpleCanvas(cw, ch);
        canvas.setMinimumSize(new Dimension(cw, ch));
        
        add(canvas);
        
		addWindowListener(this);
		
		super.setSize(frameWidth, frameHeight);
    }

    public void start(int fps, Class<? extends Stage> stage_cls) {
    	canvas.start(fps);
    	canvas.getCanvasAdapter().changeStage(stage_cls);
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
		new Thread(new Runnable() {
			public void run() {
				canvas.stop();
				System.exit(0);
			}
		}).start();
	}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    
    
    
    static public void main(String[] args)
    {
    	EventQueue.invokeLater(new Runnable() {
            public void run() {
            	CAppBridge.init();
            	new GLEngine();
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch(Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.INFO, "can not enable system look and feel", ex);
                }
                JPopupMenu.setDefaultLightWeightPopupEnabled(false);
                SimpleFrame frame = new SimpleFrame(800, 600);
                frame.setVisible(true);
                frame.canvas.start(60);
            }
        });
    }
    

}
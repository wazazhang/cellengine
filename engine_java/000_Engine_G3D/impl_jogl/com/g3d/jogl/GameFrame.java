package com.g3d.jogl;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

public class GameFrame extends JFrame implements WindowListener
{

    private GameCanvas canvas;

    public GameFrame(int cw, int ch)
    {
        setTitle("3D Shapes and Rotation tutorial by LeoLoL.com");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        
    	setSize(cw, ch);
    	
    	int frameWidth  = cw + (getInsets().left+getInsets().right);
		int frameHeight = ch + (getInsets().top+getInsets().bottom);
		this.setSize(frameWidth, frameHeight);
		this.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2
				);

	
		GLCapabilities capabilities = new GLCapabilities();
        capabilities.setHardwareAccelerated(true);
//		try to enable 2x anti aliasing - should be supported on most hardware
//		capabilities.setNumSamples(2);
//		capabilities.setSampleBuffers(true);
	     
        canvas = new GameCanvas(capabilities);
        canvas.addGLEventListener(new GLEventListener() {
			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y, int width,int height) {}
			@Override
			public void init(GLAutoDrawable drawable) {}
			@Override
			public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
			@Override
			public void display(GLAutoDrawable drawable) {
				setTitle("FPS = " + canvas.getFPS());
			}
		});
        canvas.setMinimumSize(new Dimension(cw, ch));
        
        add(canvas);
        
		addWindowListener(this);
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
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch(Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.INFO, "can not enable system look and feel", ex);
                }
                JPopupMenu.setDefaultLightWeightPopupEnabled(false);
                GameFrame frame = new GameFrame(800, 600);
                frame.setVisible(true);
                frame.canvas.start(60);
                
            }
        });
    }
    

}

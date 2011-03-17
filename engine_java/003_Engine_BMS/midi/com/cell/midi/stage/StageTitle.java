package com.cell.midi.stage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.cell.CIO;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.util.Drawing;

public class StageTitle extends Stage
{
	Sequence	sequence;
	Sequencer	sequencer;
	
	public StageTitle()
	{
		System.out.println("list midi divice");
		for (MidiDevice.Info di : MidiSystem.getMidiDeviceInfo()) {
			System.out.println("\tmidi divice : " + di.getName() + " (" + di.getDescription() + ")");
		}
		try {
			sequence	= MidiSystem.getSequence(CIO.loadStream("/com/cell/midi/skycity.mid"));
			sequencer	= MidiSystem.getSequencer(false);
			System.out.println("use divice : " + sequencer.getDeviceInfo().getName());
			sequencer.open();
			sequencer.setSequence(sequence);			
			sequencer.start();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {
		
	}

	@Override
	public void removed(DisplayObjectContainer parent) {
		try {
			sequencer.stop();
		} catch (Exception err) {}
		try {
			sequencer.close();
		} catch (Exception err) {}
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public void render(Graphics2D g) 
	{
		g.setColor(Color.BLACK);
		g.fill(local_bounds);
		
		drawPercent(g);
	}

	@Override
	protected void renderAfter(Graphics2D g) {
		g.setColor(Color.WHITE);
		Drawing.drawString(g, "FPS="+getRoot().getFPS(), 1, 1);
	}
	
	protected void drawPercent(Graphics2D g) {
		Rectangle percent = new Rectangle(10, getHeight() - 10 - 4, getWidth()-20, 4);
		g.setColor(Color.GRAY);
		g.fill(percent);
		g.setColor(Color.WHITE);
		g.fillRect(percent.x, percent.y, 
				(int)(percent.width * sequencer.getMicrosecondPosition() / sequencer.getMicrosecondLength()), 
				percent.height);
	}

}

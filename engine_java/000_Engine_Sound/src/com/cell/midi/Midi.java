package com.cell.midi;

import java.io.InputStream;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;


public class Midi 
{
	final protected Sequence	sequence;
	
	final protected Sequencer	sequencer;
	
	public Midi(InputStream midi_stream) throws Exception
	{
		this.sequence	= MidiSystem.getSequence(midi_stream);
		this.sequencer	= getSequencer();
		sequencer.open();
		sequencer.setSequence(sequence);
	}
	
	protected Sequencer getSequencer() throws Exception {
		System.out.println("list midi divice");
		for (MidiDevice.Info di : MidiSystem.getMidiDeviceInfo()) {
			System.out.println("\tmidi divice : " + di.getName() + " (" + di.getDescription() + ")");
		}
		Sequencer sequencer	= MidiSystem.getSequencer(false);
		System.out.println("use divice : " + sequencer.getDeviceInfo().getName());
		return sequencer;

	}
	
	public void start() {
		sequencer.start();
	}
	
	public void close() {
		sequencer.stop();
		sequencer.close();
	}
	
	
//	public static void main(String[] args) throws Throwable
//	{
//		CAppBridge.init();
//		
//		new Midi(CIO.loadStream("/com/cell/midi/skycity.mid")).start();
//		
//		Thread.sleep(10000);
//	}
	
	
	
	
	
	
	
	

}

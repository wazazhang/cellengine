package com.cell.midi;

import java.util.ArrayList;

public class MidiFile
{
//	------------------------------------------------------------------------------------
	
	public static byte[]	CHUNK_TYPE_HEADER = {0x4D,0x54,0x68,0x64} ;
	
	public static int		MIDI_TYPE_0			= 0 ; // int 16
	public static int		MIDI_TYPE_1			= 1 ; // int 16
	public static int		MIDI_TYPE_2			= 2 ; // int 16

	public static int		SMPTE_FPS_24					= -24<<8; // int 16
	public static int		SMPTE_FPS_25					= -25<<8; // int 16
	public static int		SMPTE_FPS_30_DROP_FRAME			= -29<<8; // int 16
	public static int		SMPTE_FPS_30_NONE_DROP_FRAME	= -30<<8; // int 16

//	------------------------------------------------------------------------------------
	
	public static byte[] 	CHUNK_TYPE_TRACK = {0x4D,0x54,0x72,0x6B};

	public static String 	TextMeta	= "Meta";
	public static String 	TextSYSEX	= "SYSEX";

}

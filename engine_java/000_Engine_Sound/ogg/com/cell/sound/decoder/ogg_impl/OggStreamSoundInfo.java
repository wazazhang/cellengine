package com.cell.sound.decoder.ogg_impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.sound.SoundInfo;
import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

public class OggStreamSoundInfo extends SoundInfo implements Runnable
{
	final String		resource;
	final InputStream	input;
	final OggOutput		output = new OggOutput();

	DecodeState			decode_state;
	
	int					size;
	int					frame_rate;
	int					channels;
	int					bit_length;
	String				comment;
	
	private int			output_count = 0;
	private boolean		is_complete = false;
	private Integer		total_size;
	
	public OggStreamSoundInfo(String res, InputStream input) {
		this.resource	= res;
		this.input		= input;
		try {
			this.decode_state = new DecodeState();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	

	void decoded(byte[] data, int offset, int count) {
		synchronized (output) {
			output.write(data, offset, count);
		}
	}
	
	@Override
	public ByteBuffer getData() {
		synchronized (output) {
			int count = output.size() - output_count;
			if (count > 0) {
				ByteBuffer buffer = ByteBuffer.wrap(output.buf(), output_count, count);
				output_count += count;
				return buffer;
			}
		}
		return null;
	}

	@Override
	public boolean hasData() {
		if (!is_complete) {
			return true;
		}
		if (output.size() > output_count) {
			return true;
		}
		return false;
	}
	
	@Override
	public void resetData() {
		synchronized (output) {
			output_count = 0;
		}
	}
	
	@Override
	public int getBitLength() {
		return bit_length;
	}

	@Override
	public int getChannels() {
		return channels;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public int getFrameRate() {
		return frame_rate;
	}

	@Override
	public String getResource() {
		return resource;
	}
	
	public Integer getTotalSize() {
		return total_size;
	}
	
	
	

	@Override
	public void run()
	{
		try {
			this.decode_state.update();
		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
//			try {
//				CIO.readStream(input);
//			} catch (Exception err) {}
//			try {
//				input.close();
//			} catch (Exception err) {}
			this.total_size		= output.size();
			this.is_complete	= true;			
			this.decode_state	= null;
//			System.out.println("done ! size = " + CUtil.getBytesSizeString(output.size()));
		}
	}
	
	class OggOutput extends ByteArrayOutputStream
	{
		byte[] buf() {
			return buf;
		}
	}

	class DecodeState
	{
		int 		convsize	= 4096 * 2;
		byte[] 		convbuffer	= new byte[convsize];

		SyncState	oy 			= new SyncState();
		StreamState os 			= new StreamState();
		Page 		og 			= new Page();
		Packet 		op 			= new Packet();
		Info 		vi 			= new Info();
		Comment 	vc 			= new Comment();
		DspState 	vd 			= new DspState();
		Block 		vb 			= new Block(vd);

		byte[] buffer;
		int bytes = 0;
		int eos = 0;
		int index;
		int i = 0;
		
		public DecodeState() throws Throwable
		{
			oy.init();
			beginCodec();
		}
		
		private boolean beginCodec() throws Throwable
		{			
			eos = 0;
			index = oy.buffer(4096);
			buffer = oy.data;
			try {
				bytes = input.read(buffer, index, 4096);
			} catch (Exception e) {
				throw e;
			}
			oy.wrote(bytes);

			if (oy.pageout(og) != 1) {
				if (bytes < 4096) {
					// Decoded completed
					return true;
				}
				throw new IOException("Input does not appear to be an Ogg bitstream.");
			}

			os.init(og.serialno());
			vi.init();
			vc.init();
			if (os.pagein(og) < 0) {
				throw new IOException("Error reading first page of Ogg bitstream data.");
			}
			if (os.packetout(op) != 1) {
				throw new IOException("Error reading initial header packet.");
			}
			if (vi.synthesis_headerin(vc, op) < 0) {
				throw new IOException("This Ogg bitstream does not contain Vorbis audio data.");
			}

			i = 0;
			while (i < 2) {
				while (i < 2) {
					int result = oy.pageout(og);
					if (result == 0)
						break;
					if (result == 1) {
						os.pagein(og);
						while (i < 2) {
							result = os.packetout(op);
							if (result == 0)
								break;
							if (result == -1) {
								throw new IOException("Corrupt secondary header.  Exiting.");
							}
							vi.synthesis_headerin(vc, op);
							i++;
						}
					}
				}
				index = oy.buffer(4096);
				buffer = oy.data;
				try {
					bytes = input.read(buffer, index, 4096);
				} catch (Exception e) {
					throw e;
				}
				if (bytes == 0 && i < 2) {
					throw new IOException("End of file before finding all Vorbis headers!");
				}
				oy.wrote(bytes);
			}

			{
				byte[][] ptr = vc.user_comments;
				for (int j = 0; j < ptr.length; j++) {
					if (ptr[j] == null)
						break;
//					System.err.println(new String(ptr[j], 0, ptr[j].length - 1));
				}
				OggStreamSoundInfo.this.channels	= vi.channels;
				OggStreamSoundInfo.this.frame_rate	= vi.rate;
				OggStreamSoundInfo.this.bit_length	= 16;
				OggStreamSoundInfo.this.comment	= vc.toString();
				
//				System.err.println("\nBitstream is " + vi.channels+ " channel, " + vi.rate + "Hz");
//				System.err.println("Encoded by: "+ new String(vc.vendor, 0, vc.vendor.length - 1)+ "\n");
			}
			
			return false;
		}
		
		void update() throws Throwable
		{
			while (true) 
			{
				convsize = 4096 / vi.channels;
	
				vd.synthesis_init(vi);
				vb.init(vd);
	
				float[][][] _pcm = new float[1][][];
				int[] _index = new int[vi.channels];
				while (eos == 0) {
					while (eos == 0) {
						int result = oy.pageout(og);
						if (result == 0)
							break;
						if (result == -1) {
							System.err.println("Corrupt or missing data in bitstream; continuing...");
						} else {
							os.pagein(og);
							while (true) {
								result = os.packetout(op);
								if (result == 0)
									break;
								if (result == -1) {
								} else {
									
									int samples;
									if (vb.synthesis(op) == 0) {
										vd.synthesis_blockin(vb);
									}
	
									while ((samples = vd.synthesis_pcmout(_pcm, _index)) > 0) {
										float[][] pcm = _pcm[0];
										int bout = (samples < convsize ? samples : convsize);
	
										for (i = 0; i < vi.channels; i++) {
											int ptr = i * 2;
											int mono = _index[i];
											for (int j = 0; j < bout; j++) {
												int val = (int) (pcm[i][mono + j] * 32767.);
												if (val > 32767) {
													val = 32767;
												}
												if (val < -32768) {
													val = -32768;
												}
												if (val < 0)
													val = val | 0x8000;
												convbuffer[ptr] = (byte) (val);
												convbuffer[ptr + 1] = (byte) (val >>> 8);
												ptr += 2 * (vi.channels);
											}
										}
										
										decoded(convbuffer, 0, 2 * vi.channels * bout);
										
										vd.synthesis_read(bout);
									}
								}
							}
							if (og.eos() != 0)
								eos = 1;
						}
					}
					if (eos == 0) {
						index = oy.buffer(4096);
						buffer = oy.data;
						try {
							bytes = input.read(buffer, index, 4096);
						} catch (Exception e) {
							throw e;
						}
						oy.wrote(bytes);
						if (bytes == 0)
							eos = 1;
					}
				}
				os.clear();
				vb.clear();
				vd.clear();
				vi.clear();
				

				
				if (beginCodec()) {
					break;
				}
	
			}
	
			oy.clear();
			
		
		}
	}
	
}

package com.g2d.jogl.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cell.CIO;
import com.cell.gfx.IPalette;


public class GLPalette implements IPalette 
{
	private byte[] data_;
	
	private short color_count_;
	
	private short transparent_color_index_;
	
	
	GLPalette(InputStream is) throws IOException
	{
		loadACT(CIO.readStream(is));
	}
	
	GLPalette(byte[] data, short color_count, short transparent_color_index)
	{
		this.data_ = data;
		this.color_count_ = color_count;
		this.transparent_color_index_ = transparent_color_index;
	}
	
	@Override
	public GLPalette clone() {
		return this;
	}
	
	private void loadACT(byte[] data) throws IOException
	{		
		this.data_ = new byte[256*3];
		System.arraycopy(data, 0, data_, 0, data_.length);
		
		if (data.length > 256*3)
		{
			int byte1 = data[this.data_.length+0];
			int byte2 = data[this.data_.length+1];
			color_count_ = (short)(((int)byte1 << 8) | (int)byte2);
			int byte3 = data[this.data_.length+2];
			int byte4 = data[this.data_.length+3];	
			transparent_color_index_ = (short)(((int)byte3 << 8) | (int)byte4);
		}
		else
		{
			color_count_ = 256;
			transparent_color_index_ = -1;
		}
	}

	
	@Override
	public byte[] getIndexColors() 
	{
		return this.data_;
	}

	@Override
	public int getIndexColorCount() 
	{
		return this.color_count_;
	}

	@Override
	public byte[] getTransparentColor() 
	{
		if ( (0 <= this.transparent_color_index_) && (this.transparent_color_index_ < 256) )
		{
			byte[] color = new byte[3];
			
			int offset = this.transparent_color_index_ * 3; 
			
			color[0] = this.data_[offset];
			color[1] = this.data_[offset+1];
			color[2] = this.data_[offset+2];
			
			return color;
		}
		
		return null;
	}

	@Override
	public int getTransparentColorIndex() 
	{
		return this.transparent_color_index_;
	}
	
	@Override
	public void dispose() 
	{
		this.data_ = null;
		this.color_count_ = 0;
		this.transparent_color_index_ = -1;
	}
};


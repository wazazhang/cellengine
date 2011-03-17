package com.net.minaimpl;

import java.io.Serializable;

import org.apache.mina.filter.codec.ProtocolCodecFactory;

import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;

public abstract class MessageHeaderCodec implements ProtocolCodecFactory
{
	public static int			PACKAGE_DEFAULT_SIZE	= 2048;
	
//	-----------------------------------------------------------------------------------------
	
	final public static byte[]	zerodata				= new byte[0];

	/** 心跳请求头*/
	final public static byte[] 	heart_beat_req 			= {-1, 0, 0, 6};   
	
	/** 心跳回馈头*/
    final public static byte[] 	heart_beat_rep 			= {-2, 0, 0, 6};
	
    /** 消息头*/
	final public static byte[]	protocol_start 			= { 2, 0, 0, 6, };
	
	 /** 消息头固定尺寸*/
	final static int			protocol_fixed_size 	= 4 + 4;
	
	final public static void setProtocolStart(
			byte[] header, 
			byte[] hb_req, 
			byte[] hb_rep) 
	{
		System.arraycopy(header, 0, protocol_start, 0, protocol_start.length);
		System.arraycopy(hb_req, 0, heart_beat_req, 0, heart_beat_req.length);
		System.arraycopy(hb_rep, 0, heart_beat_rep, 0, heart_beat_rep.length);
	}

//	-----------------------------------------------------------------------------------------

	final protected ClassLoader				class_loader;
    final protected ExternalizableFactory	ext_factory;
    
//	-----------------------------------------------------------------------------------------

    public MessageHeaderCodec(ClassLoader cl, ExternalizableFactory ext_factory) 
    {
    	this.class_loader	= cl;
    	this.ext_factory	= ext_factory;
    }

	abstract public long getTotalSentBytes() ;

	abstract public long getTotalReceivedBytes() ;

	abstract public long getSentMessageCount();

	abstract public long getReceivedMessageCount();
}

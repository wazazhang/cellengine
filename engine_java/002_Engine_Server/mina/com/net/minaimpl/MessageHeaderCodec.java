package com.net.minaimpl;

import java.io.Serializable;

import org.apache.mina.filter.codec.ProtocolCodecFactory;

import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;

public abstract class MessageHeaderCodec implements ProtocolCodecFactory
{
	public static int			PACKAGE_DEFAULT_SIZE	= 2048;

	final public static byte[]	zerodata = new byte[0];
	
//	-----------------------------------------------------------------------------------------
	

    /** 消息头*/
	final protected int						protocol_start;
	/** 心跳请求头*/
	final protected int						heart_beat_req;   
	/** 心跳回馈头*/
	final protected int						heart_beat_rep;
	 /** 消息头固定尺寸*/
	final protected int 					protocol_fixed_size	= 4 + 4;
	
	final protected ClassLoader				class_loader;
    final protected ExternalizableFactory	ext_factory;
    
//	-----------------------------------------------------------------------------------------
  
    public MessageHeaderCodec(ClassLoader cl, ExternalizableFactory ext_factory) 
    {
		this(cl, ext_factory, 0x02000006, 0x02000001, 0x02000002);
	}
    
    public MessageHeaderCodec(ClassLoader cl, ExternalizableFactory ext_factory,
    		int header, int hb_req, int hb_rep) 
    {
    	this.class_loader	= cl;
    	this.ext_factory	= ext_factory;
    	this.protocol_start = header;
    	this.heart_beat_req = hb_req;
    	this.heart_beat_rep = hb_rep;
    }

	abstract public long getTotalSentBytes() ;

	abstract public long getTotalReceivedBytes() ;

	abstract public long getSentMessageCount();

	abstract public long getReceivedMessageCount();
}

package com.net.minaimpl.server.proxy.b2c;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.SynchronizedProtocolDecoder;
import org.apache.mina.filter.codec.SynchronizedProtocolEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.io.NIOSerialize;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataOutput;
import com.net.minaimpl.MessageHeaderCodec;
import com.net.minaimpl.SessionAttributeKey;


public class ProxyEncoderB2C extends MessageHeaderCodec
{
	private static final Logger _log = LoggerFactory.getLogger(ProxyEncoderB2C.class.getName());

	

	class NetPackageDecoder extends CumulativeProtocolDecoder 
	{
		@Override
		public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
		{
			int begin = in.position();
			super.decode(session, in, out);
			TotalReceivedBytes += (in.position() - begin);
		}
		
//		如果你能够解析一次，那就需要返回 true; 
//		如果1个不够解析，返回false，继续囤积数据，并等待下一次解析
	    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception 
	    {
    		//得到上次的状态
	    	Integer protocol_size = (Integer)session.getAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
	    	
	    	try
        	{
	    		// 如果上次无状态，则代表是首次解包
	    		if (protocol_size == null)
        		{
	    			// 有足够的数据
	    			if (in.remaining() >= protocol_fixed_size)
	    			{
						// 判断是否是有效的数据包头
						for (int i = 0; i < protocol_start.length; ++i) {
							byte data = in.get();
							if (data != protocol_start[i]) {
								// 丢弃掉非法字节//返回true代表这次解包已完成,清空状态并准备下一次解包
								_log.warn("bad head, drop data : " + data);
								return true;
							}
						}
			            
			            // 生成新的状态
						protocol_size = new Integer(in.getInt());
						session.setAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL, protocol_size);
	    			}
	    			else
	    			{
	    				// 没有足够的数据时,返回 false
	    				// 返回false代表这次解包未完成,需要等待
	    				return false;
	    			}
        		}
	    		
	    		// 继续解析上一个未完成的包内容
	    		if (protocol_size != null)
	    		{
	    			int message_size = protocol_size - protocol_fixed_size;
	    			
	    			// 如果有足够的数据
	    			if (in.remaining() >= message_size) 
	    			{
	    				ProxyMessage message		= new ProxyMessage();
	    				message.ChannelID 			= in.getInt();		// 4
	    				message.ChannelSesseionID	= in.getLong();		// 8
	    				message.SessionID 			= in.getLong();		// 8
	    				message.Protocol 			= in.getShort();	// 2
	    				message.PacketNumber		= in.getInt();		// 4
	    				message.TransmissionType	= in.get();			// 1
	    				message.message_body		= new byte[message_size - header_fixed_size];
	    				in.get(message.message_body);
	    				
	    				// 清空当前的解包状态
	    				session.removeAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
	    				
	    				// 告诉 Protocol Handler 有消息被接收到
	    				out.write(message);
	    				
	    				ReceivedMessageCount ++;
	    				
	    				// 无论如何都返回true，因为当前解包已完成
	    				return true;
	    			} 
	    			// 如果没有足够的数据
	    			else
	    			{
	    				// 没有足够的数据时,返回 false
	    				// 返回false代表这次解包未完成,需要等待
	    				return false;
	    			}
	    		}
        	
	    		return false;
        	}
    		catch(Throwable err)
    		{
        		if (protocol_size != null) {
        			_log.warn("drop and clean decode state ! size = " + protocol_size);
        			session.removeAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
        		}
        		_log.error(err.getMessage(), err);
        		// 当解包时发生错误，则
        		// 返回true代表这次解包已完成,清空状态并准备下一次解包
        		return true;
        	}
	    	
    	}

	    
	}

	
//	-------------------------------------------------------------------------------------------------------------------
	
    class NetPackageEncoder extends ProtocolEncoderAdapter
    {
    	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception 
    	{
    		try
    		{
    			ProxyMessage header = (ProxyMessage)message;

    			IoBuffer buffer = IoBuffer.allocate(MessageHeader.PACKAGE_DEFAULT_SIZE);
    			buffer.setAutoExpand(true);
    			
    			int oldposition = buffer.position();
    			{
	    			// fixed data region
    				{
		    			buffer.put		(protocol_start);			// 4
		    			buffer.putInt	(protocol_fixed_size);		// 4
    				}
    				
	    			// message data region
    				int cur = buffer.position();
	    			{
						buffer.putInt	(header.ChannelID);			// 4
						buffer.putLong	(header.ChannelSesseionID);	// 8
						buffer.putLong	(header.SessionID);			// 8
						buffer.putShort	(header.Protocol);			// 2
						buffer.putInt	(header.PacketNumber);		// 4
						buffer.put		(header.message_body);
	    			}
	    			buffer.putInt(4, protocol_fixed_size + (buffer.position() - cur));
	    		}
    			TotalSentBytes += (buffer.position() - oldposition);
    			
    			buffer.shrink();
    			
    			buffer.flip();
    			out.write(buffer);
    		    
				SendedMessageCount ++;
        	}
    		catch(Throwable err) 
    		{
    			_log.error(err.getMessage(), err);
        	}
    	}
    }
	
//	-------------------------------------------------------------------------------------------------------------------

    final private ProtocolEncoder	encoder			= new NetPackageEncoder();
    final private ProtocolDecoder	decoder			= new NetPackageDecoder();
    
    public ProxyEncoderB2C(ClassLoader cl, ExternalizableFactory ext_factory) 
    {
    	super(cl, ext_factory);
    }

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
    
}



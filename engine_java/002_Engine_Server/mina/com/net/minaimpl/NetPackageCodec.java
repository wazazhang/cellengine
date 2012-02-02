package com.net.minaimpl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
import com.cell.net.io.CompressingMessage;
import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.ExternalizableMessage;
import com.cell.net.io.MessageHeader;
import com.cell.net.io.MutualMessage;
import com.cell.net.io.NetDataOutput;
import com.cell.util.zip.ZipUtil;
import com.net.Protocol;


public class NetPackageCodec extends MessageHeaderCodec
{
	private static final Logger _log = LoggerFactory.getLogger(NetPackageCodec.class.getName());

//	trace var
	private long TotalSentBytes = 0;
	private long TotalReceivedBytes = 0;
	private long SentMessageCount = 0;
	private long ReceivedMessageCount = 0;

	public long getTotalSentBytes() {
		return TotalSentBytes;
	}
	public long getTotalReceivedBytes() {
		return TotalReceivedBytes;
	}
	public long getSentMessageCount() {
		return SentMessageCount;
	}
	public long getReceivedMessageCount() {
		return ReceivedMessageCount;
	}
	
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
	    				int head = in.getInt();
						if (head != protocol_start) {
    						// 心跳包头，重置。
	    					if (head == heart_beat_req) {
	    						return true;
	    					}
	    					if (head == heart_beat_rep) {
	    						return true;
	    					}
	    					// 丢弃掉非法字节//返回true代表这次解包已完成,清空状态并准备下一次解包
							throw new IOException("bad head, drop data : " + 
									Long.toString((0x00ffffffffL & head) | 0xff00000000L, 16).substring(2));
	    				}
			            // 生成新的状态
						protocol_size = new Integer(in.getInt());
						if (protocol_size < 0 || protocol_size > PACKAGE_MAX_SIZE) {
							// 禁止创建过大的缓冲区
							throw new IOException("protocol size negative or overflow : " + 
									protocol_size);
						}
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
	    				// 清空当前的解包状态
	    				session.removeAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
	    				
	    				ProtocolImpl p = ProtocolPool.getInstance().createProtocol();
	    				p.DynamicReceiveTime		= System.currentTimeMillis();

    					p.Protocol 					= in.get();			// 1
    					p.PacketNumber				= in.getInt();		// 4
    					
    					int obj_size 				= message_size - 5;
    					
    					switch (p.Protocol) {
	    				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_MESSAGE:
	    					p.ChannelID 			= in.getInt();		// 4
	    					obj_size -= 4;
	    					break;
	    				case ProtocolImpl.PROTOCOL_SYSTEM_NOTIFY:
	    					p.SystemMessage 		= in.getInt();		// 4
	    					obj_size -= 4;
	    					break;
	    				}
    					
    					p.transmission_flag			= in.get();			// 1
						obj_size -= 1;
    					
    					if (p.transmission_flag != 0) 
    					{
        					IoBuffer obj_in = in;
        					// 确定是否要解压缩
        					if ((p.transmission_flag & ProtocolImpl.TRANSMISSION_TYPE_COMPRESSING) != 0) {
        						obj_in = decompress(in, obj_size);
        					}
        					// 解出包包含的二进制消息
    						if ((p.transmission_flag & ProtocolImpl.TRANSMISSION_TYPE_EXTERNALIZABLE) != 0) {
    							p.message = ext_factory.createMessage(obj_in.getInt());	// ext 4
    							ExternalizableMessage ext = (ExternalizableMessage)p.message;
    							NetDataInputImpl in_buff = new NetDataInputImpl(obj_in, ext_factory);
    							ext.readExternal(in_buff);
    						}
    						else if ((p.transmission_flag & ProtocolImpl.TRANSMISSION_TYPE_MUTUAL) != 0) {
    							p.message = ext_factory.createMessage(obj_in.getInt());	// ext 4
    							MutualMessage mutual = (MutualMessage)p.message;
    							NetDataInputImpl in_buff = new NetDataInputImpl(obj_in, ext_factory);
    							ext_factory.getMutualCodec().readMutual(mutual, in_buff);
     						}
    						else if ((p.transmission_flag & ProtocolImpl.TRANSMISSION_TYPE_SERIALIZABLE) != 0) {
     							p.message = (MessageHeader)obj_in.getObject(class_loader);
     						}
    					}
    					
	    				// 告诉 Protocol Handler 有消息被接收到
	    				out.write(p);
	    				
	    				//System.out.println("decoded <- " + session.getRemoteAddress() + " : " + protocol);

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
    		catch(Exception err)
    		{
        		if (protocol_size != null) {
        			_log.warn("drop and clean decode state ! size = " + protocol_size);
        			session.removeAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
        		}
//        		err.printStackTrace();
//        		_log.error(err.getMessage() + " : decode error : " + session , err);
        		
        		// 当解包时发生错误，则
        		// 返回true代表这次解包已完成,清空状态并准备下一次解包
        		throw err;
        	}
	    	
    	}

	    
	}

	
//	-------------------------------------------------------------------------------------------------------------------
//	在创建缓冲区时，可以要求创建直接缓冲区，创建直接缓冲区的成本要比创建间接缓冲区高，
//	但这可以使运行时环境直接在该缓冲区上进行较快的本机 I/O 操作。因为创建直接缓冲区所增加的成本，
//	所以直接缓冲区只用于长生存期的缓冲区，而不用于短生存期、一次性且用完就丢弃的缓冲区。
//	而且，只能在 ByteBuffer 这个级别上创建直接缓冲区，如果希望使用其它类型，则必须将 Buffer 转换成更具体的类型。
	
    class NetPackageEncoder extends ProtocolEncoderAdapter
    {
    	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception 
    	{
    		try
    		{
    			ProtocolImpl p = (ProtocolImpl)message;
				p.DynamicSendTime = System.currentTimeMillis();
    			
    			IoBuffer buffer = IoBuffer.allocate(PACKAGE_DEFAULT_SIZE, false).setAutoExpand(true);
    			
    			int oldposition = buffer.position();
    			{
	    			// fixed data region
    				{
		    			buffer.putInt		(protocol_start);		// 4
		    			buffer.putInt		(protocol_fixed_size);	// 4
    				}
    				
	    			// message data region
    				int cur = buffer.position();
	    			{
						buffer.put			(p.Protocol);			// 1
						buffer.putInt		(p.PacketNumber);		// 4
						
						switch (p.Protocol) {
	    				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_MESSAGE:
	    					buffer.putInt	(p.ChannelID);			// 4
							break;
	    				case ProtocolImpl.PROTOCOL_SYSTEM_NOTIFY:
	    					buffer.putInt	(p.SystemMessage);		// 4
	    					break;
						}
						
						if (p.message == null) 
						{
							buffer.put		((byte)0);				// 1
						}
						else
						{
							byte trans_flag = ProtocolImpl.TRANSMISSION_TYPE_UNKNOW;
							IoBuffer obj_buff = buffer;
							// 是否压缩
							if (p.message instanceof CompressingMessage) {
								obj_buff = IoBuffer.allocate(buffer.remaining(), false).setAutoExpand(true);
								trans_flag |= ProtocolImpl.TRANSMISSION_TYPE_COMPRESSING;
							}
							if (p.message instanceof ExternalizableMessage) {
								trans_flag |= ProtocolImpl.TRANSMISSION_TYPE_EXTERNALIZABLE;
							} 
							else if (p.message instanceof MutualMessage) {
								trans_flag |= ProtocolImpl.TRANSMISSION_TYPE_MUTUAL;
							}
							else if (p.message instanceof MessageHeader) {
								trans_flag |= ProtocolImpl.TRANSMISSION_TYPE_SERIALIZABLE;
							}
							
							buffer.put		(trans_flag);			// 1
							{
								if (p.message instanceof ExternalizableMessage) {
									obj_buff.putInt		(ext_factory.getMessageType(p.message));	// ext 4
									NetDataOutputImpl out_buff = new NetDataOutputImpl(obj_buff, ext_factory);
									((ExternalizableMessage)p.message).writeExternal(out_buff);
								} 
								else if (p.message instanceof MutualMessage) {
									obj_buff.putInt		(ext_factory.getMessageType(p.message));	// ext 4
									NetDataOutputImpl out_buff = new NetDataOutputImpl(obj_buff, ext_factory);
									ext_factory.getMutualCodec().writeMutual(((MutualMessage)p.message), out_buff);
								}
								else if (p.message instanceof MessageHeader) {
									obj_buff.putObject	(p.message);
								}
							}
							if (p.message instanceof CompressingMessage) {
								obj_buff.rewind();
								compress(obj_buff, buffer);
								obj_buff.free();
							}
						}
						
	    			}
	    			buffer.putInt(4, protocol_fixed_size + (buffer.position() - cur));
	    		}
    			TotalSentBytes += (buffer.position() - oldposition);

    			p.DynamicSendTime = System.currentTimeMillis();
    			
    			buffer.shrink();
    			
    			buffer.flip();
    			out.write(buffer);
//    			System.out.println(buffer.getHexDump());
    			//System.out.println("encoded -> " + session.getRemoteAddress() + " : " + protocol);
				SentMessageCount ++;
        	}
    		catch(Exception err) 
    		{
    			_log.error(err.getMessage() + "\nencode error : " + session + " :\n" + message + "");
        		//err.printStackTrace();
    			throw err;
        	}
    	}
    }
	
	private static void compress(IoBuffer in, IoBuffer out) {
		byte[] data = in.array();
//		long start_time = System.nanoTime();
//		int src_size = data.length;
//		int dst_size = 0;
		Deflater compresser = new Deflater();
		try {
			compresser.reset();
			compresser.setInput(data);
			compresser.finish();
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
//				dst_size += i;
				out.put(buf, 0, i);
			}
		} finally {
			compresser.end();
//			System.out.println("compress" +
//					" : ["+CUtil.getBytesSizeString(src_size)+"]->["+CUtil.getBytesSizeString(dst_size)+"]" +
//					" : use " + (System.nanoTime() - start_time)/1000000f + " ms");
		}
	}

	private static IoBuffer decompress(IoBuffer in, int limit) throws DataFormatException {
		byte[] data = new byte[limit];
		in.get(data);
//		long start_time = System.nanoTime();
//		int src_size = data.length;
//		int dst_size = 0;
		Inflater decompresser = new Inflater();
		IoBuffer out = IoBuffer.allocate(limit, false).setAutoExpand(true);
		try {
			decompresser.reset();
			decompresser.setInput(data);
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
//				dst_size += i;
				out.put(buf, 0, i);
			}
			out.rewind();
			return out;
		} finally {
			decompresser.end();
//			System.out.println("decompress" +
//					" : ["+CUtil.getBytesSizeString(src_size)+"]->["+CUtil.getBytesSizeString(dst_size)+"]" +
//					" : use " + (System.nanoTime() - start_time)/1000000f + " ms");
		}
	}
    
//	-------------------------------------------------------------------------------------------------------------------

    final private ProtocolEncoder	encoder			= new NetPackageEncoder();
    final private ProtocolDecoder	decoder			= new NetPackageDecoder();
    
    public NetPackageCodec(ClassLoader cl, ExternalizableFactory ext_factory) 
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



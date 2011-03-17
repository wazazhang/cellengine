package com.net.flash;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.SynchronizedProtocolDecoder;
import org.apache.mina.filter.codec.SynchronizedProtocolEncoder;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


/*

1、问题描述

      将flash发布为html格式后，加载页面后，swf无法与服务器进行socket通信。Flash端显示的错误为：
   securityErrorHandler信息: 
   [SecurityErrorEvent type="securityError" bubbles=false cancelable=false eventPhase=2 text="Error #2048"]

      在服务器端显示的信息是由客户端尝试进行连接，但是无法接受数据。接受的数据显示为空。

2.问题原因：

        最新的Flash player 9.0.124.0，当flash文件要进行socket通信的时候，需要向服务器端获取crossdomain.xml文件。如果找不到就出现客户端无法连接服
        务器的现象。

了解flash发起socket通信的三个过程

      当封装在页面的flash发起socket通信请求的时候会先寻找服务器端的843端口，获取Crossdomain.xml文件，当服务器没有开启843的时候，flashPlayer会检查
      发起请求的swf文件中中有没有使用Security.loadPolicyFile来加载策略文件Crossdomain.xml，如果还是没有就会看这个发起请求的swf要连接的目标端口有没
      有策略文件。如果都没有那么连接失败,返回如上的出错提示。

为什么老版本的Flash player没有这个问题？

      从一些官方的一些资料中了解了一下。以前的Flash Player无论你采用urlRequest的http请求方式或者xmlsocket socket方式，他们都共用一个安全策略文件。
      这个策略文件只要你放在主域的目录下就行了。而现在不行了，现在的策略文件如果你使用http请求方式那么需要把策略文件放在目录下面，如果你使用socket请求方式就必须通过socket端口来接收这个策略文件。

      对应调用的方式为：
      http请求——》Security.loadPolicyFile(“http://www.xxx.com/crossdomain.xml”)
      socket或xmlsocket请求——》Security.loadPolicyFile(“xmlsocket://www.xxx.com：port”)

怎么将Socke策略文件发给Flash Player

      Flash Player在你的socket.connect("domain",port)运行之前，会按照前面描述的三个过程向你的socket服务器的843端口（据说Adobe已经向相关管
      理机构申请保留843端口给Flash Player用）发送一个字符串 "<policy-file-request/>"，这个时候如果你有一个服务在监听843端口那么收到这个字符串之后，
      直接按照XML格式发回策略文件就解决了。（注意发回的时候记得加一个截止字符"\0"）

    当然你也可以不用843端口自己设置一个端口。因为Flash Player如果在843端口得不到信息，就会检查你是否在你的Flash文件里面自己添加了指定的获取通道，你
    可以定义一个自己的端口。不过这个时候你不能用http方式，而要用xmlsocket方式。（相当于自动帮你新建了一个xmlsocket对象，然后链接你指定的主机和端口）。
    比如你想用1234端口可以在你的Flash里面加这一句Security.loadPolicyFile(“xmlsocket://www.xxx.com:1234”)，需要注意的是这一句要加在你
    的socket.connect前面。

    还有最后一个办法，就是在你的socket连接端口监听这个请求。比如你用的是socket.connect("192.168.1.100",8888),那么在你的服务器加一段接收
    字符串"<policy-file-request/>"的代码，当接到这个字符串时将策略文家按照xml格式发到客户端。

关于策略文件的格式（可以在Flash CS3帮助里面的Flash Player安全性——》控制权限概述中找到）

1、针对web应用的策略文件

下面的示例显示了一个策略文件，该文件允许访问源自 *.iflashigame.com 和 192.0.34.166 的 SWF 文件。

<?xml version="1.0"?>
<cross-domain-policy>
    <allow-access-from domain="*.iflashigame.com" />
    <allow-access-from domain="192.0.34.166" />
</cross-domain-policy>

注意事项：
      默认情况下，策略文件必须命名为 crossdomain.xml，并且必须位于服务器的根目录中。但是，SWF 文件可以通过调用 Security.loadPolicyFile() 
      方法检查是否为其它名称或位于其它目录中。跨域策略文件仅适用于从其中加载该文件的目录及其子目录。因此，根目录中的策略文件适用于整个服务器，但是从
      任意子目录加载的策略文件仅适用于该目录及其子目录。

      策略文件仅影响对其所在特定服务器的访问。例如，位于 https://www.adobe.com:8080/crossdomain.xml 的策略文件只适用于在端口 8080 通过 HTTPS 
      对 http://www.adobe.com/ 进行的数据加载调用。

2、针对Socket的策略文件

<cross-domain-policy> 
   <allow-access-from domain="*" to-ports="507" /> 
   <allow-access-from domain="*.example.com" to-ports="507,516" /> 
   <allow-access-from domain="*.example2.com" to-ports="516-523" /> 
   <allow-access-from domain="http://www.example2.com/" to-ports="507,516-523" /> 
   <allow-access-from domain="http://www.example3.com/" to-ports="*" /> 
</cross-domain-policy>

这个策略文件是指定允许哪些域的主机通过那些端口链接。




*/


/**
 * A Simple echo server
 * @author WAZA
 */
public class FlashCrossdomainService extends IoHandlerAdapter
{
	static public void main(String[] args) 
	{
		new FlashCrossdomainService();
	}
	
	final static int PORT = 843;
	
	final static String REQUST = 
		"<policy-file-request/>";
	
	final static String RESPONSE = 
		"<cross-domain-policy> \n" +
		"<allow-access-from domain=\"*\" to-ports=\"*\" /> \n" +
		"</cross-domain-policy> \n" +
		"\0";
	
	private IoAcceptor Acceptor;
	
	private FlashCrossdomainService()
	{
		System.out.println("starting flash crossdomain service at port : " + PORT);
		
		try
		{
			Acceptor				= new NioSocketAcceptor();
			DefaultIoFilterChainBuilder chain = Acceptor.getFilterChain();
			chain.addLast("codec",	new ProtocolCodecFilter(new FlashCrossdomainCodec()));
			Acceptor.setHandler(this);
			Acceptor.bind(new InetSocketAddress(PORT));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.err.println("exceptionCaught : " + session + "\n\t" + cause.getMessage());
		cause.printStackTrace();
	}
	
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("sessionOpened : " + session);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed : " + session);
	}
	
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("messageSent : " + session + "\n" + message);
	}
	
	public void messageReceived(final IoSession session, final Object message) throws Exception {
		System.out.println("messageReceived : " + session + "\n" + message);
		String msg = (message+"").trim();
		//if (REQUST.length() <= msg.length() && REQUST.startsWith(msg)) {
			session.write(RESPONSE);
		//}
	}
	

	public class FlashCrossdomainCodec implements ProtocolCodecFactory
	{
		class FlashCrossdomainDecoder extends ProtocolDecoderAdapter 
		{
			public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception 
			{
				try{
					if (in.hasRemaining()) {
						byte[] data = new byte[in.remaining()];
						in.get(data);
						String msg = new String(data);
						out.write(msg);
					}
	    			
	    		}catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}

	    class FlashCrossdomainEncoder extends ProtocolEncoderAdapter
	    {
	    	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception 
	    	{
	    		try{
	    			out.write(IoBuffer.wrap(message.toString().getBytes()));
	    		}catch (Throwable e) {
					e.printStackTrace();
				}
	    	}
	    }
		
	    private ProtocolEncoder encoder = new SynchronizedProtocolEncoder(new FlashCrossdomainEncoder());
	    private ProtocolDecoder decoder = new SynchronizedProtocolDecoder(new FlashCrossdomainDecoder());

	    public FlashCrossdomainCodec() {}

	    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
	        return encoder;
	    }

	    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
	        return decoder;
	    }
	    
	    public ProtocolDecoder getDecoder() throws Exception {
	    	return decoder;
	    }
	    
	    public ProtocolEncoder getEncoder() throws Exception {
	    	return encoder;
	    }

	}

	
	
	
}

package proxy;

import com.net.minaimpl.proxy.ProxyImpl;

public class Proxy extends ProxyImpl
{
	public static void main(String[] args)
	{
		System.out.println("usage:");
		System.out.println("Proxy <local-port> <remote-host> <remote-port>");
		
		if (args.length >= 3)
		{
			Integer local_port = Integer.parseInt(args[0]);
			String	remote_host = args[1];
			Integer remote_port = Integer.parseInt(args[2]);
			
			System.out.println("starting proxy ...");
			
			openProxy(local_port, remote_host, remote_port);
		}
	}
	
	public static Proxy openProxy(int local_port, String remote_host, int remote_port) 
	{
		Proxy proxy = new Proxy();
		proxy.open(local_port, remote_host, remote_port);
		return proxy;
	}
	
}

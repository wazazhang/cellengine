package com.net.server.telnet;

/**


Telnet协议可以工作在任何主机（例如，任何操作系统）或任何终端之间。
RFC854[Postel和Reynolds1983a]定义了该协议的规范，其中还定义了
一种通用字符终端叫做网络虚拟终端NVT（NetworkVirtualTerminal）。
NVT是虚拟设备，连接的双方，即客户机和服务器，都必须把它们的物理终
端和NVT进行相互转换。也就是说，不管客户进程终端是什么类型，操作系
统必须把它转换为NVT格式。同时，不管服务器进程的终端是什么类型，操
作系统必须能够把NVT格式转换为终端所能够支持的格式。<br>
<br>
　　NVT是带有键盘和打印机的字符设备。用户击键产生的数据被发送到服
务器进程，服务器进程回送的响应则输出到打印机上。默认情况下，用户击
键产生的数据是发送到打印机上的，但是我们可以看到这个选项是可以改变的。<br>
<br>
NVTASCII<br>
　　术语NVTASCII代表7比特的ASCII字符集，网间网协议族都使用NVTASCII。
每个7比特的字符都以8比特格式发送，最高位比特为0。
行结束符以两个字符CR（回车）和紧接着的LF（换行）这样的序列表示。
以\r\n来表示。单独的一个CR也是以两个字符序列来表示，它们是CR和紧接
着的NUL（字节0），以\r\0表示。<br>
<br>
Telnet命令<br>
　　Telnet通信的两个方向都采用带内信令方式。字节0xff（十进制的255）
叫做IAC（interpretascommand，意思是“作为命令来解释”）。该字节后面
的一个字节才是命令字节。如果要发送数据255，就必须发送两个连续的
字节255（在前面一节中我们讲到数据流是NVTASCII，它们都是7bit的格式，
这就暗示着255这个数据字节不能在Telnet上传输。其实在Telnet中有一个
二进制选项，在RFC856[Postel和Reynolds1983b]中有定义，关于这点我们
没有讨论，该选项允许数据以8bit进行传输）。图26-8列出了所有的Telnet命令。
由于这些命令中很多命令很少用到，所以对于一些重要的命令，如果在下面章节的
例子或叙述中遇到，我们再做解释。<br>
<br>
<table border>
<tr><th>名称代码</th><th>(十进制)</th><th>描述</th>
<tr><td>EOF   </td><td>236</td> <td>文件结束符</td>
<tr><td>SUSP  </td><td>237</td> <td>挂起当前进程（作业控制）</td>
<tr><td>ABORT </td><td>238</td> <td>异常中止进程</td>
<tr><td>EOR   </td><td>239</td> <td>记录结束符</td>
<tr><td>SE    </td><td>240</td> <td>子选项结束</td>
<tr><td>NOP   </td><td>241</td> <td>无操作</td>
<tr><td>DM    </td><td>242</td> <td>数据标记</td>
<tr><td>BRK   </td><td>243</td> <td>中断</td>
<tr><td>IP    </td><td>244</td> <td>中断进程 </td>
<tr><td>AO    </td><td>245</td> <td>异常中止输出   </td>
<tr><td>AYT   </td><td>246</td> <td>对方是否还在运行？</td>
<tr><td>EC    </td><td>247</td> <td>转义字符     </td>
<tr><td>EL    </td><td>248</td> <td>删除行      </td>
<tr><td>GA    </td><td>249</td> <td>继续进行     </td>
<tr><td>SB    </td><td>250</td> <td>子选项开始    </td>
<tr><td>WILL  </td><td>251</td> <td>选项协商     </td>
<tr><td>WONT  </td><td>252</td> <td>选项协商     </td>
<tr><td>DO    </td><td>253</td> <td>选项协商     </td>
<tr><td>DONT  </td><td>254</td> <td>选项协商     </td>
<tr><td>IAC   </td><td>255</td> <td>数据字节255  </td>
</table>

<br>
当前一个字节是IAC（255）时的Telnet命令集<br>
 * @author WAZA
 *
 */
public interface TelnetProtocol
{
	final public static byte EOF   =(byte)236;//文件结束符
	final public static byte SUSP  =(byte)237;// 挂起当前进程（作业控制）
	final public static byte ABORT =(byte)238;//异常中止进程
	final public static byte EOR   =(byte)239;//记录结束符
	final public static byte SE    =(byte)240;//子选项结束
	final public static byte NOP   =(byte)241;//无操作
	final public static byte DM    =(byte)242;//数据标记
	final public static byte BRK   =(byte)243;//中断
	final public static byte IP    =(byte)244;//中断进程 
	final public static byte AO    =(byte)245;//异常中止输出   
	final public static byte AYT   =(byte)246;//对方是否还在运行？
	final public static byte EC    =(byte)247;//转义字符     
	final public static byte EL    =(byte)248;//删除行      
	final public static byte GA    =(byte)249;//继续进行     
	final public static byte SB    =(byte)250;//子选项开始    
	final public static byte WILL  =(byte)251;//选项协商     
	final public static byte WONT  =(byte)252;//选项协商     
	final public static byte DO    =(byte)253;//选项协商     
	final public static byte DONT  =(byte)254;//选项协商     
	final public static byte IAC   =(byte)255;//数据字节255  

	
	
	
}

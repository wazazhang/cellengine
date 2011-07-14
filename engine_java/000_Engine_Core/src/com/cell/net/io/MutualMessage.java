package com.cell.net.io;




/**<pre>
 * 该类将按顺序序列化所有公共字段。
 * 所以，如果想在网络上传输数据，此类成员必须声明为public。
 * 如果此类成员包含其他复杂对象，则此成员实现MutualMessage接口。
 * 此类型也可以为其他语言的消息包实现编/解码。
 * 可实现自动生成其他语言形式的编解码规范。
 * @author WAZA
 */
public interface MutualMessage extends MessageHeader
{
//	public MutualMessage() {}
//	
//	@Override
//	final public void readExternal(NetDataInput in) throws IOException {
//		in.getFactory().getMutualCodec().readExternal(this, in);
//	}
//	@Override
//	final public void writeExternal(NetDataOutput out) throws IOException {
//		out.getFactory().getMutualCodec().writeExternal(this, out);
//	}
	
}

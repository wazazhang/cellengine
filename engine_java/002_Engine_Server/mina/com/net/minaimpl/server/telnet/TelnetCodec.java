package com.net.minaimpl.server.telnet;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

public class TelnetCodec implements ProtocolCodecFactory
{
    private final TelnetEncoder		encoder;
    private final TelnetDecoder		decoder;

    public TelnetCodec(Charset charset) {
    	encoder = new TelnetEncoder(charset, LineDelimiter.UNIX);
    	decoder = new TelnetDecoder(charset, LineDelimiter.AUTO);
    }

    public ProtocolEncoder getEncoder(IoSession session) {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) {
        return decoder;
    }
    
	class TelnetEncoder extends ProtocolEncoderAdapter 
	{
		final private TextLineEncoder text_encoder;

		public TelnetEncoder(Charset charset, LineDelimiter delimiter) {
			text_encoder = new TextLineEncoder(charset, delimiter);
		}

		public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
			if (message instanceof String) {
				text_encoder.encode(session, message, out);
			} else if (message instanceof IoBuffer) {
				out.write(message);
			}
		}
	}

	class TelnetDecoder extends ProtocolDecoderAdapter 
	{
		final private TextLineDecoder text_decoder;

		public TelnetDecoder(Charset charset, LineDelimiter delimiter) {
			text_decoder = new TextLineDecoder(charset, delimiter);
		}

		@Override
		public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
			text_decoder.decode(session, in, out);
		}
	}
}

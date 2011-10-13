package com.cell.xstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.cell.classloader.jcl.JavaCompiler;
import com.cell.persistance.PersistanceManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.DefaultConverterLookup;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamAdapter extends PersistanceManager
{
//	----------------------------------------------------------------------------------------------------------
//	singleton
	final private static XStreamAdapter xstream_adapter = new XStreamAdapter();
	
	final public static XStreamAdapter getInstance() {
		return xstream_adapter;
	}

//	----------------------------------------------------------------------------------------------------------

	private CompositeClassLoader		composite_class_loader	= new CompositeClassLoader();
	private XppDriver					xpp_dirver				= new XppDriver(new XmlFriendlyReplacer(".", "_"));
//	----------------------------------------------------------------------------------------------------------

	public XStreamAdapter() {
		composite_class_loader.add(JavaCompiler.getInstance());
	}
	
	public ObjectInputStream createReadStream(InputStream reader) throws IOException {
		XStream xstream = new XStreamImpl();
		return xstream.createObjectInputStream(reader);
	}

	public ObjectOutputStream createWriteStream(OutputStream writer) throws IOException {
		XStream xstream = new XStreamImpl();
		return xstream.createObjectOutputStream(writer);
	}
	
	public ObjectInputStream createReadStream(Reader reader) throws IOException {
		XStream xstream = new XStreamImpl();
		return xstream.createObjectInputStream(reader);
	}
	
	public ObjectOutputStream createWriteStream(Writer writer) throws IOException
	{
		XStream xstream = new XStreamImpl();
		return xstream.createObjectOutputStream(writer);
	}
	
//	----------------------------------------------------------------------------------------------------------

	class MapperWrapperImpl extends MapperWrapper
	{
		public MapperWrapperImpl(MapperWrapper next) {
			super(next);
		}
		
		@SuppressWarnings("unchecked")
		public boolean shouldSerializeMember(Class definedIn, String fieldName) {
			if(definedIn != Object.class){
				return super.shouldSerializeMember(definedIn, fieldName);
			} else {
				System.err.println("ignore member : " + definedIn + " - \"" + fieldName + "\"");
				return false;
			}
		}
		
		@Override
		public Class<?> realClass(String elementName) {
			try{
				return super.realClass(elementName);
			}catch(Exception err){
				System.err.println("ignore class : " + elementName);
				return null;
			}
		}
		
	}
	
	class XStreamImpl extends XStream
	{
		public XStreamImpl() {
			super(null, 
					xpp_dirver, 
					composite_class_loader, 
					null, 
					new DefaultConverterLookup(), 
					null);
		}
		
		protected MapperWrapper wrapMapper(MapperWrapper next) {
			return new MapperWrapperImpl(next);
		}
	}

//	----------------------------------------------------------------------------------------------------------
	
	
}

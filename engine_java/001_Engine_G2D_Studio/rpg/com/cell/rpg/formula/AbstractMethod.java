package com.cell.rpg.formula;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.cell.rpg.formula.anno.MethodSynthetic;
import com.cell.rpg.formula.anno.ObjectMethod;
import com.cell.rpg.formula.helper.IFormulaAdapter;
import com.g2d.annotation.Property;

public abstract class AbstractMethod extends AbstractValue 
{
//	------------------------------------------------------------------------------------------------------------------------------
	
//	transient private String method_name;
	
	@Property("方法名")
	public MethodInfo			method_info;

	@Property("参数列表")
	public AbstractValue[]		parameters;

	/** 如果该方法返回类型是复合类型，则将调用下一个方法*/
	public SyntheticMethod		return_object_method;

	
//	------------------------------------------------------------------------------------------------------------------------------
	
	public AbstractMethod() {
		this.method_info	= new MethodInfo();
		this.parameters		= null;
	}

	public AbstractMethod(Method method, AbstractValue[] parameters) {
		this.method_info	= new MethodInfo(method);
		this.parameters		= parameters;
	}
	
	@Override
	protected Object readResolve() throws ObjectStreamException {
		if (method_info == null) {		
			System.err.println(getClass().getName() + " : method_info is null !");
			method_info = new MethodInfo();
		}
		return super.readResolve();
	}
	
	@Override
	public String toString() {
		String ret = method_info + "";
		ret += "(";
		if (parameters!=null) {
			for (int i=0; i<parameters.length; i++) {
				ret += parameters[i];
				if (i != parameters.length - 1) {
					ret += ", ";
				}
			}
		}
		ret += ")";
		if (return_object_method != null) {
			ret += "." + return_object_method;
		}
		return ret;
	}

//	------------------------------------------------------------------------------------------------------------------------------

	final public void setMethod(Method method) {
		method_info = new MethodInfo(method);
	}
	
	final public Method getMethod() {
		return method_info.method;
	}
	
	/**call*/
	final public Object[] getInvokeParams(IFormulaAdapter adapter) throws Throwable {
		Object[] params = new Object[parameters.length];
		for (int i=0; i<parameters.length; i++) {
			params[i] = Parser.castNumber(
					parameters[i].getValue(adapter), 
					method_info.parameter_types[i]);
		}
		return params;
	}

//	------------------------------------------------------------------------------------------------------------------------------

	static public boolean validateMethod(Method method) {
		if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
			return false;
		}
		if (!Parser.isNumber(method.getReturnType())) {
			if (method.getAnnotation(MethodSynthetic.class)==null) {
				return false;
			}
		}
		for (Class<?> parm : method.getParameterTypes()) {
			if (!Parser.isNumber(parm)) {
				return false;
			}
		}
		return true;
	}

	static public Collection<Method> getValidateMethods(Class<?> return_type) {
		ArrayList<Method> ret = new ArrayList<Method>();
		for (Method method : return_type.getDeclaredMethods()) {
			if (validateMethod(method)) {
				ret.add(method);
			}
		}
		return ret;
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	static public class MethodInfo implements Externalizable
	{
		public Class<?>		type;
		
		public Method		method;
		
		public Class<?>		parameter_types[];

		private transient
		MethodSynthetic		return_synthetic;
		private transient
		ObjectMethod		method_comment;
		
		public MethodInfo() {}
		
		public MethodInfo(Method method) {
			this.type				= method.getDeclaringClass();
			this.parameter_types	= method.getParameterTypes();
			this.method				= method;
			this.return_synthetic	= method.getAnnotation(MethodSynthetic.class);
			this.method_comment		= method.getAnnotation(ObjectMethod.class);
			if (Parser.isNumber(method.getReturnType())) {
				this.return_synthetic = null;
			}
		}
		
		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			try{
				this.type	= Class.forName(in.readUTF());
				this.parameter_types = new Class<?>[in.readInt()];
				for (int i=0; i<parameter_types.length; i++) {
					parameter_types[i] = Parser.classForName(in.readUTF());
				}
				this.method	= type.getMethod(in.readUTF(), parameter_types);
				this.return_synthetic	= method.getAnnotation(MethodSynthetic.class);
				this.method_comment		= method.getAnnotation(ObjectMethod.class);
				if (Parser.isNumber(method.getReturnType())) {
					this.return_synthetic = null;
				}
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		
		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			try{
				out.writeUTF(type.getName());
				out.writeInt(parameter_types.length);
				for (int i=0; i<parameter_types.length; i++) {
				out.writeUTF(parameter_types[i].getName());
				}
				out.writeUTF(method.getName());
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		
		@Override
		public String toString() {
			if (this.method != null) {
				return method.getName();
			}
			return super.toString();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj instanceof MethodInfo) {
				MethodInfo other = (MethodInfo)obj;
				if (this.type != null && this.type.equals(other.type)) {
					if (this.method != null && this.method.equals(other.method)) {
						if (CUtil.arrayEquals(this.parameter_types, other.parameter_types) ) {
							return true;
						}
					}
				}
			}
			return false;
		}
		
		public MethodSynthetic getReturnSynthetic() {
			return return_synthetic;
		}

		public ObjectMethod getMethodComment() {
			return method_comment;
		}

	}

//	------------------------------------------------------------------------------------------------------------------------------

	static public class SyntheticMethod extends AbstractMethod
	{
		public SyntheticMethod() {
			super();
		}
		
		public SyntheticMethod(Method method, AbstractValue[] values) {
			super(method, values);
		}
		
	}

//	------------------------------------------------------------------------------------------------------------------------------

	static public void main(String[] args) throws Exception
	{
		Method m = AbstractMethod.class.getMethod("validateMethod", new Class<?>[]{Method.class});
		System.out.println(m);
		System.out.println(m.getDeclaringClass());
	}
	
}

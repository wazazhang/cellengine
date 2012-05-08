package com.cell.rpg.formula;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.cell.rpg.formula.helper.IFormulaAdapter;

public abstract class AbstractValue implements Serializable
{
	final public Number getValue(IFormulaAdapter adapter) throws Throwable {
		return adapter.getValue(this);
	}

	abstract public String toString();
	

//	----------------------------------------------------------------------------------------------------------------
	
	protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	
	protected Object readResolve() throws ObjectStreamException {
		return this;
	}

//	----------------------------------------------------------------------------------------------------------------
	
}

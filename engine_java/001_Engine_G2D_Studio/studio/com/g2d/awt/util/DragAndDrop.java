package com.g2d.awt.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class DragAndDrop {

	public static<T> T getTransferData(Transferable t, Class<T> type) {
		for (DataFlavor df : t.getTransferDataFlavors()) {
			try {
				Object data = t.getTransferData(df);
				if (type.isInstance(data)) {
					return type.cast(data);
				}
				System.out.println("-------------------------------------------");
				System.out.println(data);
			} catch (Exception e) {}
		}
		return null;
	}
	
	
}

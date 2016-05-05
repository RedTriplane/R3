
package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.ArrayList;

public class LayerChildren implements java.io.Serializable {

	private static final long serialVersionUID = 99160642782889515L;

	public ArrayList<LayerElement> list = new ArrayList<LayerElement>();

	public void addElement (final LayerElement element) {
		this.list.add(element);
	}

	public int size () {
		return this.list.size();
	}

	public LayerElement elementAt (final int i) {
		return this.list.get(i);
	}

}

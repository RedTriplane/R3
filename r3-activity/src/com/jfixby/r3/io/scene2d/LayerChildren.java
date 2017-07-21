
package com.jfixby.r3.io.scene2d;

import java.util.ArrayList;

public class LayerChildren implements java.io.Serializable {

	private static final long serialVersionUID = 99160642782889515L;
	static boolean check = true;
	public ArrayList<String> list = new ArrayList<String>();

	public void addElement (final LayerElement element, final SceneStructure structure) {
		final String uid = element.uid;
		if (uid == null) {
			throw new Error("UID==null");
		}
		if (check) {
			final LayerElement result = structure.findElementByUID(uid);
			if (result != element) {
				throw new Error("corrupted structure " + structure.elements);
			}
		}
		this.list.add(uid);
	}

	public int size () {
		return this.list.size();
	}

	public LayerElement elementAt (final int i, final SceneStructure structure) {
		final String uid = this.list.get(i);
		final LayerElement result = structure.findElementByUID(uid);
		return result;
	}

}

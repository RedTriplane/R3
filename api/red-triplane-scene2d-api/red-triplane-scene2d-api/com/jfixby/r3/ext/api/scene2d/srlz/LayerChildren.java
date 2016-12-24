
package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.ArrayList;

import com.jfixby.scarabei.api.err.Err;

public class LayerChildren implements java.io.Serializable {

	private static final long serialVersionUID = 99160642782889515L;
	static boolean check = true;
	public ArrayList<String> list = new ArrayList<String>();

	public void addElement (final LayerElement element, final SceneStructure structure) {
		final String uid = element.uid;
		if (uid == null) {
			Err.reportError("UID==null");
		}
		if (check) {
			final LayerElement result = structure.findElementByUID(uid);
			if (result != element) {
				Err.reportError("corrupted structure " + structure.elements);
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

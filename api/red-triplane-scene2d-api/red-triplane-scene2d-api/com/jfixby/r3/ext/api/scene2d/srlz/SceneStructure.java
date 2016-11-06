
package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.HashMap;

public class SceneStructure implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -927468651743679015L;

	public SceneStructure () {

	}

	public String structure_name;

	public LayerElement root = null;

	public double original_width;

	public double original_height;

	final public HashMap<String, LayerElement> elements = new HashMap<String, LayerElement>();

	public LayerElement findElementByUID (final String uid) {
		return this.elements.get(uid);
	}

	public void registerNewElement (final LayerElement element) {
		if (element.uid == null) {
			throw new Error("Element uid = null " + element);
		}
		this.elements.put(element.uid, element);
	}

}

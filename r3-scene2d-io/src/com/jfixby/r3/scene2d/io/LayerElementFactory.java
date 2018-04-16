
package com.jfixby.r3.scene2d.io;

public class LayerElementFactory {

	private long uid = -1;
	private final SceneStructure structure;

	public LayerElementFactory (final SceneStructure structure) {
		this.structure = structure;
	}

	public LayerElement newLayerElement () {
		this.uid++;
		final LayerElement element = new LayerElement("le-" + this.uid);
		this.structure.registerNewElement(element);
		return element;
	}

	public SceneStructure getStructure () {
		return this.structure;
	}

}

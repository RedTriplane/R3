
package com.jfixby.r3.io.scene2d;

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

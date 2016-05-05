
package com.jfixby.r3.ext.api.scene2d.srlz;

public class LayerElementFactory {

	private long uid = -1;

	public LayerElementFactory (final SceneStructure structure) {
	}

	public LayerElement newLayerElement () {
		this.uid++;
		return new LayerElement("le-" + this.uid);
	}

}

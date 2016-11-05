
package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.HashMap;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.rana.api.asset.Asset;

public class SceneStructure implements Asset, java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -927468651743679015L;

	public String structure_name;

	public LayerElement root = null;

	public double original_width;

	public double original_height;

	final public HashMap<String, LayerElement> elements = new HashMap<String, LayerElement>();

	@Override
	public AssetID getAssetID () {
		return Names.newAssetID(this.structure_name);
	}

	public LayerElement findElementByUID (final String uid) {
		return this.elements.get(uid);
	}

	public void registerNewElement (final LayerElement element) {
		if (element.uid == null) {
			throw new Error("Element uid = null " + element);
		}
		this.elements.put(element.uid, element);
	}

	@Override
	public void dispose () {
		this.elements.clear();
	}

}

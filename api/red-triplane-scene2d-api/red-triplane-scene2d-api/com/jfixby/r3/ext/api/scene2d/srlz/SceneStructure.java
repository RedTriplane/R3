package com.jfixby.r3.ext.api.scene2d.srlz;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.r3.api.assets.Asset;

public class SceneStructure implements Asset {

	public String structure_name;

	public final LayerElement root = new LayerElement();

	@Override
	public AssetID getAssetID() {
		return Names.newAssetID(structure_name);
	}

}

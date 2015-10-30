package org.box2d.r3.gdx.beditor;

import com.jfixby.cmns.api.assets.AssetID;

public class Box2DEditorShape extends RedPolyBodySpecs {

	private AssetID asset_id;

	public AssetID getID() {
		return this.asset_id;
	}

	public void setID(AssetID newAssetID) {
		this.asset_id = newAssetID;
	}

}

package org.box2d.r3.gdx.beditor;

import com.jfixby.scarabei.api.assets.ID;

public class Box2DEditorShape extends RedPolyBodySpecs {

	private ID asset_id;

	public ID getID() {
		return this.asset_id;
	}

	public void setID(ID newAssetID) {
		this.asset_id = newAssetID;
	}

}

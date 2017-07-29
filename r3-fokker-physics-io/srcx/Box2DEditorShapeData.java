package com.jfixby.r3.fokker.core.assets;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;

public class Box2DEditorShapeData {

	private final ID asset_id;
	private final Box2DEditorShape shape;

	public Box2DEditorShapeData(ID spriteAssetID, Box2DEditorShape shape) {
		this.asset_id = spriteAssetID;
		this.shape = shape;
		Debug.checkNull("asset_id", asset_id);
		Debug.checkNull("   shape", shape);

	}

	public ID getAssetID() {
		return asset_id;
	}

}

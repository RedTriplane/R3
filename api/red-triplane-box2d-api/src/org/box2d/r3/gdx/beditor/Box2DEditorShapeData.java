package org.box2d.r3.gdx.beditor;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.r3.api.physics.PolyBodySpecs;

public class Box2DEditorShapeData {

	private final AssetID asset_id;
	private final Box2DEditorShape shape;

	public Box2DEditorShapeData(AssetID spriteAssetID, Box2DEditorShape shape) {
		this.asset_id = spriteAssetID;
		this.shape = shape;
		JUtils.checkNull("asset_id", asset_id);
		JUtils.checkNull("   shape", shape);

	}

	public AssetID getAssetID() {
		return asset_id;
	}

}

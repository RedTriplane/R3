
package com.jfixby.r3.fokker.texture.red;

import com.jfixby.r3.fokker.texture.api.FokkerTexture;
import com.jfixby.r3.fokker.texture.api.FokkerTexturePackageReader;
import com.jfixby.r3.fokker.texture.api.FokkerTexturesComponent;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

public class RedFokkerTextures implements FokkerTexturesComponent {

	final FokkerTextureLoader reader = new FokkerTextureLoader(this);
	final Map<ID, FokkerTexture> registry = Collections.newMap();

	public void register (final ID raster_id, final FokkerTexture data) {
		this.registry.put(raster_id, data);
	}

	@Override
	public FokkerTexturePackageReader packageReader () {
		return this.reader;
	}

	@Override
	public FokkerTexture obtain (final ID assetID) {
		FokkerTexture texture = this.registry.get(assetID);
		if (texture == null) {
			Err.reportError("Asset not found <" + assetID + ">");
		}
		texture = this.registry.get(assetID);
		if (texture == null) {
			Err.reportError("Texture not found: " + assetID);
		}
		return texture;
	}
}

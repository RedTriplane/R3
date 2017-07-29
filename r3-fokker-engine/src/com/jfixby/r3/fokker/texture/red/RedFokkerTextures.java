
package com.jfixby.r3.fokker.texture.red;

import java.io.IOException;

import com.jfixby.r3.fokker.texture.api.FokkerTexture;
import com.jfixby.r3.fokker.texture.api.FokkerTexturePackageReader;
import com.jfixby.r3.fokker.texture.api.FokkerTexturesComponent;
import com.jfixby.r3.rana.api.manager.AssetsManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;

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
		FokkerTexture shader = this.registry.get(assetID);
		if (shader == null) {
			try {
				AssetsManager.autoResolveAsset(assetID);
			} catch (final IOException e) {
				e.printStackTrace();
				Err.reportError(e);
			}
		}
		shader = this.registry.get(assetID);
		if (shader == null) {
			Err.reportError("Texture not found: " + assetID);
		}
		return shader;
	}
}

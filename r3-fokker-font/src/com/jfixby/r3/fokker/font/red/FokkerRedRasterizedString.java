
package com.jfixby.r3.fokker.font.red;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jfixby.r3.fokker.font.api.FokkerFont;
import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.r3.fokker.font.api.FokkerStringSpecs;
import com.jfixby.r3.fokker.font.red.gdx.RedBitmapFont;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.manager.AssetsManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class FokkerRedRasterizedString implements FokkerString {

	private RedBitmapFont font;
	private AssetHandler font_aset;
	final FokkerFont font_data;
// final File gdx_font_file;

	final VerticesCache verticesCache = new VerticesCache();

	public FokkerRedRasterizedString (final FokkerStringSpecs specs, final AssetsConsumer fontConsumer) {
// final RedRasterizedString redRasterizedString

		try {
			this.font_aset = this.obtain(specs.fontID, fontConsumer);
		} catch (final IOException e) {
			e.printStackTrace();
			Err.reportError(e);
			this.font_aset = null;
		}
		this.font_data = this.font_aset.asset();
// this.gdx_font_file = this.font_data.getFontFile();

	}

	public void dispose (final AssetsConsumer componentsFactory) {
		LoadedAssets.releaseAsset(this.font_aset, componentsFactory);
		L.e("RedRasterizedString disposal required: https://github.com/JFixby/RedTriplane/issues/3");
	}

	private AssetHandler obtain (final ID newAssetID, final AssetsConsumer componentsFactory) throws IOException {
		AssetsManager.autoResolveAssetAsync(newAssetID);
		final AssetHandler asset_handler = LoadedAssets.obtainAsset(newAssetID, componentsFactory);
		if (asset_handler == null) {
			LoadedAssets.printAllLoadedAssets();
// PackagesLoader.printAllPackageReaders();
// ResourcesManager.printAllPackages();
			Err.reportError("Font<" + newAssetID + "> not found.");
		}
		return asset_handler;
	}

	@Override
	public Array<TextureRegion> getRegions () {
		return this//
			.font//
				.getGdxFont()//
				.getRegions();
	}

	@Override
	public int getVertexCount (final int region) {
		return this.font.getCache().getVertexCount(region);
	}

	@Override
	public float[] getVertices (final int region) {
// return this.font.getCache().getVertices(region);
		this.verticesCache.updateRegion(region, this.font.getCache().getVertices(region));
		return this.verticesCache.getVertices(region);
	}
// HashMap<>

	@Override
	public int getNumberOfSprites (final int region) {
// return this.font.getCache().getVertices(region).length / SpritesRenderer.SPRITE_SIZE;
		return this.verticesCache.getNumberOfSprites(region);
	}

}

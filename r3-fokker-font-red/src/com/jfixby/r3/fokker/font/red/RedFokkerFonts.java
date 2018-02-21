
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.fokker.font.api.FokkerFont;
import com.jfixby.r3.fokker.font.api.FokkerFontPackageReader;
import com.jfixby.r3.fokker.font.api.FokkerFontsComponent;
import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.r3.fokker.font.api.FokkerStringHandler;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.font.RasterStringSettings;
import com.jfixby.scarabei.api.names.ID;

public class RedFokkerFonts implements FokkerFontsComponent {

	final FokkerStringRegister stringRegister = new FokkerStringRegister();
	final static TrueTypeFontFontPackageLoader reader = new TrueTypeFontFontPackageLoader();

	@Override
	public FokkerFontPackageReader packageReader () {
		return reader;
	}

	@Override
	public FokkerString obtainString (final RasterStringSettings rasterStringSettings) {
		return this.stringRegister.get(rasterStringSettings);
	}

	@Override
	public RasterStringSettings newStringSpec () {
		return new RasterStringSettings();
	}

	@Override
	public FokkerStringHandler spawnString (final RasterStringSettings rasterStringSettings, final AssetsConsumer consumer) {

		FokkerString string = this.obtainString(rasterStringSettings);
		Debug.checkTrue(string == null);

		final ID fontID = rasterStringSettings.fontID;
// RedTTFFontInfo font = this.fontRegister.get(fontID);
		final AssetHandler asset_handler = LoadedAssets.obtainAsset(fontID, consumer);
		if (asset_handler == null) {
			Err.reportError("Font not found: " + fontID);
		}
		final FokkerFont font = asset_handler.asset();

		string = font.produceString(rasterStringSettings);

		final FokkerStringHandler handler = this.stringRegister.register(rasterStringSettings, string, consumer);

		return handler;
	}

	@Override
	public void disposeString (final FokkerStringHandler string, final AssetsConsumer consumer) {
	}

}

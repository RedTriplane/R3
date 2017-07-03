
package com.jfixby.text.loaders.text;

import java.io.IOException;

import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.strings.api.StringData;
import com.jfixby.strings.api.Text;
import com.jfixby.strings.api.TextTranslation;
import com.jfixby.strings.api.TextTranslationsList;
import com.jfixby.strings.api.io.text.TextLocalization;
import com.jfixby.strings.api.io.text.TextPackageEntry;

public class TextDataEntry implements Text, TextTranslationsList, AssetsConsumer, AssetsGroup, Asset {

	private final ID asset_id;
	boolean lazy_mode = !true;
	final Map<String, RedTextTranslation> localizations = Collections.newMap();

	final Map<String, ID> mapping = Collections.newMap();

	public TextDataEntry (final ID asset_id) {
		this.asset_id = asset_id;

	}

	@Override
	public void dispose () {
		this.localizations.clear();
		this.mapping.clear();
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	@Override
	public TextTranslation getByLocalization (final String locale_name) {
		if (!this.mapping.containsKey(locale_name)) {
			return null;
		}
		if (!this.localizations.containsKey(locale_name)) {
			final ID locale_id = this.mapping.get(locale_name);
			this.load(locale_name, locale_id);
		}

		return this.localizations.get(locale_name);
	}

	@Override
	public AssetsGroup getGroup () {
		return this;
	}

	@Override
	public TextTranslation getLast () {
		return this.getByLocalization(this.mapping.keys().getLast());
	}

	@Override
	public TextTranslationsList listTranslations () {
		return this;
	}

	private void load (final String name, final ID locale_id) {

		AssetHandler stringData = LoadedAssets.obtainAsset(locale_id, this);

		if (stringData == null) {
			try {
				AssetsManager.autoResolveAsset(locale_id);
			} catch (final IOException e) {
				e.printStackTrace();
				Err.reportError(e);
			}
			stringData = LoadedAssets.obtainAsset(locale_id, this);
		}

		final StringData data = stringData.asset();
		final RedTextTranslation translation = new RedTextTranslation(name, data);
		this.localizations.put(name, translation);

		LoadedAssets.releaseAsset(stringData, this);

	}

	@Override
	public void print () {
		this.localizations.print("Localizations[" + this.asset_id + "]");
	}

	public void setData (final TextPackageEntry entry_srlz) {
		for (int i = 0; i < entry_srlz.localizations.size(); i++) {
			final TextLocalization loc = entry_srlz.localizations.get(i);
			final ID locale_id = Names.newID(loc.container_id);
			final String name = loc.name;

			this.mapping.put(name, locale_id);

			if (!this.lazy_mode) {
				this.load(name, locale_id);
			}
		}
// this.localizations.print("loaded: " + this.asset_id);
	}

}

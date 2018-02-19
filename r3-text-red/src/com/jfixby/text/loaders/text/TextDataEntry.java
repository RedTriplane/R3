
package com.jfixby.text.loaders.text;

import java.io.IOException;

import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.manager.AssetsManager;
import com.jfixby.r3.string.io.text.SrlzdTextLocalization;
import com.jfixby.r3.string.io.text.SrlzdTextPackageEntry;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;
import com.jfixby.scarabei.api.strings.Text;
import com.jfixby.scarabei.api.strings.TextSpawner;
import com.jfixby.text.loaders.strings.StringDataEntry;

public class TextDataEntry implements Text, TextSpawner, AssetsConsumer, AssetsGroup, Asset {

	private final ID asset_id;
	final Map<String, RedTextTranslation> localizations = Collections.newMap();

	final Map<String, ID> mapping = Collections.newMap();
	private String currentLocalization;

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
	public AssetsGroup getGroup () {
		return this;
	}

	public void setData (final SrlzdTextPackageEntry entry_srlz) {
		for (int i = 0; i < entry_srlz.localizations.size(); i++) {
			final SrlzdTextLocalization loc = entry_srlz.localizations.get(i);
			final ID locale_id = Names.newID(loc.container_id);
			final String name = loc.name;

			{
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

				final StringDataEntry data = stringData.asset();
				final RedTextTranslation translation = new RedTextTranslation(name, data);

				this.mapping.put(name, locale_id);
				this.localizations.put(name, translation);
				this.switchLocale(name);

				LoadedAssets.releaseAsset(stringData, this);
			}
		}
// this.localizations.print("loaded: " + this.asset_id);
	}

	@Override
	public String getString () {
		return this.localizations.get(this.currentLocalization).getString();
	}

	@Override
	public String getLocaleName () {
		return this.currentLocalization;
	}

	@Override
	public boolean hasLocalization (final String locale_name) {
		return this.mapping.keys().contains(locale_name);
	}

	@Override
	public Collection<String> listLocales () {
		return this.mapping.keys();
	}

	@Override
	public boolean switchLocale (final String locale_name) {
		if (!this.hasLocalization(locale_name)) {
			return false;
		}
		this.currentLocalization = locale_name;
		return true;
	}

	@Override
	public Text newInstance () {
		final TextDataEntry e = new TextDataEntry(this.asset_id);
		e.currentLocalization = this.currentLocalization;

		e.localizations.putAll(this.localizations);
		e.mapping.putAll(this.mapping);

		return e;
	}

}

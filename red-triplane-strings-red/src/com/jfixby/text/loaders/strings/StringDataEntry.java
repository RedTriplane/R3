
package com.jfixby.text.loaders.strings;

import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.strings.api.StringData;
import com.jfixby.strings.api.io.string.StringPackageEntry;

public class StringDataEntry implements StringData, AssetsGroup, Asset {

	private final ID asset_id;
	private String data;

	public StringDataEntry (final ID asset_id) {
		this.asset_id = asset_id;
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	public void setData (final StringPackageEntry entry_srlz) {
		this.data = entry_srlz.string_data;
	}

	@Override
	public String getChars () {
		return this.data;
	}

	@Override
	public String toString () {
		return "<" + this.asset_id + "> = [" + this.data + "]";
	}

	@Override
	public AssetsGroup getGroup () {
		return this;
	}

	@Override
	public void dispose () {

	}

}

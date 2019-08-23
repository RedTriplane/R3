
package com.jfixby.r3.rana.red.asset;

import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.SealedAssetsContainer;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;

public class RedAssetsContainer implements SealedAssetsContainer, AssetsContainer {

	final Map<ID, Asset> assets = Collections.newMap();

	boolean sealed = false;

	@Override
	public Asset getAsset (final ID asset_id) {
		return this.assets.get(asset_id);
	}

	@Override
	public void printAll () {
		L.d("assets", this.assets);
	}

	@Override
	public Collection<ID> listAssets () {
		return this.assets.keys();
	}

	@Override
	public void addAsset (final ID raster_id, final Asset data) {
		Debug.checkTrue("Container is already sealed<" + this + "> : " + raster_id, !this.sealed);
		Debug.checkNull("raster_id", raster_id);
		Debug.checkNull("data", data);
		this.assets.put(raster_id, data);
	}

	@Override
	public SealedAssetsContainer seal () {
		this.sealed = true;
		return this;
	}

	@Override
	public String toString () {
		return this.assets.toString();
	}

}

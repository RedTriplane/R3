
package com.jfixby.r3.rana.api.manager;

import java.io.IOException;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.promise.Promise;

public interface AssetsManagerComponent {

	void autoResolveAssetAsync (ID dependency) throws IOException;

	void autoResolveAssetsAsync (Collection<ID> dependencies) throws IOException;

	AssetsPurgeResult purge ();

	Promise<Void> autoResolveAsset (ID dependency);

	Promise<Void> autoResolveAssets (Collection<ID> dependencies);

}


package com.jfixby.r3.rana.api.manager;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.promise.Promise;

public interface AssetsManagerComponent {

	Promise<Void> autoResolveAsset (ID dependency);

	Promise<Void> autoResolveAssets (Collection<ID> dependencies);

	AssetsPurgeResult purge ();

}

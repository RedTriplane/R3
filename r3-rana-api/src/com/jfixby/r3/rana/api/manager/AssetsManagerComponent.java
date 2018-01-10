
package com.jfixby.r3.rana.api.manager;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.names.ID;

public interface AssetsManagerComponent {

	AssetsPurgeResult purge ();

	void autoResolveAsset (ID dependency) throws IOException;

	void autoResolveAssets (Collection<ID> dependencies) throws IOException;

}

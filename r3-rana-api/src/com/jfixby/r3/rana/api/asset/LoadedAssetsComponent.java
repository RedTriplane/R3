
package com.jfixby.r3.rana.api.asset;

import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.SealedAssetsContainer;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.names.ID;

public interface LoadedAssetsComponent {

	AssetHandler obtainAsset (ID asset_id, AssetsConsumer consumer);

	void releaseAsset (AssetHandler handler, AssetsConsumer consumer);

	void releaseAllAssets (AssetsConsumer consumer);

	void printAllLoadedAssets ();

	AssetHandler useAsset (ID spriteAssetID);

	boolean isRegisteredAsset (ID dependency);

// void purge ();

	void printUsages ();

	Collection<SealedAssetsContainer> listUnusedContainers ();

	void registerAssetsContainer (SealedAssetsContainer container);

	void unRegisterAssetsContainer (SealedAssetsContainer container);

	void unRegisterAssetsContainers (Collection<SealedAssetsContainer> containers);

	AssetsContainer newAssetsContainer ();

}

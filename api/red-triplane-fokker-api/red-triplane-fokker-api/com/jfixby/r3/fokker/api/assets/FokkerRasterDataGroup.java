
package com.jfixby.r3.fokker.api.assets;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Set;

public interface FokkerRasterDataGroup {

	void purge (Set<AssetID> siblings);

	Collection<AssetID> listAssets ();

	FokkerRasterData getDataFor (AssetID asset);

}

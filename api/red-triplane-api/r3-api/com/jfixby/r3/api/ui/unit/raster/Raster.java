
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;

public interface Raster extends ProjectableComponent, BlendableComponent {
	public AssetID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster copy ();

}

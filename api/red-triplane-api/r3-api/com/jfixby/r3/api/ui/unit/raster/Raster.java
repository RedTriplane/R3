
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.geometry.RectangularComponent;

public interface Raster extends BlendableComponent, RectangularComponent {
	public AssetID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster copy ();

	public void offset (double x, double y);

}

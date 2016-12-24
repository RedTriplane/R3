
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.geometry.RectangularComponent;
import com.jfixby.scarabei.api.assets.ID;

public interface Raster extends BlendableComponent, RectangularComponent {
	public ID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster copy ();

	public void offset (double x, double y);

}

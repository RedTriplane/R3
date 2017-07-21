
package com.jfixby.r3.api.activity.raster;

import com.jfixby.r3.api.activity.ComponentsFactory;
import com.jfixby.r3.api.activity.geometry.RectangularComponent;
import com.jfixby.scarabei.api.assets.ID;

public interface Raster extends BlendableComponent, RectangularComponent {
	public ID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster copy ();

	public void offset (double x, double y);

}

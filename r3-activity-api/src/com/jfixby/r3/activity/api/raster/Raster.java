
package com.jfixby.r3.activity.api.raster;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.geometry.RectangularComponent;
import com.jfixby.scarabei.api.names.ID;

public interface Raster extends BlendableComponent, RectangularComponent {
	public ID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster copy ();

	public void offset (double x, double y);

}

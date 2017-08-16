
package com.jfixby.r3.activity.api.raster;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.scarabei.api.names.ID;

public interface RasterPool extends VisibleComponent {

	public ID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster newInstance ();

}

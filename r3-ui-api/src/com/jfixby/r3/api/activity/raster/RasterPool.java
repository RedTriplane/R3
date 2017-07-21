
package com.jfixby.r3.api.activity.raster;

import com.jfixby.r3.api.activity.ComponentsFactory;
import com.jfixby.r3.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.assets.ID;

public interface RasterPool extends VisibleComponent {

	public ID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster newInstance ();

}

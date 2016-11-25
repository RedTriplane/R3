
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.assets.ID;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface RasterPool extends VisibleComponent {

	public ID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster newInstance ();

}


package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.scarabei.api.assets.ID;

public interface RasterPool extends VisibleComponent {

	public ID getAssetID ();

	public ComponentsFactory getComponentsFactory ();

	public Raster newInstance ();

}

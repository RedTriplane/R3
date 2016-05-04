
package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public interface LayerChildren extends Collection<Component> {

	Collection<Raster> findRaster (String name);

	Collection<Layer> findLayer (String string);

}

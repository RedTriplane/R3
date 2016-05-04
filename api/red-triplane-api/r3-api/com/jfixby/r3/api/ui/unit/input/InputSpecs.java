
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public interface InputSpecs {
	String getName ();

	void setName (String button_name);

	void addTouchArea (TouchAreaSpecs touch_area);

	public Collection<TouchAreaSpecs> listTouchAreas ();

	void addOption (Raster raster);

	Collection<Raster> listOptions ();

}

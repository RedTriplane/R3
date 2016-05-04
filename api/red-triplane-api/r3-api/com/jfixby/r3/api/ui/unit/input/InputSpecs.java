
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface InputSpecs {
	String getName ();

	void setName (String button_name);

	TouchAreaSpecs newTouchAreaSpecs ();

	void addTouchArea (TouchAreaSpecs touch_area_spec);

	public Collection<TouchAreaSpecs> listTouchAreas ();

	void addOption (VisibleComponent raster);

	Collection<VisibleComponent> listOptions ();

}

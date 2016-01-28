package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface ButtonSpecs {

	TouchAreaSpecs newTouchAreaSpecs();

	void addTouchArea(TouchAreaSpecs touch_area_spec);

	public Collection<TouchAreaSpecs> listTouchAreas();

	void setOnPressedRaster(VisibleComponent raster);

	void setOnReleasedRaster(VisibleComponent raster);

	public VisibleComponent getOnPressedRaster();

	public VisibleComponent getOnReleasedRaster();

	String getName();

	void setName(String button_name);

}

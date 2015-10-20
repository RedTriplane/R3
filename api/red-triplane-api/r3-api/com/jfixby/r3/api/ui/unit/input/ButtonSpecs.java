package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;

public interface ButtonSpecs {

	TouchAreaSpecs newTouchAreaSpecs();

	void addTouchArea(TouchAreaSpecs touch_area_spec);

	public Collection<TouchAreaSpecs> listTouchAreas();

	void setName(String button_name);

	// AssetID getID();

	// void setID(AssetID id);

}

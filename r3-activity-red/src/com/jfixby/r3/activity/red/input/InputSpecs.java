
package com.jfixby.r3.activity.red.input;

import com.jfixby.r3.activity.api.input.ButtonSpecs;
import com.jfixby.r3.activity.api.input.CustomInputSpecs;
import com.jfixby.r3.activity.api.input.TouchAreaSpecs;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class InputSpecs implements ButtonSpecs, CustomInputSpecs {

	final List<TouchAreaSpecs> touch_areas = Collections.newList();
	private String button_name;
	private VisibleComponent pressedRaster;
	private VisibleComponent releasedRaster;

	final List<Raster> switchOptions = Collections.newList();

	// private AssetID id;

	@Override
	public void addTouchArea (final TouchAreaSpecs touch_area_spec) {
		this.touch_areas.add(touch_area_spec);
	}

	@Override
	public Collection<TouchAreaSpecs> listTouchAreas () {
		return this.touch_areas;
	}

	@Override
	public void setName (final String button_name) {
		this.button_name = button_name;
	}

	@Override
	public void setOnPressedRaster (final VisibleComponent raster) {
		this.pressedRaster = raster;
	}

	@Override
	public void setOnReleasedRaster (final VisibleComponent raster) {
		this.releasedRaster = raster;
	}

	@Override
	public VisibleComponent getOnPressedRaster () {
		return this.pressedRaster;
	}

	@Override
	public VisibleComponent getOnReleasedRaster () {
		return this.releasedRaster;
	}

	@Override
	public String getName () {
		return this.button_name;
	}

	@Override
	public void addOption (final Raster raster) {
		this.switchOptions.add(raster);
	}

	@Override
	public Collection<Raster> listOptions () {
		return this.switchOptions;
	}

}

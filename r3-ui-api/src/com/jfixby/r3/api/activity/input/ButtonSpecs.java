
package com.jfixby.r3.api.activity.input;

import com.jfixby.r3.api.activity.layer.VisibleComponent;

public interface ButtonSpecs extends InputSpecs {

	void setOnPressedRaster (VisibleComponent raster);

	void setOnReleasedRaster (VisibleComponent raster);

	public VisibleComponent getOnPressedRaster ();

	public VisibleComponent getOnReleasedRaster ();

}

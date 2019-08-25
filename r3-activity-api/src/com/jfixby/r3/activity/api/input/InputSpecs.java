
package com.jfixby.r3.activity.api.input;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.raster.Raster;

public class InputSpecs {
	public String name;

	public final ArrayList<TouchAreaSpecs> touchAreas = new ArrayList<>();
	public final ArrayList<Raster> options = new ArrayList<>();

}

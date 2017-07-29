
package com.jfixby.r3.io.texture.slicer;

import java.io.Serializable;
import java.util.ArrayList;

public class SlicesCompositionsContainer implements Serializable {

	public static final String PACKAGE_FORMAT = "RedTriplane.TiledRaster";

	private static final long serialVersionUID = 6944710849959772300L;
	public ArrayList<SlicesCompositionInfo> content = new ArrayList<SlicesCompositionInfo>();

}

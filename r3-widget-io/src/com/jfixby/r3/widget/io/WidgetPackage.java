
package com.jfixby.r3.widget.io;

import java.util.ArrayList;

public class WidgetPackage implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7799385456223235763L;
	public static final String SCENE2D_PACKAGE_FILE_EXTENSION = "r3-widgets";
	public static final String SCENE2D_PACKAGE_FORMAT = "RedTriplane.Widget";

	public ArrayList<WidgetStructure> structures = new ArrayList<WidgetStructure>();

}

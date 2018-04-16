
package com.jfixby.r3.scene2d.io;

import java.util.ArrayList;

public class Scene2DPackage implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5114380572555020371L;
	public static final String SCENE2D_PACKAGE_FILE_EXTENSION = "r3-scenes";
	public static final String SCENE2D_PACKAGE_FORMAT = "RedTriplane.Scene2D";

	public ArrayList<SceneStructure> structures = new ArrayList<SceneStructure>();

}

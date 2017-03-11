
package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.Vector;

import com.jfixby.rana.api.asset.AssetsGroup;

public class Scene2DPackage implements java.io.Serializable, AssetsGroup {

	/**
	 *
	 */
	private static final long serialVersionUID = 5114380572555020371L;
	public static final String SCENE2D_PACKAGE_FILE_EXTENSION = "r3-scenes";
	public static final String SCENE2D_PACKAGE_FORMAT = "RedTriplane.Scene2D";

	public Vector<SceneStructure> structures = new Vector<SceneStructure>();

	@Override
	public void dispose () {

	}

}

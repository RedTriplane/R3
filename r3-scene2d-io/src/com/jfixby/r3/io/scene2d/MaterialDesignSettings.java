
package com.jfixby.r3.io.scene2d;

import java.io.Serializable;

public class MaterialDesignSettings implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6301217582400990928L;

	public LayerElement background;

	public MaterialDesignStrings strings;

	public DrawerTopBarSettings top_bar = new DrawerTopBarSettings();

	public boolean is_drawer = false;

}

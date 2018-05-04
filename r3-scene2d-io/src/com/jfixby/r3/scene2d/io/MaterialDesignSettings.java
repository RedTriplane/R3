
package com.jfixby.r3.scene2d.io;

import java.io.Serializable;
import java.util.ArrayList;

public class MaterialDesignSettings implements Serializable {

	private static final long serialVersionUID = -6301217582400990928L;

	public LayerElement background;

	public MaterialDesignStrings strings;

	public DrawerTopBarSettings top_bar = new DrawerTopBarSettings();

// public boolean is_drawer = false;

// ? public MaterialDesignSettings material_design_settiongs = new MaterialDesignSettings();
	public ArrayList<MaterialDesingSection> sections = new ArrayList<>();

// public boolean is_buttons_list;

	public ListItem list_item;

	public ListItem new_list_item;
}

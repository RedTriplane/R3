
package com.jfixby.r3.material.api;

import java.util.ArrayList;

import com.jfixby.scarabei.api.names.ID;

public class DrawerSpecs {

	public int top_bar_height;
	public ID top_bar_background;
	public ID left_icon;
	public ID drawer_background;

	public final ArrayList<DrawerSection> sections = new ArrayList();

	public DrawerSection newSection () {
		return new DrawerSection();
	}

}

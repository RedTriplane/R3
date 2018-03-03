
package com.jfixby.r3.activity.api.mtdesign;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.raster.Raster;

public class DrawerSpecs {

	public int top_bar_height;
	public Raster top_bar_background;
	public Raster left_icon;
	public Raster drawer_background;

	public final ArrayList<DrawerSection> sections = new ArrayList();

	public DrawerSection newSection () {
		return new DrawerSection();
	}

}

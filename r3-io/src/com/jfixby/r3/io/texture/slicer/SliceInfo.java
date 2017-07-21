
package com.jfixby.r3.io.texture.slicer;

import java.io.Serializable;

public class SliceInfo implements Serializable {

	private static final long serialVersionUID = -8412343232108501569L;
	public double tile_width;
	public double tile_height;
	public int index_x;
	public int index_y;
	public String postfix;
	public boolean is_empty = false;
}

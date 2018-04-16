
package com.jfixby.r3.scene2d.io;

import java.io.Serializable;

public class ListItem implements Serializable {
	private static final long serialVersionUID = 7715488741165692504L;

	public LayerElement raster;

	public double offset_x = 0d;
	public double offset_y = 0d;

	public double item_width = 0d;
	public double item_height = 0d;

}

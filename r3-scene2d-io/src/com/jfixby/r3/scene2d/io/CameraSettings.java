
package com.jfixby.r3.scene2d.io;

public class CameraSettings implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1119281818345995572L;
	public double position_x;
	public double position_y;
	public double origin_relative_x = 0;
	public double origin_relative_y = 0;
	public double width;
	public double height;

	public MODE mode = null;

	public enum MODE {
		FIT_IN, FILL_SCREEN, FILL_SCREEN_APERTURE_WRAP
	}

}

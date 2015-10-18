package com.jfixby.r3.ext.api.scene2d.srlz;

public class Anchor {

	public String time;
	public double position_x;
	public double position_y;

	@Override
	public String toString() {
		return "Anchor [time=" + time + ", position_x=" + position_x
				+ ", position_y=" + position_y + "]";
	}

	public long time() {
		if (time == null) {
			return 0;
		}
		if ("".equals(time)) {
			return 0;
		}
		return Long.parseLong(time);
	}

}

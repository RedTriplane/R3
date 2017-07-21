package com.jfixby.r3.io.scene2d;

public class Anchor implements java.io.Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 1704791898617585894L;
	public String time;
	public double position_x;
	public double position_y;

	@Override
	public String toString() {
		return "Anchor [time=" + time + ", position_x=" + position_x
				+ ", position_y=" + position_y + "]";
	}

	public int time() {
		if (time == null) {
			return 0;
		}
		if ("".equals(time)) {
			return 0;
		}
		return (int) Long.parseLong(time);
	}

}


package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.Vector;

public class AnimationSettings implements java.io.Serializable {

	/**
	  *
	  */
	private static final long serialVersionUID = 8024873716493729478L;

	public static final long MAX = 1000 * 60 * 60 * 24 * 365 * 365;

	public boolean is_positions_modifyer_animation = false;
	public boolean is_simple_animation = false;

	public boolean is_looped = false;
	public boolean use_spline = false;
	public boolean autostart = false;
	public Vector<Anchor> anchors = new Vector<Anchor>();
	public String single_frame_time = MAX + "";

	public long single_frame_time () {
		if (this.single_frame_time == null) {
			return MAX;
		}
		if ("".equals(this.single_frame_time)) {
			return MAX;
		}
		return Long.parseLong(this.single_frame_time);
	}

}

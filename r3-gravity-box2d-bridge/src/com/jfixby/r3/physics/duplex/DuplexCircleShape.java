
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.CircleShape;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;

public class DuplexCircleShape implements CircleShape {

	private final List<CircleShape> list;

	public DuplexCircleShape (final List<CircleShape> list) {
		this.list = list;
	}

	@Override
	public void setRadius (final double radius) {
		for (final CircleShape s : this.list) {
			s.setRadius(radius);
		}
	}

	@Override
	public double getRadius () {
		Err.throwNotImplementedYet();
		return 0;
	}

}

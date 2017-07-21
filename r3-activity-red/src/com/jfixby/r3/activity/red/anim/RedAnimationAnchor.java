
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.animation.PositionAnchor;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;

public class RedAnimationAnchor implements PositionAnchor {

	final private long timestamp;
	private final CanvasPosition position;

	public RedAnimationAnchor (final long time_stamp) {
		this.timestamp = time_stamp;
		this.position = Geometry.newCanvasPosition();
	}

	@Override
	public long getTime () {
		return this.timestamp;
	}

	@Override
	public String toString () {
		return "RedAnimationAnchor [timestamp=" + this.timestamp + ", rotation=" + this.position().getRotation() + ", x="
			+ this.position().getX() + ", y=" + this.position().getY() + "]";
	}

	@Override
	public CanvasPosition position () {
		return this.position;
	}

}

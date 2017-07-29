
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PolyBodyCircle;

public class RedPolyBodyCircle implements PolyBodyCircle {
	double x, y, radius;

	@Override
	public double getX () {
		return x;
	}

	@Override
	public double getY () {
		return y;
	}

	@Override
	public double getRadius () {
		return radius;
	}

	@Override
	public void setXYR (double x, double y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		RedPolyBodyCircle other = (RedPolyBodyCircle)obj;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius)) return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
		return true;
	}

	@Override
	public void setRadius (double radius) {
		this.radius = radius;
	}

	@Override
	public void setPosition (double x, double y) {
		this.x = x;
		this.y = y;
	}

}

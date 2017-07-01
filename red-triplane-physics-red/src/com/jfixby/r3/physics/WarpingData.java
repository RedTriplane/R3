
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.BodiesSetLocation;
import com.jfixby.r3.api.physics.BodyMass;
import com.jfixby.r3.api.physics.BodyMassController;
import com.jfixby.r3.api.physics.BodyPositionController;
import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.scarabei.api.math.CustomAngle;

public class WarpingData implements BodyPositionController, BodiesSetLocation, BodyMass, BodyMassController {

	double vx;
	double vy;
	final CustomAngle a = Angles.newAngle();
	double x, y;
	double mass;
	double I;

	public Angle getRotation () {
		return a;
	}

	public double getX () {
		return x;
	}

	public double getY () {
		return y;
	}

	public void setRotation (final Angle angle) {
		this.a.setValue(angle);
	}

	public void setPosition (final double x, final double y) {
		this.x = x;
		this.y = y;

	}

	public void setY (final double y) {
		this.y = y;
	}

	public void setX (final double x) {
		this.x = x;
	}

	public double getVelocityX () {

		return this.vx;
	}

	public double getVelocityY () {
		return this.vy;
	}

	public void setVelocity (final double vx, final double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public boolean isEqualTo (final WarpingData avatar_data) {
		return this.equals(avatar_data);
	}

	public void setValues (final WarpingData other) {
		this.x = other.x;
		this.y = other.y;
		this.vx = other.vx;
		this.vy = other.vy;
		this.a.setValue(other.a);
		this.mass = other.mass;
		this.I = other.I;

	}

	@Override
	public void setOriginY (final double y) {
		this.setY(y);
	}

	@Override
	public void setOriginX (double x) {
		this.setX(x);
	}

	@Override
	public double getOriginX () {
		return this.getX();
	}

	@Override
	public double getOriginY () {
		return this.getY();
	}

	@Override
	public void setOriginRotation (final Angle angle) {
		this.setRotation(angle);
	}

	@Override
	public void setOriginPosition (double x, double y) {
		this.setPosition(x, y);
	}

	@Override
	public Angle getOriginRotation () {
		return this.getRotation();
	}

	@Override
	public void setMass (final double mass) {
		this.mass = mass;
	}

	@Override
	public double getMass () {
		return this.mass;
	}

	@Override
	public void setInertia (double I) {
		this.I = I;
	}

	@Override
	public double getInertia () {
		L.d("POWER_LOSS");
		return I;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(I);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		temp = Double.doubleToLongBits(mass);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vx);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vy);
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
		WarpingData other = (WarpingData)obj;
		if (Double.doubleToLongBits(I) != Double.doubleToLongBits(other.I)) return false;
		if (a == null) {
			if (other.a != null) return false;
		} else if (!a.equals(other.a)) return false;
		if (Double.doubleToLongBits(mass) != Double.doubleToLongBits(other.mass)) return false;
		if (Double.doubleToLongBits(vx) != Double.doubleToLongBits(other.vx)) return false;
		if (Double.doubleToLongBits(vy) != Double.doubleToLongBits(other.vy)) return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
		return true;
	}

}

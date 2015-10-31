package com.jfixby.r3.api.unit.components.physics.body;

import com.jfixby.cmns.api.math.Angle;

public interface BodiesSetLocation {

	void setOriginY(final double y);

	void setOriginX(final double x);

	double getOriginX();

	double getOriginY();

	void setOriginRotation(Angle angle);

	void setOriginPosition(final double x, final double y);

	Angle getOriginRotation();

}

package org.box2d.r3.api;

public interface FixtureDef {

	void setDensity(double density);

	void setRestitution(double restitution);

	void setFriction(double friction);

	void setIsSensor(boolean is_sensor);

	Filter filter();

	void setShape(Shape boxPoly);

	double getDensity();
}

package com.jfixby.r3.api.physics;


public interface ForcesApplicator {

	BodyPosition getBodyPosition();

	BodyMass getBodyMassValues();

	void applyForce(double force_x, double force_y);

	boolean isBody(Body body);

}

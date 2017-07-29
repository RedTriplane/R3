package org.box2d.r3.api;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.Float2;

public interface Box2DBody {

	void setLinearVelocity(double vx, double vy);

	void setTransform(double x, double y, double radians);

	void applyForceToCenter(double force_x, double force_y, boolean wake);

	MassData getMassData();

	void setMassData(MassData mass_data);

	Float2 getPosition();

	double getAngle();

	Float2 getLinearVelocity();

	void createFixture(FixtureDef fixture);

	Float2 getWorldCenter();

	void setTransform(Float2 worldCenter, double angle);

	Float2 getLocalPoint(Float2 anchorA);

	boolean isActive();

	Box2DTransform getTransform();

	Collection<Fixture> getFixtureList();

	BodyType getType();

	boolean isAwake();
}

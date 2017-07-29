package org.box2d.r3.api;

import com.jfixby.scarabei.api.collections.Collection;

public interface Box2DWorld {

	Box2DBody createBody(BodyDef boxBodyDef);

	void destroyBody(Box2DBody body);

	void step(double box2d_time_step_delta, int velocityIterations,
			int positionIterations);

	void dispose();

	void setContactListener(ContactListener contactListener);

	void setAutoClearForces(boolean autoclearForces);

	Collection<Box2DBody> listBodies();

	public boolean isFloatPrecision();

	public boolean isDoublePrecision();

	// Box2DWorldRenderer getRenderer();
}

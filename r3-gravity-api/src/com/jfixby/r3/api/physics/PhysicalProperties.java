package com.jfixby.r3.api.physics;

import com.jfixby.scarabei.api.collisions.CollisionCategory;
import com.jfixby.scarabei.api.collisions.CollisionRelations;

public interface PhysicalProperties {

	boolean isSensor();

	void setSensor(boolean is_sensor);

	void setType(BODY_TYPE type);

	public BODY_TYPE getType();

	public float getFriction();

	public float getRestitution();

	public float getDensity();

	public void setFriction(final float value);

	public void setRestitution(final float value);

	public void setDensity(final float value);

	void setCollisionCategory(CollisionCategory group);

	public CollisionRelations getCollisionRelations();
}

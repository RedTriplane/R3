
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.BODY_TYPE;
import com.jfixby.r3.api.physics.PhysicalProperties;
import com.jfixby.scarabei.api.collisions.CollisionCategory;
import com.jfixby.scarabei.api.collisions.CollisionRelations;
import com.jfixby.scarabei.api.collisions.Collisions;

public class PhysicalPropertiesImpl implements PhysicalProperties {

	private BODY_TYPE type = BODY_TYPE.STATIC;
	private boolean is_sensor;
	private float friction = 0.5f; // трение
	private float restitution = 0.5f; // прыгучесть
	private float density = 0.5f;
	private CollisionCategory collisionCategory = Collisions.DEFAULT();
	private final CollisionRelations collisionRelations = Collisions.newCollisionRelations();

	@Override
	public void setType (final BODY_TYPE type) {
		if (type == null) {
			this.type = BODY_TYPE.STATIC;
			return;
		}
		this.type = type;
	}

	@Override
	public BODY_TYPE getType () {
		return this.type;
	}

	@Override
	public boolean isSensor () {
		return this.is_sensor;
	}

	@Override
	public float getFriction () {
		return this.friction;
	}

	@Override
	public float getRestitution () {
		return this.restitution;
	}

	@Override
	public float getDensity () {
		return this.density;
	}

	@Override
	public void setSensor (final boolean is_sensor) {
		this.is_sensor = is_sensor;
	}

	public boolean isEqualTo (final PhysicalPropertiesImpl avatar_gdx_properties) {
		return this.equals(avatar_gdx_properties);
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(this.density);
		result = prime * result + Float.floatToIntBits(this.friction);
		result = prime * result + (this.is_sensor ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(this.restitution);
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final PhysicalPropertiesImpl other = (PhysicalPropertiesImpl)obj;
		if (Float.floatToIntBits(this.density) != Float.floatToIntBits(other.density)) {
			return false;
		}
		if (Float.floatToIntBits(this.friction) != Float.floatToIntBits(other.friction)) {
			return false;
		}
		if (this.is_sensor != other.is_sensor) {
			return false;
		}
		if (Float.floatToIntBits(this.restitution) != Float.floatToIntBits(other.restitution)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		if (!this.collisionCategory.equals(other.collisionCategory)) {
			// L.d("prop!", this);
			// L.d("prop!", other);
			return false;
		}
		if (!this.collisionRelations.equals(other.collisionRelations)) {
			return false;
		}
		return true;
	}

	public void setValues (final PhysicalPropertiesImpl other) {
		this.density = other.density;
		this.friction = other.friction;
		this.is_sensor = other.is_sensor;
		this.restitution = other.restitution;
		this.type = other.type;
		this.collisionCategory = other.collisionCategory;
		this.collisionRelations.setValues(other.collisionRelations);
	}

	@Override
	public void setFriction (final float value) {
		this.friction = value;
	}

	@Override
	public void setRestitution (final float value) {
		this.restitution = value;
	}

	@Override
	public void setDensity (final float value) {
		this.density = value;
	}

	@Override
	public void setCollisionCategory (final CollisionCategory group) {
		this.collisionCategory = group;
		if (this.collisionCategory == null) {
			this.collisionCategory = Collisions.DEFAULT();
		}
	}

	@Override
	public CollisionRelations getCollisionRelations () {
		return this.collisionRelations;
	}

	public short getCategoryBits () {
		return (short)(this.collisionCategory).getBits();
	}

	public short getMaskBits () {
		return (short)this.collisionRelations.getMaskBits();
	};

}

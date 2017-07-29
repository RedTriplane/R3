
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BODY_TYPE;
import com.jfixby.r3.api.physics.PhysicalProperties;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collisions.CollisionCategory;
import com.jfixby.scarabei.api.collisions.CollisionRelations;
import com.jfixby.scarabei.api.err.Err;

public class DuplexPhysicalProperties implements PhysicalProperties {

	private final List<PhysicalProperties> list;
	DuplexCollisionRelations CR;

	public DuplexPhysicalProperties (final List<PhysicalProperties> list) {
		this.list = list;
		final List<CollisionRelations> crcr = Collections.newList();
		for (int i = 0; i < list.size(); i++) {
			final PhysicalProperties pp = list.getElementAt(i);
			final CollisionRelations cr = pp.getCollisionRelations();
			crcr.add(cr);
		}
		this.CR = new DuplexCollisionRelations(crcr);
	}

	@Override
	public boolean isSensor () {
		return this.list.getElementAt(0).isSensor();
	}

	@Override
	public void setSensor (final boolean is_sensor) {
		Err.throwNotImplementedYet();

	}

	@Override
	public void setType (final BODY_TYPE type) {
		for (int i = 0; i < this.list.size(); i++) {
			this.list.getElementAt(i).setType(type);
		}
	}

	@Override
	public BODY_TYPE getType () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public float getFriction () {
		Err.throwNotImplementedYet();
		return 0;
	}

	@Override
	public float getRestitution () {
		Err.throwNotImplementedYet();
		return 0;
	}

	@Override
	public float getDensity () {
		Err.throwNotImplementedYet();
		return 0;
	}

	@Override
	public void setFriction (final float value) {
		Err.throwNotImplementedYet();
	}

	@Override
	public void setRestitution (final float value) {
		Err.throwNotImplementedYet();
	}

	@Override
	public void setDensity (final float value) {
		Err.throwNotImplementedYet();
	}

	@Override
	public void setCollisionCategory (final CollisionCategory group) {
		for (int i = 0; i < this.list.size(); i++) {
			this.list.getElementAt(i).setCollisionCategory(group);
		}
	}

	@Override
	public CollisionRelations getCollisionRelations () {
		return this.CR;
	}

}


package com.jfixby.r3.physics.duplex;

import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collisions.COLLISION_RELATION;
import com.jfixby.scarabei.api.collisions.CollisionCategory;
import com.jfixby.scarabei.api.collisions.CollisionRelations;

public class DuplexCollisionRelations implements CollisionRelations {

	private final List<CollisionRelations> list;

	public DuplexCollisionRelations (final List<CollisionRelations> list) {
		this.list = list;

	}

	@Override
	public void setPolicy (final COLLISION_RELATION relation, final CollisionCategory category) {
		for (int i = 0; i < this.list.size(); i++) {
			this.list.getElementAt(i).setPolicy(relation, category);
		}
	}

	@Override
	public void setValues (final CollisionRelations other) {
		for (int i = 0; i < this.list.size(); i++) {
			this.list.getElementAt(i).setValues(other);
		}
	}

	@Override
	public long getMaskBits () {
		return this.list.getElementAt(0).getMaskBits();
	}

}

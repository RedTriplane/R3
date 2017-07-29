
package com.jfixby.r3.physics.duplex;

import org.box2d.r3.api.Box2DWorld;
import org.box2d.r3.api.Box2DWorldsContainer;

import com.jfixby.r3.api.physics.PhysicsCore;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class DuplexBox2DWorldContainer {

	private final List<Box2DWorld> render = Collections.newList();

	public DuplexBox2DWorldContainer (final List<PhysicsCore> list) {

		for (int i = 0; i < list.size(); i++) {
			final Box2DWorldsContainer e = (Box2DWorldsContainer)list.getElementAt(i);

			this.render.addAll(e.getWorldsForRender());
		}
	}

	public List<Box2DWorld> worlds () {
		return this.render;
	}

}

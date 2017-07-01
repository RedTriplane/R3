
package com.jfixby.r3.physics.duplex;

import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.Box2DWorldsContainer;

import com.jfixby.r3.api.physics.AbstractBody;
import com.jfixby.r3.api.physics.PhysicsCore;
import com.jfixby.r3.api.physics.PhysicsCoreRotor;
import com.jfixby.r3.api.physics.Shape;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;

public class DuplexPhysicsCore implements PhysicsCore, Box2DWorldsContainer {

	private final List<PhysicsCore> list;
	private final DuplexPhysicsCoreRotor rotor;
	private final DuplexBox2DWorldContainer container;

	public DuplexPhysicsCore (final List<PhysicsCore> list) {
		this.list = list;
		this.rotor = new DuplexPhysicsCoreRotor(list);
		this.container = new DuplexBox2DWorldContainer(list);
	}

	@Override
	public PhysicsCoreRotor getRotor () {
		return this.rotor;
	}

	@Override
	public void attachBody (final AbstractBody body) {
		final DuplexBody<Shape> dbody = (DuplexBody<Shape>)body;
		for (int i = 0; i < this.list.size(); i++) {
			this.list.getElementAt(i).attachBody(dbody.list.getElementAt(i));
		}
	}

	@Override
	public void detatchBody (final AbstractBody body) {
		Err.throwNotImplementedYet();
	}

	@Override
	public List<Box2DWorld> getWorldsForRender () {
		return this.container.worlds();
	}

}


package com.jfixby.r3.physics;

import org.box2d.r3.api.Box2DComponent;

import com.jfixby.r3.api.physics.BoxBody;
import com.jfixby.r3.api.physics.CircleBody;
import com.jfixby.r3.api.physics.Physics2DComponent;
import com.jfixby.r3.api.physics.PhysicsCore;
import com.jfixby.r3.api.physics.PhysicsCoreSpecs;
import com.jfixby.r3.api.physics.PolyBody;
import com.jfixby.r3.api.physics.PolyBodySpecs;
import com.jfixby.r3.physics.body.BoxBodyImpl;
import com.jfixby.r3.physics.body.CircleBodyImpl;
import com.jfixby.r3.physics.body.PolyBodyImpl;

public class RedTriplanePhysics implements Physics2DComponent {

	final public Box2DComponent box2d;

	public RedTriplanePhysics (final Box2DComponent box2d) {
		this.box2d = box2d;
	}

	@Override
	public PhysicsCoreSpecs newPhysicsCoreSpecs () {
		return new PhysicsCoreConfig();
	}

	@Override
	public PhysicsCore newPhysicsCore (final PhysicsCoreSpecs core_specs) {
		return new RedPhysicsCore(core_specs, this);
	}

	@Override
	public BoxBody newBoxBody () {
		return new BoxBodyImpl();
	}

	@Override
	public CircleBody newCircleBody () {
		return new CircleBodyImpl();
	}

	@Override
	public PolyBody newPolyBody (final PolyBodySpecs geometry) {
		return new PolyBodyImpl(geometry);
	}

	@Override
	public PolyBodySpecs newPolyBodySpecs () {
		return new RedPolyBodySpecs();
	}

}

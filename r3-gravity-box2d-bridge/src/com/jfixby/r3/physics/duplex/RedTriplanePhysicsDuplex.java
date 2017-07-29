
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BoxBody;
import com.jfixby.r3.api.physics.CircleBody;
import com.jfixby.r3.api.physics.Physics2DComponent;
import com.jfixby.r3.api.physics.PhysicsCore;
import com.jfixby.r3.api.physics.PhysicsCoreSpecs;
import com.jfixby.r3.api.physics.PolyBody;
import com.jfixby.r3.api.physics.PolyBodySpecs;
import com.jfixby.r3.physics.PhysicsCoreConfig;
import com.jfixby.r3.physics.RedPolyBodySpecs;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class RedTriplanePhysicsDuplex implements Physics2DComponent {

	private List<Physics2DComponent> components;

	public RedTriplanePhysicsDuplex (Physics2DComponent... components) {
		this.components = Collections.newList(components);
	}

	@Override
	public PhysicsCoreSpecs newPhysicsCoreSpecs () {
		return new PhysicsCoreConfig();
	}

	@Override
	public PhysicsCore newPhysicsCore (PhysicsCoreSpecs core_specs) {
		List<PhysicsCore> list = Collections.newList();
		for (int i = 0; i < components.size(); i++) {
			PhysicsCore bb = components.getElementAt(i).newPhysicsCore(core_specs);
			list.add(bb);
		}
		return new DuplexPhysicsCore(list);

	}

	@Override
	public BoxBody newBoxBody () {
		List<BoxBody> list = Collections.newList();
		for (int i = 0; i < components.size(); i++) {
			BoxBody bb = components.getElementAt(i).newBoxBody();
			list.add(bb);
		}
		return new DuplexBoxBody(list);
	}

	@Override
	public CircleBody newCircleBody () {
		List<CircleBody> list = Collections.newList();
		for (int i = 0; i < components.size(); i++) {
			CircleBody bb = components.getElementAt(i).newCircleBody();
			list.add(bb);
		}
		return new DuplexCircleBody(list);
	}

	@Override
	public PolyBody newPolyBody (PolyBodySpecs geometry) {
		List<PolyBody> list = Collections.newList();
		for (int i = 0; i < components.size(); i++) {
			PolyBody bb = components.getElementAt(i).newPolyBody(geometry);
			list.add(bb);
		}
		return new DuplexPolyBody(list);
	}

	@Override
	public PolyBodySpecs newPolyBodySpecs () {
		return new RedPolyBodySpecs();
	}

}

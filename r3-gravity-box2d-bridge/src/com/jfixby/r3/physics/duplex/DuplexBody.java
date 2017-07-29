
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BODY_SHAPE_TYPE;
import com.jfixby.r3.api.physics.Body;
import com.jfixby.r3.api.physics.BodyDebugInfo;
import com.jfixby.r3.api.physics.BodyListeners;
import com.jfixby.r3.api.physics.BodyMassController;
import com.jfixby.r3.api.physics.BodyPositionController;
import com.jfixby.r3.api.physics.PhysicalProperties;
import com.jfixby.r3.api.physics.Shape;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;

public class DuplexBody<T extends Shape> implements Body {

	public final List<? extends Body> list;
	private final DuplexPhysicalProperties PP;
	private final DuplexLocation LL;
	private final DuplexBodyListeners MM;
	private final DuplexBodyMass MS;

	// DuplexShape shape;

	public DuplexBody (final List<? extends Body> list) {
		this.list = list;
		final List<PhysicalProperties> pp = Collections.newList();
		final List<BodyPositionController> ll = Collections.newList();
		final List<BodyListeners> mm = Collections.newList();
		final List<BodyMassController> msms = Collections.newList();
		for (int i = 0; i < list.size(); i++) {
			final Body b = list.getElementAt(i);
			final PhysicalProperties p = b.physics();
			final BodyPositionController l = b.location();
			final BodyListeners m = b.listeners();
			final BodyMassController ms = b.mass();
			ll.add(l);
			pp.add(p);
			mm.add(m);
			msms.add(ms);
		}

		this.PP = new DuplexPhysicalProperties(pp);
		this.LL = new DuplexLocation(ll);
		this.MM = new DuplexBodyListeners(mm);
		this.MS = new DuplexBodyMass(msms);

	}

	@Override
	public BODY_SHAPE_TYPE getShapeType () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public PhysicalProperties physics () {
		return this.PP;
	}

	@Override
	public BodyListeners listeners () {
		return this.MM;
	}

	@Override
	public BodyPositionController location () {
		return this.LL;
	}

	@Override
	public BodyDebugInfo debugInfo () {
		return this.list.getElementAt(0).debugInfo();
	}

	@Override
	public BodyMassController mass () {
		return this.MS;
	}

}

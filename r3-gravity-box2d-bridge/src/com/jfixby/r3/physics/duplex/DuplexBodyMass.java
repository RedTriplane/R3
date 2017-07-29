
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BodyMassController;
import com.jfixby.scarabei.api.collections.List;

public class DuplexBodyMass implements BodyMassController {

	private List<BodyMassController> list;

	public DuplexBodyMass (List<BodyMassController> list) {
		this.list = list;
	}

	@Override
	public double getMass () {
		return this.list.getElementAt(0).getMass();
	}

	@Override
	public void setMass (double mass) {
		for (int i = 0; i < this.list.size(); i++) {
			this.list.getElementAt(i).setMass(mass);
		}
	}

	@Override
	public double getInertia () {
		return this.list.getElementAt(0).getInertia();
	}

	@Override
	public void setInertia (double I) {
		for (int i = 0; i < this.list.size(); i++) {
			this.list.getElementAt(i).setInertia(I);
		}
	}
}

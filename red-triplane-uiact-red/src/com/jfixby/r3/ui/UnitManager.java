
package com.jfixby.r3.ui;

import com.jfixby.r3.api.ui.unit.Unit;

public abstract class UnitManager {

	Unit unit;

	public final Unit getUnit () {
		return this.unit;
	}

	public final void setUnit (final Unit unit) {
		// JUtils.checkNull("unit",unit);
		this.unit = unit;
	}

}

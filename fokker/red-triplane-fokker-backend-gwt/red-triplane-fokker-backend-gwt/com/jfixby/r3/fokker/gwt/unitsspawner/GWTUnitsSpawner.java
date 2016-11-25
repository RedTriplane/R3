
package com.jfixby.r3.fokker.gwt.unitsspawner;

import com.jfixby.cmns.api.assets.ID;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.r3.api.ui.Intent;
import com.jfixby.r3.api.ui.unit.DefaultUnit;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.fokker.api.UnitSpawnerComponent;
import com.jfixby.r3.fokker.api.UnitsSpawningException;

public class GWTUnitsSpawner implements UnitSpawnerComponent {

	final private Map<ID, Unit> register = Collections.newMap();

	@Override
	public Unit spawnUnit (final Intent unit_id) throws UnitsSpawningException {
		final Unit unit_class = this.register.get(unit_id);
		this.register.print("register");
		if (unit_class == null) {
			throw new UnitsSpawningException("Unknown unit class: " + unit_id);
		}
		return unit_class;
	}

// @Override
// public Unit spawnUnit (final AssetID unit_id) throws UnitsSpawningException {
// final String string_name = unit_id.toString();
// Class<?> clazz;
// try {
// clazz = Class.forName(string_name);
// final Unit unit_instance = (Unit)clazz.newInstance();
// return unit_instance;
// } catch (final Throwable e) {
// throw new UnitsSpawningException(e);
// }
//
// }

	public void registerUnitClass (final ID unit_id, final DefaultUnit unit_class) {
		this.register.put(unit_id, unit_class);
	}
}

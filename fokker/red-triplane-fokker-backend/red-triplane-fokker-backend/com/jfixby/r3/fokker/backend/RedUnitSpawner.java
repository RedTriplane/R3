
package com.jfixby.r3.fokker.backend;

import com.jfixby.r3.api.ui.Intent;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.fokker.api.UnitSpawnerComponent;
import com.jfixby.r3.fokker.api.UnitsSpawningException;

public class RedUnitSpawner implements UnitSpawnerComponent {
	@Override
	public Unit spawnUnit (final Intent intent) throws UnitsSpawningException {
		final String string_name = intent.getUnitClassID().toString();
		Class<?> clazz;
		try {
			clazz = Class.forName(string_name);
			final Unit unit_instance = (Unit)clazz.newInstance();
			return unit_instance;
		} catch (final Throwable e) {

			throw new UnitsSpawningException(e + "", intent.getStack());
		}

	}
}

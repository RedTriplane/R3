package com.jfixby.r3.fokker.android.unitsspawner;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.fokker.api.UnitSpawnerComponent;

public class AndroidUnitsSpawner implements UnitSpawnerComponent {

	final private Map<AssetID, Unit> register = Collections.newMap();

	@Override
	public Unit spawnUnit(AssetID unit_id) {
		Unit unit_class = register.get(unit_id);
		register.print("register");
		if (unit_class == null) {
			throw new Error("Unknown unit class: " + unit_id);
		}
		return unit_class;
	}

	public void registerUnitClass(AssetID unit_id, Unit unit_class) {
		this.register.put(unit_id, unit_class);
	}
}

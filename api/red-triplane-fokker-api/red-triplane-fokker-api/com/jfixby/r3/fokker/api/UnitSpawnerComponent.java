
package com.jfixby.r3.fokker.api;

import com.jfixby.r3.api.ui.Intent;
import com.jfixby.r3.api.ui.unit.DefaultUnit;

public interface UnitSpawnerComponent {

	public DefaultUnit spawnUnit (Intent unit_class_name) throws UnitsSpawningException;

}

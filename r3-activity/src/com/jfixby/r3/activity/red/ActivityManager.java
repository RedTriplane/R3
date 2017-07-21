
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.spawn.Intent;
import com.jfixby.r3.engine.api.exe.EngineState;

public class ActivityManager {
	private ActivityContainer current_unit_container;

	public void loadNext (final Intent intent) {
		if (this.current_unit_container != null) {
			this.current_unit_container.doDispose();
			this.current_unit_container = null;
		}
		final ActivityContainerProperties unit_container_propertis = new ActivityContainerProperties();
		unit_container_propertis.setIntent(intent);
		this.current_unit_container = ActivityContainer.newActivityContainer(unit_container_propertis);
		this.current_unit_container.doDeploy();

	}

	public boolean isIdle () {
		return this.current_unit_container == null;
	}

	public void suspend () {
		this.current_unit_container.doSuspend();
	}

	public void resume () {
		this.current_unit_container.doResume();
	}

	public void update (final EngineState engine_state) {

		this.current_unit_container.doUpdate(engine_state);
	}

	public void render (final EngineState engine_state) {
		this.current_unit_container.doRender(engine_state);
	}

}

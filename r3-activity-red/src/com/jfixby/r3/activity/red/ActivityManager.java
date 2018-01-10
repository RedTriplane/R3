
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivitySpawner;
import com.jfixby.r3.activity.api.spawn.ActivitySpawningException;
import com.jfixby.r3.engine.api.exe.EngineState;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.promise.Future;

public class ActivityManager {
	private ActivityContainer current_unit_container;
	private final Future<Activity, Void> deployNextActiviy = new Future<Activity, Void>() {

		@Override
		public Void deliver (final Activity unit) throws Throwable {

			return null;
		}

	};

	public void deployActivity (final Activity unit) {
		if (ActivityManager.this.current_unit_container != null) {
			ActivityManager.this.current_unit_container.doDispose();
			ActivityManager.this.current_unit_container = null;
		}
		ActivityManager.this.current_unit_container = new ActivityContainer(unit);
		ActivityManager.this.current_unit_container.doDeploy();
	}

	public void pushNextActivity (final ID unitID) throws ActivitySpawningException {
		final Activity promise = ActivitySpawner.spawnActivity(unitID);
		ActivityManager.this.deployActivity(promise);
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

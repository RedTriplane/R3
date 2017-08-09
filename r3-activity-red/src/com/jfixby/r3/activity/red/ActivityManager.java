
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivitySpawner;
import com.jfixby.r3.engine.api.exe.EngineState;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.PromiseSpecs;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class ActivityManager {
	private ActivityContainer current_unit_container;
	private final Future<Activity, Void> deployNextActiviy = new Future<Activity, Void>() {

		@Override
		public Void deliver (final Activity unit) throws Throwable {
			ActivityManager.this.deployActivity(unit);
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

	public void pushNextActivity (final ID unitID) {
		final Promise<Activity> promise = ActivitySpawner.spawnActivity(unitID);
		final PromiseSpecs specs = TaskManager.newPromiseSpecs();
		specs.name = "pushNextActivity(" + unitID + ")";
		specs.executeInMainThread = true;
		promise.then(specs, this.deployNextActiviy);
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

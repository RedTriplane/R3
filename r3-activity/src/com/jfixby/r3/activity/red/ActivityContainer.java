
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivityListener;
import com.jfixby.r3.activity.api.spawn.ActivitySpawner;
import com.jfixby.r3.activity.api.spawn.ActivitySpawningException;
import com.jfixby.r3.activity.api.spawn.Intent;
import com.jfixby.r3.api.exe.EngineState;
import com.jfixby.r3.api.screen.Screen;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.err.Err;

public class ActivityContainer {

	public static ActivityContainer newActivityContainer (final ActivityContainerProperties unit_container_propertis) {
		return new ActivityContainer(unit_container_propertis);
	}

	private final Intent intent;
	private Activity unit;
	private final RedActivityExecutor unit_executor;

	public ActivityContainer (final ActivityContainerProperties unit_container_propertis) {
		this.intent = unit_container_propertis.getIntent();
		this.unit_executor = new RedActivityExecutor(this);
	}

	public void doDispose () {
		this.unit.onPause();
		this.inspector.onPause();
		LoadedAssets.releaseAllAssets((AssetsConsumer)this.unit_executor.getComponentsFactory());
		this.unit.onDestroy();
		this.inspector.onDestroy();

	}

	final RedActivityStateInspector inspector = new RedActivityStateInspector();

	public void doDeploy () {
		try {
			this.unit = ActivitySpawner.spawnActivity(this.intent);
		} catch (final ActivitySpawningException e) {
			e.printStackTrace();
			Err.reportError(e);
		}
		final ActivityListener listener = this.intent.getActivityListener();
		this.inspector.setListener(listener);
		this.inspector.setActivity(this.unit);
		this.inspector.onInit();
		this.unit.onCreate(this.unit_executor);
		this.inspector.onCreate();
		this.unit.onStart();
		this.inspector.onStart();
	}

	public void doExecute (final EngineState engine_state) {

	}

	public void doSuspend () {
		this.unit_executor.suspend();
		this.unit.onPause();
		this.inspector.onPause();
	}

	public void doResume () {
		this.unit_executor.resume();
		this.unit.onResume();
		this.inspector.onResume();
	}

	public void doUpdate (final EngineState engine_state) {
		this.unit_executor.update(engine_state);
	}

	public void doRender (final EngineState engine_state) {
		if (!Screen.isInValidState()) {
			return;
		}
		this.unit_executor.render(engine_state);
	}

	public String getActivityDebugName () {
		return this.unit.toString();
	}

}

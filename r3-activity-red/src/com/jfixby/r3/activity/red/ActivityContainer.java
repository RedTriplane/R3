
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.engine.api.exe.EngineState;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.debug.Debug;

public class ActivityContainer {

	private final Activity unit;
	private final RedActivityManager unit_executor;

	public ActivityContainer (final Activity unit) {
		this.unit = Debug.checkNull("Activity", unit);

		this.unit_executor = new RedActivityManager(this);
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
		this.unit_executor.renderAudio(engine_state);
		this.unit_executor.renderGraphics(engine_state);
	}

	public String getActivityDebugName () {
		return this.unit.toString();
	}

	public Activity getActivity () {
		return this.unit;
	}

}

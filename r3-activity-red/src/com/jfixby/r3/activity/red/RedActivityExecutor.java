
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.spawn.ActivityMachine;
import com.jfixby.r3.activity.api.spawn.ActivityMachineComponent;
import com.jfixby.r3.activity.api.spawn.Intent;
import com.jfixby.r3.activity.api.spawn.IntentStack;
import com.jfixby.r3.engine.api.EngineAssembler;
import com.jfixby.r3.engine.api.RedTriplane;
import com.jfixby.r3.engine.api.exe.EngineExecutor;
import com.jfixby.r3.engine.api.exe.EngineState;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Queue;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.api.taskman.SysExecutor;
import com.jfixby.scarabei.api.ver.Version;

public class RedActivityExecutor implements ActivityMachineComponent, EngineExecutor {

	final private EngineAssembler engine_assembler;
	private Queue<IntentContainer> queue;
	ActivityManager units_manager;

	public RedActivityExecutor (final EngineAssembler engine_assembler) {
		this.engine_assembler = engine_assembler;
	}

	@Override
	public void doDeploy () {
		this.queue = Collections.newQueue();
		ActivityMachine.installComponent(this);

		if (this.engine_assembler != null) {
			this.engine_assembler.assembleEngine();
		}

		final String applicationPackageName = (SystemSettings.getStringParameter(Version.Tags.PackageName));
		final String versionCode = SystemSettings.getStringParameter(Version.Tags.VersionCode);
		final String versionName = SystemSettings.getStringParameter(Version.Tags.VersionName);

		Debug.checkNull("SystemSettings :: " + Version.Tags.PackageName, applicationPackageName);
		Debug.checkEmpty("SystemSettings :: " + Version.Tags.PackageName, applicationPackageName);

		Debug.checkNull("SystemSettings :: " + Version.Tags.VersionCode, versionCode);
		Debug.checkEmpty("SystemSettings :: " + Version.Tags.VersionCode, versionCode);

		Debug.checkNull("SystemSettings :: " + Version.Tags.VersionName, versionName);
		Debug.checkEmpty("SystemSettings :: " + Version.Tags.VersionName, versionName);

		L.d("------[RedTriplane Engine Start]---------------------------------------------------------");
		L.d("            version - " + RedTriplane.VERSION().getName());
		L.d("           build id - " + RedTriplane.VERSION().getBuildID());
		L.d("           homepage - " + RedTriplane.VERSION().getHomePage());
		L.d();
		L.d("        application - " + applicationPackageName);
		L.d("            version - " + versionName);
		L.d("            v. code - " + versionCode);
		L.d();
		L.d("            starter - " + RedTriplane.getGameStarter());
		L.d();
		RedTriplane.VERSION().printDetails("            v. code".length());
		L.d("-----------------------------------------------------------------------------------------");

		// Sys.exit();

		this.units_manager = new ActivityManager();

		SysExecutor.onSystemStart();

// RenderMachine.init();

		final ID starter = RedTriplane.getGameStarter();
// if (starter == null) {
// Err.reportError("RedTriplane.GameStarter is not set");
// }
		final Intent intent = ActivityMachine.newIntent(starter);
		this.nextActivity(intent);
// L.d("Screen dimensions", Screen.getScreenDimensions());
		RenderMachine.component().deploy();
	}

	@Override
	public void doDispose () {
		if (this.units_manager.isIdle()) {
			return;
		}
	}

	@Override
	public void doPause () {
		if (this.units_manager.isIdle()) {
			return;
		}
		this.units_manager.suspend();
	}

	@Override
	public void doRender (final EngineState engine_state) {
// if (this.queue.hasMore()) {
// final Intent intent = this.queue.pop().intent();
// this.units_namager.loadNext(intent);
// return;
// }
		if (this.units_manager.isIdle()) {
			return;
		}
		this.units_manager.render(engine_state);
	}

	@Override
	public void doResume () {
		if (this.units_manager.isIdle()) {
			return;
		}
		this.units_manager.resume();
	}

	@Override
	public void doUpdate (final EngineState engine_state) {
		if (this.queue.hasMore()) {
			final Intent intent = this.queue.dequeue().intent();
			this.units_manager.loadNext(intent);
			return;
		}
		if (this.units_manager.isIdle()) {
			return;
		}

		this.units_manager.update(engine_state);

	}

	@Override
	public Intent newIntent (final ID intent_id) {
		final IntentStack stack = new IntentStack();
		return new RedActivityMachineIntent(intent_id, stack);
	}

	@Override
	public void nextActivity (final Intent intent) {
		this.queue.enqueue(new IntentContainer(intent));
	}

}

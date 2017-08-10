
package com.jfixby.r3.fokker.adaptor;

import com.badlogic.gdx.Gdx;
import com.jfixby.r3.engine.api.exe.EngineExecutor;
import com.jfixby.r3.engine.api.exe.EngineState;
import com.jfixby.r3.engine.api.exe.InputQueue;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.fokker.api.FokkerThread;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.ui.UIThread;
import com.jfixby.scarabei.api.util.StateSwitcher;
import com.jfixby.scarabei.api.util.Utils;

public class GdxAdaptor implements com.badlogic.gdx.ApplicationListener, EngineState {

	@Override
	public String toString () {
		return "EngineState [cycle=" + this.cycle + ", viewport_state=" + this.viewport_state + ", input_adaptor="
			+ this.input_adaptor + "]";
	}

	private final EngineExecutor executor;
	long cycle = 0;
	final GdxAdaptorViewportState viewport_state = new GdxAdaptorViewportState(this);
	final GdxInputAdaptor input_adaptor = new GdxInputAdaptor(this);

// private FokkerRenderMachine fokker_render_machine;
	private final StateSwitcher<ENGINE_STATE> state;
	private DebugTimer timer;
	private final GdxThread fokkerThread = new GdxThread();

	public GdxAdaptor (final EngineExecutor executor) {
		this.executor = executor != null ? executor : new DefaultExecutor();
		this.state = Utils.newStateSwitcher(ENGINE_STATE.NEW);
	}

	public com.badlogic.gdx.ApplicationListener getGDXApplicationListener () {
		return this;
	}

	@Override
	public void create () {
		UIThread.registerUIThread();
		FokkerThread.installComponent(this.fokkerThread);
	}

	@Override
	public void resize (final int width, final int height) {
		this.viewport_state.updateViewport(width, height);
		this.viewport_state.flagNeedUpdate();
	}

	@Override
	public void render () {
		if (this.state.currentState() == ENGINE_STATE.OK) {
			this.do_full_cycle();
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.RESTORING) {
			this.do_resume();
			this.setState(ENGINE_STATE.OK);
			this.do_full_cycle();
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.NEW) {
			this.deploy();
			this.setState(ENGINE_STATE.OK);
			this.do_full_cycle();
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.BROKEN) {
			// this.setState(ENGINE_STATE.RESTORING);
			// this.do_resume();
			// this.setState(ENGINE_STATE.OK);
			// this.do_cycle();

			this.do_only_update_cycle();

			return;
		}
	}

	private void do_only_update_cycle () {
		this.fokkerThread.pushTasks();
		this.input_adaptor.flush();
		this.viewport_state.checkNeedUpdateFlag(this.cycle);
		this.executor.doUpdate(this);
		this.cycle++;
	}

	private void do_resume () {
		// viewport_state.flagNeedUpdate();
		this.executor.doResume();
		this.input_adaptor.enable();
	}

	private void do_full_cycle () {
		// this.timer.reset();
		this.fokkerThread.pushTasks();
		// this.timer.printTime("SysExecutor.pushTasks()");
		this.viewport_state.checkNeedUpdateFlag(this.cycle);
		// this.timer.reset();
		this.executor.doUpdate(this);
		// this.timer.printTime("doUpdate()");
		// this.timer.reset();
		this.executor.doRender(this);
		// this.timer.printTime("doRender()");
		this.input_adaptor.flush();
		this.cycle++;
	}

	private void deploy () {
		this.state.expectState(ENGINE_STATE.NEW);
		Screen.installComponent(this.viewport_state);
// fokker_render_machine = new FokkerRenderMachine();

// ------------------------------
		this.executor.doDeploy();
		Gdx.input.setInputProcessor(this.input_adaptor);
		this.input_adaptor.deploy();
		this.input_adaptor.enable();
		this.timer = Debug.newTimer();

	}

	private void do_pause () {
		this.executor.doPause();
		this.input_adaptor.disable();
	}

	@Override
	public void pause () {
		if (this.state.currentState() == ENGINE_STATE.OK) {
			this.do_pause();
			this.setState(ENGINE_STATE.BROKEN);
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.NEW) {
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.RESTORING) {
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.BROKEN) {
			return;
		}
	}

	private void setState (final ENGINE_STATE next_state) {
		L.d("ENGINE: " + this.state, next_state);
		this.state.switchState(next_state);
	}

	@Override
	public void resume () {
		if (this.state.currentState() == ENGINE_STATE.BROKEN) {
			this.setState(ENGINE_STATE.RESTORING);
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.OK) {
			this.do_pause();
			this.setState(ENGINE_STATE.BROKEN);
			this.setState(ENGINE_STATE.RESTORING);
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.RESTORING) {
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.NEW) {
			return;
		}
	}

	@Override
	public void dispose () {
		this.executor.doDispose();
	}

	@Override
	public long getCurrentCycleNumber () {
		return this.cycle;
	}

	@Override
	public InputQueue getInputQueue () {
		return this.input_adaptor;
	}

	@Override
	public boolean isBroken () {
		return this.state.currentState() == ENGINE_STATE.BROKEN;
	}
}

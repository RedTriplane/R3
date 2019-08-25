
package com.jfixby.r3.activity.red.act;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.act.UIAction;
import com.jfixby.r3.activity.api.act.UIEventsManagerComponent;
import com.jfixby.r3.activity.api.spawn.ActivitySpawningException;
import com.jfixby.r3.activity.red.ActivityManager;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

public class RedUIManager implements UIEventsManagerComponent {

	private final ActivityManager units_manager;

	public RedUIManager (final ActivityManager units_manager) {
		super();
		this.units_manager = units_manager;
	}

	final UIEventsQueue events_queue = new UIEventsQueue();

	@Override
	public void startEventsMachine () {
		this.events_queue.startProcessing();
	}

	@Override
	public void showLoadingScreen (final ID loader_unit_id, final boolean fadedOut) {
// final ShowLoadingScreenEvent event = new ShowLoadingScreenEvent(this, loader_unit_id, fadedOut);
// this.events_queue.put(event);
		Err.throwNotImplementedYet();
	}

	@Override
	public void pushFadeOut (final long period) {
		final FadeEvent event = new FadeEvent(period, FadeEvent.FADE_MODE.FADE_OUT, this);
		this.events_queue.put(event);
	}

	@Override
	public void loadUnit (final ID unit_id) {
		final LoadUnitEvent event = new LoadUnitEvent(this, unit_id);
		this.events_queue.put(event);
// Err.throwNotImplementedYet();
	}

	@Override
	public void pushFadeIn (final long period) {
		final FadeEvent event = new FadeEvent(period, FadeEvent.FADE_MODE.FADE_IN, this);
		this.events_queue.put(event);
	}

	@Override
	public void pushWait (final long time) {
		final WaitEvent event = new WaitEvent(time, this);
		this.events_queue.put(event);
	}

	@Override
	public void allowUserInput () {
		final SwitchUserInputEvent event = new SwitchUserInputEvent(this, SwitchUserInputEvent.ALLOW);
		this.events_queue.put(event);
	}

	@Override
	public void disableUserInput () {
		final SwitchUserInputEvent event = new SwitchUserInputEvent(this, SwitchUserInputEvent.DISABLE);
		this.events_queue.put(event);
	}

	@Override
	public <T> void pushAction (final UIAction<T> action) {
		final RedActionTask<T> event = new RedActionTask<>(this, action);
		this.events_queue.put(event);
// return event.getStatus();
	}

	public Activity getActivity () {
		return this.units_manager.getActivity();
	}

	public void loadNextActivity (final ID unit_id) throws ActivitySpawningException {
		this.units_manager.pushNextActivity(unit_id);
	}

}

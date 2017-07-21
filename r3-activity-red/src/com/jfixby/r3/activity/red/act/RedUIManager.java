
package com.jfixby.r3.activity.red.act;

import com.jfixby.r3.activity.api.act.AnimationsMachine;
import com.jfixby.r3.activity.api.act.UIAction;
import com.jfixby.r3.activity.api.act.UIEventsManagerComponent;
import com.jfixby.scarabei.api.assets.ID;

public class RedUIManager implements UIEventsManagerComponent {

	public RedUIManager () {
		super();
	}

	final LoaderManager loder_unit_manager = new LoaderManager();

	public LoaderManager getLoader () {
		return this.loder_unit_manager;
	}

	final UIEventsQueue events_queue = new UIEventsQueue();

	final GameActivityManager game_unit_manager = new GameActivityManager();
	ActivityManager current = null;

	@Override
	public void startEventsMachine () {
		this.events_queue.startProcessing();
	}

	@Override
	public void showLoadingScreen (final ID loader_unit_id, final boolean fadedOut) {
		final ShowLoadingScreenEvent event = new ShowLoadingScreenEvent(this, loader_unit_id, fadedOut);
		this.events_queue.put(event);
	}

	@Override
	public void pushFadeOut (final long period) {
		final FadeEvent event = new FadeEvent(period, FadeEvent.FADE_MODE.FADE_OUT, this);
		this.events_queue.put(event);
	}

	@Override
	public void switchToUI (final ID game_ui_unit_id) {
		final ShowGameUIScreenEvent event = new ShowGameUIScreenEvent(this, game_ui_unit_id);
		this.events_queue.put(event);
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

	public ActivityManager getCurrent () {
		return (this.current);
	}

	public void setCurrent (final ActivityManager next) {
		this.current = next;
	}

	public GameActivityManager getGameActivityContainer () {
		return this.game_unit_manager;
	}

	@Override
	public AnimationsMachine newAnimationsMachine () {
		return new RedAnimationsMachine();
	}

	@Override
	public <T> void pushAction (final UIAction<T> action) {
		final RedActionTask<T> event = new RedActionTask<T>(this, action);
		this.events_queue.put(event);
// return event.getStatus();
	}

}

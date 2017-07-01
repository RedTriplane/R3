
package com.jfixby.r3.ui;

import com.jfixby.r3.uiact.AnimationsMachine;
import com.jfixby.r3.uiact.LoadTask;
import com.jfixby.r3.uiact.UIAction;
import com.jfixby.r3.uiact.UIActionStatus;
import com.jfixby.r3.uiact.UIEventsManagerComponent;
import com.jfixby.r3.uiact.UILoaderListener;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;

public class RedUIManager implements UIEventsManagerComponent {

	public RedUIManager () {
		super();
	}

	final UIEventsQueue events_queue = new UIEventsQueue();

	final LoaderManager loder_unit_manager = new LoaderManager();
	final GameUnitManager game_unit_manager = new GameUnitManager();
	UnitManager current = null;

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
	public LoadTask prepareLoadUITask (final Collection<ID> asetsToLoad) {
		final RedLoadTask load_task = new RedLoadTask(asetsToLoad);
		return load_task;
	}

	@Override
	public LoadTask prepareLoadUITask (final ID... asetsToLoad) {
		return this.prepareLoadUITask(Collections.newList(asetsToLoad));
	}

	@Override
	public void pushTaskToLoader (final LoadTask task, final UILoaderListener ui_loader_listener) {
		final LoadTaskEvent event = new LoadTaskEvent(task, ui_loader_listener, this);
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

	public LoaderManager getLoader () {
		return this.loder_unit_manager;
	}

	public UnitManager getCurrent () {
		return (this.current);
	}

	public void setCurrent (final UnitManager next) {
		this.current = next;
	}

	public GameUnitManager getGameUnitContainer () {
		return this.game_unit_manager;
	}

	@Override
	public void pushLoadAssetsTask (final Collection<ID> newList, final UILoaderListener loader_listener) {
		final RedLoadTask task = new RedLoadTask(newList);
		this.pushTaskToLoader(task, loader_listener);

	}

	@Override
	public AnimationsMachine newAnimationsMachine () {
		return new RedAnimationsMachine();
	}

	@Override
	public <T> UIActionStatus pushAction (final UIAction<T> action) {
		final RedActionTask<T> event = new RedActionTask<T>(this, action);
		this.events_queue.put(event);
		return event.getStatus();
	}

}

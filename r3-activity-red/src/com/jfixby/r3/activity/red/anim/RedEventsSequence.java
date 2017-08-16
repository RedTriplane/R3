
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.animation.EventsGroupSpecs;
import com.jfixby.r3.activity.api.animation.EventsSequence;
import com.jfixby.r3.activity.api.animation.EventsSequenceSpecs;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.update.ActivityClocks;
import com.jfixby.r3.activity.api.update.OnUpdateListener;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Queue;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.util.StateSwitcher;
import com.jfixby.scarabei.api.util.Utils;

public class RedEventsSequence implements EventsSequence, LayerBasedComponent {

	private final boolean is_looped;

	final StateSwitcher<ANIMATION_STATE> state = Utils.newStateSwitcher(ANIMATION_STATE.VOID);

	final Queue<RedEventsGroup> queue = Collections.newQueue();

	@Override
	public String toString () {
		return "EventsSequence[" + "" + "]  ";
	}

	final List<RedEventsGroup> groups = Collections.newList();

	private final RedComponentsFactory master;

	private final RedLayer root;

	private final OnUpdateListener update_listener = new OnUpdateListener() {

		@Override
		public void onUpdate (final ActivityClocks unit_clock) {
			RedEventsSequence.this.update();
		}

	};

	private RedEventsGroup current_events_group;

	private void update () {
		if (this.state.currentState() == ANIMATION_STATE.DONE) {
			return;
		}
		if (this.state.currentState() == ANIMATION_STATE.VOID) {
			return;
		}
		if (this.current_events_group == null) {
			if (this.queue.size() == 0) {
				this.state.switchState(ANIMATION_STATE.DONE);
				return;
			}
			this.current_events_group = this.queue.dequeue();
		}

		if (this.queue.size() == 0) {
			this.state.switchState(ANIMATION_STATE.DONE);
			return;
		}
		this.current_events_group = this.queue.dequeue();

	}

	public RedEventsSequence (final EventsSequenceSpecs specs, final RedComponentsFactory master) {
		this.master = master;
		this.root = master.newLayer();

		this.root.attachComponent(this.update_listener);
		this.is_looped = specs.isLooped();
		final Collection<EventsGroupSpecs> groups = specs.listGroups();
		for (int i = 0; i < groups.size(); i++) {
			final EventsGroupSpecs group_specs = groups.getElementAt(i);
			final RedEventsGroup group = this.spawn(group_specs);
			this.groups.add(group);
		}
		if (this.groups.size() == 0) {
			Err.reportError("No events found!");
		}

	}

	private RedEventsGroup spawn (final EventsGroupSpecs group_specs) {
		final RedEventsGroup g = new RedEventsGroup();
		final Collection<ID> events = group_specs.listEvents();
		g.addEvents(events);
		return g;
	}

	// @Override
	// public void startAnimation() {
	// this.queue.clear();
	// this.queue.putAll(groups);
	// this.current_events_group = queue.get();
	// this.current_events_group.startAll();
	// }
	//
	// @Override
	// public void stopAnimation() {
	// queue.clear();
	// for (RedEventsGroup g : this.groups) {
	// g.stopAll();
	// }
	// this.current_events_group = null;
	// }
	//
	// @Override
	// public boolean isAnimationDone() {
	// return queue.size() > 0;
	// }

	@Override
	public boolean isLooped () {
		return this.is_looped;
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

}

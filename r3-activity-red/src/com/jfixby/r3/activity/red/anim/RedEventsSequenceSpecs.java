
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.animation.EventsGroupSpecs;
import com.jfixby.r3.activity.api.animation.EventsSequenceSpecs;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.time.TimeStream;

public class RedEventsSequenceSpecs implements EventsSequenceSpecs {

	private boolean is_looped;
	private TimeStream clock;

	@Override
	public void setTimeStream (TimeStream clock) {
		this.clock = clock;
	}

	@Override
	public TimeStream getTimeStream () {
		return this.clock;
	}

	@Override
	public void setIsLooped (boolean is_looped) {
		this.is_looped = is_looped;
	}

	@Override
	public boolean isLooped () {
		return this.is_looped;
	}

	List<EventsGroupSpecs> e_groups = Collections.newList();

	@Override
	public void addGroup (EventsGroupSpecs group_specs) {
		e_groups.add(group_specs);
	}

	@Override
	public Collection<EventsGroupSpecs> listGroups () {
		return e_groups;
	}

}

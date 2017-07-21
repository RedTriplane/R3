package com.jfixby.r3.api.activity.animation;

import com.jfixby.scarabei.api.collections.Collection;

public interface EventsSequenceSpecs extends AnimationSpecs {

	void addGroup(EventsGroupSpecs group_specs);

	public Collection<EventsGroupSpecs> listGroups();

}

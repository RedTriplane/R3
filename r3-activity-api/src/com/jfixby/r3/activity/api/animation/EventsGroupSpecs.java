package com.jfixby.r3.activity.api.animation;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.names.ID;

public interface EventsGroupSpecs {

	void addEventID(ID event_id);

	Collection<ID> listEvents();

}

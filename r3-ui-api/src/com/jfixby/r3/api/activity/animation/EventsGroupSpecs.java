package com.jfixby.r3.api.activity.animation;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface EventsGroupSpecs {

	void addEventID(ID event_id);

	Collection<ID> listEvents();

}

package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.assets.ID;
import com.jfixby.cmns.api.collections.Collection;

public interface EventsGroupSpecs {

	void addEventID(ID event_id);

	Collection<ID> listEvents();

}

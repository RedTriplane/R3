package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;

public interface EventsGroupSpecs {

	void addEventID(AssetID event_id);

	Collection<AssetID> listEvents();

}

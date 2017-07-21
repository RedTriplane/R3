
package com.jfixby.r3.activity.red.anim;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class RedEventsGroup {
	final List<ID> list = Collections.newList();

	public void addEvents (Collection<ID> events) {
		this.list.addAll(events);

	}

	public void startAll () {
	}

	public void stopAll () {
	}

}

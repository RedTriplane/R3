
package com.jfixby.r3.activity.red.act;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Queue;

public class RedAnimationEventsQueue {
	final Queue<RedAnimationsMachineEvent> queue = Collections.newQueue();

	public void put (RedAnimationsMachineEvent event) {
		queue.enqueue(event);
	}

	public boolean hasMore () {
		return queue.hasMore();
	}

	public RedAnimationsMachineEvent pop () {
		return queue.dequeue();
	}

}

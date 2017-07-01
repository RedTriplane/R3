
package com.jfixby.r3.ui;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Queue;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.taskman.Job;
import com.jfixby.scarabei.api.taskman.Task;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class UIEventsQueue {

	final Queue<UIEvent> queue = Collections.newQueue();
	UIEvent current_event = null;
	static private long SLEEP_TIME = 100;

	final private Job check_and_execute_queue_job = new Job() {

		@Override
		public void doStart () throws Throwable {
		}

		@Override
		public void doPush () throws Throwable {
			if (UIEventsQueue.this.current_event != null) {
				if (UIEventsQueue.this.current_event.isOver()) {
					UIEventsQueue.this.current_event = null;
				}
			}

			if (UIEventsQueue.this.current_event == null) {
				if (UIEventsQueue.this.queue.hasMore()) {
					UIEventsQueue.this.current_event = UIEventsQueue.this.queue.dequeue();
					UIEventsQueue.this.current_event.go();
				}
			}

		}

		@Override
		public boolean isDone () {
			return false;
		}
	};
	private Task queue_execution_task;

	public void put (final UIEvent event) {
		Debug.checkNull(event);
		this.queue.enqueue(event);

	}

	public void startProcessing () {
		this.queue_execution_task = TaskManager.newTask("TintoUIEventsQueue", this.check_and_execute_queue_job);
	}

}

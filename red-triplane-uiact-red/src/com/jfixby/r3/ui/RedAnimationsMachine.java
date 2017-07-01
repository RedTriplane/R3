
package com.jfixby.r3.ui;

import com.jfixby.r3.uiact.AnimationsMachine;
import com.jfixby.scarabei.api.taskman.Job;
import com.jfixby.scarabei.api.taskman.Task;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class RedAnimationsMachine implements AnimationsMachine {
	final RedAnimationEventsQueue queue = new RedAnimationEventsQueue();
	boolean is_paused = true;
	RedAnimationsMachineEvent current_event = null;
	private Task queue_processing_task;

	@Override
	public void resume () {
		is_paused = false;
	}

	@Override
	public void pause () {
		is_paused = true;
	}

	@Override
	public void activate () {
		queue_processing_task = TaskManager.newTask("AnimationsMachine", queue_processing_job);
	}

	final Job queue_processing_job = new Job() {

		@Override
		public void doStart () throws Throwable {
		}

		@Override
		public void doPush () throws Throwable {
			if (is_paused) {
				// L.d("queue_processing_job", "is paused");
				return;
			}

			if (current_event != null) {
				if (current_event.isOver()) {
					current_event = null;
				}
			}

			if (current_event == null) {
				if (queue.hasMore()) {
					current_event = queue.pop();
					current_event.go();
				}
			}
		}

		@Override
		public boolean isDone () {
			return false;
		}
	};

}

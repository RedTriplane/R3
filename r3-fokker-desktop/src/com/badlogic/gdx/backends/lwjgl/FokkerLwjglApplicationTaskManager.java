
package com.badlogic.gdx.backends.lwjgl;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.taskman.Job;
import com.jfixby.scarabei.api.taskman.SimpleProgress;
import com.jfixby.scarabei.api.taskman.Task;
import com.jfixby.scarabei.api.taskman.TaskManagerComponent;
import com.jfixby.scarabei.api.taskman.TaskSpecs;

public class FokkerLwjglApplicationTaskManager implements TaskManagerComponent {
	public FokkerLwjglApplicationTaskManager () {
		Err.throwNotImplementedYet();
	}

	@Override
	public Task newTask (final String task_name, final Job job) {
		return null;
	}

	@Override
	public Task newTask (final String task_name, final Job... jobs_sequence) {
		return null;
	}

	@Override
	public Task newTask (final String task_name, final Collection<Job> jobs_sequence) {
		return null;
	}

	@Override
	public Task newTask (final Job job) {
		return null;
	}

	@Override
	public Task newTask (final Job... jobs_sequence) {
		return null;
	}

	@Override
	public Task newTask (final Collection<Job> jobs_sequence) {
		return null;
	}

	@Override
	public TaskSpecs newTaskSpecs () {
		return null;
	}

	@Override
	public Task newTask (final TaskSpecs specs) {
		return null;
	}

	@Override
	public SimpleProgress newSimpleProgress () {
		return null;
	}

	@Override
	public boolean executeImmediately (final Job job) {
		return false;
	}

}

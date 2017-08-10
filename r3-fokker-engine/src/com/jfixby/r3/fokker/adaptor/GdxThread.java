
package com.jfixby.r3.fokker.adaptor;

import com.jfixby.r3.fokker.api.FokkerThreadComponent;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManagerComponent;
import com.jfixby.scarabei.red.promise.RedPromise;
import com.jfixby.scarabei.red.taskman.PassiveTaskManager;

public class GdxThread implements FokkerThreadComponent {
	final PassiveTaskManager taskExecutor = new PassiveTaskManager();

	public void pushTasks () {
		this.taskExecutor.pushAllTasks();
	}

	@Override
	public <R> Promise<R> executeInMainThread (final String debugName, final Future<Void, R> future) {
		return new RedPromise<>(debugName, future, null, this.taskExecutor);
	}

	@Override
	public TaskManagerComponent taskManager () {
		return this.taskExecutor;
	}

}

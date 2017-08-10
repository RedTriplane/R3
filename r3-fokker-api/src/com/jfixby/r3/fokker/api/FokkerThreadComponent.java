
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManagerComponent;

public interface FokkerThreadComponent {

	<R> Promise<R> executeInMainThread (final String debugName, Future<Void, R> future);

	TaskManagerComponent taskManager ();

}

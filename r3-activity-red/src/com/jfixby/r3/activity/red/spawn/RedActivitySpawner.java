
package com.jfixby.r3.activity.red.spawn;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivitySpawnerComponent;
import com.jfixby.r3.activity.api.spawn.ActivitySpawningException;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class RedActivitySpawner implements ActivitySpawnerComponent {

	public Activity spawnActivityAsync (final ID classID) throws ActivitySpawningException {
		Debug.checkNull("classID", classID);
		final String string_name = classID.toString();
		Class<?> clazz;
		try {
			clazz = Class.forName(string_name);
			final Activity unit_instance = (Activity)clazz.newInstance();
			return unit_instance;
		} catch (final Throwable e) {
			Err.reportError(e);
			throw new ActivitySpawningException(e + "");
		}

	}

	@Override
	public Promise<Activity> spawnActivity (final ID classID) {
		final Future<Void, Activity> future = new Future<Void, Activity>() {

			@Override
			public Activity deliver (final Void input) throws Throwable {
				return RedActivitySpawner.this.spawnActivityAsync(classID);
			}
		};
		return TaskManager.newPromise("spawnActivity(" + classID + ")", future);
	}
}

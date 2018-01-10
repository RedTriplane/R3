
package com.jfixby.r3.activity.red.spawn;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivitySpawnerComponent;
import com.jfixby.r3.activity.api.spawn.ActivitySpawningException;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

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
	public Activity spawnActivity (final ID classID) throws ActivitySpawningException {
		return RedActivitySpawner.this.spawnActivityAsync(classID);
	}
}

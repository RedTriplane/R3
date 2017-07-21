
package com.jfixby.r3.activity.red.spawn;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivitySpawnerComponent;
import com.jfixby.r3.activity.api.spawn.ActivitySpawningException;
import com.jfixby.r3.activity.api.spawn.Intent;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;

public class RedActivitySpawner implements ActivitySpawnerComponent {
	@Override
	public Activity spawnActivity (final Intent intent) throws ActivitySpawningException {
		Debug.checkNull("intent", intent);
		final ID classID = intent.getActivityClassID();
		final String string_name = classID.toString();
		Class<?> clazz;
		try {
			clazz = Class.forName(string_name);
			final Activity unit_instance = (Activity)clazz.newInstance();
			return unit_instance;
		} catch (final Throwable e) {
			Err.reportError(e);
			throw new ActivitySpawningException(e + "", intent.getStack());
		}

	}
}

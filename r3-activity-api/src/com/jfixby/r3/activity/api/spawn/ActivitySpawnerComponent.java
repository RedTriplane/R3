
package com.jfixby.r3.activity.api.spawn;

import com.jfixby.r3.activity.api.Activity;

public interface ActivitySpawnerComponent {

	public Activity spawnActivity (Intent unit_class_name) throws ActivitySpawningException;

}

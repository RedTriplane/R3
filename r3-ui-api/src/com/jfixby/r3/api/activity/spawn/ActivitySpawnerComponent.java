
package com.jfixby.r3.api.activity.spawn;

import com.jfixby.r3.api.activity.Activity;

public interface ActivitySpawnerComponent {

	public Activity spawnActivity (Intent unit_class_name) throws ActivitySpawningException;

}

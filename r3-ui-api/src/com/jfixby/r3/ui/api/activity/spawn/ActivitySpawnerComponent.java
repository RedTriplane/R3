
package com.jfixby.r3.ui.api.activity.spawn;

import com.jfixby.r3.ui.api.activity.Activity;

public interface ActivitySpawnerComponent {

	public Activity spawnActivity (Intent unit_class_name) throws ActivitySpawningException;

}

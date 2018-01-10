
package com.jfixby.r3.activity.api.spawn;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.scarabei.api.names.ID;

public interface ActivitySpawnerComponent {

	public Activity spawnActivity (ID unit_class_name) throws ActivitySpawningException;

}


package com.jfixby.r3.activity.api.spawn;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.promise.Promise;

public interface ActivitySpawnerComponent {

	public Promise<Activity> spawnActivity (ID unit_class_name);

}

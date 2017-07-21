
package com.jfixby.r3.activity.api.spawn;

import com.jfixby.scarabei.api.assets.ID;

public interface ActivityMachineComponent {

	void nextActivity (Intent intent);

	public Intent newIntent (ID unit_class_id);

}

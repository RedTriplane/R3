
package com.jfixby.r3.api.activity.spawn;

import com.jfixby.scarabei.api.assets.ID;

public interface ActivityMachineComponent {

	void nextActivity (Intent intent);

	public Intent newIntent (ID unit_class_id);

}

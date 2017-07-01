
package com.jfixby.r3.api.ui.unit;

import com.jfixby.scarabei.api.assets.ID;

public interface UnitsMachineComponent {

	void nextUnit (Intent intent);

	public Intent newIntent (ID unit_class_id);

}

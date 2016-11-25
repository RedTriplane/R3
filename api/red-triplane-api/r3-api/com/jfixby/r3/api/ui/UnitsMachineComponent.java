package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.assets.ID;

public interface UnitsMachineComponent {

	void nextUnit(Intent intent);

	public Intent newIntent(ID unit_class_id);

}

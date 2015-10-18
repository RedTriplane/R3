package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.assets.AssetID;

public interface UnitsMachineComponent {

	void nextUnit(Intent intent);

	public Intent newIntent(AssetID unit_class_id);

}

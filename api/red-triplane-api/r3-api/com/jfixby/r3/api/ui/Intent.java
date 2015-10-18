package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.assets.AssetID;

public interface Intent {

	public AssetID getUnitClassId();

	public void setUnitListener(UnitListener listener);

	UnitListener getUnitListener();

}

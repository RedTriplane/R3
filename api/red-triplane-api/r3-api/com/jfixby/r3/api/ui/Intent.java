
package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.assets.ID;

public interface Intent {

	public ID getUnitClassID ();

	public void setUnitListener (UnitListener listener);

	UnitListener getUnitListener ();

	public IntentStack getStack ();

}

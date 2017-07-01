
package com.jfixby.r3.api.ui.unit;

import com.jfixby.scarabei.api.assets.ID;

public interface Intent {

	public ID getUnitClassID ();

	public void setUnitListener (UnitListener listener);

	UnitListener getUnitListener ();

	public IntentStack getStack ();

}

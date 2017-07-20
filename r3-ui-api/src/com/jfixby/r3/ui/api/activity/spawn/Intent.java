
package com.jfixby.r3.ui.api.activity.spawn;

import com.jfixby.scarabei.api.assets.ID;

public interface Intent {

	public ID getActivityClassID ();

	public void setActivityListener (ActivityListener listener);

	ActivityListener getActivityListener ();

	public IntentStack getStack ();

}

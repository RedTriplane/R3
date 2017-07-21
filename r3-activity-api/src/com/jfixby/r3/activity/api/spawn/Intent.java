
package com.jfixby.r3.activity.api.spawn;

import com.jfixby.scarabei.api.assets.ID;

public interface Intent {

	public ID getActivityClassID ();

	public void setActivityListener (ActivityListener listener);

	ActivityListener getActivityListener ();

	public IntentStack getStack ();

}

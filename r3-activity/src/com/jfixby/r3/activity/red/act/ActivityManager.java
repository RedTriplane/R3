
package com.jfixby.r3.activity.red.act;

import com.jfixby.r3.activity.api.Activity;

public abstract class ActivityManager {

	Activity unit;

	public final Activity getActivity () {
		return this.unit;
	}

	public final void setActivity (final Activity unit) {
		// JUtils.checkNull("unit",unit);
		this.unit = unit;
	}

}

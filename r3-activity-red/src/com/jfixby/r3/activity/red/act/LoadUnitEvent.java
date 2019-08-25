
package com.jfixby.r3.activity.red.act;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivitySpawningException;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

public class LoadUnitEvent extends UIEvent {

	private final RedUIManager redUIManager;
	private final ID unit_id;

	public LoadUnitEvent (final RedUIManager redUIManager, final ID unit_id) {
		this.redUIManager = redUIManager;
		this.unit_id = unit_id;
	}

	@Override
	public void go () {
		final Activity unit = this.redUIManager.getActivity();
		if (unit == null) {
			Err.reportError("Current unit is null. Task failed " + this);
		}
		try {
			this.redUIManager.loadNextActivity(this.unit_id);
		} catch (final ActivitySpawningException e) {
			e.printStackTrace();
			Err.reportError(e);
		}
	}

	@Override
	public boolean isOver () {
		return true;
	}

}

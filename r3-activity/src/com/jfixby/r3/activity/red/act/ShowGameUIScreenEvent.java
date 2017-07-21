
package com.jfixby.r3.activity.red.act;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.spawn.ActivityListener;
import com.jfixby.r3.activity.api.spawn.ActivityMachine;
import com.jfixby.r3.activity.api.spawn.Intent;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;

public class ShowGameUIScreenEvent extends UIEvent implements ActivityListener {

	private final RedUIManager tintoUIManager;
	private Activity current_unit;
	private boolean over = false;
	private final ID asset_id;
	private final Intent intent;

	public ShowGameUIScreenEvent (final RedUIManager tintoUIManager, final ID asset_id) {
		this.asset_id = Debug.checkNull(asset_id);
		this.tintoUIManager = tintoUIManager;
		this.intent = ActivityMachine.newIntent(this.asset_id);
	}

	@Override
	public void go () {

		final ActivityListener listener = this;
		this.intent.setActivityListener(listener);
		ActivityMachine.nextActivity(this.intent);
	}

	@Override
	public boolean isOver () {
		return this.over;
	}

	@Override
	public void onActivityCreated (final Activity unit) {
		this.current_unit = Debug.checkNull(unit);
	}

	@Override
	public void onActivityStarted () {
		this.tintoUIManager.getGameActivityContainer().setActivity(this.current_unit);
		this.tintoUIManager.setCurrent(this.tintoUIManager.getGameActivityContainer());
		this.over = true;
	}

}

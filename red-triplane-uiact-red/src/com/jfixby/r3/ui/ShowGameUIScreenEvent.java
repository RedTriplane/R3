
package com.jfixby.r3.ui;

import com.jfixby.r3.api.ui.unit.Intent;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitListener;
import com.jfixby.r3.api.ui.unit.UnitsMachine;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;

public class ShowGameUIScreenEvent extends UIEvent implements UnitListener {

	private final RedUIManager tintoUIManager;
	private Unit current_unit;
	private boolean over = false;
	private final ID asset_id;
	private final Intent intent;

	public ShowGameUIScreenEvent (final RedUIManager tintoUIManager, final ID asset_id) {
		this.asset_id = Debug.checkNull(asset_id);
		this.tintoUIManager = tintoUIManager;
		this.intent = UnitsMachine.newIntent(this.asset_id);
	}

	@Override
	public void go () {

		final UnitListener listener = this;
		this.intent.setUnitListener(listener);
		UnitsMachine.nextUnit(this.intent);
	}

	@Override
	public boolean isOver () {
		return this.over;
	}

	@Override
	public void onUnitCreated (final Unit unit) {
		this.current_unit = Debug.checkNull(unit);
	}

	@Override
	public void onUnitStarted () {
		this.tintoUIManager.getGameUnitContainer().setUnit(this.current_unit);
		this.tintoUIManager.setCurrent(this.tintoUIManager.getGameUnitContainer());
		this.over = true;
	}

}

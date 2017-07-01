
package com.jfixby.r3.ui;

import com.jfixby.r3.api.ui.unit.Intent;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitListener;
import com.jfixby.r3.api.ui.unit.UnitsMachine;
import com.jfixby.r3.uiact.ShadowStateListener;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;

public class ShowLoadingScreenEvent extends UIEvent {
	boolean unit_created = false;

	private final UnitListener load_listener = new UnitListener() {
		@Override
		public void onUnitStarted () {
			L.d("Unit started", ShowLoadingScreenEvent.this.tintoUIManager.getLoader().getUnit());
		}

		@Override
		public void onUnitCreated (final Unit unit) {
			Debug.checkNull(unit);
			ShowLoadingScreenEvent.this.tintoUIManager.getLoader().setUnit(unit);
			ShowLoadingScreenEvent.this.tintoUIManager.setCurrent(ShowLoadingScreenEvent.this.tintoUIManager.getLoader());
			ShowLoadingScreenEvent.this.unit_created = true;

			if (ShowLoadingScreenEvent.this.fadedOut) {
				if (unit instanceof ShadowStateListener) {
					final ShadowStateListener shadow_state_listener = (ShadowStateListener)unit;
					shadow_state_listener.updateShadow(1);
				} else {
					L.e("Unit <" + unit + " is not instance of ShadowStateListener");
				}
			}
		}
	};

	private final RedUIManager tintoUIManager;

	private final ID asset_id;

	private final boolean fadedOut;

	public ShowLoadingScreenEvent (final RedUIManager tintoUIManager, final ID asset_id, final boolean fadedOut) {
		this.tintoUIManager = tintoUIManager;
		this.asset_id = Debug.checkNull(asset_id);
		this.fadedOut = fadedOut;
	}

	@Override
	public void go () {

		final Intent intent = UnitsMachine.newIntent(this.asset_id);

		final UnitListener listener = this.load_listener;
		intent.setUnitListener(listener);
		UnitsMachine.nextUnit(intent);
	}

	@Override
	public boolean isOver () {
		return this.unit_created;
	}

}

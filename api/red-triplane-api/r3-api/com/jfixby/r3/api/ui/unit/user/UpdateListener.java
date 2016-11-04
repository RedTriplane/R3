package com.jfixby.r3.api.ui.unit.user;

import com.jfixby.r3.api.ui.unit.layer.Component;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;

public interface UpdateListener extends Component {

	public void onUpdate(UnitClocks unit_clock);

}

package com.jfixby.r3.api.ui.unit.update;

import com.jfixby.cmns.api.time.TimeStream;

public interface UnitClocks {

	TimeStream getRenderClock();

	TimeStream getUnitClock();

}

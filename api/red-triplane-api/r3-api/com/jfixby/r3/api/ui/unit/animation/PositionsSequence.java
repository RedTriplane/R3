package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.time.TimeStream;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface PositionsSequence extends Animation, VisibleComponent {

	TimeStream getTimeStream();

}

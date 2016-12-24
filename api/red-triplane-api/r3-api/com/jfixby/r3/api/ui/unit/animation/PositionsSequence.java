package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.scarabei.api.time.TimeStream;

public interface PositionsSequence extends Animation, VisibleComponent {

	TimeStream getTimeStream();

}

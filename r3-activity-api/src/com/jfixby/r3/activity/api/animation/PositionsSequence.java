package com.jfixby.r3.activity.api.animation;

import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.scarabei.api.time.TimeStream;

public interface PositionsSequence extends Animation, VisibleComponent {

	TimeStream getTimeStream();

}

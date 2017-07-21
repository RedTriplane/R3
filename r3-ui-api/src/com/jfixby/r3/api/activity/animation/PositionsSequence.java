package com.jfixby.r3.api.activity.animation;

import com.jfixby.r3.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.time.TimeStream;

public interface PositionsSequence extends Animation, VisibleComponent {

	TimeStream getTimeStream();

}

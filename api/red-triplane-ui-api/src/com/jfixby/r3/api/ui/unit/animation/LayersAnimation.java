
package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.time.TimeStream;

public interface LayersAnimation extends Animation, VisibleComponent {

	public TimeStream getTimeStream ();

	public Collection<VisibleComponent> listFrames ();

}

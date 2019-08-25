
package com.jfixby.r3.activity.api.audio;

import com.jfixby.r3.activity.api.CanvasPositionable;
import com.jfixby.r3.activity.api.ComponentsFactory;

public interface SoundEvent extends SoundComponent, CanvasPositionable {

	public ComponentsFactory getComponentsFactory ();

	SoundEvent copy ();

	public void play ();

	public void mute ();

}

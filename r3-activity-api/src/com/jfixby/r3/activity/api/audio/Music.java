
package com.jfixby.r3.activity.api.audio;

import com.jfixby.r3.activity.api.ComponentsFactory;

public interface Music extends SoundComponent {
	public ComponentsFactory getComponentsFactory ();

	Music copy ();

	public void setLooping (Boolean is_looped);

	public float loopsComplete ();

}

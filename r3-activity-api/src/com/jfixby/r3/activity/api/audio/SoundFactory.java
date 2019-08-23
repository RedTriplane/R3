
package com.jfixby.r3.activity.api.audio;

import com.jfixby.scarabei.api.names.ID;

public interface SoundFactory {
	public SoundEvent newSoundEvent (ID soundid);

	public SoundEvent newSoundEvent (SoundEventSpecs specs);
}

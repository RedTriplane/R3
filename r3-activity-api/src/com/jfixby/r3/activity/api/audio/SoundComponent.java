
package com.jfixby.r3.activity.api.audio;

import com.jfixby.r3.activity.api.layer.NamedComponent;
import com.jfixby.scarabei.api.names.ID;

public interface SoundComponent extends NamedComponent {
	public ID getAssetID ();

	public void setVolume (float volume);

	public float getVolume ();

}

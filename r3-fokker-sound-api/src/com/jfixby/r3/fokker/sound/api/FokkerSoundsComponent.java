
package com.jfixby.r3.fokker.sound.api;

import com.jfixby.scarabei.api.names.ID;

public interface FokkerSoundsComponent {

	public FokkerAudioPackageReader packageReader ();

	public FokkerAudio obtain (ID assetID);

}

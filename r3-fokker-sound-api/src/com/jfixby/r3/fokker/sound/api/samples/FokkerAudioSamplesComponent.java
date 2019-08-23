
package com.jfixby.r3.fokker.sound.api.samples;

import com.jfixby.scarabei.api.names.ID;

public interface FokkerAudioSamplesComponent {

	public FokkerAudioSamplesPackageReader packageReader ();

	public FokkerAudioSample obtain (ID assetID);

}

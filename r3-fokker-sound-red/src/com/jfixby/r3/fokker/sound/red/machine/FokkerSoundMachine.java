
package com.jfixby.r3.fokker.sound.red.machine;

import com.jfixby.r3.engine.api.sound.SoundMachineComponent;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamples;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.loader.PackagesLoader;
import com.jfixby.scarabei.api.log.L;

public class FokkerSoundMachine implements SoundMachineComponent, AssetsConsumer {

	private final AssetsConsumer assetsConsumer = this;

	@Override
	public void deploy () {
		PackagesLoader.registerPackageReader(FokkerAudioSamples.invoke().packageReader().reader());
	}

	@Override
	public void destroy () {
		L.e("Destroy", this);
	}

}

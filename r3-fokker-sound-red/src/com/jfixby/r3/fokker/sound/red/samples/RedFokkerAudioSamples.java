
package com.jfixby.r3.fokker.sound.red.samples;

import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSample;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamplesComponent;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamplesPackageReader;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

public class RedFokkerAudioSamples implements FokkerAudioSamplesComponent {
	final FokkerSoundLoader reader = new FokkerSoundLoader(this);
	final Map<ID, FokkerAudioSample> registry = Collections.newMap();

	public void register (final ID raster_id, final FokkerAudioSample data) {
		this.registry.put(raster_id, data);
	}

	@Override
	public FokkerAudioSamplesPackageReader packageReader () {
		return this.reader;
	}

	@Override
	public FokkerAudioSample obtain (final ID assetID) {
		FokkerAudioSample audio = this.registry.get(assetID);
		if (audio == null) {
			Err.reportError("Asset not found <" + assetID + ">");
		}
		audio = this.registry.get(assetID);
		if (audio == null) {
			Err.reportError("Texture not found: " + assetID);
		}
		return audio;
	}
}

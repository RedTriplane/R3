
package com.jfixby.r3.fokker.sound.red;

import com.jfixby.r3.fokker.sound.api.FokkerAudio;
import com.jfixby.r3.fokker.sound.api.FokkerAudioPackageReader;
import com.jfixby.r3.fokker.sound.api.FokkerSoundsComponent;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

public class RedFokkerSounds implements FokkerSoundsComponent {
	final FokkerSoundLoader reader = new FokkerSoundLoader(this);
	final Map<ID, FokkerAudio> registry = Collections.newMap();

	public void register (final ID raster_id, final FokkerAudio data) {
		this.registry.put(raster_id, data);
	}

	@Override
	public FokkerAudioPackageReader packageReader () {
		return this.reader;
	}

	@Override
	public FokkerAudio obtain (final ID assetID) {
		FokkerAudio audio = this.registry.get(assetID);
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

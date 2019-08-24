
package com.jfixby.r3.activity.red.sound;

import com.jfixby.r3.activity.api.audio.Music;
import com.jfixby.r3.activity.api.audio.MusicSpecs;
import com.jfixby.r3.activity.api.audio.SoundEvent;
import com.jfixby.r3.activity.api.audio.SoundEventSpecs;
import com.jfixby.r3.activity.api.audio.SoundFactory;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.engine.api.sound.AudioSample;
import com.jfixby.r3.engine.api.sound.R3_SOUND_PARAMS;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.pkg.PackagesManager;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedSoundFactory implements SoundFactory {

	private final RedComponentsFactory master;

	public RedSoundFactory (final RedComponentsFactory master) {
		this.master = master;
	}

	@Override
	public SoundEvent newSoundEvent (final ID newAssetID) {
		final SoundEventSpecs s = new SoundEventSpecs();
		s.audio_sample_id = newAssetID;
		return this.newSoundEvent(s);
	}

	@Override
	public SoundEvent newSoundEvent (final SoundEventSpecs specs) {
		final ID newAssetID = specs.audio_sample_id;
		final AudioSample sample = this.obtainAudioSample(newAssetID);
		return new RedSoundEvent(this.master, sample);
	}

	private AudioSample obtainAudioSample (final ID newAssetID) {

		final boolean allowMissingAsset = SystemSettings.getFlag(R3_SOUND_PARAMS.AllowMissingSound);
		final String missingAssetString = SystemSettings.getStringParameter(R3_SOUND_PARAMS.SOUND_IS_MISING, "");
		final boolean reportFail = SystemSettings.getFlag(R3_SOUND_PARAMS.PrintLogMessageOnMissingSound);

		AssetHandler asset_handler = LoadedAssets.obtainAsset(newAssetID, this.master);
		if (asset_handler == null) {
			if (!allowMissingAsset) {
				PackagesManager.printAllIndexes();
				Err.reportError("Asset<" + newAssetID + "> is not loaded.");
				return null;
			}

			final ID missingAsset = Names.newID(missingAssetString);
			asset_handler = LoadedAssets.obtainAsset(missingAsset, this.master);
			if (asset_handler == null) {
				Err.reportError("Asset is not loaded: " + missingAsset);
				return null;
			}

		}

		final Asset asset = asset_handler.asset();

		if (asset instanceof AudioSample) {
			return (AudioSample)asset;
		}

		Err.reportError("Unsupported audio asset type: " + asset);
		return null;
	}

	@Override
	public Music newMusic (final MusicSpecs specs) {
		final ID newAssetID = specs.audio_sample_id;
		final AudioSample sample = this.obtainAudioSample(newAssetID);
		return new RedMusic(this.master, sample);
	}

	@Override
	public Music newMusic (final ID newAssetID) {
		final MusicSpecs s = new MusicSpecs();
		s.audio_sample_id = newAssetID;
		return this.newMusic(s);
	}

}

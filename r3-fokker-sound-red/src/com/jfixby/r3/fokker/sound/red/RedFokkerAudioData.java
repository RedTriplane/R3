
package com.jfixby.r3.fokker.sound.red;

import com.badlogic.gdx.audio.Sound;
import com.jfixby.r3.fokker.sound.api.FokkerAudio;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

public class RedFokkerAudioData implements Asset, FokkerAudio {
	private final ID asset_id;
	private final Sound gdxSound;
	private final RedFokkerAudioSamplesGroup master;

	public RedFokkerAudioData (final ID asset_id, final Sound gdxSound, final RedFokkerAudioSamplesGroup group) {
		this.gdxSound = gdxSound;
		this.asset_id = asset_id;
		this.master = group;
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	@Override
	public AssetsGroup getGroup () {
		return this.master;
	}

	@Override
	public String toString () {
		return "RedFokkerAudioData[" + this.master.toString() + "]";
	}

	public Sound gdxSound () {
		return this.gdxSound;
	}

	@Override
	public void play () {
		Err.throwNotImplementedYet();
// gdxSound.play();
	}

	@Override
	public void setVolume (final float volume) {
// gdxSound.setVolume(soundId, volume);
		Err.throwNotImplementedYet();
	}

	@Override
	public void setPitch (final float pitch) {
		Err.throwNotImplementedYet();
	}

	@Override
	public void setPan (final float leftPan, final float rightPan) {
		Err.throwNotImplementedYet();
	}

}

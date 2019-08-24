
package com.jfixby.r3.fokker.sound.red.samples;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.jfixby.r3.engine.api.sound.AudioSample;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSample;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.names.ID;

public class RedFokkerAudioSample implements Asset, FokkerAudioSample, AudioSample {
	private final ID asset_id;
	private final Sound gdxSound;
	private final Music gdxMusic;
	private final RedFokkerAudioSamplesGroup master;

	public RedFokkerAudioSample (final ID asset_id, final Sound gdxSound, final Music gdxMusic,
		final RedFokkerAudioSamplesGroup group) {
		this.gdxSound = gdxSound;
		this.gdxMusic = gdxMusic;
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

	@Override
	public Sound getGdxSound () {
		return this.gdxSound;
	}

	@Override
	public Music getGdxMusic () {
		return this.gdxMusic;
	}

}

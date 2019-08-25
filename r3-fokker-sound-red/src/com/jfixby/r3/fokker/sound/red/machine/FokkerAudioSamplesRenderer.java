
package com.jfixby.r3.fokker.sound.red.machine;

import java.util.HashMap;

import com.badlogic.gdx.audio.Sound;
import com.jfixby.r3.engine.api.sound.VocalEventState;
import com.jfixby.r3.engine.api.sound.Vocalizable;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSample;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamples;
import com.jfixby.scarabei.api.names.ID;

public class FokkerAudioSamplesRenderer {
	final HashMap<Vocalizable, Long> currently_playing = new HashMap<>(64);
	final HashMap<Vocalizable, ID> assets = new HashMap<>(64);
	final HashMap<Vocalizable, Long> previously_playing = new HashMap<>(64);

	private FokkerAudioSample sound;
	private Sound gdx_sound;

	public void init () {
	}

	public void endFrame () {
		for (final Vocalizable k : this.previously_playing.keySet()) {
			final Long instance_id = this.previously_playing.get(k);
			final ID asset_id = this.assets.remove(k);
			this.sound = FokkerAudioSamples.obtain(asset_id);
			this.gdx_sound = this.sound.getGdxSound();
			this.gdx_sound.stop(instance_id);
			this.sound = null;
			this.gdx_sound = null;

		}
		this.previously_playing.clear();
		this.previously_playing.putAll(this.currently_playing);
		this.currently_playing.clear();
	}

	public void beginFrame () {
	}

	public void vocalizeEvent (final ID asset_id, final Vocalizable event, final VocalEventState state, final boolean isMuted) {
		this.sound = FokkerAudioSamples.obtain(asset_id);
		this.gdx_sound = this.sound.getGdxSound();

		Long instance_id = this.previously_playing.remove(event);
		if (instance_id != null) {
			this.currently_playing.put(event, instance_id);
			this.gdx_sound.setVolume(instance_id, state.volume);
			return;
		}

		instance_id = this.gdx_sound.play();
		this.gdx_sound.setVolume(instance_id, state.volume);

		this.currently_playing.put(event, instance_id);
		this.assets.put(event, asset_id);

		this.sound = null;
		this.gdx_sound = null;
	}

}


package com.jfixby.r3.fokker.sound.red.machine;

import java.util.HashMap;

import com.badlogic.gdx.audio.Sound;
import com.jfixby.r3.engine.api.sound.VocalEventState;
import com.jfixby.r3.engine.api.sound.Vocalizable;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSample;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamples;
import com.jfixby.scarabei.api.names.ID;

public class FokkerAudioSamplesRenderer {
	final HashMap<Vocalizable, MusicHandler> currently_playing = new HashMap<>(64);
	final HashMap<Vocalizable, MusicHandler> previously_playing = new HashMap<>(64);

	private FokkerAudioSample sound;
	private Sound gdx_sound;

	public void init () {
	}

	public void endFrame () {
		for (final Vocalizable k : this.previously_playing.keySet()) {
			final MusicHandler h = this.previously_playing.get(k);
			final Long instance_id = h.instance_id;
			final ID asset_id = h.assetID;
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

	public void vocalizeEvent (final ID asset_id, final Vocalizable event, final VocalEventState state) {
		this.sound = FokkerAudioSamples.obtain(asset_id);
		this.gdx_sound = this.sound.getGdxSound();
		MusicHandler h = this.previously_playing.remove(event);

		if (h != null) {
			this.currently_playing.put(event, h);
			this.gdx_sound.setVolume(h.instance_id, state.volume);
			return;
		}

		if (state.isMuted) {
			return;
		}

		h = new MusicHandler();
		h.instance_id = this.gdx_sound.play();
		this.gdx_sound.setVolume(h.instance_id, state.volume);
		state.loopsComplete = 0;

		this.currently_playing.put(event, h);
		h.assetID = asset_id;

		this.sound = null;
		this.gdx_sound = null;
	}

}

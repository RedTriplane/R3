
package com.jfixby.r3.fokker.sound.red.machine;

import java.util.HashMap;

import com.badlogic.gdx.audio.Music;
import com.jfixby.r3.engine.api.sound.VocalEventState;
import com.jfixby.r3.engine.api.sound.Vocalizable;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSample;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamples;
import com.jfixby.scarabei.api.names.ID;

public class FokkerMusicRenderer {
	private FokkerAudioSample sound;
	private Music gdx_music;

	final HashMap<Vocalizable, ID> currently_playing = new HashMap<>(64);
	final HashMap<Vocalizable, ID> previously_playing = new HashMap<>(64);

	public void init () {
	}

	public void beginFrame () {
	}

	public void endFrame () {
		for (final Vocalizable k : this.previously_playing.keySet()) {
			final ID asset_id = this.previously_playing.get(k);
			this.sound = FokkerAudioSamples.obtain(asset_id);
			this.gdx_music = this.sound.getGdxMusic();
			this.gdx_music.stop();
			this.sound = null;
			this.gdx_music = null;

		}
		this.previously_playing.clear();
		this.previously_playing.putAll(this.currently_playing);
		this.currently_playing.clear();
	}

	public void vocalizeMusic (final ID asset_id, final Vocalizable music, final VocalEventState state, final boolean isMuted) {
		ID instance_id = this.previously_playing.remove(music);
		if (instance_id != null) {
			this.currently_playing.put(music, instance_id);
			return;
		}

		this.sound = FokkerAudioSamples.obtain(asset_id);
		this.gdx_music = this.sound.getGdxMusic();

		this.gdx_music.play();
		instance_id = asset_id;
		this.currently_playing.put(music, instance_id);

		this.sound = null;
		this.gdx_music = null;
	}

}

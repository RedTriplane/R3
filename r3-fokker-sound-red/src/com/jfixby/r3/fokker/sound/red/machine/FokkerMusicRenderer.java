
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

	final HashMap<Vocalizable, MusicHandler> currently_playing = new HashMap<>(64);
	final HashMap<Vocalizable, MusicHandler> previously_playing = new HashMap<>(64);

	public void init () {
	}

	public void beginFrame () {
	}

	public void endFrame () {
		for (final Vocalizable k : this.previously_playing.keySet()) {
			final MusicHandler h = this.previously_playing.get(k);
			final ID asset_id = h.assetID;
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

	public void vocalizeMusic (final ID asset_id, final Vocalizable music, final VocalEventState state) {
		this.sound = FokkerAudioSamples.obtain(asset_id);
		this.gdx_music = this.sound.getGdxMusic();

		MusicHandler h = this.previously_playing.remove(music);
		if (h != null) {
			state.loopsComplete = h.loopsComplete + this.gdx_music.getPosition();
			this.gdx_music.setVolume(state.volume);
			this.currently_playing.put(music, h);
			return;
		}

		if (state.isMuted) {
			return;
		}

		h = new MusicHandler();
		this.gdx_music.setPosition(state.position);
		this.gdx_music.setVolume(state.volume);
		this.gdx_music.setLooping(state.isLooping);
		this.gdx_music.setOnCompletionListener(h);
		state.loopsComplete = h.loopsComplete + this.gdx_music.getPosition();
		this.gdx_music.play();

		h.assetID = asset_id;
		this.currently_playing.put(music, h);

		this.sound = null;
		this.gdx_music = null;
	}

}

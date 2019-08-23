
package com.jfixby.r3.fokker.sound.red.machine;

import com.jfixby.r3.engine.api.sound.DefaultAudioAssets;
import com.jfixby.r3.fokker.api.FOKKER_SYSTEM_ASSETS;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.names.ID;

public class FokkerDefaultAudioAssets implements DefaultAudioAssets {

	private final ID SOUND_TEST_MP3;
	private final ID SOUND_TEST_OGG;
	private final ID SOUND_TEST_WAV;

	FokkerDefaultAudioAssets () {
		this.SOUND_TEST_MP3 = (FOKKER_SYSTEM_ASSETS.TEST_SOUND_MP3);
		this.SOUND_TEST_OGG = (FOKKER_SYSTEM_ASSETS.TEST_SOUND_OGG);
		this.SOUND_TEST_WAV = (FOKKER_SYSTEM_ASSETS.TEST_SOUND_WAV);
	}

	public List<ID> list () {
		final List<ID> result = Collections.newList();
		result.add(this.SOUND_TEST_MP3);
// result.add(this.SOUND_TEST_OGG);
// result.add(this.SOUND_TEST_WAV);
		return result;
	}

	@Override
	public ID TEST_SOUND_MP3 () {
		return this.SOUND_TEST_MP3;
	}

	@Override
	public ID TEST_SOUND_OGG () {
		return this.SOUND_TEST_OGG;
	}

	@Override
	public ID TEST_SOUND_WAV () {
		return this.SOUND_TEST_WAV;
	}

}

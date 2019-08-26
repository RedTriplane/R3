
package com.jfixby.r3.fokker.sound.red.machine;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.jfixby.scarabei.api.names.ID;

public class MusicHandler implements OnCompletionListener {

	ID assetID;
	int loopsComplete = 0;
	Long instance_id;

	@Override
	public void onCompletion (final Music music) {
		this.loopsComplete++;
	};

}

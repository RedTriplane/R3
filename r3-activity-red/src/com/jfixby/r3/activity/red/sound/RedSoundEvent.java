
package com.jfixby.r3.activity.red.sound;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.audio.SoundEvent;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.RedRectangularComponent;
import com.jfixby.r3.engine.api.sound.AudioSample;
import com.jfixby.r3.engine.api.sound.SoundMachine;
import com.jfixby.r3.engine.api.sound.VocalEventState;
import com.jfixby.r3.engine.api.sound.Vocalizable;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.names.ID;

public class RedSoundEvent extends RedRectangularComponent implements SoundEvent, Vocalizable {

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.master;
	}

	private final ID asset_id;
	private final RedComponentsFactory master;
	private final int hashid;
	static int idspawner = 0;

	@Override
	public int hashCode () {
		return this.hashid;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final RedSoundEvent other = (RedSoundEvent)obj;
		if (this.hashid != other.hashid) {
			return false;
		}
		return true;
	}

	public RedSoundEvent (final RedComponentsFactory master, final AudioSample data) {
		super(master);
		this.master = master;
		this.asset_id = data.getAssetID();
		this.hashid = idspawner++;
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	@Override
	public SoundEvent copy () {
		final SoundEvent event = this.master.getSoundFactory().newSoundEvent(this.asset_id);

		// copy settings...

		return event;
	}

	@Override
	public void setOriginAbsolute (final ReadOnlyFloat2 point) {
		this.setOriginAbsoluteX(point.getX());
		this.setOriginAbsoluteY(point.getY());
	}

	@Override
	public void setOriginRelative () {
		this.shape.setOriginRelative();
	}

	@Override
	public void setSize (final Rectangle rectangle) {
		this.setSize(rectangle.getWidth(), rectangle.getHeight());
	}

	@Override
	public boolean isMute () {
		return !this.isVisible();
	}

	VocalEventState state = new VocalEventState();

	@Override
	public void doVocalize (final boolean isMuted) {
		SoundMachine.component().VocalizeEvent(this.asset_id, this, this.state, isMuted);
	}

	@Override
	public void play () {
		this.show();
	}

	@Override
	public void mute () {
		this.hide();
	}

	@Override
	public void setVolume (final float volume) {
		this.state.volume = volume;
	}

	@Override
	public float getVolume () {
		return this.state.volume;
	}

}

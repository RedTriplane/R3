
package com.jfixby.r3.activity.red.sound;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.audio.SoundEvent;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.RedRectangularComponent;
import com.jfixby.r3.engine.api.sound.AudioSample;
import com.jfixby.r3.engine.api.sound.SoundMachine;
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

	public RedSoundEvent (final RedComponentsFactory master, final AudioSample data) {
		super(master);
		this.master = master;
		this.asset_id = data.getAssetID();
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

	@Override
	public void doVocalize () {
		SoundMachine.component().VocalizeEvent(this.asset_id);
	}

}

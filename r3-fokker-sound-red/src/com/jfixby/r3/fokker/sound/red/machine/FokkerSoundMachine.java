
package com.jfixby.r3.fokker.sound.red.machine;

import java.io.IOException;

import com.jfixby.r3.engine.api.sound.SoundMachineComponent;
import com.jfixby.r3.engine.api.sound.VocalEventState;
import com.jfixby.r3.engine.api.sound.Vocalizable;
import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamples;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.loader.PackagesLoader;
import com.jfixby.r3.rana.api.manager.AssetsManager;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.StateSwitcher;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.geometry.projections.ProjectionsStack;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;

public class FokkerSoundMachine implements SoundMachineComponent, AssetsConsumer {

	private static StateSwitcher<AUDIO_RENDER_MACHINE_STATE> render_state;
	public Projection camera_projection;
	public Projection layer_projection;

	final FokkerAudioSamplesRenderer audioSamplesRenderer = new FokkerAudioSamplesRenderer();
	final FokkerMusicRenderer musicRenderer = new FokkerMusicRenderer();

	@Override
	public void deploy () {
		render_state = Debug.newStateSwitcher(AUDIO_RENDER_MACHINE_STATE.NEW);
		render_state.setDebugName("render_state");
		render_state.setDebugFlag(!true);

		PackagesLoader.registerPackageReader(FokkerAudioSamples.invoke().packageReader().reader());

		this.audioSamplesRenderer.init();
		this.musicRenderer.init();

		expectState(AUDIO_RENDER_MACHINE_STATE.NEW);
		switchState(AUDIO_RENDER_MACHINE_STATE.READY);

		try {
			AssetsManager.autoResolveAssets(this.DefaultAssets().list());
		} catch (final IOException e) {
			e.printStackTrace();
			Err.reportError(e);
		}
	}

	private final FokkerDefaultAudioAssets defaultAssets = new FokkerDefaultAudioAssets();
	private Vocalizable currentComponent;

	@Override
	public FokkerDefaultAudioAssets DefaultAssets () {
		return this.defaultAssets;
	}

	static final private void expectState (final AUDIO_RENDER_MACHINE_STATE expected) {
		render_state.expectState(expected);
	}

	static final private AUDIO_RENDER_MACHINE_STATE switchState (final AUDIO_RENDER_MACHINE_STATE next_state) {
		render_state.switchState(next_state);
		return next_state;
	}

	@Override
	public void destroy () {
		L.e("Destroy", this);
	}

	@Override
	public void setProjection (final ProjectionsStack projection) {
		expectState(AUDIO_RENDER_MACHINE_STATE.FRAME);
		this.layer_projection = projection;
		if (this.layer_projection == null) {
			this.layer_projection = Geometry.getProjectionFactory().IDENTITY();

		}
	}

	@Override
	public void setCameraProjection (final Projection camera) {
		expectState(AUDIO_RENDER_MACHINE_STATE.FRAME);
		this.camera_projection = camera;
		if (this.camera_projection == null) {
			this.camera_projection = Geometry.getProjectionFactory().IDENTITY();
		}
	}

	@Override
	final public void beginDrawComponent (final Vocalizable fokkerDrawable) {
		expectState(AUDIO_RENDER_MACHINE_STATE.FRAME);
		this.currentComponent = fokkerDrawable;
	}

	@Override
	final public void endDrawComponent (final Vocalizable fokkerDrawable) {
		expectState(AUDIO_RENDER_MACHINE_STATE.FRAME);
		this.currentComponent = null;
	}

	@Override
	public void beginFrame () {
		expectState(AUDIO_RENDER_MACHINE_STATE.READY);
		switchState(AUDIO_RENDER_MACHINE_STATE.FRAME);
		this.audioSamplesRenderer.beginFrame();
		this.musicRenderer.beginFrame();
	}

	@Override
	final public void endFrame () {
		expectState(AUDIO_RENDER_MACHINE_STATE.FRAME);
		switchState(AUDIO_RENDER_MACHINE_STATE.READY);
		this.audioSamplesRenderer.endFrame();
		this.musicRenderer.endFrame();
		Debug.component().checkTrue(this.layer_projection == Geometry.getProjectionFactory().IDENTITY());
	}

	@Override
	public void VocalizeEvent (final ID asset_id, final Vocalizable event, final VocalEventState state, final boolean isMuted) {
		this.audioSamplesRenderer.vocalizeEvent(asset_id, event, state, isMuted);
	}

	@Override
	public void VocalizeMusic (final ID asset_id, final Vocalizable music, final VocalEventState state, final boolean isMuted) {
		this.musicRenderer.vocalizeMusic(asset_id, music, state, isMuted);

	}

}

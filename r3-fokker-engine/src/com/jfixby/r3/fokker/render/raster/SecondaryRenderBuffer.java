
package com.jfixby.r3.fokker.render.raster;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.jfixby.r3.engine.api.render.BUFFER_STATE;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.engine.api.screen.ScreenDimentionsChecker;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.util.StateSwitcher;
import com.jfixby.scarabei.api.util.Utils;

public class SecondaryRenderBuffer {
	private final ScreenDimentionsChecker checker;
	private static StateSwitcher<BUFFER_STATE> buffer_state;
	private FrameBuffer secondary_buffer;

	public SecondaryRenderBuffer () {
		super();
		this.checker = Screen.newScreenDimentionsChecker();
		buffer_state = Utils.newStateSwitcher(BUFFER_STATE.PAUSED);
	}

	private void createBuffers () {
		try {
			this.secondary_buffer = new FrameBuffer(Format.RGB888, FRAME_BUFFER_WIDTH(), FRAME_BUFFER_HEIGHT(), false);
			// secondary_buffer = new FrameBuffer(Format.RGBA8888,
			// FRAME_BUFFER_WIDTH(), FRAME_BUFFER_HEIGHT(), false);
		} catch (final Throwable e) {
			e.printStackTrace();
			Err.reportError("failed to start graphics");
		}
	}

	public void begin (final Matrix4 combined) {
		buffer_state.expectState(BUFFER_STATE.PAUSED);
		buffer_state.switchState(BUFFER_STATE.FRAME);
		if (this.checker.screenDimentionsHaveChanged()) {
			if (this.secondary_buffer != null) {
				this.secondary_buffer.dispose();
			}
			this.createBuffers();
			this.checker.okGotIt();
		}
		this.secondary_buffer.begin();
		GdxRender.setShader(null);
		GdxRender.clearScreen(Colors.NO());
	}

	private static final int FRAME_BUFFER_WIDTH = 1024;

	private static final int FRAME_BUFFER_HEIGHT = 1024;

	private static int FRAME_BUFFER_HEIGHT () {
		return Screen.getScreenHeight();
	}

	private static int FRAME_BUFFER_WIDTH () {
		return Screen.getScreenWidth();
	}

	public void end () {
		buffer_state.expectState(BUFFER_STATE.FRAME);
		buffer_state.switchState(BUFFER_STATE.PAUSED);
		this.secondary_buffer.end();
	}

	public Texture getResult () {
		return this.secondary_buffer.getColorBufferTexture();
	}

	public void saveTexture (final Texture tmp_texture) {
		GdxRender.primaryBufferTextureFlush(tmp_texture, this.getWidth(), this.getHeight());
	}

	public int getWidth () {
		return this.secondary_buffer.getWidth();
	}

	public int getHeight () {
		return this.secondary_buffer.getHeight();
	}

}

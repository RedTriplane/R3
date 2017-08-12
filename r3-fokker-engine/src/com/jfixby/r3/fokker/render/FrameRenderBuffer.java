
package com.jfixby.r3.fokker.render;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.jfixby.r3.engine.api.render.BUFFER_STATE;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.engine.api.screen.ScreenDimentionsChecker;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.util.StateSwitcher;
import com.jfixby.scarabei.api.util.Utils;

public class FrameRenderBuffer extends RenderBuffer {
	private static StateSwitcher<BUFFER_STATE> buffer_state;
	private FrameBuffer primary_buffer;
	// private FrameBuffer secondary_buffer;
	// private FrameBuffer tmp_buffer;
	private ScreenDimentionsChecker checker;
	private Matrix4 matrix;
	private DebugTimer timer;

	// private BLEND_MODE current_mode = BLEND_MODE.NORMAL;

	public FrameRenderBuffer () {

	}

	// private void swap() {
	// this.tmp_buffer = this.primary_buffer;
	// this.primary_buffer = this.secondary_buffer;
	// this.secondary_buffer = this.tmp_buffer;
	// }

	private static int FRAME_BUFFER_HEIGHT () {
		return Screen.getScreenHeight();
	}

	private static int FRAME_BUFFER_WIDTH () {
		return Screen.getScreenWidth();
	}

	@Override
	public void init () {
		this.checker = Screen.newScreenDimentionsChecker();
		buffer_state = Utils.newStateSwitcher(BUFFER_STATE.NEW);
	}

	private void createBuffers () {
		try {
			this.primary_buffer = new FrameBuffer(Format.RGBA8888, FRAME_BUFFER_WIDTH(), FRAME_BUFFER_HEIGHT(), false);
		} catch (final Throwable e) {
			e.printStackTrace();
			Err.reportError("failed to start graphics");
		}
		this.timer = Debug.newTimer();
	}

	@Override
	public void pause () {
		buffer_state.expectState(BUFFER_STATE.FRAME);
		buffer_state.switchState(BUFFER_STATE.PAUSED);
		// matrix = GdxRender.closeRasterRenderer();
		this.primary_buffer.end();
	}

	@Override
	public void resume () {
		buffer_state.expectState(BUFFER_STATE.PAUSED);
		buffer_state.switchState(BUFFER_STATE.FRAME);
		this.primary_buffer.begin();
		// GdxRender.openRasterRenderer(matrix);
		// matrix = null;
	}

	@Override
	final public void beginFrame () {
		buffer_state.expectState(BUFFER_STATE.NEW);
		buffer_state.switchState(BUFFER_STATE.FRAME);
		if (this.checker.screenDimentionsHaveChanged()) {
			if (this.primary_buffer != null) {
				this.primary_buffer.dispose();
				// secondary_buffer.dispose();
			}
			this.createBuffers();
			this.checker.okGotIt();
		}
		// current_mode = BLEND_MODE.NORMAL;
		this.primary_buffer.begin();
		GdxRender.setShader(null);
	}

	@Override
	final public void endFrame () {
		buffer_state.expectState(BUFFER_STATE.FRAME);
		buffer_state.switchState(BUFFER_STATE.NEW);
		// gdx_sprite_batch.end();
		// gdx_sprite_batch.setShader(null);
		// current_mode = BLEND_MODE.NORMAL;
		this.primary_buffer.end();
		// current_mode = BLEND_MODE.NORMAL;
		GdxRender.setShader(null);
		// timer.reset();
		final Texture texture = this.primary_buffer.getColorBufferTexture();

		GdxRender.primaryBufferTextureFlush(texture, texture.getWidth(), texture.getHeight());
		// timer.printTime("primaryBufferTextureFlush");

	}

	@Override
	public Texture getResult () {
		return this.primary_buffer.getColorBufferTexture();

	}

	// final public void updateMode(final BLEND_MODE next_mode) {
	// if (next_mode != current_mode) {
	// current_mode = next_mode;
	//
	// final Matrix4
	//
	// // gdx_sprite_batch.end();
	// primary_buffer.end();
	// final Texture texture = primary_buffer.getColorBufferTexture();
	// swap();
	//
	// final ShaderProgram shader_program = setupShader(current_mode, texture);
	// GdxRender.setShader(shader_program);
	// GdxRender.openRasterRenderer(matrix);
	// //
	// // primary_buffer.begin();
	// // gdx_sprite_batch.begin();
	// // // gdx_sprite_batch.setColor(1f, 1f, 1f, 1f);
	// // gdx_sprite_batch.draw(texture, 0, 0);
	//
	// }
	// }

}

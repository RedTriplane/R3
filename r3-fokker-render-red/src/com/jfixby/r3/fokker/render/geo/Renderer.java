
package com.jfixby.r3.fokker.render.geo;

import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.engine.api.screen.ScreenDimentionsChecker;
import com.jfixby.r3.fokker.render.FokkerRenderMachine;
import com.jfixby.r3.fokker.shader.api.FokkerShader;
import com.jfixby.scarabei.api.err.Err;

public abstract class Renderer {

	public FokkerRenderMachine machine;

	public void init (final FokkerRenderMachine fokkerRenderMachine) {
		this.machine = fokkerRenderMachine;
	}

	boolean is_open = false;

	public final boolean isOpen () {
		return this.is_open;
	}

	public final void open () {
		if (this.is_open) {
			Err.reportError("Is open altready!");
		}
		this.is_open = true;

		this.doOpen();
	}

	public final void close () {
		if (!this.is_open) {
			Err.reportError("Is open altready!");
		}
		this.is_open = !true;
		this.doClose();
	}

	public abstract void doOpen ();

	public abstract void doClose ();

	private com.badlogic.gdx.graphics.OrthographicCamera gdx_screen_Camera;
	private ScreenDimentionsChecker checker;
	private FokkerShader shader_handler;
	private int viewportWidth;
	private int viewportHeight;

	final void checkScreen (final boolean and_set) {
		if (this.gdx_screen_Camera == null) {
			this.gdx_screen_Camera = new com.badlogic.gdx.graphics.OrthographicCamera();
			this.checker = Screen.newScreenDimentionsChecker();
		}
		if (this.checker.screenDimentionsHaveChanged()) {
			this.viewportWidth = Screen.getScreenWidth();
			this.viewportHeight = Screen.getScreenHeight();
			this.gdx_screen_Camera.setToOrtho(true, this.viewportWidth, this.viewportHeight);
			this.gdx_screen_Camera.update();
			// if (and_set) {
			// this.setGdxProjectionMatrix(this.gdx_screen_Camera.combined);
			// }

			this.checker.okGotIt();

		}

	}

	// public abstract void setGdxProjectionMatrix(Matrix4 combined);

	final public com.badlogic.gdx.graphics.OrthographicCamera getGdxCamera () {
		return this.gdx_screen_Camera;
	}

	final public void beginFrame () {
		this.checkScreen(false);
		this.onFrameBegin();
	}

	public abstract void onFrameBegin ();

	public abstract void onFrameEnd ();

	final public void endFrame () {
		this.onFrameEnd();
	}

	// public final com.badlogic.gdx.graphics.glutils.ShaderProgram
	// getGdxShader() {
	// return this.machine.current_shader.getGdxShaderProgram();
	// }
}


package com.jfixby.r3.fokker.render;

import com.badlogic.gdx.graphics.Texture;
import com.jfixby.scarabei.api.err.Err;

public class NoRenderBuffer extends RenderBuffer {

	@Override
	public void init () {
	}

	@Override
	public void pause () {
	}

	@Override
	public Texture getResult () {
		Err.reportError("Not supported");
		return null;
	}

	@Override
	public void resume () {
	}

	@Override
	public void beginFrame () {
	}

	@Override
	public void endFrame () {
	}

}

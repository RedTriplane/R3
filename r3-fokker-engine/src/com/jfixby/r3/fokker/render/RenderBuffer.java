
package com.jfixby.r3.fokker.render;

import com.badlogic.gdx.graphics.Texture;

public abstract class RenderBuffer {

	abstract public void init ();

	abstract public void pause ();

	abstract public Texture getResult ();

	abstract public void resume ();

	abstract public void beginFrame ();

	abstract public void endFrame ();
}

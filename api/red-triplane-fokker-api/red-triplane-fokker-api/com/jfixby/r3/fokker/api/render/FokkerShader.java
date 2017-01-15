
package com.jfixby.r3.fokker.api.render;

import com.jfixby.scarabei.api.geometry.Rectangle;

public interface FokkerShader {

	com.badlogic.gdx.graphics.glutils.ShaderProgram getGdxShaderProgram ();

	boolean isOverlay ();

	Rectangle shape ();

}

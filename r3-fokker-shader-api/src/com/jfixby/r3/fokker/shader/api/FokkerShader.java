
package com.jfixby.r3.fokker.shader.api;

import com.jfixby.r3.engine.api.render.Shader;
import com.jfixby.r3.engine.api.render.ShaderSettings;

public interface FokkerShader extends Shader {

	com.badlogic.gdx.graphics.glutils.ShaderProgram getGdxShaderProgram ();

	boolean isOverlay ();

	void applySettings (ShaderSettings params);

	void setOpacity (double opacity);

}

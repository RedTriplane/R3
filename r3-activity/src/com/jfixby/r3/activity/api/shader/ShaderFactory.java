
package com.jfixby.r3.activity.api.shader;

import com.jfixby.r3.activity.api.ComponentsFactory;

public interface ShaderFactory {

	public ComponentsFactory parent ();

	public ShaderSpecs newShaderSpecs ();

	public Shader newShader (ShaderSpecs specs);

}

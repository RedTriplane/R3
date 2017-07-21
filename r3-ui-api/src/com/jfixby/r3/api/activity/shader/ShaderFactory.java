
package com.jfixby.r3.api.activity.shader;

import com.jfixby.r3.api.activity.ComponentsFactory;

public interface ShaderFactory {

	public ComponentsFactory parent ();

	public ShaderSpecs newShaderSpecs ();

	public Shader newShader (ShaderSpecs specs);

}

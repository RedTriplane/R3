
package com.jfixby.r3.api.ui.unit.shader;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;

public interface ShaderFactory {
	public ShaderSpecs newShaderSpecs ();

	public ShaderComponent newShader (ShaderSpecs specs);

	public ComponentsFactory parent ();

}

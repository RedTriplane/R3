package com.jfixby.r3.api.ui.unit.shader;

import com.jfixby.r3.api.ui.unit.txt.Shader;

public interface ShaderFactory {

	ShaderSpecs newShaderSpecs();

	Shader newShader(ShaderSpecs shader_specs);

}

package com.jfixby.r3.api.ui.unit.shader;

public interface ShaderFactory {

	ShaderSpecs newShaderSpecs();

	Shader newShader(ShaderSpecs shader_specs);

}

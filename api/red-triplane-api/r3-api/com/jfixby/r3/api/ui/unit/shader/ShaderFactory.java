package com.jfixby.r3.api.ui.unit.shader;

public interface ShaderFactory {
    public ShaderSpecs newShaderSpecs();

    public Shader newShader(ShaderSpecs specs);

}

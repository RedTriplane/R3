package com.jfixby.r3.api.shader;

import com.jfixby.rana.api.pkg.PackageReader;

public interface R3ShaderComponent {
	ShaderSpecs newShaderSpecs();

	Shader newShader(ShaderSpecs shader_specs);

	PackageReader getPackageReader();

	PhotoshopShaders PHOTOSHOP();

}

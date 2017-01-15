
package com.jfixby.r3.api.shader;

import com.jfixby.r3.api.shader.srlz.ShaderParameterInfo;

public class FOKKER_SHADER_PARAMS {

	public static final ShaderParameterInfo SCREEN_WIDTH = new ShaderParameterInfo("SCREEN_WIDTH".toLowerCase(), "float");
	public static final ShaderParameterInfo SCREEN_HEIGHT = new ShaderParameterInfo("SCREEN_HEIGHT".toLowerCase(), "float");
	public static final ShaderParameterInfo ALPHA_BLEND = new ShaderParameterInfo("ALPHA_BLEND".toLowerCase(), "float");
	public static final ShaderParameterInfo U_TEXTURE_0_CURRENT = new ShaderParameterInfo("U_TEXTURE_0_CURRENT".toLowerCase(),
		"int");
	public static final ShaderParameterInfo U_TEXTURE_1_ORIGINAL = new ShaderParameterInfo("U_TEXTURE_1_ORIGINAL".toLowerCase(),
		"int");
	public static final ShaderParameterInfo U_TEXTURE_2_ALPHA = new ShaderParameterInfo("U_TEXTURE_2_ALPHA".toLowerCase(), "int");

}

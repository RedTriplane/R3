
package com.jfixby.r3.fokker.assets.api.shader.io;

public class FOKKER_SHADER_PARAMS {

	public static final ShaderParameterInfo SCREEN_WIDTH = new ShaderParameterInfo("SCREEN_WIDTH".toLowerCase(),
		ShaderParameterInfo.TYPE_FLOAT, 0f);
	public static final ShaderParameterInfo SCREEN_HEIGHT = new ShaderParameterInfo("SCREEN_HEIGHT".toLowerCase(),
		ShaderParameterInfo.TYPE_FLOAT, 0f);
	public static final ShaderParameterInfo ALPHA_BLEND = new ShaderParameterInfo("ALPHA_BLEND".toLowerCase(),
		ShaderParameterInfo.TYPE_FLOAT, 1f);
	public static final ShaderParameterInfo U_TEXTURE_0_CURRENT = new ShaderParameterInfo("U_TEXTURE_0_CURRENT".toLowerCase(),
		ShaderParameterInfo.TYPE_INT, 0);
	public static final ShaderParameterInfo U_TEXTURE_1_ORIGINAL = new ShaderParameterInfo("U_TEXTURE_1_ORIGINAL".toLowerCase(),
		ShaderParameterInfo.TYPE_INT, 1);
	public static final ShaderParameterInfo U_TEXTURE_2_ALPHA = new ShaderParameterInfo("U_TEXTURE_2_ALPHA".toLowerCase(),
		ShaderParameterInfo.TYPE_INT, 2);

}

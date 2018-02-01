
package com.jfixby.r3.fokker.shader.api;

import com.jfixby.scarabei.api.names.ID;

public interface FokkerShadersComponent {

	public FokkerShaderPackageReader packageReader ();

	public FokkerShader obtain (ID assetID);

}

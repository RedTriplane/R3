
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.collections.Mapping;

public interface ShaderProperties {

	Mapping<String, ShaderParameter> listParameters ();

	ShaderSettings newShaderSettings ();

}

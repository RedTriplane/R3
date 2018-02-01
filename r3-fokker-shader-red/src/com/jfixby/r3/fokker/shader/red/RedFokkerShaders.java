
package com.jfixby.r3.fokker.shader.red;

import com.jfixby.r3.fokker.shader.api.FokkerShader;
import com.jfixby.r3.fokker.shader.api.FokkerShaderPackageReader;
import com.jfixby.r3.fokker.shader.api.FokkerShadersComponent;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;

public class RedFokkerShaders implements FokkerShadersComponent {
	final RedFokkerShaderPackageReader reader = new RedFokkerShaderPackageReader(this);
	final Map<ID, FokkerShader> registry = Collections.newMap();

	public void register (final ID raster_id, final FokkerShader data) {
		this.registry.put(raster_id, data);
	}

	@Override
	public FokkerShaderPackageReader packageReader () {
		return this.reader;
	}

	@Override
	public FokkerShader obtain (final ID assetID) {
		FokkerShader shader = this.registry.get(assetID);
		if (shader == null) {
			Err.reportError("shader not found " + assetID);
		}
		shader = this.registry.get(assetID);
		if (shader == null) {
			Err.reportError("Shader not found: " + assetID);
		}
		return shader;
	}

}

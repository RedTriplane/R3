
package com.jfixby.r3.api.ui.unit.shader;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface ShaderSpecs {

	void setShaderAssetID (ID shader_asset);

	ID getShaderAssetID ();

	void setShape (Rectangle shape);

	Rectangle getShape ();
}

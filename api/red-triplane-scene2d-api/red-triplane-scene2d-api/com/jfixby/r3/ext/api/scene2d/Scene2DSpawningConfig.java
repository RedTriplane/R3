package com.jfixby.r3.ext.api.scene2d;

import com.jfixby.cmns.api.assets.AssetID;

public interface Scene2DSpawningConfig {

	void setStructureID(AssetID asset_id);

	AssetID getStructureID();

	String getDefaultLocaleName();

	void setDefaultLocaleName(String locale_name);

	float getDebugOpacity();
	
	void setDebugOpacity(float debug_opacity);

}

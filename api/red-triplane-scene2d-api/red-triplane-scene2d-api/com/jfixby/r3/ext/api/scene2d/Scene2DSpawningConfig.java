
package com.jfixby.r3.ext.api.scene2d;

import com.jfixby.cmns.api.assets.ID;
import com.jfixby.rana.api.pkg.PackageReaderListener;

public interface Scene2DSpawningConfig {

	void setStructureID (ID asset_id);

	ID getStructureID ();

	String getDefaultLocaleName ();

	void setDefaultLocaleName (String locale_name);

	float getDebugOpacity ();

	void setDebugOpacity (float debug_opacity);

	boolean renderDebugInfo ();

	void setRenderDebugInfo (boolean b);

	PackageReaderListener getPackageListener ();

	void setPackageListener (PackageReaderListener listener);

// void setAssetsConsumer (AssetsConsumer consumer);

}

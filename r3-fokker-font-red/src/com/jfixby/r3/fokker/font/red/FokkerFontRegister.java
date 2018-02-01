
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.fokker.font.api.FokkerFont;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.names.ID;

public class FokkerFontRegister {

	final TrueTypeFontFontPackageLoader reader = new TrueTypeFontFontPackageLoader(this);
	final Map<ID, FokkerFont> registry = Collections.newMap();

	public void register (final ID raster_id, final FokkerFont data) {
		this.registry.put(raster_id, data);
	}

	public FokkerFont get (final ID assetID) {
		return this.registry.get(assetID);
	}

}

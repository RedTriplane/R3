
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.engine.api.render.FontParameters;
import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.names.ID;

public class FokkerStringRegister {
	final Map<ID, FokkerStringGroup> registry = Collections.newMap();

// public void register (final ID fontID, final FokkerString data) {

// this.registry.put(raster_id, data);
// }

	public FokkerString get (final ID fontID, final FontParameters fontParams, final String stringValue) {
		final FokkerStringGroup group = this.registry.get(fontID);
		return group.resolve(fontParams, stringValue);
	}
}

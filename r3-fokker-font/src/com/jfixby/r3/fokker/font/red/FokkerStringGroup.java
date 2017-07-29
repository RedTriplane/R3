
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.engine.api.render.FontParameters;
import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;

public class FokkerStringGroup {
	final Map<ID, FokkerStringGroup> registry = Collections.newMap();

	public FokkerString resolve (final FontParameters fontParams, final String stringValue) {
		return null;
	}

}

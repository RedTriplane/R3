
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.r3.fokker.font.api.FokkerStringHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.font.RasterStringSettings;

public class FokkerStringRegister {
	final Map<RasterStringSettings, FokkerStringGroup> registry = Collections.newMap();

	public FokkerStringHandler register (final RasterStringSettings rasterStringSettings, final FokkerString string,
		final AssetsConsumer consumer) {

		return null;
	}
}

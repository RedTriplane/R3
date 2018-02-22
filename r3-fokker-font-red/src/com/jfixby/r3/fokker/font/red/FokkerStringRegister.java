
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.r3.fokker.font.api.FokkerStringHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.font.RasterStringSettings;
import com.jfixby.scarabei.api.log.L;

public class FokkerStringRegister {
	final Map<RasterStringSettings, RedFokkerStringHandler> settingsToHandler = Collections.newMap();
	final Map<FokkerStringHandler, RasterStringSettings> handlerToSettings = Collections.newMap();

	public RedFokkerStringHandler register (final RasterStringSettings rasterStringSettings, final FokkerString string,
		final AssetsConsumer consumer) {

		final RedFokkerStringHandler handler = new RedFokkerStringHandler(string, consumer);

		Debug.checkTrue(!this.settingsToHandler.containsKey(rasterStringSettings));

		this.settingsToHandler.put(rasterStringSettings, handler);
		this.handlerToSettings.put(handler, rasterStringSettings);

		return handler;
	}

	public RedFokkerStringHandler get (final RasterStringSettings rasterStringSettings) {
		return this.settingsToHandler.get(rasterStringSettings);
	}

	public void unRegister (final FokkerStringHandler string, final AssetsConsumer consumer) {

		final RasterStringSettings settings = this.handlerToSettings.remove(string);
		Debug.checkNull(settings);

		final RedFokkerStringHandler handler = this.settingsToHandler.remove(settings);

		Debug.checkTrue(handler.consumer == consumer);
		Debug.checkTrue(handler == string);

	}

	public void print () {
		L.d(" handler", this.handlerToSettings);
		L.d("settings", this.settingsToHandler);
	}
}

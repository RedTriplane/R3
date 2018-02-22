
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.r3.fokker.font.api.FokkerStringHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.scarabei.api.font.RasterStringSettings;

public class RedFokkerStringHandler implements FokkerStringHandler {
	final public FokkerString string;
	final public AssetsConsumer consumer;

	public RedFokkerStringHandler (final FokkerString string, final AssetsConsumer consumer) {
		this.string = string;
		this.consumer = consumer;
	}

	public FokkerString resolve (final RasterStringSettings specs) {
		return this.string;
	}

}


package com.jfixby.r3.fokker.font.api;

import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.scarabei.api.font.RasterStringSettings;

public interface FokkerFontsComponent {

	public FokkerFontPackageReader packageReader ();

	public FokkerString obtainString (RasterStringSettings specs);

	public RasterStringSettings newStringSpec ();

	public FokkerStringHandler spawnString (RasterStringSettings rasterStringSettings, final AssetsConsumer consumer);

	public void disposeString (FokkerStringHandler string, final AssetsConsumer consumer);

}

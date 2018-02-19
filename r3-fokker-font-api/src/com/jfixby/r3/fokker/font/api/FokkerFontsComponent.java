
package com.jfixby.r3.fokker.font.api;

import com.jfixby.r3.engine.api.render.FontParameters;
import com.jfixby.scarabei.api.font.StringSpec;
import com.jfixby.scarabei.api.names.ID;

public interface FokkerFontsComponent {

	public FokkerFontPackageReader packageReader ();

	public FokkerFont obtainFont (ID assetID);

	public FokkerString obtainString (final ID fontID, final FontParameters fontParams, final String stringValue);

	public StringSpec newStringSpec ();

	public FokkerStringHandler obtainString (StringSpec stringSpecs);

	public void disposeString (FokkerStringHandler string);

}

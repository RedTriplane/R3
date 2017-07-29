
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.engine.api.render.FontParameters;
import com.jfixby.r3.fokker.font.api.FokkerFont;
import com.jfixby.r3.fokker.font.api.FokkerFontPackageReader;
import com.jfixby.r3.fokker.font.api.FokkerFontsComponent;
import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.scarabei.api.assets.ID;

public class RedFokkerFonts implements FokkerFontsComponent {

	final FokkerFontRegister fontRegister = new FokkerFontRegister();
	final FokkerStringRegister stringRegister = new FokkerStringRegister();

	@Override
	public FokkerFontPackageReader packageReader () {
		return this.fontRegister.reader;
	}

	@Override
	public FokkerFont obtainFont (final ID assetID) {
		return this.fontRegister.get(assetID);
	}

	@Override
	public FokkerString obtainString (final ID fontID, final FontParameters fontParams, final String stringValue) {
		return this.stringRegister.get(fontID, fontParams, stringValue);
	}

}

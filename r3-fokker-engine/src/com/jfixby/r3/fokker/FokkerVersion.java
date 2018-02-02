
package com.jfixby.r3.fokker;

import com.jfixby.r3.engine.api.EngineVersion;
import com.jfixby.r3.fokker.font.api.FokkerFonts;
import com.jfixby.r3.fokker.shader.api.FokkerShaders;
import com.jfixby.r3.fokker.texture.api.FokkerTextures;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.strings.Strings;

public class FokkerVersion implements EngineVersion {

	private final String name;
	private final String build_id;
	private final String homepage;

	@Override
	public String toString () {
		return "FokkerVersion [name=" + this.name + ", build_id=" + this.build_id + ", homepage=" + this.homepage + "]";
	}

	public FokkerVersion (final String name, final String buildID, final String homepage) {
		this.name = name;
		this.build_id = buildID;
		this.homepage = homepage;

	}

	@Override
	public void printDetails (final int offset) {
		print(offset, "FokkerShaders", FokkerShaders.component());
		print(offset, "FokkerFonts", FokkerFonts.component());
		print(offset, "FokkerTextures", FokkerTextures.component());
	}

	static private void print (final int offset, final String name, final Object value) {
		L.d(Strings.prefix(" ", offset - name.length()) + name + " - " + value);
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public String getBuildID () {
		return this.build_id;
		// sdfdsfsdf
	}

	@Override
	public String getHomePage () {
		return this.homepage;
	}

}

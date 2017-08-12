
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;

public class FokkerEngineParams {

	public static class FokkerTextureFilter {
		public static final ID Mag = Names.newID("FokkerEngineParams.TextureFilter.Mag");
		public static final ID Min = Names.newID("FokkerEngineParams.TextureFilter.Min");
	}

	public static class Settings {

		public static final ID PrintLogMessageOnMissingShader = Names.newID("PrintLogMessageOnMissingShader");
// public static final String DisableLogo = "DisableLogo";
		public static final ID AllowMissingShader = Names.newID("AllowMissingShader");

		public static final ID PrintLogMessageOnDuplicateAssetRequest = Names.newID("PrintLogMessageOnDuplicateAssetRequest");
	}

	public static class Assets {

		public static final ID SPRITE_BLACK = Names.newID("BLACK");
		public static final ID SPRITE_BLACK_DEBUG = Names.newID("BLACK_DEBUG");
		public static final ID DefaultFont = Names.newID("DefaultFont");
		public static final ID CLEAR_SCREEN_COLOR_ARGB = Names.newID("CLEAR_SCREEN_COLOR_ARGB");
		public static final ID ASSET_INFO_TAG = Names.newID("ASSET_INFO_TAG");
		public static final ID DEFAULT_LOGO_FADE_TIME = Names.newID("DEFAULT_LOGO_FADE_TIME");
	}

}

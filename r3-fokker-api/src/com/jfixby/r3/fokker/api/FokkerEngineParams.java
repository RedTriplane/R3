
package com.jfixby.r3.fokker.api;

public class FokkerEngineParams {

	public static class FokkerTextureFilter {
		public static final String Mag = "FokkerEngineParams.TextureFilter.Mag";
		public static final String Min = "FokkerEngineParams.TextureFilter.Min";
	}

	public static class Settings {

		public static final String PrintLogMessageOnMissingShader = "PrintLogMessageOnMissingShader";
// public static final String DisableLogo = "DisableLogo";
		public static final String AllowMissingShader = "AllowMissingShader";

		public static final String PrintLogMessageOnDuplicateAssetRequest = "PrintLogMessageOnDuplicateAssetRequest";
	}

	public static class Assets {

		public static final String SPRITE_BLACK = "BLACK";
		public static final String SPRITE_BLACK_DEBUG = "BLACK_DEBUG";
		public static final String DefaultFont = "DefaultFont";
		public static final String CLEAR_SCREEN_COLOR_ARGB = "CLEAR_SCREEN_COLOR_ARGB";
		public static final String ASSET_INFO_TAG = "ASSET_INFO_TAG";
		public static final String DEFAULT_LOGO_FADE_TIME = "DEFAULT_LOGO_FADE_TIME";
	}

}

package com.jfixby.r3.api;

public class EngineParams {

	public static class Settings {
		public static final String PrintLogMessageOnMissingSprite = "PrintLogMessageOnMissingSprite";
		public static final String PrintLogMessageOnMissingShader = "PrintLogMessageOnMissingShader";
		public static final String DisableLogo = "DisableLogo";
		public static final String AllowMissingRaster = "AllowMissingRaster";
		public static final String AllowMissingShader = "AllowMissingShader";
		public static final String ExitOnMissingSprite = "ExitOnMissingSprite";
		public static final String PrintLogMessageOnDuplicateAssetRequest = "PrintLogMessageOnDuplicateAssetRequest";
	}

	public static class Assets {
		public static final String RASTER_IS_MISING = "RASTER_IS_MISING";
		public static final String SPRITE_BLACK = "BLACK";
		public static final String SPRITE_BLACK_DEBUG = "BLACK_DEBUG";
		public static final String DefaultFont = "DefaultFont";
		public static final String CLEAR_SCREEN_COLOR_ARGB = "CLEAR_SCREEN_COLOR_ARGB";
		public static final String ASSET_INFO_TAG = "ASSET_INFO_TAG";
		public static final String DEFAULT_LOGO_FADE_TIME = "DEFAULT_LOGO_FADE_TIME";
	}

	public static class Version {
		public static final String Name = "r3.version.name";
		public static final String BuildID = "r3.version.build_id";
		public static final String HomePage = "r3.version.homepage";
	}

}


package com.jfixby.r3.fokker.render;

import com.jfixby.r3.engine.api.render.DefaultAssets;
import com.jfixby.r3.fokker.FOKKER_SYSTEM_ASSETS;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;

public class FokkerDefaultAssets implements DefaultAssets {

	public static ID RASTER_IS_MISING;

	public static ID SHADER_GDX_DEFAULT;
	public static ID SHADER_GRAYSCALE;

	public static ID BLACK;
	public static ID DEBUG_BLACK;
	public static ID LOGO;

	public static ID GENERIC_FONT;

	public static ID SHADERS;
	public static ID SHADER_TEST;
	public static ID SHADER_NORMAL;
	public static ID SHADER_MULTIPLY;

	FokkerDefaultAssets () {
		RASTER_IS_MISING = Names.newID(FOKKER_SYSTEM_ASSETS.RASTER_IS_MISING);
		BLACK = Names.newID(FOKKER_SYSTEM_ASSETS.BLACK);
		DEBUG_BLACK = Names.newID(FOKKER_SYSTEM_ASSETS.DEBUG_BLACK);
		GENERIC_FONT = Names.newID(FOKKER_SYSTEM_ASSETS.GENERIC_FONT);
		LOGO = Names.newID(FOKKER_SYSTEM_ASSETS.LOGO);
		SHADERS = Names.newID(FOKKER_SYSTEM_ASSETS.SHADERS);
		SHADER_TEST = Names.newID(FOKKER_SYSTEM_ASSETS.SHADER_TEST);
		SHADER_NORMAL = Names.newID(FOKKER_SYSTEM_ASSETS.SHADER_NORMAL);
		SHADER_MULTIPLY = Names.newID(FOKKER_SYSTEM_ASSETS.SHADER_MULTIPLY);
		SHADER_GRAYSCALE = Names.newID(FOKKER_SYSTEM_ASSETS.SHADER_GRAYSCALE);
		SHADER_GDX_DEFAULT = Names.newID(FOKKER_SYSTEM_ASSETS.SHADER_GDX_DEFAULT);
	}

	@Override
	public ID RASTER_IS_MISING () {
		return RASTER_IS_MISING;
	}

	@Override
	public ID BLACK () {
		return BLACK;
	}

	@Override
	public ID DEBUG_BLACK () {
		return DEBUG_BLACK;
	}

	@Override
	public ID LOGO () {
		return LOGO;
	}

}

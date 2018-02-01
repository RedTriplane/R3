
package com.jfixby.r3.fokker.render;

import com.jfixby.r3.engine.api.render.DefaultAssets;
import com.jfixby.r3.fokker.api.FOKKER_SYSTEM_ASSETS;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.names.ID;

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
		RASTER_IS_MISING = (FOKKER_SYSTEM_ASSETS.RASTER_IS_MISING);
		BLACK = (FOKKER_SYSTEM_ASSETS.BLACK);
		DEBUG_BLACK = (FOKKER_SYSTEM_ASSETS.DEBUG_BLACK);
		GENERIC_FONT = (FOKKER_SYSTEM_ASSETS.GENERIC_FONT);
		LOGO = (FOKKER_SYSTEM_ASSETS.LOGO);
		SHADERS = (FOKKER_SYSTEM_ASSETS.SHADERS);
		SHADER_TEST = (FOKKER_SYSTEM_ASSETS.SHADER_TEST);
		SHADER_NORMAL = (FOKKER_SYSTEM_ASSETS.SHADER_NORMAL);
		SHADER_MULTIPLY = (FOKKER_SYSTEM_ASSETS.SHADER_MULTIPLY);
		SHADER_GRAYSCALE = (FOKKER_SYSTEM_ASSETS.SHADER_GRAYSCALE);
		SHADER_GDX_DEFAULT = (FOKKER_SYSTEM_ASSETS.SHADER_GDX_DEFAULT);
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

	public List<ID> list () {
		final List<ID> result = Collections.newList();
		result.add(SHADER_GDX_DEFAULT);
		result.add(RASTER_IS_MISING);
		result.add(BLACK);
		result.add(DEBUG_BLACK);
		result.add(GENERIC_FONT);
		result.add(LOGO);
		result.add(SHADER_TEST);
		result.add(SHADER_NORMAL);
		result.add(SHADER_MULTIPLY);
		result.add(SHADER_GRAYSCALE);
		return result;
	}

}


package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class FOKKER_SYSTEM_ASSETS {

	public static final ID LOCAL_BANK_NAME = Names.newID("com.jfixby.fokker.assets.local");
	public static ID SHADERS = Names.newID("com.jfixby.fokker.assets.render.shader");

	public static ID RASTER_IS_MISING = Names.newID("com.jfixby.fokker.assets.render.raster_is_missing");

	public static ID BLACK = Names.newID("com.jfixby.fokker.assets.render.black");
	public static ID DEBUG_BLACK = Names.newID("com.jfixby.fokker.assets.render.black-debug");
	public static ID LOGO = Names.newID("com.jfixby.fokker.assets.render.logo");

	public static ID GENERIC_FONT = Names.newID("otf.GenericFont");

	public static ID SHADER_TEST = SHADERS.child("test");
	public static ID SHADER_NORMAL = SHADERS.child("normal");
	public static ID SHADER_MULTIPLY = SHADERS.child("multiply");
	public static ID SHADER_GRAYSCALE = SHADERS.child("grayscale");
	public static ID SHADER_GDX_DEFAULT = Names.newID("com.badlogic.gdx.graphics.g2d.SpriteBatch.DefaultShader");

}

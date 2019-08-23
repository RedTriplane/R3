
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class FOKKER_SYSTEM_ASSETS {
	public static final ID LOCAL_BANK_NAME = Names.newID("com.jfixby.fokker.assets.local");

	public static final ID RASTER_IS_MISING = Names.newID("com.jfixby.fokker.assets.render.raster_is_missing");

	public static final ID RASTER_BLACK = Names.newID("com.jfixby.fokker.assets.render.black");
	public static final ID RASTER_DEBUG_BLACK = Names.newID("com.jfixby.fokker.assets.render.black-debug");
	public static final ID RASTER_LOGO = Names.newID("com.jfixby.fokker.assets.render.logo");

	public static final ID FONT_GENERIC = Names.newID("otf.GenericFont");

	public static final ID SHADERS = Names.newID("com.jfixby.fokker.assets.render.shader");
	public static final ID SHADER_TEST = SHADERS.child("test");
	public static final ID SHADER_NORMAL = SHADERS.child("normal");
	public static final ID SHADER_MULTIPLY = SHADERS.child("multiply");
	public static final ID SHADER_GRAYSCALE = SHADERS.child("grayscale");
	public static final ID SHADER_GDX_DEFAULT = Names.newID("com.badlogic.gdx.graphics.g2d.SpriteBatch.DefaultShader");

	public static final ID SOUND_TEST_MP3 = Names.newID("com.jfixby.fokker.assets.sound.test.mp3");
	public static final ID SOUND_TEST_OGG = Names.newID("com.jfixby.fokker.assets.sound.test.ogg");
	public static final ID SOUND_TEST_WAV = Names.newID("com.jfixby.fokker.assets.sound.test.wav");

}

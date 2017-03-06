
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.java.LegacyFloat;
import com.jfixby.scarabei.api.java.LegacyInt;

public interface ShaderSettings {

	Rectangle shape ();

	boolean hasParams ();

	Mapping<String, LegacyFloat> floatParams ();

	Mapping<String, LegacyInt> intParams ();

}
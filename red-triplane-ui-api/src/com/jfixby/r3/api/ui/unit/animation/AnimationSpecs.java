
package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.scarabei.api.time.TimeStream;

public interface AnimationSpecs {

	void setTimeStream (TimeStream clock);

	TimeStream getTimeStream ();

	void setIsLooped (boolean is_looped);

	public boolean isLooped ();

}

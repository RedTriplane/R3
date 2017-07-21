
package com.jfixby.r3.activity.api.camera;

import com.jfixby.r3.activity.api.layer.Component;

public interface Shadow extends Component {

	void setValue (float f);

	float getValue ();

	public static final float ABSOLUTE_DARKNESS = 1f;
	public static final float ABSOLUTE_CLEAR = 0f;

}

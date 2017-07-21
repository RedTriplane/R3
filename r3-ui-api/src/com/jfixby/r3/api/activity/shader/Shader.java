
package com.jfixby.r3.api.activity.shader;

import com.jfixby.r3.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface Shader extends VisibleComponent {

	Rectangle getShape ();

	void setFloatParameterValue (String string, double time);

}

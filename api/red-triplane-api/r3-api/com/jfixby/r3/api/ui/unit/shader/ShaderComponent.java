
package com.jfixby.r3.api.ui.unit.shader;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface ShaderComponent extends VisibleComponent {

	void setFloatParameterValue (String name, double value);

	Rectangle getShape ();

}

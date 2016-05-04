
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface CustomInput extends VisibleComponent, InputComponent {

	void setDebugRenderFlag (boolean b);

	Collection<VisibleComponent> listOptions ();

	Collection<TouchArea> listTouchAreas ();

}

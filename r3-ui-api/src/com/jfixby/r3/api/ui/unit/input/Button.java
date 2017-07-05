
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.r3.api.ui.unit.layer.NamedComponent;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.scarabei.api.collections.Collection;

public interface Button extends VisibleComponent, InputComponent, NamedComponent {

	void setDebugRenderFlag (boolean b);

	public Collection<TouchArea> getTouchAreas ();

}

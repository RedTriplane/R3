
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.layer.NamedComponent;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface Button extends VisibleComponent, InputComponent, NamedComponent {

	void setDebugRenderFlag (boolean b);

	public Collection<TouchArea> getTouchAreas ();

}


package com.jfixby.r3.activity.api.input;

import com.jfixby.r3.activity.api.layer.NamedComponent;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.scarabei.api.collections.Collection;

public interface Button extends VisibleComponent, InputComponent, NamedComponent {

	void setDebugRenderFlag (boolean b);

	public Collection<TouchArea> getTouchAreas ();

}

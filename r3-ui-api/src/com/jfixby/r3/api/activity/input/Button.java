
package com.jfixby.r3.api.activity.input;

import com.jfixby.r3.api.activity.layer.NamedComponent;
import com.jfixby.r3.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.collections.Collection;

public interface Button extends VisibleComponent, InputComponent, NamedComponent {

	void setDebugRenderFlag (boolean b);

	public Collection<TouchArea> getTouchAreas ();

}

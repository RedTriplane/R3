
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public interface CustomInput extends VisibleComponent, InputComponent {

	void setDebugRenderFlag (boolean b);

	Collection<Raster> listOptions ();

	Collection<TouchArea> listTouchAreas ();

	void setPosition (Float2 position);

	Float2 getPosition ();

	void updateChildrenPositionRespectively ();

	void setPositionX (double x);

	void setPositionY (double y);

}


package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;

public interface CustomInput extends VisibleComponent, InputComponent {

	void setDebugRenderFlag (boolean b);

	Collection<Raster> listOptions ();

	Collection<TouchArea> listTouchAreas ();

	void setPosition (Float2 position);

	CanvasPosition getPosition ();

	void updateChildrenPositionRespectively ();

	void setPositionX (double x);

	void setPositionY (double y);

	double getPositionX ();

	double getPositionY ();

}

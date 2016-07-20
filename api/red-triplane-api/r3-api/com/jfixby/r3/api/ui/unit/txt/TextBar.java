
package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.locale.LocalizedComponent;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface TextBar extends VisibleComponent, LocalizedComponent, CanvasPositionable {

	Rectangle shape ();

}

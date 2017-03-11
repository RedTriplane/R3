
package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.r3.api.ui.unit.locale.LocalizedComponent;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface TextBar extends VisibleComponent, LocalizedComponent, CanvasPositionable {

	Rectangle shape ();

}


package com.jfixby.r3.api.ui.unit.parallax;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.r3.api.ui.unit.layer.Component;

public interface Parallax extends Component {

	void setParallaxOffset (Float2 offset);

	void setPositionX (double x);

	void setPositionY (double y);

}

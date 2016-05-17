
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.layer.Component;

public interface MouseEventListener extends Component {

	public static final MouseEventListener DEBUG = new MouseEventListener() {

		@Override
		public boolean onMouseMoved (final MouseMovedEvent event) {
			L.d("DEBUG", event);
			return true;
		}

		@Override
		public boolean onTouchDown (final TouchDownEvent event) {
			L.d("DEBUG", event);
			return true;
		}

		@Override
		public boolean onTouchUp (final TouchUpEvent event) {
			L.d("DEBUG", event);
			return true;
		}

		@Override
		public boolean onTouchDragged (final TouchDraggedEvent event) {
			L.d("DEBUG", event);
			return true;
		}

	};

	boolean onMouseMoved (MouseMovedEvent input_event);

	boolean onTouchDown (TouchDownEvent input_event);

	boolean onTouchUp (TouchUpEvent input_event);

	boolean onTouchDragged (TouchDraggedEvent input_event);

}

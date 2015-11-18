package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.layer.Component;

public interface U_OnMouseInputEventListener extends Component {

	public static final U_OnMouseInputEventListener DEBUG = new U_OnMouseInputEventListener() {

		@Override
		public boolean onMouseMoved(MouseMovedEvent event) {
			L.d(event);
			return false;
		}

		@Override
		public boolean onTouchDown(TouchDownEvent event) {
			L.d(event);
			return false;
		}

		@Override
		public boolean onTouchUp(TouchUpEvent event) {
			L.d(event);
			return false;
		}

		@Override
		public boolean onTouchDragged(TouchDraggedEvent event) {
			L.d(event);
			return false;
		}

	};

	boolean onMouseMoved(MouseMovedEvent input_event);

	boolean onTouchDown(TouchDownEvent input_event);

	boolean onTouchUp(TouchUpEvent input_event);

	boolean onTouchDragged(TouchDraggedEvent input_event);

}

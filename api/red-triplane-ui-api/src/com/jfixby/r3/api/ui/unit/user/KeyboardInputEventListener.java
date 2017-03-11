package com.jfixby.r3.api.ui.unit.user;

import com.jfixby.r3.api.ui.unit.input.MouseScrolledEvent;
import com.jfixby.r3.api.ui.unit.layer.Component;
import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.log.L;

public interface KeyboardInputEventListener extends Component {

	KeyboardInputEventListener DEBUG = new KeyboardInputEventListener() {

		@Override
		public boolean onKeyDown(Key key) {
			L.d("onKeyDown", key);
			return false;
		}

		@Override
		public boolean onKeyUp(Key key) {
			L.d("onKeyUp", key);
			return false;
		}

		@Override
		public boolean onCharTyped(char char_typed) {
			L.d("onCharTyped", char_typed);
			return false;
		}

		@Override
		public boolean onMouseScrolled(MouseScrolledEvent event) {
			L.d("onMouseScrolled", event);
			return false;
		}
	};

	boolean onKeyDown(Key key);

	boolean onKeyUp(Key key);

	boolean onCharTyped(char char_typed);

	boolean onMouseScrolled(MouseScrolledEvent event);

}

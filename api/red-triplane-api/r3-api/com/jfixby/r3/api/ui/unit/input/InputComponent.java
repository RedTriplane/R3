package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.r3.api.ui.unit.user.MouseInputEventListener;

public interface InputComponent {

	public String getName();

//	public AssetID getID();

	public MouseInputEventListener getInputListener();

	public void setInputListener(MouseInputEventListener listener);

}

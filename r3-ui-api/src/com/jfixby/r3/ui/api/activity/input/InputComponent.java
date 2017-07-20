package com.jfixby.r3.ui.api.activity.input;

import com.jfixby.r3.ui.api.activity.user.MouseInputEventListener;

public interface InputComponent {

	public String getName();

//	public AssetID getID();

	public MouseInputEventListener getInputListener();

	public void setInputListener(MouseInputEventListener listener);

}

package com.jfixby.r3.activity.api.input;

import com.jfixby.r3.activity.api.user.MouseInputEventListener;

public interface InputComponent {

	public String getName();

//	public AssetID getID();

	public MouseInputEventListener getInputListener();

	public void setInputListener(MouseInputEventListener listener);

}

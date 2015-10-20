package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.ui.unit.user.U_OnMouseInputEventListener;

public interface InputComponent {

	public String getName();

//	public AssetID getID();

	public U_OnMouseInputEventListener getInputListener();

	public void setInputListener(U_OnMouseInputEventListener listener);

}

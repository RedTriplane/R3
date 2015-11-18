package com.jfixby.r3.api.ui.unit.input;


public interface InputComponent {

	public String getName();

//	public AssetID getID();

	public U_OnMouseInputEventListener getInputListener();

	public void setInputListener(U_OnMouseInputEventListener listener);

}

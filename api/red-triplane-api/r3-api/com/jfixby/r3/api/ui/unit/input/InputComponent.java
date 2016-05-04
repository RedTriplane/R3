package com.jfixby.r3.api.ui.unit.input;


public interface InputComponent {

	public String getName();

//	public AssetID getID();

	public MouseEventListener getInputListener();

	public void setInputListener(MouseEventListener listener);

}

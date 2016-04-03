package com.jfixby.r3.ext.api.scene2d.srlz;

public class InputSettings implements java.io.Serializable{
	public boolean is_button;

	public LayerElement touch_area = null;
	public LayerElement on_hover = null;

	public LayerElement on_press = null;
	public LayerElement on_pressed = null;

	public LayerElement on_released = null;
	public LayerElement on_release = null;

}

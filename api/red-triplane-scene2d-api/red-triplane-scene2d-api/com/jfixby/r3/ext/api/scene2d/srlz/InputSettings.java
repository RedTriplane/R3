
package com.jfixby.r3.ext.api.scene2d.srlz;

public class InputSettings implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 564549012111462584L;
	public boolean is_button;
	public boolean is_switch;
	public boolean is_custom;

	public LayerElement touch_area = null;
	public LayerElement on_hover = null;

	public LayerElement on_press = null;
	public LayerElement on_pressed = null;

	public LayerElement on_released = null;
	public LayerElement on_release = null;

}

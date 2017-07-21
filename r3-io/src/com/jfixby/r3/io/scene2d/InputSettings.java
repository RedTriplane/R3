
package com.jfixby.r3.io.scene2d;

public class InputSettings implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 564549012111462584L;
	public boolean is_button;
	public boolean is_switch;
	public boolean is_custom;
	public boolean is_touch_area;

	public LayerElement touch_area = null;
	public LayerElement on_hover = null;

	public LayerElement on_press = null;
	public LayerElement on_pressed = null;

	public LayerElement on_released = null;
	public LayerElement on_release = null;

	@Override
	public String toString () {
		return "InputSettings [is_button=" + this.is_button + ", is_switch=" + this.is_switch + ", is_custom=" + this.is_custom
			+ ", is_touch_area=" + this.is_touch_area + "]";
	}

}


package com.jfixby.r3.activity.api.input;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.layer.VisibleComponent;

public class ButtonSpecs {

	public VisibleComponent onPressed;
	public VisibleComponent onReleased;
	public VisibleComponent onHover;
	public VisibleComponent onPress;

	public final ArrayList<TouchAreaSpecs> touchAreas = new ArrayList<>();

}

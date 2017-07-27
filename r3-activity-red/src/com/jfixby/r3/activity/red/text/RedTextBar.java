
package com.jfixby.r3.activity.red.text;

import com.jfixby.r3.activity.api.txt.TextBar;
import com.jfixby.r3.activity.api.txt.TextBarSpecs;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.math.Angle;

public class RedTextBar implements TextBar {

	public RedTextBar (final TextBarSpecs text_specs, final RedComponentsFactory componentsFactory) {
		Err.throwNotImplementedYet();
	}

	@Override
	public void hide () {
	}

	@Override
	public void show () {
	}

	@Override
	public boolean isVisible () {
		return false;
	}

	@Override
	public void setVisible (final boolean b) {
	}

	@Override
	public void setName (final String name) {
	}

	@Override
	public String getName () {
		return null;
	}

	@Override
	public void setLocaleName (final String locale_name) {
	}

	@Override
	public String getLocaleName () {
		return null;
	}

	@Override
	public void setPosition (final CanvasPosition position) {
	}

	@Override
	public void setPosition (final ReadOnlyFloat2 position) {
	}

	@Override
	public void setPosition (final double position_x, final double position_y) {
	}

	@Override
	public void offset (final double x, final double y) {
	}

	@Override
	public double getPositionX () {
		return 0;
	}

	@Override
	public double getPositionY () {
		return 0;
	}

	@Override
	public Angle getRotation () {
		return null;
	}

	@Override
	public void setRotation (final Angle rotation) {
	}

	@Override
	public void setRotation (final double rotation) {
	}

	@Override
	public void setPositionX (final double x) {
	}

	@Override
	public void setPositionY (final double y) {
	}

	@Override
	public void setText (final String text) {
	}

	@Override
	public String getText () {
		return null;
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
	}

	@Override
	public boolean getDebugRenderFlag () {
		return false;
	}

	@Override
	public void setPositionXY (final double x, final double y) {
	}

}

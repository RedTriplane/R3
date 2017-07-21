
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.color.CustomColor;
import com.jfixby.scarabei.api.debug.Debug;

public abstract class RedDrawableComponent implements VisibleComponent {
	private boolean visible = true;
	private boolean debug_render = false;;
	private String name = null;
	private float opacity = 1;
	private final CustomColor debug_color;

	public RedDrawableComponent () {
		this.debug_color = Colors.newColor();
	}

	@Override
	public void setVisible (final boolean b) {
		this.show();
		if (!b) {
			this.hide();
		}
	}

	public void setDebugColor (final Color debug_render_color) {
		this.debug_color.setValue(debug_render_color);
	}

	public Color getDebugColor () {
		return this.debug_color;
	}

	@Override
	public String getName () {
		return this.name;
	}

	public void setOpacity (final float alpha) {
		this.opacity = alpha;
	}

	public float getOpacity () {
		return this.opacity;
	}

	@Override
	public void setName (final String name) {
		Debug.checkEmpty("name", name);
		Debug.checkNull("name", name);
		this.name = name;
	}

	public void setDebugRenderFlag (final boolean b) {
		this.debug_render = b;
	}

	final public boolean getDebugRenderFlag () {
		return this.debug_render;
	}

	@Override
	public void hide () {
		this.visible = false;
	}

	@Override
	public void show () {
		this.visible = true;
	}

	@Override
	final public boolean isVisible () {
		return this.visible;
	}

}

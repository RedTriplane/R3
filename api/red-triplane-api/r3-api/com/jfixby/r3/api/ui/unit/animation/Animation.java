package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface Animation extends VisibleComponent {

	void startAnimation();

	void stopAnimation();

	boolean isAnimationDone();

	public boolean isLooped();

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();

	public void setDebugColor(Color debug_render_color);

	public Color getDebugColor();

}

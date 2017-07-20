package com.jfixby.r3.ui.api.activity.animation;

import com.jfixby.r3.ui.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.color.Color;

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
